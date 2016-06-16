import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class FireBall extends Object {
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	protected Animation animation = new Animation();
	private static boolean facingRight;
	
	public FireBall(TileMap tm, boolean right) {
		
		super(tm);
				
		moveSpeed = 3.8;
		hit = false;
		remove = false;
		
		width = 30;
		height = 15;
		cwidth = 14;
		cheight = 14;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream(
					"fireball-spriteset.png"
				)
			);
			
			sprites = new BufferedImage[12];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
			hitSprites = new BufferedImage[3];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(
					i * width,
					height,
					width,
					height
				);
			}
			
			animation.setFrames(sprites);
			animation.setDelay(4);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void shootFireball(double startX, double startY, boolean facing){
		x = startX;
		y = startY;
		facingRight = facing;
		
		if(facingRight) dx = moveSpeed;
		else dx = -moveSpeed;
	}
	public void setHit() {
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(4);
		dx = 0;
	}
	
	public boolean isHit() { return hit; }
	public boolean shouldRemove() { return remove; }
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
		}
		
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		if(facingRight) {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2), 		(int)(y + ymap - height / 2), null );
		}
		else {
			g.drawImage( animation.getImage(), (int)(x + xmap - width / 2 + width), (int)(y + ymap - height / 2), -width, height, null );
		}
	}
	
}


















