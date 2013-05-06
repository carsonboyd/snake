import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;



public class GameLogicThread extends Thread {

static int count = 1;

	private Board board; // the board that the snakes will be travelling along
	private Item[] items; // the list of positions of the items on the map
	private Snake[] snakes = new Snake[0]; // list of snakes
	private Game snakeGame;
	private Point[] corners;

	private int itemCount;

	public GameLogicThread(Game snakeGame) {

		setBoard(new Board(snakeGame.getBoardWidth(),
				snakeGame.getBoardHeight()));
		setItems(new Item[snakeGame.getMaxPlayers() - 1]);
		setSnakeGame(snakeGame);
		setSnakes(new Snake[snakeGame.getMaxPlayers()]); // TODO generalise the number of players

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

		PlayerThread player;
		for (int i = 0; i < this.snakes.length; i++)
		{
			player = snakeGame.getPlayers()[i];
			Snake snake = player.getSnake();
			LinkedList<Point> body = new LinkedList<Point>();
			Point head;

			if (player.position == "NW")
			{
				body.add(new Point(10, 5));
				body.add(new Point(9, 5));
				body.add(new Point(8, 5));
				body.add(new Point(7, 5));
				snake.setSnakeBody(body);
				head = snake.getSnakeBody().get(0);
				head  = right(snake, "U");
				snake.setSnakeBody(tailFollow(snake, head));
			}
			else if (player.position == "NE")
			{
				body.add(new Point(40, 8));
				body.add(new Point(40, 7));
				body.add(new Point(40, 6));
				body.add(new Point(40, 5));
				snake.setSnakeBody(body);
				head = snake.getSnakeBody().get(0);
				head  = forward(snake, "D");
				snake.setSnakeBody(tailFollow(snake, head));
			}
			else if (player.position == "SE")
			{
				body.add(new Point(40, 40));
				body.add(new Point(39, 40));
				body.add(new Point(38, 40));
				body.add(new Point(37, 40));
				snake.setSnakeBody(body);
				head = snake.getSnakeBody().get(0);
				head  = left(snake, "U");
				snake.setSnakeBody(tailFollow(snake, head));
			}
			else if (player.position == "SW")
			{
				body.add(new Point(10, 37));
				body.add(new Point(10, 38));
				body.add(new Point(10, 39));
				body.add(new Point(10, 40));
				snake.setSnakeBody(body);
				head = snake.getSnakeBody().get(0);
				head  = forward(snake, "U");
				snake.setSnakeBody(tailFollow(snake, head));
			}
		}

		Item[] newList = new Item[4];

		snakeGame.getGame().setItems(newList);
		newList[0] = generateItem();
		newList[1] = generateItem();
		newList[2] = generateItem();
		newList[3] = generateItem();
		snakeGame.getGame().setItems(newList);
		setItemCount(4);

		// System.out.println("This is the point we are looking for = "
		//		+ snakeGame.getGame().getItems()[0]);
		// generateItem();

		for (int i = 0; i < this.snakes.length; i++)
			snakeGame.getPlayers()[i].start();

		while (running) {

			// update the board every so often - 1000 so far (need to calculate
			// for approx of speed increase)

			try {

				Thread.sleep(200);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

			updateBoard(board.getGrid());

		}

	}

	/**
	 * This method will calculate the snakes growth after the collection of an
	 * item.
	 * 
	 * @param game
	 * @param snake
	 */
	public void grow(Snake snake, Game game) {
		// TODO

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
	public Point left(Snake snake, String direction) {

		Point head = snake.getSnakeBody().get(0);

		if (direction.equals("L")) {
			if (head.y + 1 == snakeGame.getBoardHeight()) {
				return new Point(head.x, 0);
			}
			return new Point(head.x, head.y + 1);
		} else if (direction.equals("R")) {
			if (head.y - 1 == -1) {
				return new Point(head.x, snakeGame.getBoardHeight() - 1);
			}
			return new Point(head.x, head.y - 1);
		} else if (direction.equals("U")) {
			if (head.x - 1 == -1) {
				return new Point(snakeGame.getBoardWidth() - 1, head.y);
			}
			return new Point(head.x - 1, head.y);
		} else if (direction.equals("D")) {

			if (head.x + 1 == snakeGame.getBoardWidth()) {
				return new Point(0, head.y);
			}
			return new Point(head.x + 1, head.y);
		}

		return null;

	}

	/**
	 * This method will move the snake in the right direction and have changed
	 * the forward movement of the snake
	 */
	public Point right(Snake snake, String direction) {

		Point head = snake.getSnakeBody().get(0);

		if (direction.equals("L")) {
			if (head.x - 1 == -1) {
				return new Point(snakeGame.getBoardWidth() - 1, head.y);
			}
			return new Point(head.x, head.y - 1);
		} else if (direction.equals("R")) {
			if (head.y + 1 == snakeGame.getBoardHeight()) {
				return new Point(head.x, 0);
			}
			return new Point(head.x, head.y + 1);
		} else if (direction.equals("U")) {
			if (head.x + 1 == snakeGame.getBoardWidth()) {
				return new Point(0, head.y);
			}
			return new Point(head.x + 1, head.y);
		} else if (direction.equals("D")) {
			if (head.y - 1 == -1) {
				return new Point(head.x, snakeGame.getBoardHeight() - 1);
			}
			return new Point(head.x - 1, head.y);
		}

		return null;

	}

	/**
	 * This method will move the snake in the forward direction
	 */
	public Point forward(Snake snake, String direction) {

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

		System.out.println(count++);
		System.out.println(snake.getSnakeBody());

		Point head = snake.getSnakeBody().get(0);
		Point second = snake.getSnakeBody().get(1);

		int xValue = head.x - second.x;
		int yValue = head.y - second.y;

		if (yValue == 0) {

			if (xValue == -1) {

				// System.out.println("GOING LEFT");
				// System.out.println("x = " + xValue);
				// System.out.println("y = " + yValue);
				return "L";

			} else if (xValue == 1) {

				// System.out.println("GOING RIGHT");
				// System.out.println("x = " + xValue);
				// System.out.println("y = " + yValue);
				return "R";

			} else if (xValue <= -1) {

				return "R";
			} else if (xValue >= 1) {
				return "L";
			}

		} else if (xValue == 0) {

			if (yValue == -1) {

				// System.out.println("GOING UP");
				return "U";

			} else if (yValue == 1) {

				// System.out.println("GOING DOWN");
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

			// System.out.println(snake.getSnakeBody().get(i));

		}

		// System.out.println(direction + "," + command);
		if (command.equals("LEFT")) {
			// // System.out.println("Now going Left");
			headPoint = left(snake, direction);
			// // System.out.println(headPoint);
		} else if (command.equals("RIGHT")) {
			// // System.out.println("Now going Right");
			headPoint = right(snake, direction);
			// // System.out.println(headPoint);
		} else if (command.equals("FORWARD")) {
			headPoint = forward(snake, direction);
		} else {
			// // System.out.println("Now going Forward");
			headPoint = forward(snake, direction);
		}

		// System.out.println("Before Tailoff");

		snake.setSnakeBody(tailFollow(snake, headPoint));

		for (int i = 0; i < snake.getSnakeBody().size(); i++) {

			// System.out.println(snake.getSnakeBody().get(i));

		}

	}

	public LinkedList<Point> tailFollow(Snake snake, Point headPoint) {

		LinkedList<Point> newSnake = new LinkedList<Point>();

		// System.out.println("Before Snake Tail off return1");
		newSnake.add(headPoint);
		Point prev = snake.getSnakeBody().get(0);
		// System.out.println("Before Snake Tail off return1");
		int size = snake.getSnakeBody().size();
		// System.out.println("Over HERE = " + size);
		if (size == 2) {

			newSnake.add(prev);

		} else {
			for (int i = 1; i < size; i++) {

				// System.out.println("Prev = " + prev);
				newSnake.add(prev);
				prev = snake.getSnakeBody().get(i);
				// System.out.println("Prev = " + prev);
				// System.out.println("Before Snake Tail off return " + i);

			}
		}

		// System.out.println("Before Snake Tail off return2");

		return newSnake;

	}

	public void changeSpeed() {

	}

	/**
	 * This method will be used to calculate the collision of snakes and snakes,
	 * and of snakes and items(?)
	 */
	public synchronized void collide() {// TODO

		// check against others
		for (Snake solidSnake : snakes)
		{
			Point solidHead = solidSnake.getSnakeBody().get(0);

			for (Snake liquidSnake : snakes)
			{
				if (solidSnake == liquidSnake)
					continue;

				Point liquidHead = liquidSnake.getSnakeBody().get(0);

				if (solidHead.x == liquidHead.x && solidHead.y == liquidHead.y) 
				{
					System.out.println("Face off");

					// Check direction
					String solidDirection = calcDirection(solidSnake);
					String liquidDirection = calcDirection(liquidSnake);

					if (solidDirection == "L" && liquidDirection == "R")
					{
						if (solidSnake.getSnakeBody().size() > liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
						else if (solidSnake.getSnakeBody().size() == liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
					}
					else if (solidDirection == "R" && liquidDirection == "L")
					{
						if (solidSnake.getSnakeBody().size() > liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
						else if (solidSnake.getSnakeBody().size() == liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
					}
					else if (solidDirection == "U" && liquidDirection == "D")
					{
						if (solidSnake.getSnakeBody().size() > liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
						else if (solidSnake.getSnakeBody().size() == liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
					}
					else if (solidDirection == "D" && liquidDirection == "U")
					{
						if (solidSnake.getSnakeBody().size() > liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
						else if (solidSnake.getSnakeBody().size() == liquidSnake.getSnakeBody().size())
						{
							liquidSnake.setState(false);
						}
					}
					else 
					{
						
					}
				}

				for (Point liquidBody : liquidSnake.getSnakeBody())
				{
					if (solidHead.x == liquidBody.x && solidHead.y == liquidBody.y)
					{
						solidSnake.setState(false);
					}	
				}
			}
		}


		for (int i = 0; i < snakes.length; i++) {

			Point headPoint = snakes[i].getSnakeBody().get(0);

			// check itself

			for (int j = 1; j < snakes[i].getSnakeBody().size(); j++) {

				Point bodyPoint = snakes[i].getSnakeBody().get(j);

				if (headPoint.x == bodyPoint.x && headPoint.y == bodyPoint.y) {

					snakes[i].setState(false);
					// snakeCollision(snakes[i], snakes[i], snakeGame);

				}

			}

			// check for items
			for (int j = 0; j < itemCount; j++) {

				Point itemPoint = items[j].getLocation();
				if (headPoint.x == itemPoint.x && headPoint.y == itemPoint.y) 
				{
					items[j] = generateItem();
					snakes[i].grow(new Point(itemPoint.x, itemPoint.y));
					break;
				}
			}
		}
	}

	public void itemCollision(Snake snake, Point item, Game game) {

		// grow(snake, game);
		removeItem(item); // in removeItem generateItem

	}

	public synchronized void snakeCollision(Snake snake1, Snake snake2,
			Game game) {

		if (snake1 == snake2) {

			// self collision

			System.out.println("Self Collision");
			removePlayer(snake1, game);

		} else {

			// other snake collision

			if (snake1.getLength() > snake2.getLength()) {
				System.out.println("Snake 2");
				removePlayer(snake2, game);
			} else {
				System.out.println("Snake 1");
				removePlayer(snake1, game);
			}

		}

	}

	private void removePlayer(Snake snake, Game game) {

		int snakeNum = 0;
		for (int i = 0; i < game.getPlayerCount() - 1; i++) {
			// System.out.println(game.getPlayers());
			// System.out.println(game.getPlayers()[0]);
			// System.out.println(game.getPlayers()[0].getSnake());
			// System.out.println(i);

			if (game.getPlayers()[i].getSnake() == snake) {
				snakeNum = i;
			}

		}

		game.getPlayers()[snakeNum] = null;
		game.setPlayerCount(game.getPlayerCount() - 1);
		// implement null check for multiple players(?)
		if (game.getPlayerCount() == 1) {
			winner();
		}

	}

	/**
	 * This method will grab the winner and display the amount of items
	 * collected in order to win
	 */
	public void winner() {// TODO

		System.out.println("There is a winner");
		System.exit(0);

	}

	/**
	 * This method will update the board and send the information to all clients
	 * in order to keep a persistant game board throughout all clients
	 */
	public synchronized void updateBoard(Map<Point, String> board) {// TODO

		if (snakes[0] != null) {
			collide();
		}

		// go through players

		Board newBoard = new Board(snakeGame.getGame().getBoard().getWidth(),
				snakeGame.getGame().getBoard().getHeight());

		String jsonString = "{\"snake\":";

		for (int i = 0; i < snakeGame.getMaxPlayers(); i++) { // TODO generalise for number of players

			PlayerThread player = snakeGame.getPlayers()[i];

			snakes[i] = player.getSnake();

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

		// go through items

		Item[] items = snakeGame.getGame().getItems();

		jsonString += "{\"food\":[";

		for (int i = 0; i < snakeGame.getGame().getItemCount(); i++) {

			// newBoard.getGrid().put(items[i].getLocation(), "ITEM");

			// System.out.println("1 = " + items[i]);
			// System.out.println("2 = " + items[i].getLocation());
			// System.out.println("3 = " + items[i].getLocation().x);
			// System.out.println("4 = " + items[i].getLocation().y);

			jsonString += "{\"x\":" + items[i].getLocation().x + ",\"y\":"
					+ items[i].getLocation().y + "]}";

			if (!(i == snakeGame.getGame().getItems().length - 1)) {

				jsonString += ",";

			}

		}
		jsonString += "]";

		// System.out.println(jsonString);

		snakeGame.getGame().setBoard(newBoard);

		snakeGame.sendMessage(snakeGame.getGame().getBoard().getGrid(),
				snakeGame);

	}

	/**
	 * This method will display the game board, mainly for debugging if used at
	 * all
	 */
	public void displayBoard() {

	}

	public void removeItem(Point item) {

		System.out.println(item);
		try 
		{
			generateItem();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}	
		// removal of item from the list
		// generate an item from the method to add to the list.

	}

	/**
	 * This method will generate an item. Should be used repeatedly in order to
	 * generate a new location for existing items that have just been collected.
	 * 
	 * @return
	 */
	public Item generateItem() {// TODO
		// i think has been completed(?)

		Random random = new Random();
		boolean notFound = true;

		Point randomLocation = null;

		while (notFound) {

			int x = random.nextInt(snakeGame.getGame().getBoard().getWidth());
			int y = random.nextInt(snakeGame.getGame().getBoard().getHeight());

			int num = 0;

			randomLocation = new Point(x, y);

			// searching through points on the map.

			for (int i = 0; i < snakeGame.getMaxPlayers(); i++) {

				PlayerThread player = snakeGame.getPlayers()[i];

				for (int j = 0; j < player.getSnake().getSnakeBody().size(); j++) {

					if (player.getSnake().getSnakeBody().get(j) == randomLocation) {

						num++;

					}

				}

			}

			// System.out.println("Item C0unt = "
			//		+ snakeGame.getGame().getItemCount());

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
		// setItemCount(getItemCount() + 1);

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

	public Snake[] getSnakes() {
		return snakes;
	}

	public void setSnakes(Snake[] snakes) {
		this.snakes = snakes;
	}

}
