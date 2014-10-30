package clueGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private String weapon;
	private String person;
	ArrayList <Card> seen = new ArrayList <Card>();
	ArrayList<String> personOptions;
	ArrayList<String> weaponOptions;
	ArrayList<String> roomOptions;
	
	public ComputerPlayer(String name) {
		super(name);
		roomOptions = new ArrayList<String>();
		weaponOptions = new ArrayList<String>();
		personOptions = new ArrayList<String>();
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {		
		LinkedList<BoardCell> boardList = new LinkedList<BoardCell>();
		
		for (BoardCell cell : targets) {
			if (cell.isDoorway()) {
				if (((RoomCell)cell).getInitial() != lastRoomVisited) {
					return cell;
				}
				boardList.add(cell);
			} else {
				boardList.add(cell);
			}
		}
		
		Random rand = new Random();
		return boardList.get(rand.nextInt(boardList.size()));
	}

	public ArrayList<Card> getSeen() {
		return seen;
	}

	public Suggestion createSuggestion(String currentRoom, ArrayList<Card> cards) {
		
		// Pick suggestion from lists
		Random rand = new Random();
		if (personOptions.size() > 1) {
			person = personOptions.get(rand.nextInt(personOptions.size()));
		} else {
			person = personOptions.get(0);
		}
		
		if (weaponOptions.size() > 1) {
			weapon = weaponOptions.get(rand.nextInt(weaponOptions.size()));
		} else {
			weapon = weaponOptions.get(0);
		}
		
		return new Suggestion(person, weapon, currentRoom);
	}
	
	// Setter only for debugging purposes
	public void setLastRoomVisited(char room) {
		this.lastRoomVisited = room;
	}
	
	public void updateSeen(Card shown) {
		seen.add(shown);
		switch(shown.getType()){
		case PERSON:
			personOptions.remove(shown.getName());
			break;
		case ROOM:
			roomOptions.remove(shown.getName());
			break;
		case WEAPON:
			weaponOptions.remove(shown.getName());
			break;
		}
	}
}
