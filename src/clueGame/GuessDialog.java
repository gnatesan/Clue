package clueGame;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import clueGame.Card.CardType;

public class GuessDialog extends JDialog{

	private Player play;
	private Board b;
	private ClueGame game;
	private ArrayList<Card> gameCards;
	private JComboBox<String> p, w;
	private ControlGui c;


	public GuessDialog(Player p, ClueGame game) {
		this.play = p;
		this.b = game.getBoard();
		this.game = game;
		gameCards = game.getCards();
		setUpGui();
		game.setMadeSuggestion(true);
	}

	
	public void setGui(ControlGui c){
		this.c = c;
	}
	
	public void setUpGui() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setLayout(new GridLayout(4, 1));
		
		add(generateRoomBox(), BorderLayout.NORTH);
		add(generatePersonGuess(), BorderLayout.CENTER);
		add(generateWeaponGuess(), BorderLayout.SOUTH);
		JButton sub = new JButton("Submit");
		sub.addActionListener(new ActionListener() {
			//this implements the interface, which allows us to do this because
			//ActionListener is an interface
			public void actionPerformed(ActionEvent e)
			{
				//gets the solution from the selections and sends
				//call method for handling disproving suggestion
				BoardCell loc = b.getCellAt(play.getRow(), play.getCol());
				game.handleSuggestion(((String)p.getSelectedItem()), b.getRooms().get(((RoomCell)loc).getInitial()), ((String)w.getSelectedItem()), play, game.getPlayers());
				Suggestion suggest = game.getSuggestion();
				c.updateGuessPanel(suggest);	
				c.updateGuessResult(game.getDisproveCard().getName());
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

	public JPanel generateRoomBox() {
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(1, 2));
		//loc should be a RoomCell if this GuessDialog is called
		BoardCell loc = b.getCellAt(play.getRow(), play.getCol());
		//gets the room name and sets that to the JTextField
		JTextField r = new JTextField(b.getRooms().get(((RoomCell)loc).getInitial()));
		JTextField yourRoom = new JTextField("Your room");
		room.add(yourRoom);
		room.add(r);	
		return room;
	}

	public JPanel generatePersonGuess() {
		JPanel person = new JPanel();

		person.setLayout(new GridLayout(1, 2));
		JTextField yourPers = new JTextField("Person");
		p = createPersonBox();
		person.add(yourPers);
		person.add(p);
		return person;
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
		JPanel weapon = new JPanel();

		weapon.setLayout(new GridLayout(1, 2));
		JTextField yourWeap = new JTextField("Weapon");
		w = createWeaponBox();
		weapon.add(yourWeap);
		weapon.add(w);
		return weapon;
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


}


