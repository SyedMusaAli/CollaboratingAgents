//package MARF.LRTA;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

class MyComponent
    extends JComponent
{
  Point location;
  int MyPointCount;
  int width = 4;
  int height = 4;
  public List<AgentContainer> MyPoints;
  public StatesInfo MyStatesObj;
  public InputVariables IVObj;
  public Target TargetObj;
 // public Packet PacketObj;
  public Point Target;
  //public Point Packet;
  public Graphics2D g2d;
  private int MAP_SQUAREX = 15;
  private int MAP_SQUAREY = 15;
  private int DISPLAY_WIDTH = 1000; //578
  private int DISPLAY_HEIGHT = 700; //500
  private String PTmp;
  private Point P;

  public MyComponent()
  {
    MyPointCount = 0;
    MyPoints = new ArrayList<AgentContainer>();
    Target = new Point();
    P = new Point();
  }

  public void paint(Graphics g)
  {
    MAP_SQUAREX = (int) (DISPLAY_WIDTH / IVObj.GSize.x);
    MAP_SQUAREY = (int) (DISPLAY_HEIGHT / IVObj.GSize.y);
    g2d = (Graphics2D) g;
    // Display Lines
    for (int x = 0; x < DISPLAY_WIDTH; x += MAP_SQUAREX)
    {
    	g2d.drawLine(x, 0, x, DISPLAY_HEIGHT);
    }
    for (int y = 0; y < DISPLAY_HEIGHT; y += MAP_SQUAREY)
    {
    	g2d.drawLine(0, y, DISPLAY_WIDTH, y);
    }
    /**************************************************
     ** Create Agents On GUI
     ***************************************************/
    // Draw Agents
    for (int i = 0; i < MyPointCount; i++)
    {
    	if(MyPoints.get(i).state == 0)
    	{
    		location = (Point) MyPoints.get(i).point;
    	      g2d.fill3DRect( (location.x * MAP_SQUAREX), (location.y * MAP_SQUAREY),
    	    		  MAP_SQUAREX, MAP_SQUAREY, true);
    	}
    }
    
    g2d.setColor(Color.GREEN);
    for (int i = 0; i < MyPointCount; i++)
    {
    	if(MyPoints.get(i).state == 1)
    	{
    		location = (Point) MyPoints.get(i).point;
    	      g2d.fill3DRect( (location.x * MAP_SQUAREX), (location.y * MAP_SQUAREY),
    	    		  MAP_SQUAREX, MAP_SQUAREY, true);
    	}
    }
    
    // Draw Targets
    g2d.setColor(Color.RED);
    for (int i = 0; i < TargetObj.TargetPositions.size(); i++)
    {
      location = (Point) TargetObj.TargetPositions.get(i);
      g2d.fill3DRect( (location.x * MAP_SQUAREX), (location.y * MAP_SQUAREY),
    		  MAP_SQUAREX, MAP_SQUAREY, true);
    }
    // Draw packets
/* ==================== QUAIN Code starts here ============================== */    
    g2d.setColor(Color.PINK);
	for (int i = 0; i < MyStatesObj.PacketPos.size(); i++)
    {
		location = (Point) MyStatesObj.PacketPos.get(i);
		g2d.fill3DRect( (location.x * MAP_SQUAREX), (location.y * MAP_SQUAREY),
				MAP_SQUAREX, MAP_SQUAREY, true);
    }
/* ==================== QUAIN Code ends here ============================== */
    /**************************************************
     ** Create Obstacle On GUI
     ***************************************************/
	g2d.setColor(Color.BLUE);
    Iterator ObsIterator = MyStatesObj.Obstacles.keySet().iterator();
    while (ObsIterator.hasNext())
    {
      PTmp = (String) ObsIterator.next();
      String[] s = PTmp.split(",");
      P.x = Integer.parseInt(s[0]);
      P.y = Integer.parseInt(s[1]);
      g2d.fill3DRect( (P.x * MAP_SQUAREX), (P.y * MAP_SQUAREY), MAP_SQUAREX,
    		  MAP_SQUAREY, true);
    }
    /**************************************************
     ** Draw Path Of Each Agent On GUI
     ***************************************************/
 /*   Iterator KeyIterator = MyStatesObj.MyStates.keySet().iterator();
    while (KeyIterator.hasNext())
    {
      PTmp = (String) KeyIterator.next();
      String[] s = PTmp.split(",");
      P.x = Integer.parseInt(s[0]);
      P.y = Integer.parseInt(s[1]);
      g2d.setColor(MyStatesObj.GetStateColor(P.x, P.y));
      g2d.fill3DRect( (P.x * MAP_SQUAREX), (P.y * MAP_SQUAREY), 5, 5, true);
      g2d.setColor(Color.BLACK);
      g2d.drawString(MyStatesObj.GetH(P.x, P.y) + "", (P.x * MAP_SQUAREX),
                     ( (P.y + 1) * MAP_SQUAREY));
    }

    Font f=new Font("Arial",Font.BOLD,10);
    g2d.setColor(Color.DARK_GRAY);
    g2d.drawString(MyStatesObj.Count + "", (4 * MAP_SQUAREX),
                   ( 4 * MAP_SQUAREY));
*/
  }

  public void AddPoint(int x, int y)
  {
    location = new Point();
    location.x = x;
    location.y = y;
    AgentContainer agent = new AgentContainer();
    agent.point = location;
    MyPoints.add(agent);
    MyPointCount++;
  }
}
