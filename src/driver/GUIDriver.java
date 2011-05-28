package driver;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class GUIDriver extends JFrame implements ActionListener
{
	JLabel lbl = new JLabel("Program");
	JButton btn = new JButton("START");
	JCheckBox chk = new JCheckBox("Check");
	JTextArea text = new JTextArea();
	JPanel itemsPanel = new JPanel();
	JPanel allPanel = new JPanel();
	JRadioButton rb1 = new JRadioButton("ON", true);
	JRadioButton rb2 = new JRadioButton("OFF", false);
	
	public GUIDriver()
	{
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
 
		btn.addActionListener(this);
		chk.addActionListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
 
	/**
	 * The program    
	 * @param args the program start up parameters, not used.
	 */
	public static void main(String[] args)
	{
		GUIDriver program = new GUIDriver();

		program.setLocation(200, 200);
		program.setSize(400, 300);

		program.setVisible(true);
	}

	/**
	 * Each non abstract class that implements the ActionListener
	 * must have this method.
	 * 
	 * @param e the action event.
	 */
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