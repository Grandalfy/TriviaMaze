package model;

import java.util.ArrayList;

/**
 * Maze composing of rooms with Pokemon questions; represented by a 2D matrix.
 * Main gameplay element where the player starts in one location and tries to
 * answer Pokemon questions to get to the end of the maze.
 * 
 * Class is designed with Singleton principles so only one maze is instantiated
 * for the game.
 * 
 * @author Kenneth Ahrens
 * @author AJ Downey
 * @author Katlyn Malone
 * @version Spring 2021
 */

public class Maze {

	/*
	 * Constants
	 */
	private final static int ROWS = 4;
	private final static int COLS = 4;
	private final static int[] WIN_LOCATION = new int[] { (ROWS - 1),
			(ROWS - 1) }; // end of maze

	/*
	 * 2D array to store rooms in the maze
	 */
	private final Room[][] myMatrix;
	
	private boolean[][] myEnterMatrix;

	/*
	 * Location of the player in the maze
	 */
	private int[] myPlayerLocation;

	/*
	 * Location the player is trying to move to
	 */
	private int[] myAttemptLocation;

	/*
	 * Keeps track of how many rooms have been made in the maze. Mainly for
	 * debugging.
	 */
	private int roomCounter;

	/*
	 * List of Pokemon objects
	 */
	private final ArrayList<Pokemon> myPokemonList;

	/*
	 * Boolean to verify when the player has won the game
	 */
	private boolean myWinCondition;

	private boolean myLoseCondition;
	// /*
	// * Big data storage of all pokemon info
	// */
	// private static Pokedex myPokedex;

	/*
	 * Singleton maze instantiation
	 */
	private static Maze singleMaze = null;

	// TODO Current win condition is that the player needs to get to the
	// opposite corner that they are in.
	// TODO: private final boolean winCondition;

	/**
	 * Constructor for maze
	 */
	private Maze() {
		roomCounter = 0;
		myMatrix = fillRooms();
		myPlayerLocation = new int[] { 0, 0 };
		myAttemptLocation = myPlayerLocation.clone();
		myPokemonList = fillPokemonList();
		// TODO: test stuff delete later
		myMatrix[0][0].setPlayer(true); // put player location at 0,0
		myWinCondition = false;
		myLoseCondition = false;

		// set the first room to be visited since we dont play that room
		myMatrix[0][0].setVisited(true);

	}

	/**
	 * Singleton maze instantiation
	 * 
	 * @return Maze
	 */
	public static Maze getInstance() {
		if (singleMaze == null) {
			singleMaze = new Maze();
		}
		return singleMaze;
	}

