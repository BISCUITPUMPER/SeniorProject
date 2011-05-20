/*
	Provides an abstracted version of a client.  This class handles anything related 
*/
package client;

//Socket Class
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import shared.StatusCode;

public class Client 
{
	//Private variables
	private String host;
	private int port;
	private Socket sock;
	private PrintWriter out;
	private BufferedReader in;
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
		out = new PrintWriter(sock.getOutputStream());
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
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
	 * @throws IOException
	 */
	public void sendCommand(String cmd) throws IOException
	{
		if (sock.isConnected())
		{
			out.println(cmd);
			out.flush();
		}
		System.out.println("Sent command.  Waiting for response");
		String returnStatus = read();
		System.out.println(returnStatus);
		if (returnStatus == null)
		{
			System.err.println("There was a problem reading the response.  Closing connection.");
		}
		else
		{
			//TODO: Client side response
			StatusCode s = StatusCode.valueOf(returnStatus);
			System.out.printf("Read the response message %s, with a value of %d\n", returnStatus, s.statusCode);
		}
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
	
	public String read() throws IOException
	{
		System.out.println("reading");
		String s = in.readLine();
		System.out.println("read:" + s);
		return s;
	}
}
