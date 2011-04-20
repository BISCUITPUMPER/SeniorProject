package client;

//Read from keyboard
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CommandLineClient 
{

	public static void main(String[] args) throws UnknownHostException, IOException 
	{
		//Create reader that reads from keyboard input
		Scanner reader = new Scanner(System.in);
				
		Client commandLineClient = new Client(reader.nextLine(), reader.nextInt());

	}

}
