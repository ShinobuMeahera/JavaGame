import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Teleport extends Object {
	
	private BufferedImage[] sprites;
	private boolean facingRight;
	private Animation animation;
	
	public Teleport(TileMap tm) {
		super(tm);
		facingRight = true;
		width = height = 32;
		cwidth = 32;
		cheight = 32;
		try {
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("portal-spritemap.png")
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
		}
	}
	
	public void update() {
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		if(facingRight) {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2), 		(int)(y + ymap - height / 2), null );
		}
		else {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null );
		}
	}
	
}
