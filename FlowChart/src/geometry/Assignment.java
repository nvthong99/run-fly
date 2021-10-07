package geometry;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Assignment extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final int ASSIGNMENT_HEIGHT = 40;
	private final int DEFAULT_LENGTH = 70;
	private final int LETTER_SIZE = 8;

	private String assignmentString;
	private int length;
	
	public Assignment(String newString) {
		
		this.assignmentString = newString;
		makeAssignmentLabel();
	}
	
	private void makeAssignmentLabel() {
		
		setBackground(Color.WHITE);
		length = assignmentString.length() * LETTER_SIZE;
		if (length < DEFAULT_LENGTH) length = DEFAULT_LENGTH;
		setSize(length, ASSIGNMENT_HEIGHT);
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(null);
		
		JLabel assignmentLabel = new JLabel(assignmentString);
		assignmentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		assignmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		assignmentLabel.setBounds(0, 0, length, ASSIGNMENT_HEIGHT);
		add(assignmentLabel);
	}
	
	public int getLength() {
		return this.length;
	}
}
