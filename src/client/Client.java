/*
	Provides an abstracted version of a client.  This class handles anything related 
*/
package client;

//Socket Class
import java.net.Socket;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.imageio.ImageIO;

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
		
		//Specify a timeout of one minute
		sock.setSoTimeout(3600 * 1000);
		
		//Check for connection (through SOCK_CONNECT)
		String s = read();
		StatusCode r = StatusCode.valueOf(s);
		if (r.statusCode == StatusCode.SOCK_CONNECT.statusCode)
		{
			System.out.println("Connected to server!");
		}
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
	 * @return (StatusCode) representing the response from server.  Defaults to StatusCode.NO_OPERATION
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public StatusCode sendCommand(String cmd) throws IOException, ClassNotFoundException
	{
		StatusCode s = StatusCode.NO_OPERATION;
		ObjectInputStream ois = null;
		if (sock.isConnected() && cmd.length() > 0)
		{
			//Client wants a screenshot
			if (cmd.substring(0, 3).equalsIgnoreCase("REQ"))
			{
				//Now prepare the object stream
				ois = new ObjectInputStream(sock.getInputStream());
				
				//Send the regular command
			}
			out.println(cmd);
			out.flush();
			
			if (ois != null)
			{
				Scanner reader = new Scanner(System.in);
				System.out.println("What is the name of the file to save the screenshot to (w/o extension)?");
				String fileName = reader.nextLine();
				fileName += ".png";
				
				Object obj = ois.readObject();
				if (obj instanceof BufferedImage)
				{
					BufferedImage ico = (BufferedImage) obj;
					ImageIO.write(ico, "png", new File(fileName));	
				}
			}
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
			s = StatusCode.valueOf(returnStatus);
			if (s.statusCode == StatusCode.SOCK_DISCONNECT.statusCode)
			{
				sock.close();
			}
		}
		return s;
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
		String s = in.readLine();
		return s;
	}
}
