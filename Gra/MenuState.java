import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// PLIK GOTOWY

public class MenuState extends GameState {
		
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Quit"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Font font2;
	
	public MenuState(GameStateManager gsm) {
		
		super(gsm);
			// titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 10);	
	}
	
	public void init() {}
	
	public void update() {
		// check keys
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("SUPER PROJEKT GEJM", GamePanel.WIDTH/2 - 150, GamePanel.HEIGHT/2 - 50);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Zaciuraj paru pojebuff", 	GamePanel.WIDTH/2 - 100, 	GamePanel.HEIGHT/2);
		g.drawString("Wykurwiaj w sina dal", 	GamePanel.WIDTH/2 - 100,	GamePanel.HEIGHT/2 + 30);
		
		// draw point
		if(currentChoice == 0) g.fillRect(		GamePanel.WIDTH/2 - 120, GamePanel.HEIGHT/2 - 5, 		5, 5);
		else if(currentChoice == 1) g.fillRect(	GamePanel.WIDTH/2 - 120, GamePanel.HEIGHT/2 -5 + 30, 	5, 5);
		
		// other
		g.setFont(font2);
		g.drawString("Dwa tysiunce szesnascie, kopirajt baj Pszemeg i Anyszka", 10, 300);
		
	}
	
	private void select() {
		if(currentChoice == 0) { gsm.setState(GameStateManager.LEVEL1);	}
		else if(currentChoice == 1) { System.exit(0); }
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) { currentChoice++; }
		}
	}
}