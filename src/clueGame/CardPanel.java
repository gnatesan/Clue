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
		JTextField p = new JTextField("");
		for (Card c : cards) {
			if (c.getType() == CardType.PERSON) {
				p = new JTextField(c.getName());
				break;
			}
		}
		person.add(p);
		return person;
	}
	public JPanel generateRoomCard() {
		JPanel room = new JPanel();
		
		room.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		JTextField r = new JTextField("");
		for (Card c : cards) {
			if (c.getType() == CardType.ROOM) {
				r = new JTextField(c.getName());
				break;
			}
		}
		
		room.add(r);
		
		return room;
	}
	public JPanel generateWeaponCard() {
		JPanel weapon = new JPanel();
		
		weapon.setBorder(new TitledBorder(new EtchedBorder(), "Weapon"));
		JTextField w = new JTextField("");
		for (Card c : cards) {
			if (c.getType() == CardType.WEAPON) {
				w = new JTextField(c.getName());
				break;
			}
		}
		
		weapon.add(w);
		
		return weapon;
	}
}
