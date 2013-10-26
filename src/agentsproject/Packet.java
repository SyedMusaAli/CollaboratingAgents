
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

    public int getWeight() {
        return weight;
    }
    
    public synchronized void lift()
    {
        weight--;
    }
    
    public synchronized void drop()
    {
        weight++;
    }

    public boolean isTaken() {
        return taken;
    }

    public Point getDestination() {
        return Destination;
    }
   int weight;
   boolean taken;
   Point Destination;
   
   public Packet(Point init, Point end, int w)
   {
       Pos = new Point(init);
       Destination = new Point(end);
       weight = w;
       System.out.println("weight: "+weight);
   }
   
   public String getID()
   {
       String s = Pos.x+","+Pos.y;
       return s;
   }
   
   private double distanceBetween(Point a, Point b)
   {
       return Math.sqrt(((a.x-b.x)*(a.x-b.x))+((a.y-b.y)*(a.y-b.y)));
   }
   
   public double getDistance(Point p)
   {
       return distanceBetween(Pos, p);
   }
   
   public boolean isCloserThan(Point p, Point from)
   {
       double d1 = getDistance(from);
       double d2 = distanceBetween(p, from);
       return d1 < d2;
   }
}
