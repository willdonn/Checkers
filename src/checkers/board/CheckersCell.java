package checkers.board;

import java.awt.Color;
import java.awt.Dimension;

public class CheckersCell {
	
	protected boolean cellFocus = false;
	protected boolean moveFocus = false;
	protected Color color;
	protected CheckersPiece piece;
	protected int x;
	protected int y;
	protected Dimension size;
	
	public CheckersCell(Color c, CheckersPiece piece, int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.color = c;
		this.piece= piece;
		this.size = new Dimension(100, 100);
	}
	
	public void setPiece(CheckersPiece piece) {
		this.piece = piece;
	}
	
	public CheckersPiece getPiece() {
		return piece;
	}
	
	public void setPieceFocus(boolean focus) {
		if (piece != null) {
			piece.focus = focus;
		}
	}
	
	public void setCellFocus(boolean focus) {
		if (piece == null) {
			this.cellFocus = focus;
		}
	}
	
	public void setMoveFocus(boolean focus) {
		this.moveFocus = focus;
	}
	
	public boolean getMoveFocus() {
		return moveFocus;
	}
	
	public int getCellX() {
		return x;
	}
	
	public int getCellY() {
		return y;
	}

	
	public boolean containsPiece(CheckersPiece piece) {
		return this.piece.equals(piece);
	}
	
	public void removePiece() {
		piece = null;
		cellFocus = false;
	}
}
