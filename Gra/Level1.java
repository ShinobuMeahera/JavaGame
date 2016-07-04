import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// PLIK GOTOWY

public class Level1 extends GameState{

	private Background back;
	private ArrayList<Rectangle> tb;
	private Player player;
	private TileMap tileMap;
	private FireBall fireball;
	private ArrayList<Enemy> enemies;
	private ArrayList<FireBall> fireballs;
	private ArrayList<EnergyParticle> energyParticles;
	private BufferedImage hpBar = null;
	private BufferedImage mpBar = null;
	private BufferedImage staBar = null;
	private BufferedImage hudBar = null;
	private EnemyBoss1 eb;
	
	private int eventCount = 0;
	private boolean eventStart;
	private boolean eventDead;
	private boolean blockInput = false;

	public Level1(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		back = new Background("tlo.png", 0.5);

		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("tileset3.png");
		tileMap.loadMap("Mapy/level15.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(0.05);
		
		//player
		player = new Player(tileMap);
		player.setPosition(180, 1115);	
		fireballs = new ArrayList<FireBall>();
		
		//takie ladne zielone intro
		eventStart = true;
		tb = new ArrayList<Rectangle>();	
		eventStart();
		
		//wrogowie
		enemies = new ArrayList<Enemy>();
		
		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();
		
		// init player
		player.init(enemies, energyParticles);
		populateEnemies();
		
		try{
			hpBar = ImageIO.read( getClass().getResourceAsStream("hp-bar.png"));
			mpBar = ImageIO.read( getClass().getResourceAsStream("fireball-bar.png"));
			staBar = ImageIO.read( getClass().getResourceAsStream("dash-bar.png"));
			hudBar = ImageIO.read( getClass().getResourceAsStream("hud.png"));
		} catch (Exception e){}
	}
	
	private void populateEnemies() {
		enemies.clear();
		EnemySkeleton es;
		EnemyGhost eg;
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(660, 1175);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(1035, 1118);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(808, 1118);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(340, 200);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(1764, 1088);
		enemies.add(es);
		
		
		eg = new EnemyGhost(tileMap, player);
		eg.setPosition(1464, 1088);
		enemies.add(eg);

		eg = new EnemyGhost(tileMap, player);
		eg.setPosition(1956, 1088);
		enemies.add(eg);		
				
		eg = new EnemyGhost(tileMap, player);
		eg.setPosition(1720, 2250);
		enemies.add(eg);
		
		
		
		eb = new EnemyBoss1(tileMap, player);
		eb.setPosition(2550, 1700);
		enemies.add(eb);
	}
	
	public void update() {
		handleInput();
		
		if(player.getHealth() == 0 || player.gety() > tileMap.getHeight()) {
			eventDead = blockInput = true;
		}
		
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		
		back.setPosition(tileMap.getx(), tileMap.gety());
		
		player.update();
		if (player.getx() > 2240 && player.gety() > 1530 && player.gety() < 1870){
			tileMap.setPosition(
				GamePanel.WIDTH / 2 - player.getx()-200,
				GamePanel.HEIGHT / 2 - player.gety()+100); // TUTAJ USTAWIAMY WIDOK
		}
		else tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx()-(50*player.setViewLeftRight()),
			GamePanel.HEIGHT / 2 - player.gety()-(100*player.setViewDown())); // TUTAJ USTAWIAMY WIDOK
			
		tileMap.update();
		tileMap.fixBounds();
		
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
		g.setColor(java.awt.Color.GREEN);
		Rectangle r = new Rectangle(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
		g.fill(r);
		
		back.draw(g);
		
		player.draw(g);
		for(int i = 0; i < fireballs.size(); i++) { fireballs.get(i).draw(g); }
		for(int i = 0; i < enemies.size(); i++) { enemies.get(i).draw(g); }
		 
		tileMap.draw(g);
		
		g.drawImage(hpBar, (player.getHealth()*2) - 75, 15, null);
		g.drawImage(mpBar, (player.getMana()) - 75, 15+16, null);
		g.drawImage(staBar, (player.getSta()/2) - 75, 15+32, null);
		g.drawImage(hudBar, 0, 13, null);
		
		eb.drawHPBar(g);
		
		/*try {
			String tekst = new String(String.parseString(player.moveSpeed));
			g.drawString(tekst, GamePanel.WIDTH-150, 50);
		} catch (Exception e){}*/
		
		g.setColor(java.awt.Color.GREEN);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
		
		
	}
	
	// taki event, tylko intro
	
	private void reset() {
		player.reset();
		player.setPosition(400, 600);
		populateEnemies();
		blockInput = true;
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
	}
	
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

	private void eventDead() {
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getHealth() == 0) {
				gsm.setState(GameStateManager.MENUSTATE);
			}
			else {
				eventDead = blockInput = false;
				eventCount = 0;
				reset();
			}
		}
	}
}