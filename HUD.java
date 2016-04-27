package mathgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HUD 
{
	private int score = 0;
	private int level = 0;
	private boolean gameOver = false;
	
	public void render( Graphics g)
	{
		Font fnt = new Font("arial", 1, 24);
		g.setFont(fnt);
		g.setColor(Color.gray);
		g.drawString("Score: " + score, 330, 50);
		g.drawString("Level: " + level, 180, 50);
		
	}
	
	public void setGameOver(boolean g)
	{
		this.gameOver = g;
	}
	
	public void setScore( int score )
	{
		this.score = score;
	}

	public void setLevel( int level )
	{
		this.level = level;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public boolean getGameOver()
	{
		return gameOver;
	}
}