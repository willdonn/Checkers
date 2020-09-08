package checkers.opponent.AI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import checkers.board.BoardModel;
import checkers.board.CheckersCell;
import checkers.board.CheckersCellView;

public class CheckersAI implements Runnable{
	
	private BoardModel model;
	private Color team;
	private CheckersMinimax minimax;
	private final AtomicBoolean running = new AtomicBoolean(false);
	
	public CheckersAI(BoardModel model, Color team) {
		this.model = model;
		this.team = team;
		minimax = new CheckersMinimax(model, team, (team == Color.RED) ? Color.BLUE : Color.RED);
	}
	
	private void movePlayerTwo() throws InterruptedException {
		
		if (model.getFocusPiece() != null && model.getAvailableMoves().size() != 0) {
			Thread.sleep(500);
			CheckersCell targetCell = model.getAvailableMoves().get(0);
			MouseEvent me2 = new MouseEvent(targetCell.getView(), 0, 0, 0, 100, 100, 1, false);
			targetCell.getView().getMouseListeners()[0].mousePressed(me2);
			return;
		}

		for (int i = 0; i < model.getBoard().size(); i++) {
			for (CheckersCell cell : model.getBoard().get(i)) {
				if (cell.getPiece() == null || cell.getPiece().getColor() != team) continue;
				model.setFocus(cell);
				model.refreshAvailableMoves();
				if (model.getAvailableMoves().size() != 0) {
					model.clearFocus();
					MouseEvent me = new MouseEvent(cell.getView(), 0, 0, 0, 100, 100, 1, false);
					cell.getView().getMouseListeners()[0].mousePressed(me);
					Thread.sleep(500);
					CheckersCell targetCell = model.getAvailableMoves().get(0);
					MouseEvent me2 = new MouseEvent(targetCell.getView(), 0, 0, 0, 100, 100, 1, false);
					targetCell.getView().getMouseListeners()[0].mousePressed(me2);
					return;
				}
			}
		}
	}
	
	public void stop() {
		running.set(false);;
	}

	@Override
	public void run() {
		running.set(true);
		while(running.get()) {
			try {
				while (!model.getTurn().equals(team) && running.get()) {
					synchronized(this) {
						this.wait();
					}
				}

				if (running.get()) movePlayerTwo();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
