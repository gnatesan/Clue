package GameBoard;

public class RoomCell extends BoardCell {
	enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE};
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
		// TODO Auto-generated method stub
		
	}
	public char getRoomInital() {
		return roomInital;
	}
	
	
	

}
