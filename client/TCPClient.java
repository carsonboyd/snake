package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

class TCPClient {

	public static void main(String[] args) throws IOException {

		String serverHostname = new String("127.0.0.1");
		Socket echoSocket = null;

		ObjectOutputStream keySender = null;

		try {

			echoSocket = new Socket(serverHostname, 8000);

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));

			String userInput;

			while ((userInput = stdIn.readLine()) != null) {

				if (echoSocket.isClosed()) {

					System.out.println("Closed!");

					System.out
							.println("Sorry you can't connect to the server at this time!");
					System.exit(0);

				}

				keySender = new ObjectOutputStream(echoSocket.getOutputStream());

				// send the key over
				keySender.writeObject(userInput);

			}

			keySender.close();

			stdIn.close();
			echoSocket.close();

		} catch (ConnectException ce) {

			System.out
					.println("Client cannot connect to Server, please try again later!");
			System.out.println("Exitting...");
			System.exit(0);

		} catch (SocketException se) {

			System.out.println("Connection has ended.");

		} catch (Exception e) {

			System.out.println("Sorry something is wrong");
			e.printStackTrace();

		}

	}
}