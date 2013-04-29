package server;

import java.awt.Point;

public class Item {

	private Point location;

	public Item(int x, int y) {

		setLocation(new Point(x, y));

	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

}
