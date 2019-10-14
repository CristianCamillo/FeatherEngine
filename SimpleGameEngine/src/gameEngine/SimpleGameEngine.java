package gameEngine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class SimpleGameEngine implements KeyListener, MouseListener
{
	// Cristian Camillo, 2019-10-14 (if for some reason you want to use this, please credit me)
	
	// COPY-PASTE THIS CLASS (and this class only) INTO OTHER PROJECTS.
	// NO NEED TO EDIT THIS CLASS
	
	/*********************************************************************/
	/* Base components                                                   */
	/*********************************************************************/
	
	private final JFrame frame;
	private final Canvas canvas;
	
	/*********************************************************************/
	/* Settings                                                          */
	/*********************************************************************/
	
	private int width;
	private int height;
	private String title;
	private boolean FPSLock;
	private int FPSCap;
	private boolean showFPS;
	private boolean fullscreen;
	
	/*********************************************************************/
	/* Game Loop variables                                               */
	/*********************************************************************/
	
	private boolean running = true;
	private int frameCounter = 0;	
	private int FPS = 0;
	private long elapsedTime = 0;	
	
	/*********************************************************************/
	/* Keyboard and Mouse Inputs                                         */
	/*********************************************************************/
	
	private boolean[] key = new boolean[256];
	private boolean[] mouse = new boolean[MouseInfo.getNumberOfButtons()];
	
	private boolean[] keyToggle = new boolean[256];
	private boolean[] mouseToggle = new boolean[MouseInfo.getNumberOfButtons()];
	
	/*********************************************************************/
	/* Constants                                                         */
	/*********************************************************************/
	
	private final static int SECOND = 1000000000;
	
	/*********************************************************************/
	/* Constructor                                                       */
	/*********************************************************************/
	
	public SimpleGameEngine(int width, int height, String title, boolean FPSLock, int FPSCap, boolean showFPS, boolean fullscreen) throws Exception
	{
		if(width < 1 || height < 1)
			throw new IllegalArgumentException("Width and height must be greater than 0.");
		
		if(title == null)
			throw new NullPointerException("The title cannot be null.");
		
		this.width = width;
		this.height = height;
		this.title = title;
		this.FPSLock = FPSLock;
		this.FPSCap = FPSCap;
		this.showFPS = showFPS;
		this.fullscreen = fullscreen;
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(title);
		frame.setResizable(false);
		
		canvas = new Canvas();
		canvas.setSize(width, height);
		
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);		
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
		frame.setAlwaysOnTop(false);
		
		canvas.createBufferStrategy(2);
		
		frame.addKeyListener(this);
		canvas.addKeyListener(this);
		
		frame.addMouseListener(this);
		canvas.addMouseListener(this);
	
		long t0 = System.nanoTime();
		long t1 = t0;
		long start;
		
		while(running)
		{
			start = System.nanoTime();
			
			update();
			render();
			
			drawGraphics();
			
			frameCounter++;
			
			if(t1 >= t0 + 1000000000)
			{			
				t0 = t1;
				FPS = frameCounter;
				frameCounter = 0;
				
				if(showFPS)
					frame.setTitle(this.title + " - " + FPS + " fps");
			}
				
			if(this.FPSLock)
			{
				long timeLeft = t1 + SECOND / this.FPSCap - System.nanoTime() - 1000000; // stabilize the framerate
				if(timeLeft > 0)
						Thread.sleep(timeLeft / 1000000, (int)(timeLeft % 1000000));
				while(System.nanoTime() < t1 + (SECOND * 1f / this.FPSCap)); // stabilize the framerate
			}
			
			t1 = System.nanoTime();
			elapsedTime = t1 - start;
		}
		
		frame.dispose();
	}
	
	/* "throws Exception" is included to allow exception throwing methods into the methods */
	
	public void update() throws Exception {}
	public void render() throws Exception {}
	
	public final Graphics getGraphics()
	{
		Graphics g = canvas.getBufferStrategy().getDrawGraphics();
		g.clearRect(0, 0, width, height);
		
		return g;
	}
	
	private void drawGraphics()
	{
		canvas.getBufferStrategy().show();
		canvas.getBufferStrategy().getDrawGraphics().dispose();
	}
	
	public final void stop()
	{
		running = false;
	}
	
	/*********************************************************************/
	/* Getters                                                           */
	/*********************************************************************/
	
	public final int getWidth()
	{
		return width;
	}
	
	public final int getHeight()
	{
		return height;
	}
	
	public final String getTitle()
	{
		return title;
	}
	
	public final int getFPS()
	{
		return FPS;
	}
	
	public final boolean getFPSLock()
	{
		return FPSLock;
	}
	
	public final int getFPSCap()
	{
		return FPSCap;
	}
	
	public final boolean getShowFPS()
	{
		return showFPS;
	}
	
	public final boolean isFullscreen()
	{
		return fullscreen;
	}
	
	public final long getElapsedTime()
	{
		return elapsedTime;
	}
	
	public final int getMouseX()
	{
		return (int)(MouseInfo.getPointerInfo().getLocation().getX() - canvas.getLocationOnScreen().getX());
	}
	
	public final int getMouseY()
	{
		return (int)(MouseInfo.getPointerInfo().getLocation().getY() - canvas.getLocationOnScreen().getY());
	}
	
	public final boolean key(int code)
	{
		return key[code];
	}
	
	public final boolean mouse(int code)
	{
		return mouse[code];
	}
	
	/*********************************************************************/
	/* Setters                                                           */
	/*********************************************************************/
	
	public final void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		canvas.setSize(width, height);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public final void setTitle(String title)
	{
		this.title = title;
		if(!showFPS)
			frame.setTitle(title);
		else
			frame.setTitle(title + " - " + FPS + " fps");
	}
	
	public final void setFPSLock(boolean FPSLock)
	{
		this.FPSLock = FPSLock;
	}
	
	public final void setFPSCap(int FPSCap)
	{
		this.FPSCap = FPSCap;
	}
	
	public final void setShowFPS(boolean showFPS)
	{
		this.showFPS = showFPS;
	}
	
	public final void setFullscreen(boolean fullscreen)
	{
		this.fullscreen = fullscreen;
	}
	
	/*********************************************************************/
	/* Toggle                                                            */
	/*********************************************************************/
	
	public final synchronized boolean keyToggle(int code)
	{
		return toggle(code, key, keyToggle);
	}
	
	public final synchronized boolean mouseToggle(int code)
	{
		return toggle(code, mouse, mouseToggle);
	}
	
	private boolean toggle(int code, boolean[] baseArray, boolean[] toggleArray)
	{
		if(baseArray[code])
		{
			if(!toggleArray[code])
			{
				toggleArray[code] = true;
				return true;
			}
		}
		else
			toggleArray[code] = false;
		
		return false;
	}
	
	/*********************************************************************/
	/* Events                                                            */
	/*********************************************************************/

	public final void keyPressed(KeyEvent e)
	{
		key[e.getKeyCode()] = true;
	}

	public final void keyReleased(KeyEvent e)
	{
		key[e.getKeyCode()] = false;
	}
	
	public void mousePressed(MouseEvent e)
	{
		mouse[e.getButton()] = true;
	}

	public void mouseReleased(MouseEvent e)
	{
		mouse[e.getButton()] = false;
	}
	
	public final void keyTyped(KeyEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}	
}
