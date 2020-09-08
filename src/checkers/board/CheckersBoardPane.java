package checkers.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import checkers.CheckersView;
import checkers.menu.settings.GameSettingsModel;

public class CheckersBoardPane extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CheckersView parent;
	
	private CheckersBoard board;
	
	private JPanel boardPanel;
	
	private JPanel infoPanel;
	private JLabel moveLabel;
	private JButton menuButton;
	private JButton endTurn;
	private GameSettingsModel settings;

	private JButton undo;
	
	public CheckersBoardPane(CheckersView parent, GameSettingsModel settings) {
		super();
		this.parent = parent;
		this.settings = settings;
		initInfoPanel();
		initBoardPanel();
		initView();
	}
	
	private void initView() {
		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.SOUTH);
	}
	
	private void initBoardPanel() {
		
		boardPanel = new JPanel();
		
		board = new CheckersBoard();
		
		boardPanel.setLayout(new GridLayout(8, 8,5,5));
		boardPanel.setBackground(Color.GRAY);
		
		boolean start = false;
		
		for (int i = 0; i < 8; i++) {
			board.add(new ArrayList<CheckersCell>());
			for (int j = 0; j < 8; j++) {
				if (start) {
					if (i < 3)
						board.get(i).add(new CheckersCell(Color.BLACK, settings.isMoveFocusAssist(), new CheckersPiece(settings.getPlayer2Team(), i, j), i, j));
					else if (i > 4)
						board.get(i).add(new CheckersCell(Color.BLACK, settings.isMoveFocusAssist(), new CheckersPiece(settings.getPlayer1Team(), i, j), i, j));
					else
						board.get(i).add(new CheckersCell(Color.BLACK, settings.isMoveFocusAssist(), null, i, j));
				}
				else board.get(i).add(new CheckersCell(Color.WHITE, settings.isMoveFocusAssist(), null, i, j));
				start = !start;
				boardPanel.add(board.get(i).get(j).getView());
			}
			start = !start;
		}
	}
	
	private void initInfoPanel() {
		infoPanel = new JPanel();
		
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		
		moveLabel = new JLabel();
		moveLabel.setBorder(BorderFactory.createEmptyBorder(0, parent.getWidth()/3, 0, parent.getWidth()/3));
		moveLabel.setFont(new Font("Comic Sans", Font.PLAIN, 24));
		menuButton = new JButton("MAIN MENU");
		endTurn = new JButton("FINISH TURN");
		endTurn.setEnabled(false);
		undo = new JButton("UNDO");
		undo.setEnabled(false);
		infoPanel.add(moveLabel);
		if (settings.isOnePlayer() && settings.isAllowUndo()) infoPanel.add(undo);
		infoPanel.add(endTurn);
		infoPanel.add(menuButton);
	}
	
	
	protected JLabel getMoveLabel() {
		return moveLabel;
	}
	
	protected JButton getMenuButton() {
		return menuButton;
	}
	
	protected JButton getEndTurnButton() {
		return endTurn;
	}
	
	protected JButton getUndoButton() {
		return undo;
	}
	
	protected CheckersBoard getBoard(){
		return board;
	}
	
}