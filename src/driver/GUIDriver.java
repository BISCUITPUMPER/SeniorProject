package driver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class GUIDriver extends JFrame implements ActionListener
{
	// CONSTRUCTOR
	
	JLabel lbl = new JLabel("Program");
	JButton btn = new JButton("START");
	//JCheckBox chk = new JCheckBox("Check");
	JTextArea text = new JTextArea();
	JPanel itemsPanel = new JPanel();
	JPanel allPanel = new JPanel();
	JRadioButton rb1 = new JRadioButton("UP", true);
	JRadioButton rb2 = new JRadioButton("DOWN", false);
	
	// ComboBox; Used instead of menu
	String[] items = {"Message", "Move", "Scroll", "Key Press", "Mouse"};
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
		itemsPanel.add(rb1);
		itemsPanel.add(rb2);

		allPanel.setLayout(new BorderLayout());
		allPanel.add(itemsPanel, BorderLayout.SOUTH);
		allPanel.add(text, BorderLayout.CENTER);
		allPanel.add(lbl, BorderLayout.NORTH);
		getContentPane().add(allPanel, BorderLayout.CENTER);
		
		// Grouping the radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(rb1);
		group.add(rb2);

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
			if (cmd == "Message")
			{
				text.setText("Make a message!");
			}
			if (cmd == "Move")
			{
				text.setText("Move the mouse!");
			}
			if (cmd == "Scroll")
			{
				text.setText("Scroll the page!");
				if (rb1.isSelected())
				{
					
				}
				else
				{
					
				}
			}
			if (cmd == "Key Press")
			{
				text.setText("Perform a key stroke!");
			}
			if (cmd == "Mouse")
			{
				text.setText("Click!");
			}
		}
		else
			text.setText("E ...?");
	}
}