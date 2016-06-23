import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// PLIK GOTOWY

public class EnemyBoss1 extends Enemy {
	
	private BufferedImage[] sprites;
	private BufferedImage hpBar;
	private BufferedImage hpBarOutline;
	private Player player;
	private boolean active;
	private int eventCount;
	
	private int tick;
	private double a;
	private double b;
	private int currentAction;
	private boolean playerCatch;
	
	public EnemyBoss1(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		health = maxHealth = 100;
		lastBreath = 5;
		width = 57*2;
		height = 88*2;
		
		eventCount = 0;
		cwidth = 57*2;
		cheight = 88*2;
		
		damage = 50;
		
		playerCatch = false;
		
		moveSpeed = 0.1;
		maxSpeed = 1.8;
		stopSpeed = 0.1;

		facingRight = false;
		
		sprites = Content.EnemyBoss1[0];
		
		animation.setFrames(sprites);
		animation.setDelay(4);
		
		tick = 0;
		b = Math.random() * 0.06 + 0.07;
		
		try{
			hpBar = ImageIO.read( getClass().getResourceAsStream("boss-hp-bar.png"));
			hpBarOutline = ImageIO.read( getClass().getResourceAsStream("boss-hp-bar-outline.png"));
		} catch (Exception e){}
	}
		
	public void update() {
		tick++;
		eventCount++;
		
		if (eventCount % 50 == 0){
			
		}
		if (dead) {
			
			lastBreath--;
			

			if (lastBreath <= 0) remove = true;
		}
		
		if(player.getx() < x) facingRight = false;
		else facingRight = true;
		
		
		if (player.getx() > 2240 && player.gety() > 1530 && player.gety() < 1870){
			playerCatch = true;
			
			if (Math.abs(player.getx() - x) < 250){ // jestesmy blisko bossa, to sie oddala od nas
				
				if (!facingRight) {
					dx += moveSpeed;
					if(dx > maxSpeed) {
						dx = maxSpeed;
					}
				}
				else {
					dx -= moveSpeed;
					if(dx < -maxSpeed) {
						dx = -maxSpeed;
					}
				}
			} 
			else if(Math.abs(player.getx() - x) > 250 && Math.abs(player.getx() - x) < 400){ // oddalimy sie, ale nas goni
				if (facingRight) {
					dx += moveSpeed;
					if(dx > maxSpeed) {
						dx = maxSpeed;
					}
				}
				else {
					dx -= moveSpeed;
					if(dx < -maxSpeed) {
						dx = -maxSpeed;
					}
				}
			}
		}
		else if (Math.abs(player.getx() - x) > 400 && !playerCatch){ // ale jezeli jestesmy daleko, to opuszcza poscig
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
						
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		else playerCatch = false;
		
		x += dx;
		y = Math.sin(b * tick) + y;
		
		// update animation
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		super.draw(g);
		if (playerCatch){
			g.drawImage(hpBar, 70, 72, health*10/2, 8, null);

		}
	}
	
	public void drawHPBar(Graphics2D g){
	if (playerCatch){

			g.drawImage(hpBarOutline, 68, 70, 504, 12, null);
			
			g.drawString("Andromalius", GamePanel.WIDTH/2, 60);
		}
	}
}
