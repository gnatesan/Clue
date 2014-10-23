package clueGame;

import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(int r, int c, char i, char j ) {
		super(r, c);
		roomInitial = i;		
		switch (j){
			case 'U':
				doorDirection = DoorDirection.UP;
				doorway = true;
				break;
				
			case 'D':
				doorDirection = DoorDirection.DOWN;
				doorway = true;
				break;
				
			case 'L':
				doorDirection = DoorDirection.LEFT;
				doorway = true;
				break;
				
			case 'R':
				doorDirection = DoorDirection.RIGHT;
				doorway = true;
				break;
				
			default:
				doorDirection = DoorDirection.NONE;
				doorway = false;
				break;
		}
		
	}

	@Override
	public boolean isRoom(){
		return true;
	}
	
	
	
	@Override
	public void draw(Graphics g) {
		
	}
	public char getInitial() {
		return roomInitial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	
	
	

}
