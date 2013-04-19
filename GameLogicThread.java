package Server;

public class GameLogicThread extends Thread {

	private Board board;
	private Item[] items;

	/**
	 * The overridden method of the Thread Class used to run a game thread
	 */
	public void run() {

	}

	/**
	 * This method will calculate the snakes growth after the collection of an
	 * item.
	 */
	public void grow() {

	}

	/**
	 * This method will increase the speed that the snakes movement is
	 * calculated at.
	 */
	public void increaseSpeed() {

	}

	/**
	 * This method will decrease the speed that the snakes movement is
	 * calculated at.
	 */
	public void decreaseSpeed() {

	}

	/**
	 * This method will move the snake in the left direction and have changed
	 * the forward movement of the snake
	 */
	public void left() {

	}

	/**
	 * This method will move the snake in the right direction and have changed
	 * the forward movement of the snake
	 */
	public void right() {

	}

	/**
	 * This method will be used to calculate the collision of snakes and snakes,
	 * and of snakes and items(?)
	 */
	public void collide() {

	}

	/**
	 * This method will grab the winner and display the amount of items
	 * collected in order to win
	 */
	public void winner() {

	}

	/**
	 * This method will update the board and send the information to all clients
	 * in order to keep a persistant game board throughout all clients
	 */
	public void updateBoard() {

	}

	/**
	 * This method will display the game board, mainly for debugging if used at
	 * all
	 */
	public void displayBoard() {

	}

	/**
	 * This method will generate an item. Should be used repeatedly in order to
	 * generate a new location for existing items that have just been collected.
	 */
	public void generateItem() {

	}

	// TODO add extra methods - according to class diagram

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

}
