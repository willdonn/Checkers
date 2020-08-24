package checkers.board;

import java.awt.Color;

public class CheckersPiece {
	
	protected Color color;
	protected boolean focus;
	protected boolean available;
	protected int x;
	protected int y;
	
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

}
