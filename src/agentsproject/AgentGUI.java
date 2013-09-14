//package MARF.LRTA;

import java.awt.*;
import javax.swing.*;

public class AgentGUI extends JFrame
{
  Point location;
  public MyComponent MyObj;
  public Target TargetObj;
  int x,y;
  private static final int MAP_SQUARE = 15;
  public AgentGUI()
  {
    super("LRTA* With Coordination");
    getContentPane().setBackground(Color.white);
  }

  public void RePaintWindow()
  {
    this.repaint();
  }
}
