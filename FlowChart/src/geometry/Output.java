package geometry;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Output extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final int LETTER_SIZE = 8;
	private final int INOUT_HEIGHT = 40;
	private final int DEFAULT_LENGTH = 70;
	
	int length;
	
	public Output(String string) {

		string = string.replaceFirst("<<", "");
		string = string.replaceAll("<<", ", ");
		string = string.replaceFirst(">>", "");
		string = string.replaceAll(">>", ", ");
		String bannerString = string;
		
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(null);
		setBackground(Color.WHITE);
		
		length = bannerString.length() * LETTER_SIZE;
		if (length < DEFAULT_LENGTH) length = DEFAULT_LENGTH;
		setSize(length + 40, INOUT_HEIGHT);
		setPreferredSize(new Dimension(length + 40, INOUT_HEIGHT));
		
		JLabel outputLabel = new JLabel(bannerString);
		outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
		outputLabel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		outputLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		outputLabel.setBounds(20, 0, length, INOUT_HEIGHT);
		add(outputLabel);
	}
	
	public int getLength() {
		
		return this.length + 40;
	}
}
