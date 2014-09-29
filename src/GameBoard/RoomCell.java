package GameBoard;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE};
	private DoorDirection doorDirection;
	private char roomInital;
	
	public RoomCell(int r, int c, char i, char j) {
		super(r, c);
		roomInital = i;
		if (j == 'U'){
			doorDirection = DoorDirection.UP;
		}
		else if (j == 'D'){
			doorDirection = DoorDirection.UP;
		}
		else if (j == 'L'){
			doorDirection = DoorDirection.UP;
		}
		else if (j == 'R'){
			doorDirection = DoorDirection.UP;
		}
		else {
			doorDirection = DoorDirection.UP;
		}
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
		return DoorDirection.;
	}
	
	
	
	

}
