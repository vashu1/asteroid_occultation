package solution.algorithm;
import solution.model.*;
import java.util.*;

public class AlgorithmRunner {
	public static double INITIAL_WIDTH = 1;
	public static double INITIAL_RADIUS = 100;
	private static double STEP_X = INITIAL_RADIUS;
	private SolutionScoreCalculator scoreCalculator;
	private Asteroid asteroid;
	
	private List<Event> events;
	
	public AlgorithmRunner(List<Telescope> telescopes, List<Event> events, List<Asteroid> asteroids) {
		this.scoreCalculator = new SolutionScoreCalculator(events, telescopes);
		this.events = events;
		
		this.asteroid = asteroids.get(0);
		Solution sol = new Solution(this.asteroid);
		System.out.println("FOR ASTEROID " + this.scoreCalculator.getSolutionScore(sol));
		List<Solution> solutions = new ArrayList<Solution>();
		for (int param = -6; param <= 6; param++) {
			solutions.add(getChangedSolution(sol, 10, param));
			System.out.println(getChangedSolution(sol, 10, param));
		}
		System.out.println("getMaxScoreSolution");
		System.out.println(solutions.size());
		System.out.println(getMaxScoreSolution(solutions));
		System.out.println("getMaxScoreSolution0");
		
	}

	public void run() {
		Solution best = getInitialSolution(events.get(0), events.get(events.size() - 1));
		System.out.println("BEST INIT " + best);
		
		double e = STEP_X * 2;
		while (e > 0.05) {
			List<Solution> solutions = new ArrayList<Solution>();
			for (int param = -6; param <= 6; param++) {
				solutions.add(getChangedSolution(best, e, param));
			}
			best = getMaxScoreSolution(solutions);
			//if(e>(INITIAL_RADIUS))
			System.out.println("BEST CANDIDATE SCORE: " + best.getScore());
					
			e *= 0.9;
			System.out.println("e " + e);
		}
		System.out.println("BEST SOLUTION " + best);
		System.out.println("SOMETHING IVAN ASKED " + (best.getX0() - (best.getT0() * best.getXv())));
		System.out.println("SOMETHING IVAN ASKED " + (best.getY0() - (best.getT0() * best.getYv())));
		System.out.println("----");
	}
	
	private Solution getMaxScoreSolution(List<Solution> solutions) {
		if (solutions.size() == 0) {
			return null;
		}
		Solution maxScore = solutions.get(0);
		for (Solution solution: solutions) {
			//System.out.println("ALTERENATIVE SCORE " + solution);
			if (maxScore.getScore() < solution.getScore()) {
				maxScore = solution;
			}
		}
		System.out.println("\n\n\n");
		return maxScore;
	}
	
	public Solution getInitialSolution(Event e1, Event e2) {
		System.out.println("INITIAL EVENTS " + e1 + " (2): " + e2);
		double xv = (e2.getTelescope().getX() - e1.getTelescope().getX()) / (e2.getStartTime() - e1.getStartTime());
		double yv = (e2.getTelescope().getY() - e1.getTelescope().getY()) / (e2.getStartTime() - e1.getStartTime());
		double middle_t = (e2.getStartTime() - e1.getStartTime()) / 2;
		double t0 = e1.getStartTime() + middle_t;
		double x0 = e1.getTelescope().getX() + middle_t * xv;
		double y0 = e1.getTelescope().getY() + middle_t * yv;
		Solution result =  new Solution(INITIAL_RADIUS, t0, x0, y0, xv, yv);
		result.setScore(this.scoreCalculator.getSolutionScore(result));
		System.out.println("INITIAL SCORE " + result.getScore());
		return result;
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
