package checkers.menu.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameSettingsPane extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel teamSelectionPanel; 
	private JCheckBox redTeam;
	private JCheckBox blueTeam;
	
	private JPanel difficultySelectionPanel;
	private JComboBox<String> difficulty;
	
	private JPanel focusAssistSelectionPanel;
	private JCheckBox moveFocusAssist;
	private JCheckBox pieceFocusAssist;

	private JPanel undoSelectionPanel;
	private JCheckBox undoSelection;
	
	private JPanel buttonPanel;
	private JButton menuButton;
	private JButton startButton;

	private final Color BACKGROUND = Color.GRAY;
	
	private int players;
	
	
	public GameSettingsPane(int players) {
		super();
		this.players = players;
		initView();
	}

	private void initView() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(BACKGROUND);
		
		createFocusAssistSelection();
		createButtonPanel();
		
		JLabel title = new JLabel("SETTINGS");
		title.setAlignmentX(CENTER_ALIGNMENT);
		title.setPreferredSize(new Dimension(250,250));
		title.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		
		add(title);
		if (players == 1) {
			createTeamSelection();
			createDifficultySelection();
			createUndoSelection();
			add(teamSelectionPanel);
			add(difficultySelectionPanel);
			add(undoSelectionPanel);
		}
		add(focusAssistSelectionPanel);
		add(buttonPanel);
	}

	private void createFocusAssistSelection() {
		focusAssistSelectionPanel = new JPanel();
		focusAssistSelectionPanel.setBackground(BACKGROUND);
		
		JLabel moveFocusAssistLabel = new JLabel("Show Available Moves");
		moveFocusAssistLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		moveFocusAssist = new JCheckBox();
		moveFocusAssist.setOpaque(false);
		
		JLabel pieceFocusAssistLabel = new JLabel("Show Available Pieces");
		pieceFocusAssistLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		pieceFocusAssist = new JCheckBox();
		pieceFocusAssist.setOpaque(false);
		
		focusAssistSelectionPanel.add(moveFocusAssist);
		focusAssistSelectionPanel.add(moveFocusAssistLabel);
		focusAssistSelectionPanel.add(pieceFocusAssist);
		focusAssistSelectionPanel.add(pieceFocusAssistLabel);
		
	}

	private void createDifficultySelection() {
		difficultySelectionPanel = new JPanel();
		difficultySelectionPanel.setBackground(BACKGROUND);
		JLabel difficultyLabel = new JLabel("Difficulty: ");
		difficultyLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		difficulty = new JComboBox<String>();
		difficulty.setOpaque(false);
		difficulty.addItem("EASY");
		difficulty.addItem("MEDIUM");
		difficulty.addItem("HARD");
		difficultySelectionPanel.add(difficultyLabel);
		difficultySelectionPanel.add(difficulty);
	}

	private void createTeamSelection() {
		teamSelectionPanel = new JPanel();
		teamSelectionPanel.setBackground(BACKGROUND);
		
		JLabel teamSelectionLabel = new JLabel("Team: ");
		teamSelectionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		JLabel redLabel = new JLabel("RED");
		redLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		redLabel.setForeground(Color.RED);
		redTeam = new JCheckBox();
		redTeam.setOpaque(false);
		
		JLabel blueLabel = new JLabel("BLUE");
		blueLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		blueLabel.setForeground(Color.blue);
		blueTeam = new JCheckBox();
		blueTeam.setOpaque(false);
		
		teamSelectionPanel.add(teamSelectionLabel);
		teamSelectionPanel.add(redTeam);
		teamSelectionPanel.add(redLabel);
		teamSelectionPanel.add(blueTeam);
		teamSelectionPanel.add(blueLabel);
	}
	
	private void createButtonPanel(){
		buttonPanel = new JPanel();
		buttonPanel.setBackground(BACKGROUND);
		startButton = new JButton("Start!");
		menuButton = new JButton("Main Menu");
		buttonPanel.add(startButton);
		buttonPanel.add(menuButton);
	}
	
	private void createUndoSelection() {
		undoSelectionPanel = new JPanel();
		undoSelectionPanel.setBackground(BACKGROUND);
		JLabel undoSelectionLabel = new JLabel("Allow Undo: ");
		undoSelectionLabel.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		undoSelection = new JCheckBox();
		undoSelection.setOpaque(false);
		undoSelectionPanel.add(undoSelectionLabel);
		undoSelectionPanel.add(undoSelection);
	}
	
	protected JCheckBox getMoveFocusAssist() {
		return moveFocusAssist;
	}
	
	protected JCheckBox getPieceFocusAssist() {
		return pieceFocusAssist;
	}
	
	protected JComboBox<String> getDifficulty(){
		return difficulty;
	}
	
	protected JCheckBox getRedTeamSelection() {
		return redTeam;
	}
	
	protected JCheckBox getBlueTeamSelection() {
		return blueTeam;
	}
	
	protected JButton getStartGameButton() {
		return startButton;
	}
	
	protected JButton getMenuButton() {
		return menuButton;
	}

	public JCheckBox getUndoSelection() {
		return undoSelection;
	}
	
}
