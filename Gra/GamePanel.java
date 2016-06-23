import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;

// PLIK GOTOWY

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
		
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	// rozmiary okna * skala, powiększenie w grze
	public static final int SCALE = 2;
	public static final int WIDTH = (int) dim.getWidth()/ SCALE;
	public static final int HEIGHT = (int) dim.getHeight()/ SCALE;
	
	
	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	// GSM, bedzie sluzyc do wczytywania poziomow & shit
	private GameStateManager gsm;
		
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
		
		image = new BufferedImage((int)WIDTH, (int)HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
				
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
		
		g2.drawImage(image, 0, 0,(int) (WIDTH * SCALE), (int)(HEIGHT  * SCALE), null);
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