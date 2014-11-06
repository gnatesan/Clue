package clueGame;

import java.util.Random;

import javax.swing.JOptionPane;

public class HumanPlayer extends Player{
	private final int MAX_NUM_ON_DIE = 6;

	public HumanPlayer(String name) {
		super(name);
	}


	public void showMove(Board b, int roll){
		//calls method in Board to highlight targets
		//when we call calcTargets for human player, highlight them
		//calculate possible targets
		b.calcTargets(this.getRow(), this.getCol(), roll);
		
		//highlight the targets
		b.repaint();
	}
	
	public void makeMove(Board b, BoardCell c){
		//when clicked, select target and update row and column
		//update players row and column
		this.setRow(c.getRow());
		this.setCol(c.getColumn());
		//repaint board
		b.repaint();
		//handle suggestion and accusation(next part)
	}

}
