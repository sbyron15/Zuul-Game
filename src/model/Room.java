package model;

import java.io.Serializable;
import java.util.*;

import model.object.Item;
import model.object.Monster;

/**
 * Class Room - a room in an adventure game.
 * 
 * This class is part of the "World of Zuul" application. "World of Zuul" is a
 * very simple, text based adventure game.
 * 
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via exits. The exits are labelled north, east, south, west.
 * For each direction, the room stores a reference to the neighboring room, or
 * null if there is no exit in that direction.
 * 
 */
public class Room implements Serializable, Cloneable {

	private static final long serialVersionUID = 413390181119184381L;
	private String description;
	private HashMap<String, Wall> walls;
	private boolean visited;

	/**
	 * Create a room described "description". Initially, it has no exits.
	 * "description" is something like "a kitchen" or "an open court yard".
	 * 
	 * @param description
	 *            The room's description.
	 */
	public Room(String description) {
		this.description = description;

		initWalls();
		visited = false;
	}

	private void initWalls() {
		walls = new HashMap<String, Wall>();
		walls.put("north", new Wall());
		walls.put("south", new Wall());
		walls.put("east", new Wall());
		walls.put("west", new Wall());
	}

	/**
	 * Define the exits of this room. Every direction either leads to another
	 * room or is null (no exit there).
	 * 
	 * @param north
	 *            The north exit.
	 * @param east
	 *            The east east.
	 * @param south
	 *            The south exit.
	 * @param west
	 *            The west exit.
	 */
	public void setExits(String direction, Room neighbor) {
		walls.get(direction).setExit(neighbor);
	}

	public HashMap<String, Wall> getWalls() {
		return this.walls;
	}

	/**
	 * @return The description of the room.
	 */
	public String getDescription() {
		return description;
	}

	public String getExitString() {

		String s = "Exits : ";
		Set<String> keys = walls.keySet();
		for (String exit : keys) {
			if (walls.get(exit).getExit() != null) {
				s += " " + walls.get(exit).getExit().getDescription();
			}
		}

		return s + "\n";

	}

	/**
	 * Return a long description of this room, of the form : you are in the
	 * kitchen Exits : north west
	 * 
	 * @return A description of the room, include exits
	 * 
	 */
	public String getLongDescription() {
		return ("You are at the " + description + ".\n" + getExitString()
				+ getItemString() + "\n" + getMonstersString());
	}

	public Room getExit(String direction) {
		return walls.get(direction).getExit();
	}

	public void addItem(Item item, String direction) {
		walls.get(direction).setItem(item);
	}

	private String getItemString() {
		String itemString = "Items in room : ";
		Set<String> keys = walls.keySet();
		for (String item : keys) {
			if (walls.get(item).getItem() != null) {
				itemString += " Key : "
						+ walls.get(item).getItem().getItemName()
						+ " Description : "
						+ walls.get(item).getItem().getItemName()
						+ " Weight : "
						+ walls.get(item).getItem().getItemWeight() + "\n";
			}
		}
		return itemString;
	}

	/**
	 * Author: Sean
	 * 
	 * @return returns a list of the monsters in the room and their health
	 */
	private String getMonstersString() {
		String ret = "Monsters in room:\n";
		Set<String> keys = walls.keySet();
		for (String monster : keys) {
			if (walls.get(monster).getMonster() != null) {
				if (walls.get(monster).getMonster().isAlive()) {
					ret += "- Name : "
							+ walls.get(monster).getMonster().getName() + " ("
							+ walls.get(monster).getMonster().getHealth()
							+ ")\n";
				} else {
					ret += "- Name : "
							+ walls.get(monster).getMonster().getName()
							+ " (DEAD)\n";
				}
			}
		}
		return ret;
	}

	public void removeItem(String itemKey) {
		Set<String> keys = walls.keySet();
		for (String direction : keys) {
			if (walls.get(direction).getItem() != null
					&& walls.get(direction).getItem().getItemName()
							.equals(itemKey)) {
				walls.get(direction).setItem(null);
				return;
			}
		}
	}

	public void removeItemByDirection(String direction) {
		Wall wall = walls.get(direction);
		if (wall != null) {
			wall.setItem(null);
		}
	}

	public Item getItem(String itemKey) {
		Set<String> keys = walls.keySet();
		for (String direction : keys) {
			if (walls.get(direction).getItem() != null
					&& walls.get(direction).getItem().getItemName()
							.equals(itemKey)) {
				return walls.get(direction).getItem();
			}
		}
		return null;
	}

	public boolean containsItem(String itemKey) {
		Set<String> keys = walls.keySet();
		for (String direction : keys) {
			if (walls.get(direction).getItem() != null
					&& walls.get(direction).getItem().getItemName()
							.equals(itemKey)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Modification by Sean Adds a monster to the room
	 * 
	 * @param key
	 * @param monster
	 */
	public void addMonster(Monster monster, String direction) {
		walls.get(direction).setMonster(monster);
	}

	/**
	 * Modification by Sean Removes a monster with from the room
	 * 
	 * @param key
	 */
	public void removeMonster(String key) {
		Set<String> keys = walls.keySet();
		for (String direction : keys) {
			if (walls.get(direction).getMonster() != null
					&& walls.get(direction).getMonster().getName().equals(key)) {
				walls.get(direction).setMonster(null);
			}
		}
	}

	public void removeMonsterByDirection(String direction) {
		Wall wall = walls.get(direction);
		if (wall != null) {
			wall.setMonster(null);
		}
	}

	/**
	 * Modification by Sean Byron Gets a monster from the room
	 * 
	 * @param key
	 * @return
	 */
	public Monster getMonster(String key) {
		Set<String> keys = walls.keySet();
		for (String direction : keys) {
			if (walls.get(direction).getMonster() != null
					&& walls.get(direction).getMonster().getName().equals(key)) {
				return walls.get(direction).getMonster();
			}
		}
		return null;
	}

	public ArrayList<Monster> getMonsters() {
		ArrayList<Monster> m = new ArrayList<Monster>();
		Set<String> keys = walls.keySet();
		for (String direction : keys) {
			if (walls.get(direction).getMonster() != null) {
				m.add(walls.get(direction).getMonster());
			}
		}
		return m;
	}

	/*
	 * public HashMap<String, Monster> getMonsterList(){ return monsters; }
	 */
	public boolean hasMonster() {
		Set<String> keys = walls.keySet();
		for (String direction : keys)
			if (walls.get(direction).getMonster() != null)
				return true;

		return false;
	}

	/**
	 * Addition by Sean Byron Sets the room as having been visited by the user
	 */
	public void visit() {
		visited = true;
	}

	/**
	 * Addition by Sean Byron
	 * 
	 * @return true if the room has been visited
	 * @return false if the player hasn't visited the room yet
	 */
	public boolean hasBeenVisited() {
		return visited;
	}

	public Wall getWall(String direction) {
		return walls.get(direction);
	}

	public void removeExit(String direction) {
		walls.get(direction).setExit(null);
	}

	public boolean hasItem() {
		Set<String> keys = walls.keySet();
		for (String direction : keys)
			if (walls.get(direction).getItem() != null)
				return true;

		return false;
	}

	public ArrayList<Item> getItems() {
		ArrayList<Item> i = new ArrayList<Item>();
		Set<String> keys = walls.keySet();
		for (String direction : keys) {
			if (walls.get(direction).getItem() != null) {
				i.add(walls.get(direction).getItem());
			}
		}
		return i;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}
}