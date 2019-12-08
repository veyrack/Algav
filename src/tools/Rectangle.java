package tools;

import java.awt.Point;
import java.util.ArrayList;

public class Rectangle {

	public Point a,b,c,d;
	
	public Rectangle(Point a,Point b,Point c,Point d) {
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
	}
	
	public Rectangle(Line l1,Line l2,Line l3,Line l4) {
		this(l1.intersection(l2), l2.intersection(l3), l3.intersection(l4), l4.intersection(l1));
	}
	
	public double aire() {
		return b.distance(a) * b.distance(c);
	}
	
	public boolean isInRect(ArrayList<Point> l) {
		for(Point tmp : l) {
			if((b.x - a.x) * (tmp.y - a.y) - (tmp.x - a.x) * (b.y - a.y)<=0 &&
				((c.x - b.x) * (tmp.y - b.y) - (tmp.x - b.x) * (c.y - b.y)<=0) &&
				((d.x - c.x) * (tmp.y - c.y) - (tmp.x - c.x) * (d.y - c.y)<=0) &&
				((a.x - d.x) * (tmp.y - d.y) - (tmp.x - d.x) * (a.y - d.y)<=0))
				continue;
			else return false;
				
		}
		
		return true;
	}
	
	public String toString() {
		return "Rectangle : \nPoint a : "+a.toString()+
				"\nPoint b : "+b.toString()+
				"\nPoint c : "+c.toString()+
				"\nPoint d : "+d.toString();
				
	}
}
