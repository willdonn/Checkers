package checkers.board;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import checkers.CheckersView;
import checkers.opponent.AI.CheckersAI;

public class CheckersBoardController implements MouseListener, ActionListener {
	
	private BoardModel model;
	private CheckersBoardPane view;
	private CheckersView parent;
	
	public CheckersBoardController(CheckersBoardPane view, int players, CheckersView parent) {
		this.model = view.getModel();
		model.setPlayers(players);
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
	
	private void handlePieceSelected(CheckersCell cell) {
		
		view.getMoveButton().setEnabled(false);
		CheckersPiece focusPiece = model.getFocusPiece();
		
		if (focusPiece == null) return;
		
		int x = focusPiece.x;
		int y = focusPiece.y;
		if (x - 1 >= 0 && model.isValidDirection(x, y, x - 1)) {
			if (y - 1 >= 0) {
				if (!model.containsPiece(x-1, y-1))
					model.addFocusCell(x-1,y-1);
				else if (y-2 >= 0 && x-2 >= 0 && !model.comparePieceColor(x, y, x-1, y-1) && !model.containsPiece(x-2, y-2))
					model.addFocusCell(x-2, y-2);
			} if (y + 1 <= 7) {
				if (!model.containsPiece(x-1, y+1))
					model.addFocusCell(x-1, y+1);
				else if (y+2 <= 7 && x-2 >= 0 && !model.comparePieceColor(x, y, x-1, y+1) && !model.containsPiece(x-2, y+2))
					model.addFocusCell(x-2, y+2);
			}
		} if (x + 1 <= 7 && model.isValidDirection(x, y, x + 1)) {
			if (y - 1 >= 0) {
				if(!model.containsPiece(x+1, y-1))
					model.addFocusCell(x+1, y-1);
				else if (x+2 <= 7 && y-2 >=0 && !model.comparePieceColor(x, y, x+1, y-1) && !model.containsPiece(x+2, y-2))
					model.addFocusCell(x+2, y-2);
			} if (y + 1 <= 7)
				if (!model.containsPiece(x+1, y+1))
					model.addFocusCell(x+1, y+1);
				else if (x+2 <= 7 && y+2 <= 7 && !model.comparePieceColor(x, y, x+1, y+1) && !model.containsPiece(x+2, y+2))
					model.addFocusCell(x+2, y+2);
		}
		
		view.repaint();
	}
	
	private void handleJumpSequence(CheckersCell cell) {
		CheckersPiece focusPiece = model.getFocusPiece();
		
		if (focusPiece == null) return;
		
		focusPiece.focus = true;
		
		int x = focusPiece.x;
		int y = focusPiece.y;
		if (y-2 >= 0 && x-2 >= 0 && !model.comparePieceColor(x, y, x-1, y-1) && model.containsPiece(x-1, y-1) && !model.containsPiece(x-2, y-2))
				model.addFocusCell(x-2, y-2);
		if (y+2 <= 7 && x-2 >= 0 && !model.comparePieceColor(x, y, x-1, y+1) && model.containsPiece(x-1, y+1) && !model.containsPiece(x-2, y+2))
				model.addFocusCell(x-2, y+2);
		if (x+2 <= 7 && y-2 >=0 && !model.comparePieceColor(x, y, x+1, y-1) && model.containsPiece(x+1, y-1) && !model.containsPiece(x+2, y-2))
				model.addFocusCell(x+2, y-2);
		if (x+2 <= 7 && y+2 <= 7 && !model.comparePieceColor(x, y, x+1, y+1) && model.containsPiece(x+1, y+1) && !model.containsPiece(x+2, y+2))
				model.addFocusCell(x+2, y+2);

		view.repaint();
	}
	
	private void checkGameOver() {
		Color loser = (model.getTurn().equals(Color.RED)) ? Color.BLUE : Color.RED;
		if (model.numberOfPieces(loser) == 0) {
			Color winner = !(model.getTurn().equals(Color.RED)) ? Color.BLUE : Color.RED;
			parent.showGameOver(winner.toString(), loser.toString());
		}
		
	}
	
	private void handleChangeTurn(boolean pieceRemoved, CheckersCell cell) {
		if (pieceRemoved && model.getFocusPiece() != null) {
			model.changeFocus(cell);
			model.changeFocus(cell);
			handleJumpSequence(model.getPieceCell(model.getFocusPiece()));
		}
		
		if (model.getFocusCells().size() == 0) {
			changeTurn();
		} 
		else {
			model.setSwitchFocusEnabled(false);
			view.getEndTurnButton().setEnabled(true);
		}
	}
	
	private void changeTurn() {
		model.changeTurn();
		view.getMoveLabel().setText(model.getTurn() == Color.BLUE ? "BLUE" : "RED");
		view.getMoveLabel().setForeground(model.getTurn());
		view.getEndTurnButton().setEnabled(false);
		
		view.repaint();
	}

	private void movePiece() {
		boolean removedPiece = false;
		if (model.getMoveCell().getPiece() == null && model.isMoveCell() && model.getFocusPiece() != null) {
			CheckersCell cell = model.getMoveCell();
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
			
			model.movePiece();
			handleChangeTurn(removedPiece, cell);
			view.getMoveButton().setEnabled(false);
		} 
	}
	
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
		if (model.isCellFocused(cell) && !model.isMoveCell(cell)) {
			model.setMoveCell(cell);
			view.getMoveButton().setEnabled(true);
		}
		else if (model.isMoveCell()) movePiece();
		
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
		if (e.getSource() == view.getMoveButton()) {
			movePiece();
		}
		else if (e.getSource() == view.getEndTurnButton()) {
			model.clearFocus();
			changeTurn();
		}
		
	}
	
}
