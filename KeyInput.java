package mathgame;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter 
{
	private char guess;
	
	
	public KeyInput()
	{
		
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
	if (key == KeyEvent.VK_ESCAPE)
		System.exit(1);
	
	if (key == KeyEvent.VK_DELETE)
		this.guess = (char)8;
	else
		this.guess = (char)key;
		
	}
	
	public char getGuessChar()
	{
		char tempChar = this.guess;
		this.guess = 0;
		return tempChar;
	}
	
}