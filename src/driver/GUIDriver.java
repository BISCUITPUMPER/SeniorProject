package driver;

import java.awt.*;
import javax.swing.*;
 
public class GUIDriver extends JFrame
{
  JButton button = new JButton("START");
  JCheckBox checkBox = new JCheckBox("Check");
  JTextArea text = new JTextArea(); 
  JPanel bottomPanel = new JPanel();
  JPanel holdAll = new JPanel();

  public GUIDriver()
  {
    bottomPanel.setLayout(new FlowLayout());
    bottomPanel.add(myCheckBox);
    bottomPanel.add(myButton);
 
    holdAll.setLayout(new BorderLayout());
    holdAll.add(bottomPanel, BorderLayout.SOUTH);
    holdAll.add(myText, BorderLayout.CENTER);
 
    getContentPane().add(holdAll, BorderLayout.CENTER);
 
    button.addActionListener(this);
    checkBox.addActionListener(this);
 
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }
 
  /**
   * The program    * @param args the program start up parameters, not used.
   */
  public static void main(String[] args)
  {
    GUIDriver myApplication = new GUIDriver();
 
    // Specify where will it appear on the screen:
    myApplication.setLocation(10, 10);
    myApplication.setSize(300, 300);
 
    // Show it!
    myApplication.setVisible(true);
  }
 
  /**
   * Each non abstract class that implements the ActionListener
   * must have this method.
   * 
   * @param e the action event.
   */
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == button)
      text.setText("A button click");
    else if (e.getSource() == checkBox)
      text.setText("The checkbox state changed to " + 
                     checkBox.isSelected());
    else
      text.setText("E ...?");
  }
}