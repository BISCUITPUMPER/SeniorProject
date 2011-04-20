/*
	Provides an abstracted version of a client.  This class handles anything related 
*/
package client;

//Socket Class
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client 
{
	//Private variables
	private String host;
	private int port;
	private Socket sock;
	
	/**
	 * Creates a socket to h on port p
	 * @param h (String) Hostname or IP Address of server 
	 * @param p (int) Port number to connect on
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client(String h, int p) throws UnknownHostException, IOException
	{
		host = h;
		port = p;
		sock = new Socket(host, port);
	}
}
