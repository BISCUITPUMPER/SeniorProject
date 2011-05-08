package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import shared.StatusCode;

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
			StatusCode result = parseCommand(cmd);
			System.out.printf("Sending the response message %s with the status code of %d back to client", result);
			out.write(result.statusCode);
		}
	}
	
	
	/**
	 * Takes a command string, processes it, and attempts to execute the command
	 * @param cmd (String) Command to be executed
	 * @return (int) Status code from command execution
	 */
	private StatusCode parseCommand(String cmd)
	{
		return StatusCode.NO_OPERATION;
	}

}
