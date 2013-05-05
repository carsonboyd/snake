import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public final class SnakePanel extends JPanel {
	private final static int SIZE = 300;
	private final static int ROWS = 11; // need to add one extra
	private SnakeModel model;

	public SnakePanel(SnakeModel model) {
		this.model = model;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = SIZE / ROWS;
		// paint cells
		g.setColor(Color.lightGray);
		for (int i = 0; i < ROWS; i++) {
			g.drawLine(i * width, 0, i * width, SIZE);
			g.drawLine(0, i * width, SIZE, i * width);
		}

		// paint walls
		g.setColor(Color.gray);
		List<Point> wallDots = model.getWallDots();
		for (Point wallDot : wallDots) {
			paintDots(wallDot, g);
		}

		// paint apples
		g.setColor(Color.red);
		List<Point> appleDots = model.getAppleDots();
		for (Point appleDot : appleDots) {
			paintDots(appleDot, g);
		}

		// paint snakes
		List<List<Point>> snakePoints = model.getSnakePoints();
		for (int i = 0; i < snakePoints.size(); i++) {
			Color snakeColor;
			if (i == 0) {
				snakeColor = Color.blue;
			} else if (i == 1) {
				snakeColor = Color.orange;
			} else if (i == 2) {
				snakeColor = Color.green;
			} else {
				snakeColor = Color.pink;
			}

			g.setColor(snakeColor);
			for (Point snakeDot : snakePoints.get(i)) {
				paintDots(snakeDot, g);
			}
		}
	}

	private void paintDots(Point p, Graphics g) {
		int cellSize = SIZE / ROWS;
		g.fillRect(p.getX() * cellSize, p.getY() * cellSize, cellSize, cellSize);
	}
}
