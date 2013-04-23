package Server;

import java.net.ServerSocket;
import java.net.Socket;

public class PlayerThread extends Thread {

	private Socket socket; // the socket of the client
	private ServerSocket serverSocket;

	public PlayerThread(Socket clientSocket, ServerSocket serverSocket) {

		super("PlayerThread");
		this.setSocket(socket);
		this.setServerSocket(serverSocket);

	}

	/**
	 * An overridden method that will create a thread from which all the client
	 * instructions will be performed
	 */

	public void run() {// TODO

	}

	/**
	 * This method will call for the client's snake to be turned left
	 */

	public void left() {// TODO

	}

	/**
	 * This method will call for the client's snake to be turned left
	 */

	public void right() {// TODO

	}

	/**
	 * This method will increase the times at which the snake's movement will be
	 * calculated.
	 */

	public void increaseSpeed() {// TODO

	}

	/**
	 * This method will decrease the times at which the snake's movement will be
	 * calculated.
	 */

	public void decreaseSpeed() {// TODO

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

}
