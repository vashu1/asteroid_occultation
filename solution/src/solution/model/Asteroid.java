package solution.model;

public class Asteroid {
	
	int x0;
	int y0;
	int xv;
	int yv;
	double r;
	public int getX0() {
		return x0;
	}
	public void setX0(int x0) {
		this.x0 = x0;
	}
	public int getY0() {
		return y0;
	}
	public void setY0(int y) {
		this.y0 = y;
	}
	public int getXv() {
		return xv;
	}
	public void setXv(int xv) {
		this.xv = xv;
	}
	public int getYv() {
		return yv;
	}
	public void setYv(int yv) {
		this.yv = yv;
	}
	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	
	public String toString() {
		return "Asteroid(x0,y0,xv,yv,r)" + x0 + "," + y0 +"," + xv + "," + yv + "," + r;
	}
	

}
