import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Player extends Object{
	
	private ArrayList<Enemy> enemies;
	// dostepne ruchy
	protected boolean hi_attack;
	protected boolean attack;
	protected boolean low_attack;
	public static boolean fireballShooted;
	public int fireballCooldown;
	public int maxFireballCooldown;
		
	private boolean doubleJump;
	private boolean alreadyDoubleJump;
	private double doubleJumpStart;
	
	// dynks do animacji
	public boolean facing = true;		// true - w prawo, false - w lewo
	protected int currentAction;
	protected int previousAction;
	
	private int health;
	private int maxHealth;
	private int damage;
	public boolean knockback;
	private boolean flinching;
	private long flinchCount;
	private boolean teleporting;
	private boolean dashing;
	private int dashTimer;
	
	// ANIMACJE
	private ArrayList<BufferedImage[]> sprites;
	private ArrayList<BufferedImage[]> robeSprites;
	private ArrayList<BufferedImage[]> swordSprites;
	
	//ilosc klatek animacji
	private final int[] NUMFRAMES = {
		1, 1, 1, 8, 4, 4, 4, 1, 8
	};
	
	//rozmiar klatki animacji
	private final int[] FRAMEWIDTHS = {
		46, 46, 46, 46, 46, 46, 46, 46, 46
	};
	
	//rozmiar klatki animacji
	private final int[] FRAMEHEIGHTS = {
		50, 50, 50, 50, 50, 50, 50, 50, 50
	};
	
	//opoznienie klatki, im mniejsze tym szybsza animacja
	private final int[] SPRITEDELAYS = {
		-1, -1, -1, 5, 5, 5, 5, -1, 4
	};
	
	
	
	// KLATKI DLA MEICZA
	private final int [] swordNUMFRAMES = {
		0, 0, 0, 0, 5, 5, 5, 0, 0
	};
	private final int[] swordFRAMEWIDTHS = {
		60, 60, 60, 60, 60, 60, 60, 60, 60
	};
	
	//rozmiar klatki animacji
	private final int[] swordFRAMEHEIGHTS = {
		30, 30, 30, 30, 30, 30, 30, 30, 30
	};
	
	//opoznienie klatki, im mniejsze tym szybsza animacja
	private final int[] swordSPRITEDELAYS = {
		-1, -1, -1, -1, 5, 5, 5, -1, -1
	};
	
	//klasa animacji
	protected Animation animation = new Animation();
	protected Animation robeAnimation = new Animation();
	protected Animation swordAnimation = new Animation();
	
	private Rectangle attackRect;
	private Rectangle attackRectDraw;
	private Rectangle aur;
	private Rectangle alr;
	private Rectangle cr;
	
	// akce animacji, spojrz na obrazek
	private static final int STAND = 0;
	private static final int JUMPING = 1;
	private static final int FALLING = 2;
	private static final int WALKING = 3;
	private static final int ATTACK = 4;
	private static final int HIGH_ATTACK = 5;
	private static final int LOW_ATTACK = 6;
	private static final int SQUAT = 7;
	private static final int KNOCKBACK = 8;
	private static final int DEAD = 9;
	private static final int TELEPORTING = 10;

	public Player(TileMap tm) {
	
		super(tm);
		
		attackRect = new Rectangle(0, 0, 0, 0);
		attackRect.width = 20;
		attackRect.height = 10;
		maxFireballCooldown = 50;
		
		fireballShooted = false;
		setFireballCooldown(maxFireballCooldown);
		
		alr = new Rectangle((int)x - 15, (int)y - 45, 45, 45);
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;

		attackRectDraw = new Rectangle();
		attackRectDraw = attackRect;
		
		//rozmiary gracza, do wyswietlenia
		width = 45;
		height = 45;
		
		// rozmiary collision boxa
		cwidth = 20;
		cheight = 45;
		
		//artybuty poruszania sie
		moveSpeed = 0.5;
		maxSpeed = 2.8;
		stopSpeed = 1.0;
		fallSpeed = 0.2;
		maxFallSpeed = 9.0;
		jumpStart = -5.5;
		stopJumpSpeed = 0.3;
		doubleJumpStart = -5;
		
		facing = true;
		attack = false;
		hi_attack = false;
		low_attack = false;
		
		damage = 2;
		health = maxHealth = 5;
				
		// ładowanie sprajtow, ogolnie to powinny byc tak ustawione, że co linijka to inna animacja
		try {
			
			BufferedImage spritesheet = ImageIO.read( getClass().getResourceAsStream("player-spritemap.png"	));
			BufferedImage spritesheet2 = ImageIO.read(getClass().getResourceAsStream("robe02-spritemap.png"	));
			BufferedImage spritesheet3 = ImageIO.read(getClass().getResourceAsStream("sword-slash.png"		));
			
			//tutaj częśc dla człowieczka
			int count = 0;
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet.getSubimage(
						j * FRAMEWIDTHS[i],
						count,
						FRAMEWIDTHS[i],
						FRAMEHEIGHTS[i]
					);
				}
				sprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}

			// tutaj część dla szaty
			count = 0;
			robeSprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < NUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[NUMFRAMES[i]];
				for(int j = 0; j < NUMFRAMES[i]; j++) {
					bi[j] = spritesheet2.getSubimage(
						j * FRAMEWIDTHS[i],
						count,
						FRAMEWIDTHS[i],
						FRAMEHEIGHTS[i]
					);
				}
				robeSprites.add(bi);
				count += FRAMEHEIGHTS[i];
			}	
			
			// tutaj czesc dla miecza
			count = 0;
			swordSprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < swordNUMFRAMES.length; i++) {
				BufferedImage[] bi = new BufferedImage[swordNUMFRAMES[i]];
				for(int j = 0; j < swordNUMFRAMES[i]; j++) {
					bi[j] = spritesheet3.getSubimage(
						j * swordFRAMEWIDTHS[i],
						count,
						swordFRAMEWIDTHS[i],
						swordFRAMEHEIGHTS[i]
					);
				}
				swordSprites.add(bi);
				count += swordFRAMEHEIGHTS[i];
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
		setAnimation(STAND);
	}	
	
	public void init(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}
	
	public void setFireballCooldown(int x){
		fireballCooldown = x;
	}
	
	public void setTeleporting(boolean b) {
		teleporting = b;
	}
	
	public boolean isFireballReady(){
		if (fireballCooldown <= 0 && (!falling || jumping )&& !knockback) return true;
		else return false;
	}
	
	public void setJumping(boolean b) {
		if(knockback) return;
		if(b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
		}
		jumping = b;
	}
	
	public void setDead() {
		health = 0;
		stop();
	}
	
	public void setAttacking() {
		if(knockback) return;
		if(dashing) return;
		
		if(jumping && (!attack || !hi_attack) && !squat){
					hi_attack = true;
			attack = false;
			low_attack = false;
		}
		else if (squat && (!attack || !low_attack) && !jumping && !falling){
			hi_attack = false;
			attack = false;
					low_attack = true;
		}
		else if (!squat && !jumping && !falling && !attack && !low_attack && !hi_attack){
			hi_attack = false;
						attack = true;
			low_attack = false;
		}
	}
	
	public void setDashing() {
		if(knockback) return;
		if(!attack && !hi_attack && !dashing) {
			dashing = true;
			dashTimer = 0;
		}
	}
		
	public void reset() {
		facing = true;
		currentAction = -1;
		stop();
	}
	
	public void stop() {
		left = right = jumping = flinching = dashing = squat = attack = hi_attack = low_attack = false;
	}
	
	private void getNextPosition() {
		
		if(knockback) {
			dy += fallSpeed * 2;
			if(!falling) knockback = false;
			return;
		}
		
		//double maxSpeed = this.maxSpeed;
		//if(dashing) maxSpeed *= 1.75;
		
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
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
		
		if((attack || hi_attack || low_attack || dashing) && !(jumping || falling)) { dx = 0; }		
		
		if(jumping && !falling) {
			dy = jumpStart;
			falling = true;
			
		}
		
		if(dashing) {
			if (dashTimer > 50) dashing = false;
			else{
				dashTimer++;
				if(facing) dx = moveSpeed * (10 - dashTimer * 0.07);
				else dx = -moveSpeed * (10 - dashTimer * 0.07);
			}
		}
		
		if(doubleJump) {
			dy = doubleJumpStart;
			alreadyDoubleJump = true;
			doubleJump = false;
		}
		
		if(!falling) alreadyDoubleJump = false;
		
		if(falling) {
			dy += fallSpeed;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
	}
	
	private void setAnimation(int i) {
		currentAction = i;
		
		animation.setFrames(sprites.get(currentAction));
		robeAnimation.setFrames(robeSprites.get(currentAction));
		swordAnimation.setFrames(swordSprites.get(currentAction));
		
		animation.setDelay(SPRITEDELAYS[currentAction]);
		robeAnimation.setDelay(SPRITEDELAYS[currentAction]);
		swordAnimation.setDelay(swordSPRITEDELAYS[currentAction]);
	
		width = FRAMEWIDTHS[currentAction];
		height = FRAMEHEIGHTS[currentAction];
	}
	
	public void hit(int damage) {
		if(flinching) return;
		
		stop();
		health -= damage;
		if(health < 0) health = 0;
		flinching = true;
		flinchCount = 0;

		if(facing) dx = -1;
		else dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}
	
	public void update() {
		boolean isFalling = falling;
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0) x = (int)x;
		if (maxFireballCooldown - fireballCooldown > 15) fireballShooted = false;
		if (fireballCooldown < 0){
			fireballCooldown = 0;
		}
		else fireballCooldown--;
		
		if(flinching) {
			flinchCount++;		
			if(flinchCount > 120) {
				flinching = false;
			}
		}			
			
		if(currentAction == ATTACK || currentAction == HIGH_ATTACK || currentAction == LOW_ATTACK) {
			if(animation.hasPlayedOnce()) {
				hi_attack = false;
				attack = false;
				low_attack = false;
			}
		}
		
		if (currentAction == KNOCKBACK){
		
			if (!animation.hasPlayedOnce()){
				knockback = true;
				if (dy == 0) dx = 0;
			}
		}
		
		/*if(dashing) {
			if(animation.hasPlayedOnce()) {
				dashing = false;
			}
		}*/
		
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy e = enemies.get(i);
			
			// sprawdzenie ataku, zadajemy obrazenia wrogowi
			if( currentAction == HIGH_ATTACK ) {
				if(e.intersects(attackRect)) {
					e.hit(damage);
				}
			}
			if( currentAction == ATTACK ) {
				if(e.intersects(attackRect)) {
					e.hit(damage);
				}
			}
			else if( currentAction == LOW_ATTACK ) {
				if(e.intersects(attackRect)) {
					e.hit(damage);
				}
			}					
			// kolizja z wrogiem, na niekorzysc gracza
			if(!e.isDead() && intersects(e)) {
				hit(e.getDamage());
			}
			
		}
		
		// SPRAWDZENIE ANIMACJI
		if(teleporting) {
			if(currentAction != TELEPORTING) {
				setAnimation(TELEPORTING);
			}
		}
		else if(knockback) {
			if(currentAction != KNOCKBACK) {
				setAnimation(KNOCKBACK);
			}
		}
		else if (hi_attack){
			if (currentAction != HIGH_ATTACK){
				setAnimation(HIGH_ATTACK);
				attackRect.y = (int)y - 16;
				if(facing) attackRect.x = (int)x + 10;
				else attackRect.x = (int)x - 35;
			}
		}
		else if (attack){
			if (currentAction != ATTACK){
				setAnimation(ATTACK);
				attackRect.y = (int)y - 16;
				if(facing) attackRect.x = (int)x + 10;
				else attackRect.x = (int)x - 35;
			}
		}
		else if (low_attack){
			if (currentAction != LOW_ATTACK){
				setAnimation(LOW_ATTACK);
				attackRect.y = (int)y;
				if(facing) attackRect.x = (int)x + 10;
				else attackRect.x = (int)x - 35;
			}
		}				
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				setAnimation(JUMPING);
			}
		}
		else if(dy > 0) {
			if(currentAction != FALLING) {
				setAnimation(FALLING);
			}
		}
		else if(dashing && (left || right)) {
			if(currentAction != WALKING) {
				setAnimation(WALKING);
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				setAnimation(WALKING);
			}
		}
		else if (squat){
			if (currentAction != SQUAT) {
				setAnimation(SQUAT);
			}
		}
		else if(currentAction != STAND) {
			setAnimation(STAND);
		}
		
		
		//aktualizacja animacji
		animation.update();
		robeAnimation.update();
		swordAnimation.update();
		
		// ustawienie kierunku
		if(!attack && !hi_attack && !low_attack && !knockback) {
			if(right) facing = true;
			if(left) facing = false;
		}
	}
	
	public void draw(Graphics2D g) {
	
		//rysowanie animacji, najpierw czlowieczek, potem szata, ogolnie zasada taka
		// zeby rysowac warstwami, najpierw te co glebiej, potem te co blizej nas
		
		setMapPosition();
		
		if(flinching && !knockback) {
			if(flinchCount % 10 < 5) return;
		}
		
		if(facing) {
			// jeżeli obrócony w prawo
			g.drawImage( animation.getImage(), 		(int)(x + xmap - width / 2),	(int)(y + ymap - height / 2), null );
			g.drawImage( robeAnimation.getImage(), 	(int)(x + xmap - width / 2), 	(int)(y + ymap - height / 2), null );
			
			if (!fireballShooted){
				if (attack || low_attack || hi_attack){
					double new_y = 0;
					
					if (squat){
						new_y = y+ ymap -(height/2) + 10;
					}
					else {
						new_y = y+ ymap -height/2;
					}
					
					g.drawImage( swordAnimation.getImage(),	(int)(x + xmap- width / 2),	(int)(new_y), null );
				}
			}
		}
		else {
			// jeżeli obrócony w lewo
			g.drawImage( animation.getImage(), 		(int)(x + xmap - width / 2 + width),	(int)(y + ymap - height / 2), -width, height, null);
			g.drawImage( robeAnimation.getImage(),	(int)(x + xmap - width / 2 + width),	(int)(y + ymap - height / 2), -width, height, null);
			if (!fireballShooted){
				if (attack || low_attack || hi_attack){
					double new_y = 0;
					
					if (squat) {
						new_y = y + ymap - (height / 2) + 10;
					}
					else {
						new_y = y + ymap - height / 2;
					}
					
					g.drawImage( swordAnimation.getImage(),	(int)(x + xmap - width / 2 + width), (int)(new_y), -60,	30,	null );
				}
			}
		}
		
		// collision box
		/*Rectangle r = getRectangle();
		r.x += xmap;
		r.y += ymap;
		g.draw(r);*/
	}
}