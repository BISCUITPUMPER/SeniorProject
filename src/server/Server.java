package server;

import java.awt.AWTException;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Robot;

import javax.swing.JOptionPane;

import shared.StatusCode;

public class Server 
{
	private int port;
	private BufferedReader in;
	private PrintWriter out;
	private Component frame;
	private Robot r;
	public Server(int p) throws AWTException
	{
		port = p;
		in = null;
		out = null;
		frame = null;
		r = new Robot();
	}
	
	public Server(int p, Component fr) throws AWTException
	{
		port = p;
		in = null;
		out = null;
		frame = fr;
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
		out.write(StatusCode.SOCK_CONNECT.toString());
		while (client.isConnected())
		{
			cmd = in.readLine();
			System.err.println("Calling parseCommand(" + cmd + ")");
			StatusCode result = parseCommand(cmd);
			System.out.printf("Sending the response message %s with the status code of %d back to client", result, result.statusCode);
			out.write(result.statusCode);
		}
	}
	
	
	/**
	 * Takes a command string, processes it, and attempts to execute the command
	 * @param cmd (String) Command to be executed
	 * @return (StatusCode) Status code from command execution
	 */
	private StatusCode parseCommand(String cmd)
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
		else if (cmdBreak[0].equalsIgnoreCase("SCROLL")
		{
			int clicks = Integer.parseInt(cmdBreak[1]);
			r.mouseWheel(clicks);
			return StatusCode.MOUSE_SCROLL;
		}
		return StatusCode.NO_OPERATION;
	}

}
