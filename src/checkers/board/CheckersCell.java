package checkers.board;

import java.awt.Color;
import java.awt.Dimension;

public class CheckersCell {
	
	private CheckersCellView view;
	protected boolean cellFocus = false;
	protected boolean isShowAvailableMove;
	protected boolean showFocus;
	protected Color color;
	protected CheckersPiece piece;
	protected int x;
	protected int y;
	protected Dimension size;
	
	public CheckersCell(Color c, boolean isShowAvailableMove, CheckersPiece piece, int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.color = c;
		this.piece= piece;
		this.isShowAvailableMove = isShowAvailableMove;
		this.size = new Dimension(100, 100);
		this.view = new CheckersCellView(this);
	}
	
	public void setPiece(CheckersPiece piece) {
		this.piece = piece;
	}
	
	public CheckersPiece getPiece() {
		return piece;
	}
	
	public void setCellFocus(boolean focus) {
		if (piece == null) {
			this.cellFocus = focus;
		}
	}
	
	public int getCellX() {
		return x;
	}
	
	public int getCellY() {
		return y;
	}

	public CheckersCellView getView() {
		return view;
	}
	
	public boolean containsPiece(CheckersPiece piece) {
		if (piece == null) return false;
		return this.piece.equals(piece);
	}
	
	public void removePiece() {
		piece = null;
		cellFocus = false;
	}

}
