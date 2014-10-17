package clueGame;

public class Player {
	private String name;
	private String color;
	private int row;
	private int col;
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Player(String name) {
		this.name = name;
	}
	
	/*public Card disproveSuggestion(String person, String room, String weapon) {
		
	}*/
}
