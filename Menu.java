package mathgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import mathgame.Game.STATE;

public class Menu extends MouseAdapter
{
	private Game game;
	private Handler handler;
	private Random r = new Random();
	private HUD hud;
	
	public Menu(Game game, Handler handler, HUD hud)
	{
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void mousePressed(MouseEvent e)
	{
		// mx = mouse x position
		int mx = e.getX();
		int my = e.getY();

	
		if (game.gameState == STATE.Menu)
		{
			//play button
			if(mouseOver(mx, my, 210, 150, 200, 64))
			{
				//add the questions to handler
				game.gameState = STATE.Game;
				game.setGuess(true);
			}
			
			// High Score buttton is pressed
			else if (mouseOver(mx, my, 210, 250, 200, 64))
			{
				game.gameState = STATE.Score;
			}
			
			//quit button
			else if (mouseOver(mx, my, 210, 350, 200, 64))
			{
				System.exit(1);
			}
		}
		
		//back button for help is pressed
	    if (game.gameState == STATE.Score)
		{
			if (mouseOver(mx, my, 210, 350, 200, 64))
			{
				game.gameState = STATE.Menu;
				return;
			}
		}	


		//game over back button
	    if (game.gameState == STATE.End)
		{
			if (mouseOver(mx, my, 210, 250, 200, 64))
			{
				game.gameState = STATE.Game;
				hud.setLevel(1);
				hud.setScore(0);
				
				handler.clearQuestions();
			}
			
			else if (mouseOver(mx, my, 210, 350, 200, 64))
			{
				System.exit(1);
			}
		}
	    
	    //
	    if (game.gameState == STATE.HighScore)
	    {
	    	// need to change position of buttons and print the buttons
	    	if (mouseOver(mx, my, 210, 250, 200, 64))
			{
				game.gameState = STATE.Game;
				hud.setLevel(1);
				hud.setScore(0);
				
				handler.clearQuestions();
			}
			
			else if (mouseOver(mx, my, 210, 350, 200, 64))
			{
				System.exit(1);
			}
	    	
	    }
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height)
	{
		if (mx > x && mx < x + width)
		{
			if (my > y && my < y + height)
			{
				return true;
			}
			else 
				return false;
		}
		else
			return false;
	}

	
	public void render(Graphics g)
	{
		if (game.gameState == STATE.Menu)
		{	
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("Menu", 240, 70);
			
			g.setFont(fnt2);
			g.setColor(Color.WHITE);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Play", 280, 192);
			
			g.setColor(Color.WHITE);
			g.drawRect(210, 250, 200, 64);
			g.drawString("High Score", 230, 292);
	
			
			g.setColor(Color.WHITE);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Quit", 280, 392);
		}
		
		
		
		//game over text
		else if (game.gameState == STATE.End)
		{
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("Game Over", 190, 70);
			
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("You lost with a score of: " + hud.getScore(), 100, 125);
			
			g.drawString("Try Again", 240, 292);
			
			g.drawRect(210, 250, 200, 64);
			g.drawString("Quit", 280, 392);
		}
		
		else if (game.gameState == STATE.HighScore)
		{
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("Game Over", 190, 70);
			
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("You got a high socre of: " + hud.getScore(), 100, 125);
			
			g.drawString("Try Again", 240, 292);
			
			g.drawRect(210, 250, 200, 64);
			g.drawString("Quit", 280, 392);
			
			
		}

		// Prints the high score texts
		if (game.gameState == STATE.Score)
		{
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString("High Score", 180, 70);
			
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 392);
		}

	}
	    

}