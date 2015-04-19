package game3d.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements MouseListener, KeyListener, MouseMotionListener, FocusListener {
	public boolean[] key = new boolean[68836];
	public static int MouseX;
	public static int MouseY;

	@Override
	public void focusGained(FocusEvent e) {
		// System.out.println("focusGained invoked.");
	}

	@Override
	public void focusLost(FocusEvent e) {
		// System.out.println("focusLost invoked.");
		for (int i = 0; i < key.length; i++) {
			key[i] = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// System.out.println("mouseDragged invoked.");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// System.out.println("mouseMoved invoked.");
		MouseX = e.getX();
		MouseY = e.getY();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// System.out.println("keyTyped invoked.");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("keyPressed invoked.");
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length)
			key[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// System.out.println("keyReleased invoked.");
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length)
			key[keyCode] = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("mouseClicked invoked.");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// System.out.println("mousePressed invoked.");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// System.out.println("mouseReleased invoked.");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// System.out.println("mouseEntered invoked.");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// System.out.println("mouseExited invoked.");
	}
}
