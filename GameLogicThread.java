package Server;

import java.awt.Point;

public class GameLogicThread extends Thread {

	private Board board;
	private Item[] items;
	private Game snakeGame;
	private Point[] corners;

	private int itemCount;

	public GameLogicThread(Game snakeGame) {

		setBoard(new Board(100, 100));
		setItems(new Item[snakeGame.getMaxPlayers() - 1]);
		setItemCount(0);
		setSnakeGame(snakeGame);

	}

	/**
	 * The overridden method of the Thread Class used to run a game thread
	 */
	public void run() {// TODO

		// game started

		// generate a list of possible corners

		setCorners(new Point[4]);

		getCorners()[0] = new Point(0, 0);
		getCorners()[1] = new Point(0, snakeGame.getBoardHeight());
		getCorners()[2] = new Point(snakeGame.getBoardWidth(), 0);
		getCorners()[3] = new Point(snakeGame.getBoardWidth(),
				snakeGame.getBoardHeight());

		// each player picks in order

		for (int i = 0; i < snakeGame.getPlayers().length; i++) {

			// send the request for corner
			// TODO send a proper object message
			snakeGame.sendMessage(getCorners());

			// receive the message for corner
			Point[] points = (Point[]) snakeGame.receiveMessage();

			// corner is now removed from the list of possible corners - since
			// the list is returned from the client without the selected corner
			setCorners(points);
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
	 * the forward movement of the snake
	 */
	public void left() {// TODO

	}

	/**
	 * This method will move the snake in the right direction and have changed
	 * the forward movement of the snake
	 */
	public void right() {// TODO

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
	public void updateBoard() {// TODO

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
	 */
	public void generateItem() {// TODO

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

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
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

}
