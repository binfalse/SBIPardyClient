/**
 * 
 */
package de.binfalse.martin.jeopardy.client;

import java.io.IOException;

import de.binfalse.martin.jeopardy.client.net.ClientSpeaker;
import de.binfalse.martin.jeopardy.client.x.ClientWindow;


/**
 * @author Martin Scharm
 *
 */
public class Main
{
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main (String[] args) throws IOException
	{
		args = new String [] {"localhost", "1234"};
		
		/*ClientSpeaker cs = new ClientSpeaker (args[0], 1234);
		cs.answer ();*/
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
          new ClientWindow().setVisible(true);
      }
  });
	}
	
}
