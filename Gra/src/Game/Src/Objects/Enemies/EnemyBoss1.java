package Game.Src.Objects.Enemies;

import Game.Src.Control.Content;
import Game.Src.Map.TileMap.TileMap;
import Game.Src.Objects.Player;
import Game.Src.Start.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyBoss1 extends Enemy {

	public static final String BOSSHPBAR = "/Game/Src/Assets/boss-hp-bar.png";
	public static final String BOSSBAROUTLINE = "/Game/Src/Assets/boss-hp-bar-outline.png";

	private BufferedImage hpBar;
	private BufferedImage hpBarOutline;
	private boolean active;
	private int eventCount;
	
	private int tick;
	private double a;
	private double b;
	private int currentAction;
	private boolean playerCatch;
	private int szerokosc;
	
	private double hp_max;
	private double hp;
	private double maxHp;
	private final Font font = new Font("Viner Hand ITC", Font.PLAIN, 18);
	
	public EnemyBoss1(TileMap tm, Player p) {
		
		super(tm);
		player = p;
		
		health =  50;
		maxHealth = 50;
		
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
			hpBar = ImageIO.read( getClass().getResourceAsStream(BOSSHPBAR));
			hpBarOutline = ImageIO.read( getClass().getResourceAsStream(BOSSBAROUTLINE));
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Error loading graphics from BOSS_1.");
			System.exit(0);
		}
	}
		
	public void update() {
		tick++;
		eventCount++;
		
		hp = (double)health;
		maxHp = (double)maxHealth;
		
		hp_max = hp/maxHp;
		
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
	}
	
	public void drawHPBar(Graphics2D g){
		if (dead) return;
	
		if (playerCatch){
			
			g.drawImage(hpBar, 72, 122, (int)((GamePanel.WIDTH-142)*hp_max ), 14, null);
			g.drawImage(hpBarOutline, 0+70, 0+120, GamePanel.WIDTH-140, 16, null);
			
			g.setFont(font);
			g.drawString("Andromalius", GamePanel.WIDTH/2-20, 105);
			}
		}
}
