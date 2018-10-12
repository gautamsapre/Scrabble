import javax.swing.JFrame;
import java.io.IOException;
import static java.lang.System.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Game extends JFrame implements ActionListener
{
	private static final int WIDTH = 1920;
	private static final int HEIGHT = 1080;
	int x,y;
	private ScrabbleBoard sb;

	private JButton pass, replaceAll, next, reset, exit, letterVals,playAgain;
	private JTextField textField;
	private JLabel letterValues;
	private boolean h = false;
	private String[] players;

	public Game(int numPlayers,String[] players) throws IOException
	{

		super("Scrabble Board");
		setSize(WIDTH,HEIGHT);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		pass = new JButton("PASS");
		pass.setBounds(695, 420, 80, 65);
		pass.addActionListener(this);
		pass.setBackground(new Color(0,0,205));
		pass.setForeground(Color.YELLOW);
		getContentPane().add(pass);

		replaceAll = new JButton("REPLACE ALL LETTERS");
		replaceAll.setBounds(780, 420, 220, 30);
		replaceAll.addActionListener(this);
		replaceAll.setBackground(new Color(0,0,205));
		replaceAll.setForeground(Color.YELLOW);
		getContentPane().add(replaceAll);

		next = new JButton("NEXT");
		next.setBounds(1005, 420, 80, 65);
		next.addActionListener(this);
		next.setBackground(new Color(0,0,205));
		next.setForeground(Color.YELLOW);
		getContentPane().add(next);

		reset = new JButton("RESET TURN");
		reset.setBounds(780, 455, 220, 30);
		reset.addActionListener(this);
		reset.setBackground(new Color(0,0,205));
		reset.setForeground(Color.YELLOW);
		getContentPane().add(reset);

		exit = new JButton("END GAME");
		exit.setBounds(780, 580, 220, 30);
		exit.addActionListener(this);
		exit.setBackground(Color.RED);
		exit.setForeground(Color.YELLOW);
		getContentPane().add(exit);

		letterVals = new JButton("Letter Values");
		letterVals.setBounds(780,520,220,30);
		letterVals.addActionListener(this);
		letterVals.setBackground(Color.GREEN);
		letterVals.setForeground(Color.RED);
		getContentPane().add(letterVals);

		sb = new ScrabbleBoard(numPlayers, players);
		getContentPane().add(sb);
		setVisible(true);
		//pack();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{

		if (!sb.aiPlaying)
		{
			if (e.getSource() == pass)
			{
				sb.passPressed = true;
				sb.repaint();
			}
			if (e.getSource() == replaceAll)
			{
				sb.replaceAll = true;
				sb.repaint();
			}
			if (e.getSource() == next)
			{
				sb.next = true;
				sb.repaint();
			}
			if (e.getSource() == reset)
			{
				sb.reset = true;
				sb.repaint();
			}
			if (e.getSource() == exit)
			{
				pass.setVisible(false);
				next.setVisible(false);
				exit.setVisible(false);
				reset.setVisible(false);
				letterVals.setVisible(false);
				replaceAll.setVisible(false);
				sb.endGame = true;
				sb.repaint();
			}
			if (e.getSource() == letterVals)
			{
				sb.let = h;
				h=!h;
				sb.repaint();
			}
		}
	}

	public static void main( String args[] ) throws IOException
	{

		Buttons start = new Buttons();
		String[] players = new String[0];
		while (!(start.startGame))
		{
			players = start.getNames();
			out.print("");
		}

		Game run = new Game(players.length, players);


		//GENERAL TESTING
		//Game run = new Game(1, new String[]{"ABC", "XYZ"});



		//AI TESTING
/*
		Scrabble sb = new Scrabble(1);
		AI ai = new AI(sb.getWords());
		ai.setHand(new String[] {"P","E","O","I","H","A","U"});
		ArrayList<String> temp = ai.findWords();
		out.println(""+ temp);
		sb.updateBoard("APPLE",7,7,"h");
		out.println("BEST WORD " + ai.findBestWord());
		sb.updateBoard(temp.get(0),0,0,"h");
		out.println(sb.printBoard());*/




	}
}
