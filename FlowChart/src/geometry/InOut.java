package geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class InOut extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int LETTER_SIZE = 9;
	private final int INOUT_HEIGHT = 40;
	private final int PARALLELOGRAM_SPACE = 20;
	private final int DEFAULT_LENGTH = 130;

	private String string;
	private Boolean isInput;
	private int length;

	public InOut(String newString, Boolean isInput) {

		this.string = newString;
		this.isInput = isInput;
		makeInputLabel();
	}

	private void makeInputLabel() {

		setBackground(Color.WHITE);
		setLayout(null);
		
		string = string.replaceAll("<<", " ");
		String bannerString = ((isInput) ? "ĐV " : "ĐR ") + string;
	//	String bannerString = "nhập   " +string;
		length = bannerString.length() * LETTER_SIZE;
		if (length < DEFAULT_LENGTH)
			length = DEFAULT_LENGTH;
		setSize(length, INOUT_HEIGHT);

		JLabel inputLabel = new JLabel(bannerString);
		inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inputLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		inputLabel.setBounds(0, 0, length, INOUT_HEIGHT);
		add(inputLabel);
		
	}
	  
	/*
	 * //Thầy Hoàn string = string.replaceFirst("<<", ""); string =
	 * string.replaceAll("<<", ", "); string = string.replaceFirst(">>", ""); string
	 * = string.replaceAll(">>", ", "); String bannerString = string;
	 */
	/*
	 * //Thầy Vũ 
	 * string = string.replaceAll(">>", " >> "); string =
	 * string.replaceAll("<<", " << "); String bannerString = ((isInput)? "Vào" :
	 * "Ra") + string;
	 */
	/*
	 * //Thầy Đạt string = string.replaceAll(">>", " >> "); string =
	 * string.replaceAll("<<", " << "); String bannerString = ((isInput)? "ĐV" :
	 * "ĐR") + string;
	 */

	public int getLength() {
		return this.length;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		// Draw parallelogram
		g2D.drawLine(0, INOUT_HEIGHT, PARALLELOGRAM_SPACE, 0);
		g2D.drawLine(length, 0, PARALLELOGRAM_SPACE, 0);
		g2D.drawLine(length, 0, length - PARALLELOGRAM_SPACE, INOUT_HEIGHT);
		g2D.drawLine(0, INOUT_HEIGHT, length - PARALLELOGRAM_SPACE, INOUT_HEIGHT);

	}
}
