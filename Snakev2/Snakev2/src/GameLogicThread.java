import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class GameLogicThread extends Thread {

	private Board board; // the board that the snakes will be travelling along
	private Item[] items; // the list of positions of the items on the map
	private Game snakeGame;
	private Point[] corners;

	private int itemCount;

	public GameLogicThread(Game snakeGame) {

		setBoard(new Board(snakeGame.getBoardWidth(),
				snakeGame.getBoardHeight()));
		setItems(new Item[snakeGame.getMaxPlayers() - 1]);
		setSnakeGame(snakeGame);

	}

	/**
	 * The overridden method of the Thread Class used to run a game thread
	 */
	public void run() {// TODO

		// game started

		boolean running = true;

		// generate a list of possible corners

		setCorners(new Point[4]);

		getCorners()[0] = new Point(0, 0);
		getCorners()[1] = new Point(0, snakeGame.getBoardHeight());
		getCorners()[2] = new Point(snakeGame.getBoardWidth(), 0);
		getCorners()[3] = new Point(snakeGame.getBoardWidth(),
				snakeGame.getBoardHeight());

		Map<Point, String> cornerBoard = new HashMap<Point, String>();

		cornerBoard.put(getCorners()[0], null);
		cornerBoard.put(getCorners()[1], null);
		cornerBoard.put(getCorners()[2], null);
		cornerBoard.put(getCorners()[3], null);

		// first player
		PlayerThread player1 = snakeGame.getPlayers()[0];

		Snake snake1 = player1.getSnake();

		LinkedList<Point> body1 = new LinkedList<Point>();

		body1.add(new Point(2, 0));
		body1.add(new Point(1, 0));
		body1.add(new Point(0, 0));

		snake1.setLength(3);
		snake1.setSnakeBody(body1);

		PlayerThread player2 = snakeGame.getPlayers()[1];

		Snake snake2 = player2.getSnake();

		LinkedList<Point> body2 = new LinkedList<Point>();

		body2.add(new Point(snakeGame.getGame().getBoard().getWidth() - 2,
				snakeGame.getGame().getBoard().getHeight() - 1));
		body2.add(new Point(snakeGame.getGame().getBoard().getWidth() - 1,
				snakeGame.getGame().getBoard().getHeight() - 1));

		snake2.setSnakeBody(body2);

		Item[] newList = new Item[2];

		snakeGame.getGame().setItems(newList);
		newList[0] = generateItem();
		snakeGame.getGame().setItems(newList);

		System.out.println("This is the point we are looking for = "
				+ snakeGame.getGame().getItems()[0]);
		// generateItem();

		snakeGame.getPlayers()[0].start();
		snakeGame.getPlayers()[1].start();

		// // each player picks in order
		//
		// for (int i = 0; i < snakeGame.getPlayers().length; i++) {
		//
		// // send the request for corner
		// // TODO send a proper object message
		// snakeGame.sendMessage(cornerBoard);
		//
		// // This should be used for client corner selection at the player
		// // thread (below) TODO
		//
		// /*
		// * // receive the message for corner Point[] points =
		// * snakeGame.receiveMessage();
		// *
		// * // corner is now removed from the list of possible corners -
		// * since // the list is returned from the client without the
		// * selected corner setCorners(points);
		// */
		// }

		while (running) {

			// update the board every so often - 1000 so far (need to calculate
			// for approx of speed increase)

			try {

				Thread.sleep(500);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			updateBoard(board.getGrid());

		}

	}

	/**
	 * This method will calculate the snakes growth after the collection of an
	 * item.
	 */
	public void grow() {// TODO

	}

	/**
	 * This method will increase the speed that the snakes movement is
	 * calculated at.
	 */
	public void increaseSpeed() {// TODO

	}

	/**
	 * This method will decrease the speed that the snakes movement is
	 * calculated at.
	 */
	public void decreaseSpeed() {// TODO

	}

	/**
	 * This method will move the snake in the left direction and have changed
	 * the forward movement of the snake - will return the Point that the head
	 * will take.
	 */
	public Point left(Snake snake, String direction) {// TODO

		Point head = snake.getSnakeBody().get(0);

		if (direction.equals("L")) {
			if (head.y - 1 == -1) {
				return new Point(head.x, snakeGame.getBoardHeight());
			}
			return new Point(head.x, head.y + 1);
		} else if (direction.equals("R")) {
			if (head.x + 1 == snakeGame.getBoardWidth()) {
				return new Point(0, head.y);
			}
			return new Point(head.x, head.y - 1);
		} else if (direction.equals("U")) {
			if (head.x - 1 == -1) {
				return new Point(snakeGame.getBoardWidth(), head.y);
			}
			return new Point(head.x - 1, head.y);
		} else if (direction.equals("D")) {
			if (head.y + 1 == snakeGame.getBoardHeight()) {
				return new Point(head.x, 0);
			}
			return new Point(head.x + 1, head.y);
		}

		return null;

	}

	/**
	 * This method will move the snake in the right direction and have changed
	 * the forward movement of the snake
	 */
	public Point right(Snake snake, String direction) { // TODO

		Point head = snake.getSnakeBody().get(0);

		if (direction.equals("L")) {
			if (head.y + 1 == snakeGame.getBoardHeight()) {
				return new Point(head.x, 0);
			}
			return new Point(head.x, head.y - 1);
		} else if (direction.equals("R")) {
			if (head.x + 1 == snakeGame.getBoardWidth()) {
				return new Point(0, head.y);
			}
			return new Point(head.x, head.y + 1);
		} else if (direction.equals("U")) {
			if (head.y - 1 == -1) {
				return new Point(head.x, snakeGame.getBoardHeight());
			}
			return new Point(head.x + 1, head.y);
		} else if (direction.equals("D")) {
			if (head.x - 1 == -1) {
				return new Point(snakeGame.getBoardWidth(), head.y);
			}
			return new Point(head.x - 1, head.y);
		}

		return null;

	}

	/**
	 * This method will move the snake in the forward direction
	 */
	public Point forward(Snake snake, String direction) {// TODO

		Point head = snake.getSnakeBody().get(0);

		if (direction.equals("L")) {
			if (head.x - 1 == -1) {
				return new Point(snakeGame.getBoardWidth() - 1, head.y);
			}
			return new Point(head.x - 1, head.y);
		} else if (direction.equals("R")) {
			if (head.x + 1 == snakeGame.getBoardWidth()) {
				return new Point(0, head.y);
			}
			return new Point(head.x + 1, head.y);
		} else if (direction.equals("U")) {
			if (head.y - 1 == -1) {
				return new Point(head.x, snakeGame.getBoardHeight() - 1);
			}
			return new Point(head.x, head.y - 1);
		} else if (direction.equals("D")) {
			if (head.y + 1 == snakeGame.getBoardHeight()) {
				return new Point(head.x, 0);
			}
			return new Point(head.x, head.y + 1);
		}

		return null;

	}

	public synchronized String calcDirection(Snake snake) {

		Point head = snake.getSnakeBody().get(0);
		Point second = snake.getSnakeBody().get(1);

		int xValue = head.x - second.x;
		int yValue = head.y - second.y;

		if (yValue == 0) {

			if (xValue == -1) {

				System.out.println("GOING LEFT");
				System.out.println("x = " + xValue);
				System.out.println("y = " + yValue);
				return "L";

			} else if (xValue == 1) {

				System.out.println("GOING RIGHT");
				System.out.println("x = " + xValue);
				System.out.println("y = " + yValue);
				return "R";

			} else if (xValue <= -1) {

				return "R";
			} else if (xValue >= 1) {
				return "L";
			}

		} else if (xValue == 0) {

			if (yValue == -1) {

				System.out.println("GOING UP");
				return "U";

			} else if (yValue == 1) {

				System.out.println("GOING DOWN");
				return "D";

			} else if (yValue <= -1) {
				return "U";
			} else if (yValue >= 1) {
				return "D";
			}

		}

		return null;

	}

	public void moveSnake(Snake snake, String command) {

		String direction = calcDirection(snake);
		Point headPoint = null;

		for (int i = 0; i < snake.getSnakeBody().size(); i++) {

			System.out.println(snake.getSnakeBody().get(i));

		}

		System.out.println(direction + "," + command);
		if (command.equals("LEFT")) {
			// System.out.println("Now going Left");
			headPoint = left(snake, direction);
			// System.out.println(headPoint);
		} else if (command.equals("RIGHT")) {
			// System.out.println("Now going Right");
			headPoint = right(snake, direction);
			// System.out.println(headPoint);
		} else if (command.equals("FORWARD")) {
			headPoint = forward(snake, direction);
		} else {
			// System.out.println("Now going Forward");
			headPoint = forward(snake, direction);
		}

		System.out.println("Before Tailoff");

		snake.setSnakeBody(tailFollow(snake, headPoint));

		for (int i = 0; i < snake.getSnakeBody().size(); i++) {

			System.out.println(snake.getSnakeBody().get(i));

		}

	}

	public LinkedList<Point> tailFollow(Snake snake, Point headPoint) {

		LinkedList<Point> newSnake = new LinkedList<Point>();

		System.out.println("Before Snake Tail off return1");
		newSnake.add(headPoint);
		Point prev = snake.getSnakeBody().get(0);
		System.out.println("Before Snake Tail off return1");
		int size = snake.getSnakeBody().size();
		System.out.println("Over HERE = " + size);
		if (size == 2) {

			newSnake.add(prev);

		} else {
			for (int i = 1; i < size; i++) {

				System.out.println("Prev = " + prev);
				newSnake.add(prev);
				prev = snake.getSnakeBody().get(i);
				System.out.println("Prev = " + prev);
				System.out.println("Before Snake Tail off return " + i);

			}
		}

		System.out.println("Before Snake Tail off return2");

		return newSnake;

	}

	public void changeSpeed() {

	}

	/**
	 * This method will be used to calculate the collision of snakes and snakes,
	 * and of snakes and items(?)
	 */
	public void collide() {// TODO

	}

	/**
	 * This method will grab the winner and display the amount of items
	 * collected in order to win
	 */
	public void winner() {// TODO

	}

	/**
	 * This method will update the board and send the information to all clients
	 * in order to keep a persistant game board throughout all clients
	 */
	public synchronized void updateBoard(Map<Point, String> board) {// TODO

		// go through players

		Board newBoard = new Board(snakeGame.getGame().getBoard().getWidth(),
				snakeGame.getGame().getBoard().getHeight());

		String jsonString = "{\"snake\":";

		for (int i = 0; i < 2; i++) {

			PlayerThread player = snakeGame.getPlayers()[i];
			// System.out.println("Starting at variables" + i);
			// System.out.println(player);
			// // System.out.println(player.getSnake());
			// System.out.println(player.getSnake().getSnakeBody());
			// System.out.println(player.getSnake().getSnakeBody().size());
			// System.out.println("Finished at variables");

			jsonString += "{\"snake" + (i + 1) + "\":[";

			for (int j = 0; j < player.getSnake().getSnakeBody().size(); j++) {

				// newBoard.getGrid().put(player.getSnake().getSnakeBody().get(j),
				// "Player" + i);

				jsonString += "{\"x\":"
						+ player.getSnake().getSnakeBody().get(j).x + ",\"y\":"
						+ player.getSnake().getSnakeBody().get(j).y + "}";

				if (!(j == player.getSnake().getSnakeBody().size() - 1)) {

					jsonString += ",";

				}

			}
			jsonString += "]}";

		}

		jsonString += "}";
		// Collection<String> vCollection = newBoard.getGrid().values();
		// Collection<Point> kCollection = newBoard.getGrid().keySet();
		// Iterator<String> vRuns = vCollection.iterator();
		// Iterator<Point> kRuns = kCollection.iterator();

		// for (int j = 0; j < vCollection.size(); j++) {
		//
		// if (kRuns.hasNext()) {
		//
		// System.out.print(kRuns.next());
		//
		// }
		//
		// if (vRuns.hasNext()) {
		//
		// System.out.println(vRuns.next());
		//
		// }
		//
		// }

		// go through items

		Item[] items = snakeGame.getGame().getItems();

		jsonString += "{\"item\":";

		for (int i = 0; i < snakeGame.getGame().getItemCount(); i++) {

			// newBoard.getGrid().put(items[i].getLocation(), "ITEM");

			System.out.println("1 = " + items[i]);
			System.out.println("2 = " + items[i].getLocation());
			System.out.println("3 = " + items[i].getLocation().x);
			System.out.println("4 = " + items[i].getLocation().y);

			jsonString += "{\"item" + (i + 1) + "\":[{\"x\":"
					+ items[i].getLocation().x + ",\"y\":"
					+ items[i].getLocation().y + "]}";

			if (!(i == snakeGame.getGame().getItems().length - 1)) {

				jsonString += ",";

			}

		}

		System.out.println(jsonString);

		snakeGame.getGame().setBoard(newBoard);

		snakeGame.sendMessage(snakeGame.getGame().getBoard().getGrid(),
				snakeGame);

	}

	/**
	 * This method will display the game board, mainly for debugging if used at
	 * all
	 */
	public void displayBoard() {// TODO

	}

	/**
	 * This method will generate an item. Should be used repeatedly in order to
	 * generate a new location for existing items that have just been collected.
	 * 
	 * @return
	 */
	public Item generateItem() {// TODO

		Random random = new Random();
		boolean notFound = true;

		Point randomLocation = null;

		while (notFound) {

			int x = random.nextInt(snakeGame.getGame().getBoard().getWidth());
			int y = random.nextInt(snakeGame.getGame().getBoard().getHeight());

			int num = 0;

			randomLocation = new Point(x, y);

			// searching through points on the map.

			for (int i = 0; i < 2; i++) {

				PlayerThread player = snakeGame.getPlayers()[i];

				for (int j = 0; j < player.getSnake().getSnakeBody().size(); j++) {

					if (player.getSnake().getSnakeBody().get(j) == randomLocation) {

						num++;

					}

				}

			}

			System.out.println("Item C0unt = "
					+ snakeGame.getGame().getItemCount());

			if (snakeGame.getGame().getItemCount() == 0) {

				for (int i = 0; i < snakeGame.getGame().getItemCount(); i++) {

					// newBoard.getGrid().put(items[i].getLocation(), "ITEM");

					if (items[i].getLocation() == randomLocation) {

						num++;

					}

				}

			}

			if (num == 0) {

				notFound = false;
			}

		}

		Item newItem = new Item(randomLocation.x, randomLocation.y);
		setItemCount(getItemCount() + 1);

		return newItem;

	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

	public Game getSnakeGame() {
		return snakeGame;
	}

	public void setSnakeGame(Game snakeGame) {
		this.snakeGame = snakeGame;
	}

	public Point[] getCorners() {
		return corners;
	}

	public void setCorners(Point[] corners) {
		this.corners = corners;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

}
