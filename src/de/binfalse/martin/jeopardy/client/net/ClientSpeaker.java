/**
 * 
 */
package de.binfalse.martin.jeopardy.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import de.binfalse.martin.jeopardy.client.x.ClientWindow;



// TODO: Auto-generated Javadoc
/**
 * The Class ClientSpeaker.
 *
 * @author Martin Scharm
 */
public class ClientSpeaker
	extends Thread
{
	
	/** Here we'll speak. */
	private PrintWriter			sout;
	
	/** Here we'll listen. */
	private BufferedReader	sin;
	
	/** The socket to the model server. */
	private Socket					server;
	
	/** The name. */
	private String					name;
	
	
	/**
	 * Instantiates a new client speaker.
	 *
	 * @param host the host
	 * @param port the port
	 * @param name the name
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ClientSpeaker (String host, int port, String name)
		throws UnknownHostException,
			IOException
	{
		server = new Socket (host, port);
		sout = new PrintWriter (server.getOutputStream (), true);
		sin = new BufferedReader (new InputStreamReader (server.getInputStream ()));
		
		this.name = name;
		
	}
	
	
	/**
	 * Gets the player name.
	 *
	 * @return the player name
	 */
	public String getPlayerName ()
	{
		return name;
	}
	
	
	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected ()
	{
		return sout != null && sin != null && server.isConnected ();
	}
	
	/** The win. */
	private ClientWindow	win;
	
	
	/**
	 * Sets the client window.
	 *
	 * @param win the new client window
	 */
	public void setClientWindow (ClientWindow win)
	{
		this.win = win;
	}
	
	
	/**
	 * Say.
	 *
	 * @param s the s
	 */
	private void say (String s)
	{
		System.out.println ("saying: " + s);
		sout.println (s);
	}
	
	
	/**
	 * Answer.
	 *
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public boolean answer () throws IOException
	{
		if (isConnected ())
		{
			say ("I want");
		}
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run ()
	{
		if (win == null)
		{
			System.out.println ("no win");
			System.exit (1);
		}
		talk ();
		try
		{
			System.out.println ("closing connection");
			server.close ();
			System.out.println ("connection closed");
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
		System.exit (1);
	}
	
	
	/**
	 * Talk.
	 */
	private void talk ()
	{
		String inputLine;
		
		try
		{
			while ( (inputLine = sin.readLine ()) != null)
			{
				System.out.println ("read: " + inputLine);
				if (inputLine.equals ("your turn"))
					win.setActivity (ClientWindow.YOUR_TURN);
				else if (inputLine.equals ("wait"))
					win.setActivity (ClientWindow.WAIT);
				else if (inputLine.equals ("open"))
					win.setActivity (ClientWindow.OPEN);
				else if (inputLine.equals ("your name?"))
					sout.println ("my name is " + name);
				else if (inputLine.equals ("name vergeben"))
				{
					JOptionPane
						.showMessageDialog (
							null,
							"Name already in use!\nPlease start again and choose a different name.",
							"Name err", JOptionPane.ERROR_MESSAGE);
					System.exit (1);
				}
				else
					System.out.println ("don't understand");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}
	}
}
