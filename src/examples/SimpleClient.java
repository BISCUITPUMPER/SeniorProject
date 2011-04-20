package examples;

//Error handling
import java.io.IOException;
//Socket Class
import java.net.Socket;
//Error Handling
import java.net.UnknownHostException;
//Read from keyboard
import java.util.Scanner;

public class SimpleClient
{
	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		//Create reader so that it can read from keyboard
		Scanner reader = new Scanner(System.in);
		//Connect to the host on port 8888
		Socket sock = new Socket(reader.nextLine(),8888);
		//Ensure the connection isn't killed
		sock.setKeepAlive(true);
		sock.setSoTimeout(Integer.MAX_VALUE);
		
		//Until disconnected, send keyboard input to server
		while (sock.isConnected())
		{
			//Get the command
			String cmd = (reader.nextLine());
			//Send the command
			sock.getOutputStream().write(cmd.getBytes());
		}
	}
}

