package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;

public class Main {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		JTextArea mainArea = new JTextArea();
		mainArea.setBorder(new EmptyBorder(20, 15, 0, 0));
		mainArea.setTabSize(2);
		mainArea.setCaretColor(Color.WHITE);
		mainArea.setFont(new Font("Calibri", Font.PLAIN, 20));
		mainArea.setForeground(Color.WHITE);
		mainArea.setBackground(Color.BLACK);
		mainArea.setBounds(12, 32, 510, 956);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		mainPanel.setLocation(524, 32);
		mainPanel.setSize(1350, 956);
		mainPanel.setLayout(null);
		
		JButton convert = new JButton("Convert");
		convert.setBorder(new MatteBorder(0, 0, 2, 2, (Color) new Color(0, 0, 0)));
		convert.setFont(new Font("Tahoma", Font.PLAIN, 15));
		convert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String lines = mainArea.getText();
				mainPanel.removeAll();
				mainPanel.repaint();
				DrawChart drawChart = new DrawChart(lines);
				drawChart.startDraw();
				mainPanel.add(drawChart);
				mainPanel.revalidate();
			}
		});
		convert.setBounds(526, 34, 83, 27);
		
		JLabel banner1 = new JLabel("Code:");
		banner1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		banner1.setBounds(12, 0, 52, 25);

		JLabel banner2 = new JLabel("Flow Chart:");
		banner2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		banner2.setBounds(524, 0, 102, 25);
		
		frame.getContentPane().add(convert);
		frame.getContentPane().add(mainArea);
		frame.getContentPane().add(mainPanel);
		frame.getContentPane().add(banner2);
		frame.getContentPane().add(banner1);
		
	}
}