package Game.Src.Objects;

import Game.Src.Map.TileMap.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Teleport extends ParentObject {

	public Teleport(TileMap tm) {

		super(tm);
		facingRight = true;
		width = height = 32;
		cwidth = 32;
		cheight = 32;

		BufferedImage[] sprites;

		try {
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Game/Src/Assets/portal-spritemap.png")
			);
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width, 0, width, height
				);
			}
			animation.setFrames(sprites);
			animation.setDelay(1);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics for TELEPORT.");
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
