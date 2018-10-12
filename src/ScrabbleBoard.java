import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Canvas;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import static java.lang.System.*;
import java.io.IOException;
import javax.swing.*;


class ScrabbleBoard extends Canvas implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xPos, yPos, s;
	private int score;
	private String[][] matrix;
	private Scrabble game;
	private Player p;
	private String[] hand;
	public boolean next, passPressed, replaceAll, reset, wait, endGame, let;
	private static int boxesColored;
	private String hold;
	private String word;
	private String tempWord;
	private String[] letterVals = {"A - 1","B - 3","C - 3","D - 2","E - 1","F - 4","G - 2","H - 4","I - 1","J - 8","K - 5","L - 1","M - 3",
	"N - 1","O - 1","P - 3","Q - 10","R - 1","S - 1","T - 1","U - 1","V - 4","W - 4","X - 8","Y - 4","Z - 10"};
	private ArrayList<Integer> coordinatesX;
	private ArrayList<Integer> coordinatesY;
	private ArrayList<Integer> coordinates;
	public boolean aiPlaying;

	public ScrabbleBoard(int numPlayers, String[] playerList) throws IOException
	{
		setBackground(Color.BLACK);
		next = false;
		replaceAll = false;
		passPressed = false;
		endGame = false;
		game = new Scrabble(numPlayers,playerList);
		game.dealAllHand();
		xPos = 50; yPos = 934;
		addMouseListener(this);
		p = game.getNextPlayer();
		p.setCurrentPlayer(true);
		matrix = game.getBoard();
		word = "";
		tempWord = "";
		hand = Arrays.copyOf(p.getHand(),p.getHand().length);
		coordinatesX = new ArrayList<Integer>();
		coordinatesY = new ArrayList<Integer>();
		aiPlaying = false;
	}

	@Override
	public void paint( Graphics window )
	{
		window.setColor(Color.RED);
		window.setFont(new Font("TAHOMA",Font.BOLD,12));
		if (endGame)
		{
			endGame = game.gameEnded(endGame);
		}
		if (replaceAll)
		{
			game.replaceHand(p);
			next = false;
			replaceAll = false;
			passPressed = true;
			reset = true;
			word = "";
		}

		if (next)
		{
			int x = Integer.parseInt(findLetter());
			tempWord = removeDuplicates(tempWord,x);
			out.println(tempWord);
			if (game.isValidWord(tempWord))
			{
				wait = true;
				passPressed = true;
				p.getWordScore(tempWord);
				p.replaceLetters(game.getTiles());
				word = "";
				tempWord = "";
				coordinatesX = new ArrayList<Integer>();
				coordinatesY = new ArrayList<Integer>();
				next = false;
				replaceAll = false;
				repaint();
			}
			else
			{
				String input = JOptionPane.showInputDialog("PLEASE TYPE IN WORD AGAIN: ");
				if (input == null)
				{
					input = "";
				}
				reset = true;
				if (game.isValidWord(input))
				{
					passPressed = true;
					p.getWordScore(tempWord);
					p.replaceLetters(game.getTiles());
					word = "";
					tempWord = "";
					coordinatesX = new ArrayList<Integer>();
					coordinatesY = new ArrayList<Integer>();
					next = false;
					replaceAll = false;
					repaint();
				}
				else
				{
					reset = true;
					next = false;
					JOptionPane.showMessageDialog(this, "Word is not valid. Try Again");
				}
			}
		}
		if (reset)
		{
			game.clearWord(coordinatesX, coordinatesY);
			coordinatesX = new ArrayList<Integer>();
			coordinatesY = new ArrayList<Integer>();
			reset = false;
			word = "";
			tempWord = "";
			p.handRestore();
			hand = Arrays.copyOf(p.getHand(),p.getHand().length);
			repaint();
		}
		if (passPressed)
		{
			p.setCurrentPlayer(false);
			p = game.getNextPlayer();
			p.setCurrentPlayer(true);
			if (p.getName().equals("AI"))
			{
				AI ai = (AI)p;
				aiPlaying = true;
				out.println(Arrays.toString(ai.getHand()));
				ai.findWords();
				String x = ai.findBestWord();
				if (ai.words.isEmpty() || x == null || x.equals(""))
				{
					replaceAll = true;
					passPressed = false;
				}
				String[] y = ai.findBestMove(game.getBoard());
				if (!(game.updateBoard(x,Integer.valueOf(y[0]),Integer.valueOf(y[1]),y[2])) || !ai.isValidBoard(game.getBoard(),y))
				{
					reset = true;
					if (!(ai.tryAgain()))
					{
						replaceAll = true;
						repaint();
					}
					while (ai.tryAgain())
					{
						y = ai.findBestMove(game.getBoard());
						if (game.updateBoard(x,Integer.valueOf(y[0]),Integer.valueOf(y[1]),y[2]) && ai.isValidBoard(game.getBoard(),y))
						{
							break;
						}
					}
				}
				if (game.updateBoard(x,Integer.valueOf(y[0]),Integer.valueOf(y[1]),y[2]) && ai.isValidBoard(game.getBoard(),y))
				{
					ai.addBestScore();
				}
				if (!ai.replaceLettersAI(game.getTiles()))
				{
					endGame = true;
					repaint();
				}
				aiPlaying = false;
				p.setCurrentPlayer(false);
				p = game.getNextPlayer();
				p.setCurrentPlayer(true);
				repaint();
			}
			passPressed = false;
			word = "";
			hand = Arrays.copyOf(p.getHand(),p.getHand().length);
			repaint();
		}
		if (!(endGame))
		{
			drawBoxes(window);
			letterValues(window, letterVals);
			matrix = game.getBoard();
		  	boardUpdate(window,matrix);
			playerInfo(window, p.getHand(), p.getName(), p.getScore(),
			game.getNameOfOpponents(),game.getScoreOfOpponents());
		  	updateBoxes(window, xPos, yPos);
		  	handBoard(window,xPos, yPos, hand);
		  	if(let)
			{
				letterValues(window, letterVals);
			}
		}
		if (endGame)
		{
			endScreen(window);
		}
	}

	public void drawBoxes(Graphics window)
	{
		window.setColor(new Color(102,51,0));
		window.fillRect(20,5,640,640);
		int x = 40;
		int y = 25;
		int a = 50;
		int k = 1;
		for(int j =0; j<15; j++)
		{
			window.setColor(Color.RED);
			window.setFont(new Font("TAHOMA",Font.BOLD,12));
			window.drawString((j+1)+"",a+5,20 );
			a+=40;
			for(int i = 0; i<15; i++)
			{
				if(i==7 && j==7)
				{
					k++;
					window.setColor(Color.GREEN);
					window.fillRect(x,y,40,40);
					x = x + 40;
				}
				else
				{
				  k++;
				  if(k%2==0)
				  {
				      window.setColor(new Color(255,0,0));
				  }
				  else
				  {
				      window.setColor(new Color(255,255,255));
				  }
				  window.fillRect(x,y,40,40);
				  x = x + 40;
				}
			}
			y = y + 40;
			x = 40;
		}
	}

	public void letterValues(Graphics window,String[] vals)
	{
		window.setFont(new Font("TIMES NEW ROMAN",Font.PLAIN,20));
		int y = 360;
		for (int i = 0; i<13; i++)
		{
			window.drawString(vals[i],1100,y);
			y+=27;
		}
		y = 360;
		for (int i = 13; i<vals.length; i++)
		{
			window.drawString(vals[i],1180,y);
			y+=27;
		}
	}

	public void boardUpdate(Graphics window, String[][] temp)
	{
		for (int i = 0; i<temp.length; i++)
		{
			for (int j = 0; j < temp[i].length; j++)
			{
				window.setColor(Color.BLACK);
				if(!(temp[i][j].equals("")))
				{
					window.setFont(new Font("TAHOMA",Font.BOLD,20));
			  		window.drawString(temp[i][j], 52+(40*(i)),50+(40*(j)) );
				}
			}
		}
	 }

	public void handBoard(Graphics window,int x1,int y1,String[] array)
	{
		int x = 670;
		int y = 210;
		for (int i = 0; i<7;i++)
		{
			if(x1>=(670+(40*i)+(15*i))&&x1<(670+((40)*(i+1))+(15*i))&&y1>=210&&y1<250)
			{
				window.setColor(Color.BLUE);
				window.fillRect(670+(40*i)+(15*i),210,41,41);
				s = 670+(40*i)+(15*i);
				hold = array[i];
				window.setColor(Color.RED);
				window.drawString(hold, 12+x,26+y );
				break;
			}
			x+=55;
		}
	}

	public void playerInfo(Graphics window, String array[], String name, int score, ArrayList<String> oName, ArrayList<Integer> oScore )
	{
	 	int length = 0;
	 	if (name.length()>5)
	 	{
	 		length = 25;
	 	}
	 	else
	 	{
	 		length = 30;
	 	}

	 	window.setColor(Color.ORANGE);
	 	window.drawRect(660,5,200,130);
	 	window.drawRect(860,5,200,130);
	 	window.drawRect(660,5,400,30);
	 	window.drawRect(660,135,400,200);
	 	window.drawRect(660,135,400,30);
	 	window.setColor(Color.WHITE);
	 	window.setFont(new Font("TAHOMA",Font.BOLD,20));
		window.drawString("Player", 720,27 );
		window.setFont(new Font("TAHOMA",Font.BOLD,length));
		window.setColor(Color.YELLOW);
		window.drawString(name, 720,90 );
		window.setColor(Color.WHITE);
	 	window.setFont(new Font("TAHOMA",Font.BOLD,20));
		window.drawString("Score", 930,27);
		window.setColor(Color.YELLOW);
		window.setFont(new Font("TAHOMA",Font.BOLD,length));
		window.drawString(score+"", 950,90 );
		window.setColor(Color.WHITE);
	 	window.setFont(new Font("TAHOMA",Font.BOLD,20));
		window.drawString(" Current Hand ",780,157 );
		int linelength = 0;
		if(oName.size()>0)
		{
			int xC = 1060;
			int yC = 5;
			window.setColor(Color.ORANGE);
			window.drawRect(xC,yC,295,30);
			linelength = 30;
			if (oName.size() == 1)
			{
				window.drawString("Opponent",xC+40,yC+20 );
				window.drawString("Score",xC+210,yC+20 );
			}
			else
			{
				window.drawString("Opponents",xC+40,yC+20 );
				window.drawString("Scores",xC+210,yC+20 );
			}

			for(int i = 0; i<oName.size(); i++)
			{
				window.drawRect(xC,yC+30,295,100);
				window.drawString(oName.get(i),xC+80,yC+80 );
				window.drawString(oScore.get(i)+"",xC+240,yC+80 );
				linelength+=100;
				yC+=100;
			}
		}
		window.drawLine(1257,5,1257,linelength);

		window.setColor(Color.WHITE);
	 	int x = 670;
		int y = 210;
	 	for (int i = 0; i<array.length;i++)
	 	{
	 		window.setColor(Color.YELLOW);
			window.drawRect(x,y,40,40);
			window.setColor(Color.YELLOW);
			window.setFont(new Font("TAHOMA",Font.BOLD,20));
			window.drawString(array[i], 12+x,26+y );
			x+=55;
	 	}
	}

	public void updateBoxes(Graphics window,int x1,int y1)
	{
	 	for (int i = 0; i<15; i++)
		{
			for (int j = 0; j < 15; j++)
			{
				if(!(matrix[i][j].equals("")))
				{
					window.setColor(Color.BLACK);
					window.fillRect(40+(40*i),25+(40*j),40,40);
					window.setColor(Color.WHITE);
					window.drawString(matrix[i][j],50+(40*i),50+(40*j));
				}
			}
		}
	 	if(hold!=null)
	 	{
	 	   window.setColor(Color.BLACK);
	       for (int i = 0; i<15; i++)
			{
				for (int j = 0; j < 15; j++)
				{
					if(matrix[i][j].equals(""))
					{
						if(x1>=(40+(40*i))&&x1<(40+(40*(i+1)))&&y1>=(25+(40*j))&&y1<(25+(40*(j+1))))
						{
							window.setColor(Color.BLACK);
							window.fillRect(40+(40*i),25+(40*j),40,40);
							matrix[i][j] = hold;
							word += hold;
							out.println(word);
							coordinatesX.add(i);
							coordinatesY.add(j);
							p.changeHand(hold);
							window.setColor(Color.WHITE);
							window.drawString(matrix[i][j],50+(40*i),50+(40*j));
							window.setColor(Color.BLACK);
							window.fillRect(s,210,40,40);
							window.setColor(Color.YELLOW);
							window.drawRect(s,210,40,40);
							window.setColor(Color.BLACK);
							boxesColored++;
							hold = null;
						}
					}
					else
					{
						if(!(matrix[i][j].equals("")))
						{
							window.setColor(Color.BLACK);
							window.fillRect(40+(40*i),25+(40*j),40,40);
							window.setColor(Color.WHITE);
							window.drawString(matrix[i][j],50+(40*i),50+(40*j));

						}
						boxesColored++;
					}
				}
			}
	 	}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		xPos = e.getX();
		yPos = e.getY();
		//System.out.println(xPos + ", " + yPos);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e){}

	public String findLetter() //Finds letters in between tht the user didn't click.
	{
		tempWord = "";
		int num = -1; //if x is same num = 0 || if y is same num = 1 || if both are same num = 2
		String tempx = "";
		for (int i=0;i<coordinatesX.size()-1;i++)
		{
			if (coordinatesX.get(i) == coordinatesX.get(i+1))
			{
				num = 0;
			}
			if (coordinatesY.get(i) == coordinatesY.get(i+1))
			{
				num = 1;
			}
			if (coordinatesX.get(i) == coordinatesX.get(i+1) && coordinatesY.get(i) == coordinatesY.get(i+1))
			{
				num = 2;
			}
		}
		for (int x = 0; x<15; x++)
		{
			for (int y = 0; y<15; y++)
			{
				if (num == 0)
				{
					tempx += matrix[coordinatesX.get(0)][y];
					if (game.isValidWord(tempx))
					{
						tempWord += tempx;
						return tempx;
					}
					tempWord += tempx;
					tempx = "";
				}
				if (num == 1)
				{
					tempx += matrix[x][coordinatesY.get(0)];
					if (game.isValidWord(tempx))
					{
						tempWord += tempx;
						return tempx;
					}
					tempWord += tempx;
					tempx = "";
				}

				if (num == 2)
				{
				}
			}
		}
		return String.valueOf(num);
	}

	public String removeDuplicates(String input,int x)
	{
		String result = "";
		if(x== 0)
		{
			result = input.substring(0,(input.length()/15));
			return result;
		}
		if(x == 1)
		{
			for (int i=14;i<input.length();i+= 14)
			{
				result += String.valueOf(input.charAt(i));
			}
			return result;
		}
		return "";
	}

	public void endScreen(Graphics window)
	{
		window.setColor(Color.BLACK);
		window.fillRect(0,0,10000,10000);
		window.setColor(Color.ORANGE);
		window.drawRect(450,120,500,300);
		window.drawLine(430+360,120,360+430,300+120);
		window.drawLine(450,170,950,170);
		window.setFont(new Font("TAHOMA",Font.BOLD,30));
		window.drawString("Players                    Score",540,155);
		ArrayList<Player> x = game.getAllPlayer();
		if (x.get(0).getName().equals("AI") || x.get(1).getName().equals("AI"))
		{
			if (x.get(0).getScore() < x.get(1).getScore())
			{
				Player temp = x.get(0);
				x.set(0,x.get(1));
				x.set(1,temp);
			}
		}
		window.setFont(new Font("AGENCY FB",Font.PLAIN,60));
		window.drawString(x.get(0).getName() + " WINS!",600,500);
		window.setFont(new Font("TAHOMA",Font.ITALIC,30));
		for (int i=0;i<x.size();i++)
		{
			window.setColor(Color.RED);
			window.drawString(x.get(i).getName(),470,210+(60*i));
			window.drawString(String.valueOf(x.get(i).getScore()),860,210+(60*i));
		}
		window.setColor(Color.BLUE);
		window.setFont(new Font("TAHOMA",Font.PLAIN,80));
		window.drawString("SCORE-BOARD",440,100);
	}
}