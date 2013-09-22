
package agentsproject;

import java.awt.Point;

/**
 * @author Musa Ali
 */
public class Packet {
   Point Pos;

    public Point getPos() {
        return Pos;
    }

    public boolean isHeavy() {
        return Heavy;
    }

    public boolean isTaken() {
        return taken;
    }

    public Point getDestination() {
        return Destination;
    }
   boolean Heavy;
   boolean taken;
   Point Destination;
   
   public Packet(Point init, Point end, boolean H)
   {
       Pos = new Point(init);
       Destination = new Point(end);
       Heavy = H;
   }
   
   public String getID()
   {
       String s = Pos.x+","+Pos.y;
       return s;
   }
}
