//package MARF.LRTA;

import java.awt.*;

public class CoordinationAlgo
{
  public boolean UseCoordinatedMove;
  public StatesInfo MyStateInfo;
  public CoordinatedMove CM;
  private Color[] StateColors;
  public CoordinationAlgo()
  {
    UseCoordinatedMove = false;

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

  /**************************************
  ** Coordination Algo
  **************************************/
  public void RunAlgorithm(int Dimx,int Dimy)
  {
    Color c = MyStateInfo.GetStateColor(Dimx,Dimy);
    if (c != Color.WHITE && c != null)
    {
      if (c == Color.YELLOW)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
      else if (c == Color.GREEN)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
      else if (c == Color.BLACK)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
      else if (c == Color.DARK_GRAY)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
      else if (c == Color.PINK)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
      else if (c == Color.ORANGE)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
      else if (c == Color.RED)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
      else if (c == Color.MAGENTA)
      {
        /**************************************
         ** Calculate Next Possible Move
         **************************************/
        FindNextPosition(Dimx, Dimy, c);
        /**************************************
         ** Update The Color Of State
         **************************************/
        MyStateInfo.AddStateColor(Dimx,Dimy,CM.StateColor);
        UseCoordinatedMove = true;
      }
    }
  }

  /*************************************************
  ** DetectObstacles Function Takes An Point As
  ** Input And Returns True if Obstacle Found Else
  ** Return False
  *************************************************/
 private boolean DetectObstacles(Point CP)
 {
   long Obsfd;
   Obsfd = MyStateInfo.GetObstacle(CP.x,CP.y);
   if (Obsfd == -1)
   {
     return false;
   }
   else
   {
     return true;
   }
 }

 /*************************************************
  ** MinValueCalculation Function Takes x,y Which
  ** Represent Dimension State And c The Color of
  ** That State As Input And Calculate And Calculate
  ** The Next Possible Move Of An Agent And Returns
  ** The Next Possible Move State
  *************************************************/
 private void FindNextPosition(int x, int y, Color c)
 {
   boolean FoundObstacle = false;
   for (int i = 0; i <= 1; i++)
   {
     if (c == StateColors[0])
     {
       /******************************************
       ** Current State Has Yellow Color
       *******************************************/
       CM.NextMove.x = x+1;
       CM.NextMove.y = y+1;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle == true)
       {
         c = StateColors[1];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[1];
         break;
       }
     }
     if (c == StateColors[1])
     {
       /******************************************
       ** Current State Has Green Color
       *******************************************/
       CM.NextMove.x = x;
       CM.NextMove.y = y+1;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle == true)
       {
         c = StateColors[2];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[2];
         break;
       }
     }
     if (c == StateColors[2])
     {
       /******************************************
       ** Current State Has Black Color
       *******************************************/
       CM.NextMove.x = x-1;
       CM.NextMove.y = y+1;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle == true)
       {
         c = StateColors[3];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[3];
         break;
       }
     }
     if (c == StateColors[3])
     {
       /******************************************
       ** Current State Has DARK_GRAY Color
       *******************************************/
       CM.NextMove.x = x-1;
       CM.NextMove.y = y;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle == true)
       {
         c = StateColors[4];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[4];
         break;
       }
     }
     if (c == StateColors[4])
     {
       /******************************************
       ** Current State Has PINK Color
       *******************************************/
       CM.NextMove.x = x-1;
       CM.NextMove.y = y-1;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle == true)
       {
         c = StateColors[5];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[5];
         break;
       }
     }
     if (c == StateColors[5])
     {
       /******************************************
       ** Current State Has ORANGE Color
       *******************************************/
       CM.NextMove.x = x;
       CM.NextMove.y = y-1;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle == true)
       {
         c = StateColors[6];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[6];
         break;
       }
     }
     if (c == StateColors[6])
     {
       /******************************************
       ** Current State Has RED Color
       *******************************************/
       CM.NextMove.x = x+1;
       CM.NextMove.y = y-1;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle==true)
       {
         c = StateColors[7];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[7];
         break;
       }
     }
     if (c == StateColors[7])
     {
       /******************************************
       ** Current State Has MAGENTA Color
       *******************************************/
       CM.NextMove.x = x+1;
       CM.NextMove.y = y;
       FoundObstacle = DetectObstacles(CM.NextMove);
       if (FoundObstacle == true)
       {
         c = StateColors[0];
       }
       else
       {
         /******************************************
         ** Assign New Color To Current State So
         ** Storing New Color In Global Variable gc
         *******************************************/
         CM.StateColor = StateColors[0];
         break;
       }
     }
   }
 }
}
