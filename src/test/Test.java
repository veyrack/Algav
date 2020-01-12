package test;

import java.awt.Point;
import java.util.ArrayList;

import algorithms.CircMinimum;
import algorithms.RectMinimum;
import tools.Geometry;

public class Test {


	public static void main(final String[] args) {
		for (int i = 0; i < args.length; ++i) {
			if (args[i].charAt(0) == '-') {
				if (args[i + 1].charAt(0) == '-') {
					System.err.println("Option " + args[i] + " expects an argument but received none");
					return;
				}
				switch (args[i]) {
				case "-nbPoints": {
					try {
						
						ArrayList<Point> list = new ArrayList<>();
						Geometry.generateList(list, Integer.parseInt(args[i + 1]));
						
						System.out.println(CircMinimum.calculCercleMin(list).toString());
						System.out.println(RectMinimum.rectMini(list).toString());
					}
					catch (Exception e2) {
						System.err.println("Invalid argument for option " + args[i] + ": Integer type expected");
						return;
					}

					++i;
					continue;
				}
				default:
					break;
				}
				System.err.println("Unknown option " + args[i]);
				return;
			}
		}
	}
}
