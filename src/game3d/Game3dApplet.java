package game3d;

import java.awt.BorderLayout;

import javax.swing.JApplet;

public class Game3dApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	private Display display = new Display();

	public void init() {
		setLayout(new BorderLayout());
		add(display);
	}

	public void start() {
		display.start();
	}

	public void stop() {
		display.stop();
	}
}