package driver;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import client.Client;

public class CommandLineClient
{
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		Client client;
		try 
		{
			client = new Client(reader.nextLine(), reader.nextInt());
		} 
		catch (Exception e)
		{
			System.err.println("Something unexpected has occured!");
			e.printStackTrace();
		}
	}
}