package driver;

import java.awt.AWTException;
import java.io.IOException;

import server.Server;

public class CommandLineServer 
{
	public static void main(String[] args) throws IOException, AWTException 
	{
		Server serv = new Server(7890);
		serv.start();
		System.out.println("Server has stopped listening");
	}

}
