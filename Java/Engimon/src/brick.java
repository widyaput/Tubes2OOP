import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class brick {
	int groundx[] ={
			0,50,100,150,200,250,300,350,400,
			0,50,100,150,200,250,300,350,400,
			0,50,100,150,200,250,300,350,400,
			0,50,100,150,200,250,300,350,400,
			200,250,300,350,400,450,500,550,600,
			200,250,300,350,400,450,500,550,600,
			200,250,300,350,400,450,500,550,600,
			200,250,300,350,400,450,500,550,600,
			0,50,100,150,200,250,300,350,400,
			0,50,100,150,200,250,300,350,400,
			0,50,100,150,200,250,300,350,400,
			0,50,100,150,200,250,300,350,400};
	
	int groundy[] = {
			0,0,0,0,0,0,0,0,0,
			50,50,50,50,50,50,50,50,50,
			100,100,100,100,100,100,100,100,100,
			150,150,150,150,150,150,150,150,150,
			200,200,200,200,200,200,200,200,200,
			250,250,250,250,250,250,250,250,250,
			300,300,300,300,300,300,300,300,300,
			350,350,350,350,350,350,350,350,350,
			400,400,400,400,400,400,400,400,400,
			450,450,450,450,450,450,450,450,450,
			500,500,500,500,500,500,500,500,500,
			550,550,550,550,550,550,550,550,550};
	
	int airx[] = {
		450,500,550,600,
		450,500,550,600,
		450,500,550,600,
		450,500,550,600};
	
	int airy[] = 
		{0,0,0,0,
		50,50,50,50,
		100,100,100,100,
		150,150,150,150
		};
	
	int rockx[] = 
		{0,50,100,150,
		0,50,100,150,
		0,50,100,150,
		0,50,100,150
		};
	
	int rocky[] = 
		{200,200,200,200,
		250,250,250,250,
		300,300,300,300,
		350,350,350,350
		};
	
	int icex[] = 
		{450,500,550,600,
		450,500,550,600,
		450,500,550,600,
		450,500,550,600};
	
	int icey[] = 
		{400,400,400,400,
		450,450,450,450,
		500,500,500,500,
		550,550,550,550};
	
	int brickON[] = new int[108];
	
	private ImageIcon groundimg;
	private ImageIcon airimg;
	private ImageIcon rockimg;
	private ImageIcon iceimg;
	
	public brick()
	{
		groundimg=new ImageIcon("break_brick.png");
		airimg=new ImageIcon("air.png");
		rockimg=new ImageIcon("rock.jpg");
		iceimg=new ImageIcon("ice.png");
		
		for(int i=0; i< brickON.length;i++)
		{
			brickON[i] = 1;
		}
	}
	
	public void draw(Component c, Graphics g)
	{
		for(int i=0; i< brickON.length;i++)
		{
			if(brickON[i]==1)
			{
				groundimg.paintIcon(c, g, groundx[i],groundy[i]);
			}
		}
	}
	
	public void draw4(Component c, Graphics g)
	{
		for(int i=0; i< rockx.length;i++)
		{			
			rockimg.paintIcon(c, g, rockx[i],rocky[i]);
		}
		
	}
	public void draw3(Component c, Graphics g)
	{
		for(int i=0; i< icex.length;i++)
		{			
			iceimg.paintIcon(c, g, icex[i],icey[i]);
		}
	}
	public void draw2(Component c, Graphics g)
	{
		for(int i=0; i< airx.length;i++)
		{			
			airimg.paintIcon(c, g, airx[i],airy[i]);
		}
	}
	public boolean checkCollision(int x, int y)
	{
		boolean collided = false;
		for(int i=0; i< brickON.length;i++)
		{
			if(brickON[i]==1)
			{
				if(new Rectangle(x, y, 50, 50).intersects(new Rectangle(groundx[i], groundy[i], 50, 50)))
				{
					brickON[i] = 0;
					collided = true;
					break;
				}
			}
		}
		
		return collided;
	}
	
//	public boolean checkSolidCollision(int x, int y)
//	{
//		boolean collided = false;
//		for(int i=0; i< solidBricksXPos.length;i++)
//		{		
//			if(new Rectangle(x, y, 50, 50).intersects(new Rectangle(solidBricksXPos[i], solidBricksYPos[i], 50, 50)))
//			{		
//				collided = true;
//				break;
//			}			
//		}
//		
//		return collided;
//	}
}
