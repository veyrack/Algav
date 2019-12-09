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

import algorithms.DefaultTeam;
import tools.Geometry;
import tools.Rectangle;

public class Test {

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
		//Test des rect minimum ainsi que qualité
		List<Point> points=new ArrayList<>();
		final File folder = new File("varoumas_samples");
		
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
    			Rectangle rec = DefaultTeam.toussaint((ArrayList<Point>)points);
    			
    			double rec_aire = rec.aire();
    			StringBuilder res = new StringBuilder();
    			res.append("Pour le fichier "+s+"\nAire du rectangle = "+rec_aire+"\n");
    			double conv_aire = Geometry.convexeAire(DefaultTeam.enveloppeConvexeJarvis((ArrayList<Point>) points));
    			res.append("Aire de l'enveloppe convexe = "+conv_aire+"\n");
    			res.append("Qualité : "+(rec_aire/conv_aire-1)+"\n");//marge d'erreur du rect par rapport a convexe
    			
    			BufferedWriter writer = new BufferedWriter(new FileWriter("varoumas_samples/resultat.txt",true));
    		    writer.write(res.toString());
    		     
    		    writer.close();
    		} 
    		
    		catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} 
        }
        
        
	}

}
