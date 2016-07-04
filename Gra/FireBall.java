import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class FireBall extends Object {
	
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private Rectangle attackRect;
	private int damage;	
	
	public FireBall(TileMap tm, boolean right) {
		
		super(tm);
		
		animation = new Animation();
		
		moveSpeed = 3.8;
		hit = false;
		remove = false;
		damage = 5;
		
		width = 30;
		height = 15;
		cwidth = 28;
		cheight = 14;
		
		attackRect = new Rectangle(0, 0, 0, 0);
		attackRect.width = 28;
		attackRect.height = 14;
		
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
	
	public void update(ArrayList<Enemy> enemies) {
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
			
			// sprawdzenie ataku, zadajemy obrazenia wrogowi
			
			if(e.intersects(attackRect)) {
					e.hit(damage);
					hit = true;
			}
			
		}
		if (!hit){
			attackRect.y = (int)y - 7;
			if(facingRight) attackRect.x = (int)x - 7;
			else attackRect.x = (int)x - 20;
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
		}
		
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}