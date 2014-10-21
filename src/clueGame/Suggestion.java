package clueGame;

public class Suggestion {
	
	private String person;
	private String room;
	private String weapon;
	
	public Suggestion(String person, String weapon, String room) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	boolean equals(Suggestion s) {
		if (this.room.equals(s.room) && this.weapon.equals(s.weapon) && this.person.equals(s.person))
			return true;
		return false;
	}
	
}
