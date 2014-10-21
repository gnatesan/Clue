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
	
	public String getPerson() {
		return this.person;
	}
	
	public String getRoom() {
		return this.room;
	}
	
	public String getWeapon() {
		return this.weapon;
	}
	
	@Override
	public boolean equals(Object s) {
		if (this.room.equalsIgnoreCase(((Suggestion)s).getRoom()) 
				&& this.weapon.equalsIgnoreCase(((Suggestion)s).getWeapon()) 
				&& this.person.equalsIgnoreCase(((Suggestion)s).getPerson())) {
			return true;
		}
		return false;
	}
	
}
