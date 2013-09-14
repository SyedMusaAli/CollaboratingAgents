//package MARF.LRTA;

import jade.core.Agent;
import java.util.*;
import java.awt.*;

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
  protected void setup()
  {
    Object[] args = getArguments();
    IVObj = (InputVariables) args[0];

    while (IVObj.ObstRatio<=55)
    {
      NoofMazes=0;
      while (NoofMazes < IVObj.NoofDifMazes)
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
        DBLogger dblogger = new DBLogger();
        int RunNo = 0;
        try
        {
          RunNo = dblogger.WriteMasterStats(IVObj.AgentNo,
                                            IVObj.GSize.x + "x" +
                                            IVObj.GSize.y,
                                            IVObj.NoofDifMazes,
                                            IVObj.NoofTrials,
                                            IVObj.ObstRatio,
                                            IVObj.ObstRatioIncrease,
                                            IVObj.PacketNo,
                                            IVObj.DestinationNo);
        }
        catch (Exception Ex)
        {
          RunNo = 0;
        }
        int LC;
        for (LC = 0; LC <= 1; LC++)
        {
          Trails = 0;
          MyStateInfo.MakeFileEmpty();

          while (Trails < IVObj.NoofTrials)
          {
            Trails++;
            TargetObj = new Target();
            MyAgents = new ArrayList();

            MyComponent MyObj = new MyComponent();
            MyStateInfo.Initialize();

            int MyAgentsLength = 0;
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
              args[9] = LC +"";
              jade.wrapper.AgentContainer Acontainer = (jade.wrapper.
                  AgentContainer)
                  getContainerController();
              try
              {
                a = Acontainer.createNewAgent(name, ProblemSolver.class.getName(),
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
              if (TargetObj.Caught == true)
              {
                this.doWait(1000);
                for (int j = 0; j < MyAgentsLength; j++)
                {
                  try
                  {
                    a = (jade.wrapper.AgentController) MyAgents.get(j);
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
          }
        }
        MyStateInfo.Count++;
      }
      IVObj.ObstRatio += IVObj.ObstRatioIncrease;
    }
  }
}
