package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.lang.reflect.Field;

public class Player {
	private String name;
	private Color color;
	private int row;
	private int col;
	private ArrayList<Card> cards;
	
	public Player(String name) {
		this.name = name;
		this.cards = new ArrayList<Card>();
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(String strColor) { 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim().toUpperCase());     
			this.color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
	}
	
	public void addCard(Card c) {
		cards.add(c);
	}
	
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	
	/*public Card disproveSuggestion(String person, String room, String weapon) {
		
	}*/
}
