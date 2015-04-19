package game3d.graphics;

public class Render {
	public int width;
	public int height;
	public int[] pixels;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void draw(Render render, int xoffs, int yoffs) {
		for (int y = 0; y < render.height; y++) {
			int yPix = y + yoffs;
			if (yPix < 0 || yPix >= height)
				continue;
			for (int x = 0; x < render.width; x++) {
				int xPix = x + xoffs;
				if (xPix < 0 || xPix >= width)
					continue;
				pixels[xPix + yPix * width] = render.pixels[x + y * render.width];
				int alpha = render.pixels[x + y * render.width];
				if ((alpha & 0x00ffffff) > 0) {
					pixels[xPix + yPix * width] = alpha;
				}
			}
		}
	}
}
