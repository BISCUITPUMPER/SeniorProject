package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
	private int port;
	
	public Server(int p)
	{
		port = p;
	}
	
	public void start() throws IOException
	{
		ServerSocket sock = new ServerSocket(port);
		Socket client = sock.accept();
		String cmd = "";
		while (client.isBound())
		{
			cmd = client.getInputStream().toString();
			byte result = parseCommand(cmd);
			System.out.printf("Sending %d back to client", result);
			client.getOutputStream().write(result);
		}
	}
	
	
	/**
	 * 
	 * @param cmd (String) Command to be executed
	 * @return (int) Status code from command execution
	 */
	private byte parseCommand(String cmd)
	{
		return 0x40;
	}

}

/* 
	List of Status Codes:
		0 - Invalid command
		1 - Message
		2 - Mouse Move
		3 - Mouse Click
		4 - Key Press
		5 - Key Release
*/
