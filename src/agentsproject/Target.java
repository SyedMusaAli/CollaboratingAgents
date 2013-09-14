//package MARF.LRTA;

import java.util.*;
import java.awt.*;
public class Target
{
  public Point TargetPosition;
  public java.util.List TargetPositions;
  public Map TP;
  public int NoofTargets;
  public boolean Caught;
  public Target()
  {
    TargetPosition=new Point();
    TargetPositions = new ArrayList();
    TP = new HashMap();
    TargetPosition.x=0;
    TargetPosition.y=0;
    Caught=false;
    NoofTargets=1;
  }

  public synchronized void AddTP(int Dimx, int Dimy,int Reached)
  {
    String RIndex = Dimx + "," + Dimy + "";
    String s = Reached + "";
    TP.put(RIndex, s);
  }

  public int GetTP(int Dimx, int Dimy)
  {
    String RIndex = Dimx + "," + Dimy + "";
    String s;
    s = (String) TP.get(RIndex);
    if (s != null)
    {
      return Integer.parseInt(s);
    }
    else
    {
        return 1;
    }
  }
}
