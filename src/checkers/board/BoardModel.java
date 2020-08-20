package checkers.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import checkers.board.CheckersPiece.Type;

public class BoardModel {

	private List<ArrayList<CheckersCell>> board;
	private List<ArrayList<CheckersCellView>> view;
	private List<CheckersCell> availableMoves;
	private List<CheckersCell> availablePieces;
	private CheckersPiece focusPiece;
	
	private Color turn = Color.RED;
	private boolean switchFocus = true;
	private int players;
	
	public BoardModel(List<ArrayList<CheckersCell>> board, List<ArrayList<CheckersCellView>> view) {
		this.board = board;
		this.view = view;
		this.availableMoves = new ArrayList<CheckersCell>();
	}
	
	private void resetAvailableMoves() {
		for (CheckersCell c : availableMoves) {
			c.setCellFocus(false);
		}
		availableMoves.clear();
		
	}
	
	public boolean containsPiece(int x, int y) {
		return board.get(x).get(y).getPiece() != null;
	}
	
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
	
	public void addAvailableMove(int x, int y) {
		CheckersCell cell = board.get(x).get(y);
		cell.setCellFocus(true);
		availableMoves.add(cell);
	}
	
	public List<ArrayList<CheckersCell>> getBoard(){
		return board;
	}
	
	public List<ArrayList<CheckersCellView>> getBoardView(){
		return view;
	}
	
	public List<CheckersCell> getAvailableMoves(){
		return availableMoves;
	}
	
	public CheckersPiece getFocusPiece() {
		return focusPiece;
	}
	
	public Color getTurn() {
		return turn;
	}
	
	public void changeTurn() {
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
	
	public void movePiece(CheckersCell cell) {
		CheckersCell originCell = getPieceCell(focusPiece);
		originCell.setPiece(null);
		originCell.setCellFocus(false);
		
		focusPiece.x = cell.getCellX();
		focusPiece.y = cell.getCellY();
		cell.setPiece(focusPiece);
		
		if (focusPiece.type == CheckersPiece.Type.REGULAR && focusPiece.color.equals(Color.RED) && focusPiece.x == 0 || focusPiece.color.equals(Color.BLUE) && focusPiece.x == 7) {
			focusPiece.type = CheckersPiece.Type.KING;
			clearFocus();
			focusPiece = null;
			
		}

		resetAvailableMoves();
		
	}
	
	public boolean isCellFocused(CheckersCell cell) {
		return availableMoves.contains(cell);
	}

	public boolean comparePieceColor(int x1, int y1, int x2, int y2) {
		if (containsPiece(x1, y1) && containsPiece(x2, y2)) {
			return getPieceAt(x1, y1).color.equals(getPieceAt(x2, y2).color);
		}
		return false;
	}
	
	public boolean isValidDirection(int x1, int y1, int x2) {
		if (containsPiece(x1, y1)) {
			CheckersPiece piece = getPieceAt(x1, y1);
			if (piece.isKing()) return true;
			if (piece.color.equals(Color.BLUE) && x2 - x1 >= 0) return true;
			if (piece.color.equals(Color.RED) && x2 - x1 <= 0) return true;
		}
		return false;
	}
	
	public int numberOfPieces(Color color) {
		int i = 0;
		for (List<CheckersCell> row : board) {
			for (CheckersCell cell : row) {
				if (cell.getPiece() != null && cell.getPiece().color.equals(color)) i++;
			}
		}
		return i;
	}

	public void setSwitchFocusEnabled(boolean switchFocus) {
		this.switchFocus = switchFocus;
	}

	public void setPlayers(int players) {
		this.players = players;
	}
	
	public int players() {
		return players;
	}
	
}
