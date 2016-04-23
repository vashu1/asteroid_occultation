package solution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import solution.algorithm.AlgorithmRunner;
import solution.model.Asteroid;
import solution.model.AsteroidManager;
import solution.model.Event;
import solution.model.EventManager;
import solution.model.Telescope;
import solution.model.TelescopeManager;

public class Integration {
	private final static String INPUT_FOLDER = ".";
	//private final static String SIMULATOR_COMMAND = 
	
	public static void main(String[] args) throws Exception {
		
		
		StringBuilder results = new StringBuilder();
		for (int i=0;i<1000;i++) {
			launchSimulation();
			String result = launchSolution();
			results.append(result).append("\n");
		}
		Files.write(Paths.get("./duke.txt"), results.toString().getBytes());
	}
	
	private static void launchSimulation() throws Exception {
		String absPath = Paths.get(Paths.get("").toAbsolutePath() + java.io.File.separator + INPUT_FOLDER +
									java.io.File.separator + ".." + java.io.File.separator + "simulation" + java.io.File.separator + "generate_data.py" ).toString();
		String command = "python " + absPath;
		String line;
	    Process child = Runtime.getRuntime().exec(command);
	    BufferedReader input = new BufferedReader(new InputStreamReader(child.getInputStream()));
	    while ((line = input.readLine()) != null) {
	      System.out.println(line);
	    }
	    input.close();

	}
	
	private static String launchSolution() throws Exception {
		TelescopeManager tm = new TelescopeManager(INPUT_FOLDER);
		tm.Load();
		
		AsteroidManager am = new AsteroidManager(INPUT_FOLDER);
		am.Load();
		
		EventManager em = new EventManager(INPUT_FOLDER);
		em.Load(tm);
		
		List<Telescope> telescopes = tm.getTelescopes();
		List<Asteroid> asteroids = am.getAsteroids();
		List<Event> events = em.getEvents();
		
		String solution = new AlgorithmRunner(telescopes, events, asteroids).getInitialSolution().getScore() + "";
		System.out.println(solution);
		return solution;
	}
}
