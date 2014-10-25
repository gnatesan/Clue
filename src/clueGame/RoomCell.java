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
	public void draw(Graphics g, Board board) {
		if (isRoom()) {
			if (doorway) {
				g.setColor(Color.BLUE);
			}
			else {
				g.setColor(Color.GRAY);
				if (roomInitial == 'X') {
					g.setColor(Color.RED);
				}
			}
			g.fillRect(this.getColumn()*20, this.getRow()*20, 20, 20);
		}
	}
	
	public void drawName(Graphics g, Board board) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		if (board.getCenters().contains(this)) { //If this RoomCell is a center label
			for (Character c : board.getRooms().keySet()) {
				String s = board.getRooms().get(c);
				g.drawString(s, this.getColumn()*20, this.getRow()*20); //draw the label starting from that cell
				//board.getRooms().remove(c);
				//break;
			}
		}
	}
	
	public char getInitial() {
		return roomInitial;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	
	
	

}
