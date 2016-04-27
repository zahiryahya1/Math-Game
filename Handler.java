package mathgame;


import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Handler 
{	
	// linked list to keep track of all the objects
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	Random r = new Random();
	GameObject currentObject;

	public void render( Graphics g)
	{	
					currentObject.render(g);
	}
	
	public void nextQuestion()
	{
		GameObject tempObject;
		int size = object.size();
		if ( size > 0)
		{
			// render question
			do{
				int i = r.nextInt(size);
				tempObject = object.get(i);
			
			} while (tempObject.getID() != ID.Question);
			
		currentObject = tempObject;
		this.removeObject(tempObject);
		}
	}
	
	
	public void addObject( GameObject object )
	{
		this.object.add( object );
	}
	
	public void removeObject( GameObject object )
	{
		this.object.remove( object );
	}
	
	public void gameOver()
	{
		// later on add a top score system.
		object.clear();
	}
	
	// might have to change add hud because i dont think hud is in the link list
	public void clearQuestions()
	{
		for ( int i = 0; i < object.size(); i++ )
		{
				object.clear();
		}
	}
	
	public GameObject getGameObject()
	{
		return (Question)this.currentObject;
	}
}
	
	