package solution.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class EventManager {
	private final static String INPUT_FILE_EVENTS = "events.txt";

	private List<Event> events = new ArrayList<Event>();
	private TreeMap<Double, List<Event>> buckets = new TreeMap<Double, List<Event>>();
	private String folder;
	private static double step = 0.5;
	public EventManager(String folder) {
		this.folder = folder;
	}
	
	public void Load(TelescopeManager tm) throws IOException {
		Path eventsFile = Paths.get(Paths.get("").toAbsolutePath() + java.io.File.separator + this.folder + java.io.File.separator + INPUT_FILE_EVENTS);
		BufferedReader reader = Files.newBufferedReader(eventsFile, Charset.defaultCharset());
		String line = null;
		while ((line = reader.readLine()) != null) {
		    
		    String[] parts = line.split(" ");
		    if (parts[0].equals("#")) { 
		    	continue;
		    }
		    Event e = new Event(tm.getTelescopeById(Integer.parseInt(parts[0])), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
		    events.add(e);
		    Collections.sort(this.events);
		}
		this.calculateTimeBuckets();
		showBuckets();
		System.out.println("Buckets" + buckets);
	}
	
	private void showBuckets() {
		for(List<Event> events: buckets.values()) {
			System.out.println("Bucket Size" + events.size());
		}
	}
	
	private void calculateTimeBuckets() {
		double currentKey = 0; 
		double currentBucketTimeLimit = events.get(0).getStartTime() + step;
		buckets.put(currentKey, new ArrayList<Event> ());
		for (Event e: this.events) {
			if (e.getStartTime() > currentBucketTimeLimit){
				buckets.put(++currentKey, new ArrayList<Event> ());	
				currentBucketTimeLimit = currentBucketTimeLimit + step;	
			}
			buckets.get(currentKey).add(e);
		}
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public Map<Double, List<Event>> getTimeBuckets() {
		return null;
	}
	
}
