package solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import solution.model.Asteroid;
import solution.model.Solution;

public class SolutionAnalyser {
	public Asteroid realAsteroid;
	public List<Asteroid> bestCandidates;
	private List<Solution> solutions;
	
	public SolutionAnalyser(Asteroid realAsteroid, List<Solution> bestSolutionCandidates) {
		super();
		this.realAsteroid = realAsteroid;
		this.solutions = bestSolutionCandidates;
		Collections.sort(this.solutions);
		this.bestCandidates = getBestCandidates(bestSolutionCandidates);
	}
	
	private List<Asteroid> getBestCandidates(List<Solution> solutions) {
		List<Asteroid> asteroids = new ArrayList<>();
		for (Solution solution: solutions) {
			asteroids.add(new Asteroid(solution));
		}
		return asteroids;
	}
	
	public double analyze() {
		System.out.println("ASTEROID " + realAsteroid);
		Asteroid found = new Asteroid(this.solutions.get(0));
		System.out.println("BEST candidate " + found);
		double error = getError(realAsteroid, found);
		System.out.println("ERROR: " + error);	
		return error;
	}
	
	private double getError(Asteroid real, Asteroid found) {
		return Math.sqrt(Math.pow(real.getXv() - found.getXv(), 2) + Math.pow(real.getYv() - found.getYv(), 2));
	}
	
	
}
