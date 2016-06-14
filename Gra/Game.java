import javax.swing.JFrame;

// PLIK GOTOWY, startowy

public class Game {
	public static void main(String[] args) {
		JFrame window = new JFrame("Swing 0.2.1.2 - gotowe kolizje i przewijanie");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}