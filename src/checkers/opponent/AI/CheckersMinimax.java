package checkers.opponent.AI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import checkers.board.BoardModel;
import checkers.board.CheckersPiece;

public class CheckersMinimax {
	
	private BoardModel model;
	private Color friendly;
	private Color opponent;
	
	public CheckersMinimax(BoardModel model, Color friendly, Color opponent) {
		this.model = model;
		this.friendly = friendly;
		this.opponent = opponent;
	}
	
	protected int[] findMove() {
		Color[][] boardState = model.getBoard().getState();
		ArrayList<CheckersPiece> friendlyPieces = new ArrayList<CheckersPiece>();
		ArrayList<CheckersPiece> opponentPieces = new ArrayList<CheckersPiece>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardState[i][j] == friendly)
					friendlyPieces.add(new CheckersPiece(friendly, i, j));
				else if (boardState[i][j] == opponent)
					opponentPieces.add(new CheckersPiece(opponent, i, j));
			}
		}
		return minimax(boardState, friendlyPieces, opponentPieces, friendly, 0);
	}
	
	private int[] minimax(Color[][] boardState, ArrayList<CheckersPiece> friendlyPieces, ArrayList<CheckersPiece> opponentPieces, Color turn, int depth) {
		
		int[] maxMove = {0, 0, 0};
		if (depth == 1) return maxMove;
		
		if (turn == friendly) {
			for (CheckersPiece piece : friendlyPieces) {
				List<int[]> moves = generateMoves(boardState, piece);
				int score = 0;
				for (int[] i : moves) {

					CheckersPiece focusPiece = piece.clone();
		
					ArrayList<CheckersPiece> friendlyPiecesCopy = new ArrayList<CheckersPiece>();
					ArrayList<CheckersPiece> opponentPiecesCopy = new ArrayList<CheckersPiece>();
					
					for (CheckersPiece p : friendlyPieces) {
						if (p == piece) {
							friendlyPiecesCopy.add(focusPiece);
						}
						else friendlyPiecesCopy.add(p.clone());
					}
					for (CheckersPiece p : opponentPieces) opponentPiecesCopy.add(p.clone());
					
					Color[][] newState = move(boardState, opponentPiecesCopy, focusPiece, i);
					
					score += heuristic(newState, opponentPiecesCopy, friendlyPiecesCopy);
					
					score += minimax(newState, friendlyPiecesCopy, opponentPiecesCopy, opponent, depth+1)[0];
					if (score > maxMove[0]) {
						maxMove[0] = score;
						maxMove[1] = i[0];
						maxMove[2] = i[1];
					}
				}
			}
		}
		else {
			for (CheckersPiece piece : opponentPieces) {
				List<int[]> moves = generateMoves(boardState, piece);
				int score = 0;
				for (int[] i : moves) {

					CheckersPiece focusPiece = piece.clone();
					
					ArrayList<CheckersPiece> friendlyPiecesCopy = new ArrayList<CheckersPiece>();
					ArrayList<CheckersPiece> opponentPiecesCopy = new ArrayList<CheckersPiece>();
					
					for (CheckersPiece p : opponentPieces) {
						if (p == piece) {
							opponentPiecesCopy.add(focusPiece);
						}
						else opponentPiecesCopy.add(p.clone());
					}
					for (CheckersPiece p : friendlyPieces) friendlyPiecesCopy.add(p.clone());
					Color[][] newState = move(boardState, friendlyPiecesCopy, focusPiece, i);
					
					score += heuristic(newState, opponentPiecesCopy, friendlyPiecesCopy);
					
					if (score < maxMove[0]) {
						maxMove[0] = score;
						maxMove[1] = i[0];
						maxMove[2] = i[1];
					}
				}
			}
		}
		return maxMove;
	}
	
	private int heuristic(Color[][] newState, ArrayList<CheckersPiece> opponentPiecesCopy, ArrayList<CheckersPiece> friendlyPiecesCopy) {
		int score = 0;
		for (CheckersPiece piece : friendlyPiecesCopy) {
			if (piece.isKing()) score += 5;
			else score += 3;
			if (piece.x > 5) score += piece.x;
		}
		for (CheckersPiece piece : opponentPiecesCopy) {
			if (piece.isKing()) score -= 5;
			else score -= 3;
			if (piece.x < 2) score -= (8-piece.x);
		}
		
		return score;
		
	}

	private Color[][] move(Color[][] state, ArrayList<CheckersPiece> newPieces, CheckersPiece piece, int[] coords){
		Color[][] newState = state.clone();
		int x1 = piece.x;
		int y1 = piece.y;
		int x2 = coords[0];
		int y2 = coords[1];
		if (Math.abs(x1-x2) == 2 || (Math.abs(y1-y2) == 2)){
			if (x1 - x2 < 0) {
				if (y1 - y2 < 0) {
					newState[x1+1][y1+1] = null;
					for (CheckersPiece p : newPieces) if (p.x==x1+1 && p.y==y1+1) {
						newPieces.remove(p);
						break;
					}
				}
				else {
					newState[x1+1][y1-1] = null;
					for (CheckersPiece p : newPieces) if (p.x==x1+1 && p.y==y1-1) {
						newPieces.remove(p);
						break;
					}
				}
			} else {
				if (y1 - y2 < 0) {
					newState[x1-1][y1+1] = null;
					for (CheckersPiece p : newPieces) if (p.x==x1-1 && p.y==y1+1) {
						newPieces.remove(p);
						break;
					}
				}
				else {
					newState[x1-1][y1-1] = null;
					for (CheckersPiece p : newPieces) if (p.x==x1-1 && p.y==y1-1) {
						newPieces.remove(p);
						break;
					}
				}
					
			}
				
		}
		piece.x =coords[0];
		piece.y =coords[1];
		
		if (!piece.isKing() && piece.getColor().equals(opponent) && piece.x == 0 
				|| piece.getColor().equals(friendly) && piece.x == 7)
			piece.setKing();
		
		return newState;
	}
	
	
	
	private ArrayList<int[]> generateMoves(Color[][] state, CheckersPiece piece){
		ArrayList<int[]> availableMoves = new ArrayList<int[]>();
		int x = piece.x;
		int y = piece.y;
		if (piece.isKing() && x - 1 >= 0) {
			if (y - 1 >= 0) {
				if (state[x-1][y-1] == null)
					availableMoves.add(new int[] {x-1,y-1});
				else if (y-2 >= 0 && x-2 >= 0 && state[x][y] != state[x-1][y-1] && state[x-2][y-2] == null)
					availableMoves.add(new int[] {x-2, y-2});
			} if (y + 1 <= 7) {
				if (state[x-1][y+1] == null)
					availableMoves.add(new int[] {x-1, y+1});
				else if (y+2 <= 7 && x-2 >= 0 && state[x][y] != state[x-1][y+1] && state[x-2][y+2] == null)
					availableMoves.add(new int[] {x-2, y+2});
			}
		} if (x + 1 <= 7 && isValidDirection(x, x + 1, piece)) {
			if (y - 1 >= 0) {
				if(state[x+1][y-1] == null)
					availableMoves.add(new int[] {x+1, y-1});
				else if (x+2 <= 7 && y-2 >=0 && state[x][y] != state[x+1][y-1] && state[x+2][y-2] == null)
					availableMoves.add(new int[] {x+2, y-2});
			} if (y + 1 <= 7)
				if (state[x+1][y+1] == null)
					availableMoves.add(new int[] {x+1, y+1});
				else if (x+2 <= 7 && y+2 <= 7 && state[x][y] != state[x+1][y+1] && state[x+2][y+2] == null)
					availableMoves.add(new int[] {x+2, y+2});
		}
		return availableMoves;
	}
	
	private boolean isValidDirection(int x1, int x2, CheckersPiece piece) {
		if (piece.isKing() || x2 - x1 >= 0) return true;
		return false;
	}
	
}
