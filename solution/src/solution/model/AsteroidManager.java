package solution.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AsteroidManager {
	
	private final static String INPUT_FILE_ASTEROIDS = "asteroid.txt";

	private List<Asteroid> asteroids = new ArrayList<Asteroid>();
	
    private String folder;
	
	public AsteroidManager(String folder) {
		this.folder = folder;
	}
	
	public List<Asteroid> getAsteroids() {
		return asteroids;
	}
	
	public void Load() throws IOException {
		Path eventsFile = Paths.get(Paths.get("").toAbsolutePath() + java.io.File.separator + this.folder + java.io.File.separator + INPUT_FILE_ASTEROIDS);
		BufferedReader reader = Files.newBufferedReader(eventsFile, Charset.defaultCharset());
		StringBuilder content = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    content.append(line).append("/n");
		    
		    String[] parts = line.split(" ");
		    if (parts[0].equals("#")) { 
		    	continue;
		    }
		    Asteroid t = new Asteroid();
		    t.setX0(Integer.parseInt(parts[0]));
		    t.setY0(Integer.parseInt(parts[1]));
		    t.setXv(Integer.parseInt(parts[2]));
		    t.setYv(Integer.parseInt(parts[3]));
		    t.setR(Double.parseDouble(parts[4]));
		    asteroids.add(t);
		}
	}
}
