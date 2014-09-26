package GameBoard;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection;
	private char roomInital;
	
	public RoomCell() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isRoom(){
		return true;
	}
	
	@Override
	public void draw() {
		
	}
	public char getRoomInitial() {
		return roomInital;
	}

	public DoorDirection getDoorDirection() {
		return DoorDirection.UP;
	}
	
	

}
