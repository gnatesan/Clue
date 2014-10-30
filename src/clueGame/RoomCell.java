package clueGame;

import java.awt.Color;
import java.awt.Font;
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
		
		if (roomInitial == 'X') {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GRAY);
		}
		
		g.fillRect(this.getColumn() * CELL_SIZE, this.getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		
		if (doorway) {
			g.setColor(Color.BLUE);
			
			switch (doorDirection) {
			case UP:
				g.fillRect(getColumn() * CELL_SIZE, getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE / 4);
				break;
			case DOWN:
				g.fillRect(getColumn() * CELL_SIZE, getRow() * CELL_SIZE + 3 * (CELL_SIZE / 4), CELL_SIZE, CELL_SIZE / 4);
				break;
			case LEFT:
				g.fillRect(getColumn() * CELL_SIZE, getRow() * CELL_SIZE, CELL_SIZE / 4, CELL_SIZE);
				break;
			case RIGHT:
				g.fillRect(getColumn() * CELL_SIZE + 3 * (CELL_SIZE / 4), getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				break;
			case NONE:
				break;
			}
		}
	}
	
	public void drawName(Graphics g, Board board) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", Font.PLAIN, 12));
		if (board.getCenters().contains(this)) { //If this RoomCell is a center label			
			g.drawString(board.getRooms().get(roomInitial).toUpperCase(), getColumn() * CELL_SIZE, getRow() * CELL_SIZE);
		}
	}
	
	public char getInitial() {
		return roomInitial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	
	
	

}
