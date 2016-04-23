package solution.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
	private final static String INPUT_FOLDER = "src" + java.io.File.separator + "generate_data_output_example";
	private final static String INPUT_FILE_EVENTS = "events.txt";

	private List<Event> telescopes = new ArrayList<Event>();
	
	public void Load(TelescopeManager tm) throws IOException {
		Path eventsFile = Paths.get(Paths.get("").toAbsolutePath() + java.io.File.separator + INPUT_FOLDER + java.io.File.separator + INPUT_FILE_EVENTS);
		BufferedReader reader = Files.newBufferedReader(eventsFile, Charset.defaultCharset());
		String line = null;
		while ((line = reader.readLine()) != null) {
		    
		    String[] parts = line.split(" ");
		    if (parts[0].equals("#")) { 
		    	continue;
		    }
		    Event e = new Event();
		    e.setTelescope(tm.getTelescopeById(Integer.parseInt(parts[0])));
		    e.setStartTime(Long.parseLong(parts[1]));
		    e.setEndTime(Long.parseLong(parts[2]));
		    telescopes.add(e);
		}
	}

	public List<Event> getEvents() {
		return telescopes;
	}

	public void setEvents(List<Event> telescopes) {
		this.telescopes = telescopes;
	}
	
}
