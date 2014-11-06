package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGui extends JPanel {
	private JTextField rollText;
	private JTextField turnField;
	private ClueGame game;

	public ControlGui(ClueGame game) {
		this.game = game;
		setSize(650, 250);
		setLayout(new GridLayout(2, 1));

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));

		topPanel.add(createTurnPanel());
		topPanel.add(createTopButtonPanel());

		bottomPanel.add(createDicePanel());
		bottomPanel.add(createGuessPanel());
		bottomPanel.add(createGuessResult());

		add(topPanel);
		add(bottomPanel);
	}

	private JPanel createTopButtonPanel() {
		JPanel panel = new JPanel();
		JButton nextPlayerButton = new JButton("Next Player");
		//need to make a listener for this button\
		nextPlayerButton.addActionListener(new ActionListener() {
			//this implements the interface, which allows us to do this because
			//ActionListener is an interface
			public void actionPerformed(ActionEvent e){
				nextPlayer();
			}
		});


		JButton makeAccuseButton = new JButton("Make Accusation");

		panel.setLayout(new GridLayout(1,0));
		panel.add(nextPlayerButton);

		panel.add(makeAccuseButton);


		return panel;
	}

	public void nextPlayer(){
		if (game.getBoard().isHumanMustFinish()){
			JOptionPane.showMessageDialog(null,"You must make a valid move!","Cannot Advance!", JOptionPane.INFORMATION_MESSAGE);
		}else{
			game.setTurn();
			updateTurnPanel(game.getTurn().getName());
			int roll = game.roll();
			updateDiePanel(roll);
			game.getBoard().repaint();
			if(!game.getTurn().isHuman()){
				game.makeMove(((ComputerPlayer)game.getTurn()), roll);
			}
			else{
				((HumanPlayer) game.getTurn()).showMove(game.getBoard(), roll);
			}	
		}
	}


	private JPanel createTurnPanel() {
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,0));

		JLabel turnLabel = new JLabel("Whose turn?", JLabel.CENTER);
		turnField = new JTextField();

		turnPanel.add(turnLabel);
		turnPanel.add(turnField);

		return turnPanel;
	}

	private void updateTurnPanel(String s){
		turnField.setText(s);
	}

	private JPanel createDicePanel() {
		JPanel dicePanel = new JPanel();

		JLabel roll = new JLabel("Roll");
		rollText = new JTextField("   ");
		//want to be able to change the number whenever we roll for each turn
		rollText.setEditable(true);

		dicePanel.add(roll);
		dicePanel.add(rollText);
		dicePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));

		return dicePanel;
	}

	private void updateDiePanel(int roll){
		String s = "";
		rollText.setText(s + roll);
	}

	private JPanel createGuessPanel() {
		JPanel guessPanel = new JPanel();

		JLabel guessLabel = new JLabel("Guess");
		JTextField guessText = new JTextField(15);
		guessText.setEditable(false);

		guessPanel.add(guessLabel);
		guessPanel.add(guessText);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		return guessPanel;
	}

	private JPanel createGuessResult() {
		JPanel guessResultPanel = new JPanel();

		JLabel responseLabel = new JLabel("Response");
		JTextField responseText = new JTextField(8);
		responseText.setEditable(false);

		guessResultPanel.add(responseLabel);
		guessResultPanel.add(responseText);
		guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Result"));

		return guessResultPanel;
	}


}