package clueGame;

import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	
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

	public void createSuggestion() {
		
	}
	
	// Setter only for debugging purposes
	public void setLastRoomVisited(char room) {
		this.lastRoomVisited = room;
	}
	
	/*public void updateSeen(Card seen) {
		
	}*/
}
