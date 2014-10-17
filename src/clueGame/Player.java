package clueGame;

import java.util.ArrayList;

public class Player {
	private String name;
	private String color;
	private int row;
	private int col;
	private ArrayList<Card> cards;
	
	public Player() {
		this.cards = new ArrayList<Card>(3);
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Player(String name) {
		this.name = name;
	}
	
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	
	/*public Card disproveSuggestion(String person, String room, String weapon) {
		
	}*/
}
