package mathgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import mathgame.Game.STATE;

public class Question extends GameObject
{
	private String question;
	private String answer;
	private Handler handler;
	
	public Question(ID id, Handler handler, String question, String answer)
	{
		super(id);
		this.answer = answer;
		this.question = question;
		this.handler = handler;
		
	}
	
	public void render( Graphics g )
	{
		
		Font fnt = new Font("arial", 1, 50);
		Font fnt2 = new Font("arial", 1, 50);
		
		g.setFont(fnt2);
		g.setColor( Color.WHITE);
		g.drawString("Problem:", 60, 150);
		
		g.setFont(fnt);
		g.setColor(Color.WHITE);
		g.drawString(question + " ?", 300, 150);
		
		g.drawString("Answer: ", 60, 300);
		
	}
	
	public String getAnswer()
	{
		return this.answer;
	}
	
	public void setAnswer(String a)
	{
		this.answer = a;
	}
	
	public String getQuestion()
	{
		return this.question;
	}
	
	public void setQuestion(String q)
	{
		this.question = q;
	}
}