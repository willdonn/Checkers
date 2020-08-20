package checkers.menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import checkers.CheckersView;

public class StartMenuPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel background;
	private JButton startGame1Button;
	private JButton startGame2Button;
	private Image backgroundImage;
	
	private CheckersView parent;
	
	public StartMenuPane(CheckersView parent) {
		super();
		this.parent = parent;
		initView();
	}
	
	private void initView() {
		setLayout(new OverlayLayout(this));
		setSize(parent.getWidth(), parent.getHeight());
		backgroundImage = Toolkit.getDefaultToolkit().createImage("Resources\\CheckersBoard.png");
		background = new JLabel(new ImageIcon(backgroundImage));
		background.setAlignmentX(CENTER_ALIGNMENT);
		background.setAlignmentY(CENTER_ALIGNMENT);
		background.setBounds(getWidth(), getWidth(), getWidth(), getWidth());
		background.setBorder(BorderFactory.createEtchedBorder());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		startGame1Button = new JButton("Start 1 Player Game");
		startGame2Button = new JButton("Start 2 Player Game");
		buttonPanel.add(startGame1Button);
		buttonPanel.add(startGame2Button);
		
		add(background);
		add(buttonPanel);
	}
	
	public JButton getStartGameTwoPlayerButton() {
		return startGame2Button;
	}
	
	public JButton getStartGameOnePlayerButton() {
		return startGame1Button;
	}
	
	public void paintComponent(Graphics2D g){
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this);
	}
	
}
