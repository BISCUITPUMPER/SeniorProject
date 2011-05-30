package driver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import client.Client;
import server.Server;
 
public class GUIDriver extends JFrame implements ActionListener
{
	// CONSTRUCTOR
	
	JLabel lbl = new JLabel("Program");
	JButton btn = new JButton("START");
	JButton dscnct = new JButton("DISCONNECT");
	//JCheckBox chk = new JCheckBox("Check");
	JTextArea text = new JTextArea();
	JPanel itemsPanel = new JPanel();
	JPanel allPanel = new JPanel();
	JRadioButton rbU = new JRadioButton("UP", true);
	JRadioButton rbD = new JRadioButton("DOWN", false);
	
	// ComboBox; Used instead of menu
	String[] items = {"Message", "Move", "Scroll", "Key Press", "Mouse", "Run"};
	JComboBox cb = new JComboBox(items);
	
	// Menu Materials (UNUSED)
	/*
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Actions");
	JMenuItem miMESG = new JMenuItem("Message");
	JMenuItem miMOVE = new JMenuItem("Move");
	JMenuItem miSCROLL = new JMenuItem("Scroll");
	JMenuItem miKEY = new JMenuItem("Key press");
	JMenuItem miMOUSE = new JMenuItem("Mouse");
	*/
	public GUIDriver()
	{
		// Label
		Font font = new Font("Dialog", Font.BOLD, 30);
		lbl.setFont(font);
		lbl.setHorizontalAlignment(JLabel.CENTER);
		
		
		// Menu creation (Not Used)
		// menuBar.add(menu);
		
		// ComboBox
		cb.setSelectedIndex(0);
		
		// Adding items to the frame
		itemsPanel.setLayout(new FlowLayout());
		//itemsPanel.add(chk);
		itemsPanel.add(cb);
		itemsPanel.add(btn);
		itemsPanel.add(rbU);
		itemsPanel.add(rbD);
		itemsPanel.add(dscnct);

		allPanel.setLayout(new BorderLayout());
		allPanel.add(itemsPanel, BorderLayout.SOUTH);
		allPanel.add(text, BorderLayout.CENTER);
		allPanel.add(lbl, BorderLayout.NORTH);
		getContentPane().add(allPanel, BorderLayout.CENTER);
		
		// Grouping the radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(rbU);
		group.add(rbD);

		// Setting up buttons to perform actions via ActionListener
		btn.addActionListener(this);
		//chk.addActionListener(this);
		//rb1.addActionListener(this);
		//rb2.addActionListener(this);
		cb.addActionListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args)
	{
		// Create the frame
		GUIDriver program = new GUIDriver();

		program.setLocation(200, 200);
		program.setSize(400, 300);

		program.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btn)
		{
			String cmd = (String)cb.getSelectedItem();
			
			// Message
			if (cmd == "Message")
			{
				text.setText("Make a message!");
				
				//Create Input Dialog box for string input
				String s = (String)JOptionPane.showInputDialog(null, 
						"Enter your message...", "Message Dialog", 1);

				//If a string was returned, say so.
				if ((s != null) && (s.length() > 0)) 
				{
					// Creation of full string for Server.parseCommand
					String str = "MESG~" + s;
				    Server.parseCommand(str);
				    return;
				}
				else
					//If you're here, the return value was null/empty.
					text.setText("There was no message!");
			}
			// Mouse Move
			if (cmd == "Move")
			{
				text.setText("Move the mouse!");
				String s = (String)JOptionPane.showInputDialog(null, 
						"Enter mouse coordinates, separated by a comma...", "Mouse Move Dialog", 1);
				String delims = "[ ,]+";
				String[] tokens = s.split(delims);
				if ((s != null) && (s.length() > 0)) 
				{
					String str = "MOVE~" + tokens[0] + "~" + tokens[1];
				    Server.parseCommand(str);
				    return;
				}
				else
					text.setText("Invalid coordinates!");
			}
			// Mouse Scroll
			if (cmd == "Scroll")
			{
				text.setText("Scroll the page!");
				String s = (String)JOptionPane.showInputDialog(null, 
						"Enter an amount to scroll...", "Scroll Dialog", 1);
				if ((s != null) && (s.length() > 0)) 
				{
					if (rbU.isSelected())
					{
						// Up
						String str = "SCROLL~" + s;
						Server.parseCommand(str);
					    return;
					}
					else
					{
						// Down (Negative connotation)
						String str = "SCROLL~-" + s;
						Server.parseCommand(str);
					    return;
					}
				    
				}
				else
					text.setText("No amount to scroll!");
			}
			// Key Press
			if (cmd == "Key Press")
			{
				text.setText("Perform a key stroke!");
				String s = (String)JOptionPane.showInputDialog(null, 
						"Enter a key...", "Key Press Dialog", 1);

				if ((s != null) && (s.length() > 0)) 
				{
					if (rbU.isSelected())
					{
						// Up
						String str = "KEY~" + s + "_UP";
						Server.parseCommand(str);
					    return;
					}
					else
					{
						// Down
						String str = "KEY~" + s + "_DOWN";
						Server.parseCommand(str);
					    return;
					}
				}
				else
					text.setText("No key pressed!");
			}
			// Mouse Click
			if (cmd == "Mouse")
			{
				text.setText("Click!");
				String s = (String)JOptionPane.showInputDialog(null, 
						"Click the mouse...", "Mouse Click Dialog", 1);

				if ((s != null) && (s.length() > 0)) 
				{
					String str = "MOUSE~" + s;
				    Server.parseCommand(str);
				    return;
				}
				else
					text.setText("No Click?");
			}
			// Run Program
			if (cmd == "Run")
			{
				text.setText("Run a Program!");
				String s = (String)JOptionPane.showInputDialog(null, 
						"Enter program to run...", "Run Program Dialog", 1);

				if ((s != null) && (s.length() > 0)) 
				{
					String str = "RUN~" + s;
				    Server.parseCommand(str);
				    return;
				}
				else
					text.setText("Invalid coordinates!");
			}
		}
		
	// Disconnect with option dialog box
		else if (e.getSource() == dscnct)
		{
			int n = JOptionPane.showConfirmDialog(null, "Do you really want to disconnect?",
				    "Disconnect?", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION)
				Server.parseCommand("DISCONNECT");
			else 
				return;
		}
		else
			text.setText("...");
	}
}