package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import tools.Geometry;

public class EnveloppeConvexe {

	
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
	
	private final static int XMAX = 500000;

	public static ArrayList<Point.Double> graham(ArrayList<Point.Double> points) {
		// Copie du nuage de points, pour préserver la liste originale
		ArrayList<Point.Double> enveloppe = (ArrayList<Point.Double>) points.clone();
		// Parcours de la liste.
		for (int i = 0; i < enveloppe.size(); i++) {
			// Trois points du nuage de points.
			Point.Double p = enveloppe.get(i);
			Point.Double q = enveloppe.get((i + 1) % enveloppe.size());
			Point.Double r = enveloppe.get((i + 2) % enveloppe.size());
			// Si l'angle formé par les trois points (angle des vecteurs
			// (PQ,QR)) ne tourne pas dans le bon sens
			if (!clockwise(p, q, r)) {
				// Alors Q n'appartient pas à l'enveloppe convexe, on l'enlève
				// de la liste.
				enveloppe.remove((i + 1) % enveloppe.size());
				// Si possible, on recule dans la liste, car on a enlevé un
				// point.
				if (i != 0) {
					i--;
				}
				// En plus, il faut reculer encore une fois pour "contrer" le
				// i++
				i--;

			}
		}
		// On renvoie la liste, où tous les points qui n'appartiennent pas à
		// l'enveloppe ont été enlevés.
		return enveloppe;
	}

	/**
	 * Vérifie si l'angle formé par les vecteurs PQ et QR est dans le sens
	 * horaire.
	 * 
	 * @param p
	 *            Premier point
	 * @param q
	 *            Deuxième point ("pointe" de l'angle)
	 * @param r
	 *            Troisième point
	 * @return Vrai si l'angle formé par les vecteurs PQ et QR est dans le sens
	 *         horaire, Faux sinon
	 */
	public static boolean clockwise(Point.Double p, Point.Double q,
			Point.Double r) {
		double pqX = q.getX() - p.getX();
		double pqY = q.getY() - p.getY();
		double qrX = r.getX() - p.getX();
		double qrY = r.getY() - p.getY();
		double prodV = crossProduct(pqX, pqY, qrX, qrY);
		if (prodV <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Renvoie le produit vectoriel des vecteurs A(x1,y1) et B(x2,y2)
	 * 
	 * @param x1
	 *            Abscisse du premier vecteur
	 * @param y1
	 *            Ordonnée du premier vecteur
	 * @param x2
	 *            Abscisse du deuxième vecteur
	 * @param y2
	 *            Ordonnée du deuxième vecteur
	 * @return Le produit vectoriel des vecteurs A(x1,y1) et B(x2,y2)
	 */
	public static double crossProduct(double x1, double y1, double x2, double y2) {
		return x1 * y2 - x2 * y1;
	}

	/**
	 * Méthode de précalcul au Scan de Graham. Pour chaque abscisse entière, on
	 * garde uniquement les points d'ordonnées maximum et minimum (qui peuvent
	 * être le même point, si le point est seul sur son abscisse)
	 * 
	 * @param set
	 *            La liste de points à traiter
	 * @return La liste de points traitée.
	 */
	public static ArrayList<Point.Double> pixelSort(ArrayList<Point.Double> set) {
		// Tableaux contenant une case par abscisse entière.
		// Tableau des points d'ordonnée maximum
		Point.Double[] tabMax = new Point.Double[XMAX];
		// Tableau des points d'ordonnée minimum
		Point.Double[] tabMin = new Point.Double[XMAX];
		// On parcourt le nuage de points.
		for (Point.Double p : set) {
			// Si on n'a pas encore stocké de point pour cette abscisse, ou si
			// le point stocké à une ordonnée inférieure
			if (tabMax[(int) p.getX()] == null
					|| tabMax[(int) p.getX()].getY() < p.getY()) {
				// On met à jour le tableau des ordonnées maximum
				tabMax[(int) p.getX()] = p;
			}
			// Si on n'a pas encore stocké de point pour cette abscisse, ou si
			// le point stocké à une ordonnée supérieure
			if (tabMin[(int) p.getX()] == null
					|| tabMin[(int) p.getX()].getY() > p.getY()) {
				// On met à jour le tableau des ordonnées minimum
				tabMin[(int) p.getX()] = p;
			}

		}
		// Liste que l'on va renvoyer
		ArrayList<Point.Double> resultat = new ArrayList<>();
		// On parcourt le tableau des ordonnées maximum
		for (Point.Double p : tabMax) {
			// Si la case du tableau n'est pas vide
			if (p != null) {
				// On ajoute le point à la liste
				resultat.add(p);
			}
		}
		int first;
		// On cherche le premier indice contenant quelque chose dans tabMin (en
		// partant de la fin)
		for (first = tabMin.length - 1; tabMin[first] == null; first--)
			;
		// Si le premier point est le même que le dernier point ajouté par
		// tabMax, on avance encore, car on ne veut pas de doublons
		if (tabMin[first].equals(resultat.get(resultat.size() - 1)))
			first--;
		// On parcourt le tableau des ordonnées minimum à l'envers.
		for (int i = first; i >= 0; --i) {
			// Si la case du tableau n'est pas vide
			if (null != tabMin[i]) {
				// On ajoute le point à la liste
				resultat.add(tabMin[i]);
			}
		}
		// Si le dernier point ajouté est le même point que le dernier
		if (resultat.get(resultat.size() - 1).equals(resultat.get(0)))
			// On supprime le premier point (doublon)
			resultat.remove(0);
		
		return resultat;

	}

}
