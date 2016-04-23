package solution.algorithm;
import solution.model.*;
import java.util.*;

public class AlgorithmRunner {
	private static double INITIAL_RADIUS = 100;
	private static double STEP_X = INITIAL_RADIUS;
	private int e = 0;
	private List<Telescope> telescopes;
	private List<Event> events;
	
	public AlgorithmRunner(List<Event> events, List<Telescope> telescopes) {
		this.events = events;
		this.telescopes = telescopes;
		/* Solution best = Something(getInitialSolution(events.get(0), events.get(events.size() - 1)));
		
		double e = STEP_X;
		while (e > 0.5) {
			List<Solution> solutions = new ArrayList<Solution>();
			for (int param = -5; param <= 5; param++) {
				solutions.add(getChangedSolution(best, e, param));
			}
			best = max solutions;
					
			e *= 0.8;
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
		
		result.setScore(solutionScore(result));
		
		return result;
	}
	
	public double solutionScore(Solution solution) {
		List<Event> solutionEvents = calculateSolutionEvents(solution);
		return eventFit(solutionEvents, this.events);
	}
	
	private double eventFit(List<Event> solutionEvents, List<Event> realEvents) {
		return 0; // TODO
	}
	
	private List<Event> calculateSolutionEvents(Solution solution) {
		List<Event> list = new ArrayList<Event>();
		for (Telescope telescope : this.telescopes) {
			Event ev = calculateTelescopeEvent(telescope, solution);
			if (ev != null) {
				list.add(ev);
			}
		}
		return list;
	}
	
	private Event calculateTelescopeEvent(Telescope telescope, Solution solution) {
		double xa = solution.getX0();
		double ya = solution.getY0();
		double vxa = solution.getXv();
		double vya = solution.getYv();
		double xt = telescope.getX();
		double yt = telescope.getY();
		
		double xd = xa - xt;
		double yd = ya - yt;
		
		double a = vxa * vxa + vya * vya;
		double b = 2 * (vxa * xd + vya * yd);
		double c = xd * xd + yd * yd - solution.getRadius() * solution.getRadius();
		
		double D = b * b - 4 * a * c;
		
		if (D <= 0)
			return null;
		
		double t1 = (-b + Math.sqrt(D)) / (2 * a);
		double t2 = (-b - Math.sqrt(D)) / (2 * a);
		
		return new Event(telescope, t2, t1);
	}
}
