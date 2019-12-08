package tools;

public class Vector {
	public double x,y;
	
	public Vector(double x,double y) {
		this.x=x;
		this.y=y;
	}
	
	public double prodSca(Vector autre) {
		return (x * autre.x) + (y * autre.y);
	}
	
	public double norme() {
		return Math.sqrt(x*x+y*y);
	}
	
	public Vector normal() {
		return new Vector(-y,x);
	}
	
	public Vector invert() {
		return normal().normal();
	}
	public String toString() {
		return "Vec : "+x+" ; "+y;
	}
}
