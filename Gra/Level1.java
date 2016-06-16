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
	
	private BufferedImage background = null;
	private ArrayList<Enemy> enemies;
	
	private int eventCount = 0;
	private boolean eventStart;

	public Level1(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	public void init() {
		back = new Background("tlo.png", 0);
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("tileset3.png");
		tileMap.loadMap("level12.map");
		tileMap.setPosition(400, 50);
		tileMap.setBounds( tileMap.getWidth() - 1 * tileMap.getTileSize(), tileMap.getHeight() - 2 * tileMap.getTileSize(),	0, 0);
		tileMap.setTween(0.05);
		
		//player
		player = new Player(tileMap);
		player.setPosition(400, 150);	
		
		//takie ladne zielone intro
		eventStart = true;
		tb = new ArrayList<Rectangle>();	
		eventStart();
		
		//wrogowie
		enemies = new ArrayList<Enemy>();
		player.init(enemies);
		populateEnemies();
		
		try{
			background = ImageIO.read( getClass().getResourceAsStream("background.png"));
		} catch (Exception e){}
	}
	
	private void populateEnemies() {
		enemies.clear();
		EnemySkeleton es;
		EnemyGhost eg;
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(1425, 50);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(2500, 100);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(340, 200);
		enemies.add(es);
		
		es = new EnemySkeleton(tileMap, player);
		es.setPosition(2000, 100);
		enemies.add(es);
		
		eg = new EnemyGhost(tileMap, player);
		eg.setPosition(2300, 200);
		enemies.add(eg);
	}
	
	public void update() {
		handleInput();
		if(eventStart) eventStart();
		player.update();
		back.setPosition(tileMap.getx(), tileMap.gety());
		tileMap.setPosition( GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety() );
		tileMap.update();
		tileMap.fixBounds();

		if (player.gety() >= tileMap.getHeight()) gsm.setState(gsm.MENUSTATE); // jeżeli spadniemy to wraca do menu
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
			}
		}
	}
	
	public void handleInput() {
		player.setJumping(Keys.keyState[Keys.UP]);
		player.setLeft(Keys.keyState[Keys.LEFT]);
		player.setRight(Keys.keyState[Keys.RIGHT]);
		player.setDown(Keys.keyState[Keys.DOWN]);
		if(Keys.isPressed(Keys.ENTER)) player.setAttacking();
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(background,0,0,GamePanel.WIDTH,  GamePanel.HEIGHT, null);
		
		back.draw(g);
		player.draw(g);
		
		for(int i = 0; i < enemies.size(); i++) { enemies.get(i).draw(g); }
		 
		 
		 
		
		tileMap.draw(g);
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