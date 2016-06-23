import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// PLIK GOTOWY

public class EnemyGhost extends Enemy {
	
	private BufferedImage[] sprites;
	private Player player;
	private boolean active;
	
	private boolean jumping;
	
	private static final int IDLE = 0;
	private static final int JUMPING = 1;
	private static final int ATTACKING = 2;
	
	private int currentAction;
	
	private int attackTick;
	private int attackDelay = 30;
	private int step;
	
	public EnemyGhost(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health = maxHealth = 1;
		lastBreath = 5;
		width = 25;
		height = 40;
		
		cwidth = 25;
		cheight = 40;
		
		damage = 1;

		moveSpeed = 1.5;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		
		attackTick = 0;
		facingRight = true;
		
		sprites = Content.EnemyGhost[0];
		
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
		
		
		if (dead) {
			
			lastBreath--;
			

			if (lastBreath <= 0) remove = true;
		}
		else{
			if(!active) {
				if(Math.abs(player.getx() - x) < GamePanel.WIDTH) active = true;
				return;
			}
			
			
			getNextPosition();
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
			
			if(player.getx() < x) facingRight = false;
			else facingRight = true;
			
			// idle
			if(step == 0) {
				if(currentAction != IDLE) {
					currentAction = IDLE;
					animation.setDelay(-1);
				}
				attackTick++;
				if(attackTick >= attackDelay && Math.abs(player.getx() - x) < 60) {
					step++;
					attackTick = 0;
				}
			}
			// jump away
			if(step == 1) {
				if(currentAction != JUMPING) {
					currentAction = JUMPING;
					animation.setDelay(-1);
				}
				jumping = true;
				if(facingRight) left = true;
				else right = true;
				if(falling) {
					step++;
				}
			}
			// attack
			if(step == 2) {
				if(dy > 0 && currentAction != ATTACKING) {
					currentAction = ATTACKING;

				}
				if(currentAction == ATTACKING && animation.hasPlayedOnce()) {
					step++;
					currentAction = JUMPING;

				}
			}
			// done attacking
			if(step == 3) {
				if(dy == 0) step++;
			}
			// land
			if(step == 4) {
				step = 0;
				left = right = jumping = false;
			}
			
			// update animation
			animation.update();
		}
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
	}
}
