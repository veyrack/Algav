package tools;

import java.awt.Point;


public class Geometry {
	
	public static double distance(Point p, Point a, Point b) {
        return Math.abs(crossProduct(a,b,a,p));
    }
	
	public static double crossProduct(Point p, Point q, Point s, Point t){
        return ((q.x-p.x)*(t.y-s.y)-(q.y-p.y)*(t.x-s.x));
    }
	  
	  /*
	   * < 0 p a gauche de ab
	   * = 0 p appartient a ab
	   * > 0 p a droite de ab
	   */
	  public static double prodVec(Point a,Point b,Point p) {
		  return (b.x-a.x)*(p.y-a.y)-(b.y-a.y)*(p.x-a.x);
	  }
	  
	  public static int square(int x){
		  return x * x;
	  }
	  
	  public static int distanceSquare(Point p, Point q){
		  return square(q.x - p.x) + square(q.y - p.y);
	  }
	  
	  public static double getCos(Line l1,Line l2) {
		  double res = l1.vector.prodSca(l2.vector)/(l1.vector.norme()*l2.vector.norme());
		  return Math.acos(res);
	  }
}
