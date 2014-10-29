package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {

	public WalkwayCell(int r, int c) {
		super(r, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isWalkway() {
		return true;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(getColumn() * CELL_SIZE, getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(getColumn() * CELL_SIZE,  getRow() * CELL_SIZE,  CELL_SIZE, CELL_SIZE);	
	}
	
	public void drawName(Graphics g, Board b) {
		
	}

}
