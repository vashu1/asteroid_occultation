package solution;

import java.nio.file.Files;
import java.nio.file.Paths;
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
	private final static int ITERATION_COUNT = 5;
	
	public static void main(String[] args) throws Exception {
		System.out.println("Occultation calculation started");
		int iterationCount = ITERATION_COUNT;
		if (args.length > 0) {
			iterationCount = Integer.parseInt(args[0]);
		}
		long startTime = System.currentTimeMillis();
		
		StringBuilder errors = new StringBuilder();
		int noGoodPairCount = 0;
		for (int i=1; i<=iterationCount; i++) {
			new SimulationRunner("generate_data_noise.py").launchSimulation();
			try {
				errors.append(runAlgorithm()).append("\n");
			} catch(NoGoodPairException e) {
				noGoodPairCount++;
			}
			System.out.println("Iteration " + i + " of " + iterationCount + " finished, processing time: " + 
					((System.currentTimeMillis() - startTime) / 1000.0) + " sec");
		}
		outputResult(errors, noGoodPairCount);
	}
	
	private static void outputResult(StringBuilder errors, int noGoodPairCount) throws Exception {
		errors.append("\n\n").append("No good pairs run: ").append(noGoodPairCount);
		Files.write(Paths.get("./result.txt"), errors.toString().getBytes());
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
