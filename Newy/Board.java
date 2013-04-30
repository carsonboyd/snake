
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Board {

	private Map<Point, String> grid; // The game board
	private int width;
	private int height;

	public Board(int width, int height) {

		Map<Point, String> newGrid = new HashMap<Point, String>();
		setWidth(width);
		setHeight(height);
		setGrid(newGrid);

	}

	public Map<Point, String> getGrid() {
		return grid;
	}

	public void setGrid(Map<Point, String> newGrid) {
		this.grid = newGrid;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
