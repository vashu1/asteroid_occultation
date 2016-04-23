package solution.algorithm;
import solution.model.*;
import java.util.*;

public class AlgorithmRunner {
	private static double INITIAL_RADIUS = 100;
	private static double STEP_X = INITIAL_RADIUS;
	private int e = 0;
	private SolutionScoreCalculator scoreCalculator;
	
	public AlgorithmRunner(List<Event> events, List<Telescope> telescopes) {
		this.scoreCalculator = new SolutionScoreCalculator(events, telescopes);
		/* Solution best = Something(getInitialSolution(events.get(0), events.get(events.size() - 1)));
		
		double e = STEP_X;
		while (e > 0.5) {
			List<Solution> solutions = new ArrayList<Solution>();
			for (int param = -5; param <= 5; param++) {
				solutions.add(getChangedSolution(best, e, param));
			}
			best = max solutions;
					
			e *= 0.9;
		} */
	}
	
	public Solution getInitialSolution(Event e1, Event e2) {
		double xv = (e2.getTelescope().getX() - e1.getTelescope().getX()) / (e2.getStartTime() - e1.getEndTime());
		double yv = (e2.getTelescope().getY() - e1.getTelescope().getY()) / (e2.getStartTime() - e1.getEndTime());
		double x0 = e1.getTelescope().getX() - e1.getStartTime() * xv;
		double y0 = e1.getTelescope().getY() - e1.getStartTime() * yv;
		return new Solution(INITIAL_RADIUS, x0, y0, xv, yv);
	}

	
	/**
	 * 
	 * @param solution
	 * @param e
	 * @param paramNumberToChange : - 5 to 5
	 * @return
	 */
	public Solution getChangedSolution(Solution solution, double e, int paramNumberToChange) {
		Solution result = new Solution(solution);
		
		e = paramNumberToChange > 0 ? +e : -e;
		paramNumberToChange = Math.abs(paramNumberToChange); // positive now
		
		switch(paramNumberToChange) {
			case 1:
				result.setX0(result.getX0() + e);
				break;
			case 2:
				result.setY0(result.getY0() + e);
				break;
			case 3:
				result.setXv(result.getXv() + e);
				break;
			case 4:
				result.setYv(result.getYv() + e);
				break;
			case 5:
				result.setRadius(result.getRadius() + e * INITIAL_RADIUS);
				break;
			case 0:
				// do nothing
				return result;
		};
		
		result.setScore(this.scoreCalculator.getSolutionScore(result));
		
		return result;
	}
	
}
