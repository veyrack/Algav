package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import supportGUI.Circle;

import tools.Geometry;
import tools.Line;
import tools.Rectangle;
import tools.Vector;

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
  
  public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
/*	  
	  points.removeAll(points);
	  
	  points.add(new Point(350,300));
	  points.add(new Point(350,310));
	  points.add(new Point(300,150));
	  points.add(new Point(540,360));
	  points.add(new Point(600,400));
	  points.add(new Point(260,350));
	  points.add(new Point(480,500));
	  points.add(new Point(350,650));
	  points.add(new Point(290,100));
*/
	  
	  ArrayList<Point.Double> l = new ArrayList<Point.Double>();
	  for(Point p:points) {
		  l.add(new Point.Double(p.x,p.y));
	  }
	  l=EnveloppeConvexe.pixelSort(l);
	  l=EnveloppeConvexe.graham(l);
	  points.removeAll(points);
	  for(Point.Double p:l) {
		  points.add(new Point((int)p.x,(int)p.y));
	  }
	  Rectangle rec = toussaint(points);
	  ArrayList<Point> res = new ArrayList<Point>();
	  res.add(rec.a);
	  res.add(rec.b);
	  res.add(rec.c);
	  res.add(rec.d);
	  
	  //System.out.println(rec.toString());
	  return res;
  }
  
  // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
  //   renvoie l'enveloppe convexe de la liste.
  public ArrayList<Point> enveloppeConvexeJarvis(ArrayList<Point> points){
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
	  
	  System.out.println("[Jarvis] OUT : " + enveloppe.size());
	  return enveloppe;
  }
  
  

  
  
  public ArrayList<Point> enveloppeConvexeNaif(ArrayList<Point> points){
    if (points.size()<3) {
      return null;
    }

    ArrayList<Point> enveloppe = new ArrayList<Point>();

    for(Point p : points) {
    	for(Point q:points) {
    		if (p.equals(q))
    			continue;
    		Point r = p;
    		for(Point rr:points) {
    			if(Geometry.prodVec(p,q,rr) != 0) {
    				r=rr;
    				break;
    			}
    		}
    		if (r.equals(p)) {
    			enveloppe.add(p);
    			enveloppe.add(q);
    			continue;
    		}
    		double prod = Geometry.prodVec(p, q, r);
    		boolean meme_cote=true;
    		for(Point s:points) {
    			if(prod * Geometry.prodVec(p, q, s)<0) {
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
 
  public Rectangle toussaint (ArrayList<Point> points){
	  //points=enveloppeConvexeJarvis(points);
	  //Si il n'y a pas 4 points -> impossible
	  if (points.size()<4)
		  return null;
	  for(Point tmp : points) {
		  System.out.println("ENV"+tmp.toString());
	  }
	  
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
	  //save les points de base
	  int init_i = p_i;
	  int init_j = p_j;
	  int init_k = p_k;
	  int init_l = p_l;
	  
	  //les droites associees
	  Line d_i = new Line(points.get(p_i), new Vector(0,1));
	  Line d_j = new Line(points.get(p_j), new Vector(1,0));
	  Line d_k = new Line(points.get(p_k), new Vector(0,-1));
	  Line d_l = new Line(points.get(p_l), new Vector(-1,0));
	  System.out.println(d_i.toString());
	  System.out.println(d_j.toString());
	  System.out.println(d_k.toString());
	  System.out.println(d_l.toString());
	  
	  boolean finished=false;
	  double theta_max;
	  int index_max;
	  boolean bool_i = false,bool_j = false,bool_k = false,bool_l = false;
	  
	  int cpt=0;
	  
	  Rectangle res = new Rectangle(d_i,d_j,d_k,d_l);
	  Rectangle rect=res;
	  System.out.println(points.get(p_i).toString());
	  System.out.println(points.get(p_j).toString());
	  System.out.println(points.get(p_k).toString());
	  System.out.println(points.get(p_l).toString());
	  double min_aire = res.aire();
	  
	  while(!finished) {
	  //for(int a=0;a<2;a++) {
		  //ang theta, l'angle avec la droite qui contient le point suivant
		  double theta_i = Geometry.getCos(d_i, new Line(points.get(p_i),points.get((p_i+1)%points.size())));
		  double theta_j = Geometry.getCos(d_j, new Line(points.get(p_j),points.get((p_j+1)%points.size())));
		  double theta_k = Geometry.getCos(d_k, new Line(points.get(p_k),points.get((p_k+1)%points.size())));
		  double theta_l = Geometry.getCos(d_l, new Line(points.get(p_l),points.get((p_l+1)%points.size())));
		  //recuperation du plus petit thetale donc le plus gros theta theta
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
		  System.out.println(theta_i);
		  System.out.println(theta_j);
		  System.out.println(theta_k);
		  System.out.println(theta_l);
		  System.out.println(theta_max);
		  //rotation
		  System.out.println(p_i);
		  System.out.println(p_j);
		  System.out.println(p_k);
		  System.out.println(p_l);
		  System.out.println(index_max);
		  if(index_max == p_i) {
			  
			  d_i = new Line(points.get(p_i),points.get((p_i+1)%points.size()));
			  d_j = new Line(points.get(p_j),d_i.vector.normal());
			  d_k = new Line(points.get(p_k),d_i.vector.invert());
			  d_l = new Line(points.get(p_l),d_j.vector.invert());
			  //p_i = (p_i+1)%points.size();
			  //d_i.point = points.get(p_i);
			  bool_i = true;
			  System.out.println("ROTA I");
		  } else if (index_max == p_j) {		
			  System.out.println("before");
			  System.out.println(d_i.toString());
			  System.out.println(d_j.toString());
			  System.out.println(d_k.toString());
			  System.out.println(d_l.toString());
			  d_j = new Line(points.get(p_j),points.get((p_j+1)%points.size()));
			  d_k = new Line(points.get(p_k),d_j.vector.normal());
			  d_l = new Line(points.get(p_l),d_j.vector.invert());
			  d_i = new Line(points.get(p_i),d_k.vector.invert());
			  
			  //p_j = (p_j+1)%points.size();
			  //d_j.point = points.get(p_j);
			  bool_j = true;
			  System.out.println("ROTA J");
		  } else if (index_max == p_k) {			  
			  
			  d_k = new Line(points.get(p_k),points.get((p_k+1)%points.size()));
			  d_l = new Line(points.get(p_l),d_k.vector.normal());
			  d_i = new Line(points.get(p_i),d_k.vector.invert());
			  d_j = new Line(points.get(p_j),d_l.vector.invert());
			  
			  //p_k = (p_k+1)%points.size();
			  //d_k.point = points.get(p_k);
			  bool_k = true;
			  System.out.println("ROTA K");
		  } else {
			  
			  d_l = new Line(points.get(p_l),points.get((p_l+1)%points.size()));
			  d_i = new Line(points.get(p_i),d_l.vector.normal());
			  d_j = new Line(points.get(p_j),d_l.vector.invert());
			  d_k = new Line(points.get(p_k),d_i.vector.invert());
			  
			  //p_l = (p_l+1)%points.size();
			  //d_l.point = points.get(p_l);
			  bool_l = true;
			  System.out.println("ROTA L");
		  }
		  System.out.println("After");
		  System.out.println(d_i.toString());
		  System.out.println(d_j.toString());
		  System.out.println(d_k.toString());
		  System.out.println(d_l.toString());
		  
		  rect = new Rectangle(d_i,d_j,d_k,d_l); 
		  if (rect.isInRect(points)) {
			  
		  
			  double tmp = rect.aire();
			  System.out.println("AIRE de l'ancien "+ min_aire);
			  System.out.println("AIRE du new "+ tmp);
			  if(tmp < min_aire) {
				  res = rect;
				  min_aire = tmp;
			  }
			  else finished=true;
		  }
		  else 
			  finished=true; 
		  
		  //check pour l'arret
		  
		  
		  
	    
	  }
	  
	  return res;
  }
  
 
  
  
}
