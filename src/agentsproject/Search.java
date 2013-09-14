//package MARF.LRTA;

import java.awt.*;

public interface Search
{
  public void Lookahead();
  public void AgentAction();
  public void UpdateHueristic(Point CP,int HueristicVal);
}
