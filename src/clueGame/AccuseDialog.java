package clueGame;


//maybe change getCards to getSeen cards????????
///look at this comment

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;
public class AccuseDialog extends JDialog{

	private Player p;
	private Board b;
	private JComboBox<String> room, person, weapon;
	private ClueGame game;
	private ArrayList<Card> gameCards;


	public AccuseDialog(Player p, ClueGame game) {
		this.p = p;
		this.b = game.getBoard();
		this.game = game;
		gameCards = game.getCards();
		setUpGui();
	}

	public void setUpGui() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setLayout(new GridLayout(4, 2));
		JPanel ro = generateRoomGuess();
		add(ro);
		JPanel pers = generatePersonGuess();
		add(pers);
		JPanel weap = generateWeaponGuess();
		add(weap);
		JButton sub = new JButton("Submit");
		sub.addActionListener(new ActionListener() {
			//this implements the interface, which allows us to do this because
			//ActionListener is an interface
			public void actionPerformed(ActionEvent e)
			{
				//make a solution based on their selections
				String pers = person.getSelectedItem().toString();
				Solution sol = new Solution(((String)person.getSelectedItem()), ((String)weapon.getSelectedItem()), ((String)room.getSelectedItem()));
				
				//test accusation
				//if correct, display message
				if(game.checkAccusation(sol)){
					JOptionPane.showMessageDialog(null,"You win!", "You guessed correctly.", JOptionPane.INFORMATION_MESSAGE);
					//exit if you win
					System.exit(0);
				}
				else JOptionPane.showMessageDialog(null,"Continue play", "You guessed incorrectly.", JOptionPane.INFORMATION_MESSAGE);
				 
				setVisible(false);
			}

		});
		add(sub);
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			//this implements the interface, which allows us to do this because
			//ActionListener is an interface
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}

		});
		add(cancel);
		setVisible(true);
		pack();
	}
	
	public JPanel generatePersonGuess() {
		JPanel per = new JPanel();

		//people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		per.setLayout(new GridLayout(1, 2));
		JTextField yourPers = new JTextField("Person");
		person = createPersonBox();
		per.add(yourPers);
		per.add(person);
		return per;
	}

	private JComboBox<String> createPersonBox()
	{
		JComboBox<String> combo = new JComboBox<String>();
		for (Card c : gameCards) {
			if (c.getType() == CardType.PERSON) {
				combo.addItem(c.getName());
			}
		}
		return combo;
	}


	public JPanel generateWeaponGuess() {
		JPanel wea = new JPanel();

		//people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		wea.setLayout(new GridLayout(1, 2));
		JTextField yourWeap = new JTextField("Weapon");
		weapon = createWeaponBox();
		wea.add(yourWeap);
		wea.add(weapon);
		return wea;
	}

	private JComboBox<String> createWeaponBox()
	{
		JComboBox<String> combo = new JComboBox<String>();
		for (Card c : gameCards) {
			if (c.getType() == CardType.WEAPON) {
				combo.addItem(c.getName());
			}
		}
		return combo;
	}


	public JPanel generateRoomGuess() {
		JPanel roo = new JPanel();

		//people.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		roo.setLayout(new GridLayout(1, 2));
		JTextField yourWeap = new JTextField("Room");
		room = creatRoomBox();
		roo.add(yourWeap);
		roo.add(room);
		return roo;
	}

	private JComboBox<String> creatRoomBox()
	{
		JComboBox<String> combo = new JComboBox<String>();
		for (Card c : gameCards) {
			if (c.getType() == CardType.ROOM) {
				combo.addItem(c.getName());
			}
		}
		return combo;
	}
}




