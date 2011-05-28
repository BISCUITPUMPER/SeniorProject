package driver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class GUIDriver extends JFrame implements ActionListener
{
	// CONSTRUCTOR
	
	JLabel lbl = new JLabel("Program");
	JButton btn = new JButton("START");
	JCheckBox chk = new JCheckBox("Check");
	JTextArea text = new JTextArea();
	JPanel itemsPanel = new JPanel();
	JPanel allPanel = new JPanel();
	JRadioButton rb1 = new JRadioButton("ON", true);
	JRadioButton rb2 = new JRadioButton("OFF", false);
	
	// Menu Materials
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("Actions");
	JMenuItem miMESG = new JMenuItem("Message");
	JMenuItem miMOVE = new JMenuItem("Move");
	JMenuItem miSCROLL = new JMenuItem("Scroll");
	JMenuItem miKEY = new JMenuItem("Key press");
	JMenuItem miMOUSE = new JMenuItem("Mouse");


	public GUIDriver()
	{
		// Label
		Font font = new Font("Dialog", Font.BOLD, 30);
		lbl.setFont(font);
		
		// Menu creation
		menuBar.add(menu);
		
		// Adding items to the frame
		itemsPanel.setLayout(new FlowLayout());
		itemsPanel.add(chk);
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
		
		// Setting up buttons to perform actions
		btn.addActionListener(this);
		chk.addActionListener(this);
		rb1.addActionListener(this);
		rb2.addActionListener(this);
		
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
			text.setText("A button click");
		else if (e.getSource() == chk)
			text.setText("The checkbox state changed to " + 
					chk.isSelected());
		else
			text.setText("E ...?");
	}
}