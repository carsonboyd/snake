package server;

import java.awt.Point;
import java.util.LinkedList;

public class Snake {

	private LinkedList<Point> snakeBody;
	private int length; // the length of the snake
	private int itemsEaten; // a record of how many items eaten for the endgame

	public Snake() {

		setSnakeBody(new LinkedList<Point>());
		setLength(2);
		setItemsEaten(0);

	}

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

	public LinkedList<Point> getSnakeBody() {
		return snakeBody;
	}

	public void setSnakeBody(LinkedList<Point> snakeBody) {
		this.snakeBody = snakeBody;
	}

}
