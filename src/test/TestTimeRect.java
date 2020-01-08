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

import algorithms.RectMinimum;

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
	
	public static void main(String[] args) {
		//Test des rect minimum ainsi que qualité
		List<Point> points=new ArrayList<>();
		final File folder = new File("my_samples");
		
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
        
        
	}

}
