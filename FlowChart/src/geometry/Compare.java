package geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Compare extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public static final int COMPARE_HEIGHT = 40;
	private final int LETTER_SIZE = 12;
	private final int DEFAULT_LENGTH = 100;
	private final int RIGHT_WRONG_SPACE = 20;
	
	private String compareString;
	private String rightString  = "-";
	private String bottomString = "+";
	private int length;
	
	public Compare(String newString) {
		
		this.compareString = newString;
		makeCompareLabel();
	}
	
	public Compare(String newString, boolean isDoWhile) {
		
		this.compareString = newString;
		if (isDoWhile) {
			this.rightString = "Đ";
			this.bottomString = "S";
		}
		makeCompareLabel();
	}
	
	private void makeCompareLabel() {
		
		setBackground(Color.WHITE);
		setLayout(null);
		length = compareString.length() * LETTER_SIZE;
		if (length < DEFAULT_LENGTH) length = DEFAULT_LENGTH;
		setSize(length + RIGHT_WRONG_SPACE, COMPARE_HEIGHT + RIGHT_WRONG_SPACE);
		
		JLabel compareLabel = new JLabel(compareString);
		compareLabel.setHorizontalAlignment(SwingConstants.CENTER);
		compareLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		compareLabel.setBounds(0, 0, length, COMPARE_HEIGHT);
		add(compareLabel);
		
		JLabel rightLabel = new JLabel(rightString);
		rightLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rightLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		rightLabel.setBounds(length, 0, RIGHT_WRONG_SPACE, RIGHT_WRONG_SPACE);
		add(rightLabel);
		
		JLabel bottomLabel = new JLabel(bottomString);
		bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		bottomLabel.setBounds(length / 2, COMPARE_HEIGHT, RIGHT_WRONG_SPACE, RIGHT_WRONG_SPACE);
		add(bottomLabel);
	}
	
	public int getLength() {
		return this.length;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		// Draw rhombus
		g2D.drawLine(length / 2, 0, 0, COMPARE_HEIGHT / 2);
		g2D.drawLine(length / 2, COMPARE_HEIGHT, 0, COMPARE_HEIGHT / 2);
		g2D.drawLine(length / 2, 0, length, COMPARE_HEIGHT / 2);
		g2D.drawLine(length / 2, COMPARE_HEIGHT, length, COMPARE_HEIGHT / 2);
		g2D.drawLine(length, COMPARE_HEIGHT / 2, length + RIGHT_WRONG_SPACE, COMPARE_HEIGHT / 2);
		g2D.drawLine(length / 2, COMPARE_HEIGHT, length/2, COMPARE_HEIGHT + RIGHT_WRONG_SPACE);
	}
}
