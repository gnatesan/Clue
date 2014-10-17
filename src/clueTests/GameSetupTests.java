package clueTests;

import org.junit.Assert;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class GameSetupTests {
	
	private static ClueGame game;
	
	@BeforeClass
	public static void setUp() throws FileNotFoundException, BadConfigFormatException{
		game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadPlayerConfig();
	}
	
	@Test
	public void testLoadPlayerConfig() {
		ArrayList <Player> test = game.getPlayers();
		//Test human player
		Assert.assertEquals(test.get(0).getName(), "John");
		Assert.assertEquals(test.get(0).getColor(), "Blue");
		Assert.assertEquals(test.get(0).getRow(), 21);
		Assert.assertEquals(test.get(0).getCol(), 9);
		
		//Test second to last computer player
		Assert.assertEquals(test.get(4).getName(), "Dave");
		Assert.assertEquals(test.get(4).getColor(), "Brown");
		Assert.assertEquals(test.get(4).getRow(), 18);
		Assert.assertEquals(test.get(4).getCol(), 4);
		
		//Test last computer player
		Assert.assertEquals(test.get(5).getName(), "Emily");
		Assert.assertEquals(test.get(5).getColor(), "Purple");
		Assert.assertEquals(test.get(5).getRow(), 9);
		Assert.assertEquals(test.get(5).getCol(), 1);
		
	}

}
