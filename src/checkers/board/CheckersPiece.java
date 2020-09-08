package checkers.board;

import java.awt.Color;

public class CheckersPiece {
	
	protected Color color;
	protected boolean focus;
	protected boolean available;
	public int x;
	public int y;
	
	public enum Type { 
			REGULAR,
			KING
	};
	
	public Type type;
	
	public CheckersPiece(Color color, int x, int y) {
		this.color = color;
		this.focus = false;
		this.x = x;
		this.y = y;
		this.type = Type.REGULAR;
		this.available = false;
	}
	
	public boolean isKing() {
		return type == Type.KING;
	}

	public void setKing() {
		type = Type.KING;
	}

	public Color getColor() {
		return color;
	}
	
	public void setAvailablePiece(boolean available) {
		this.available = available;
	}
	
	public void setPieceFocus(boolean focus) {
		this.focus = focus;
	}
	
	public CheckersPiece clone() {
		CheckersPiece newPiece = new CheckersPiece(this.color, this.x, this.y);
		newPiece.type = this.type;
		return newPiece;
	}

}
