package game3d.graphics;

import game3d.Game;
import game3d.input.Controller;

public class Render3D extends Render {
	public double[] zBuffer;
	private double renderDistance = 5000;

	public Render3D(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
	}

	public void floor(Game game) {
		double floorPosition = 8.0;
		double ceilingPosition = 8.0;
		double forward = game.controls.z;
		double right = game.controls.x;
		double up = game.controls.y; // TEST: Math.sin(game.time/ 10.0) * 2;
		double walking = Math.sin(game.time / 6.0) * calculateWalkFactor(game);
		double rotation = game.controls.rotation;
		double cosine = Math.cos(rotation);
		double sine = Math.sin(rotation);
		for (int y = 0; y < height; y++) {
			double ceiling = (y + -height / 2.0) / height;
			double z = (ceiling != 0 ? ((floorPosition + up + walking) / ceiling) : 0.0);
			if (ceiling < 0) {
				z = (ceilingPosition - up - walking) / -ceiling;
			}
			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine; // + right;
				double yy = z * cosine - depth * sine; // + forward;
				int xPix = (int) (xx + right);
				int yPix = (int) (yy + forward);
				int pixelPosition = x + y * width;
				zBuffer[pixelPosition] = z;
				//pixels[pixelPosition] = (((xPix & 15) << 4) | ((yPix & 15) << 12)) & 0x00ffffff;
				pixels[pixelPosition] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 8];
				if (z > 500 || y == (height / 2)) {
					pixels[pixelPosition] = 0;
				}
			}
		}
	}

	public void renderDistanceLimiter() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);
			if (brightness < 0) brightness = 0;
			if (brightness > 255) brightness = 255;
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = color & 0xff;
			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;
			pixels[i] = r << 16 | g << 8 | b;
		}
	}

	private double calculateWalkFactor(Game game) {
		double walkFactor = 0.0;
		if (Controller.isWalking) {
			if (Controller.isCrouchWalk) {
				walkFactor = 0.3;
			} else if (Controller.isRunning) {
				walkFactor = 0.8;
			} else {
				walkFactor = 0.5;
			}
		}
		return walkFactor;
	}
}
