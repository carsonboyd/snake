package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Game {

	private ServerSocket server;
	private PlayerThread[] players;
	private GameLogicThread game;

	/**
	 * This is the main class of the server, will create a determined number of
	 * player threads and then create a game thread
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		int numClients = 0;
		try {

			serverSocket = new ServerSocket(8000);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean listeningSocket = true;

		while (listeningSocket) {

			System.out.print("Start " + numClients);
			numClients = addPlayer(serverSocket, numClients);
			numClients = numClients + 1;
			System.out.println(" Finish " + numClients);

		}

	}

	/**
	 * Will be the general sendMessage method used to send a message to a client
	 */
	public void sendMessage() {

	}

	/**
	 * Will be the general receiveMessage method used to receive a message from
	 * a client
	 */
	public void receiveMessage() {

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

	public static int addPlayer(ServerSocket serverSocket, int numClients) {
		Socket clientSocket = null;

		if (numClients > 1) {

			try {

				// I think since there is still a socket
				// it must erase the first object to create a new one
				System.out.println(" Disconnecting Next Server...");
				clientSocket = serverSocket.accept();
				clientSocket.close();
				numClients = numClients - 1;
				return numClients;

			} catch (IOException ioe) {
				// TODO Auto-generated catch block
				ioe.printStackTrace();

			} catch (Exception e) {

				e.printStackTrace();

			}

		} else {

			try {

				clientSocket = serverSocket.accept();

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

					PlayerThread mini = new PlayerThread(clientSocket,
							serverSocket);
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

}
