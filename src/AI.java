import java.util.ArrayList;
import static java.lang.System.*;
import java.io.IOException;
import java.util.Arrays;

public class AI extends Player implements Comparable<Player>{

	private final int HandSize = 7;
	private final int[] values = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10};
	private final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int totalScore;
	private String[] hand;
	private String[] handCopy;
	private boolean currentPlayer;
	private String bestWord;
	private ArrayList <String> wordList;
	private String letter;
	public ArrayList<String> words;
	private ArrayList<Integer> points;
	private boolean tryAgain;

	public AI(ArrayList<String> wrds) throws IOException
	{
		super("AI");
		currentPlayer = false;
		wordList = wrds;
		bestWord = "";
		hand = new String[7];
		totalScore = 0;
		letter = "";
		tryAgain = false;
		words = new ArrayList<String>();
		points = new ArrayList<Integer>();
	}

	public boolean replaceLettersAI(ArrayList<String> tiles)
	{
		String[] temp = getLetters(bestWord);
		for (int i=0;i<hand.length;i++)
		{
			for (int j=0;j<temp.length;j++)
			{
				if (hand[i].equals(temp[j]) && !(hand[i].equals(letter)))
				{
					int a = (int)(Math.random() * tiles.size());
					String x = "";
					try {
					x = tiles.remove(a);
					}
					catch (IndexOutOfBoundsException ex) {
					return false;
					}

					hand[i] = x;
					temp[j] = "";
				}
			}
		}
		handCopy = Arrays.copyOf(hand,hand.length);
		words = new ArrayList<String>();
		return true;
	}

	public int findWordScore(String word)
	{
		String [] letters = getLetters(word);
		int score = 0;
		for(int i=0;i<letters.length;i++)
		{
			int index = alphabets.indexOf(letters[i]);
			score += values[index];
		}
		//totalScore += score;
		return score;
	}

	public ArrayList<String> findWords()
	{
		ArrayList<String> temp = new ArrayList<String>();
		for (String s : wordList)
		{
			if (s.length() < 8 && s.length() > 2)
			{
				if (isValidWordAI(s))
				{
					temp.add(s);
				}
			}
		}
		words = temp;
		return temp;
	}

	public boolean isValidWordAI(String word)
	{
		Boolean valid = false;
		word = word.toUpperCase();
		String[] letters = getLetters(word);
		String[] temp = Arrays.copyOf(hand,hand.length);
		if (!wordList.contains(word))
		{
			return false;
		}
		for (int i=0;i<hand.length;i++)
		{
			for (int j=0;j<letters.length;j++)
			{
				if (temp[i].equals(letters[j]))
				{
					letters[j] = "";
					temp[i] = "";
				}
			}
		}
		for (int i=0;i<letters.length;i++)
		{
			if (letters[i] != "")
			{
				return false;
			}
		}
		return true;
	}

	public String findBestWord()
	{
		String word = "";
		int score = 0;
		for (String s : words)
		{
			int temp = findWordScore(s);
			if (temp > score)
			{
				score = temp;
				word = s;
			}
		}
		bestWord = word;
		return word;
	}

	public String[] findBestMove(String[][] temp)
	{
		String[] y = new String[3];
		if (boardIsEmpty(temp)){
			return new String[]{"7","7","h"};
		}
		String[] x = getLetters(bestWord);
		ArrayList<Integer> coordinates = new ArrayList<Integer>();
		if (!tryAgain){
			for (int a=0;a<x.length;a++){
				for (int i=0;i<15;i++){
					for (int j=0;j<15;j++){
						if (x[a].equals(temp[i][j])){
							coordinates.add(i);		//X = 0
							coordinates.add(j);		//y = 1
						}
					}
				}
			}
		}
		if (tryAgain){
			coordinates = points;
		}
		points = new ArrayList<Integer>(coordinates);
		if (wordVertical(temp, coordinates)){
			int index = 0;
			String letter = temp[coordinates.get(0)][coordinates.get(1)];
			int indexLetter = bestWord.indexOf(letter);
			if (indexLetter == 0){
				index = coordinates.get(1);
			}
			else {
				index = coordinates.get(1) - indexLetter;
			}
			return new String[]{String.valueOf(coordinates.get(0)),String.valueOf(index),"v"};
		}

		if (wordHorizontal(temp, coordinates)){
			int index = 0;
			String letter = temp[coordinates.get(0)][coordinates.get(1)];
			int indexLetter = bestWord.indexOf(letter);
			if (indexLetter == 0){
				index = coordinates.get(0);
			}
			else {
				index = coordinates.get(0) - indexLetter;
			}
			return new String[]{String.valueOf(index),String.valueOf(coordinates.get(1)),"h"};
		}
		if (coordinates.isEmpty() && temp[0][0].equals("")) { //top-left corner
			return new String[]{"0","0","h"};
		}

		else if (coordinates.isEmpty() && temp[0][14].equals("")) { //bottom-left corner
			return new String[]{"0","14","h"};
		}

		else if (coordinates.isEmpty() && temp[14][0].equals("")) { //top-right corner
			int a = 15 - bestWord.length();
			return new String[]{String.valueOf(a),"0","h"};
		}

		else if (coordinates.isEmpty() && temp[14][14].equals("")) { //bottom-right corner
			int a = 15 - bestWord.length();
			return new String[]{String.valueOf(a),"14","h"};
		}
		return new String[]{"0","0","v"};
	}

	public boolean tryAgain()
	{
		if (points.isEmpty())
		{
			tryAgain = false;
			return false;
		}
		points.remove(0);
		points.remove(0);
		tryAgain = true;
		return true;
	}

	public boolean wordHorizontal(String[][] temp, ArrayList<Integer> coordinates)
	{
		if (!coordinates.isEmpty())
		{
			if (coordinates.get(0) > 0 && coordinates.get(0) < 15 && coordinates.get(1)-1 > 0 && coordinates.get(1)-1 < 15 && coordinates.get(1)+1 > 0 && coordinates.get(1)+1 < 15)
			{
				if (!(temp[coordinates.get(0)][coordinates.get(1)-1].equals("")) || !(temp[coordinates.get(0)][coordinates.get(1)+1].equals("")))
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean wordVertical(String[][] temp, ArrayList<Integer> coordinates)
	{
		if (!coordinates.isEmpty())
		{
			if (coordinates.get(1) > 0 && coordinates.get(1) < 15 && coordinates.get(0)-1 > 0 && coordinates.get(0)-1 < 15 && coordinates.get(0)+1 > 0 && coordinates.get(0)+1 < 15)
			{
				if (!(temp[coordinates.get(0)-1][coordinates.get(1)].equals("")) || !(temp[coordinates.get(0)+1][coordinates.get(1)].equals("")))
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean boardIsEmpty(String[][] temp)
	{
		for (int i=0;i<15;i++)
		{
			for (int j=0;j<15;j++)
			{
				if (!(temp[i][j].equals("")))
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
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

	@Override
	public String getName()
	{
		return super.getName();
	}

	@Override
	public String[] getHand()
	{
		return hand;
	}

	@Override
	public int getScore()
	{
		return totalScore;
	}

	public void addBestScore()
	{
		totalScore += findWordScore(bestWord);
	}

	@Override
	public void setCurrentPlayer(boolean bool)
	{
		currentPlayer = bool;
	}

	@Override
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

	@Override
	public void setHand(String[] hnd)
	{
		hand = hnd;
	}

	@Override
	public void handRestore()
	{
		hand = handCopy;
	}

	@Override
	public int getWordScore(String word)
	{
		String [] letters = getLetters(word);
		out.println(Arrays.toString(letters) + "word score print");
		int score = 0;
		for(int i=0;i<letters.length;i++)
		{
			int index = alphabets.indexOf(letters[i]);
			score += values[index];
		}
		totalScore += score;
		return score;
	}

	@Override
	public String[] getLetters(String word)
	{
		String[] temp = new String[word.length()];
		for (int i=0;i<word.length();i++)
		{
			temp[i] = word.substring(i,i+1);
		}
		return temp;
	}

	public boolean isValidBoard(String [][] mat, String[] position)
	{
		//position[0] - X, position[1] - Y, position[2] - h/v
		String temp = "";
		if (position.length == 2)
		{
			return false;
		}
		int x = Integer.parseInt(position[0]);
		int y = Integer.parseInt(position[1]); //probably enters here  
		/*
		if (position[2].equals("h"))
		{
			if (y > 0 && (y+1+bestWord.length()) < 15 && x > 0 && x < 15)
			{
				if (!mat[x][y-1].equals(""))
				{
					return false;
				}
				if (!mat[x][y+1+bestWord.length()].equals(""))
				{
					return false;
				}
			}
		}
		if (position[2].equals("h"))
		{
			if (y > 0 && (y+1+bestWord.length()) < 15 && x > 0 && x < 15)
			{
				if (!mat[x-1][y].equals(""))
				{
					return false;
				}
				if (!mat[x+1+bestWord.length()][y].equals(""))
				{
					return false;
				}
			}
		}
		*/
		return true;
	}

	@Override
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
		return Double.compare(getScore(),p.getScore());
	}
}