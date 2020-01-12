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
//	  System.out.println(q.toString());

	  
	  Point c2 = null;
	  double cp = 0;
	  
	  // Coordonnées du centre du segment [PQ]
	  double a = (p.x + q.x) / 2;
	  double b = (p.y + q.y) / 2;
	  // C, le centre du segment [PQ]
	  Point c = new Point((int)a, (int)b);
//	  System.out.println(c.toString());
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
//	  System.out.println("alpha = "+alpha);
//	  System.out.println("a = "+a);
//	  System.out.println("beta = "+beta);
//	  System.out.println("s.x = "+s.x);
	  double a2 = alpha * a + beta * s.x;
	  double b2 = alpha * b + beta * s.y;
//	  System.out.println(a2);
//	  System.out.println(b2);
	  c2 = new Point((int)a2,(int) b2);
//	  System.out.println(c2.toString());
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
  
  
  public static ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
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
	  
	  
	  points=EnveloppeConvexe.pixelSort(points);
	  points=EnveloppeConvexe.graham(points);
	  points.removeAll(points);
	  
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

  
  public static ArrayList<Point> filterPointsAlignes(ArrayList<Point> points){
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
	  
  }
 
  public static Rectangle toussaint (ArrayList<Point> points){
	  points=enveloppeConvexeJarvis(points);//on recupere l'enveloppe convexe
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
