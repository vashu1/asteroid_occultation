package solution;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import solution.algorithm.AlgorithmRunner;
import solution.model.Asteroid;
import solution.model.AsteroidManager;
import solution.model.EventManager;
import solution.model.NoGoodPairException;
import solution.model.Solution;
import solution.model.Telescope;
import solution.model.TelescopeManager;

public class SmartSolutionManager {

	private final static String INPUT_FOLDER = ".";
	private final static int ITERATION_COUNT = 100;
	
	public static void main(String[] args) throws Exception {
		System.out.println("STARTED " + System.currentTimeMillis());

		StringBuilder errors = new StringBuilder();
		int noGoodPairCount = 0;
		for (int i=0; i<ITERATION_COUNT; i++) {
			new SimulationRunner("generate_data_noise.py").launchSimulation();
			try {
				errors.append(runAlgorithm()).append("\n");
			} catch(NoGoodPairException e) {
				noGoodPairCount++;
			}
		}
		outputResult(errors, noGoodPairCount);
		System.out.println("ENDED " + System.currentTimeMillis());
	}
	
	private static void outputResult(StringBuilder errors, int noGoodPairCount) throws Exception {
		errors.append("\n\n").append("No good pairs run: ").append(noGoodPairCount);
		Files.write(Paths.get("./errors.txt"), errors.toString().getBytes());
	}
	
	private static double runAlgorithm() throws Exception {
		TelescopeManager tm = new TelescopeManager(INPUT_FOLDER);
		tm.Load();
		
		AsteroidManager am = new AsteroidManager(INPUT_FOLDER);
		am.Load();
		
		EventManager em = new EventManager(INPUT_FOLDER);
		em.Load(tm);
		
		List<Telescope> telescopes = tm.getTelescopes();
		
		List<Asteroid> asteroids = am.getAsteroids();
				
		List<Solution> solutions = new AlgorithmRunner(telescopes, em, asteroids).smartRun();
		SolutionAnalyser analyzer = new SolutionAnalyser(asteroids.get(0), solutions);
		return analyzer.analyze();
		
	}

}
