//package MARF.LRTA;

import agentsproject.Packet;
import jade.core.*;
import java.awt.*;
import java.util.*;

public class ProblemSolver
    extends Agent
{
  private Point LocalTarget;
  private LRTAAlgo LA;
  private int LocalTargetPos;
  
  Packet myPacket;
  
  protected void setup()
  {
    Point T;
    DBLogger dblogger;
    int RunNo;
    myPacket = null;
    
    LA = new LRTAAlgo();
    /********************************************/
    /***** Getting Passed Arguments *************/
    Object[] args = getArguments();
    T = (Point) args[0];

    LA.MyCurrentPos.x = T.x;
    LA.MyCurrentPos.y = T.y;

    LA.TargetObj = (Target) args[1];
    LA.MyObj = (MyComponent) args[2];
    LA.myGui = (AgentGUI) args[3];
    String s = (String) args[4];
    LA.MyStateInfo = (StatesInfo) args[5];
    LA.CurrentPos = Integer.parseInt(s);
    LA.IVObj = (InputVariables) args[6];
    dblogger = (DBLogger) args[7];
    RunNo = Integer.parseInt( (String) args[8]);
    LA.LC = Integer.parseInt( (String) args[9]);

    /********************************************/
    /******* Calculating Start Time *************/
    Date STime = new Date();
    System.out.println("Start Time " + STime);
    /********************************************/
    MyTarget();
    
    LA.TargetPosition = LA.MyStateInfo.ChoosePacket(LA.MyCurrentPos);
    while (LA.MyStateInfo.PacketPos.size() > 0 || myPacket != null)
    {
        System.out.println("At: "+LA.MyCurrentPos.x+","+LA.MyCurrentPos.y);
        System.out.println("Goal: "+LA.TargetPosition.x+","+LA.TargetPosition.y);
        System.out.println("Hval: "+LA.MyStateInfo.GetH(LA.MyCurrentPos.x, LA.MyCurrentPos.y));
        
        if(LA.MyStateInfo.getPacket(LA.TargetPosition) == null && myPacket== null)
        {
            LA.TargetPosition = LA.MyStateInfo.ChoosePacket(LA.MyCurrentPos);
            LA.MyStateInfo.ClearH();
        }
        
      /**********************************************************************/
      /******* Checking Whether Current State Is Goal State or Not **********/
      if (LA.MyCurrentPos.x == LocalTarget.x && LA.MyCurrentPos.y == LocalTarget.y)
      {
        LA.TargetObj.AddTP(LocalTarget.x,LocalTarget.y,1);
        LA.MyStateInfo.Initialize();
        MyTarget();
        LA.TargetPosition = LocalTarget;
        /**********************************************************************/
        /******* If Yes Writes States In File And Calculate End Time **********/
        if (LocalTarget.x == 0 && LocalTarget.y == 0)
        {
          LA.TargetObj.Caught = true;
          LA.MyStateInfo.WriteStates();
          Date ETime = new Date();
          System.out.println("End Time " + ETime);
          try
          {
            long TimeTaken = ETime.getTime() - STime.getTime();
//          int TT = (int) (TimeTaken / 1000);
            String Algo;
            if (LA.LC == 0)
            {
              Algo = "L";
            }
            else
            {
              Algo = "C";
            }
            dblogger.WriteDetailStats(RunNo, LA.IVObj.ObstRatio, TimeTaken,
                                      LA.SolutionLength, Algo);
          }
          catch (Exception Ex)
          {

          }
          break;
        }
      }
      else if (LA.MyCurrentPos.x == LA.TargetPosition.x && LA.MyCurrentPos.y == LA.TargetPosition.y)
      {
      	System.out.println("Reached at ("+LA.TargetPosition.x+","+LA.TargetPosition.y+")");
     	
        
        if(myPacket == null)
        {
            myPacket = LA.MyStateInfo.getPacket(LA.TargetPosition);
            LA.MyStateInfo.removePacket(LA.TargetPosition);
            System.out.println("Picked up packet");
            LA.TargetPosition = myPacket.getDestination();
            LA.MyStateInfo.ClearH();
            AgentContainer ac = LA.MyObj.MyPoints.get(LA.CurrentPos);
          	ac.state = 1;
          	
          	LA.MyObj.MyPoints.set(LA.CurrentPos, ac);
        }
        else
        {
            System.out.println("Delivered");
            myPacket = null;
            LA.TargetPosition = LA.MyStateInfo.ChoosePacket(LA.MyCurrentPos);
            LA.MyStateInfo.ClearH();
            AgentContainer ac = LA.MyObj.MyPoints.get(LA.CurrentPos);
          	ac.state = 0;
          	
          	LA.MyObj.MyPoints.set(LA.CurrentPos, ac);
        }
        
      	AgentContainer ac = LA.MyObj.MyPoints.get(LA.CurrentPos);
      	ac.state = 1;
      	
      	LA.MyObj.MyPoints.set(LA.CurrentPos, ac);
      }

      if (LA.TargetObj.Caught == true)
      {
          System.out.println("Done");
        break;
      }
      if(LA.TargetObj.GetTP(LocalTarget.x,LocalTarget.y)==1)
      {
          System.out.println("yahan");
        MyTarget();
        LA.TargetPosition = LocalTarget;
      }
      /********************************************/
      /******* Calling LRTA* Algorithm ************/
      LA.RunLRTA();
      /********************************************/
      try
      {
        LA.myGui.RePaintWindow();
        doWait(200);
      }
      catch (Exception ex)
      {

      }
    }
  }

  private void MyTarget()
  {
      System.out.println("in MyTarget()");
    int MinDistance = 999;
    int Distance;
    boolean CaughtTargets = true;
    LocalTargetPos = 0;
    Point LT;
    for (int i = 0; i < LA.TargetObj.TargetPositions.size(); i++)
    {
      LT = (Point) LA.TargetObj.TargetPositions.get(i);
      if(LA.TargetObj.GetTP(LT.x,LT.y)==0)
      {
        CaughtTargets = false;
        
        //TODO: make a better Distance Function
        Distance = Math.abs(LT.x - LA.MyCurrentPos.x) +
            Math.abs(LT.y - LA.MyCurrentPos.y);
        if (MinDistance > Distance)
        {
          LocalTargetPos = i;
          MinDistance = Distance;
        }
      }
    }
    System.out.println("Agent "+getLocalName()+"Index"+LocalTargetPos);
    LocalTarget = new Point();
    if(!CaughtTargets)
    {
      LocalTarget = (Point) LA.TargetObj.TargetPositions.get(LocalTargetPos);
    }
    else
    {
      LocalTarget.x = 0;
      LocalTarget.y = 0;
    }
  }
}
