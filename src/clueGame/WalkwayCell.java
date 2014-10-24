package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {

	public WalkwayCell(int r, int c, char i) {
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
		g.fillRect(getRow() * CELL_SIZE, getColumn() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(getRow() * CELL_SIZE,  getColumn() * CELL_SIZE,  CELL_SIZE, CELL_SIZE);
	}

}
