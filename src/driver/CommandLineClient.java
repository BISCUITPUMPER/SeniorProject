package driver;

import java.io.IOException;
import java.util.Scanner;
import client.Client;

public class CommandLineClient
{
	public static void main(String[] args) throws IOException
	{
		Scanner reader = new Scanner(System.in);
		Client client = null;
		try 
		{
			client = new Client(reader.nextLine(), reader.nextInt());
		} 
		catch (Exception e)
		{
			System.err.println("Something unexpected has occured!");
			e.printStackTrace();
		}
		client.read();
		System.out.println("sending message");
		reader.nextLine();
		client.sendCommand("MESG~HELLO");
	}
}