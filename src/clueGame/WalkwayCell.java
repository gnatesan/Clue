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
		// Draw the walkway cell
		g.setColor(Color.YELLOW);
		g.drawRect(getRow(), getColumn(), 5, 5);
		g.setColor(Color.BLACK);
		g.drawRect(getRow(), getColumn(), 5, 5);
	}

}
