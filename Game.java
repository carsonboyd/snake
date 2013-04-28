package server;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

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
		snakeGame.setPlayers(new PlayerThread[4]);
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
		snakeGame.setGame(gameLogic);

		// starting the game
		gameLogic.start();

	}

	/**
	 * Will be the general sendMessage method used to send a board update to all
	 * clients
	 * 
	 * @param cornerBoard
	 *            the board object specifying the location of objects in the
	 *            game
	 */
	public void sendMessage(Map<Point, String> board) { // TODO

		// TODO create serialisable objects that use the objects that can be
		// used, such as intBox (for int numbers), or locBox (for locations,
		// that players can send info where they want to be placed (corners)
		// or where they are heading next (left or right move))

		// TODO for other objects just send Object objects and then cast in
		// order to receive the intended object?

		for (int i = 0; i < players.length; i++) {

			// open up a stream to be used to send the data to the players.
			try {

				ObjectOutputStream oos = new ObjectOutputStream(players[i]
						.getSocket().getOutputStream());
				oos.writeObject(board);
				oos.close();

			} catch (IOException io) {

				io.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

	/**
	 * will be used to send a string message to all clients
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {

		for (int i = 0; i < players.length; i++) {

			// open up a stream to be used to send the data to the players.
			try {

				ObjectOutputStream oos = new ObjectOutputStream(players[i]
						.getSocket().getOutputStream());
				oos.writeObject(message);
				oos.close();

			} catch (IOException io) {

				io.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

	/**
	 * Will be the general receiveMessage method used to receive a message from
	 * a client
	 * 
	 * @return
	 */
	public String receiveMessage(Socket socket) {// TODO

		String message = null;

		try {
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());
			message = (String) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {

			e.printStackTrace();
		}

		return message;

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

					System.out.println(snakeGame.getPlayers().length);
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

						// should be put into the playerthread class TODO

						// receive message from client - should be a number
						/*
						 * Object size = snakeGame.receiveMessage(); // TODO
						 * create a message for this (above) & object for // the
						 * message
						 * 
						 * // create a player list of size received
						 * 
						 * snakeGame.setPlayers(new PlayerThread[(int) size]);
						 * snakeGame.setMaxPlayers((int) size);
						 */

					}

					PlayerThread mini = new PlayerThread(clientSocket,
							snakeGame.getServer(), snakeGame);

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

	/**
	 * The method for the playerthread to request a change to their speed or
	 * direction
	 * 
	 * @param player
	 *            - the client
	 * @param action
	 */
	public synchronized void requestChange(PlayerThread player, String action) {// TODO

		switch (action) {

		case "LEFT":
			System.out.println("Left Move");
			game.moveSnake(player.getSnake(), action);
			break;

		case "RIGHT":
			System.out.println("Right Move");
			game.moveSnake(player.getSnake(), action);
			break;

		case "INCREASE":
			System.out.println("Increase Speed");
			break;

		case "DECREASE":
			System.out.println("Decrease Speed");
			break;
		}

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
