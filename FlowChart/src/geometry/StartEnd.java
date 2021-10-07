package geometry;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartEnd extends JPanel {

	private static final long serialVersionUID = 1L;

	private final int OVAL_HEIGHT = 40;
	private final int DEFAULT_LENGTH = 100;
	
	private boolean isStart;
	
	public StartEnd(boolean isStart) {
		this.isStart = isStart;
		makeOvalLabel();
	}
	
	private void makeOvalLabel() {
		
		setBackground(Color.WHITE);
		setSize(DEFAULT_LENGTH, OVAL_HEIGHT);
		setLayout(null);
		
		JLabel ovalLabel = new JLabel(isStart? "BĐ" : "KT");
		ovalLabel.setBounds(0, 0, DEFAULT_LENGTH, OVAL_HEIGHT);
		ovalLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		ovalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(ovalLabel);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		// Draw oval
		g2D.drawOval(0, 0, DEFAULT_LENGTH, OVAL_HEIGHT);
	}
}
