import java.util.Collections;
import java.util.ArrayList;
import static java.lang.System.*;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;

public class Scrabble {

	private final int HandSize = 7;
	private ArrayList<Player> players;
	private ArrayList<String> tiles;
	private ArrayList<String> wordList;
	private int playerNum;
	private String[][] board;
	private boolean changed;
	private ArrayList<String> wordsDone;
	private Scanner txt;


	public Scrabble(int numPlayers) throws IOException
	{
		loadStuff();
		players = new ArrayList<Player>();
		if (numPlayers == 1)
		{
			players.add(new Player("Player"));
			players.add(new AI(wordList));
		}
		else
		{
			for (int i=0;i<numPlayers;i++)
			{
				players.add(new Player(i));
			}
		}
	}

	public Scrabble(int numPlayers,String[] playerList) throws IOException
	{
		loadStuff();
		players = new ArrayList<Player>();
		if (numPlayers == 1)
		{
			players.add(new Player(playerList[0]));
			players.add(new AI(wordList));
		}
		else
		{
			for (int i=0;i<numPlayers;i++)
			{
				if (playerList[i].equals("") || playerList[i] == null || playerList[i].equals(" "))
				{

					players.add(new Player(i));
				}
				else
				{
					players.add(new Player(playerList[i]));
				}
			}
		}
	}

	public void loadStuff()
	{
		changed = false;
		out.println("Loading Words.....");
		playerNum = 0;
		board = new String[15][15];
		for (int x=0;x<15;x++)
		{
			for (int y=0;y<15;y++)
			{
				board[x][y] = "";
			}
		}
		tiles = new ArrayList<String>(Arrays.asList("A","A","A","A","A","A","A","A","A","E","E","E","E","E","E","E","E","E","E","E","E","I",
															"I","I","I","I","I","I","I","I","O","O","O","O","O","O","O","O","U","U","U","U","B","B","C","C","D","D","D",
													"D","F","F","G","G","G","H","H","J","K","L","L","L","L","M","M","N","N","N","N","N","N","P","P","Q","R","R","R","R",
													"R","R","S","S","S","S","T","T","T","T","T","T","V","V","W","W","X","Y","Y","Z"));
		try
		{
			txt = new Scanner(new File("words.dat"));
			wordList = new ArrayList<String>();
	    	while (txt.hasNext())
	    	{
	    		wordList.add(txt.next().toUpperCase());
	    	}
		}
		catch(IOException err){
			out.println("TXT NOT FOUND");
		}
    	out.println(wordList.size() + " Words Loaded!");
    	out.println(tiles.size() + " Tiles Loaded");
	}

	public void dealAllHand()
	{
		for (Player p : players)
		{
			p.dealHand(tiles);
		}
	}

	public boolean isValidWord(String word)
	{
		//Boolean valid = false;
		word = word.toUpperCase();
		//out.println("Valid Word Print" + word);
		//String[] temp = hand;
		if (word.equals("") || word.length() == 1)
		{
			return false;
		}
		for (int i=0;i<wordList.size();i++)
		{
			if (wordList.get(i).equals(word))
			{
				wordList.remove(i);
				return true;
			}
		}

		return false;
	}

	public Player getNextPlayer()
	{
		if (playerNum >= players.size())
		{
			playerNum = 0;
		}
		playerNum++;
		changed = true;
		return players.get(playerNum-1);

	}

	public String[][] getBoard()
	{
		return board;
	}

	public boolean updateBoard(String word, int r1, int c1, String direction)	{
		int r2 = 0; int c2 = 0;
		if (direction.equals("h")) {
			r2 = word.length() + r1;
			c2 = c1;
		}
		if (direction.equals("v")) {
			r2 = r1;
			c2 = word.length() + c1;
		}
		if (!(r1 >= 0 && r1 <= 15 && r2 >= 0 && r2 <= 15  && c1 >= 0 && c1 <= 15 && c2 >= 0 && c2 <= 15)) {
			return false;
		}
		for (int i=0;i<word.length();i++)
		{
			String letter = word.substring(i,i+1);
			if (!(board[r1][c1].equals("") || board[r1][c1].equals(" ")))	{
				if (!board[r1][c1].equals(letter))	{
					return false;
				}
			}
			board[r1][c1] = letter;
			if (r1 == r2)	{
				c1++;
				if ((r2-r1) > word.length())	{
					r2 = word.length();
				}
			}
			else if (c1 == c2)	{
				r1++;
				if ((c2-c1) > word.length())	{
					c2 = word.length();
				}
			}
		}
		return true;
	}

	public void replaceHand(Player p)
	{
		String[] temp = p.getHand();
		for (int i=0;i<temp.length;i++)
		{
			tiles.add(temp[i]);
		}
		p.dealHand(tiles);
		changed = true;
	}

	public String getTile()
	{
		Collections.shuffle(tiles);
		String letter = tiles.remove(0);
		return letter;
	}

	public void replaceOne(Player p, String letter)
	{
		if (!(Arrays.asList(p.getHand()).contains(letter)))
		{
			out.println("LETTER DOESN'T EXIST IN HAND!");
		}
		Collections.shuffle(tiles);
		p.dealHand(tiles);
		changed = true;
	}

	public void setChanged(boolean bool)
	{
		changed = bool;
	}
	public boolean isChanged()
	{
		return changed;
	}

	public void setBoard(String[][] matrix)
	{
		board = matrix;
	}

	public void clearWord(ArrayList<Integer> tempX,ArrayList<Integer> tempY)
	{
		for (int i=0;i<tempX.size();i++)
		{
			board[tempX.get(i)][tempY.get(i)] = "";
		}
	}

	public ArrayList<String> getTiles()
	{
		return tiles;
	}

	public ArrayList<String> getNameOfOpponents()
	{
		ArrayList<String> temp = new ArrayList<String>();
		for (int i=0;i<players.size();i++)
		{
			if (!(players.get(i).currentPlayer))
			{
				temp.add(players.get(i).getName());
			}
		}
		return temp;
	}

	public ArrayList<Integer> getScoreOfOpponents()
	{
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i=0;i<players.size();i++)
		{
			if (!(players.get(i).currentPlayer))
			{
				temp.add(players.get(i).getScore());
			}
		}
		return temp;
	}

	public ArrayList<String> getWords()
	{
		return wordList;
	}

	public String[] getLetters(String word)
	{
		String[] temp = new String[word.length()];
		for (int i=0;i<word.length();i++)
		{
			temp[i] = word.substring(i,i+1);
		}
		return temp;
	}

	public String printBoard()
	{
		String output = "";
		for (int i=0;i<15;i++)
		{
			for(int j=0;j<15;j++)
			{
				if (!(board[i][j].equals("")))
					output += board[i][j] + " ";
				output += "_";
			}
			output += "\n";
		}
		return output;
	}


	public boolean gameEnded(boolean end)
	{
		if (tiles.size() < (7 * players.size()))
		{
			end = true;
		}
		return end;
	}

	public ArrayList<Player> getAllPlayer()
	{
		Collections.sort(players);
		out.println(players);
		return players;
	}
}


		/*
		try {
		Thread.sleep(3000);
		}
		catch (InterruptedException ex) {
		Thread.currentThread().interrupt();
		}
		*/