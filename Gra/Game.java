import javax.swing.JFrame;

// PLIK GOTOWY, startowy

public class Game {
	public static void main(String[] args) {
		JFrame window = new JFrame("Swing v0.5.6.4 - MOZNA ODDAC");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}