package clueGame;

import java.awt.Font;
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
	private JTextField guessField, guessPanel;
	private ClueGame game;

	public ControlGui(ClueGame game) {
		guessPanel = new JTextField(15);
		this.game = game;
		setSize(650, 300);
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
		nextPlayerButton.addActionListener(new ActionListener() {
			//this implements the interface, which allows us to do this because
			//ActionListener is an interface
			public void actionPerformed(ActionEvent e){
				nextPlayer();
			}
		});


		JButton makeAccuseButton = new JButton("Make Accusation");
		makeAccuseButton.addActionListener(new ActionListener() {
			//this implements the interface, which allows us to do this because
			//ActionListener is an interface
			public void actionPerformed(ActionEvent e){
				if(game.getTurn() instanceof HumanPlayer&&!((HumanPlayer)game.getHuman()).getMoved()){
					makeAccusation();
					//nextPlayer();
				}
				else if(((HumanPlayer)game.getHuman()).getMoved()){
					 JOptionPane.showMessageDialog(game,"You have already taken your turn.","Cannot make an accusation", JOptionPane.INFORMATION_MESSAGE);
				}
				else JOptionPane.showMessageDialog(game,"You are not a human player!","Cannot make an accusation", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		panel.setLayout(new GridLayout(1,0));
		panel.add(nextPlayerButton);

		panel.add(makeAccuseButton);


		return panel;
	}
	
	private void makeAccusation(){
		//create a makeAccusation dialog
		AccuseDialog accuse = new AccuseDialog(game.getTurn(), game);
	}

	public void nextPlayer(){
		if (game.getBoard().isHumanMustFinish()){
			JOptionPane.showMessageDialog(game,"You must make a valid move!","Cannot Advance!", JOptionPane.INFORMATION_MESSAGE);
		}else{
			game.setTurn();
			updateTurnPanel(game.getTurn().getName());
			int roll = game.roll();
			updateDiePanel(roll);
			game.getBoard().repaint();
			if(!game.getTurn().isHuman()){
				game.getHuman().setMoved(false);
				game.makeMove(((ComputerPlayer)game.getTurn()), roll);
				if(game.getDisproveCard()!=null)
					updateGuessResult(game.getDisproveCard().getName());
				else updateGuessResult("no new clue");
				if(game.getMadeSuggestion()){
					Suggestion suggest = game.getSuggestion();
					updateGuessPanel(suggest);
				}
			}
			else{
				((HumanPlayer) game.getTurn()).showMove(game.getBoard(), roll);
				if(game.getMadeSuggestion()){
					Suggestion suggest = game.getSuggestion();
					updateGuessPanel(suggest);
					game.setMadeSuggestion(false);
				}
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
		rollText.setEditable(false);

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
		JPanel guessP = new JPanel();
		guessP.setLayout(new GridLayout(2, 1));
		JLabel guessLabel = new JLabel("Guess");
		guessPanel.setEditable(false);
		Font font = new Font("Verdana", Font.PLAIN, 10);
		guessPanel.setFont(font);

		guessP.add(guessLabel);
		guessP.add(guessPanel);
		guessP.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));

		return guessP;
	}
	
	public void updateGuessPanel(Suggestion s){
		guessPanel.setText(s.getPerson().getName() + " in the " + s.getRoom().getName() + " with the " + s.getWeapon().getName());
	}


	private JPanel createGuessResult() {
		JPanel guessResultPanel = new JPanel();

		JLabel responseLabel = new JLabel("Response");
		guessField = new JTextField(8);
		guessField.setEditable(false);

		guessResultPanel.add(responseLabel);
		guessResultPanel.add(guessField);
		guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Result"));

		return guessResultPanel;
	}
	
	public void updateGuessResult(String s){
		guessField.setText(s);
	}


}