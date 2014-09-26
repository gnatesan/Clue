package GameBoard;

public abstract class BoardCell {
	private int row;
	private int column;
	
	public boolean isWalkway(){
		return false;
	}
	public boolean isDoorway(){
		return false;
	}
	public boolean isRoom(){
		return false;
	}
	
	 //Add this later:
	 public abstract void draw();
	 

}
