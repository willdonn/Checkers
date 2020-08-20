package checkers.gameover;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPane extends JPanel {
	
	private JButton mainMenu;
	private JLabel gameOver;
	private String loser;
	private String winner;
	
	public GameOverPane(String winner, String loser) {
		super();
		this.winner = winner;
		this.loser = loser;
		initView();
	}
	
	private void initView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		mainMenu = new JButton("Main Menu");
		gameOver = new JLabel("Game Over! - "+this.winner+" WINS! "+this.loser+" is A LOSER!");
		
		add(gameOver);
		add(mainMenu);
		
	}
	
	public JButton getMainMenuButton() {
		return mainMenu;
	}

}
