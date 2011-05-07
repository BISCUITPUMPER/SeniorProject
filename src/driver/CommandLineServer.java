package driver;

import java.io.IOException;

import server.Server;

public class CommandLineServer 
{
	public static void main(String[] args) throws IOException 
	{
		Server serv = new Server(7890);
		serv.start();
	}

}
