import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Buttons extends JFrame implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L; //idk what this shit is
	private JFrame f;
	private JPanel p;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JPanel p4;
	private JPanel p5;
	private JPanel p6;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private JButton b5;
	private JButton back1;
	private JButton back2;
	private JButton back3;
	private JButton back4;
	private JButton back5;
	private JButton enter;
	private JButton enter1;
	private JButton enter2;
	private JButton enter3;
	private Label player1;
	private Label player2;
	private Label player3;
	private Label player4;
	private TextField pl1;
	private TextField pl2;
	private TextField pl3;
	private TextField pl4;
	private String pla1;
	private String pla2;
	private String pla3;
	private String pla4;
	private String[] player;
	private ImageIcon image;
	private JLabel label;
	public boolean startGame;

	private Label i;
	private Label i1;
	private Label i2;
	private Label i3;
	private Label i4;
	private Label i5;
	private Label i6;
	private Label i7;
	private Label i8;
	private Label i9;
	private Label i10;
	private Label i11;
	private JButton Instructions;
	private JButton Back;


	public Buttons()
	{
		gui();
		startGame = false;
	}


	public void gui()
	{
		f = new JFrame("Scrabble");
		f.setVisible(true);
		f.setSize(1280,720);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);

		p = new JPanel(new GridBagLayout()); //first window
		p1 = new JPanel(new GridBagLayout()); //1 player
		p2 = new JPanel(new GridBagLayout()); //second window with  options
		p3 = new JPanel(new GridBagLayout()); //2 player
		p4 = new JPanel(new GridBagLayout()); //3 player
		p5 = new JPanel(new GridBagLayout()); //4 player
		p6 = new JPanel(new GridBagLayout()); //Instructions

		p.setBackground(Color.BLACK);
		p1.setBackground(Color.BLACK);
		p2.setBackground(Color.BLACK);
		p3.setBackground(Color.BLACK);
		p4.setBackground(Color.BLACK);
		p5.setBackground(Color.BLACK);
		p6.setBackground(Color.BLACK);



		b1 = new JButton("Single Player");
		b2 = new JButton("Multi Player");
		b3 = new JButton("2 Player");
		b4 = new JButton("3 Player");
		b5 = new JButton("4 Player");
		enter = new JButton("Enter");
		enter1 = new JButton("Enter");
		enter2 = new JButton("Enter");
		enter3 = new JButton("Enter");
		back1 = new JButton("Back");
		back2 = new JButton("Back");
		back3 = new JButton("Back");
		back4 = new JButton("Back");
		back5 = new JButton("Back");

		b1.setBackground(Color.BLUE);
		b1.setForeground(Color.white);
		b2.setBackground(Color.BLUE);
		b2.setForeground(Color.white);
		b3.setBackground(Color.BLUE);
		b3.setForeground(Color.white);
		b4.setBackground(Color.BLUE);
		b4.setForeground(Color.white);
		b5.setBackground(Color.BLUE);
		b5.setForeground(Color.white);
		enter.setBackground(Color.BLUE);
		enter.setForeground(Color.white);
		enter1.setBackground(Color.BLUE);
		enter1.setForeground(Color.white);
		enter2.setBackground(Color.BLUE);
		enter2.setForeground(Color.white);
		enter3.setBackground(Color.BLUE);
		enter3.setForeground(Color.white);
		back1.setBackground(Color.BLUE);
		back1.setForeground(Color.white);
		back2.setBackground(Color.BLUE);
		back2.setForeground(Color.white);
		back3.setBackground(Color.BLUE);
		back3.setForeground(Color.white);
		back4.setBackground(Color.BLUE);
		back4.setForeground(Color.white);
		back5.setBackground(Color.BLUE);
		back5.setForeground(Color.white);

		Instructions = new JButton("How to Play");
		Instructions.setBackground(Color.BLUE);
		Instructions.setForeground(Color.white);

		Back = new JButton("Back");
		Back.setBackground(Color.BLUE);
		Back.setForeground(Color.white);

		//adding buttons
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		enter.addActionListener(this);
		enter1.addActionListener(this);
		enter2.addActionListener(this);
		enter3.addActionListener(this);
		back1.addActionListener(this);
		back2.addActionListener(this);
		back3.addActionListener(this);
		back4.addActionListener(this);
		back5.addActionListener(this);
		Instructions.addActionListener(this);
		Back.addActionListener(this);

		//labels and text fields
		player1 = new Label("Enter Player 1 Name");
		player1.setForeground(Color.YELLOW);
		player2 = new Label("Enter Player 2 Name");
		player2.setForeground(Color.YELLOW);
		player3 = new Label("Enter Player 3 Name");
		player3.setForeground(Color.YELLOW);
		player4 = new Label("Enter Player 4 Name");
		player4.setForeground(Color.YELLOW);



		pl1 = new TextField(20);
		pl2 = new TextField(20);
		pl3 = new TextField(20);
		pl4 = new TextField(20);

		i = new Label("Scrabble is a game where you test your wits to form words with the letters you are given in your hand.");
		i.setForeground(Color.YELLOW);
		i1 = new Label("Within Scrabble, as a person places a tile, they will gain points equivalent to the number of letters used within that word.");
		i1.setForeground(Color.YELLOW);
		i2 = new Label("The scrabble board consists of a 15x15 board tiles. Your job is to get more points than the players against you.");
		i2.setForeground(Color.YELLOW);
		i3 = new Label("There will be 26 different tiles in the game each corresponding to a different letter in the alphabet.");
		i3.setForeground(Color.YELLOW);
		i4 = new Label("Decide among the players who will go first. They will be player 1. Now decide who is to go second and they will be player 2 and hence forth until you have 4 players.");
		i4.setForeground(Color.YELLOW);
		i5 = new Label("Each player will start by drawing 7 random tiles. Player 1 will use their letters to make a word if possible. If not possible, they have the liberty of starting the round off with a letter in the center square.");
		i5.setForeground(Color.YELLOW);
		i6 = new Label("Once tiles are placed on the board, players will draw random tiles until they have 7 tiles at hand as replacement of those played on the board.");
		i6.setForeground(Color.YELLOW);
		i7 = new Label("Once all the tiles have been dispensed from the bag and one player has dispensed all letters from their hand, the game will end and scores for each player will be tallied.");
		i7.setForeground(Color.YELLOW);
		i8 = new Label("The player with the highest score at the end of the game will be the winner of the scrabble game. ");
		i8.setForeground(Color.YELLOW);
		i9 = new Label("All words that are accepted by the game are from the oxford dictionary of 2016. If a word is not in this dictionary, it will not be accepted as a word that is useable within the game.");
		i9.setForeground(Color.YELLOW);
		i10 = new Label("All words that are being used are in the English Language and therefore any foreign language words will not be accepted as words of the game.");
		i10.setForeground(Color.YELLOW);
		i11 = new Label("Click on a letter then click where you would like to place it on the board. It is not drag and drop. ");
		i11.setForeground(Color.YELLOW);





		image = new ImageIcon(getClass().getResource("image.png"));
		label = new JLabel(image);

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		p.add(label,c);
		f.add(p);
		c.gridx = 0;
		c.gridy = 5;
		p.add(b1,c);
		c.gridx = 0;
		c.gridy = 6;
		p.add(b2,c);
		c.gridx = 0;
		c.gridy = 7;
		p.add(Instructions,c);

		f.setExtendedState(Frame.MAXIMIZED_BOTH);
		//f.setUndecorated(true);
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == b1) //single player button
		{
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			p1.setVisible(true);
			player1.setVisible(true);
			pl1.setVisible(true);
			enter1.setVisible(true);
			back5.setVisible(true);
			p.setVisible(false);
			p2.setVisible(false);
			f.add(p1);
			p1.add(player1);
			p1.add(pl1);
			c.gridx = 1;
			c.gridy = 1;
			p1.add(enter,c);
			c.gridx = 0;
			c.gridy = 1;
			p1.add(back5,c);
			player = new String[1];


		}
		if(e.getSource() == enter)
		{
			pla1 = pl1.getText();
			player[0] = pla1;
			System.out.println(pla1);
			f.setVisible(false);
			startGame = true;
		}
		if(e.getSource() == b2) //multiplayer button
		{
			f.add(p2);
			p2.add(b3);
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			p2.add(b3,c);
			c.gridx = 0;
			c.gridy = 1;
			p2.add(b4, c);
			c.gridx = 0;
			c.gridy = 2;
			p2.add(b5,c);
			c.gridx = 0;
			c.gridy = 3;
			p2.add(back1,c);
			c.gridx = 0;
			c.gridy = 5;
			p.setVisible(false);
			p2.setVisible(true);
			b3.setVisible(true);
			b4.setVisible(true);
			b5.setVisible(true);
			back1.setVisible(true);
		}
		if(e.getSource() == b3) //2 player button
		{
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			f.add(p3);
			p3.add(player1);
			p3.add(pl1);
			c.gridx = 0;
			c.gridy = 2;
			p3.add(player2,c);
			c.gridx = 1;
			c.gridy = 2;
			p3.add(pl2,c);
			c.gridx = 1;
			c.gridy = 3;
			p3.add(enter1,c);
			c.gridx = 0;
			c.gridy = 3;
			p3.add(back2,c);
			p2.setVisible(false);
			p3.setVisible(true);
			player1.setVisible(true);
			pl1.setVisible(true);
			player2.setVisible(true);
			pl2.setVisible(true);
			enter1.setVisible(true);
			back2.setVisible(true);
			player = new String[2];
		}
		if(e.getSource() == enter1) //2 Player get input
		{
			pla1 = pl1.getText();
			pla2 = pl2.getText();
			player[0] = pla1;
			player[1] = pla2;
			System.out.println(pla1);
			System.out.println(pla2);
			startGame = true;
			f.setVisible(false);
		}
		if(e.getSource() == b4) //3 player button
		{
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			f.add(p4);
			p4.add(player1);
			p4.add(pl1);
			c.gridx = 0;
			c.gridy = 2;
			p4.add(player2,c);
			c.gridx = 1;
			c.gridy = 2;
			p4.add(pl2,c);
			c.gridx = 0;
			c.gridy = 3;
			p4.add(player3,c);
			c.gridx = 1;
			c.gridy = 3;
			p4.add(pl3,c);
			c.gridx = 1;
			c.gridy = 4;
			p4.add(enter2,c);
			c.gridx = 0;
			c.gridy = 4;
			p4.add(back3,c);
			p2.setVisible(false);
			p4.setVisible(true);
			player1.setVisible(true);
			pl1.setVisible(true);
			player2.setVisible(true);
			pl2.setVisible(true);
			player3.setVisible(true);
			pl3.setVisible(true);
			enter2.setVisible(true);
			back3.setVisible(true);
			player = new String[3];
		}
		if(e.getSource() == enter2) //3 Player get input
		{
			pla1 = pl1.getText();
			pla2 = pl2.getText();
			pla3 = pl3.getText();
			player[0] = pla1;
			player[1] = pla2;
			player[2] = pla3;
			System.out.println(pla1);
			System.out.println(pla2);
			System.out.println(pla3);
			startGame = true;
			f.setVisible(false);
		}
		if(e.getSource() == b5) //4 player button
		{
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			f.add(p5);
			p5.add(player1);
			p5.add(pl1);
			c.gridx = 0;
			c.gridy = 2;
			p5.add(player2,c);
			c.gridx = 1;
			c.gridy = 2;
			p5.add(pl2,c);
			c.gridx = 0;
			c.gridy = 3;
			p5.add(player3,c);
			c.gridx = 1;
			c.gridy = 3;
			p5.add(pl3,c);
			c.gridx = 0;
			c.gridy = 4;
			p5.add(player4,c);
			c.gridx = 1;
			c.gridy = 4;
			p5.add(pl4,c);
			c.gridx = 1;
			c.gridy = 5;
			p5.add(enter3,c);
			c.gridx = 0;
			c.gridy = 5;
			p5.add(back4,c);
			p2.setVisible(false);
			p5.setVisible(true);
			p5.setVisible(true);
			player1.setVisible(true);
			pl1.setVisible(true);
			player2.setVisible(true);
			pl2.setVisible(true);
			player3.setVisible(true);
			pl3.setVisible(true);
			player4.setVisible(true);
			pl4.setVisible(true);
			enter3.setVisible(true);
			back4.setVisible(true);
			player = new String[4];
		}
		if(e.getSource() == enter3) //4 Player get input
		{
			pla1 = pl1.getText();
			pla2 = pl2.getText();
			pla3 = pl3.getText();
			pla4 = pl4.getText();
			player[0] = pla1;
			player[1] = pla2;
			player[2] = pla3;
			player[3] = pla4;
			System.out.println(pla1);
			System.out.println(pla2);
			System.out.println(pla3);
			System.out.println(pla4);
			startGame = true;
			f.setVisible(false);
		}
		if(e.getSource() == back1) //back to first screen
		{
			p.setVisible(true);
			p1.setVisible(false);
			b3.setVisible(false);
			b4.setVisible(false);
			b5.setVisible(false);
			back1.setVisible(false);
		}
		if(e.getSource() == back2)//back to player number selection from 2 player
		{
			p3.setVisible(false);
			p2.setVisible(true);
			b3.setVisible(true);
			b4.setVisible(true);
			b5.setVisible(true);
			back1.setVisible(true);
		}
		if(e.getSource() == back3)
		{
			p4.setVisible(false);
			player1.setVisible(false);
			pl1.setVisible(false);
			player2.setVisible(false);
			pl2.setVisible(false);
			player3.setVisible(false);
			pl3.setVisible(false);
			enter2.setVisible(false);
			back3.setVisible(false);
			p2.setVisible(true);
		}
		if(e.getSource() == back4)
		{
			p5.setVisible(false);
			player1.setVisible(false);
			pl1.setVisible(false);
			player2.setVisible(false);
			pl2.setVisible(false);
			player3.setVisible(false);
			pl3.setVisible(false);
			player4.setVisible(false);
			pl4.setVisible(false);
			enter3.setVisible(false);
			back4.setVisible(false);
			p2.setVisible(true);
		}
		if(e.getSource() == back5) //back to main screen from single player
		{
			p.setVisible(true);
			p1.setVisible(false);
			player1.setVisible(false);
			pl1.setVisible(false);
			enter1.setVisible(false);
			back5.setVisible(false);
		}
		if(e.getSource() == Instructions)
		{
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10,10,10,10);
			f.add(p6);
			p.setVisible(false);
			p2.setVisible(false);
			p6.setVisible(true);
			c.gridx = 0;
			c.gridy = 0;
			p6.add(i,c);
			c.gridx = 0;
			c.gridy = 1;
			p6.add(i1,c);
			c.gridx = 0;
			c.gridy = 2;
			p6.add(i2,c);
			c.gridx = 0;
			c.gridy = 3;
			p6.add(i3,c);
			c.gridx = 0;
			c.gridy = 4;
			p6.add(i4,c);
			c.gridx = 0;
			c.gridy = 5;
			p6.add(i5,c);
			c.gridx = 0;
			c.gridy = 6;
			p6.add(i6,c);
			c.gridx = 0;
			c.gridy = 7;
			p6.add(i7,c);
			c.gridx = 0;
			c.gridy = 8;
			p6.add(i8,c);
			c.gridx = 0;
			c.gridy = 9;
			p6.add(i9,c);
			c.gridx = 0;
			c.gridy = 10;
			p6.add(i10,c);
			c.gridx = 0;
			c.gridy = 11;
			p6.add(i11,c);
			c.gridx = 0;
			c.gridy = 12;
			p6.add(Back,c);
			i.setVisible(true);
			i1.setVisible(true);
			i2.setVisible(true);
			i3.setVisible(true);
			i4.setVisible(true);
			i5.setVisible(true);
			i6.setVisible(true);
			i7.setVisible(true);
			i8.setVisible(true);
			i9.setVisible(true);
			i10.setVisible(true);
			i11.setVisible(true);
		}
		if(e.getSource() == Back)
		{
			i.setVisible(false);
			i1.setVisible(false);
			i2.setVisible(false);
			i3.setVisible(false);
			i4.setVisible(false);
			i5.setVisible(false);
			i6.setVisible(false);
			i7.setVisible(false);
			i8.setVisible(false);
			i9.setVisible(false);
			i10.setVisible(false);
			i11.setVisible(false);
			p.setVisible(true);
			p6.setVisible(false);
			b3.setVisible(false);
			b4.setVisible(false);
			b5.setVisible(false);
			back1.setVisible(false);
		}
	}

	public String[] getNames()
	{
		return player;
	}
}
