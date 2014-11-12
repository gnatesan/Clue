package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

public class CardPanel extends JPanel{
	private ArrayList<Card> cards;
	
	public CardPanel(ArrayList<Card> c){
		this.cards = c;
		GridLayout g = new GridLayout(3, 0);
		setLayout(g);
		add(generatePersonCard());
		add(generateRoomCard());
		add(generateWeaponCard());
	}
	
	public JPanel generatePersonCard() {
		JPanel person = new JPanel();
		person.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		
		ArrayList<JTextField> peopleCards = new ArrayList<JTextField>();
		for (Card c : cards) {
			if (c.getType() == CardType.PERSON) {
				peopleCards.add(new JTextField(c.getName()));
			}
		}
		for(JTextField j: peopleCards){
			person.add(j);
			j.setEditable(false);
		}
		
		person.setLayout(new GridLayout(peopleCards.size(), 1));

		return person;
	}
	
	public JPanel generateRoomCard() {
		JPanel room = new JPanel();
		
		room.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		

		ArrayList<JTextField> roomCards = new ArrayList<JTextField>();
		for (Card c : cards) {
			if (c.getType() == CardType.ROOM) {
				roomCards.add(new JTextField(c.getName()));
			}
		}
		for(JTextField j: roomCards){
			room.add(j);
			j.setEditable(false);
		}
		room.setLayout(new GridLayout(roomCards.size(), 1));

		
		return room;
	}
	public JPanel generateWeaponCard() {
		JPanel weapon = new JPanel();
		
		weapon.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
		
		ArrayList<JTextField> weaponCards = new ArrayList<JTextField>();
		for (Card c : cards) {
			if (c.getType() == CardType.WEAPON) {
				weaponCards.add(new JTextField(c.getName()));
			}
		}
		for(JTextField j: weaponCards){
			weapon.add(j);
			j.setEditable(false);
		}
		weapon.setLayout(new GridLayout(weaponCards.size(), 1));
		
		return weapon;
	}
}
