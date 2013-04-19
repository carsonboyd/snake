package Server;

public class Snake {

	private int length; //the length of the snake
	private int itemsEaten; // a record of how many items eaten for the endgame

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getItemsEaten() {
		return itemsEaten;
	}

	public void setItemsEaten(int itemsEaten) {
		this.itemsEaten = itemsEaten;
	}

}
