package client;

// package gui

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;


public final class Frame extends javax.swing.JFrame 
{
	public Frame(boolean newGame) 
	{
		if (newGame)
			createGame();
		else
			initialise();
	}

	private void initialise() 
	{ 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() 
        {
            public void windowClosing(java.awt.event.WindowEvent evt) 
            {
                cleanUp(evt);
            }
        });


        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
 		javax.swing.JLabel playerOne = new javax.swing.JLabel();
 		javax.swing.JLabel playerTwo = new javax.swing.JLabel();
 		javax.swing.JLabel playerThree = new javax.swing.JLabel();
 		javax.swing.JLabel playerFour = new javax.swing.JLabel();

        javax.swing.JPanel dataJPanel = new javax.swing.JPanel();
        javax.swing.JPanel gameStatsJPanel = new javax.swing.JPanel();

        javax.swing.JToggleButton initialiseToggle = new javax.swing.JToggleButton();

        Integer[] data = {2, 3, 4};
        JComboBox playerCount = new JComboBox(data);
		playerCount.setSelectedIndex(0);
		// playerCount.addActionListener(this);


        dataJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection Data"));
        dataJPanel.setLayout(new javax.swing.BoxLayout(dataJPanel, javax.swing.BoxLayout.LINE_AXIS));
		getContentPane().add(dataJPanel, java.awt.BorderLayout.NORTH);
		jLabel1.setText("Number of Players");
        dataJPanel.add(jLabel1);
        dataJPanel.add(playerCount);

        initialiseToggle.setText("Create a Game");
        dataJPanel.add(initialiseToggle);

        // gameStatsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Game"));
        // gameStatsJPanel.setLayout(new javax.swing.BoxLayout(gameStatsJPanel, javax.swing.BoxLayout.LINE_AXIS));
        
   		// playerOne.setText("Player One: Connected");
 		// playerTwo.setText("Player Two: Waiting...");
 		// playerThree.setText("Player Three: Waiting...");
 		// playerFour.setText("Player Four: Waiting...");

 		// gameStatsJPanel.add(playerOne);
 		// gameStatsJPanel.add(playerTwo);
 		// gameStatsJPanel.add(playerThree);
 		// gameStatsJPanel.add(playerFour);

		SnakeModel model = new SnakeModel();
        Panel panel = new Panel(model);
        getContentPane().add(panel, java.awt.BorderLayout.CENTER);


	}

	private void createGame()
	{
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() 
        {
            public void windowClosing(java.awt.event.WindowEvent evt) 
            {
                cleanUp(evt);
            }
        });

		javax.swing.JButton initialiseGame = new javax.swing.JButton();
        javax.swing.JPanel dataJPanel = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
		Integer[] data = {2, 3, 4};
        JComboBox playerCount = new JComboBox(data);
		playerCount.setSelectedIndex(0);

        dataJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Game"));
        dataJPanel.setLayout(new javax.swing.BoxLayout(dataJPanel, javax.swing.BoxLayout.LINE_AXIS));
		getContentPane().add(dataJPanel, java.awt.BorderLayout.NORTH);
		jLabel1.setText("Number of Players");
        dataJPanel.add(jLabel1);
        dataJPanel.add(playerCount);

        initialiseGame.setText("Create a Game");
        dataJPanel.add(initialiseGame);

        initialiseGame.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //Execute when initialiseGame is pressed
                System.out.println("You created a game");
				Frame frame = new Frame(false);
				frame.setSize(600, 700);        
				frame.setVisible(true);
            }
        });
	}

	private void cleanUp(java.awt.event.WindowEvent evt) 
	{
		// socket.close();
	}

	public static void main(String args[])
	{
		boolean gameInProgress = false;

		// Connect to Server
		// gameInProgress = serverResult;

		// If no game is taking place, create a game
		if (gameInProgress)
		{
			Frame frame = new Frame(false);
			frame.setSize(600, 700);        
			frame.setVisible(true);
		} else 
		{
			Frame frame = new Frame(true);
			frame.setSize(600, 85);        
			frame.setVisible(true);
		}

		// Else, open the game


	}
}