package checkers.gameover;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import checkers.CheckersView;

public class GameOverController implements ActionListener {

	private GameOverPane view;
	private CheckersView parent;
	
	public GameOverController(GameOverPane view, CheckersView parent) {
		this.view = view;
		this.parent = parent;
		mapActions();
	}
	
	private void mapActions() {
		view.getMainMenuButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getMainMenuButton()) parent.showStartMenu();
	}
	
}
