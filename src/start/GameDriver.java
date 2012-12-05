package start;

import java.util.HashMap;

import model.Game;
import model.object.Player;
import view.FirstPersonRoom;
import view.FirstPersonView;
import controller.FPKeyListener;
import controller.FPMouseListener;

/**
 * Sets-up and starts a new game
 * @author seanbyron
 *
 */
public class GameDriver {

	private Game game;
	private FPKeyListener keyListener;
	private FPMouseListener mouseListener;
	private FirstPersonView view;

	/**
	 * @param args
	 */
	public GameDriver() {

		// Create a mouse listener
		mouseListener = new FPMouseListener();

		// Create a new game
		game = new Game(mouseListener);

		// Create a key listener
		keyListener = new FPKeyListener();
		keyListener.addObserver(game);

		mouseListener.addObserver(game);

		// Create a 3D First Person View
		view = new FirstPersonView("World of Zuul", keyListener);

		game.addObserver(view);
		view.addObserver(game);
	}

	public void startDefaultGame() {
		game.loadDefaultGame();
		view.show();
	}

	public void startGame(Player player, HashMap<String, FirstPersonRoom> rooms) {
		game.loadGame(player, rooms);
		view.show();
	}

	public void startGameFromLevel(String levelName) {
		game.loadGameFromLevel(levelName);
		view.show();
	}
	

}
