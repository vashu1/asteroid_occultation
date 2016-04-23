package solution.model;

public class Solution {
	private double radius;
	private double t0;
	private double x0;
	private double y0;
	private double xv;
	private double yv;
	private double score;
	
	public Solution(Solution solution) {
		this.radius = solution.radius;
		this.t0 = solution.t0;
		this.x0 = solution.x0;
		this.y0 = solution.y0;
		this.xv = solution.xv;
		this.yv = solution.yv;
	}
	
	public Solution(double radius, double t0, double x0, double y0, double xv, double yv) {
		super();
		this.radius = radius;
		this.t0 = t0;
		this.x0 = x0;
		this.y0 = y0;
		this.xv = xv;
		this.yv = yv;
	}
	
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
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
	public void setY0(double y0) {
		this.y0 = y0;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getT0() {
		return t0;
	}

	public void setT0(double t0) {
		this.t0 = t0;
	}

	@Override
	public String toString() {
		return "Solution [radius=" + radius + ", t0=" + t0 + ", x0=" + x0 + ", y0=" + y0 + ", xv=" + xv + ", yv=" + yv
				+ ", score=" + score + "]";
	}
	
	
}
