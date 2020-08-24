package checkers.gameover;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPane extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
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
		setLayout(new GridLayout(2,1));
		setBackground(Color.GRAY);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BorderLayout());
		titlePanel.setOpaque(false);
		gameOver = new JLabel("Game Over! - "+this.winner+" WINS! "+this.loser+" is A LOSER!");
		gameOver.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		gameOver.setHorizontalAlignment(JLabel.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		mainMenu = new JButton("Main Menu");
		mainMenu.setAlignmentX(CENTER_ALIGNMENT);
		
		titlePanel.add(gameOver, BorderLayout.CENTER);
		buttonPanel.add(mainMenu);
		add(titlePanel);
		add(buttonPanel);
		
	}
	
	public JButton getMainMenuButton() {
		return mainMenu;
	}

}
