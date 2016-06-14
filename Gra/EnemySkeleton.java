import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// PLIK GOTOWY

public class EnemySkeleton extends Enemy {
	
	private BufferedImage[] sprites;
	private Player player;
	private boolean active;

	public EnemySkeleton(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health = maxHealth = 3;
		
		width = 36;
		height = 48;
		
		cwidth = 25;
		cheight = 45;
		
		damage = 1;
		moveSpeed = 0.8;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		
		left = true;
		facingRight = false;
		
		sprites = Content.EnemySkeleton[0];
		
		
		animation.setFrames(sprites);
		animation.setDelay(4);
	}
	
	private void getNextPosition() {
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
	}
	
	public void update() {
		
		if(!active) {
			if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
			return;
		}
		
		if (dead) {
			health = 40;
			sprites = Content.EnemySkeletonDead[0];
			health--;
			dx = 0;
			dy = 0;
		}
		
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		
		// update animation
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}
