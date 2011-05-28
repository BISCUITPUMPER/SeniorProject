package server;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;
import shared.StatusCode;

public class Server 
{
	private int port;
	private BufferedReader in;
	private PrintWriter out;
	private Component frame;
	private Robot r;
	private Socket sock;
	public Server(int p) throws AWTException
	{
		port = p;
		in = null;
		out = null;
		frame = null;
		sock = null;
		r = new Robot();
	}
	
	public Server(int p, Component fr) throws AWTException
	{
		port = p;
		in = null;
		out = null;
		frame = fr;
		sock = null;
		r = new Robot();
	}
	
	public void start() throws IOException
	{
		ServerSocket sock = new ServerSocket(port);
		Socket client = sock.accept();
		out = new PrintWriter(client.getOutputStream());
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String cmd = "";
		System.out.println("connected...now waiting for a command");
		out.println(StatusCode.SOCK_CONNECT.toString());
		out.flush();
		while (client.isConnected())
		{
			cmd = in.readLine();
			System.err.println("Calling parseCommand(" + cmd + ")");
			StatusCode result = parseCommand(cmd);
			System.out.printf("Sending the response message %s with the status code of %d back to client\n", result, result.statusCode);
			out.println(result);
			out.flush();
			if (result.statusCode == StatusCode.SOCK_DISCONNECT.statusCode)
			{
				sock.close();
				System.out.println("Closing connection to client");
				return;
			}
		}
	}
	
	
	/**
	 * Takes a command string, processes it, and attempts to execute the command
	 * @param cmd (String) Command to be executed
	 * @return (StatusCode) Status code from command execution
	 * @throws IOException 
	 */
	private StatusCode parseCommand(String cmd) throws IOException
	{
		//The tilde (~) is used as a character that seperates portions of the command string (ie coordinates for mouse move or message body)
		String[] cmdBreak = cmd.split("~");
		if (cmdBreak[0].equalsIgnoreCase("MESG"))
		{
			//Store the contents of the string in a StringBuilder object until needed
			//Initially, put the first element of cmdBreak (message)
			StringBuilder sb = new StringBuilder(cmdBreak[1]);
			//If there is a tilde in the message, additional cmdBreak elements will be created
			for (int x = 2; x < cmdBreak.length; x++)
			{
				sb.append("~" + cmdBreak[x]);
			}
			System.err.println(sb.toString());
			//showMessageDialog waits until the user presses the button
			JOptionPane.showMessageDialog(frame, sb.toString(), "New Message!", JOptionPane.PLAIN_MESSAGE);
			return StatusCode.MESSAGE_SENT;
		}
		else if (cmdBreak[0].equalsIgnoreCase("MOVE"))
		{
			int x = Integer.parseInt(cmdBreak[1]);
			int y = Integer.parseInt(cmdBreak[2]);
			r.mouseMove(x,y);
			return StatusCode.MOUSE_MOVE;
		}
		else if (cmdBreak[0].equalsIgnoreCase("SCROLL"))
		{
			int clicks = Integer.parseInt(cmdBreak[1]);
			r.mouseWheel(clicks);
			return StatusCode.MOUSE_SCROLL;
		}
		else if (cmdBreak[0].equalsIgnoreCase("KEY"))
		{
			String key = "";
			//If the key to be pressed is a tilde, the length will be > 2
			if (cmdBreak.length>2)
			{
				for (int x = 1; x < cmdBreak.length; x++)
				{
					key += cmdBreak[x];
				}
			}
			else
			{
				key = cmdBreak[1];
			}
			return key_manager(key);
		}
		else if (cmdBreak[0].equalsIgnoreCase("MOUSE"))
		{
			String key = "";
			//If the key to be pressed is a tilde, the length will be > 2
			if (cmdBreak.length>2)
			{
				for (int x = 1; x < cmdBreak.length; x++)
				{
					key += cmdBreak[x];
				}
			}
			else
			{
				key = cmdBreak[1];
			}
			return mouse_click(key);
		}
		else if (cmdBreak[0].equalsIgnoreCase("RUN"))
		{
			Runtime r = Runtime.getRuntime();
			try
			{
				r.exec(cmdBreak[1]);	
			}
			catch (Exception e)
			{				
				return StatusCode.NO_OPERATION;
			}
			return StatusCode.PROG_EXECUTE;
			
		}
		else if (cmdBreak[0].equalsIgnoreCase("REQUEST"))
		{
			//Open an Out Object Stream
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			
			//Store data of the screen shot
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screen = new Rectangle(screenSize);
			
			//Store the screenshot
			BufferedImage screenshot = r.createScreenCapture(screen);
			
			//Write the object to the output stream.
			//If it fails, send a StatusCode.NO_OPERATION
			
			try
			{
				oos.writeObject(screenshot);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				oos.close();
				return StatusCode.NO_OPERATION;
			}
			oos.close();
			return StatusCode.SCREEN_SENT;
			
		}
		else if (cmdBreak[0].equalsIgnoreCase("DISCONNECT"))
		{
			return StatusCode.SOCK_DISCONNECT;
		}
		return StatusCode.NO_OPERATION;
	}

