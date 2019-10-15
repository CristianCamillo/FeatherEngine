package p1;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gameEngine.SimpleGameEngine;

public class Launcher
{	
	static long c = 0;
	static long k = 0;
	static long z = 0;
	
	public static void main(String[] args) throws Exception
	{		
		new SimpleGameEngine(1600, 900, "SimpleGameEngine", true, 120, true, true)
		{
			public void update()
			{				
				if(key(KeyEvent.VK_ESCAPE))
					stop();
				
				if(keyToggle(KeyEvent.VK_ENTER))
				{
					System.out.println(c + " Enter");
					c++;
				}
				
				if(mouseToggle(MouseEvent.BUTTON1))
				{
					System.out.println(k + " Left Click");
					k++;
				}
				
				if(key(KeyEvent.VK_SPACE))
				{
					System.out.println(z + " Space bar");
					z++;
				}
				
				if(keyToggle(KeyEvent.VK_R))
					setSize(1280, 720, false);
				
				if(keyToggle(KeyEvent.VK_T))
					setTitle((int)(Math.random() * 10000) + "");
				
				if(keyToggle(KeyEvent.VK_L))
					setFPSLock(!getFPSLock());
				
				if(keyToggle(KeyEvent.VK_C))
					setFPSCap(getFPSCap() == 120 ? 60 : 120);
				
				if(keyToggle(KeyEvent.VK_F))
					setSize(1280, 720, !getFullscreen());
			}
			
			public void render()
			{
				Graphics g = getGraphics();
				
				g.drawLine(0, 0, getWidth(), getHeight());
				
				g.drawString(getWidth() + " x " + getHeight(), 200, 200);
			}
		};
		
		System.out.println("FINE");
	}
}
