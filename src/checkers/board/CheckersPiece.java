package checkers.board;

import java.awt.Color;

public class CheckersPiece {
	
	public Color color;
	public boolean focus;
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
	}
	
	public boolean isKing() {
		return type == Type.KING;
	}

	public void setKing() {
		type = Type.KING;
	}

}
