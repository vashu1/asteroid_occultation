package solution.model;

public class Event implements Comparable {
	private Telescope telescope;
	private double startTime; //milliseconds
	private double endTime; //milliseconds
	
	public Event(Telescope telescope, double startTime, double endTime) {
		super();
		this.telescope = telescope;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public Telescope getTelescope() {
		return telescope;
	}
	public void setTelescope(Telescope telescope) {
		this.telescope = telescope;
	}
	public double getStartTime() {
		return startTime;
	}
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
	public double getEndTime() {
		return endTime;
	}
	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}
	public String toString() {
		return "Event:" + telescope + " StartTime " + startTime + ", endTime " + endTime;
	}
	@Override
	public int compareTo(Object e2) {
		return new Double(this.startTime).compareTo(((Event)e2).getStartTime());
	}
	
	
}
