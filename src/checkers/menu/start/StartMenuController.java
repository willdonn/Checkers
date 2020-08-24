package checkers.menu.start;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import checkers.CheckersView;

public class StartMenuController implements ActionListener{
	
	private StartMenuPane view;
	private CheckersView parent;
	
	public StartMenuController(StartMenuPane view, CheckersView parent) {
		this.view = view;
		this.parent = parent;
		mapActions();
	}
	
	public void mapActions() {
		view.getStartGameOnePlayerButton().addActionListener(this);
		view.getStartGameTwoPlayerButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getStartGameOnePlayerButton()) parent.showSettingsMenu(1);
		else if (e.getSource() == view.getStartGameTwoPlayerButton()) parent.showSettingsMenu(2);
	}
	
}
