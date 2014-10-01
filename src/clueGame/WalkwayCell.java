package clueGame;

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
	public void draw() {
		// TODO Auto-generated method stub

	}

}
