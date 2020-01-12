package test;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import algorithms.CircMinimum;
import algorithms.EnveloppeConvexe;
import supportGUI.Circle;
import tools.Geometry;

public class TestCirc {

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
	
	public static void main(String[] args) {
		//Test des cercles minimum ainsi que qualité
		List<Point> points=new ArrayList<>();
		final File folder = new File("resultat");
		
        List<String> result = new ArrayList<>();
        
        search(".*\\.points", folder, result);
        Collections.sort(result);
        for (String s : result) {
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
    			Circle circ = CircMinimum.calculCercleMin((ArrayList<Point>)points);

    			double conv_aire = Geometry.convexeAire(EnveloppeConvexe.enveloppeConvexeJarvis((ArrayList<Point>) points));


    			
    			double circ_aire = Geometry.circArea(circ);
    			StringBuilder res = new StringBuilder();
    			//res.append("Pour le fichier "+s+"\nAire du cercle = "+circ_aire+"\n");
    			//res.append("Aire de l'enveloppe convexe = "+conv_aire+"\n");
    			res.append((circ_aire/conv_aire-1)+"\n");//marge d'erreur du rect par rapport a convexe
    			
    			BufferedWriter writer = new BufferedWriter(new FileWriter("resultat/resultatCercle.csv",true));
    		    writer.write(res.toString());
    		     
    		    writer.close();
    		    points.clear();
    		} 
    		
    		catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 
        }
	}
}
