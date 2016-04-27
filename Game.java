/*
 * Author: Zahir Yahya
 * FileName: Game.java
 * Description: This file makes a game class when running the game.
 * 				It sets up everything.
 * Last modified Date:
 */

package mathgame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.Scanner;


/*
 * Name: Game
 * Description: Runs the program
 */
public class Game extends Canvas implements Runnable
{	

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	
	private KeyInput key;
	private Handler handler;
	private Random r;
	private HUD hud;
	private Menu menu;
	public boolean guess;
	private boolean startGame = true;
	private String userGuess = "";
	private ArrayList<String> nameList;
	private ArrayList<Integer> scoreList;
	private String userName;
	
	// runs the program
	public static void main(String args[])
	{
		new Game();
	}
	
    // creates an enum state to keep track of what game state we are in
	public enum STATE 
	{
		Menu,
		Game,
		End,
		HighScore,
		Score,
	};	
	
	// begin in the menu state
	public static STATE gameState = STATE.Menu;
	
	public Game()
	{	
		// creates objects
		handler = new Handler();
		hud = new HUD();
		menu = new Menu( this, handler, hud);
		guess = false;
		key = new KeyInput( );
		scoreList = new ArrayList<Integer>();
		nameList =  new ArrayList<String>();
		
		// gets input from the mouse and keybord
		this.addKeyListener( key );
		this.addMouseListener( menu );
		
		// creates the window
		new Window( WIDTH, HEIGHT, "HOW GOOD ARE YOU AT MATH?", this);
		r = new Random();
		
		if ( gameState == STATE.Game )
		{
			level();
		}
		
	}
	
	// this method is called when the game begins. Its starts the game.
	public synchronized void start()
	{

		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	// this method stops the game
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
		guess = true;
		// TODO Auto-generated method stub
		// 30 frames a second
		while ( running )
		{
			char c = key.getGuessChar();
			// to render the menu once
			try 
			{
				thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// renders the game (question) and sets the guess to false
			// after each guess
			render();
			guess = false;


			
			if (!guess && gameState == STATE.End)
			{
				render();
				guess = true;
			}
			
			
			//prints guess on screen
			if (gameState == STATE.Game)
			{
				// if press enter
				if ( (int)c == 10 )
				{
					// compare correct answer with the users answer.
					if ( userGuess.equals( ((Question) handler.getGameObject()).getAnswer() ) )
					{
						guess = true;
						userGuess = "";
					}
					
					else
					{
						gameState = STATE.End;
						
						// check if user score becomes a high score
						int i;
						for (i = 0; i < scoreList.size(); i ++)
						{
							if (hud.getScore() > scoreList.get(i))
							{
								gameState = STATE.HighScore;
								break;
							}
						}
						
						// adds the new score and user name
						if ( i < scoreList.size())
						{
							char name = key.getGuessChar();

							while ((int)name != 10)
							{
								// if not delete or null, add char to string
								if ( (int)c != 127 && (int)c != 0 )
								{
									userName = userName + c;
									System.out.println(userName);

								}

								//delete char
								if ( (int) c == 8 && userName.length() > 1)
								{
									System.out.println(userName);
									userName = userName.substring(0, userName.length() - 2);
									System.out.println(userName);

								}
								
								render();

							}
							
							scoreList.add(i, hud.getScore());
							nameList.add(i, userName);
							scoreList.remove(scoreList.size() - 1);
							nameList.remove(nameList.size() - 1);
							
							gameState = STATE.Menu;
							
							// save the scores
							String filename = "score.txt";

							try
							{
								PrintWriter writer = new PrintWriter(filename, "UTF-8");
								int j = 0;
								while(nameList.get(j) != null)
								{
									writer.println(nameList.get(j));
									writer.println(Integer.toString( scoreList.get(j) ));
									j++;
								}
								writer.close();
							}catch(FileNotFoundException ex) {
					            System.out.println(
					                    "Unable to write file '" + 
					                    filename + "'");                
					        }
					        catch(IOException ex) {
					                System.out.println(
					                    "Error writing file '" 
					                    + filename + "'");                  
					                // Or we could just do this: 
					                // ex.printStackTrace();
					        }
	
						}
					}
				}
				
				// if not delete or null, add char to string
				else if ( (int)c != 127 && (int)c != 0 )
				{
					userGuess = userGuess + c;

				}

				//delete char
				if ( (int) c == 8 && userGuess.length() > 1)
				{
					userGuess = userGuess.substring(0, userGuess.length() - 2);
				}
			}	
		}
		
		stop();
		
	}
	
	
	private void render()
	{
		//create an strategy for multi-buffering.
		//returns the buffer strategy used by this component
		//jframe extends windows so i can call this method 
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(2);
			return;
		}
	
		//gets the graphic
		Graphics g = bs.getDrawGraphics();
		//sets the background
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		
		// renders the game
		if (gameState == STATE.Game)
		{
			if (startGame)
			{
				hud.setScore(0);
				hud.setLevel(0);
				startGame = false;
			}
			
			if ( hud.getScore() % 5 == 0 && guess == true) {
				handler.clearQuestions();
				hud.setLevel(hud.getLevel() + 1);
				handler.clearQuestions();
				level();
				handler.nextQuestion();
			}
			
			if ( guess )
			{
				hud.setScore(hud.getScore() + 1);
				handler.nextQuestion();
			}

			hud.render(g);
			//renders the Questions graphic
			handler.render(g);
			printGuess(g, userGuess);
			
		}
		
		// renders highScore menu
		else if (gameState == STATE.HighScore)
		{	printGuess(g, userName);
			guess = false;
			startGame = true;
			userGuess = "";
			handler.clearQuestions();
			menu.render(g);
			
		}
		
		//renders the menu
		else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Score)
		{
			guess = false;
			startGame = true;
			menu.render(g);
			userGuess = "";
			handler.clearQuestions();
		}
		
