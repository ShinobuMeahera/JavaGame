package Game.Src.Start;

import Game.Src.Map.GameStateManager;
import Game.Src.Control.Keys;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.Transparency;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
		
	public static final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static double SCALE = 2.00;
	public static final int SCREEN_WIDTH = (int) (dim.getWidth());
	public static final int SCREEN_HEIGHT = (int) (dim.getHeight());
	public static int WIDTH = (int) (SCREEN_WIDTH/SCALE);
	public static int HEIGHT = (int) (SCREEN_HEIGHT/SCALE);


	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private Graphics2D g;
	private GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	private GraphicsDevice device = env.getDefaultScreenDevice();
	private GraphicsConfiguration config = device.getDefaultConfiguration();
	private BufferedImage buffyNormal;
	private BufferedImage buffyBoss;
	private BufferedImage readyImage;

	private GameStateManager gsm;
		
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);

			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		buffyNormal = config.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		readyImage = buffyNormal;
		g = (Graphics2D) readyImage.getGraphics();
				
		running = true;
		
		gsm = new GameStateManager();
		
	}
	
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		// głowna petla gry
		while(running) {
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;

			thread.setName("Andromalius");

			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void update() {
		gsm.update();
		Keys.update();

	}

	private void draw() {
		gsm.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();

		g2.drawImage(readyImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
		g2.dispose();
		
	}
	
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		
		Keys.keySet(key.getKeyCode(), true);
	}
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

}