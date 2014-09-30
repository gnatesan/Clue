package GameBoard;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(int r, int c, char i, char j) {
		
		super(r, c);
		roomInitial = i;
		super.doorway = true;
		System.out.println(roomInitial);
		System.out.println("blargh" + j);
		
		switch (j){
			case 'U':
				doorDirection = DoorDirection.UP;
				break;
				
			case 'D':
				doorDirection = DoorDirection.DOWN;
				break;
				
			case 'L':
				doorDirection = DoorDirection.LEFT;
				break;
				
			case 'R':
				doorDirection = DoorDirection.RIGHT;
				break;
				
			default:
				doorDirection = DoorDirection.NONE;
				break;
		}
		
	}

	@Override
	public boolean isRoom(){
		return true;
	}
	
	
	@Override
	public void draw() {
		
	}
	public char getRoomInitial() {
		return roomInitial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	
	
	

}
