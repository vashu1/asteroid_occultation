package solution;

import java.util.ArrayList;
import java.util.List;

import solution.model.Asteroid;
import solution.model.Solution;

public class SolutionAnalyser {
	public Asteroid realAsteroid;
	public List<Asteroid> bestCandidates;
	
	public SolutionAnalyser(Asteroid realAsteroid, List<Solution> bestSolutionCandidates) {
		super();
		this.realAsteroid = realAsteroid;
		this.bestCandidates = getBestCandidates(bestSolutionCandidates);
	}
	
	private List<Asteroid> getBestCandidates(List<Solution> solutions) {
		List<Asteroid> asteroids = new ArrayList<>();
		for (Solution solution: solutions) {
			asteroids.add(new Asteroid(solution));
		}
		return asteroids;
	}
	
	public void analyze() {
		System.out.println("IVAN, analyze the result please " + this.bestCandidates);
		// TODO: IVAN
	}
	
	
}
