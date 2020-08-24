package checkers.menu.settings;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSettingsModel implements ActionListener {
	
	private GameSettingsPane view;
	
	private String difficulty;
	private Color player1team;
	private Color player2team;
	private boolean moveFocusAssist;
	private boolean pieceFocusAssist;
	private boolean allowUndo;
	private int players;
	
	public GameSettingsModel(GameSettingsPane view, int players) {
		this.view = view;
		this.players = players;
		initDefaultSettings();
		mapActions();
	}
	
	private void initDefaultSettings() {
		difficulty = "MEDIUM";
		player1team = Color.RED;
		player2team = Color.BLUE;
		moveFocusAssist = true;
		pieceFocusAssist = false;
		allowUndo = true;
		
		if (players == 1) {
			if (player1team == Color.red) view.getRedTeamSelection().setSelected(true);
			else view.getBlueTeamSelection().setSelected(true);
			view.getDifficulty().setSelectedItem(difficulty);
			view.getUndoSelection().setSelected(allowUndo);
		}
		
		view.getMoveFocusAssist().setSelected(moveFocusAssist);
		view.getPieceFocusAssist().setSelected(pieceFocusAssist);
		
	}

	private void mapActions() {
		if (players == 1) {
			view.getBlueTeamSelection().addActionListener(this);
			view.getRedTeamSelection().addActionListener(this);
			view.getDifficulty().addActionListener(this);
			view.getUndoSelection().addActionListener(this);
		}
		view.getMoveFocusAssist().addActionListener(this);
		view.getPieceFocusAssist().addActionListener(this);
	}
	
	public String getDifficulty() {
		return difficulty;
	}
	
	public Color getPlayer1Team() {
		return player1team;
	}
	
	public Color getPlayer2Team() {
		return player2team;
	}
	
	public boolean isMoveFocusAssist() {
		return moveFocusAssist;
	}
	
	public boolean isPieceFocusAssist() {
		return pieceFocusAssist;
	}
	
	public boolean isAllowUndo() {
		return allowUndo;
	}
	
	public boolean isOnePlayer() {
		return players == 1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getDifficulty()) difficulty = (String) view.getDifficulty().getSelectedItem();
		else if (e.getSource() == view.getUndoSelection()) allowUndo = view.getUndoSelection().isSelected();
		else if (e.getSource() == view.getRedTeamSelection() || e.getSource() == view.getBlueTeamSelection()) {
			player1team = (view.getRedTeamSelection().isSelected()) ? Color.RED : Color.BLUE;
			player2team = player1team == Color.BLUE ? Color.RED : Color.BLUE;
		}
		else if (e.getSource() == view.getMoveFocusAssist()) moveFocusAssist = view.getMoveFocusAssist().isSelected();
		else if (e.getSource() == view.getPieceFocusAssist()) pieceFocusAssist = view.getPieceFocusAssist().isSelected();
	}
	
}
