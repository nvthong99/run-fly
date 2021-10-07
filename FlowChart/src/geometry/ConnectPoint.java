package geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ConnectPoint extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public int SIZE_OF_POINT = 30;
	
	public ConnectPoint(int number) {
		
		setBackground(Color.WHITE);
		setSize(SIZE_OF_POINT, SIZE_OF_POINT);
		setLayout(null);

		JLabel numberLabel = new JLabel(Integer.toString(number));
		numberLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberLabel.setBounds(0, 0, SIZE_OF_POINT, SIZE_OF_POINT);
		add(numberLabel);

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		// Draw oval
		g2D.drawOval(0, 0, SIZE_OF_POINT, SIZE_OF_POINT);
	}
}
