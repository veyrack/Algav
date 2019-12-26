package tools;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;


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
	  
	  public static double convexeAire(ArrayList<Point> points) {
		  int i = 0;
		  int d,g=d=0;
		  if(points.size()<3)return -1;
		  do {
			  g += points.get(i).x*points.get((i+1)%points.size()).y;
			  d += points.get((i+1)%points.size()).x*points.get(i).y;
			  i = (i+1)%points.size();
		  } while(i!=0);
		  return 0.5*(g-d);
		  
	  }
	  
	  public static double circArea(Circle c) {
		  return Math.PI*c.getRadius()*c.getRadius();
	  }
}
