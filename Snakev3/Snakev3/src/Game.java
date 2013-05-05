import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
		setBoardWidth(10);
		setBoardHeight(10);
		setPlayerCount(2);

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
	public synchronized void sendMessage(Map<Point, String> board,
			Game snakeGame) { // TODO

		ObjectOutputStream oos = null;

		PrintWriter outputWriter = null;

		// open up a stream to be used to send the data to the players.

		String jsonString = "{\"snakes\": {";

		for (int i = 0; i < 2; i++) {

			PlayerThread player = snakeGame.getPlayers()[i];
			// System.out.println("Starting at variables" + i);
			// System.out.println(player);
			// // System.out.println(player.getSnake());
			// System.out.println(player.getSnake().getSnakeBody());
			// System.out.println(player.getSnake().getSnakeBody().size());
			// System.out.println("Finished at variables");

			jsonString += "\"snake" + (i + 1) + "\":[";

			for (int j = 0; j < player.getSnake().getSnakeBody().size(); j++) {

				// newBoard.getGrid().put(player.getSnake().getSnakeBody().get(j),
				// "Player" + i);

				jsonString += "{\"x\":"
						+ player.getSnake().getSnakeBody().get(j).x + ",\"y\":"
						+ player.getSnake().getSnakeBody().get(j).y + "}";

				if (!(j == player.getSnake().getSnakeBody().size() - 1)) {

					jsonString += ",";

				}

			}
			if (i == 0)
				jsonString += "],";
			else
				jsonString += "]}";
		}

		jsonString += "}";

		// go through items

		// Item[] items = snakeGame.getGame().getItems();

		// jsonString += "{\"item\":";

		// for (int i = 0; i < snakeGame.getGame().getItemCount(); i++) {

		// // newBoard.getGrid().put(items[i].getLocation(), "ITEM");

		// System.out.println("1 = " + items[i]);
		// System.out.println("2 = " + items[i].getLocation());
		// System.out.println("3 = " + items[i].getLocation().x);
		// System.out.println("4 = " + items[i].getLocation().y);

		// jsonString += "{\"item" + (i + 1) + "\":[{\"x\":"
		// + items[i].getLocation().x + ",\"y\":"
		// + items[i].getLocation().y + "]}";

		// if (!(i == snakeGame.getGame().getItems().length - 1)) {

		// jsonString += ",";

		// }

		// }

		System.out.println(jsonString);

		for (int i = 0; i < 2; i++) {

			try {

				outputWriter = new PrintWriter(players[i].getSocket()
						.getOutputStream(), true);

				outputWriter.println(jsonString);

			} catch (IOException e) {
				e.printStackTrace();
			}
			// catch (org.json.JSONException e)
			// {
			// System.out.println("ERRRORRRR>..");
			// System.out.println(jsonString);
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
			e1.printStackTrace();
		}

		String receivedMessage = null;
		try {

			if ((receivedMessage = inputReader.readLine()) == null) {
				receivedMessage = "f";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("Carson was Here !!!! " + receivedMessage);

		if (receivedMessage.contains("direction")) {
			try {

				JSONObject jsonObj = new JSONObject(receivedMessage);
				direction = (String) jsonObj.get("direction");
				System.out.println(direction);

				if (direction.equals("l")) {
					direction = "LEFT";
				} else if (direction.equals("r")) {
					direction = "RIGHT";
				} else if (direction.equals("f")) {
					direction = "FORWARD";
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

					Thread.sleep(500);

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
					// String message = new String("RequestMessage");
					// // create
					// // a
					// // proper
					// // object
					// // message
					// snakeGame.sendMessage(message);
					//
					// // should be put into the playerthread class
					//
					// // receive message from client - should be a number
					// /*
					// * Object size = snakeGame.receiveMessage();
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

		if (action.equals("LEFT")) {
			// System.out.println("Left Move");
			getGame().moveSnake(player.getSnake(), action);
		} else if (action.equals("RIGHT")) {
			// System.out.println("Right Move");
			getGame().moveSnake(player.getSnake(), action);
		} else if (action.equals("INCREASE")) {
			// System.out.println("Increase Speed");
		} else if (action.equals("DECREASE")) {
			// System.out.println("Decrease Speed");
		} else {
			getGame().moveSnake(player.getSnake(), action);
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
