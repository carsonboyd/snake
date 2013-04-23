package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Game {

	private ServerSocket server;
	private PlayerThread[] players;
	private GameLogicThread game;

	private int boardWidth;
	private int boardHeight;

	private int playerCount = 0;

	private int maxPlayers = 2;

	/**
	 * Constructor method that initialises the Game Object
	 */

	public Game() {

		setServer(null);
		setPlayers(null);
		setGame(null);
		setBoardWidth(100);
		setBoardHeight(100);

	}

	/**
	 * This is the main class of the server, will create a determined number of
	 * player threads and then create a game thread
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Game snakeGame = new Game();

		snakeGame.setServer(null);
		int numClients = 0;
		try {

			snakeGame.setServer(new ServerSocket(8000));

		} catch (IOException e) {

			e.printStackTrace();
		}

		boolean listeningSocket = true;

		while (listeningSocket) {

			System.out.print("Start " + numClients);
			numClients = addPlayer(snakeGame, numClients);
			numClients = numClients + 1;
			System.out.println(" Finish " + numClients);

			if (snakeGame.getPlayerCount() == snakeGame.getMaxPlayers()) {

				listeningSocket = false;
			}

		}

		// create a game thread in order to start the game

		GameLogicThread gameLogic = new GameLogicThread(snakeGame);

		// starting the game
		gameLogic.start();

	}

	/**
	 * Will be the general sendMessage method used to send a message to a client
	 */
	public void sendMessage(Object object) { // TODO

		// TODO create serialisable objects that use the objects that can be
		// used, such as intBox (for int numbers), or locBox (for locations,
		// that players can send info where they want to be placed (corners)
		// or where they are heading next (left or right move))

		// TODO for other objects just send Object objects and then cast in
		// order to receive the intended object?

	}

	/**
	 * Will be the general receiveMessage method used to receive a message from
	 * a client
	 * 
	 * @return
	 */
	public Object receiveMessage() {// TODO

		return null;

	}

	/**
	 * This method will add a new player thread and count the amount of players
	 * in total. It will place the player into the list of players already in
	 * the game.
	 * 
	 * @param serverSocket
	 *            - The socket of the server to pass to the client
	 * @param numClients
	 *            - the total number of clients and the number of the client is
	 *            passed on
	 * @return This will return the int number of the total number of clients in
	 *         the game.
	 */
	public static synchronized int addPlayer(Game snakeGame, int numClients) {

		Socket clientSocket = null;

		if (numClients > snakeGame.getMaxPlayers()) {

			try {

				// I think since there is still a socket
				// it must erase the first object to create a new one
				System.out.println(" Disconnecting Next Server...");
				clientSocket = snakeGame.getServer().accept();
				clientSocket.close();
				numClients = numClients - 1;
				return numClients;

			} catch (IOException ioe) {

				ioe.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			}

		} else {

			try {

				clientSocket = snakeGame.getServer().accept();

			} catch (SocketException se) {

				try {

					Thread.sleep(1000);

				} catch (InterruptedException e) {

					e.printStackTrace();

				}

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				if (clientSocket != null) {

					// initialise the players array, determined by the first
					// player
					if (snakeGame.getPlayers().length == 0) {

						// send message to client to request a number of players
						// to join
						String message = new String("RequestMessage"); // TODO
																		// create
																		// a
																		// proper
																		// object
																		// message
						snakeGame.sendMessage(message);

						// receive message from client - should be a number

						Object size = snakeGame.receiveMessage();
						// TODO create a message for this (above) & object for
						// the message

						// create a player list of size received

						snakeGame.setPlayers(new PlayerThread[(int) size]);
						snakeGame.setMaxPlayers((int) size);

					}

					PlayerThread mini = new PlayerThread(clientSocket,
							snakeGame.getServer());

					// include the first player as number one in the list.

					snakeGame.getPlayers()[snakeGame.getPlayerCount()] = mini;

					// increase player counter in order to place players in
					// order.

					snakeGame.setPlayerCount(snakeGame.getPlayerCount() + 1);
					mini.start();

				}

			}

		}

		return numClients;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public PlayerThread[] getPlayers() {
		return players;
	}

	public void setPlayers(PlayerThread[] players) {
		this.players = players;
	}

	public GameLogicThread getGame() {
		return game;
	}

	public void setGame(GameLogicThread game) {
		this.game = game;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}

	public void setBoardHeight(int boardHeight) {
		this.boardHeight = boardHeight;
	}

}
