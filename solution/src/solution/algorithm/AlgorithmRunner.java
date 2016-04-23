package solution.algorithm;
import solution.model.*;
import java.util.*;

public class AlgorithmRunner {
	public static double INITIAL_WIDTH = 1;
	public static double INITIAL_RADIUS = 100;
	private static double STEP_X = INITIAL_RADIUS;
	private SolutionScoreCalculator scoreCalculator;
	
	public AlgorithmRunner(List<Telescope> telescopes, List<Event> events) {
		this.scoreCalculator = new SolutionScoreCalculator(events, telescopes);
		Solution best = getInitialSolution(events.get(0), events.get(events.size() - 1));
		
		double e = STEP_X;
		while (e > 0.5) {
			List<Solution> solutions = new ArrayList<Solution>();
			for (int param = -6; param <= 6; param++) {
				solutions.add(getChangedSolution(best, e, param));
			}
			best = getMaxScoreSolution(solutions);
					
			e *= 0.9;
		}
		System.out.println("INITIAL SOLUTION " + getInitialSolution(events.get(0), events.get(events.size() - 1)));
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
	
	public Solution getInitialSolution(Event e1, Event e2) {
		System.out.println("INITIAL EVENTS " + e1 + " (2): " + e2);
		double xv = (e2.getTelescope().getX() - e1.getTelescope().getX()) / ((e2.getStartTime() - e1.getEndTime()) / 1000);
		double yv = (e2.getTelescope().getY() - e1.getTelescope().getY()) / ((e2.getStartTime() - e1.getEndTime()) / 1000);
		double t0 = ((e2.getStartTime() - e1.getEndTime()) / 1000);
		double x0 = e1.getTelescope().getX() + t0 / 1000 * xv;
		double y0 = e1.getTelescope().getY() + t0 / 1000 * yv;
		return new Solution(INITIAL_RADIUS, t0, x0, y0, xv, yv);
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
				result.setT0(result.getT0() + e / INITIAL_RADIUS);
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
				return result;
		};
		
		result.setScore(this.scoreCalculator.getSolutionScore(result));
		
		return result;
	}
	
}
