package checkers.board;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Refreshable;

import checkers.CheckersView;
import checkers.menu.settings.GameSettingsModel;
import checkers.opponent.AI.CheckersAI;

public class CheckersBoardController implements MouseListener, ActionListener {
	
	private BoardModel model;
	private GameSettingsModel settings;
	private CheckersBoardPane view;
	private CheckersView parent;
	private CheckersAI opponent;
	
	public CheckersBoardController(CheckersBoardPane view, GameSettingsModel settings, CheckersView parent) {
		this.model = new BoardModel(view.getBoard(), settings);
		this.settings = settings;
		view.getMoveLabel().setForeground(settings.getPlayer1Team());
		view.getMoveLabel().setText(settings.getPlayer1Team() == Color.RED ? "RED" : "BLUE");
		if (settings.isPieceFocusAssist()) model.refreshAvailablePieces();
		if (settings.isOnePlayer()) {
			opponent = new CheckersAI(model, settings.getPlayer2Team());
			Thread oppponentThread = new Thread(opponent);
			oppponentThread.start();
		}
		this.parent = parent;
		this.view = view;
		mapActions();
	}
	
	private void mapActions() {
		view.getMenuButton().addActionListener(this);
		view.getEndTurnButton().addActionListener(this);
		if (settings.isOnePlayer()) view.getUndoButton().addActionListener(this);
		for (List<CheckersCell> row : model.getBoard()) {
			for (CheckersCell cell : row) {
				cell.getView().addMouseListener(this);
			}
		}
	}
	
	/* Game transaction handlers */
	private void handlePieceSelected(CheckersCell cell) {
		
		model.refreshAvailableMoves();
		
		view.repaint();
	}
	
	private void handleJumpSequence() {
		CheckersPiece focusPiece = model.getFocusPiece();
		
		if (focusPiece == null) return;
		model.refreshJumpSequenceMoves();

		view.repaint();
	}
	
	private void handleChangeTurn(boolean pieceRemoved, CheckersCell cell) {
		if (pieceRemoved && model.getFocusPiece() != null) {
			view.getEndTurnButton().setEnabled(true);
			handleJumpSequence();
		}
		
		if (model.getAvailableMoves().size() == 0) {
			changeTurn();
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
		if (settings.isOnePlayer()) view.getUndoButton().setEnabled(model.getTurn() == settings.getPlayer1Team() && model.isUndoAvailable());
		
		if (settings.isPieceFocusAssist()) model.refreshAvailablePieces();
		
		view.repaint();
		
		if (!checkGameOver() && settings.isOnePlayer() && model.getTurn().equals(settings.getPlayer2Team())) {
			synchronized(opponent) {
				opponent.notify();
			}
		}
	}

	private void movePiece(CheckersCell cell) {
		if (settings.isOnePlayer() && model.getTurn() == settings.getPlayer1Team()) model.saveState();
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
				
		}

		model.movePiece(cell);
		handleChangeTurn(removedPiece, cell);
	}
	
	private boolean checkGameOver() {
		
		Color team = model.getTurn();
		
		boolean moveAvailable = false;
		for (List<CheckersCell> row : model.getBoard()) {
			for (CheckersCell cell : row) {
				if (moveAvailable) break;
				if (cell.getPiece() != null && cell.getPiece().color == team) {
					model.setFocus(cell);
					model.refreshAvailableMoves();
					if (model.getAvailableMoves() != null && !model.getAvailableMoves().isEmpty()) {
						moveAvailable = true;
						break;
					}
				}
			}
		}
				
		if (model.getNumberOfPieces(team) == 0 || !moveAvailable) {
			String loser = (model.getTurn().equals(Color.RED)) ? "RED" : "BLUE";
			String winner = !(model.getTurn().equals(Color.RED)) ? "RED" : "BLUE";
			if (settings.isOnePlayer())
				opponent.stop();

			parent.showGameOver(winner, loser);
			return true;
		}
		model.clearFocus();
		return false;
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
		
		if (cell.getPiece() != null && cell.getPiece().color == model.getTurn()) {
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
		else if (e.getSource() == view.getMenuButton()) {
			if (settings.isOnePlayer()) {
				opponent.stop();
			}
			parent.showStartMenu();
		}
		
		else if (e.getSource() == view.getUndoButton()) {
			model.undo();
			if (settings.isPieceFocusAssist()) model.refreshAvailablePieces();
			view.getUndoButton().setEnabled(model.isUndoAvailable());
			view.repaint();
		}
		
	}
	
}
