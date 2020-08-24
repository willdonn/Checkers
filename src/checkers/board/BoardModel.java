package checkers.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import checkers.menu.settings.GameSettingsModel;

public class BoardModel {

	private List<ArrayList<CheckersCell>> board;
	private Stack<List<ArrayList<CheckersPiece>>> moveStack;
	private List<ArrayList<CheckersCellView>> view;
	private GameSettingsModel settings;
	private List<CheckersCell> availableMoves;
	private List<CheckersCell> availablePieces;
	private CheckersPiece focusPiece;
	
	private Color turn;
	private boolean switchFocus = true;
	
	
	public BoardModel(List<ArrayList<CheckersCell>> board, List<ArrayList<CheckersCellView>> view, GameSettingsModel settings) {
		this.board = board;
		this.view = view;
		this.settings = settings;
		this.availableMoves = new ArrayList<CheckersCell>();
		this.availablePieces = new ArrayList<CheckersCell>();
		this.moveStack = new Stack<List<ArrayList<CheckersPiece>>>();
		turn = settings.getPlayer1Team();
	}
	
	/* Getters */
	public CheckersPiece getPieceAt(int x, int y) {
		if (containsPiece(x, y))
			return board.get(x).get(y).getPiece();
		return null;
	}
	
	public CheckersCell getCellAt(int x, int y) {
		return board.get(x).get(y);
	}
	
	public CheckersCell getPieceCell(CheckersPiece piece) {
		return getCellAt(piece.x, piece.y);
	}

	public synchronized CheckersCellView getCellView(CheckersCell cell) {
		return view.get(cell.x).get(cell.y);
	}
	
	public CheckersPiece getFocusPiece() {
		return focusPiece;
	}
	
	public synchronized Color getTurn() {
		return turn;
	}
	
	public synchronized List<ArrayList<CheckersCell>> getBoard(){
		return board;
	}
	
	public synchronized List<ArrayList<CheckersCellView>> getBoardView(){
		return view;
	}
	
	public synchronized List<CheckersCell> getAvailableMoves(){
		return availableMoves;
	}
	
	public int getNumberOfPieces(Color color) {
		int i = 0;
		for (List<CheckersCell> row : board) {
			for (CheckersCell cell : row) {
				if (cell.getPiece() != null && cell.getPiece().color.equals(color)) i++;
			}
		}
		return i;
	}
	
	/* Setters */
	public void setSwitchFocusEnabled(boolean switchFocus) {
		this.switchFocus = switchFocus;
	}
	
	/* Piece Movement */
	public void addAvailableMove(int x, int y) {
		CheckersCell cell = board.get(x).get(y);
		cell.setCellFocus(true);
		availableMoves.add(cell);
	}
	
	public void addAvailablePiece(int x, int y) {
		board.get(x).get(y).getPiece().available = true;
		availablePieces.add(board.get(x).get(y));
	}
	
	private void resetAvailableMoves() {
		for (CheckersCell c : availableMoves) {
			c.setCellFocus(false);
		}
		availableMoves.clear();
	}
	
	private void resetAvailablePieces() {
		for (CheckersCell c : availablePieces) {
			c.setAvailablePiece(false);
		}
		availablePieces.clear();
	}
	
	public void refreshAvailablePieces() {
		if (focusPiece != null) focusPiece.available = false;
		resetAvailablePieces();
		for (List<CheckersCell> row : board) {
			for(CheckersCell cell : row) {
				if (cell.getPiece() != null && cell.getPiece().getColor() == turn) {
					setFocus(cell);
					refreshAvailableMoves();
					if (!availableMoves.isEmpty()) addAvailablePiece(focusPiece.x, focusPiece.y);
				}
			}
		}
		clearFocus();
	}
	
	public synchronized void refreshAvailableMoves() {
		resetAvailableMoves();
		
		if (focusPiece == null) return;
		
		int x = focusPiece.x;
		int y = focusPiece.y;
		if (x - 1 >= 0 && isValidDirection(x, y, x - 1)) {
			if (y - 1 >= 0) {
				if (!containsPiece(x-1, y-1))
					addAvailableMove(x-1,y-1);
				else if (y-2 >= 0 && x-2 >= 0 && !comparePieceColor(x, y, x-1, y-1) && !containsPiece(x-2, y-2))
					addAvailableMove(x-2, y-2);
			} if (y + 1 <= 7) {
				if (!containsPiece(x-1, y+1))
					addAvailableMove(x-1, y+1);
				else if (y+2 <= 7 && x-2 >= 0 && !comparePieceColor(x, y, x-1, y+1) && !containsPiece(x-2, y+2))
					addAvailableMove(x-2, y+2);
			}
		} if (x + 1 <= 7 && isValidDirection(x, y, x + 1)) {
			if (y - 1 >= 0) {
				if(!containsPiece(x+1, y-1))
					addAvailableMove(x+1, y-1);
				else if (x+2 <= 7 && y-2 >=0 && !comparePieceColor(x, y, x+1, y-1) && !containsPiece(x+2, y-2))
					addAvailableMove(x+2, y-2);
			} if (y + 1 <= 7)
				if (!containsPiece(x+1, y+1))
					addAvailableMove(x+1, y+1);
				else if (x+2 <= 7 && y+2 <= 7 && !comparePieceColor(x, y, x+1, y+1) && !containsPiece(x+2, y+2))
					addAvailableMove(x+2, y+2);
		}
	}
	
