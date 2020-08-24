package checkers.menu.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import checkers.CheckersView;

public class GameSettingsController implements ActionListener {
	
	private CheckersView parent;
	
	private GameSettingsPane view;
	private GameSettingsModel settings;
	
	private JCheckBox redTeam;
	private JCheckBox blueTeam;
	
	private JButton startGame;
	private JButton mainMenu;
	
	public GameSettingsController(GameSettingsPane view, int players, CheckersView parent) {
		this.view = view;
		this.settings = new GameSettingsModel(view, players);
		this.parent = parent;
		getComponents();
		mapActions();
	}

	private void getComponents() {
		if (settings.isOnePlayer()) {
			redTeam = view.getRedTeamSelection();
			blueTeam = view.getBlueTeamSelection();
		}
		startGame = view.getStartGameButton();
		mainMenu = view.getMenuButton();
	}

	private void mapActions() {
		if (settings.isOnePlayer()) {
			redTeam.addActionListener(this);
			blueTeam.addActionListener(this);
		}
		startGame.addActionListener(this);
		mainMenu.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == blueTeam) 
			redTeam.setSelected(!redTeam.isSelected());
			
		else if(e.getSource() == redTeam)
			blueTeam.setSelected(!blueTeam.isSelected());
	
		else if (e.getSource() == startGame) parent.startGame(settings);
		else if (e.getSource() == mainMenu) parent.showStartMenu();
	}
	
}
