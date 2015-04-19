package game3d.graphics;

import game3d.Game;

import java.util.Random;

public class Screen extends Render {

	private Render test;
	private final int BLOCK_SIZE = 256;
	private Render3D render;

	@SuppressWarnings("unused")
	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render = new Render3D(width, height);
	}

	public void render(Game game) {
		for (int i = 0; i < (width * height); i++) {
			pixels[i] = 0;
		}
		render.floor(game);
		render.renderDistanceLimiter();
		draw(render, 0, 0);
	}
}
