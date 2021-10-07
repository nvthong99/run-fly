package main;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;

import geometry.Assignment;
import geometry.Compare;
import geometry.ConnectPoint;
import geometry.InOut;
import geometry.StartEnd;
import parser.Lexer;

public class DrawChart extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final int BLOCK_DISTANCE = 30;			
	private final int COMPARE_SPACE = 35;			
	private final int CHART_SPACE = 300;			
	private final int START_WIDTH = 200;			
	private final int START_HEIGHT = 5;				
	private final int ELSE_DISTANCE = 50;			
	private final int CONNECT_DISTANCE = 75;		
	private final int START_CONNECTION_POINT = 0;	
	private final int ARROW_SPACE = 5;				
	private final int ARROW_HEIGHT = 10;			
	
	private int curPosX = START_WIDTH;				
	private int curPosY = START_HEIGHT;				
	private int index;
	private int countConnectPoint = START_CONNECTION_POINT;
	
	private boolean isHaveArrow = true;
	private boolean isHorizontalDraw = false;
	
	private String sourceString;
	private Stack<Integer> precurPosX = new Stack<>();				// storge x position of compare
	private Stack<Integer> precurPosY = new Stack<>();				// storge y position of compare
	private Stack<Integer> compareHeight = new Stack<>();			// storge height position of compare block
	private Stack<Integer> compareWidth = new Stack<>();			// storge width position of compare block
	private Stack<Integer> forPlusWidth = new Stack<>();			// storge width position of compare
	private Stack<String> keyword = new Stack<>();					// storge keyword: if, else, while, for, do
	private Stack<Integer> count = new Stack<>();					// count command of keyword
	private Stack<String> varUpdate = new Stack<>();				// storge variable update of for loop
	private Stack<Boolean> isHaveElse = new Stack<>();				// Is have else of if command?
	private ArrayList<Point> pointArrowHead = new ArrayList<>();	// storge head arrow
	private ArrayList<Point> pointArrowTail = new ArrayList<>();	// storge tail arrow
	private ArrayList<Point> pointLineHead = new ArrayList<>();		// storge head line
	private ArrayList<Point> pointLineTail = new ArrayList<>();		// storge tail line
	private ArrayList<String> listCommand;							// list of commands
	
	public DrawChart(String sourceString) {
		this.sourceString = sourceString;
		lexing();
	}
	
	public void startDraw() {
		
		setLayout(null);
		setSize(1349, 955);
		int startConnectPoint = START_CONNECTION_POINT;
		if (startConnectPoint == 0) {
			StartEnd start = new StartEnd(true);
			start.setLocation(curPosX - 50, curPosY);
			curPosY += start.getHeight();
			startConnectPoint++;
			add(start);
		} else {
			ConnectPoint connectStartPoint = new ConnectPoint(countConnectPoint);
			connectStartPoint.setLocation(curPosX - connectStartPoint.getWidth() / 2, curPosY);
			curPosY += connectStartPoint.getHeight();
			add(connectStartPoint);
		}
		
		for (index = 0; index < listCommand.size(); index++)
			parsing();
		counting();
		
		StartEnd end = new StartEnd(false);
		if (isHorizontalDraw) {
			addArrowRight(end);
			end.setLocation(curPosX, curPosY);
		} else {
			addArrowDown();
			end.setLocation(curPosX - 50, curPosY);
		}
		add(end);
		repaint();
	}
	
	public void lexing() {
		
		if (sourceString.indexOf("main") != -1) {
			Stack<Integer> curly = new Stack<>();
			curly.push(1);
			String string = sourceString.substring(sourceString.indexOf("main"));
			string = string.substring(string.indexOf("{") + 1);
			int pos;
			for (pos = 0; !curly.empty(); pos++) {
				if (string.charAt(pos) == '{') curly.push(1);
				if (string.charAt(pos) == '}') curly.pop();
			}
			sourceString = string.substring(0, pos - 1);
		}

		Lexer lexer = new Lexer(sourceString);
		lexer.lexing();
		this.listCommand = lexer.getListString();
	}
	
	private void parsing() {
		
		if (listCommand.get(index).equals("{")) {
			count.set(count.size() - 1, -999);
			return;
		}
		if (listCommand.get(index).equals("}")) {
			int pos;
			for (pos = count.size() - 1; count.get(pos) >= 0; pos--)
				count.set(pos, 1);
			counting();
			count.set(pos, 1);
			return;
		}
		if (listCommand.get(index).equals(";")) {
			counting();
			return;
		}
		if (listCommand.get(index).equals("getline")) {
			String[] getLineString = listCommand.get(++index).split(","); 
			drawCin(getLineString[1]);
			index++;
			return;
		}
		if (listCommand.get(index).equals("cout")) {
			drawCout(listCommand.get(++index));
			return;
		}
		if (listCommand.get(index).equals("cin")) {
			drawCin(listCommand.get(++index));
			return;
		}
		if (listCommand.get(index).equals("gets")) {
			drawCin(listCommand.get(++index));
			return;
		}
		if (listCommand.get(index).equals("if")) {
			drawCompare(listCommand.get(++index), "if", true);
			return;
		}
		if (listCommand.get(index).equals("else")) {
			drawElse();
			return;
		}
		if (listCommand.get(index).equals("for")) {
			drawFor(listCommand.get(++index));
			return;
		}
		drawAssignment(listCommand.get(index), true);
	}
	
	private void drawFor(String string) {
		
		counting();
		String[] lines = string.split(";");
		if (!lines[2].equals(""))
			varUpdate.push(lines[2]);
		if (!lines[0].equals(""))
			drawAssignment(lines[0], false);
		drawCompare(lines[1], "for", false);
	}
	
	private void drawCompare(String string, String keywords, boolean isCount) {
		
		if (isCount) counting();
		Compare compare = new Compare(string);
		if (isHaveArrow) addArrowDown();
		else {
			isHaveArrow = true;
			addHeight(Compare.COMPARE_HEIGHT / 2);
			addArrowDown();
		}
		compare.setLocation(curPosX - compare.getLength() / 2, curPosY);
		if (keyword.indexOf("else") == -1) {
			for(int pos = 0; pos < compareWidth.size(); pos++)
				if (keyword.get(pos).equals(keywords))
					compareWidth.set(pos, compareWidth.get(pos) + COMPARE_SPACE);
			for (int pos = 0; pos < forPlusWidth.size(); pos++)
				forPlusWidth.set(pos, forPlusWidth.get(pos) + COMPARE_SPACE);
		}
		addWidth(compare.getLength() / 2);
		addHeight(Compare.COMPARE_HEIGHT);
		if (keywords.equals("for") && keyword.indexOf("for") != -1)
			forPlusWidth.push(compare.getLength() / 2 + COMPARE_SPACE);
		push(curPosX, curPosY - Compare.COMPARE_HEIGHT, compare.getLength() / 2 + COMPARE_SPACE, Compare.COMPARE_HEIGHT, keywords);
		add(compare);
	}
	
	private void drawElse() {
		
		for (int pos = 0; pos < compareWidth.size() - 1; pos++)
			if (keyword.get(pos).equals("if") && !isHaveElse.get(pos))
				compareWidth.set(pos, compareWidth.get(pos) + 2 * ELSE_DISTANCE - COMPARE_SPACE / 2);
		for (int pos = 0; pos < forPlusWidth.size(); pos++)
			forPlusWidth.set(pos, forPlusWidth.get(pos) + 2 * ELSE_DISTANCE - COMPARE_SPACE / 2);
		for (int pos = 0; pos < compareHeight.size() - 1; pos++)
			compareHeight.set(pos, compareHeight.get(pos) - compareHeight.peek() + Compare.COMPARE_HEIGHT);
		compareWidth.set(compareWidth.size() - 1, compareWidth.peek() + ELSE_DISTANCE);
		curPosX += compareWidth.peek();
		curPosY = precurPosY.peek() + Compare.COMPARE_HEIGHT;
		isHaveElse.set(isHaveElse.size() - 1, true);
		push(curPosX, curPosY, compareWidth.peek(), Compare.COMPARE_HEIGHT, "else");
	}
	
	private void push(int x, int y, int width, int height, String keywords) {
		
		precurPosX.push(x);
		precurPosY.push(y);
		compareWidth.push(width);
		compareHeight.push(height);
		keyword.push(keywords);
		count.push(0);
		isHaveElse.push(false);
	}
	
	private void counting() {
		
		if (keyword.empty()) return;
		count.set(count.size() - 1, count.lastElement() + 1);
		while (count.lastElement() == 2) {
			drawLine(keyword.peek());
			pop();
			if (keyword.empty()) break;
			count.set(count.size() - 1, count.lastElement() + 1);
		}
	}
	
	private void pop() {
		precurPosX.pop();
		precurPosY.pop();
		compareWidth.pop();
		compareHeight.pop();	
		count.pop();
		isHaveElse.pop();
	}
	
	private void drawLine(String string) {
		
		if (string.equals("if")) 
			drawLineIf();
		else if (string.equals("else"))
			drawLineElse();
		else if (string.equals("for"))
			drawLineFor();
	}
	
	private void drawLineIf() {
		
		int x =  precurPosX.peek();
		int y = precurPosY.peek() + Compare.COMPARE_HEIGHT / 2;
		int w = compareWidth.peek();
		int h = compareHeight.peek();
		pointLineHead.add(new Point(x, y));
		x += w;
		pointLineTail.add(new Point(x, y));
		pointLineHead.add(new Point(x, y));
		h = h - Compare.COMPARE_HEIGHT / 2 + BLOCK_DISTANCE / 2;
		y += h;
		pointLineTail.add(new Point(x, y));
		pointArrowHead.add(new Point(x, y));
		x -= w;
		pointArrowTail.add(new Point(x, y));
		keyword.pop();
		return;
	}
	
	private void drawLineElse() {
		
		int x = precurPosX.get(precurPosX.size() - 2);
		int y = precurPosY.get(precurPosY.size() - 2);
		int curHeight = compareHeight.peek();
		int preHeight = compareHeight.get(compareHeight.size() - 2) - curHeight + Compare.COMPARE_HEIGHT;
		compareHeight.set(compareHeight.size() - 2, preHeight);
		
		if (curHeight > preHeight) {
			pointLineHead.add(new Point(x, y));
			compareHeight.set(compareHeight.size() - 2, curHeight);
			pointLineTail.add(new Point(x, y + curHeight));
		} else {
			curPosY = y + preHeight;
			for(int pos = 0; pos < compareHeight.size() - 2; pos++)
				compareHeight.set(pos, compareHeight.get(pos) + preHeight - curHeight);
		}
		curPosX = x;
		keyword.pop();
	}
	
	private void drawLineFor() {

		drawAssignment(varUpdate.peek(), false);
		
		int x = precurPosX.peek();
		int y = precurPosY.peek();
		int w = compareWidth.peek();
		int h = compareHeight.peek();
		
		y -= BLOCK_DISTANCE / 2;
		pointArrowTail.add(new Point(x, y));
		x -= w;
		pointArrowHead.add(new Point(x, y));
		pointLineHead.add(new Point(x, y));
		y += BLOCK_DISTANCE / 2 + h - Assignment.ASSIGNMENT_HEIGHT / 2;
		pointLineTail.add(new Point(x, y));
		pointLineHead.add(new Point(x, y));
		x += w;
		pointLineTail.add(new Point(x, y));
		
		addHeight(BLOCK_DISTANCE);
		varUpdate.pop();
		keyword.pop();
		
		x = precurPosX.peek();
		y = precurPosY.peek();
		
		if (keyword.indexOf("for") != -1) {
			y += Compare.COMPARE_HEIGHT / 2;
			pointLineHead.add(new Point(x, y));
			x += forPlusWidth.peek();
			pointLineTail.add(new Point(x, y));
			pointLineHead.add(new Point(x, y));
			y += compareHeight.peek();
			pointLineTail.add(new Point(x, y));
			pointLineHead.add(new Point(x, y));
			x = x - forPlusWidth.peek();
			pointLineTail.add(new Point(x, y));
			forPlusWidth.pop();
			isHaveArrow = false;
		} else if (listCommand.size() > 2 + index) {
			ConnectPoint connectStartPoint = new ConnectPoint(++countConnectPoint);
			connectStartPoint.setLocation(x + CONNECT_DISTANCE, y + Compare.COMPARE_HEIGHT / 2 - connectStartPoint.SIZE_OF_POINT / 2);
			pointArrowHead.add(new Point(x, y + Compare.COMPARE_HEIGHT / 2));
			pointArrowTail.add(new Point(x + CONNECT_DISTANCE, y + Compare.COMPARE_HEIGHT / 2));
			add(connectStartPoint);
			
			curPosX = START_WIDTH + (countConnectPoint - START_CONNECTION_POINT) * CHART_SPACE;
			curPosY = START_HEIGHT;
			
			ConnectPoint connectEndPoint = new ConnectPoint(countConnectPoint);
			connectEndPoint.setLocation(curPosX - connectEndPoint.SIZE_OF_POINT / 2, curPosY);
			curPosY += connectEndPoint.SIZE_OF_POINT;
			add(connectEndPoint);
			
			
		} else {
			isHorizontalDraw = true;
			curPosX = x;
			curPosY = y;
			pointLineHead.add(new Point(curPosX, curPosY + Compare.COMPARE_HEIGHT / 2));
			curPosX += CONNECT_DISTANCE;
			pointLineTail.add(new Point(curPosX, curPosY + Compare.COMPARE_HEIGHT / 2));
		}
	}
	
	private void drawAssignment(String string, boolean isCount) {
		
		if (isCount) counting();
		Assignment assignment = new Assignment(string);
		if (isHorizontalDraw) {
			addArrowRight(assignment);
			assignment.setLocation(curPosX, curPosY);
			curPosX += assignment.getLength();
		} else {
			if (isHaveArrow) addArrowDown();
			else {
				pointArrowHead.add(new Point(curPosX + assignment.getLength() / 2 + COMPARE_SPACE, curPosY + assignment.getHeight() / 2));
				pointArrowTail.add(new Point(curPosX + assignment.getLength() / 2, curPosY + assignment.getHeight() / 2));
				isHaveArrow = true;
			}
			assignment.setLocation(curPosX - assignment.getLength() / 2, curPosY);
			addWidth(assignment.getLength() / 2);
			addHeight(assignment.getHeight());
		}
		add(assignment);
	}
	
	private void drawCout(String string) {
		
		counting();
		string = string.replaceAll(" ", "");
		if (string.indexOf("<i") != -1 || string.indexOf("<i+1") != -1)
			return;
		string = string.replaceAll("endl", "");
		string = string.replaceAll("\".*?\"", "");
		while (string.indexOf("<<<<") != -1)
			string = string.replaceAll("<<<<", "<<");
		if (!string.isEmpty() && string.lastIndexOf("<<") == string.length() - 2)
			string = string.substring(0, string.length() - 2);
		if (!string.isEmpty()) {
			InOut inOut = new InOut(string, false);
			if (isHorizontalDraw) {
				addArrowRight(inOut);
				inOut.setLocation(curPosX, curPosY);
				curPosX += inOut.getLength();
			} else {
				if (isHaveArrow) addArrowDown();
				else {
					pointArrowHead.add(new Point(curPosX + inOut.getLength() / 2 + COMPARE_SPACE, curPosY + inOut.getHeight() / 2));
					pointArrowTail.add(new Point(curPosX + inOut.getLength() / 2, curPosY + inOut.getHeight() / 2));
					isHaveArrow = true;
				}
				inOut.setLocation(curPosX - inOut.getLength() / 2, curPosY);
				addWidth(inOut.getLength() / 2);
				addHeight(inOut.getHeight());
			}
			add(inOut);
		}
	}
	
	private void drawCin(String string) {
		
		counting();
		string = string.replaceAll(" ", "");
		InOut inOut = new InOut(string, true);
		if (isHorizontalDraw) {
			addArrowRight(inOut);
			inOut.setLocation(curPosX, curPosY);
			curPosX += inOut.getLength() / 2;
		} else {
			if (isHaveArrow) addArrowDown();
			else {
				pointArrowHead.add(new Point(curPosX + inOut.getLength() / 2 + COMPARE_SPACE, curPosY + inOut.getHeight() / 2));
				pointArrowTail.add(new Point(curPosX + inOut.getLength() / 2, curPosY + inOut.getHeight() / 2));
				isHaveArrow = true;
			}
			inOut.setLocation(curPosX - inOut.getLength() / 2, curPosY);
			addWidth(inOut.getLength() / 2);
			addHeight(inOut.getHeight());
		}
		add(inOut);
	}
	
	private void addWidth(int length) {

		if (compareWidth.empty()) return;
		if (length + 20 > compareWidth.peek()) {
			length = length - compareWidth.peek() + COMPARE_SPACE;
			for (int pos = 0; pos < compareWidth.size(); pos++)
				compareWidth.set(pos, compareWidth.get(pos) + length);
			for (int pos = 0; pos < forPlusWidth.size(); pos++)
				forPlusWidth.set(pos, forPlusWidth.get(pos) + length);
		}
	}
	
	private void addHeight(int height) {
		
		curPosY += height;
		for(int pos = 0; pos < compareHeight.size(); pos++)
			compareHeight.set(pos, compareHeight.get(pos) + height);
	}
	
	private void addArrowDown() {
		
		pointArrowHead.add(new Point(curPosX, curPosY));
		addHeight(BLOCK_DISTANCE);
		pointArrowTail.add(new Point(curPosX, curPosY));
	}
	
	private void addArrowRight(JPanel panel) {
		
		pointArrowHead.add(new Point(curPosX, curPosY + panel.getHeight() / 2));
		curPosX += BLOCK_DISTANCE;
		pointArrowTail.add(new Point(curPosX, curPosY + panel.getHeight() / 2));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		for (int index = pointArrowHead.size() - 1; index >= 0; index--) {
			drawArrow(g, pointArrowHead.get(index), pointArrowTail.get(index));
		}
		
		for (int index = pointLineHead.size() - 1; index >= 0; index--) {
			drawLine(g, pointLineHead.get(index), pointLineTail.get(index));
		}
	}
	
	void drawLine(Graphics g, Point point1, Point point2) {
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		g2D.drawLine(point1.x, point1.y, point2.x, point2.y);
	}
	
	void drawArrow(Graphics g, Point point1, Point point2) {
		
		if ((point1.x == point2.x && point1.y == point2.y) || (point1.x != point2.x && point1.y != point2.y)) return;
				
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		g2D.drawLine(point1.x, point1.y, point2.x, point2.y);
		
		// Draw arrow at the top of line
		
		if (point2.y > point1.y) {
			g2D.drawLine(point2.x - ARROW_SPACE, point2.y - ARROW_HEIGHT, point2.x, point2.y);
			g2D.drawLine(point2.x + ARROW_SPACE, point2.y - ARROW_HEIGHT, point2.x, point2.y);
		}
		
		if (point2.y < point2.y) {
			g2D.drawLine(point2.x - ARROW_SPACE, point2.y + ARROW_HEIGHT, point2.x, point2.y);
			g2D.drawLine(point2.x + ARROW_SPACE, point2.y + ARROW_HEIGHT, point2.x, point2.y);
		}
		
		if (point2.x > point1.x) {
			g2D.drawLine(point2.x - ARROW_HEIGHT, point2.y + ARROW_SPACE, point2.x, point2.y);
			g2D.drawLine(point2.x - ARROW_HEIGHT, point2.y - ARROW_SPACE, point2.x, point2.y);
		}
		
		if (point2.x < point1.x) {
			g2D.drawLine(point2.x + ARROW_HEIGHT, point2.y + ARROW_SPACE, point2.x, point2.y);
			g2D.drawLine(point2.x + ARROW_HEIGHT, point2.y - ARROW_SPACE, point2.x, point2.y);
		}
	}
}
