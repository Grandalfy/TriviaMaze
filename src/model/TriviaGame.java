package model;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Used to initialize a trivia maze game
 * 
 * @author ajdowney
 * @author ken
 */
public class TriviaGame extends JPanel{
	
	private final Color BG= new Color(220, 20, 60);
	
	String[] GAMEMODES = new String[] {"Multiple Choice", "User Input"};

	/*
	 * Holds a map that stores info on all Pokemon
	 */
	private final Pokedex myPokedex;
	/*
	 * Maze that is played on
	 */
	private final Maze myMaze;
	
	

	/*
	 * Constructor
	 */
	public TriviaGame() {
		setBackground(BG);
		setPreferredSize(new Dimension(1920, 1080));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TriviaGame.class.getResource("/other/WhoPKMNLogo.png")));
		
		JButton btnNewButton = new JButton("Start Game");
		
		JComboBox gamemodeBox = new JComboBox();
		gamemodeBox.addItem(GAMEMODES[0]);
		gamemodeBox.addItem(GAMEMODES[1]);
		
		JComboBox playerBox = new JComboBox();
		
		JButton btnNewButton_1 = new JButton("Tutorial");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(738)
					.addComponent(btnNewButton_1)
					.addGap(37)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(playerBox, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addComponent(gamemodeBox, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(553, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(547, Short.MAX_VALUE)
					.addComponent(lblNewLabel)
					.addGap(426))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(130)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 351, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(playerBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1)
						.addComponent(gamemodeBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(479, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		myPokedex = Pokedex.getInstance();
		myMaze = Maze.getInstance();
	}

	/**
	 * Pokedex getter
	 * 
	 * @return Pokedex data of pokemon
	 */
	public Pokedex getPokedex() {
		return myPokedex;
	}

	/**
	 * Maze getter
	 * 
	 * @return Maze
	 */
	public Maze getMaze() {
		return myMaze;
	}
}
