package model;

import java.util.Arrays;

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
	private final int ROWS = 4;
	private final int COLS = 4;
	private final int[] WIN_LOCATION = new int[] { (ROWS * COLS - 1),
			(ROWS * COLS - 1) }; // end of maze

	/*
	 * 2D array to store rooms in the maze
	 */
	private final Room[][] myMatrix;

	/*
	 * Location of the player in the maze
	 */
	private int[] myPlayerLocation;

	/*
	 * Keeps track of how many rooms have been made in the maze. Mainly for
	 * debugging.
	 */
	private int roomCounter;

	/*
	 * Big data storage of all pokemon info
	 */
	private static Pokedex myPokedex;

	/*
	 * Singleton maze instantiation
	 */
	private static Maze singleMaze = null;

//       TODO Current win condition is that the player needs to get to the
//        opposite corner that they are in.
//TODO:        private final boolean winCondition;

	/**
	 * Constructor for maze
	 */
	private Maze() {
		roomCounter = 0;
		myMatrix = fillRooms();
//		winCondition = false;
		final int[] h = new int[] { 0, 0 };
		myPlayerLocation = h;
		myMatrix[0][0].setPlayer(true); // put player location at 0,0
	}

	/**
	 * Constructor for maze given pokedex info
	 * 
	 * @param thePokedex
	 */
	public Maze(Pokedex thePokedex) {
		myPokedex = thePokedex;
		roomCounter = 0;
		myMatrix = fillRooms();
		final int[] h = new int[] { 0, 0 };
		myPlayerLocation = h;

		// TODO: test stuff delete later
		myMatrix[0][0].setPlayer(true); // put player location at 0,0

	}

	/**
	 * Singleton maze instantiation
	 * 
	 * @return Maze
	 */
	public static Maze getInstance() {
		if (singleMaze == null) {
			singleMaze = new Maze(myPokedex);
		}
		return singleMaze;
	}

	/**
	 * Fills matrix with new rooms that have questions.
	 * 
	 * @return Room[][] matrix of instantiated rooms
	 */
	private Room[][] fillRooms() {
		// TODO Auto-generated method stub
		final Room[][] res = new Room[ROWS][COLS];
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) {
				res[i][j] = new Room(roomCounter, myPokedex);
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
	 * Returns the players current location
	 * 
	 * @return int[] an integer array of the players current location 0 = row, 1
	 *         = col
	 */
	public int[] getPlayerLocation() {
		return myPlayerLocation;
	}

	/**
	 * Sets location of the player
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
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(Arrays.toString(getPlayerLocation()));
	}

	/*
	 * Return current room player is in
	 */
	public Room getCurrRoom() {
		return myMatrix[myPlayerLocation[0]][myPlayerLocation[1]];
	}

	/**
	 * 
	 * @param theR the row
	 * @param theC the col
	 * @return the room at that index
	 */
	public Room getRoom(final int theR, final int theC) {
		Room res = null;
		try {
			if (theR < 0 || theC < 0 || theR > ROWS || theC > COLS) {
				throw new Exception(
						"Room does not exist at [" + theR + ", " + theC + "]");
			} else {
				res = myMatrix[theR][theC];
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;

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

}
