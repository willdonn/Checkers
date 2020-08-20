package checkers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import checkers.board.CheckersBoardController;
import checkers.board.CheckersBoardPane;
import checkers.gameover.GameOverController;
import checkers.gameover.GameOverPane;
import checkers.menu.StartMenuController;
import checkers.menu.StartMenuPane;
import checkers.opponent.AI.CheckersAI;

public class CheckersView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CheckersBoardPane checkersBoard;
	CheckersBoardController checkersBoardController;
	
	StartMenuPane startMenu;
	StartMenuController startMenuController;
	
	GameOverPane gameOver;
	GameOverController gameOverController;
	
	private enum ViewState {MENU, GAME, OVER};
	
	public ViewState state;
	private CheckersAI checkersAI;
	
	public CheckersView() {
		super();
		state = ViewState.MENU;
		setTitle("Checkers");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		//setLayout(new BorderLayout());
		initWindow();
		showStartMenu();
		run();
	}
	
	private void initWindow() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) Math.round(screenSize.width*0.4);
		
		int centrX = screenSize.width/2-(width/2);
		int centrY = screenSize.height/2 - (width/2);

		setSize(new Dimension(width, width));
		setLocation(centrX, centrY);
	}

	public void showGameOver(String winner, String loser) {
		gameOver = new GameOverPane(winner, loser);
		gameOverController = new GameOverController(gameOver, this);
		state = ViewState.OVER;
		showView();
	}
	
	public void showStartMenu() {
		startMenu = new StartMenuPane(this);
		startMenuController = new StartMenuController(startMenu, this);
		state = ViewState.MENU;
		showView();
	}
	
	public void startGame(int players) {
		checkersBoard = new CheckersBoardPane(this);
		checkersBoardController = new CheckersBoardController(checkersBoard, players, this);
		state = ViewState.GAME;
		showView();
	}
	
	private void showView() {
		getContentPane().removeAll();
		getContentPane().invalidate();

		if (state == ViewState.GAME) add(checkersBoard, BorderLayout.CENTER);
		else if (state == ViewState.MENU) add(startMenu, BorderLayout.CENTER);
		else if (state == ViewState.OVER) add(gameOver, BorderLayout.CENTER);
		
		getContentPane().revalidate();
	}
	
	private void run() {
		setResizable(false);
		setVisible(true);
	}


	public static void main(String[] args) {
		new CheckersView();
	}
	
}