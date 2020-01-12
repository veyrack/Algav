package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import tools.Geometry;
import tools.Line;
import tools.Rectangle;
import tools.Vector;

public class RectMinimum {
	
	public static ArrayList<Point> rectMini(ArrayList<Point> points){
	  
			  points=EnveloppeConvexe.pixelSort(points);
			  System.out.println("computing");
			  points=EnveloppeConvexe.graham(points);
			  Rectangle rec = toussaint(points);
			  ArrayList<Point> res = new ArrayList<Point>();
			  res.add(rec.a);
			  res.add(rec.b);
			  res.add(rec.c);
			  res.add(rec.d);
			  //System.out.println(rec.toString());
			  return res;
		  }
	
	/*public static ArrayList<Point> filterPointsAlignes(ArrayList<Point> points){
		  //System.out.println("filtrerPointsAlignes");
		  ArrayList<Point> tmp = new ArrayList<Point>();
		  
		  for(int i  = 0; i < points.size(); i++){
			  Point p = points.get(i);
			  tmp.clear();
			  for(int j  = 0; j < points.size(); j++){
				  Point q = points.get(j);
				  if(p.getX() == q.getX()) {
					  tmp.add(q);
				  }
			  }
			  if(tmp.size()<=2)
				  continue;
			  Point maxY=tmp.get(0), minY=tmp.get(0);
			  for(int j  = 0; j < tmp.size(); j++) {
				  Point t = tmp.get(j);
				  if(t.getY() > maxY.getY()) {
					  maxY = t;
				  }
				  if(t.getY() < minY.getY()) {
					  minY = t;
				  }
			  }
			  for(int j  = 0; j < tmp.size(); j++) {
				  Point t = tmp.get(j);
				  if(t.y <maxY.y && t.y>minY.y) {
					  points.remove(t);
				  }
			  }
		  }
		  	 
		  for(int i  = 0; i < points.size(); i++){
			  Point p = points.get(i);
			  tmp.clear();
			  for(int j  = 0; j < points.size(); j++){
				  Point q = points.get(j);
				  if(p.getY() == q.getY()) {
					  tmp.add(q);
				  }
			  }
			  if(tmp.size()<=2)
				  continue;
			  Point maxX=tmp.get(0), minX=tmp.get(0);
			  for(int j = 0; j < tmp.size(); j++) {
				  Point t = tmp.get(j);
				  if(t.getX() > maxX.getX()) {
					  maxX = t;
				  }
				  if(t.getY() < minX.getY()) {
					  minX = t;
				  }
			  }
			  for(int j  = 0; j < tmp.size(); j++) {
				  Point t = tmp.get(j);
				  if(t.x < maxX.x && t.x > minX.x) {
					  points.remove(t);
				  }
			  }
		  }
		  
		  //System.out.println("[filtre] len Point : " + points.size());
		  return points ;
		  
	  }*/
	/*
	// enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
	  //   renvoie l'enveloppe convexe de la liste.
	  public static ArrayList<Point> enveloppeConvexeJarvis(ArrayList<Point> points){
		  ArrayList<Point> enveloppe = new ArrayList<>();
		  points=filterPointsAlignes(points);
		  Point p=points.get(0);
		  for(Point tmp:points) {
			  if (tmp.x<p.x)
				  p=tmp;
		  }
		  
		  Point first = p;
		  
		  do{
			  enveloppe.add(p);
			  Point q = null;
			  for(Point sq : points){
				  if(!p.equals(sq))
				  {
					  q = sq;
					  break;
				  }
			  }
			  if(q == null)
				  return enveloppe;
			  for(Point sq : points){
				  double prod = Geometry.prodVec(p, q, sq);
				  if(prod > 0)
					  continue;
				  else if(prod == 0){
					  if(Geometry.distanceSquare(p, sq) > Geometry.distanceSquare(p, q))
						  q = sq;
				  }
				  else
					  q = sq;
			  }
			  p = q;
		  } while(!p.equals(first));
		  
		  //System.out.println("[Jarvis] OUT : " + enveloppe.size());
		  return enveloppe;
	  }*/

	public static Rectangle toussaint (ArrayList<Point> points){
		  //points=enveloppeConvexeJarvis(points);//on recupere l'enveloppe convexe
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
		  /*System.out.println(d_i.toString());
		  System.out.println(d_j.toString());
		  System.out.println(d_k.toString());
		  System.out.println(d_l.toString());*/
		  
		  boolean finished=false;
		  double theta_max;
		  int index_max;
		  
		  Rectangle res = new Rectangle(d_i,d_j,d_k,d_l);
		  Rectangle rect=res;
		  /*
		  System.out.println(points.get(p_i).toString());
		  System.out.println(points.get(p_j).toString());
		  System.out.println(points.get(p_k).toString());
		  System.out.println(points.get(p_l).toString());*/
		  double min_aire = res.aire();
		  
		  while(!finished) {
		  //for(int a=0;a<2;a++) {
			  
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
			  /*System.out.println(theta_i);
			  System.out.println(theta_j);
			  System.out.println(theta_k);
			  System.out.println(theta_l);
			  System.out.println(theta_max);
			  //rotation
			  System.out.println(p_i);
			  System.out.println(p_j);
			  System.out.println(p_k);
			  System.out.println(p_l);
			  System.out.println(index_max);*/
			  if(index_max == p_i) {
				  
				  d_i = new Line(points.get(p_i),points.get((p_i+1)%points.size()));
				  d_j = new Line(points.get(p_j),d_i.vector.normal());
				  d_k = new Line(points.get(p_k),d_i.vector.invert());
				  d_l = new Line(points.get(p_l),d_j.vector.invert());
				  //System.out.println("ROTA I");
			  } else if (index_max == p_j) {		
				  d_j = new Line(points.get(p_j),points.get((p_j+1)%points.size()));
				  d_k = new Line(points.get(p_k),d_j.vector.normal());
				  d_l = new Line(points.get(p_l),d_j.vector.invert());
				  d_i = new Line(points.get(p_i),d_k.vector.invert());
				  
				  //System.out.println("ROTA J");
			  } else if (index_max == p_k) {			  
				  
				  d_k = new Line(points.get(p_k),points.get((p_k+1)%points.size()));
				  d_l = new Line(points.get(p_l),d_k.vector.normal());
				  d_i = new Line(points.get(p_i),d_k.vector.invert());
				  d_j = new Line(points.get(p_j),d_l.vector.invert());
				  
				  //System.out.println("ROTA K");
			  } else {
				  
				  d_l = new Line(points.get(p_l),points.get((p_l+1)%points.size()));
				  d_i = new Line(points.get(p_i),d_l.vector.normal());
				  d_j = new Line(points.get(p_j),d_l.vector.invert());
				  d_k = new Line(points.get(p_k),d_i.vector.invert());
				  
				  //System.out.println("ROTA L");
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
