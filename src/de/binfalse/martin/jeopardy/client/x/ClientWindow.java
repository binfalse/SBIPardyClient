/**
 * 
 */
package de.binfalse.martin.jeopardy.client.x;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.binfalse.martin.jeopardy.client.net.ClientSpeaker;



// TODO: Auto-generated Javadoc
/**
 * The Class ClientWindow.
 *
 * @author Martin Scharm
 */
public class ClientWindow
	extends JFrame
{
	
	/** The Constant OPEN. */
	public static final int			OPEN			= 0;
	
	/** The Constant YOUR_TURN. */
	public static final int			YOUR_TURN	= 1;
	
	/** The Constant WAIT. */
	public static final int			WAIT			= 2;
	
	/** The cs. */
	private final ClientSpeaker	cs;
	
	/** The active. */
	private int									active;
	
	/** The label. */
	private JLabel							label;
	
	
	/**
	 * Instantiates a new client window.
	 */
	public ClientWindow ()
	{
		pname = "unknown";
		init ();
		DialogPanel dia = new DialogPanel (this, true);
		dia.setVisible (true);
		
		cs = dia.getSpeaker ();
		if (cs == null)
			System.exit (1);
		cs.setClientWindow (this);
		cs.start ();
		active = WAIT;
		pname = cs.getPlayerName ();
		setActivity (active);
	}
	
	/** The pname. */
	private String	pname;
	
	
	/**
	 * Sets the activity.
	 *
	 * @param activity the new activity
	 */
	public void setActivity (int activity)
	{
		System.out.println (pname + " setting activ: " + activity);
		active = activity;
		switch (activity)
		{
			case YOUR_TURN:
				this.getContentPane ().setBackground (Color.GREEN);
				label.setText ("It's your turn! Ask a question!");
				break;
			case OPEN:
				this.getContentPane ().setBackground (Color.RED);
				label.setText ("You are free to question! Press any key.");
				break;
			default:
				this.getContentPane ().setBackground (new Color (0, 74, 153));
				label.setText ("Wait for the next round...");
		}
		repaint ();
	}
	
	
	/**
	 * Inits the.
	 */
	private void init ()
	{
		
		label = new JLabel ();
		label.setForeground (Color.WHITE);
		label.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);
		label.setFont (label.getFont ().deriveFont (22.f));
		setActivity (WAIT);
		setDefaultCloseOperation (javax.swing.WindowConstants.EXIT_ON_CLOSE);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager ()
			.addKeyEventDispatcher (new OwnDispatcher ());
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout (
			getContentPane ());
		getContentPane ().setLayout (layout);
		layout.setHorizontalGroup (layout.createParallelGroup (
			javax.swing.GroupLayout.Alignment.LEADING).addGroup (
			layout.createSequentialGroup ().addContainerGap (100, Short.MAX_VALUE)
				.addComponent (label).addContainerGap (100, Short.MAX_VALUE)));
		layout.setVerticalGroup (layout.createParallelGroup (
			javax.swing.GroupLayout.Alignment.LEADING).addGroup (
			layout.createSequentialGroup ().addContainerGap (300, Short.MAX_VALUE)
				.addComponent (label).addContainerGap (300, Short.MAX_VALUE)));
		
		pack ();
	}
	
	/**
	 * The Class OwnDispatcher.
	 */
	private class OwnDispatcher
		implements KeyEventDispatcher
	{
		
		/* (non-Javadoc)
		 * @see java.awt.KeyEventDispatcher#dispatchKeyEvent(java.awt.event.KeyEvent)
		 */
		@Override
		public boolean dispatchKeyEvent (KeyEvent e)
		{
			if (e.getID () == KeyEvent.KEY_PRESSED)
			{
				if (active == OPEN)
					try
					{
						System.out.println ("I want");
						cs.answer ();
					}
					catch (IOException e1)
					{
						e1.printStackTrace ();
					}
			}
			else if (e.getID () == KeyEvent.KEY_RELEASED)
			{
			}
			else if (e.getID () == KeyEvent.KEY_TYPED)
			{
			}
			return false;
		}
	}
	
}
