package view.viewHelper;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Maze;
import model.Room;
import view.PokemonPanel;

/**
 * Used for QuestionRoomGUI and TextRoomGUI for behaviors that they both have.
 * 
 * Main use is to verify answers and changes maze player location on correct and
 * incorrect answers.
 * 
 * Abstract to prevent instantiation because it does nothing on its own.
 * Technically a controller and a view.
 * 
 * @author Kenneth Ahrens
 * @version Spring 2021
 */

public abstract class AbstractQuestionPanel extends JPanel {
	
	/*
	 * Icons for the option pane
	 */
	private final ImageIcon CORRECT_ICON = new ImageIcon("./src/images/other/correct_icon.png");
	private final ImageIcon INCORRECT_ICON = new ImageIcon("./src/images/other/incorrect_icon.png");
	
	/*
	 * Maze
	 */
	private final Maze myMaze;
	/*
	 * Game panel
	 */
	private final PokemonPanel myPP;

	/**
	 * Constructor
	 * 
	 * @param thePP so the panel can be modified after user answers
	 */
	public AbstractQuestionPanel(final PokemonPanel thePP) {
		super();
		myPP = thePP;
		myMaze = Maze.getInstance();
	}

	/**
	 * Compare user answer and the actual answer and tell user if it was correct
	 * via option pane.
	 * 
	 * @param String theUserAns to compare to the correct answer
	 */
	public void verifyAnswer(final String theUserAns) {

		final Room curr = myMaze.getAttemptRoom();
		String correctAns = curr.getAnswer();
		final String correct = correctAns + " was the correct answer!";
		final String incorrect = "Sorry, but " + theUserAns
				+ " is incorrect... ";

		// format answer to prevent errors
		correctAns = correctAns.toLowerCase().strip();
		final String userAns = theUserAns.toLowerCase().strip();
		final Boolean isCorrect = userAns.equals(correctAns);

		// call method to change the maze
		doUserAnswer(isCorrect);
		myPP.setShowQMark(); //turn off q mark because the user answered

		if (isCorrect) {
//		        myPP.setMyReveal(isCorrect);
			firePropertyChange("showpkmn", null, true);
			updateGUI();
			JOptionPane.showMessageDialog(null, correct,
					"Correct! Good job!", JOptionPane.INFORMATION_MESSAGE, CORRECT_ICON);

		} else { // incorrect
//		        myPP.setMyReveal(isCorrect);
			firePropertyChange("showpkmn", null, false);
			updateGUI();
			JOptionPane.showMessageDialog(null, incorrect + correct,
					"Incorrect...", JOptionPane.INFORMATION_MESSAGE, INCORRECT_ICON);
		}
		
		//disable the buttons after the user answers
		this.enableButtons(false);
		checkWinLoseCondition();
		myPP.refreshGUI(); //one full refresh of the gui


	}

	/**
	 * User answered from a room panel question gui. Read a boolean for if they
	 * were right/wrong. Update player/attempt statuses and locations in maze
	 * based on answer result.
	 * 
	 * 
	 * @param Boolean theRes true = user answered correctly set curr and attempt
	 *                room to visited, false = incorrect, set visited false and
	 *                block the room
	 */
	private void doUserAnswer(final Boolean theResult) {

		final Room curr = myMaze.getCurrRoom();
		final Room attempt = myMaze.getAttemptRoom();
		if (theResult) { // answered correctly
			// set current player room and attempted room to visited
			curr.setVisited(theResult);
			attempt.setVisited(theResult);
			myMaze.setPlayerLocation(myMaze.getAttemptedLocation());
		} else { // answered incorrectly
			attempt.setEntry(false); // block that room
			// reset attempt location to default
			myMaze.setAttemptLocation(myMaze.getPlayerLocation());
		}
		


	}
	
	/*
	 * Fire property changes for if the player has won or lost
	 */
	private void checkWinLoseCondition() {
		if (myMaze.isWinCondition()) {
			firePropertyChange("win", null, null);
		}

		// TODO: if maze isLoseCondition() fire lose
	}

	/*
	 * Update gui components to ensure everything is current
	 */
	private void updateGUI() {
		myPP.refreshPokemonImage();
		firePropertyChange("newpos", null, null);


	}

	/**
	 * Enable or disable the answer fields
	 */
	public abstract void enableButtons(Boolean theBool);

	/**
	 * alter the answer fields to the default
	 */
	public abstract void setButtons();

}
