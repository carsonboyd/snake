package Server;

public class Board {

	private int[][] grid; // The game board

	public Board(int width, int height) {

		int[][] newGrid = new int[width][height];
		setGrid(newGrid);

	}

	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

}
