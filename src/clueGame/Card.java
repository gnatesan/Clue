package clueGame;

public class Card {
	public enum CardType {
		ROOM, WEAPON, PERSON
	};
	
	private String name;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.name = name;
		this.type = type;
	}
		
	public String getName() {
		return this.name;
	}
	
	public CardType getType() {
		return this.type;
	}
	
	public boolean equals(Card c) {
		if (this.name.equals(c.getName()) && this.type.equals(c.getType()))
				return true;
		return false;
	}
}
