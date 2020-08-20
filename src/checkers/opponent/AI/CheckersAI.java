package checkers.opponent.AI;

import java.awt.Color;
import java.awt.event.MouseEvent;

import checkers.board.BoardModel;
import checkers.board.CheckersCellView;

public class CheckersAI implements Runnable{
	
	private BoardModel model;
	
	public CheckersAI(BoardModel model) {
		this.model = model;
	}
	
	private void movePlayerTwo() throws InterruptedException {
		
		if (model.getFocusPiece() != null && model.getAvailableMoves().size() != 0) {
			Thread.sleep(500);
			CheckersCellView targetCellView = model.getCellView(model.getAvailableMoves().get(0));
			MouseEvent me2 = new MouseEvent(targetCellView, 0, 0, 0, 100, 100, 1, false);
			targetCellView.getMouseListeners()[0].mousePressed(me2);
			return;
		}

		for (int i = 0; i < model.getBoard().size(); i++) {
			for (CheckersCellView cellView : model.getBoardView().get(i)) {
				if (cellView.getModel().getPiece() == null || cellView.getModel().getPiece().color == Color.RED) continue;
				model.setFocus(cellView.getModel());
				model.generateAvailableMoves();
				if (model.getAvailableMoves().size() != 0) {
					model.clearFocus();
					MouseEvent me = new MouseEvent(cellView, 0, 0, 0, 100, 100, 1, false);
					cellView.getMouseListeners()[0].mousePressed(me);
					Thread.sleep(500);
					CheckersCellView targetCellView = model.getCellView(model.getAvailableMoves().get(0));
					MouseEvent me2 = new MouseEvent(targetCellView, 0, 0, 0, 100, 100, 1, false);
					targetCellView.getMouseListeners()[0].mousePressed(me2);
					return;
				}
			}
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				while (model.getTurn() == Color.RED) {
					synchronized(this) {
						this.wait();
					}
				}

				movePlayerTwo();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
