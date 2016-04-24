package solution;

import java.util.List;

import solution.algorithm.AlgorithmRunner;
import solution.model.Asteroid;
import solution.model.AsteroidManager;
import solution.model.EventManager;
import solution.model.Telescope;
import solution.model.TelescopeManager;

public class SmartSolutionManager {

	private final static String INPUT_FOLDER = ".";

	public static void main(String[] args) throws Exception {
		new SimulationRunner("generate_data_noise.py").launchSimulation();
		runAlgorithm();
	}
	
	private static void runAlgorithm() throws Exception {
		TelescopeManager tm = new TelescopeManager(INPUT_FOLDER);
		tm.Load();
		
		AsteroidManager am = new AsteroidManager(INPUT_FOLDER);
		am.Load();
		
		EventManager em = new EventManager(INPUT_FOLDER);
		em.Load(tm);
		
		List<Telescope> telescopes = tm.getTelescopes();
		
		List<Asteroid> asteroids = am.getAsteroids();
				
//		System.out.println(telescopes);
//		System.out.println(events);
//		System.out.println(asteroids);
//		
		SolutionAnalyser analyzer = new SolutionAnalyser(asteroids.get(0), new AlgorithmRunner(telescopes, em, asteroids).smartRun());
		analyzer.analyze();
	}

}
