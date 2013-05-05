

import java.util.ArrayList;
import java.util.List;

public final class SnakeModel {
    private List<List<Point>> snakePoints = new ArrayList<List<Point>>();
    private List<Point> appleDots = new ArrayList<Point>();
    private List<Point> wallDots = new ArrayList<Point>();
    
    public SnakeModel() {
        for(int i = 0; i < 4; i++) {
            List<Point> snake = new ArrayList<Point>();
            snakePoints.add(snake);
        }
    }
    
    public void setSnakeOnePoints(List<Point> snakeOnePoints) {
        snakePoints.set(0, snakeOnePoints);
    }
    
    public void setSnakeTwoPoints(List<Point> snakeTwoPoints) {
        snakePoints.set(1, snakeTwoPoints);
    }
    
    public void setSnakeThreePoints(List<Point> snakeThreePoints) {
        snakePoints.set(2, snakeThreePoints);
    }

    public void setSnakeFourPoints(List<Point> snakeFourPoints) {
        snakePoints.set(3, snakeFourPoints);
    }
    
    public void setAppleDots(List<Point> appleDots) {
        this.appleDots = appleDots;
    }
    
    public void setWallDots(List<Point> wallDots) {
        this.wallDots = wallDots;
    }
    
    public List<List<Point>> getSnakePoints() {
        return snakePoints;
    }
    
    public List<Point> getAppleDots() {
        return appleDots;
    }
    
    public List<Point> getWallDots() {
        return wallDots;
    }
}