package clueGame;

import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	
	public ComputerPlayer(String name) {
		super(name);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return new WalkwayCell(1,1, 'W');
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
