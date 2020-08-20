package checkers.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class CheckersCellView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CheckersCell model;
	
	public CheckersCellView(CheckersCell model) {
		this.model = model;
		initView();
	}
	
	private void initView() {
		setBackground(model.color);
		setSize(model.size);
	}
	
	public CheckersCell getModel() {
		return model;
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		

		int centrX = getWidth()/2;
		int centrY = getWidth()/2;
		int radius = getWidth()/4;
		
		if (model.piece != null) {
			g.setColor(model.piece.color);
			g.fillOval(centrX-radius, centrY-radius, radius*2, radius*2);
			
			if (model.piece.isKing()) {
				g.setColor(Color.GREEN);
				int innerRadius = radius/2;
				g.fillOval(centrX-innerRadius, centrY-innerRadius, innerRadius*2, innerRadius*2);
			}
			
			if (model.piece.focus) {
				g.setColor(Color.YELLOW);
				g.drawOval(centrX-radius, centrY-radius, radius*2, radius*2);
			}
		}
		
		if (model.piece == null && model.cellFocus) {
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
	}

}
	