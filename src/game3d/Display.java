package game3d;

import game3d.graphics.Screen;
import game3d.input.Controller;
import game3d.input.InputHandler;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Display extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "3D Game Test";
	public static final int FRAMES_PER_SECOND = 60;
	private Thread thread;
	private boolean running = false;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private int[] pixels;
	private InputHandler input;
	private int newX = 0;
	private int oldX = 0;
	private int fps = 0;

	public Display() {
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		game = new Game();
		input = new InputHandler();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addFocusListener(input);
	}

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void tick() {
		game.tick(input.key);
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render(game);
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("Verdana", Font.BOLD, 20));
		g.setColor(Color.yellow);
		g.drawString("FPS: " + fps, 20, 40);
		g.dispose();
		bs.show();
	}

	@Override
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / (double) FRAMES_PER_SECOND;

		int tickCount = 0;

		boolean ticked = false;

		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;

			previousTime = currentTime;
			unprocessedSeconds += (passedTime / 1000000000.0);

			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % FRAMES_PER_SECOND == 0) {
					previousTime += 1000;
					tickCount = 0;
					fps = frames;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
				ticked = false;
			}
			newX = InputHandler.MouseX;
			if (newX > oldX) {
				Controller.turnRight = true;
			} else if (newX < oldX) {
				Controller.turnLeft = true;
			} else {
				Controller.turnLeft = false;
				Controller.turnRight = false;
			}
			oldX = newX;
		}
	}

	public void resizeToInternalSize(JFrame frame, int internalWidth, int internalHeight) {
		Insets insets = frame.getInsets();
		final int newWidth = internalWidth + insets.left + insets.right;
		final int newHeight = internalHeight + insets.top + insets.bottom;
		Runnable resize = new Runnable() {
			public void run() {
				setSize(newWidth, newHeight);
			}
		};
		if (SwingUtilities.isEventDispatchThread()) {
			try {
				SwingUtilities.invokeAndWait(resize);
			} catch (Exception e) {
				// ignore
			}
		} else {
			resize.run();
		}
		validate();
	}

	public static void main(String[] args) {
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.getContentPane().setCursor(blank);
		frame.setTitle(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		BufferedImage imageIcon = null;
		try {
			URL location = Display.class.getProtectionDomain().getCodeSource().getLocation();
			File file = new File(location.getFile());
			imageIcon = ImageIO.read(new File(file.getParentFile() + "/res/icons/icon.png"));
			frame.setIconImage(imageIcon);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		frame.setVisible(true);
		frame.requestFocus();
		game.resizeToInternalSize(frame, WIDTH, HEIGHT);
		game.start();
	}
}
