package clueGame;

import clueGame.Card.CardType;

public class Suggestion {
	
	private Card person;
	private Card room;
	private Card weapon;
	
	public Suggestion(String person, String weapon, String room) {
		this.person = new Card(person, CardType.PERSON);
		this.room = new Card(room, CardType.ROOM);
		this.weapon = new Card(weapon, CardType.WEAPON);
	}
	
	public Card getPerson() {
		return this.person;
	}
	
	public Card getRoom() {
		return this.room;
	}
	
	public Card getWeapon() {
		return this.weapon;
	}
	
	@Override
	public boolean equals(Object s) {
		if (this.room.equals(((Suggestion)s).getRoom()) 
				&& this.weapon.equals(((Suggestion)s).getWeapon()) 
				&& this.person.equals(((Suggestion)s).getPerson())) {
			return true;
		}
		return false;
	}
	
}
