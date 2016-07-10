package Game.Src.Start;

import javax.swing.JFrame;

public class Game {
	public static void main(String[] args) {
		JFrame window = new JFrame("ANDROMALIUS");
		window.add(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.setUndecorated(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}