//package MARF.LRTA;

import java.sql.*;

public class DBLogger
{
  public int WriteMasterStats(int NoOfAgents, String GridSize,
                         int NoOfMazes, int NoofTrials,int ObstacleRatio,int ObstacleIncrease,int NoOfPackets, int NoofDestinations ) throws
      Exception
  {
    String dbUrl = "jdbc:odbc:inmiclrta";
    String user = "";
    String password = "";
    Connection c = null;
    try
    {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      c = DriverManager.getConnection(dbUrl, user, password);
      Statement s = c.createStatement();
      String strUpd = "INSERT INTO Trails (NoOfAgents, GridSize,NoOfMazes, NoOfTrials,ObstacleRatio,ObstacleIncrease, NoOfPackets, NoofDestinations)" +
                      " VALUES(" + NoOfAgents + ",'" + GridSize +
                      "'," + NoOfMazes + "," + NoofTrials + "," + ObstacleRatio +"," + ObstacleIncrease + "," + NoOfPackets + "," + NoofDestinations + ")";
      s.executeUpdate(strUpd);
      String selectRunNo = "Select Max(RunNo) From Trails";
      ResultSet result = s.executeQuery(selectRunNo);
      while (result.next())
      {
         int runNo = result.getInt(1);
         return runNo;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      c.close();
    }
    return 1;
  }

  public void WriteDetailStats(int RunNo,int ObstacleRatio,long TimeTaken,int SolutionLength,String LC) throws Exception
  {
    String dbUrl = "jdbc:odbc:inmiclrta";
    String user = "";
    String password = "";
    Connection c = null;
    try
    {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
      c = DriverManager.getConnection(dbUrl, user, password);
      Statement s = c.createStatement();

      String strUpd = "INSERT INTO TrialDetails (RunNo, ObstacleRatio,TimeTaken,SolutionLength,Algo)" +
                      " VALUES(" + RunNo + "," + ObstacleRatio + "," + TimeTaken + "," + SolutionLength + ",'" + LC + "')";
      s.executeUpdate(strUpd);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      c.close();
    }
  }
}