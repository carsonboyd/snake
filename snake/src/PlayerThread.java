import java.net.ServerSocket;
import java.net.Socket;

public class PlayerThread extends Thread {

	private Socket socket; // the socket of the client
	private ServerSocket serverSocket;
	private Game snakeGame;
	private Snake snake;
	public String position = "SE";

	public PlayerThread(Socket clientSocket, ServerSocket serverSocket,
			Game snakeGame) {

		super("PlayerThread");
		this.setSocket(clientSocket);
		this.setServerSocket(serverSocket);
		this.setSnakeGame(snakeGame);
		this.setSnake(new Snake());

		// System.out.println(clientSocket);
		// System.out.println(serverSocket);
		// System.out.println(snakeGame);
		// System.out.println(getSnake());

	}

	/**
	 * An overridden method that will create a thread from which all the client
	 * instructions will be performed
	 */

	public synchronized void run() {// TODO

		boolean listening = true;
		boolean winner = false;

		String message = null;

		while (listening) {

			try {

				// receive message
				// System.out.println("Message block!!!");
				message = snakeGame.receiveMessage(getSocket());

			} catch (Exception e) {

				e.printStackTrace();

			}

			// requestUpdate on the message to the GameLogicThread
			try {
				// System.out.println("CARSONSKADKDKSJD ==== " + message);

				snakeGame.requestChange(this, message);
				message = null;

			} catch (NullPointerException npe) {

				npe.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			}

			// repeat, if no errors or end cause of a winner

			// if ( !(winner = false) ) {
			//
			// listening = false;
			//
			// }

		}

		// System.out.println("I hope carson isn;t here");

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public Game getSnakeGame() {
		return snakeGame;
	}

	public void setSnakeGame(Game snakeGame) {
		this.snakeGame = snakeGame;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public String toString() {

		return "PlayerThread[ClientSocket=" + getSocket() + "/Snake="
				+ getSnake();

	}

}
