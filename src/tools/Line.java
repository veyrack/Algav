package tools;

import java.awt.Point;

public class Line {
	public Vector vector;
	public Point point;
	
	public Line(Point p1,Point p2) {
		if(p1.x == p2.x)
			this.vector = new Vector(0.0, 1.0);
		else 
			this.vector = new Vector( p2.x - p1.x, p2.y - p1.y );
		this.point = p1;
	}
	
	public Line(Point p,Vector v) {
		this.point=p;
		this.vector=v;
	}
	
	public double getPente() {
		return this.vector.y / this.vector.x;
	}
	
	public double getOrdo() {
		return this.point.y - getPente() * this.point.x;
	}
	
	public Point intersection(Line other) {
		int xInter, yInter;
		// Si la droite actuelle (this) est verticale
		if (vector.x == 0) {
			// Si l'autre l'est également.
			if (other.vector.x == 0) {
				// Alors pas d'intersection
				return null;
			}
			// Sinon, le point de l'autre droite à l'abscisse de notre droite.
			return new Point(point.x,(int)
					(other.getPente() * point.x + other.getOrdo()));
		}
		// Si l'autre droite est verticale.
		if (other.vector.x == 0) {
			// On renvoie le point de notre droite passant par l'abscisse de
			// l'autre droite
			return new Point(other.point.x,(int)
					(getPente() * other.point.x + getOrdo()));
		}
		// Si les deux droites sont parallèles, pas d'intersection.
		if (other.getPente() == getPente())
			return null;
		// Cas normal, on calcule les coordonnées du point normalement (système
		// de deux équations à deux inconnues).
		xInter = (int) ((other.getOrdo() - getOrdo()) / ( getPente() - other.getPente()));
		yInter = (int) (getPente() * xInter + getOrdo());
		return new Point(xInter, yInter);

	}
	
	
	public String toString() {
		return "Ligne : "+point.toString()+" ; "+vector.toString();
	}

	public Point getP() {
		return point;
	}
	
	public Point getQ() {
		return new Point((int)(point.x+vector.x),(int)(point.y+vector.y));
	}
}
