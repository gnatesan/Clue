package clueGame;

import java.awt.Graphics;

public abstract class BoardCell {
	private int row;
	private int column;
	protected static final int CELL_SIZE = 25;

	protected boolean doorway = false;

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public boolean isWalkway() {
		return false;
	}

	public boolean isDoorway() {
		return doorway;
	}

	public boolean isRoom() {
		return false;
	}

	public int getRow() {
		int x = row;
		return x;

	}

	public int getColumn() {
		int x = column;
		return x;
	}

	// Add this later:
	public abstract void draw(Graphics g);

}
