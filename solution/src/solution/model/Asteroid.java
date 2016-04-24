package solution.model;

public class Asteroid {
	
	double x0;
	double y0;
	double xv;
	double yv;
	double r;
	
	public Asteroid() {
	}
	public Asteroid(Solution solution) {
		this.x0 = solution.getX0() - solution.getT0() * solution.getXv();
		this.y0 = solution.getY0() - solution.getT0() * solution.getYv();
		this.xv = solution.getXv();
		this.yv = solution.getYv();
		this.r = solution.getRadius();
	}
	public double getX0() {
		return x0;
	}
	public void setX0(double x0) {
		this.x0 = x0;
	}
	public double getY0() {
		return y0;
	}
	public void setY0(double y) {
		this.y0 = y;
	}
	public double getXv() {
		return xv;
	}
	public void setXv(double xv) {
		this.xv = xv;
	}
	public double getYv() {
		return yv;
	}
	public void setYv(double yv) {
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
