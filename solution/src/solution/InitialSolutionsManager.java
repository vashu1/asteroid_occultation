package solution;

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

public class InitialSolutionsManager {
	private final static String INPUT_FOLDER = ".";
	private final static int ITERATION_COUNT = 1;
	
	public static void main(String[] args) throws Exception {
		System.out.println("STARTED " + System.currentTimeMillis());

		StringBuilder results = new StringBuilder();
		for (int i=0; i<ITERATION_COUNT; i++) {
			new SimulationRunner().launchSimulation();
			String result = launchSolution();
			results.append(result).append("\n");
		}
		Files.write(Paths.get("./initialSolutions.txt"), results.toString().getBytes());
		System.out.println("ENDED " + System.currentTimeMillis());
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
		
		String solution = new AlgorithmRunner(telescopes, em, asteroids).getInitialSolution().getScore() + "";
//		System.out.println(solution);
		return solution;
	}
}
