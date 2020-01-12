package test;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import algorithms.CircMinimum;
import algorithms.RectMinimum;
import supportGUI.Circle;
import tools.Geometry;
import tools.Rectangle;

public class TestTimeRect {

	public static void search(final String pattern, final File folder, List<String> result) {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f, result);
            }
            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    result.add(f.getAbsolutePath());
                }
            }

        }
    }
	/*
	 * Lis dans un fichier
	 */
	/*
	public static void main(String[] args) {
		//Test des rect minimum ainsi que qualité
		List<Point> points=new ArrayList<>();
		final File folder = new File("/media/3520096/2DDE-C4DA/my_samples");
		
        List<String> result = new ArrayList<>();
        
        search(".*\\.points", folder, result);
        Collections.sort(result);
        for (String s : result) {
        	System.out.println("For the file "+s);
        	File file = new File(s); 
    		BufferedReader br;
    		try {
    			br = new BufferedReader(new FileReader(file));
    			String st;
    			List<String> l= new ArrayList<>();
    			while ((st = br.readLine()) != null) {
    				l = Arrays.asList(st.split(" "));
    				points.add(new Point(Integer.parseInt(l.get(0)),Integer.parseInt(l.get(1))));
    				
    			}
    			long before = System.currentTimeMillis();
    			RectMinimum.rectMini((ArrayList<Point>)points);
    			long after = System.currentTimeMillis();
    			points.clear();
    			System.gc();
    			StringBuilder res = new StringBuilder();
    			res.append("Pour le fichier "+s+"\n"+(after-before)+"\n");
    			BufferedWriter writer = new BufferedWriter(new FileWriter("my_samples/resultatTimeRect.txt",true));
    		    writer.write(res.toString());
    		     
    		    writer.close();
    		} 
    		
    		catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 
        }
        
        
	}*/
	
	/*
	 * Genere les points directement
	 */
	public static void main(String[] args) {
		ArrayList<Point> points= new ArrayList<>();
		long debut;
		try {
			PrintWriter writer = new PrintWriter(new File("my_samples/result_temps.csv"));
			writer.println("toussaint;ritter");
			for(int cpt = 150000 ; cpt<= 155000 ;cpt+=256) {
					
					points = Geometry.generateList(points,cpt);
					
					System.out.println("For cpt = "+cpt);
					System.out.println(points.size());
					long debut_circ=System.currentTimeMillis();
					//System.out.println(debut_circ);
					CircMinimum.calculCercleMin((ArrayList<Point> )points.clone());
					//System.out.println(System.currentTimeMillis());
					writer.print(cpt+";"+(System.currentTimeMillis()-debut_circ)+";");
					System.out.println(points.size());
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
