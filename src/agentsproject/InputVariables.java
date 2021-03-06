//package MARF.LRTA;

import java.awt.*;

public class InputVariables
{
  public int AgentNo;
  public int NoofDifMazes;
  public Point GSize;
  public int ViusalDepthStart;
  public int ViusalDepthEnd;
  public int NoofTrials;
  public int ObstRatio;
  public int ObstRatioIncrease;
  public int TotalRuns;
/* ==================== QUAIN Code starts here ============================== */
  public int PacketNo;
  public int DestinationNo;
/* ==================== QUAIN Code ends here ============================== */
 // public int TargetNo;
  public InputVariables()
  {
    GSize = new Point();
  }
  
  public InputVariables(RawInputs IV)
  {
    GSize = IV.GSize;
    NoofDifMazes = IV.NoofDifMazes;
    ViusalDepthStart = IV.ViusalDepthStart;
    ViusalDepthEnd = IV.ViusalDepthEnd;
    NoofTrials = IV.NoofTrials;
    ObstRatio = IV.ObstRatio;
    ObstRatioIncrease = IV.ObstRatioIncrease;
    TotalRuns = IV.TotalRuns;
  }
}