	/**
	 * Fills matrix with new rooms that have questions and pokemon
	 * 
	 * @return Room[][] matrix of instantiated rooms
	 */
	private Room[][] fillRooms() {
		final Room[][] res = new Room[ROWS][COLS];

		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				res[i][j] = new Room(roomCounter);
				roomCounter++;
			}
		}
		return res;
	}

	/**
	 * Returns if the player has won yet
	 * 
	 * @return boolean t = win, f = not won
	 */
	public boolean isWinCondition() {
		return myPlayerLocation[0] == WIN_LOCATION[0]
				&& myPlayerLocation[1] == WIN_LOCATION[1];
	}
	
	/**
	 * Returns if the player has lost yet
	 * 
	 * @return boolean t = lose, f = not lose
	 */
	public boolean isLoseCondition() {
		return move(fillEntryMatrix());
	}
	
	
	
	
	
	
	
	
	// TODO: fill/update matrix
	public boolean[][] fillEntryMatrix() {
		boolean[][] matrix = new boolean[ROWS][COLS];
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				final Room r = myMatrix[i][j];
				if(r.canEnter()) {
					matrix[i][j] = true;
				} else {
					matrix[i][j] = false;
				}
			}
		}
		return matrix;
	}
	
	/**
	 * Returns if the player is completely blocked in
	 * 
	 * @param theEntryMaze a matrix that keepts track of whether the user can enter a room or not
	 * @return boolean t = if there is no path to the exit, f = is there is still a path to the exit
	 */
	public boolean move(boolean[][] theEntryMatrix) {
		// Mark reachable (from top left) nodes  
	    // in first row and first column. 
	    for (int i = 1; i < ROWS; i++)  
	        if (theEntryMatrix[0][i] != false) 
	        	theEntryMatrix[0][i] = theEntryMatrix[0][i - 1];    
	  
	    for (int j = 1; j < COLS; j++)  
	        if (theEntryMatrix[j][0] != false) 
	        	theEntryMatrix[j][0] = theEntryMatrix[j - 1][0];     
	  
	    // Mark reachable nodes in remaining 
	    // matrix. 
	    for (int i = 1; i < ROWS; i++)  
	        for (int j = 1; j < COLS; j++)  
	          if (theEntryMatrix[i][j] != false) 
	        	  theEntryMatrix[i][j] = true;        
	      
	    // return true if right bottom true
	    return (theEntryMatrix[ROWS - 1][COLS - 1] == true);
	}
	

	/**
	 * winning location
	 * 
	 * @return [0] = win row, [1] = win col
	 */
	public int[] getWinLocation() {
		return WIN_LOCATION.clone();
	}

	/**
	 * Returns the players current location
	 * 
	 * @return int[] an integer array of the players current location 0 = row, 1
	 *         = col
	 */
	public int[] getPlayerLocation() {
		return myPlayerLocation;
	}

	/**
	 * Sets location of the player Also verifies if the player is in the winning
	 * location
	 * 
	 * @param int[] theNewPos [0] = row, [1] = col
	 */
	public void setPlayerLocation(final int[] theNewPos) {
		try { // error checking location
			if (theNewPos[0] < 0 || theNewPos[1] < 0 || theNewPos[0] > ROWS
					|| theNewPos[1] > COLS) {
				throw new Exception("Cannot set player location at ["
						+ theNewPos[0] + ", " + theNewPos[1] + "]");
			} else {

				myMatrix[myPlayerLocation[0]][myPlayerLocation[1]]
						.setPlayer(false);
				myMatrix[theNewPos[0]][theNewPos[1]].setPlayer(true);
				myPlayerLocation = theNewPos.clone();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		// System.out.println(Arrays.toString(getPlayerLocation()));
		getCurrRoom().setVisited(true);
		myWinCondition = isWinCondition();

		myAttemptLocation = myPlayerLocation.clone(); // set because of the
														// teleport cheat
	}

	/*
	 * Return current room player is in
	 */
	public Room getCurrRoom() {
		return myMatrix[myPlayerLocation[0]][myPlayerLocation[1]];
	}
	
	public Room getWinRoom() {
		return myMatrix[ROWS - 1][COLS - 1];
	}

	/**
	 * Return current room player is trying to move to
	 */
	public Room getAttemptRoom() {
		return myMatrix[myAttemptLocation[0]][myAttemptLocation[1]];
	}

	/*
	 * Current location in maze the player is trying to move to
	 */
	public int[] getAttemptedLocation() {
		return myAttemptLocation;
	}

	/**
	 * Sets the attempted location to move to
	 * 
	 * @param int[] theNewPos [0] = row, [1] = col
	 */

	public void setAttemptLocation(final int[] theNewPos) {
		try { // error checking location
			if (theNewPos[0] < 0 || theNewPos[1] < 0 || theNewPos[0] > ROWS
					|| theNewPos[1] > COLS) {
				throw new Exception("Cannot set attempt location at ["
						+ theNewPos[0] + ", " + theNewPos[1] + "]");
			} else {
				myAttemptLocation = theNewPos.clone();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if the player has attempted to move to a new room or not
	 * 
	 */
	public boolean hasNotMoved() {
		return myPlayerLocation[0] == myAttemptLocation[0]
				&& myPlayerLocation[1] == myAttemptLocation[1];
	}

	/**
	 * 
	 * @param theR the row
	 * @param theC the col
	 * @return the room at that index
	 * @throws Exception Handled by the room checker
	 */
	public Room getRoom(final int theR, final int theC) throws Exception {
		Room res = null;
		if (theR < 0 || theC < 0 || theR > ROWS || theC > COLS) {
			throw new Exception(
					"Room does not exist at [" + theR + ", " + theC + "]");
		} else {
			res = myMatrix[theR][theC];
		}
		return res;

	}

	/**
	 * Set a new room at a location in matrix
	 * 
	 * @param the new room
	 * @param theR the row index
	 * @param theC the col index
	 */
	public void setRoomInMatrix(final Room theRoom, final int theR,
			final int theC) {
		try {
			if (theR < 0 || theC < 0 || theR > ROWS || theC > COLS) {
				throw new Exception(
						"Room does not exist at [" + theR + ", " + theC + "]");
			} else {
				System.out.println("added" + theRoom.getAnswer());
				myMatrix[theR][theC] = theRoom;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ArrayList<Pokemon> fillPokemonList() {
		// TODO Auto-generated method stub
		final ArrayList<Pokemon> res = new ArrayList<>();
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				res.add(myMatrix[i][j].getPokemon());
			}
		}
		return res;
	}

	public ArrayList<Pokemon> getPokemonList() {
		return myPokemonList;
	}

	/**
	 * Getter for room matrix
	 * 
	 * @return room[][]
	 */
	public Room[][] getMatrix() {
		return myMatrix;
	}

	/**
	 * Getter for row count
	 * 
	 * @return row count
	 */
	public int getRows() {
		return ROWS;
	}

	/**
	 * Getter for col count
	 * 
	 * @return col count
	 */
	public int getCols() {
		return COLS;
	}
	
	/**
	 * TODO:
	 * Reset the maze to default and instantiate new rooms
	 */
	public static void reset() {
		
	}

	// TODO: DELETE LATER
	// used to visually check which rooms are set to blocked
	// not sure why but when answering incorrect all rooms are blocked off
	public void printBlockedDebugger() {
		final StringBuilder sb = new StringBuilder();
		sb.append("What rooms are blocked?");
		for (int i = 0; i < ROWS; i++) {
			sb.append("\n");
			for (int j = 0; j < COLS; j++) {
				final Room r = myMatrix[i][j];
				sb.append(r.getRoomName() + " " + r.canEnter() + ", ");
			}
		}

		System.out.println(sb);

	}

}
