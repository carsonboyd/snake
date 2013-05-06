import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Client {
	public static void main(String[] args) {
		if (args.length == 3)
		{
			if (Integer.parseInt(args[1]) >= 2 &&  Integer.parseInt(args[1]) <= 4) 
			{
				if (args[2] == "NE" || args[2] == "NW" || args[2] == "SE" || args[2] == "SW")
				{
					System.out.println("Creating new game...");
				}
			}
			else
			{
				System.out.println("Usage");
				System.out.println("===============================");
				System.out.println("java Client new <max> <pos>");
				System.out.println("java Client <pos>");
				System.out.println("<max>: {2, 3, 4} ");
				System.out.println("<pos>: {NE, NW, SE, SW}");
				System.out.println("===============================");
				System.exit(0);
			}
		}
		else if (args.length == 1)
		{	
			System.out.println(args[0]);

			if (args[0].equals("NE") || args[0].equals("NW") || args[0].equals("SE") || args[0] == "SW")
			{
				System.out.println("Joining current game...");
			}
			else
			{
				System.out.println("Usage");
				System.out.println("===============================");
				System.out.println("java Client new <max> <pos>");
				System.out.println("java Client <pos>");
				System.out.println("<max>: {2, 3, 4} ");
				System.out.println("<pos>: {NE, NW, SE, SW}");
				System.out.println("===============================");
				System.exit(0);
			}
		}
		else 
		{
			System.out.println("here");
			System.out.println("Usage");
			System.out.println("===============================");
			System.out.println("java Client new <max> <pos>");
			System.out.println("java Client <pos>");
			System.out.println("<max>: {2, 3, 4} ");
			System.out.println("<pos>: {NE, NW, SE, SW}");
			System.out.println("===============================");
			System.exit(0);
		}

		Socket socket = null;
		SnakeFrame frame = null;
		SnakeModel model = new SnakeModel();
		int player = 0;

		try {
			socket = new Socket("127.0.0.1", 8000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader inputReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter outputWriter = new PrintWriter(
					socket.getOutputStream(), true);
			// outputWriter.println("create");
			// outputWriter.flush();
			frame = new SnakeFrame(model);
			player = 1;
			frame.setStatusLabel("Waiting for another player...");

			int i = 0;
			while (true) {
				String receivedMessage = inputReader.readLine();
				System.out.println(receivedMessage);
				try {
					JSONObject jsonObj = new JSONObject(receivedMessage);
					JSONObject snakes = jsonObj.getJSONObject("snakes");
					JSONArray snake1 = snakes.optJSONArray("snake1");
					JSONArray snake2 = snakes.optJSONArray("snake2");
					JSONArray snake3 = snakes.optJSONArray("snake3");
					JSONArray snake4 = snakes.optJSONArray("snake4");

					if (snake1 != null)
						model.setSnakeOnePoints(getListOfPoints(snake1));
					if (snake2 != null)
						model.setSnakeTwoPoints(getListOfPoints(snake2));
					if (snake3 != null)
						model.setSnakeThreePoints(getListOfPoints(snake3));
					if (snake4 != null)
						model.setSnakeFourPoints(getListOfPoints(snake4));

					if (jsonObj.has("food")) {
						JSONArray array = jsonObj.getJSONArray("food");
						model.setAppleDots(getListOfPoints(array));
						// System.out.println(array);
					}
					// Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					if (frame != null) {
						char direction = frame.getDirection();

						JSONObject jsonDir = new JSONObject("{\"direction\" : "
								+ direction + "}");

						outputWriter.println(jsonDir + "\n");
						outputWriter.flush();

						frame.repaint();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<Point> getListOfPoints(JSONArray arrayDots)
			throws JSONException {
		List<Point> listDots = new ArrayList<Point>();
		for (int i = 0; i < arrayDots.length(); i++) {
			JSONObject dotCoord = (JSONObject) arrayDots.get(i);
			Point point = new Point(dotCoord.getInt("x"), dotCoord.getInt("y"));
			listDots.add(point);
		}
		return listDots;
	}

	private static void handleException(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Sorry, an unexpected error has occurred.", "Error",
				JOptionPane.ERROR_MESSAGE);
		throw new RuntimeException(e);
	}
}