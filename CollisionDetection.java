
import java.util.ArrayList;

public class CollisionDetector
{
    private ArrayList<Snake> snakes;
    private Board map;

    public CollisionDetector(ArrayList<Snake> snakes, Board map)
    {
        this.snakes = snakes;
        this.map = map;
    }

    public boolean selfCollision(Snake snake)
    {
        for (int i = 1; i < snake.length; i++) {
            if (snake.getSnakeHead().getX() == snake.getSnakeBody().get(i).getX() &&
                    snake.getSnakeHead().getY() == snake.getSnakeBody().get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean otherCollision()
    {
    	 for (Snake snake : this.snakes) {
            if (snake.getHead().getX()+5 >= wall.getX() &&
                    snake.getHead().getY()+5 >= wall.getY() &&
                    snake.getHead().getX()+ 5 <= wall.getX() + wall.getWidth() &&
                    snake.getHead().getY()+ 5 <= wall.getY() + wall.getHeight()) {
                return true;
            }
        }
        return false;
    }

    public boolean headOnCollision()
    {
    	 for (Snake snake : this.snakes) {

            if (snake.getSnakeHead().getX() >= wall.getX() &&
                    snake.getHead().getY()+5 >= wall.getY() &&
                    snake.getHead().getX()+ 5 <= wall.getX() + wall.getWidth() &&
                    snake.getHead().getY()+ 5 <= wall.getY() + wall.getHeight()) {
                return true;
            }
        }
        return false;
    }
}