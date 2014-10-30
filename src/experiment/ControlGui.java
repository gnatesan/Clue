package experiment;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGui extends JFrame {
	public ControlGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Control GUI");
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
		JButton makeAccuseButton = new JButton("Make Accusation");
		
		panel.setLayout(new GridLayout(1,0));
		panel.add(nextPlayerButton);
		panel.add(makeAccuseButton);
		
		
		return panel;
	}
	
	private JPanel createTurnPanel() {
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,0));
		
		JLabel turnLabel = new JLabel("Whose turn?", JLabel.CENTER);
		JTextField turnField = new JTextField();
		
		turnPanel.add(turnLabel);
		turnPanel.add(turnField);
		
		return turnPanel;
	}
	
	private JPanel createDicePanel() {
		JPanel dicePanel = new JPanel();
		
		JLabel roll = new JLabel("Roll");
		JTextField rollText = new JTextField(2);
		rollText.setEditable(false);
		
		dicePanel.add(roll);
		dicePanel.add(rollText);
		dicePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		
		return dicePanel;
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

	public static void main(String[] args) {
		ControlGui gui = new ControlGui();
		gui.setVisible(true);
	}

}