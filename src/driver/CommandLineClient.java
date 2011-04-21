package driver;

import java.util.Scanner;

public class CommandLineClient
{
	public static void main(String[] args)
	{
		Scanner reader = new Scanner(System.in);
		CommandLineClient client = new CommandLineClient(reader.nextLine(), reader.nextInt());
	}
}