	/**
		Takes a string that represents a key and presses or releases it
		@param key (String) A string representing the key to be used and how it will be manipulated.  Either up or down
		@return (StatusCode) StatusCode representing the methods actions
	*/
	private StatusCode key_manager(String key)
	{
		String[] split = key.split("_");
		int k = 0;
		//If the length of the first element is 0, then underscore is being used
		if (split[0].length() == 0)
		{
			k = KeyEvent.VK_UNDERSCORE;
		}
		//This will be the case that handles single character keys (punctuation, numbers, characters)
		else if (split[0].length() == 1)
		{
			//The character to be used is a letter
			//There are two cases - Upper and Lower
			if (Character.isLetter(split[0].charAt(0)))
			{
				if (Character.isLowerCase(split[0].charAt(0)))
				{
					//The key codes for letters reflect the uppercase positions
					//If it is lower, subtract 32
					k = split[0].charAt(0) - 32;
				}
				//Character is a letter that is upper case
				else
				{
					k = split[0].charAt(0);
				}
			}
			else if (Character.isDigit(split[0].charAt(0)))
			{
				//Number constants are the same as their int val
				k = split[0].charAt(0);
			}
		}
		
		//The reason that the last element is not hardcoded is because the length of an underscore split is 3
		if (split[split.length - 1].equalsIgnoreCase("up"))
		{
			r.keyRelease(k);
			return StatusCode.KEY_RELEASE;
		}
		else
		{
			r.keyPress(k);
			return StatusCode.KEY_PRESS;
		}
	}
	
	/**
		Takes a string that represents a mouse button and presses or releases it
		@param mouse (String) A string representing the mouse to be used and how it will be manipulated.  Either up or down
		@return (StatusCode) StatusCode representing the methods actions
	*/
	private StatusCode mouse_click(String mouse)
	{
		String[] split = mouse.split("_");
		int button = 0;
		//Default is to return no operation
		if (split[0].equalsIgnoreCase("L"))
		{
			button = InputEvent.BUTTON1_MASK;
		}
		else if (split[0].equalsIgnoreCase("R"))
		{
			button = InputEvent.BUTTON3_MASK;
		}
		else if (split[0].equalsIgnoreCase("M"))
		{
			button = InputEvent.BUTTON2_MASK;
		}
		else
		{
			return StatusCode.NO_OPERATION;
		}
		
		//If its an invalid op, return no op
		//Last not hardcoded to ensure that there is no invalid data (extra _)
		if (split[split.length - 1].equalsIgnoreCase("up"))
		{
			r.mouseRelease(button);
			return StatusCode.MOUSE_RELEASE;
		}
		else
		{
			r.mousePress(button);
			return StatusCode.MOUSE_PRESS;
		}
	}

}
