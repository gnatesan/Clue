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
}
