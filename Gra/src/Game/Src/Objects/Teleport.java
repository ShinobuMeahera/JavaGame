package Game.Src.Objects;

import Game.Src.Control.Animation;
import Game.Src.Map.TileMap.TileMap;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Teleport extends ParentObject {

	private static final String PORTALSPRITEMAP = "/Game/Src/Assets/penis.gif";
	private BufferedImage[] sprites;

	public Teleport(TileMap tm) {

		super(tm);
		animation = new Animation();

		facingRight = true;
		width = height = 32;
		cwidth = 64;
		cheight = 64;

		try {
			BufferedImage spritesheet = ImageIO.read( getClass().getResourceAsStream(PORTALSPRITEMAP));

			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
						i * width, 0, width, height
				);
			}

			animation.setFrames(sprites);
			animation.setDelay(8);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error from loading graphics for Teleport");
			System.exit(0);
		}

	}
	
	public void update() {
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
}
