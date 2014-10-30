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
	
	public void getDeck(ArrayList<Card> deck){
		for(Card c: deck){
			//System.out.println(c.getName());
			switch(c.getType()){
			case PERSON:
				personOptions.add(c.getName());
				//System.out.println(c.getName());
				break;
			case ROOM:
				roomOptions.add(c.getName());
				break;
			case WEAPON:
				weaponOptions.add(c.getName());
				break;
			}
		}
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

	public Suggestion createSuggestion(String currentRoom) {
		

		Solution s = new Solution("", "", " ");
		//weapons and players only contain unseen cards, randomly choose a card from each to be part of your suggestion
		Random rand = new Random();
		int r1 = rand.nextInt(personOptions.size());
		int r2 = rand.nextInt(weaponOptions.size());

		person = personOptions.get(r1);
		weapon = weaponOptions.get(r2);

		return new Suggestion(person, weapon, currentRoom);
		// Pick suggestion from lists
		/*Random rand = new Random();
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
		*/
	}
	
	// Setter only for debugging purposes
	public void setLastRoomVisited(char room) {
		this.lastRoomVisited = room;
	}
	
	public void updateSeen(Card shown) {
		//this updates the cards seen and also removes cards
		//
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
	
	public ArrayList<Card> getCardsSeen(){
		return seen;
	}
}
