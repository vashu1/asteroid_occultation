package solution.algorithm;
import solution.model.*;
import java.util.*;

public class AlgorithmRunner {
	public static double INITIAL_WIDTH = 1;
	public static double INITIAL_RADIUS = 100;
	private static double STEP_X = INITIAL_RADIUS;
	private static int BEST_CANDIDATES_COUNT = 10;
	private SolutionScoreCalculator scoreCalculator;
	private TreeMap<Double, List<Event>> timeBuckets;
	private Asteroid asteroid;
	
	private List<Event> events;
	
	public AlgorithmRunner(List<Telescope> telescopes, EventManager eventManager, List<Asteroid> asteroids) {
		this.events = eventManager.getEvents();
		this.timeBuckets = eventManager.getTimeBuckets();
		this.scoreCalculator = new SolutionScoreCalculator(events, telescopes);
		
//		this.asteroid = asteroids.get(0);
//		Solution sol = new Solution(this.asteroid);
//		List<Solution> solutions = new ArrayList<Solution>();
//		for (int param = -6; param <= 6; param++) {
//			solutions.add(getChangedSolution(sol, 10, param));
//		}
	}
	
	public List<Solution> smartRun() {
		List<Solution> solutions = new ArrayList<>();
		for (Event firstEvent: this.timeBuckets.get(this.timeBuckets.firstKey())) {
			for (Event secondEvent: this.timeBuckets.get(this.timeBuckets.lastKey())) {
				solutions.add(getInitialSolution(firstEvent, secondEvent));
			}
		}
		Collections.sort(solutions);
		List<Solution> bestInitialSolutions = solutions.subList(0, BEST_CANDIDATES_COUNT);
		List<Solution> bestSolutions = new ArrayList<>();
		for (Solution bestInitial : bestInitialSolutions) {
			bestSolutions.add(runAlgorithm(bestInitial));
		}
		return bestSolutions;
	}
	
	public void run() {
		Solution first = getInitialSolution();
		runAlgorithm(first);
	}
	
	private Solution runAlgorithm(Solution best) {
		double e = STEP_X;
		while (e > 0.05) {
			List<Solution> solutions = new ArrayList<Solution>();
			for (int param = -6; param <= 6; param++) {
				solutions.add(getChangedSolution(best, e, param));
			}
			best = getMaxScoreSolution(solutions);
					
			e *= 0.99;
		}
		return best;
//		System.out.println("SOLUTION: " + best);
	}
	
	public Solution getInitialSolution() {
		Event e1 = events.get(0);
		Event e2 = events.get(events.size() - 1);
		return getInitialSolution(e1, e2);
	}
	
	private Solution getInitialSolution(Event e1, Event e2) {
		double xv = (e2.getTelescope().getX() - e1.getTelescope().getX()) / (e2.getStartTime() - e1.getStartTime());
		double yv = (e2.getTelescope().getY() - e1.getTelescope().getY()) / (e2.getStartTime() - e1.getStartTime());
		double middle_t = (e2.getStartTime() - e1.getStartTime()) / 2;
		double t0 = e1.getStartTime() + middle_t;
		double x0 = e1.getTelescope().getX() + middle_t * xv;
		double y0 = e1.getTelescope().getY() + middle_t * yv;
		Solution result =  new Solution(INITIAL_RADIUS, t0, x0, y0, xv, yv);
		result.setScore(this.scoreCalculator.getSolutionScore(result));
		return result;
	}
	
	private Solution getMaxScoreSolution(List<Solution> solutions) {
		if (solutions.size() == 0) {
			return null;
		}
		Solution maxScore = solutions.get(0);
		for (Solution solution: solutions) {
			if (maxScore.getScore() < solution.getScore()) {
				maxScore = solution;
			}
		}
		return maxScore;
	}
	
	
	/**
	 * 
	 * @param solution
	 * @param e
	 * @param paramNumberToChange : - 6 to 6
	 * @return
	 */
	public Solution getChangedSolution(Solution solution, double e, int paramNumberToChange) {
		Solution result = new Solution(solution);
		
		e = paramNumberToChange > 0 ? +e : -e;
		paramNumberToChange = Math.abs(paramNumberToChange); // positive now
		
		switch(paramNumberToChange) {
			case 1:
				result.setT0(result.getT0() + e / 2 / INITIAL_RADIUS);
				break;
			case 2:
				result.setX0(result.getX0() + e);
				break;
			case 3:
				result.setY0(result.getY0() + e);
				break;
			case 4:
				result.setXv(result.getXv() + e);
				break;
			case 5:
				result.setYv(result.getYv() + e);
				break;
			case 6:
				result.setRadius(result.getRadius() + e);
				break;
			case 0:
				// do nothing
				//return result;
		};
		
		result.setScore(this.scoreCalculator.getSolutionScore(result));
		
		return result;
	}
	
}
