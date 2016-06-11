package game3d.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class Texture {
	public static Render floor = loadBitmap("/res/textures/floor.png");

	public static Render loadBitmap(String fileName) {
		try {
			URL location = Screen.class.getProtectionDomain().getCodeSource().getLocation();
			File file = new File(location.getFile());
			BufferedImage image = ImageIO.read(new File(file.getParentFile() + fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		} catch (Exception e) {
			System.out.println("CRASH!");
			throw new RuntimeException(e);
		}
	}
}