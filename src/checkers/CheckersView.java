package checkers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import checkers.board.CheckersBoardController;
import checkers.board.CheckersBoardPane;
import checkers.gameover.GameOverController;
import checkers.gameover.GameOverPane;
import checkers.menu.settings.GameSettingsController;
import checkers.menu.settings.GameSettingsModel;
import checkers.menu.settings.GameSettingsPane;
import checkers.menu.start.StartMenuController;
import checkers.menu.start.StartMenuPane;
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
	
	GameSettingsPane gameSettingsMenu;
	GameSettingsController gameSettingsController;
	
	GameOverPane gameOver;
	GameOverController gameOverController;
	
	private enum ViewState {START_MENU, SETTINGS_MENU, GAME, OVER};
	
	public ViewState state;
	
	public CheckersView() {
		super();
		state = ViewState.START_MENU;
		setTitle("Checkers");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		state = ViewState.START_MENU;
		showView();
	}
	
	public void showSettingsMenu(int players) {
		gameSettingsMenu = new GameSettingsPane(players);
		gameSettingsController = new GameSettingsController(gameSettingsMenu, players, this);
		state = ViewState.SETTINGS_MENU;
		showView();
	}
	
	public void startGame(GameSettingsModel settings) {
		checkersBoard = new CheckersBoardPane(this, settings);
		checkersBoardController = new CheckersBoardController(checkersBoard, settings, this);
		state = ViewState.GAME;
		showView();
	}
	
	private void showView() {
		getContentPane().removeAll();
		getContentPane().invalidate();

		if (state == ViewState.GAME) add(checkersBoard);
		else if (state == ViewState.START_MENU) add(startMenu);
		else if (state == ViewState.OVER) add(gameOver);
		else if (state == ViewState.SETTINGS_MENU) add(gameSettingsMenu);
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