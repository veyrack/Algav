package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import tools.Geometry;
import tools.Line;
import tools.Rectangle;
import tools.Vector;

public class RectMinimum {
	
	/**
	 * Prend une liste de points et renvoi une liste composé des points d'un rectangle couvrant minimum
	 * @param points
	 * @return les points du rectangle
	 */
	public static ArrayList<Point> rectMini(ArrayList<Point> points){
		//Premiere etape pour obtenir l'enveloppe convexe
		points=EnveloppeConvexe.pixelSort(points);
		points=EnveloppeConvexe.graham(points);
		
		//utilisations de l'algo de toussaint
		Rectangle rec = toussaint(points);
		
		ArrayList<Point> res = new ArrayList<Point>();
		res.add(rec.a);
		res.add(rec.b);
		res.add(rec.c);
		res.add(rec.d);
		//System.out.println(rec.toString());
		return res;
	}

	/**
	 * Applique l'algo de toussaint a la liste des points 
	 * @param points
	 * @return le rectangle minimum
	 */
	public static Rectangle toussaint (ArrayList<Point> points){
		  //Si il n'y a pas 4 points -> impossible
		  if (points.size()<4)
			  return null;
		  
		  
		  // p_i; p_j; p_k; p_l -> abs min; ord min; abs max; ord max
		  int p_i = 0, p_j = 0, p_k = 0, p_l = 0;
	    
		  for (int i = 1; i < points.size(); i++) {
	      
		      if (points.get(i).x < points.get(p_i).x) {
		        p_i = i;
		      }
		      if (points.get(i).y < points.get(p_l).y) {
		        p_l = i;
		      }
		      if (points.get(i).x > points.get(p_k).x) {
		        p_k = i;
		      }
		      if (points.get(i).y > points.get(p_j).y) {
		        p_j = i;
		      }
		  }
		  
		  //les droites associees
		  Line d_i = new Line(points.get(p_i), new Vector(0,1));
		  Line d_j = new Line(points.get(p_j), new Vector(1,0));
		  Line d_k = new Line(points.get(p_k), new Vector(0,-1));
		  Line d_l = new Line(points.get(p_l), new Vector(-1,0));
		  
		  boolean finished=false;
		  double theta_max;
		  int index_max;
		  
		  Rectangle res = new Rectangle(d_i,d_j,d_k,d_l);
		  Rectangle rect=res;

		  double min_aire = res.aire();
		  
		  while(!finished) {
			  
			  //theta, l'angle avec la droite qui contient le point suivant
			  double theta_i = Geometry.getCos(d_i, new Line(points.get(p_i),points.get((p_i+1)%points.size())));
			  double theta_j = Geometry.getCos(d_j, new Line(points.get(p_j),points.get((p_j+1)%points.size())));
			  double theta_k = Geometry.getCos(d_k, new Line(points.get(p_k),points.get((p_k+1)%points.size())));
			  double theta_l = Geometry.getCos(d_l, new Line(points.get(p_l),points.get((p_l+1)%points.size())));
			  
			  //recuperation du plus petit angle
			  if(theta_i<theta_j) {
				  theta_max = theta_i;
				  index_max = p_i;
			  }
			  else {
				  theta_max = theta_j;
				  index_max = p_j;
			  }
			  if(theta_k<theta_max) {
				  theta_max = theta_k;
				  index_max = p_k;
			  }
			  
			  if(theta_l<theta_max) {
				  theta_max = theta_l;
				  index_max = p_l;
			  }
			  
			  if(index_max == p_i) {
				  d_i = new Line(points.get(p_i),points.get((p_i+1)%points.size()));
				  d_j = new Line(points.get(p_j),d_i.vector.normal());
				  d_k = new Line(points.get(p_k),d_i.vector.invert());
				  d_l = new Line(points.get(p_l),d_j.vector.invert());
			  } else if (index_max == p_j) {		
				  d_j = new Line(points.get(p_j),points.get((p_j+1)%points.size()));
				  d_k = new Line(points.get(p_k),d_j.vector.normal());
				  d_l = new Line(points.get(p_l),d_j.vector.invert());
				  d_i = new Line(points.get(p_i),d_k.vector.invert());
			  } else if (index_max == p_k) {			  
				  d_k = new Line(points.get(p_k),points.get((p_k+1)%points.size()));
				  d_l = new Line(points.get(p_l),d_k.vector.normal());
				  d_i = new Line(points.get(p_i),d_k.vector.invert());
				  d_j = new Line(points.get(p_j),d_l.vector.invert());
			  } else {
				  d_l = new Line(points.get(p_l),points.get((p_l+1)%points.size()));
				  d_i = new Line(points.get(p_i),d_l.vector.normal());
				  d_j = new Line(points.get(p_j),d_l.vector.invert());
				  d_k = new Line(points.get(p_k),d_i.vector.invert());
			  }
			  
			  rect = new Rectangle(d_i,d_j,d_k,d_l); 

			  //check pour l'arret
			  if (rect.isInRect(points)) {
				  double tmp = rect.aire();
				  //System.out.println("AIRE de l'ancien "+ min_aire);
				  //System.out.println("AIRE du new "+ tmp);
				  if(tmp < min_aire) {
					  res = rect;
					  min_aire = tmp;
				  }
				  else finished=true;
			  }
			  else 
				  finished=true; 
		  }
		  return res;
	  }
}
