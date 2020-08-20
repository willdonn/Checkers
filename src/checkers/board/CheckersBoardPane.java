package checkers.board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import checkers.CheckersView;

public class CheckersBoardPane extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CheckersView parent;
	
	private List<ArrayList<CheckersCellView>> views;
	private List<ArrayList<CheckersCell>> cells;

	private BoardModel boardModel;
	
	private JPanel boardPanel;
	
	private JPanel infoPanel;
	private JLabel moveLabel;
	private JButton moveButton;
	private JButton endTurn;
	
	public CheckersBoardPane(CheckersView parent) {
		super();
		this.parent = parent;
		initInfoPanel();
		initBoardPanel();
		initView();
		initModel();
	}
	
	private void initView() {
		setLayout(new BorderLayout());
		add(boardPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.SOUTH);
	}
	
	private void initBoardPanel() {
		
		boardPanel = new JPanel();
		
		cells = new ArrayList<ArrayList<CheckersCell>>();
		views = new ArrayList<ArrayList<CheckersCellView>>();
		
		boardPanel.setLayout(new GridLayout(8, 8,5,5));
		boardPanel.setBackground(Color.GRAY);
		
		boolean start = false;
		
		for (int i = 0; i < 8; i++) {
			cells.add(new ArrayList<CheckersCell>());
			for (int j = 0; j < 8; j++) {
				if (start) {
					if (i < 3)
						cells.get(i).add(new CheckersCell(Color.BLACK, new CheckersPiece(Color.BLUE, i, j), i, j));
					else if (i > 4)
						cells.get(i).add(new CheckersCell(Color.BLACK, new CheckersPiece(Color.RED, i, j), i, j));
					else
						cells.get(i).add(new CheckersCell(Color.BLACK, null, i, j));
				}
				else cells.get(i).add(new CheckersCell(Color.WHITE, null, i, j));
				start = !start;
			}
			start = !start;
		}
		
		for (List<CheckersCell> row : cells) {
			ArrayList<CheckersCellView> viewRow = new ArrayList<CheckersCellView>();
			for (CheckersCell cell : row) {
				CheckersCellView view = new CheckersCellView(cell);
				viewRow.add(view);
				boardPanel.add(view);
			}
			views.add(viewRow);
		}
		
	}
	
	private void initInfoPanel() {
		infoPanel = new JPanel();
		
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		
		moveLabel = new JLabel("RED");
		moveLabel.setBorder(BorderFactory.createEmptyBorder(0, parent.getWidth()/3, 0, parent.getWidth()/3));
		moveLabel.setForeground(Color.RED);
		moveButton = new JButton("MOVE");
		moveButton.setEnabled(false);
		endTurn = new JButton("FINISH");
		endTurn.setEnabled(false);
		infoPanel.add(moveLabel);
		infoPanel.add(moveButton);
		infoPanel.add(endTurn);
	}
	
	
	public JLabel getMoveLabel() {
		return moveLabel;
	}
	
	public JButton getMoveButton() {
		return moveButton;
	}
	
	public JButton getEndTurnButton() {
		return endTurn;
	}
	
	
	private void initModel() {
		boardModel = new BoardModel(cells, views);
	}
	
	public BoardModel getModel() {
		return boardModel;
	}
	
}