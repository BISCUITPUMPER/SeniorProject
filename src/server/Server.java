package server;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import shared.StatusCode;

public class Server 
{
	private int port;
	private BufferedReader in;
	private PrintWriter out;
	private Component frame;
	public Server(int p)
	{
		port = p;
		in = null;
		out = null;
		frame = null;
	}
	
	public Server(int p, Component fr)
	{
		port = p;
		in = null;
		out = null;
		frame = fr;
	}
	
	public void start() throws IOException
	{
		ServerSocket sock = new ServerSocket(port);
		Socket client = sock.accept();
		out = new PrintWriter(client.getOutputStream());
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		String cmd = "";
		while (client.isConnected())
		{
			cmd = in.readLine();
			StatusCode result = parseCommand(cmd);
			System.out.printf("Sending the response message %s with the status code of %d back to client", result);
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
			for (int x = 1; x < cmdBreak.length; x++)
			{
				sb.append("~" + cmdBreak[x]);
			}
			//showMessageDialog waits until the user presses the button
			JOptionPane.showMessageDialog(frame, sb.toString(), "New Message!", JOptionPane.PLAIN_MESSAGE);
			return StatusCode.MESSAGE_SENT;
		}
		return StatusCode.NO_OPERATION;
	}

}
