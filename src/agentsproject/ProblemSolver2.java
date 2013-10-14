//package MARF.LRTA;

import agentsproject.Packet;
import jade.core.*;
import java.awt.*;
import java.util.*;

public class ProblemSolver2
    extends Agent
{
  private Point LocalTarget;
  private LRTAAlgo LA;
  private int LocalTargetPos;
  private boolean isWaiting;
  private String state;
  private HashMap<String, Double> Q;
  private Packet myPacket;
  
  protected void setup()
  {
    Point T;
    DBLogger dblogger;
    int RunNo;
    myPacket = null;
    state = "Free";
    initializeQ();
    
    
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
    isWaiting = false;
    
    /********************************************/
    /******* Calculating Start Time *************/
    Date STime = new Date();
    System.out.println("Start Time " + STime);
    /********************************************/
    MyTarget();
    
    String name = this.getAID().getName();
    name = name.substring(0, name.indexOf("@"));
    
    LA.TargetPosition = LA.MyStateInfo.ChoosePacket(LA.MyCurrentPos);
    while (LA.MyStateInfo.PacketPos.size() > 0 || myPacket != null)
    {
       ArrayList<String> list = getActions();
       String selectedAction = chooseAction(list);
       state = performAction(selectedAction);
       //update Q
       
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
  
  private ArrayList<String> getActions()
  {
      ArrayList<String> ans = new ArrayList<>();
      if(!state.equalsIgnoreCase("Free"))
      {
          ans.add("Continue");
      }
      if(state.equalsIgnoreCase("Free") || state.equalsIgnoreCase("WaitingForHelp"))
      {
          ans.add("ChoosePacket");
      }
      if(state.equalsIgnoreCase("Free") || state.equalsIgnoreCase("WaitingForHelp") || state.equalsIgnoreCase("MovingTowardsHelpCall"))
      {
          ans.add("RespondToHelpCall");
      }
      return ans;
  }
  
  private String chooseAction(ArrayList<String> list)
  {
      String ans = null;
      double max = -100;
      for(String str : list )
      {
          if(Q.get(state+":"+str) > max)
          {
              max = Q.get(state+":"+str);
              ans = str;
          }
      }
      return ans;
  }
  
  private String performAction(String action)
  {
      String newState = state;
      
      return newState;
  }
  
  private void initializeQ()
  {
      Q = new HashMap<>();
      Q.put("Free:ChoosePacket", 1.0);
      Q.put("Free:RespondToHelpCall", 2.0);
      Q.put("MovingToFreePacket:Continue", 1.0);
      Q.put("MovingToFreePacket:RespondToHelpCall", 2.0);
      Q.put("MovingToDestination:Continue", 1.0);
      Q.put("RespondingToHelp:Continue", 1.0);
      Q.put("WaitingForHelp:Continue", 2.0);
      Q.put("WaitingForHelp:ChoosePacket", 1.0);
      Q.put("WaitingForHelp:RespondToHelpCall", 1.5);
  }
}
