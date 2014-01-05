
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBLogger
{
    
    public DBLogger()
    {
        PrintWriter out; 
        try {
            out = new PrintWriter(new FileWriter("results.csv"));
        
        out.print("GridSize,");
        out.print("NoOfAgents,");
        out.print("NoOfPackets,"); 
        out.print("NoofDestinations,"); 
        out.println("RunNo,TimeTaken");
        out.close();
        } catch (IOException ex) {
            Logger.getLogger(DBLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  public void WriteMasterStats(int NoOfAgents, String GridSize,
                         int NoOfMazes, int NoofTrials,int ObstacleRatio,int ObstacleIncrease,int NoOfPackets, int NoofDestinations,int RunNo, long TimeTaken ) throws
      Exception
  {
      
    PrintWriter out = new PrintWriter(new FileWriter("results.csv", true)); 
    out.print(GridSize+",");
    out.print(NoOfAgents+",");
    out.print(NoOfPackets+","); 
    out.print(NoofDestinations+","); 
    out.println(RunNo+","+TimeTaken);
    out.close();
  }

}
