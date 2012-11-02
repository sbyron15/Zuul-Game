package view;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Room;

/**
 * MapView represents the 2D view of a room
 * @author Sean
 *
 */

public class MapView extends JFrame implements Observer {
	
	private static final String SOUTH = "south";
	private static final String EAST = "east";
	private static final String WEST = "west";
	private static final String NORTH = "north";
	
	private static int SIZE = 3;
	private static int WINDOW_SIZE = 600;
	private JPanel[][] tiles;
	private GridLayout layout;
	
	public MapView(String name) {
		super(name);
		
		//Initialize the layout
		layout = new GridLayout(SIZE,SIZE);
		this.setLayout(layout);
		
		tiles = new JPanel[SIZE][SIZE];
		
		//Initialize the tiles
		for (int i=0; i<SIZE; i++ ){
			for (int j=0; j<SIZE; j++) {
				tiles[i][j] = new JPanel();
				tiles[i][j].setBackground(Color.BLACK);
				//tiles[i][j].add(new JLabel("(" + i + "," + j + ")"));
				add(tiles[i][j]);
			}
		}
		
		//Setup the window
		setSize(WINDOW_SIZE,WINDOW_SIZE);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
	}
	
	/**
	 * Creates and shows a new map view
	 * @param args
	 */
	public static void main(String args[]) {
		MapView view = new MapView("World of Zuul");
		view.setVisible(true);
	}

	/**
	 * Update the tiles with the current state of the game
	 */
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof DrawableRoom) {
			//Cast arg1 as a drawable room
			DrawableRoom currentRoom = (DrawableRoom)arg1;
			
			//Create a new SIZExSIZE array to hold the current rooms
			Room rooms[][] = new Room[SIZE][SIZE];
			
			//Set the middle room (position 1,1) to the current room
			rooms[1][1] = currentRoom;
			
			//Recursively find rooms to display beginning at the center of the map
			discoverRooms(1, 1, rooms, null);
			
			//Remove all the current tiles
			getContentPane().removeAll();
			
			//Update the tiles to show the current state of the game
			for (int i=0; i<SIZE; i++) {
				for (int j=0; j<SIZE; j++) {
					if (rooms[i][j] != null) {
						DrawableRoom temp = (DrawableRoom)rooms[i][j];
						tiles[i][j] = temp.getRoomPanel();
					} else {
						tiles[i][j] = new JPanel();
					}	
					//Add the tile to the JFrame
					add(tiles[i][j]);
				}
			}

			//Repaint the 2D View
			validate();
			repaint();
		}
	}

	/**
	 * This method recursively travels through the game and builds a 2d array 
	 * of the rooms for the MapView to display
	 * @param x
	 * @param y
	 * @param rooms
	 * @param previous
	 */
	private void discoverRooms(int x, int y, Room[][] rooms, String previous) {
		//Check north
		if (previous != SOUTH) {
			if (rooms[x][y].getExits(NORTH) != null) {
				if (x>0 && rooms[x-1][y] == null) {
					rooms[x-1][y] = rooms[x][y].getExits(NORTH);
					discoverRooms(x-1, y, rooms, NORTH);
				}
			}
		}
		
		//Check south
		if (previous != NORTH) {
			if (rooms[x][y].getExits(SOUTH) != null) {
				if (x<(SIZE-1) && rooms[x+1][y] == null) {
					rooms[x+1][y] = rooms[x][y].getExits(SOUTH);
					discoverRooms(x+1, y, rooms, SOUTH);
				}
			}
		}
		
		//Check west
		if (previous != EAST) {
			if (rooms[x][y].getExits(WEST) != null) {
				if (y>0 && rooms[x][y-1] == null) {
					rooms[x][y-1] = rooms[x][y].getExits(WEST);
					discoverRooms(x, y-1, rooms, WEST);
				}
			}
		}
		
		//Check east
		if (previous != WEST) {
			if (rooms[x][y].getExits(EAST) != null) {
				if (y<(SIZE-1) && rooms[x][y+1] == null) {
					rooms[x][y+1] = rooms[x][y].getExits(EAST);
					discoverRooms(x, y+1, rooms, EAST);
				}
			}
		}
	}
}