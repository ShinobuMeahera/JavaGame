import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// PLIK GOTOWY

public class Content {
		
	public static BufferedImage[][] EnemySkeleton = load("enemy-skeleton-spritemap.png", 36, 48);
	public static BufferedImage[][] EnemySkeletonDead = load("enemy-skeleton-dead-spritemap.png", 36, 48);
	public static BufferedImage[][] EnemyGhost = load("enemy-ghost-spritemap.png", 25, 35);
	public static BufferedImage[][] EnergyParticle = load("energy-particle.png", 5, 5);
	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}
	
}
