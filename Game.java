package server;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
		setBoardWidth(55);
		setBoardHeight(55);

	}

	/**
	 * This is the main class of the server, will create a determined number of
	 * player threads and then create a game thread
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Game snakeGame = new Game();

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
			// numClients = numClients + 1;
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
	public synchronized void sendMessage(Map<Point, String> board) { // TODO

		// TODO create serialisable objects that use the objects that can be
		// used, such as intBox (for int numbers), or locBox (for locations,
		// that players can send info where they want to be placed (corners)
		// or where they are heading next (left or right move))

		// TODO for other objects just send Object objects and then cast in
		// order to receive the intended object?

		ObjectOutputStream oos = null;

		for (int i = 0; i < 2; i++) {

			// open up a stream to be used to send the data to the players.
			try {
				System.out.println("Carson = "
						+ players[i].getSocket().getOutputStream());
				oos = new ObjectOutputStream(players[i].getSocket()
						.getOutputStream());
				oos.writeObject(board);

			} catch (IOException io) {

				io.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			}
			// try {
			// oos.close();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

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
	 * @throws JSONException
	 */
	public String receiveMessage(Socket socket) throws JSONException {// TODO

		BufferedReader inputReader = null;
		String message = null;
		String direction = null;

		System.out.println();
		System.out.println(socket.getLocalPort());
		System.out.println(socket.getPort());
		System.out.println();

		try {
			inputReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String receivedMessage = null;
		try {
			receivedMessage = inputReader.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("Carson was Here !!!! " + receivedMessage);

		if (receivedMessage.contains("direction")) {
			try {
				JSONObject jsonObj = new JSONObject(receivedMessage);
				direction = (String) jsonObj.get("direction");
				System.out.println(direction);

				switch (direction) {

				case "l":
					direction = "LEFT";
					break;

				case "r":
					direction = "RIGHT";
					break;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ObjectInputStream ois = null;
		//
		// try {
		//
		// if (!socket.getInputStream().equals(null)) {
		//
		// ois = new ObjectInputStream(socket.getInputStream());
		// System.out.println("OIS = " + ois);
		// message = (String) ois.readObject();
		// ois.close();
		// }
		// } catch (IOException | ClassNotFoundException e) {
		//
		// e.printStackTrace();
		// }
		// System.out.println(" Message = " + message);

		return direction;

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

		if (!(snakeGame.getPlayerCount() <= snakeGame.getMaxPlayers())) {

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

					// System.out.println(snakeGame.getPlayers().length);
					// if (snakeGame.getPlayers().length == 0) {
					//
					// // send message to client to request a number of players
					// // to join
					// String message = new String("RequestMessage"); // TODO
					// // create
					// // a
					// // proper
					// // object
					// // message
					// snakeGame.sendMessage(message);
					//
					// // should be put into the playerthread class TODO
					//
					// // receive message from client - should be a number
					// /*
					// * Object size = snakeGame.receiveMessage(); // TODO
					// * create a message for this (above) & object for // the
					// * message
					// *
					// * // create a player list of size received
					// *
					// * snakeGame.setPlayers(new PlayerThread[(int) size]);
					// * snakeGame.setMaxPlayers((int) size);
					// */
					//
					// }
					System.out.println();
					System.out.println("ClientSocket = " + clientSocket);
					PlayerThread player = new PlayerThread(clientSocket,
							snakeGame.getServer(), snakeGame);

					// include the first player as number one in the list.

					System.out.println("Player Count = "
							+ snakeGame.getPlayerCount());

					snakeGame.getPlayers()[snakeGame.getPlayerCount()] = player;

					// increase player counter in order to place players in
					// order.

					snakeGame.setPlayerCount(snakeGame.getPlayerCount() + 1);
					// player.start();
					System.out.println("Max Players = "
							+ snakeGame.getMaxPlayers() + " Player Count = "
							+ snakeGame.getPlayerCount());

				}

			}

		}

		return numClients + 1;
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

		System.out.println(action);
		System.out.println(player.getSnake());

		switch (action) {

		case "LEFT":
			System.out.println("Left Move");
			getGame().moveSnake(player.getSnake(), action);
			break;

		case "RIGHT":
			System.out.println("Right Move");
			getGame().moveSnake(player.getSnake(), action);
			break;

		case "INCREASE":
			System.out.println("Increase Speed");
			break;

		case "DECREASE":
			System.out.println("Decrease Speed");
			break;

		default:
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
