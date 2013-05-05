import java.util.ArrayList;

public class CollisionDetector {
	private ArrayList<Snake> snakes;
	private Game game;

	public CollisionDetector(ArrayList<Snake> snakes, Game snakeGame) {
		this.snakes = snakes;
		this.game = snakeGame;
	}
}