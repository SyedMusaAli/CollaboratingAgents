//package MARF.LRTA;

import jade.core.Agent;
import java.util.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAgent
    extends Agent
{
  transient protected AgentGUI myGui;
  public String name;
  private int agentCnt = 0;
  Object[] args;
  Point MyCurrentPos;
  private Target TargetObj;
  private java.util.List MyAgents;
  private int DISPLAY_WIDTH = 1020;
  private int DISPLAY_HEIGHT = 740;
  private int Trails = 0;
  private int NoofMazes = 0;
  private int TotalRuns =0;
  
  InputVariables IVObj;
  RawInputs mainIV;
  protected void setup()
  {
    Object[] args = getArguments();
    mainIV = (RawInputs) args[0];
    DBLogger dblogger = new DBLogger();
    for(String agent: mainIV.AgentNo)
    {
        for(String packetNo: mainIV.PacketNo)
        {
            for(String destinationNo: mainIV.DestinationNo)
            {

            IVObj = new InputVariables(mainIV);
            IVObj.AgentNo = Integer.parseInt(agent);
            IVObj.PacketNo = Integer.parseInt(packetNo);
            IVObj.DestinationNo = Integer.parseInt(destinationNo);

              NoofMazes=0;
              int RunNo = 0;
               
              
              while (NoofMazes < IVObj.NoofTrials)
              {
                NoofMazes++;

                StatesInfo MyStateInfo = new StatesInfo();

                MyStateInfo.MakeFileEmpty();
                double RT = IVObj.ObstRatio * 0.01;
                MyStateInfo.GenerateMaze(IVObj.GSize.x, IVObj.GSize.y, RT,
                                         IVObj.AgentNo,IVObj.PacketNo,IVObj.DestinationNo);

                /****************************************************
                 ** Write Master Records In DataBase
                 ***************************************************/
                
                

                  Trails = 0;
                  MyStateInfo.MakeFileEmpty();


                    Trails++;
                    TargetObj = new Target();
                    MyAgents = new ArrayList();

                    MyComponent MyObj = new MyComponent();
                    MyStateInfo.Initialize();

                    //generate new maze
                     MyStateInfo.GenerateMaze(IVObj.GSize.x, IVObj.GSize.y, RT,
                                         IVObj.AgentNo,IVObj.PacketNo,IVObj.DestinationNo);

                    int MyAgentsLength = 0;
                    int totalWeight = MyStateInfo.getTotalWeight();
                    myGui = new AgentGUI();
                    int i;
                    TargetObj.NoofTargets = IVObj.DestinationNo;
        /*
         * 
         * Change the number of target position with destination number
         * 
         * */
                    for (i = 0; i < IVObj.DestinationNo; i++)
                    {
                      Point TP;
                      TP = (Point) MyStateInfo.MultipleTargets.get(i);
                      TargetObj.TargetPositions.add(TP);
                      TargetObj.AddTP(TP.x,TP.y,0);
                    }



                    // Initailize States With Previous Hvalues if any...
                    MyStateInfo.InitStates();

                    // Passing object of MyComponent in AgentGUI Object.
                    myGui.MyObj = MyObj;
                    myGui.TargetObj = TargetObj;

                    MyObj.MyStatesObj = MyStateInfo;
                    MyObj.IVObj = IVObj;
                    MyObj.TargetObj = TargetObj;

                    myGui.getContentPane().add(MyObj);

                    jade.wrapper.AgentController a = null;

                    for (i = 0; i < IVObj.AgentNo; i++)
                    {
                      args = new Object[10];
                      MyCurrentPos = new Point();
                      MyCurrentPos = (Point) MyStateInfo.AgentStartPos.get(i);
                      MyObj.AddPoint(MyCurrentPos.x, MyCurrentPos.y);
                      myGui.getContentPane().add(MyObj);
                      name = "Agent" + agentCnt++;
                      args[0] = MyCurrentPos;
                      args[1] = TargetObj;
                      args[2] = MyObj;
                      args[3] = myGui;
                      String s = new String();
                      s = "" + i;
                      args[4] = s;
                      args[5] = MyStateInfo;
                      args[6] = IVObj;
                      args[7] = dblogger;
                      args[8] = RunNo + "";
                      args[9] = 0 +"";
                      jade.wrapper.AgentContainer Acontainer = (jade.wrapper.
                          AgentContainer)
                          getContainerController();
                      try
                      {
                        a = Acontainer.createNewAgent(name, ProblemSolver2.class.getName(),
                                                      args);
                        MyAgents.add(a);
                        MyAgentsLength++;
                      }
                      catch (Exception ex)
                      {
                        System.out.println("Problem creating new agent");
                      }
                    }
                    myGui.setSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
                    myGui.setResizable(false);
                    myGui.show();
                    for (int j = 0; j < MyAgentsLength; j++)
                    {
                      try
                      {
                        a = (jade.wrapper.AgentController) MyAgents.get(j);
                        a.start();
                      }
                      catch (Exception ex)
                      {
                        System.out.println("Problem while starting new agent");
                      }
                    }
                    while (true)
                    {
                      System.out.println("Checking for stopping");
                      if (MyStateInfo.AgentsDone >= IVObj.AgentNo)
                      {
                        this.doWait(1000);
                        for (int j = 0; j < MyAgentsLength; j++)
                        {
                          try
                          {
                            a = (jade.wrapper.AgentController) MyAgents.get(j);
                            System.out.println("Killed agent "+a.getName());
                            a.kill();
                          }
                          catch (Exception ex)
                          {
                            System.out.println("Problem while killing agent");
                          }
                        }
                        myGui.setVisible(false);
                        break;
                      }
                      this.doWait(100);
                    }
                    try {
                    dblogger.WriteMasterStats(IVObj.AgentNo,
                                                    IVObj.GSize.x + "x" +
                                                    IVObj.GSize.y,
                                                    IVObj.NoofDifMazes,
                                                    IVObj.NoofTrials,
                                                    IVObj.ObstRatio,
                                                    IVObj.ObstRatioIncrease,
                                                    IVObj.PacketNo,
                                                    IVObj.DestinationNo, RunNo++, MyStateInfo.Count, totalWeight);
                } catch (Exception ex) {
                    Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
                  }

              
            }  
          }
    }
  }
}
