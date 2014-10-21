package clueGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private String weapon;
	private String person;
	private ClueGame game;
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

	public Suggestion createSuggestion(String currentRoom) {
		for (int i = 0; i < game.getCards().size(); i++) {
			if (!seen.contains(game.getCards().get(i))) {
				if (game.getCards().get(i).getType().equals(Card.CardType.PERSON))
						person = game.getCards().get(i).getName();
				else if (game.getCards().get(i).getType().equals(Card.CardType.WEAPON))
						weapon = game.getCards().get(i).getName();
			}
		}
		return new Suggestion(person, weapon, currentRoom);
	}
	
	// Setter only for debugging purposes
	public void setLastRoomVisited(char room) {
		this.lastRoomVisited = room;
	}
	
	public void updateSeen(Card showed) {
		seen.add(showed);
	}
}
