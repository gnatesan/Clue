package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	private String weapon;
	private String person;
	private String room;
	private ArrayList <Card> seen = new ArrayList <Card>();
	private ArrayList<String> personOptions;
	private ArrayList<String> weaponOptions;
	private ArrayList<String> roomOptions;
	private boolean makeAccusation;
	private Map<Character, String> rooms;

	public ComputerPlayer(String name) {
		super(name);
		roomOptions = new ArrayList<String>();
		weaponOptions = new ArrayList<String>();
		personOptions = new ArrayList<String>();
		//sets the lastRoomVisited to Z so we know that they haven't
		//visited any rooms
		lastRoomVisited = 'Z';
		makeAccusation = false;		
	}


	public boolean getMakeAccusation(){
		return makeAccusation;
	}

	public void setMakeAccusation(boolean tf){
		makeAccusation = tf;
	}

	public void getDeck(ArrayList<Card> deck){
		for(Card c: deck){
			switch(c.getType()){
			case PERSON:
				personOptions.add(c.getName());
				break;
			case ROOM:
				roomOptions.add(c.getName());
				break;
			case WEAPON:
				weaponOptions.add(c.getName());
				break;
			}
		}
	}


	public BoardCell pickLocation(Set<BoardCell> targets) {		
		LinkedList<BoardCell> boardList = new LinkedList<BoardCell>();
		LinkedList<BoardCell> doorList = new LinkedList<BoardCell>();
		for(BoardCell c: targets){
			if(c.isDoorway() && ((RoomCell)c).getInitial()!=lastRoomVisited){
				doorList.add(c);
			}
		}
		Random rand = new Random();
		if(!doorList.isEmpty()){
			int randNum = rand.nextInt(doorList.size());
			setLastRoomVisited(((RoomCell)doorList.get(randNum)).getInitial());
			return doorList.get(randNum);
		}

		for (BoardCell cell : targets) {
			boardList.add(cell);
		}
		return boardList.get(rand.nextInt(boardList.size()));
	}

	public void makeMove(){

	}

	public ArrayList<Card> getSeen() {
		return seen;
	}

	public Suggestion createSuggestion(String currentRoom) {


		//weapons and players only contain unseen cards, randomly choose a card from each to be part of your suggestion
		Random rand = new Random();
		int r1 = rand.nextInt(personOptions.size());
		int r2 = rand.nextInt(weaponOptions.size());

		person = personOptions.get(r1);
		weapon = weaponOptions.get(r2);

		return new Suggestion(person, weapon, currentRoom);
	}

	public Solution createAccusation() {
		//weapons and players only contain unseen cards, randomly choose a card from each to be part of your suggestion
		Random rand = new Random();
		int r1 = rand.nextInt(personOptions.size());
		int r2 = rand.nextInt(weaponOptions.size());
		int r3 = rand.nextInt(roomOptions.size());

		person = personOptions.get(r1);
		weapon = weaponOptions.get(r2);
		room = roomOptions.get(r3);

		return new Solution(person, weapon, room);
	}

	// Setter only for debugging purposes
	public void setLastRoomVisited(char room) {
		this.lastRoomVisited = room;
	}

	public void updateSeen(Card shown) {
		//this updates the cards seen and also removes cards
		//from the options lists
		seen.add(shown);
		switch(shown.getType()){
		case PERSON:
			personOptions.remove(shown.getName());
			break;
		case ROOM:
			roomOptions.remove(shown.getName());
			break;
		case WEAPON:
			weaponOptions.remove(shown.getName());
			break;
		}
	}

	public ArrayList<Card> getCardsSeen(){
		return seen;
	}
}
