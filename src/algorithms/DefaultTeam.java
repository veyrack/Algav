package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

  // calculDiametre: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametre(ArrayList<Point> points) {
    if (points.size()<3) {
      return null;
    }

    Point p=points.get(0);
    Point q=points.get(1);

    double max=0;
    int i=1;
    for(Point tmp : points) {
    	for (int j=i;j<points.size();j++) {
    		double x=points.get(j).getX()-tmp.getX();
    		double y=points.get(j).getY()-tmp.getY();
    		
    		if(x*x+y*y>max) {
    			max=x*x+y*y;
    			p=tmp;
    			q=points.get(j);
    		}
    	}
    	i++;
    }
    return new Line(p,q);
  }

  // calculDiametreOptimise: ArrayList<Point> --> Line
  //   renvoie une pair de points de la liste, de distance maximum.
  public Line calculDiametreOptimise(ArrayList<Point> points) {
    if (points.size()<3) {
      return null;
    }

    Point p=points.get(1);
    Point q=points.get(2);

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return new Line(p,q);
  }

  // calculCercleMin: ArrayList<Point> --> Circle
  //   renvoie un cercle couvrant tout point de la liste, de rayon minimum.
  public Circle calculCercleMin(ArrayList<Point> points) {
    if (points.isEmpty()) {
      return null;
    }

    Point center=points.get(0);
    int radius=100;

    /*******************
     * PARTIE A ECRIRE *
     *******************/
    return new Circle(center,radius);
  }
  /*
   * < 0 p a gauche de ab
   * = 0 p appartient a ab
   * > 0 p a droite de ab
   */
  public double prodVec(Point a,Point b,Point p) {
	  return (b.x-a.x)*(p.y-a.y)-(b.y-a.y)*(p.x-a.x);
  }
  
  // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
  //   renvoie l'enveloppe convexe de la liste.
  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
	  ArrayList<Point> enveloppe = new ArrayList<>();
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
			  double prod = prodVec(p, q, sq);
			  if(prod > 0)
				  continue;
			  else if(prod == 0){
				  if(distanceSquare(p, sq) > distanceSquare(p, q))
					  q = sq;
			  }
			  else
				  q = sq;
		  }
		  p = q;
	  } while(!p.equals(first));
	  
	  System.out.println("[Jarvis] OUT : " + enveloppe.size());
	  return enveloppe;
  }
  
  
  static private int square(int x){
	  return x * x;
  }
  
  static private int distanceSquare(Point p, Point q){
	  return square(q.x - p.x) + square(q.y - p.y);
  }
  
  
  public ArrayList<Point> enveloppeConvexeNaif(ArrayList<Point> points){
    if (points.size()<3) {
      return null;
    }

    ArrayList<Point> enveloppe = new ArrayList<Point>();

    points=filterPointsAlignes(points);
    for(Point p : points) {
    	for(Point q:points) {
    		if (p.equals(q))
    			continue;
    		Point r = p;
    		for(Point rr:points) {
    			if(prodVec(p,q,rr) != 0) {
    				r=rr;
    				break;
    			}
    		}
    		if (r.equals(p)) {
    			enveloppe.add(p);
    			enveloppe.add(q);
    			continue;
    		}
    		double prod = prodVec(p, q, r);
    		boolean meme_cote=true;
    		for(Point s:points) {
    			if(prod * prodVec(p, q, s)<0) {
    				meme_cote=false;
    				break;
    			}
    		}
    		if(meme_cote) {
    			enveloppe.add(p);
    			enveloppe.add(q);
    		}
    	}
    }
    
    return enveloppe;
  }

  
  public ArrayList<Point> filterPointsAlignes(ArrayList<Point> points){
	  System.out.println("filtrerPointsAlignes");
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
	  
	  System.out.println("[filtre] len Point : " + points.size());
	  return points ;
  }
}
