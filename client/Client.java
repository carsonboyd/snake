package client;

import java.io.BufferedReader;
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
			JSONObject jsonDir = null;
			while (true) {

				String jsonString = inputReader.readLine();
				try {
					// System.out.println(jsonString);
					// outputWriter.println("waiting");
					JSONObject jsonObj = new JSONObject(jsonString);
					JSONObject twoSnakes = jsonObj.getJSONObject("snake");
					JSONArray arraySnake1 = twoSnakes.getJSONArray("snake1");
					// System.out.println(arraySnake1);
					model.setSnakeOneDots(getListOfPoints(arraySnake1));
					frame.repaint();
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}

				i++;

				try {
					if (frame != null) {
						char direction = frame.getDirection();

						jsonDir = new JSONObject("{\"direction\" : "
								+ direction + "}");
						System.out.println(jsonDir);
						outputWriter.println(jsonDir + "\n");
						outputWriter.flush();
						direction = 'm';

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
