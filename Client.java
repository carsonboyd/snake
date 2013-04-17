
import java.io.*;
import java.net.*;

// Client is essentially the View
public class Client implements Runnable
{
	// Is the game currently being played, or has it not yet started or finished
	private boolean playing = false;

	// Setup the connection and handle the connection information
  	private void connectToServer() 
  	{
    	try {
      		// Create a socket to connect to the server
      		Socket socket;
        	socket = new Socket("127.0.0.1", 8000);

      		// Create an input stream to receive data from the server
      		DataInputStream fromServer = new DataInputStream(socket.getInputStream());

   		   	// Create an output stream to send data to the server
    		DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
    	}
    	catch (Exception ex) {
    		System.err.println(ex);
    	}

    	// Control the game on a separate thread
    	Thread thread = new Thread(this);
    	thread.start();
  	}

  	// The thread that controls all the game information from the server
   	public void run() 
   	{
   		// Set the game to active
   		playing = true;

    	try {
      		// Get initial game state from the server

      		// Continue to play
	      	while (playing) {
	          	// Receive information about the game state. e.g. receiveState();
	          	// Update the UI based on the game state. e.g. updateUI();
	          	// Check if the client has made a move. e.g. if (move.hasChanged())
	          	// if the client has made a move, send it to the server. e.g. sendMove();
	          	// else, continue the game loop.
	        }
	    }
    	catch (Exception ex) {
    		// Handle exception
    	}
  	}

  	// Run the application
	public static void main(String[] args)
	{
		// Initialise the game state
	}
}

// Controller is basically and API 
class Controller 
{
	public void sendMove(final Move move) { }
	public void receiveUpdate() { }
}

class Move 
{
	private final static int UP = 1;
	private final static int RIGHT = 2;
	private final static int DOWN = 3;
	private final static int LEFT = 4;
}