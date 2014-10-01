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
		ClueGame game = new ClueGame("ClueLayoutOurs.csv", "ClueLegendours.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}

	@Test 
	public void testDoorWays(){

		//Tests doors headed down
		LinkedList<BoardCell> testList = board.getAdjList(8, 2);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(8, 3)));

		testList = board.getAdjList(0, 7);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(0, 8)));

		testList = board.getAdjList(5, 11);
		Assert.assertEquals(1, testList.size());
		Assert.assertTrue(testList.contains(board.getCellAt(5, 12)));

	}

	@Test
	public void adjDoorWay(){


		LinkedList<BoardCell> testList = board.getAdjList(5,12);
		Assert.assertTrue(testList.contains(board.getCellAt(5, 11)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 13)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 12)));
		Assert.assertTrue(testList.contains(board.getCellAt(6, 12)));
		Assert.assertEquals(4, testList.size());

		testList = board.getAdjList(17, 5);
		Assert.assertTrue(testList.contains(board.getCellAt(17, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(16, 5)));
		Assert.assertEquals(2, testList.size());

		testList = board.getAdjList(16, 4);
		Assert.assertTrue(testList.contains(board.getCellAt(16, 3)));
		Assert.assertTrue(testList.contains(board.getCellAt(16, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 4)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 6)));
		Assert.assertEquals(4, testList.size());

		testList = board.getAdjList(13, 11);
		Assert.assertTrue(testList.contains(board.getCellAt(13, 10)));
		Assert.assertTrue(testList.contains(board.getCellAt(13, 12)));
		Assert.assertTrue(testList.contains(board.getCellAt(12, 11)));
		Assert.assertTrue(testList.contains(board.getCellAt(14, 11)));
		Assert.assertEquals(4, testList.size());

	}

	@Test 
	public void testWalkway(){
		LinkedList<BoardCell> testList = board.getAdjList(16, 7);
		Assert.assertTrue(testList.contains(board.getCellAt(16, 8)));
		Assert.assertTrue(testList.contains(board.getCellAt(16, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 7)));
		Assert.assertTrue(testList.contains(board.getCellAt(17, 7)));
		Assert.assertEquals(4, testList.size());
	}

	@Test
	public void testEdges(){
		LinkedList<BoardCell> testList = board.getAdjList(0, 6);
		Assert.assertTrue(testList.contains(board.getCellAt(0, 7)));
		Assert.assertTrue(testList.contains(board.getCellAt(1, 6)));
		Assert.assertEquals(2, testList.size());

		testList = board.getAdjList(22, 8);
		Assert.assertTrue(testList.contains(board.getCellAt(21, 8)));
		Assert.assertEquals(1, testList.size());

		testList = board.getAdjList(9, 0);
		Assert.assertTrue(testList.contains(board.getCellAt(9, 1)));
		Assert.assertEquals(1, testList.size());

		testList = board.getAdjList(9, 21);
		Assert.assertTrue(testList.contains(board.getCellAt(9, 20)));
		Assert.assertEquals(1, testList.size());
	}

	@Test 
	public void notDoorway(){
		LinkedList<BoardCell> testList = board.getAdjList(0, 6);
		Assert.assertTrue(testList.contains(board.getCellAt(0, 7)));
		Assert.assertTrue(testList.contains(board.getCellAt(1, 6)));
		Assert.assertEquals(2, testList.size());

		testList = board.getAdjList(21, 8);
		Assert.assertTrue(testList.contains(board.getCellAt(22, 8)));
		Assert.assertTrue(testList.contains(board.getCellAt(20, 8)));
		Assert.assertEquals(2, testList.size());

	}
	
	@Test
	public void targetWalkway(){
		
		board.calcTargets(20, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(18, 8)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 7)));
		
		board.calcTargets(21, 16, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(19, 16)));
		Assert.assertTrue(targets.contains(board.getCellAt(22, 15)));	
		Assert.assertTrue(targets.contains(board.getCellAt(21, 14)));
		
		board.calcTargets(0, 18, 1);
		targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(1, 18)));
		
		board.calcTargets(9, 18, 3);
		targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(10, 16)));
		Assert.assertTrue(targets.contains(board.getCellAt(9, 15)));	
		Assert.assertTrue(targets.contains(board.getCellAt(9, 21)));
		
		
	}
	
	@Test
	public void targetEnterRoom(){
		board.calcTargets(6, 18, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(6, 17)));
		Assert.assertTrue(targets.contains(board.getCellAt(19, 19)));
		
		board.calcTargets(22, 8, 3);
		targets= board.getTargets();
		Assert.assertEquals(5, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(20, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(20, 7)));
		Assert.assertTrue(targets.contains(board.getCellAt(21, 8)));
		Assert.assertTrue(targets.contains(board.getCellAt(22, 9)));
		
	}
	
	@Test public void targetLeaveRoom(){
		board.calcTargets(22, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(22, 8)));
		
		board.calcTargets(17, 12, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(16, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 12)));
		Assert.assertTrue(targets.contains(board.getCellAt(16, 13)));

	}



}
