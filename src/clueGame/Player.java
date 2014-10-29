package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.lang.reflect.Field;

public class Player {
	private String name;
	private Color color;
	private int row;
	private int col;
	private ArrayList<Card> cards;
	private static final int PLAYER_RADIUS = 25;

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

	public void draw(Graphics g) {
		g.setColor(getColor());
		g.fillOval(getRow() * PLAYER_RADIUS, getCol() * PLAYER_RADIUS, PLAYER_RADIUS, PLAYER_RADIUS);
	}

	public Card disproveSuggestion(String room, String weapon, String person) {
		ArrayList<Card> choices = new ArrayList<Card>();
		for (int j = 0; j < this.cards.size(); j++) {
			if (this.getCards().get(j).getName().equals(room))
				choices.add(this.cards.get(j));
			else if (this.cards.get(j).getName().equals(weapon))
				choices.add(this.cards.get(j));
			else if (this.cards.get(j).getName().equals(person))
				choices.add(this.cards.get(j));
		}
		if (choices.size() == 0) 
			return null;
		Random r = new Random();
		int test = r.nextInt(choices.size());
		return choices.get(test);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Player && this.name.equals(((Player) o).getName()))
			return true;
		return false;
	}

}
