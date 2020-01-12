package test;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import algorithms.CircMinimum;
import algorithms.RectMinimum;
import tools.Geometry;

public class TestTimeGlobal {
	public static void main(String[] args) {
		ArrayList<Point> points= new ArrayList<>();
		long debut;
		try {
			PrintWriter writer = new PrintWriter(new File("resultat/result_temps.csv"));
			writer.println("pts;ritter;toussaint");
			for(int cpt = 256 ; cpt<= 300000 ;cpt+=256) {
					
					points = Geometry.generateList(points,cpt);
					
					System.out.println("For cpt = "+cpt);
					//System.out.println(points.size());
					long debut_circ=System.currentTimeMillis();
					//System.out.println(debut_circ);
					CircMinimum.calculCercleMin((ArrayList<Point> )points.clone());
					//System.out.println(System.currentTimeMillis());
					writer.print(cpt+";"+(System.currentTimeMillis()-debut_circ)+";");
					//System.out.println(points.size());
					debut=System.currentTimeMillis();
					RectMinimum.rectMini((ArrayList<Point> )points.clone());
					writer.print(""+(System.currentTimeMillis()-debut)+"\n");	
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        
	}
}
