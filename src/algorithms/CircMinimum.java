package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;

public class CircMinimum {

	public static Circle calculCercleMin(ArrayList<Point> points) {
		  
		  /*points.removeAll(points);
		  points.add(new Point(500,500));
		  points.add(new Point(600,400));
		  points.add(new Point(400,600));
		  points.add(new Point(300,350));
		  points.add(new Point(600,600));*/
		  
		  
		  //Etape 1, on recup un point dummy
		  //Random random = new Random();
		  Point dummy = points.get(0);
		  
		  //Etape 2, on trouve le point le plus eloigne de dummy
		  double distance = 0;
		  Point p = null;
		  for (Point temp : points) {
			  double d = dummy.distance(temp);
			  if (d >= distance) {
				  distance = d;
				  p = temp;
			  }
		  }
		  //System.out.println(p.toString());
		  
		  //Etape 3, on trouve le point le plus eloigne de P
		  Point q = null;
		  for (Point temp : points) {
			  double d = p.distance(temp);
			  if (d >= distance) {
				  distance = d;
				  q = temp;
			  }
		  }
//		  System.out.println(q.toString());

		  
		  Point c2 = null;
		  double cp = 0;
		  
		  // Coordonnées du centre du segment [PQ]
		  double a = (p.x + q.x) / 2;
		  double b = (p.y + q.y) / 2;
		  // C, le centre du segment [PQ]
		  Point c = new Point((int)a, (int)b);
//		  System.out.println(c.toString());
		  // Rayon du cercle de centre c, passant par P et Q (distance CP)
		  cp = Point.distance(a, b, p.x, p.y);
		  //System.out.println("cp = "+Point.distance(a, b, p.x, p.y));
		  // On parcourt la liste de points, et on enlève tout point compris dans
		  // ce cercle
		  ArrayList<Point> l = new ArrayList<>();
		  for (Point temp : points) {
			  //System.out.println((int)Point.distance(a, b, temp.x,temp.y));
			  if (Point.distance(a, b, temp.x, temp.y) > cp) {
				  l.add(temp);
			  }
		  }
		  //System.out.println(l.toString());
		  //si l est vide alors c'est bon
		  if (l.isEmpty()) 
			  // On renvoie le cercle centré en C, et de rayon CP
			  return new Circle(c, (int)cp);
		  
		  Point s = null;
		  //sinon, tant que l n'est pas vide
		  while(!l.isEmpty()) {
			  //on prend un point dans l;
			  s=l.get(0);
			  l.remove(s);
		  
		  
		  double alpha, beta;
		  // Distance du centre du cercle à S
		  double sc = Point.distance(s.x, s.y, a, b);
		  //System.out.println(sc);
		  // T est sur le cercle,
		  double st = sc + cp;
		  double sc2 = st / 2;
		  /*System.out.println("sc2 = "+sc2);
		  System.out.println("sc = "+sc);
		  */
		  double cc2 = sc - sc2;
		  alpha = sc2 / sc;
		  beta = cc2 / sc;
//		  System.out.println("alpha = "+alpha);
//		  System.out.println("a = "+a);
//		  System.out.println("beta = "+beta);
//		  System.out.println("s.x = "+s.x);
		  double a2 = alpha * a + beta * s.x;
		  double b2 = alpha * b + beta * s.y;
//		  System.out.println(a2);
//		  System.out.println(b2);
		  c2 = new Point((int)a2,(int) b2);
//		  System.out.println(c2.toString());
		  ArrayList<Point> l1 = new ArrayList<>();
		  for (Point temp : l) {
				if (Point.distance(a2, b2, temp.x, temp.y) > sc2) {
					l1.add(temp);
				}
			}
		  if (l1.isEmpty()) {
				return new Circle(c2, (int)sc2);
			} else {
				l = (ArrayList<Point>) l1.clone();
				a = a2;
				b = b2;
				cp = sc2;
			}
		  }
		  
	    return new Circle(c2,(int)cp);
	  }
}