		// displays the buffer
		g.dispose();
		bs.show();
	}
	
	public void level()
	{
		if (hud.getLevel() == 1)
		{
			openFileQuestion("level1.txt");
		}
		
		if (hud.getLevel() == 2)
		{
			openFileQuestion("subtraction.txt");
		}
		
	}
	
	public void setGuess(boolean g)
	{
		guess = g;
	}
	
	public void printGuess( Graphics g, String s)
	{
		g.setColor(Color.WHITE);
		if (s != null)
			g.drawString(s,  285,  300);
	}
	
	//saves high score into a file.
	// when the game begins, read from the file (only holds top 5 scores)
	// when the game ends, check if the score is greater then any score in the list.
	// if the player gets a high score then ask user if they would like to save their score.
	// if they click on the yes button, then ask for name.
	// the list should remove the lowest score and add the current score in the right order.
	// when the game is over, it should write the new list of scores.
	// there should be a high score tab in the menu. that displays the top ten scores.
	
	public void saveScore()
	{
		
		
		
		
	}
	
	public void openScore()
	{
		//open file, and read input into the question objects.
		// then fill the handler list with the questions
		String filename = "score.txt";
		String name;
		String score;
		int intScore;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            name = bufferedReader.readLine();
            score = bufferedReader.readLine();
            
            while(name != null && score != null ) {
            	// add name and score to a list
            	nameList.add(name);
            	intScore = Integer.parseInt(score);
            	scoreList.add(intScore);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }    
	}
	
	public void openFileQuestion(String filename)
	{
		//open file, and read input into the question objects.
		// then fill the handler list with the questions
        String question = null;
        String answer = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(filename);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            question = bufferedReader.readLine();
            answer = bufferedReader.readLine();
            
            while(answer != null && question != null ) {
            	
                handler.addObject( new Question(ID.Question, handler, question, answer));
	            question = bufferedReader.readLine();
	            answer = bufferedReader.readLine();
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                filename + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + filename + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }    
		
	}
	
}