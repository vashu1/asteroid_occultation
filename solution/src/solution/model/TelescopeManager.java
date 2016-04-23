package solution.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class TelescopeManager {

	private final static String INPUT_FOLDER = "src" + java.io.File.separator + "generate_data_output_example";
	private final static String INPUT_FILE_EVENTS = "events.txt";
	private final static String INPUT_FILE_TELESCOPES = "telescope.txt";

	private List<Telescope> telescopes = new ArrayList<Telescope>();
	
	public void Load() throws IOException {
		Path eventsFile = Paths.get(Paths.get("").toAbsolutePath() + java.io.File.separator + INPUT_FOLDER + java.io.File.separator + INPUT_FILE_EVENTS);
		BufferedReader reader = Files.newBufferedReader(eventsFile, Charset.defaultCharset());
		StringBuilder content = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    content.append(line).append("/n");
		    
		    String[] parts = line.split(" ");
		    if (parts[0].equals("#")) { 
		    	continue;
		    }
		    System.out.println(parts[0]);
		    Telescope t = new Telescope();
		    t.setId(Integer.parseInt(parts[0]));
		    t.setX(Double.parseDouble(parts[1]));
		    t.setY(Double.parseDouble(parts[2]));
		    telescopes.add(t);
		    System.out.println(t);
		}
	}

	public List<Telescope> getTelescopes() {
		return telescopes;
	}

	public void setTelescopes(List<Telescope> telescopes) {
		this.telescopes = telescopes;
	}
	
	
}
