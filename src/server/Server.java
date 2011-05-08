package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import shared.StatusCodes;

public class Server 
{
	private int port;
	private BufferedReader in;
	private PrintWriter out;
	public Server(int p)
	{
		port = p;
		in = null;
		out = null;
	}
	
	public void start() throws IOException
	{
		ServerSocket sock = new ServerSocket(port);
		Socket client = sock.accept();
		String cmd = "";
		while (client.isBound())
		{
			cmd = in.readLine();
			int result = parseCommand(cmd);
			System.out.printf("Sending %d back to client", result);
			out.write(result);
		}
	}
	
	
	/**
	 * Takes a command string, processes it, and attempts to execute the command
	 * @param cmd (String) Command to be executed
	 * @return (int) Status code from command execution
	 */
	private int parseCommand(String cmd)
	{
		return StatusCodes.NO_OPERATION.statusCode;
	}

}
