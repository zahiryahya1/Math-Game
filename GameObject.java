package mathgame;

import java.awt.Graphics;

public abstract class GameObject 
{
	// initalize instance variables
	protected ID id;


	public GameObject( ID id)
	{
		this.id = id;
		
	}
	
	public abstract void render ( Graphics g );
	
	//getter and setters
	public ID getID()
	{
		return this.id;
	}

	public void setID( ID id )
	{
		this.id = id;
	}
}	