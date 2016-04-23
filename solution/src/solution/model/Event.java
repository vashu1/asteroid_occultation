package solution.model;

public class Event {
	private Telescope telescope;
	private long startTime; //milliseconds
	private long endTime; //milliseconds
	
	public Telescope getTelescope() {
		return telescope;
	}
	public void setTelescope(Telescope telescope) {
		this.telescope = telescope;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String toString() {
		return "Event:" + telescope + " StartTime " + startTime + ", endTime " + endTime;
	}
	
	
}
