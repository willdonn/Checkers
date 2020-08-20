package checkers.opponent.AI;

import java.awt.Color;
import java.util.List;
import java.util.Queue;

import checkers.board.BoardModel;
import checkers.board.CheckersBoardController;
import checkers.board.CheckersBoardPane;
import checkers.board.CheckersCellView;

public class CheckersAI implements Runnable{
//	private Color color;
//	private Queue q;
//	
//	public CheckersAI(Color color, Queue q) {
//		this.color = color;
//		this.q = q;
//	}
//	
//	private void movePlayerTwo(BoardModel model) throws InterruptedException {
//		for (int i = 0; i < model.getBoard().size(); i++) {
//			for (CheckersCellView cell : model.getBoard().get(i)) {
//				if (cell.getPiece() == null || cell.getPiece().color == Color.RED) continue;
//				model.setFocus(cell);
//				handleJumpSequence(cell);
//				boolean jumped = false;
//				while(model.getFocusCells().size() > 0) {
//					view.repaint();
//					Thread.sleep(1000);
//					model.setMoveCell(model.getFocusCells().get(0));
//					model.getFocusCells().get(0).repaint();
//					cell.repaint();
//					Thread.sleep(1000);
//					model.movePiece();
//				}
//				
//				if (jumped) {
//					return;
//				}
//				
//				if (model.getTurn().equals(Color.BLUE)) {
//					handlePieceSelected(cell);
//					if (!model.getFocusCells().isEmpty()) {
//						Thread.sleep(1000);
//						model.setMoveCell(model.getFocusCells().get(0));
//						model.getFocusCells().get(0).repaint();
//						cell.repaint();
//						view.repaint();
//						Thread.sleep(1000);
//						model.movePiece();
//						return;
//					}
//				}
//				
//			}
//		}
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
