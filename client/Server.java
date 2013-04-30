package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import org.json.JSONObject;

// The server
public class Server {
	/*
	 * Server properties, including thread safe handling of up to four player
	 * connections.
	 */
	private int serverPort = 15000;
	private ServerSocket serverSocket;
	private Hashtable<Integer, ConnectionService> players = new Hashtable<Integer, ConnectionService>();
	private boolean gameState = false;
	private int maxPlayers = 2;

	public Server() {
		try {
			serverSocket = new ServerSocket(serverPort);
			System.out.println("Initialised Game Server...");
			System.out.println("--------------------------");
			System.out.println("Waiting...");
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("Connected: " + socket);
				ConnectionService service = new ConnectionService(socket);
				service.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server();
	}

	// Manages the communication between the client and the server
	class ConnectionService extends Thread {
		private Socket socket;
		private BufferedReader inputReader;
		private PrintWriter outputWriter;

		public ConnectionService(Socket socket) {
			this.socket = socket;

			try {
				inputReader = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				outputWriter = new PrintWriter(socket.getOutputStream(), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					String receivedMessage = inputReader.readLine();
					if (receivedMessage.contains("connect")) {
						if (!gameState) {
							// When a Client receives a status of inactive, the
							// Client will attempt to create a new game.
							outputWriter.println("\"status\":\"inactive\"");
							System.out.println("\"status\":\"inactive\"");
						} else {
							outputWriter.println("\"status\":\"active\"");
							boolean sentinel = false;
							// player = 2 because the player cannot be player 1
							for (int player = 2; player <= maxPlayers; player++) {
								if (!players.containsKey(player)) {
									players.put(player, this);
									outputWriter.println("\"player\": "
											+ player);
									System.out.println("\"player\": " + player);
									sentinel = true;
								}
							}
							// We hit this point if there are too many players
							if (!sentinel) {
								System.out
										.println("\"connection\": \"rejected\"");
							}
						}
					} else if (receivedMessage.contains("create")) {
						if (gameState) {
							outputWriter
									.println("\"connection\": \"rejected\"");
							System.out.println("\"status\":\"rejected\"");
						} else {
							// Initialise new game.
							// Player one is always the host
							if (!players.containsKey(1)) {
								gameState = true;
								players.put(1, this);
								outputWriter.println("\"player\": 1");
								System.out.println("\"player\" 1");
								continue;
							}
						}
					} else if (receivedMessage.contains("waiting")) {// this
																		// where
																		// the
																		// server
																		// directions
																		// need
																		// to be
						int i;
						for (i = 0; i < 55; i++) {
							String jsonString = String
									.format("{\"snake\":{\"snake1\":[{\"x\":20,\"y\":30},{\"x\":%d,\"y\":30},{\"x\":%d,\"y\":30},{\"x\":%d,\"y\":30},{\"x\":%d,\"y\":30}]}}",
											i - 6, i - 5, i - 4, i - 3, i - 2,
											i - 1, i);
							outputWriter.println(jsonString);
							outputWriter.flush();
							// System.out.println(jsonString);
						}
					} else if (receivedMessage.contains("direction")) {
						try {
							JSONObject jsonObj = new JSONObject(receivedMessage);
							String direction = (String) jsonObj
									.get("direction");
							System.out.println(direction);

							switch (direction) {

							case "l":
								System.out.println("Carson was here!");
								break;

							case "r":
								break;

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			} finally {
				outputWriter.close();
			}
		}
	}
}