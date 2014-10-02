package clueTests;

import static org.junit.Assert.*;

import java.util.*;
import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;
public class AdjTest {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("ClueLayoutOurs.csv", "ClueLegendOurs.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	@Test 
	public void testDoorWays(){

		//Tests Right
		LinkedList<BoardCell> testList = board.getAdjList(2, 8);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(2, 9)));

		//Tests Up
		testList = board.getAdjList(7, 0);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(6, 0)));

		//Tests Down
		testList = board.getAdjList(11, 5);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(12, 5)));

	}

	@Test
	public void adjDoorWay(){


		LinkedList<BoardCell> testList = board.getAdjList(12, 5);
		Assert.assertTrue(testList.contains(board.getCellAt(11, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(13, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 4)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 6)));
		Assert.assertEquals(4, testList.size());

		testList = board.getAdjList(5, 17);
		Assert.assertTrue(testList.contains(board.getCellAt(6, 17)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 16)));
		Assert.assertEquals(2, testList.size());

		testList = board.getAdjList(4, 16);
		Assert.assertTrue(testList.contains(board.getCellAt(3, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 17)));
		Assert.assertEquals(4, testList.size());

		testList = board.getAdjList(12, 16);
		Assert.assertTrue(testList.contains(board.getCellAt(13, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(11, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 17)));
		Assert.assertEquals(4, testList.size());

	}

	@Test 
	public void testWalkway(){
		LinkedList<BoardCell> testList = board.getAdjList(7, 16);
		Assert.assertTrue(testList.contains(board.getCellAt(8, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(6, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 17)));
		Assert.assertEquals(4, testList.size());
	}

	@Test
	public void testEdges(){
		LinkedList<BoardCell> testList = board.getAdjList(6, 0);
		Assert.assertTrue(testList.contains(board.getCellAt(5, 0)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 0)));
		Assert.assertTrue(testList.contains(board.getCellAt(6, 1)));
		Assert.assertEquals(3, testList.size());

		testList = board.getAdjList(8, 22);
		Assert.assertTrue(testList.contains(board.getCellAt(8, 21)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 22)));
		Assert.assertEquals(2, testList.size());

		testList = board.getAdjList(0, 9);
		Assert.assertTrue(testList.contains(board.getCellAt(1, 9)));
		Assert.assertTrue(testList.contains(board.getCellAt(0, 10)));
		Assert.assertEquals(2, testList.size());

		testList = board.getAdjList(21, 9);
		Assert.assertTrue(testList.contains(board.getCellAt(20, 9)));
		Assert.assertTrue(testList.contains(board.getCellAt(21, 8)));
		Assert.assertEquals(2, testList.size());
	}

	@Test 
	public void notDoorway(){
		LinkedList<BoardCell> testList = board.getAdjList(5, 0);
		Assert.assertTrue(testList.contains(board.getCellAt(6, 0)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 1)));
		Assert.assertEquals(2, testList.size());

		testList = board.getAdjList(8, 21);
		Assert.assertTrue(testList.contains(board.getCellAt(8, 22)));
		Assert.assertTrue(testList.contains(board.getCellAt(8, 20)));
		Assert.assertEquals(2, testList.size());

	}
	
	@Test
	public void targetWalkway(){
		
		board.calcTargets(8, 20, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(8, 18)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 19)));
		Assert.assertTrue(targets.contains(board.getCellAt(8, 22)));
		Assert.assertTrue(targets.contains(board.getCellAt(9, 20)));
		
		board.calcTargets(16, 21, 2);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(16, 19)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 22)));	
		Assert.assertTrue(targets.contains(board.getCellAt(14, 21)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 20)));
		
		board.calcTargets(18, 0, 1);
		targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(18, 1)));
		
		board.calcTargets(18, 9, 3);
		targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(16, 10)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 9)));	
		Assert.assertTrue(targets.contains(board.getCellAt(21, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 10)));
		Assert.assertTrue(targets.contains(board.getCellAt(18, 6)));
		
		
	}
	
	@Test
	public void targetEnterRoom(){
		board.calcTargets(18, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(17, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(18, 5)));
		Assert.assertTrue(targets.contains(board.getCellAt(18, 7)));
		
		board.calcTargets(16, 18, 3);
		targets= board.getTargets();
		Assert.assertEquals(11, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(15, 18)));//
		Assert.assertTrue(targets.contains(board.getCellAt(17, 20)));//
		Assert.assertTrue(targets.contains(board.getCellAt(18, 19)));//
		Assert.assertTrue(targets.contains(board.getCellAt(18, 17)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 16)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 16)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 17)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 21)));//
		Assert.assertTrue(targets.contains(board.getCellAt(17, 18)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 19)));
		
	}
	
	@Test public void targetLeaveRoom(){
		board.calcTargets(17, 20, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(16, 20)));
		
		board.calcTargets(12, 17, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(11, 16)));
		Assert.assertTrue(targets.contains(board.getCellAt(12, 15)));
		Assert.assertTrue(targets.contains(board.getCellAt(13, 16)));

	}



}
