import java.util.ArrayList;
import static java.lang.System.*;
import java.io.IOException;
import java.util.Arrays;

public class Player implements Comparable<Player> {

	private final int HandSize = 7;
	private String[] hand;
	private String name;
	private int totalScore;
	public boolean currentPlayer;
	private int passCount;
	private String[] handCopy;
	private final int[] values = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
	private final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public Player(int num) throws IOException
	{
		hand = new String[HandSize];
		handCopy = new String[HandSize];
		name = "Player" + (num+1);
		totalScore =  0;
		passCount = 0;
	}

	public Player(String nam) throws IOException
	{
		hand = new String[HandSize];
		handCopy = new String[HandSize];
		totalScore = 0;
		name = nam;
		passCount = 0;
	}

	public void dealHand(ArrayList<String> tiles)
	{
		for (int i=0;i<HandSize;i++)
		{
			int a = (int)(Math.random() * tiles.size());
			String x = tiles.remove(a);
			hand[i] = x;
		}
		handCopy = Arrays.copyOf(hand,hand.length);

	}

	public void replaceLetters(ArrayList<String> tiles)
	{
		for (int i=0;i<hand.length;i++)
		{
			if (hand[i].equals(""))
			{
				int a = (int)(Math.random() * tiles.size());
				String x = tiles.remove(a);
				hand[i] = x;
			}
		}
		handCopy = Arrays.copyOf(hand,hand.length);
	}

	public String getName()
	{
		return name;
	}

	public String[] getHand()
	{
		return hand;
	}

	public int getScore()
	{
		return totalScore;
	}

	public void addScore(int s)
	{
		totalScore	+= s;
	}

	public void setCurrentPlayer(boolean bool)
	{
		currentPlayer = bool;
	}

	public void changeHand(String letter)
	{
		for (int i=0;i<7;i++)
		{
			if (hand[i].equals(letter))
			{
				hand[i] = "";
				break;
			}
		}
	}

	public void setHand(String[] hnd)
	{
		hand = hnd;
	}

	public void handRestore()
	{
		hand = Arrays.copyOf(handCopy,handCopy.length);
	}

	public int getWordScore(String word)
	{
		String [] letters = getLetters(word);
		out.println(Arrays.toString(letters));
		int score = 0;
		for(int i=0;i<letters.length;i++)
		{
			int index = alphabets.indexOf(letters[i]);
			score += values[index];
		}
		totalScore += score;
		return score;
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

	public Boolean equals(Player p)
	{

		if (getScore() > p.getScore())
		{
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Player p)
	{
		//1 = Greater
		//-1 = Smaller

		if (getScore() < p.getScore())
		{
			return 1;
		}
		else if (getScore() > p.getScore())
		{
			return -1;
		}
		return 0;

		//return Double.compare(getScore(),p.getScore());
	}

	@Override
	public String toString()
	{
		return name + " " + totalScore;
	}

}
