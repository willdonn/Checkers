package checkers.board;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import checkers.CheckersView;
import checkers.opponent.AI.CheckersAI;

public class CheckersBoardController implements MouseListener, ActionListener {
	
	private BoardModel model;
	private CheckersBoardPane view;
	private CheckersView parent;
	private CheckersAI opponent;
	
	public CheckersBoardController(CheckersBoardPane view, int players, CheckersView parent) {
		this.model = view.getModel();
		model.setPlayers(players);
		if (players == 1) {
			opponent = new CheckersAI(model);
			Thread t = new Thread(opponent);
			t.start();
		}
		this.parent = parent;
		this.view = view;
		mapActions();
	}
	
	private void mapActions() {
		view.getMoveButton().addActionListener(this);
		view.getEndTurnButton().addActionListener(this);
		
		for (List<CheckersCellView> row : model.getBoardView()) {
			for (CheckersCellView cell : row) {
				cell.addMouseListener(this);
			}
		}
	}
	
	/* Game transaction handlers */
	private void handlePieceSelected(CheckersCell cell) {
		
		view.getMoveButton().setEnabled(false);
		
		model.generateAvailableMoves();
		
		view.repaint();
	}
	
	private void handleJumpSequence() {
		CheckersPiece focusPiece = model.getFocusPiece();
		
		if (focusPiece == null) return;

		model.generateJumpSequenceMoves();

		view.repaint();
	}
	
	private void handleChangeTurn(boolean pieceRemoved, CheckersCell cell) {
		if (pieceRemoved && model.getFocusPiece() != null) {
			handleJumpSequence();
		}
		
		if (model.getAvailableMoves().size() == 0) {
			changeTurn();
		} 
		else if (model.getNumPlayers() == 1) {
			synchronized(opponent) {
				opponent.notify();
			}
		}
		else {
			model.setSwitchFocusEnabled(false);
			view.getEndTurnButton().setEnabled(true);
		}
	}
	
	
	/* Game State Modifiers */
	private void changeTurn() {
		model.changeTurn();
		view.getMoveLabel().setText(model.getTurn() == Color.BLUE ? "BLUE" : "RED");
		view.getMoveLabel().setForeground(model.getTurn());
		view.getEndTurnButton().setEnabled(false);
		
		view.repaint();
		
		if (model.getNumPlayers() == 1 && model.getTurn() == Color.BLUE) {
			synchronized(opponent) {
				opponent.notify();
			}
		}
	}

	private void movePiece(CheckersCell cell) {
		boolean removedPiece = false;
		int x1 = model.getFocusPiece().x;
		int y1 = model.getFocusPiece().y;
		int x2 = cell.getCellX();
		int y2 = cell.getCellY();
		if (Math.abs(x1-x2) == 2 || (Math.abs(y1-y2) == 2)){
			removedPiece = true;
			if (x1 - x2 < 0) {
				if (y1 - y2 < 0)
					model.getCellAt(x1+1, y1+1).removePiece();
				else
					model.getCellAt(x1+1, y1-1).removePiece();
			} else {
				if (y1 - y2 < 0)
					model.getCellAt(x1-1, y1+1).removePiece();
				else
					model.getCellAt(x1-1, y1-1).removePiece();
			}
			
			checkGameOver();
				
			}
			
			model.movePiece(cell);
			handleChangeTurn(removedPiece, cell);
			view.getMoveButton().setEnabled(false);
	}
	
	private void checkGameOver() {
		Color loser = (model.getTurn().equals(Color.RED)) ? Color.BLUE : Color.RED;
		if (model.getNumberOfPieces(loser) == 0) {
			Color winner = !(model.getTurn().equals(Color.RED)) ? Color.BLUE : Color.RED;
			parent.showGameOver(winner.toString(), loser.toString());
		}
		
	}
	
	/* Event handlers */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		CheckersCell cell = null;
		if (e.getComponent() instanceof CheckersCellView) cell = ((CheckersCellView) e.getComponent()).getModel();
		
		if (cell == null) return;
		
		if (cell.getPiece() != null) {
			model.changeFocus(cell);
			handlePieceSelected(cell);
		}
		else if (model.getFocusPiece() != null && model.getAvailableMoves().contains(cell)) movePiece(cell);
		
		view.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == view.getEndTurnButton()) {
			model.clearFocus();
			changeTurn();
		}
		
	}
	
}
