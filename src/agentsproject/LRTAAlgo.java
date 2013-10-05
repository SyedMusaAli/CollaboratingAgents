

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LRTAAlgo implements Search
{
  private Map H;
  public Point MyCurrentPos;
  public MyComponent MyObj;
  public Target TargetObj;
  public Point TargetPosition;
  private int MinPos;
  private CoordinatedMove CM;
  public int SolutionLength;
  /********************************************/
  /***** Using In Case Of Ties ****************/
  private int[] MinPosArr;
  private int MinPosArrCount;
  private int[] HGoalValues;

  /********************************************/
  private int MinValue;
  public int CurrentPos;
  transient protected AgentGUI myGui;
  public StatesInfo MyStateInfo;
  private Color[] StateColors;
  private Point[] NextMovements;
  public InputVariables IVObj;
  public int LC;

  public LRTAAlgo()
  {
      H = new HashMap();
    SolutionLength=0;
    MinPosArr = new int[8];
    HGoalValues = new int[8];

    MyCurrentPos = new Point();
    TargetPosition = new Point();
    StateColors = new Color[8];
    StateColors[0] = Color.YELLOW;
    StateColors[1] = Color.GREEN;
    StateColors[2] = Color.BLACK;
    StateColors[3] = Color.DARK_GRAY;
    StateColors[4] = Color.PINK;
    StateColors[5] = Color.ORANGE;
    StateColors[6] = Color.RED;
    StateColors[7] = Color.MAGENTA;
  }

  public void Lookahead()
  {
    MinPos = 0;
    /**********************************************************************/
    /******* Checking All Possible Moves **********************************/
    MinPosArrCount = 0;
    boolean Obstacles;
    MinValue = MinValueCalculation(MyCurrentPos);
    for (int i = 0; i < 8; i++)
    {
      int GoalDistance = 0;
      switch (i)
      {
        case 0:
          NextMovements[i].x = MyCurrentPos.x + 1;
          NextMovements[i].y = MyCurrentPos.y;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 0;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 0;
                MinPosArrCount++;
              }
          }
          break;
        case 1:
          NextMovements[i].x = MyCurrentPos.x + 1;
          NextMovements[i].y = MyCurrentPos.y + 1;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 1;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 1;
                MinPosArrCount++;
              }
          }
          break;
        case 2:
          NextMovements[i].x = MyCurrentPos.x;
          NextMovements[i].y = MyCurrentPos.y + 1;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 2;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 2;
                MinPosArrCount++;
              }
          }
          break;
        case 3:
          NextMovements[i].x = MyCurrentPos.x - 1;
          NextMovements[i].y = MyCurrentPos.y + 1;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 3;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 3;
                MinPosArrCount++;
              }
          }
          break;
        case 4:
          NextMovements[i].x = MyCurrentPos.x - 1;
          NextMovements[i].y = MyCurrentPos.y;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 4;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 4;
                MinPosArrCount++;
              }
          }
          break;
        case 5:
          NextMovements[i].x = MyCurrentPos.x - 1;
          NextMovements[i].y = MyCurrentPos.y - 1;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 5;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 5;
                MinPosArrCount++;
              }
          }
          break;
        case 6:
          NextMovements[i].x = MyCurrentPos.x;
          NextMovements[i].y = MyCurrentPos.y - 1;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 6;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 6;
                MinPosArrCount++;
              }
          }
          break;
        case 7:
          NextMovements[i].x = MyCurrentPos.x + 1;
          NextMovements[i].y = MyCurrentPos.y - 1;
          Obstacles = DetectObstacles(NextMovements[i]);
          if (Obstacles != true)
          {
            GoalDistance = GetGoalDistance(NextMovements[i]);
              if (MinValue >= GoalDistance)
              {
                MinPos = 7;
                MinValue = GoalDistance;
                HGoalValues[MinPos] = GoalDistance;
                MinPosArr[MinPosArrCount] = 7;
                MinPosArrCount++;
              }
          }
          break;
      }
    }
  }

  public void AgentAction()
  {
    if (MinPosArrCount > 1)
    {
      double SRand = Math.random() * MinPosArrCount;
      long Pos = (long) SRand;
      MinPos = MinPosArr[ (int) Pos];
    }

    /**************************************
     ** Call Coordination Algo
     **************************************/
    CM = new CoordinatedMove();
    CoordinationAlgo CA = new CoordinationAlgo();
    CA.CM = CM;
    CA.MyStateInfo = MyStateInfo;
    if(LC==0)
    {
      CA.UseCoordinatedMove = false;
    }
    else
    {
      CA.RunAlgorithm(MyCurrentPos.x, MyCurrentPos.y);
    }
    /**************************************
     ** Checking Whether To Use Coordinated
     ** Move Or Simple LRTA Move
     **************************************/
    if (CA.UseCoordinatedMove == false)
    {
      /**************************************
       ** Using Simple LRTA* Move
       ** Checking Whether We Have Any
       ** Possible Move
       **************************************/
      if (MinPosArrCount > 0)
      {
        UpdateHueristic(MyCurrentPos,HGoalValues[MinPos] + 1);
        MyStateInfo.AddStateColor(MyCurrentPos.x, MyCurrentPos.y,
                                  StateColors[MinPos]);
        MyCurrentPos.x = NextMovements[MinPos].x;
        MyCurrentPos.y = NextMovements[MinPos].y;
        AgentContainer agentContainer = MyObj.MyPoints.get(CurrentPos);
        agentContainer.point = MyCurrentPos;
        MyObj.MyPoints.set(CurrentPos, agentContainer);
      }
    }
    else
    {
      /**************************************
       ** Coordinated Move
       **************************************/
      MyCurrentPos = CM.NextMove;
      AgentContainer agentContainer = MyObj.MyPoints.get(CurrentPos);
      agentContainer.point = MyCurrentPos;
      MyObj.MyPoints.set(CurrentPos, agentContainer);
      
    }
    SolutionLength++;
  }

  public void UpdateHueristic(Point CP,int HueristicVal)
  {
    MyStateInfo.AddH(CP.x, CP.y,HueristicVal);
    AddH(CP.x, CP.y,HueristicVal);
  }
  public void RunLRTA()
  {
    NextMovements = new Point[8];
    for (int j = 0; j < 8; j++)
    {
      NextMovements[j] = new Point();
    }

    /**************************************
     ** Call Lookahead Function
     **************************************/
    Lookahead();
    AgentAction();
  }

  /*************************************************
   ** DetectObstacles Function Takes An Point As
   ** Input And Returns True if Obstacle Found Else
   ** Return False
   *************************************************/
  private boolean DetectObstacles(Point CP)
  {
    long Obsfd;
    Obsfd = MyStateInfo.GetObstacle(CP.x, CP.y);
    if (Obsfd == -1)
    {
      return false;
    }
    else
    {
      return true;
    }
  }

  /******************************************
   ** Calculate Goal Distance
   ******************************************/
   private int GetGoalDistance(Point CP)
   {
     int Hval,GoalDistance;
     Hval = MyStateInfo.GetH(CP.x, CP.y);
     if (Hval == 0)
     {
       GoalDistance = Math.abs(TargetPosition.x -
                               CP.x) +
           Math.abs(TargetPosition.y - CP.y);
     }
     else
     {
       GoalDistance = Hval;
     }
     return GoalDistance;
   }
   /*************************************************
    ** MinValueCalculation Function Takes An Point As
    ** Input And Calculate The Min Heuristic Value
    ** From Specifed Point To Target And Returns That
    *************************************************/
   private int MinValueCalculation(Point TmpCurrentPos)
   {
     boolean NoObstacles;
     int GDistance;
     int MValue;
     int Hval;
     Point PassPoint = new Point();
     MValue = 1000;
     for (int i = 0; i < 8; i++)
     {
       switch (i)
       {
         case 0:
           PassPoint.x = TmpCurrentPos.x + 1;
           PassPoint.y = TmpCurrentPos.y;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
         case 1:
           PassPoint.x = TmpCurrentPos.x + 1;
           PassPoint.y = TmpCurrentPos.y + 1;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
         case 2:
           PassPoint.x = TmpCurrentPos.x;
           PassPoint.y = TmpCurrentPos.y + 1;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
         case 3:
           PassPoint.x = TmpCurrentPos.x - 1;
           PassPoint.y = TmpCurrentPos.y + 1;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
         case 4:
           PassPoint.x = TmpCurrentPos.x - 1;
           PassPoint.y = TmpCurrentPos.y;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
         case 5:
           PassPoint.x = TmpCurrentPos.x - 1;
           PassPoint.y = TmpCurrentPos.y - 1;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
         case 6:
           PassPoint.x = TmpCurrentPos.x;
           PassPoint.y = TmpCurrentPos.y - 1;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
         case 7:
           PassPoint.x = TmpCurrentPos.x + 1;
           PassPoint.y = TmpCurrentPos.y - 1;
           NoObstacles = DetectObstacles(PassPoint);
           if (NoObstacles != true)
           {
             GDistance = GetGoalDistance(PassPoint);
             if (MValue >= GDistance)
             {
               MValue = GDistance;
             }
           }
           break;
       }
     }
     return MValue;
   }
   public void ClearH()
    {
        H.clear();
    }
   
   public synchronized void AddH(int Dimx, int Dimy, int HValue)
  {
    String RIndex = Dimx + "," + Dimy + "";
    String s = HValue + "";
    H.put(RIndex, s);
  }

  public int GetH(int Dimx, int Dimy)
  {
    String RIndex = Dimx + "," + Dimy + "";
    String s;
    s = (String) H.get(RIndex);
    if (s != null)
    {
      return Integer.parseInt(s);
    }
    else
    {
      return 0;
    }
  }
}
