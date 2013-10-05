//package MARF.LRTA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UserGUI extends JFrame implements ActionListener
{
      private JTextField AgentNo;
      private JTextField VisualDepth;
      private JTextField GSize;
      private JTextField DifMazes;
      private JTextField NoofTrials;
      private JTextField ObstRatio;
      private JTextField ObstRatioIncrease;
      private JTextField TotalRuns;
/* ==================== QUAIN Code starts here ============================ */
      private JTextField PacketNo;
      private JTextField DestinationNo;
/* ==================== QUAIN Code ends here ============================== */
    //  private JTextField TargetNo;
      private JButton Syn = new JButton("Next");
      public InputVariables IVObj;
      public boolean PutValues;
     

    public UserGUI()
    {
        super("User Input");
        PutValues=false;
       JPanel displayPanel = new JPanel(new BorderLayout());

       displayPanel.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("Input "),
                      BorderFactory.createEmptyBorder(5,5,5,5)));
       AgentNo=new JTextField("1");
       GSize=new JTextField("50x50");
       VisualDepth=new JTextField("0-0");
       DifMazes=new JTextField("1");
       NoofTrials=new JTextField("1");
       ObstRatio=new JTextField("20");
       ObstRatioIncrease=new JTextField("1");
       TotalRuns= new JTextField("1");
       PacketNo=new JTextField("1");
       DestinationNo=new JTextField("1");
       //TargetNo=new JTextField("1");
       displayPanel.setLayout(new GridLayout(11,2));

       displayPanel.add(new JLabel("No Of Agents: *"));
       displayPanel.add(AgentNo);
       displayPanel.add(new JLabel("Grid Size NxN:"));
       displayPanel.add(GSize);
       displayPanel.add(new JLabel("No. Of Mazes:"));
       displayPanel.add(DifMazes);
       displayPanel.add(new JLabel("Visual Depth Range:"));
       displayPanel.add(VisualDepth);
       displayPanel.add(new JLabel("No Of Trials:"));
       displayPanel.add(NoofTrials);
       displayPanel.add(new JLabel("Obstacls Ratio:"));
       displayPanel.add(ObstRatio);
       displayPanel.add(new JLabel("Obstacls Increment:"));
       displayPanel.add(ObstRatioIncrease);
       displayPanel.add(new JLabel("Total Runs:"));
       displayPanel.add(TotalRuns);
/* ==================== QUAIN Code starts here ============================ */       
       displayPanel.add(new JLabel("No Of Packets: *"));
       displayPanel.add(PacketNo);
       displayPanel.add(new JLabel("No Of Destination: *"));
       displayPanel.add(DestinationNo);
/* ==================== QUAIN Code ends here ============================== */
       Syn.addActionListener(this);

       displayPanel.add(new JLabel(""));
       displayPanel.add(Syn);

        //Create the main panel to contain the two sub panels.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //Add the select and display panels to the main panel.
        mainPanel.add(displayPanel);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setSize( 270, 360 );
        show();
    }
      //event handler method
    public void actionPerformed(ActionEvent ent)
    {
        if(ent.getSource()==Syn)
        {
          String s;
          IVObj = new InputVariables();
          IVObj.AgentNo= Integer.parseInt(AgentNo.getText());
          s=VisualDepth.getText();
          String[] VD=s.split("-");
          IVObj.ViusalDepthStart= Integer.parseInt(VD[0]);
          IVObj.ViusalDepthStart= Integer.parseInt(VD[1]);
          s= GSize.getText();
          String[] Dimemsions=s.split("x");
          IVObj.GSize.x=Integer.parseInt(Dimemsions[0]);
          IVObj.GSize.y=Integer.parseInt(Dimemsions[1]);

          IVObj.NoofTrials=Integer.parseInt(NoofTrials.getText());

          IVObj.NoofDifMazes= Integer.parseInt(DifMazes.getText());
          IVObj.ObstRatio= Integer.parseInt(ObstRatio.getText());
          IVObj.ObstRatioIncrease=Integer.parseInt(ObstRatioIncrease.getText());
          IVObj.TotalRuns=Integer.parseInt(TotalRuns.getText());
/* ==================== QUAIN Code starts here ============================ */ 
          IVObj.PacketNo= Integer.parseInt(PacketNo.getText());
          IVObj.DestinationNo= Integer.parseInt(DestinationNo.getText());
/* ==================== QUAIN Code ends here ============================== */
          s=VisualDepth.getText();
          PutValues=true;
          this.setVisible(false);
        }
    }
}
