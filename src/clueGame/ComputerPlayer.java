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
	
	public ComputerPlayer(String name) {
		super(name);
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
		ArrayList<String> personOptions = new ArrayList<String>();
		ArrayList<String> weaponOptions = new ArrayList<String>();
		
		for (int i = 0; i < cards.size(); i++) {
			if (!seen.contains(cards.get(i))) {
				if (cards.get(i).getType() == Card.CardType.PERSON) {
						personOptions.add(cards.get(i).getName());
				}
				else if (cards.get(i).getType() == Card.CardType.WEAPON) {
						weaponOptions.add(cards.get(i).getName());
				}
			}
		}
		
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
	}
}
