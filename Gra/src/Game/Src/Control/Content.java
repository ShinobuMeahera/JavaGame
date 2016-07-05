package Game.Src.Control;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Content {

	private static final String SKELETONSPRITEMAP = "/Game/Src/Assets/enemy-skeleton-spritemap.png";
	private static final String DEADSKELETONSPRITEMAP = "/Game/Src/Assets/enemy-skeleton-dead-spritemap.png";
	private static final String GHOSTSPRITEMAP = "/Game/Src/Assets/enemy-ghost-spritemap.png";
	private static final String PARTICLESPRITEMAP = "/Game/Src/Assets/energy-particle.png";
	private static final String BOSS1SPRITEMAP = "/Game/Src/Assets/boss-1.png";

	public static BufferedImage[][] EnemySkeleton = load(SKELETONSPRITEMAP, 36, 48);
	public static BufferedImage[][] EnemySkeletonDead = load(DEADSKELETONSPRITEMAP, 36, 48);
	public static BufferedImage[][] EnemyGhost = load(GHOSTSPRITEMAP, 25, 35);
	public static BufferedImage[][] EnergyParticle = load(PARTICLESPRITEMAP, 5, 5);
	public static BufferedImage[][] EnemyBoss1 = load(BOSS1SPRITEMAP, 57*2, 88*2);

	private static BufferedImage[][] load(String s, int w, int h) {

		BufferedImage[][] ret;

		try {
			BufferedImage ss = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = ss.getWidth() / w;
			int height = ss.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = ss.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics from CONTENT.");
			System.exit(0);
		}
		return null;
	}
	
}
