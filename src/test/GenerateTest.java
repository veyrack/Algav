package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateTest {
	/*
	 * Genere un base de test de 1664 fichier dont les points vont de 256 a 256*1664
	 */
	public static void generateFile(int size) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("my_samples/"+size+".points",true));
		
		StringBuilder res = new StringBuilder();
		for(int i = 0; i<size;i++) {
			int rand = 100 + (int)(Math.random() * size);
			int rand2 = 100 + (int)(Math.random() * size);
			res.append(rand+" "+rand2+"\n");
		}
	    writer.write(res.toString());
	     
	    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int i=256;
		while(i<=425984) {
			generateFile(i);
			i+=256;
		}
	}
}
