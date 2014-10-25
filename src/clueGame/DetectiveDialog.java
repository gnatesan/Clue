package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class DetectiveDialog extends JDialog {
	ArrayList<Card> cards;
	
	public DetectiveDialog(ArrayList<Card> cards) {
		this.cards = cards;
		setUpGui();
	}
	
	public void setUpGui() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setLayout(new GridLayout(3, 2));
		add(generatePeopleCheckboxes());
		add(generatePersonGuess());
		add(generateRoomCheckboxes());
		add(generateRoomGuess());
		add(generateWeaponCheckboxes());
		add(generateWeaponGuess());
		
		pack();
	}
	
	public JPanel generatePeopleCheckboxes() {
		JPanel people = new JPanel();
		
		people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		people.setLayout(new GridLayout(3, 2));
		for (Card c : cards) {
			if (c.getType() == CardType.PERSON) {
				people.add(new JCheckBox(c.getName()));
			}
		}
		
		return people;
	}
	
	public JPanel generatePersonGuess() {
		JPanel person = new JPanel();
		
		person.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		
		JComboBox options = new JComboBox();
		
		for (Card c : cards) {
			if (c.getType() == CardType.PERSON) {
				options.addItem(c.getName());
			}
		}
		
		person.add(options);
		
		return person;
	}
	
	public JPanel generateRoomCheckboxes() {
		JPanel room = new JPanel();
		
		room.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		room.setLayout(new GridLayout(0, 2));
		for (Card c : cards) {
			if (c.getType() == CardType.ROOM) {
				room.add(new JCheckBox(c.getName()));
			}
		}
		
		return room;
	}
	
	public JPanel generateRoomGuess() {
		JPanel rooms = new JPanel();
		
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		
		JComboBox roomGuesses = new JComboBox();
		
		for (Card c : cards) {
			if (c.getType() == CardType.ROOM){ 
				roomGuesses.addItem(c.getName());
			}
		}
		
		rooms.add(roomGuesses);
		
		return rooms;
	}
	
	public JPanel generateWeaponCheckboxes() {
		JPanel weapon = new JPanel();
		
		weapon.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		weapon.setLayout(new GridLayout(3, 2));
		for (Card c : cards) {
			if (c.getType() == CardType.WEAPON) {
				weapon.add(new JCheckBox(c.getName()));
			}
		}
		
		return weapon;
	}
	
	public JPanel generateWeaponGuess() {
		JPanel weapons = new JPanel();

		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));

		JComboBox weaponGuesses = new JComboBox();

		for (Card c : cards) {
			if (c.getType() == CardType.WEAPON){ 
				weaponGuesses.addItem(c.getName());
			}
		}
		
		weapons.add(weaponGuesses);

		return weapons;
	}
	
}
