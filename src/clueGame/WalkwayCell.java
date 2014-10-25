package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell {


	
	public WalkwayCell(int r, int c, char i) {
		super(r, c);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isWalkway(){
		return true;
	}

	@Override
	public void draw(Graphics g, Board b) {
		if (isWalkway()) {
			g.setColor(Color.YELLOW);
			g.fillRect(this.getColumn()*20, this.getRow()*20, 20, 20);
		}	
	}
	
	public void drawName(Graphics g, Board b) {
		
	}

}
