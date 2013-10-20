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
  public String name;
  
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
    name = this.getAID().getName();
    name = name.substring(0, name.indexOf("@"));
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
        System.out.println(name+" At: "+LA.MyCurrentPos.x+","+LA.MyCurrentPos.y);
       ArrayList<String> list = getActions();
       String selectedAction = chooseAction(list);
       state = performAction(selectedAction);
       //update Q
       
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
      if((state.equalsIgnoreCase("Free") || state.equalsIgnoreCase("WaitingForHelp") || state.equalsIgnoreCase("MovingToFreePacket")) && LA.MyStateInfo.checkHelpCall())
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
          System.out.println(name+" "+state+":"+str+" "+Q.get(state+":"+str));
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
      System.out.println(name+" "+state+":"+action);
      if(action.equalsIgnoreCase("ChoosePacket"))
      {
          System.out.println(name+" Choosing new packet");
          LA.TargetPosition = LA.MyStateInfo.ChoosePacket(LA.MyCurrentPos);
          LA.ClearH(); 
          newState = "MovingToFreePacket";
      }
      
      if(action.equalsIgnoreCase("RespondToHelpCall"))
      {
          Point help = LA.MyStateInfo.getHelpCall();
          if(help != null)
          {
              System.out.println(name+" Responding to call");
              LA.TargetPosition = help;
              LA.ClearH();
          }
          newState = "RespondingToHelpCall";
      }
      
      if(state.equalsIgnoreCase("WaitingForHelp"))
      {
          if(myPacket.getWeight() == 0)
            {
                 //pick it up
                LA.MyStateInfo.removePacket(LA.TargetPosition);
                System.out.println(name+" Picked up packet for "+myPacket.getDestination().x+","+myPacket.getDestination().y+")");
                LA.TargetPosition = myPacket.getDestination();
                LA.ClearH();
                AgentContainer ac = LA.MyObj.MyPoints.get(LA.CurrentPos);
                ac.state = 1;
                LA.MyObj.MyPoints.set(LA.CurrentPos, ac);
                newState = "MovingToDestination";
            }
      }
      
      if(action.equalsIgnoreCase("Continue") && !state.equalsIgnoreCase("WaitingForHelp"))
      {
          if(state.equalsIgnoreCase("MovingToFreePacket"))
          {
            if(LA.MyStateInfo.getPacket(LA.TargetPosition) == null)
            {
                  System.out.println(name+" Choosing new packet");
                  LA.TargetPosition = LA.MyStateInfo.ChoosePacket(LA.MyCurrentPos);
                  LA.ClearH();
                  return state;
            }
          }
          
            if (LA.MyCurrentPos.x == LA.TargetPosition.x && LA.MyCurrentPos.y == LA.TargetPosition.y)
            {
              System.out.println(name+" Reached at ("+LA.TargetPosition.x+","+LA.TargetPosition.y+")");



              if(myPacket == null)
              {
                  myPacket = LA.MyStateInfo.getPacket(LA.TargetPosition);

                  if(myPacket == null)
                  {
                     return "Free"; 
                  }

                  //Try to pick up
                  myPacket.lift();

                  //Check if still too heavy
                  if(myPacket.getWeight() > 0)
                  {
                      System.out.println(name+" Calling for help");
                      LA.MyStateInfo.callForHelp(LA.MyCurrentPos, myPacket.getWeight() );
                      AgentContainer ac = LA.MyObj.MyPoints.get(LA.CurrentPos);
                      ac.state = 0;
                      LA.MyObj.MyPoints.set(LA.CurrentPos, ac);
                      isWaiting = true;
                      return "WaitingForHelp";
                  }
                  else //if light enough
                  {
                      //pick it up
                      LA.MyStateInfo.removePacket(LA.TargetPosition);
                      LA.MyStateInfo.cancelHelpCall(LA.MyCurrentPos);
                      System.out.println(name+" Picked up packet for "+myPacket.getDestination().x+","+myPacket.getDestination().y+")");
                      LA.TargetPosition = myPacket.getDestination();
                      LA.ClearH();
                      AgentContainer ac = LA.MyObj.MyPoints.get(LA.CurrentPos);
                      ac.state = 1;
                      LA.MyObj.MyPoints.set(LA.CurrentPos, ac);
                      return "MovingToDestination";
                  }
              }
              else
              {
                  System.out.println(name+" Delivered");
                  myPacket = null;
                  LA.TargetPosition = LA.MyStateInfo.ChoosePacket(LA.MyCurrentPos);
                  LA.ClearH();
                  AgentContainer ac = LA.MyObj.MyPoints.get(LA.CurrentPos);
                  ac.state = 0;
                  LA.MyObj.MyPoints.set(LA.CurrentPos, ac);
                  return "Free";
              }


            }
          
          LA.RunLRTA();
      }
      
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
      Q.put("RespondingToHelpCall:Continue", 1.0);
      Q.put("WaitingForHelp:Continue", 2.0);
      Q.put("WaitingForHelp:ChoosePacket", 1.0);
      Q.put("WaitingForHelp:RespondToHelpCall", 1.5);
  }
}
