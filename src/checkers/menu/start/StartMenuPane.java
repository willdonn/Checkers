package checkers.menu.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import checkers.CheckersView;

public class StartMenuPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel buttonPanel;
	private JButton startGame1Button;
	private JButton startGame2Button;
	
	private CheckersView parent;
	
	public StartMenuPane(CheckersView parent) {
		super();
		this.parent = parent;
		initView();
	}
	
	private void initView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(parent.getWidth(), parent.getHeight());
		setBackground(Color.GRAY);
		
		JLabel title = new JLabel("Willdonn's Checkers");
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setPreferredSize(new Dimension(500,500));
		title.setFont(new Font("Times New Roman", Font.PLAIN, 64));
		
		createButtonPanel();
		
		add(title);
		add(buttonPanel);
	}
	
	private void createButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.GRAY);
		startGame1Button = new JButton("Start 1 Player Game");
		startGame2Button = new JButton("Start 2 Player Game");
		buttonPanel.add(startGame1Button);
		buttonPanel.add(startGame2Button);
	}
	
	public JButton getStartGameTwoPlayerButton() {
		return startGame2Button;
	}
	
	public JButton getStartGameOnePlayerButton() {
		return startGame1Button;
	}
	
}
