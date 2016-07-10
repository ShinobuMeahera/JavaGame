package Game.Src.Map;

import Game.Src.Control.Background;
import Game.Src.Control.Keys;
import Game.Src.Map.TileMap.TileMap;
import Game.Src.Objects.Enemies.Enemy;
import Game.Src.Objects.Enemies.EnemyBoss1;
import Game.Src.Objects.Enemies.EnemyGhost;
import Game.Src.Objects.Enemies.EnemySkeleton;
import Game.Src.Objects.Player;
import Game.Src.Objects.Projectiles.EnergyParticle;
import Game.Src.Objects.Projectiles.FireBall;
import Game.Src.Start.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// PLIK GOTOWY


public class Level1 extends GameState{

	private static final String TILESET = "/Game/Src/Assets/tileset3.png";
	private static final String LEVEL = "/Game/Src/Map/Level_1/level1.map";
	private static final String BACKGROUND = "/Game/Src/Assets/tlo.png";
	private static final String HPBAR = "/Game/Src/Assets/hp-bar.png";
	private static final String FIREBAR = "/Game/Src/Assets/fireball-bar.png";
	private static final String DASHBAR = "/Game/Src/Assets/dash-bar.png";
	private static final String HUD = "/Game/Src/Assets/hud.png";

	private Background back;
	private ArrayList<Rectangle> tb;
	private Player player;
	private TileMap tileMap;
	private FireBall fireball;
	private ArrayList<Enemy> enemies;
	private ArrayList<FireBall> fireballs;
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
	private ArrayList<EnergyParticle> energyParticles;
	private BufferedImage hpBar = null;
	private BufferedImage mpBar = null;
	private BufferedImage staBar = null;
	private BufferedImage hudBar = null;
	private EnemyBoss1 eb;
=======
>>>>>>> master:Gra/Level1.java
	
	private int eventCount = 0;
	private boolean eventStart;

	public Level1(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
		back = new Background(BACKGROUND, 0.5);

		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles(TILESET);
		tileMap.loadMap(LEVEL);
		tileMap.setPosition(0, 0);
=======
		back = new Background("tlo.png", 0.2);
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("tileset3.png");
		tileMap.loadMap("level15.map");
		tileMap.setPosition(400, 600);
>>>>>>> master:Gra/Level1.java
		tileMap.setTween(0.05);
		
		//player
		player = new Player(tileMap);
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
		player.setPosition(180, 1115);	
=======
		player.setPosition(400, 250);	
>>>>>>> master:Gra/Level1.java
		fireballs = new ArrayList<FireBall>();
		
		//takie ladne zielone intro
		eventStart = true;
		tb = new ArrayList<Rectangle>();	
		eventStart();
		
		//wrogowie
		enemies = new ArrayList<Enemy>();
		player.init(enemies);
		populateEnemies();
		
		try{
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
			hpBar = ImageIO.read( getClass().getResourceAsStream(HPBAR));
			mpBar = ImageIO.read( getClass().getResourceAsStream(FIREBAR));
			staBar = ImageIO.read( getClass().getResourceAsStream(DASHBAR));
			hudBar = ImageIO.read( getClass().getResourceAsStream(HUD));
=======
			background = ImageIO.read( getClass().getResourceAsStream("background.png"));
>>>>>>> master:Gra/Level1.java
		} catch (Exception e){}
	}
	
	private void populateEnemies() {
		enemies.clear();
		EnemySkeleton es;
		EnemyGhost eg;
		
		es = new EnemySkeleton(tileMap, player);
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
		es.setPosition(660, 1175);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(1035, 1118);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(808, 1118);
=======
		es.setPosition(1425, 50);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(2094, 115);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(600, 150);
>>>>>>> master:Gra/Level1.java
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(340, 200);
		enemies.add(es);
		
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(1764, 1088);
		enemies.add(es);
		
		
		eg = new EnemyGhost(tileMap, player);
		eg.setPosition(1464, 1088);
		enemies.add(eg);

		eg = new EnemyGhost(tileMap, player);
		eg.setPosition(1956, 1088);
		enemies.add(eg);		
=======
>>>>>>> master:Gra/Level1.java
				
		eg = new EnemyGhost(tileMap, player);
		eg.setPosition(1240, 200);
		enemies.add(eg);
	}
	
	public void update() {
		handleInput();
		if(eventStart) eventStart();
		back.setPosition(tileMap.getx(), tileMap.gety());
		player.update();
		tileMap.setPosition( GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety() );
		tileMap.update();
		tileMap.fixBounds();

		//if (player.gety() >= tileMap.getHeight()) gsm.setState(gsm.MENUSTATE); // jeżeli spadniemy to wraca do menu
		
		System.out.println(player.getx() + " " + player.gety());
		for(int i = 0; i < fireballs.size(); i++){
			FireBall f = fireballs.get(i);
			f.update(enemies);
			if (f.isHit() ){
				fireballs.remove(i);
				i--;
			}
		}
		
		
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.shouldRemove()){
				enemies.remove(i);
				i--;
			}
		}
	}
	
	public void handleInput() {
		FireBall fb;
		
		player.setJumping(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		if(Keys.isPressed(Keys.BUTTON3)) player.setAttacking();
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
		
		if(Keys.isPressed(Keys.BUTTON2)){
			if (player.isDashingReady()) player.setDashing();
		}

		if(Keys.isPressed(Keys.BUTTON1)) {
			if ( player.isFireballReady()){
				fb = new FireBall(tileMap, player.getFacing());
				fb.shootFireball(player.getx(), player.gety(), player.getFacing());
				fireballs.add(fb);
								
				player.fireballShooted = true;
				player.setAttacking();
				player.setFireballCooldown(player.maxFireballCooldown);
			}
		}
	}
	
	public void draw(Graphics2D g) {
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
		g.setColor(java.awt.Color.GREEN);
		Rectangle r = new Rectangle(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
		g.fill(r);
=======
		//g.drawImage(background,0,0,GamePanel.WIDTH,  GamePanel.HEIGHT, null);
>>>>>>> master:Gra/Level1.java
		
		back.draw(g);
		player.draw(g);
		for(int i = 0; i < fireballs.size(); i++) { fireballs.get(i).draw(g); }
		for(int i = 0; i < enemies.size(); i++) { enemies.get(i).draw(g); }
		 
		 
		tileMap.draw(g);
<<<<<<< HEAD:Gra/src/Game/Src/Map/Level1.java
		
		g.drawImage(hpBar, (player.getHealth()*2) - 75, 15, null);
		g.drawImage(mpBar, (player.getMana()) - 75, 15+16, null);
		g.drawImage(staBar, (player.getSta()/2) - 75, 15+32, null);
		g.drawImage(hudBar, 0, 13, null);
		
		eb.drawHPBar(g);
		
		/*try {
			String tekst = new String(String.parseString(player.moveSpeed));
			g.drawString(tekst, GamePanel.WIDTH-150, 50);
		} catch (Exception e){}*/
		
=======
>>>>>>> master:Gra/Level1.java
		g.setColor(java.awt.Color.GREEN);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
		
		
	}
	
	// taki event, tylko intro
	private void eventStart() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 60) {
			eventStart = false;
			eventCount = 0;
			tb.clear();
		}
	}
}