	protected void refreshJumpSequenceMoves() {
		if (focusPiece == null) return;
		
		focusPiece.focus = true;
		
		int x = focusPiece.x;
		int y = focusPiece.y;
		if (y-2 >= 0 && x-2 >= 0 && !comparePieceColor(x, y, x-1, y-1) && containsPiece(x-1, y-1) && !containsPiece(x-2, y-2))
			addAvailableMove(x-2, y-2);
		if (y+2 <= 7 && x-2 >= 0 && !comparePieceColor(x, y, x-1, y+1) && containsPiece(x-1, y+1) && !containsPiece(x-2, y+2))
			addAvailableMove(x-2, y+2);
		if (x+2 <= 7 && y-2 >=0 && !comparePieceColor(x, y, x+1, y-1) && containsPiece(x+1, y-1) && !containsPiece(x+2, y-2))
			addAvailableMove(x+2, y-2);
		if (x+2 <= 7 && y+2 <= 7 && !comparePieceColor(x, y, x+1, y+1) && containsPiece(x+1, y+1) && !containsPiece(x+2, y+2))
			addAvailableMove(x+2, y+2);
	}
	
	public void movePiece(CheckersCell target) {
		CheckersCell originCell = getPieceCell(focusPiece);
		originCell.setPiece(null);
		originCell.setCellFocus(false);
		
		focusPiece.x = target.getCellX();
		focusPiece.y = target.getCellY();
		target.setPiece(focusPiece);
		
		if (!focusPiece.isKing() && focusPiece.color.equals(settings.getPlayer1Team()) && focusPiece.x == 0 || focusPiece.color.equals(settings.getPlayer2Team()) && focusPiece.x == 7) {
			focusPiece.setKing();
			clearFocus();
			focusPiece = null;	
		}
		resetAvailableMoves();
	}
	
	/* Board Interaction */
	public synchronized void changeTurn() {
		resetAvailableMoves();
		if (focusPiece != null)
			focusPiece.focus = false;
		focusPiece = null;
		switchFocus = true;
		turn = turn.equals(Color.RED) ? Color.BLUE : Color.RED;
	}
	
	public void clearFocus() {
		if (focusPiece != null) focusPiece.focus = false;
		focusPiece = null;
		resetAvailableMoves();
	}

	public void setFocus(CheckersCell cell) {
		clearFocus();
		
		cell.setPieceFocus(true);
		focusPiece = cell.getPiece();
	}
	
	public void changeFocus(CheckersCell cell) {
		if (!switchFocus) return;
		
		if (cell.containsPiece(focusPiece) || !turn.equals(cell.getPiece().color)) {
			clearFocus();
			return;
		}
	
		setFocus(cell);
	}
	
	/* Helpers */
	public boolean isCellFocused(CheckersCell cell) {
		return availableMoves.contains(cell);
	}

	public boolean comparePieceColor(int x1, int y1, int x2, int y2) {
		if (containsPiece(x1, y1) && containsPiece(x2, y2)) {
			return getPieceAt(x1, y1).color.equals(getPieceAt(x2, y2).color);
		}
		return false;
	}
	
	public boolean containsPiece(int x, int y) {
		return board.get(x).get(y).getPiece() != null;
	}
	
	public boolean isValidDirection(int x1, int y1, int x2) {
		if (containsPiece(x1, y1)) {
			CheckersPiece piece = getPieceAt(x1, y1);
			if (piece.isKing()) return true;
			if (piece.color.equals(settings.getPlayer2Team()) && x2 - x1 >= 0) return true;
			if (piece.color.equals(settings.getPlayer1Team()) && x2 - x1 <= 0) return true;
		}
		return false;
	}

	public boolean isUndoAvailable() {
		return !moveStack.isEmpty();
	}
	
	public void saveState() {
		if (moveStack.size() > 5) {
			moveStack.remove(0);
		}
		
		List<ArrayList<CheckersPiece>> save = new ArrayList<ArrayList<CheckersPiece>>();
		for (int i = 0; i < 8; i++) {
			ArrayList<CheckersPiece> row = new ArrayList<CheckersPiece>();
			for (int j = 0; j < 8; j++) {
				CheckersCell cell = board.get(i).get(j);
				row.add(cell.getPiece() != null ? new CheckersPiece(cell.getPiece().color, cell.getPiece().x, cell.getPiece().y) : null);
			}
			save.add(row);
		}
		
		moveStack.add(save);
	}

	public void undo() {
		List<ArrayList<CheckersPiece>> save = moveStack.pop();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++) {
				board.get(i).get(j).setPiece(save.get(i).get(j));
			}
		}
		resetAvailableMoves();
	}
	
}
