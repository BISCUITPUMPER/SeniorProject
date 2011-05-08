/*
	Provides an abstracted version of a client.  This class handles anything related 
*/
package client;

//Socket Class
import java.net.Socket;
import java.io.IOException;
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
	
	/**
	 * Close the current connection to a client
	 * @throws IOException 
	 */
	public void closeConnection() throws IOException
	{
		sock.close();
	}
	
	/**
	 * 
	 * @param cmd (String) Command to send to server
	 * @return (boolean) Whether or not the command was sent successfully.  Possible errors include if client isn't connected or if the server responds with an error message
	 * @throws IOException
	 */
	public boolean sendCommand(String cmd) throws IOException
	{
		//Assume that the command fails
		boolean retVal = false;
		if (sock.isConnected())
		{
			sock.getOutputStream().write(cmd.getBytes());
			retVal = sock.getInputStream().read() < 0;
		}
		return retVal;
	}
	/**
	 * Returns the hostname/IP address of the remote host
	 * @return (String) hostname/IP address of the remote host
	 */
	public String getHost()
	{
		return host;
	}
	
	/**
	 * Gets the status of the connection
	 * @return (boolean) True if connected, false if not
	 */
	public boolean isConnected()
	{
		return sock.isConnected();
	}
}
