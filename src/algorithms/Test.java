package algorithms;

import java.awt.Point;
import java.util.ArrayList;

import tools.Geometry;
import tools.Line;
import tools.Rectangle;

public class Test {

	public static void main(String[] args) {
		Point p1=new Point(10,10);
		Point p2=new Point(10,20);
		Point p3=new Point(20,20);
		Point p4=new Point(20,10);
		Rectangle rec = new Rectangle(p1, p2, p3, p4);
		
		ArrayList<Point> l = new ArrayList<>();
		l.add(new Point(20,15));
		System.out.println(rec.isInRect(l));
	}

}
