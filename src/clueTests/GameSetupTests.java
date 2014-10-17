package clueTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ClueGame;

public class GameSetupTests {
	
	private static Board board;

	@BeforeClass
	public void setUp() {
		ClueGame game = new ClueGame("ClueLayoutOurs.csv", "ClueLegendOurs.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	
	

}
