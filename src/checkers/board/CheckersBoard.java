package checkers.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class CheckersBoard extends ArrayList<ArrayList<CheckersCell>>{	
	
	private static final long serialVersionUID = 1L;
	
	public CheckersBoard() {
		super();
	}
	
	public Color[][] getState() {
		Color[][] board = new Color[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = (this.get(i).get(j).piece != null) ? this.get(i).get(j).piece.color : null;
			}
		}
		return board;
	}
}
