//package MARF.LRTA;

import jade.core.AID;
import jade.core.Agent;
import java.util.*;
import java.awt.*;


public class InitializeAgent extends Agent
{
  public RawInputs IVObj;
  jade.wrapper.AgentController a = null;
  protected void setup()
  {
    UserGUI UserObj=new UserGUI();

    while(UserObj.PutValues!=true)
    {
      doWait(100);
    }
    IVObj = UserObj.IVObj;
    jade.wrapper.AgentContainer Acontainer = (jade.wrapper.AgentContainer)
        getContainerController();
    try
    {
      Object[] args=new Object[1];
      args[0]= IVObj;

      a = Acontainer.createNewAgent("CAgent", ControllerAgent.class.getName(),args);
      a.start();
    }
    catch (Exception ex)
    {
      System.out.println("Problem creating Controller Agent");
    }

  }
}
