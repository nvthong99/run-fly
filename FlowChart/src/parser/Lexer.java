package parser;

import java.util.ArrayList;
import java.util.Stack;

public class Lexer {
	
	private String sourceString;
	private ArrayList<String> listString = new ArrayList<>();
	
	public Lexer(String sourceString) {
		this.sourceString = sourceString;
	}
	
	public ArrayList<String> getListString() {
		return this.listString;
	}
	
	public void lexing() {
		
		int state = 0;
		char ch;
		String string = "";
		Stack<Integer> stack = new Stack<>();
		
		for (int index = 0; index < sourceString.length(); index++) {
			ch = sourceString.charAt(index);
			switch (state) {
			case 0:
				if (Character.isLetterOrDigit(ch)) {
					state = 1;
					string += ch;
				} else if (ch == '(') {
					stack.push(index); 
					state = 2;
				}
				else if (ch == '{') listString.add("{");
				else if (ch == '}') listString.add("}");
				else if (ch == '<') {
					state = 3;
					string += ch;
				}
				else if (ch == '>') {
					state = 3;
					string += ch;
				}
				else if (ch == ';') listString.add(";");
				break;
				
			case 1:
				if (Character.isLetterOrDigit(ch)) {
					string += ch;
				} else {
					if (isIdString(string)) {
						string = "";
						state = 0;
						index--;
					} else {
						string += ch;
						state = 3;
					}
				}
				break;
				
			case 2:
				if (ch == '(') stack.push(index);
				if (ch == ')') stack.pop();
				if (stack.isEmpty()) {
					listString.add(string);
					string = "";
					state = 0;
					index--;
				} else {
					string += ch;
				}
				break;
				
			case 3:
				if (ch == ';') {
					listString.add(string);
					string = "";
					state = 0;
				} else {
					string += ch;
				}
				break;
			}
		}
	}
	
	private boolean isIdString(String string) {
		if (string.equals("if") || string.equals("for") || string.equals("else") || string.equals("while") 
				|| string.equals("do") || string.equals("cout") || string.equals("cin") || string.equals("gets")
				|| string.equals("getline")) {
			listString.add(string);
			return true;
		}
		return false;
	}
}
