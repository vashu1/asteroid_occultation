package solution.algorithm;

import java.util.ArrayList;
import java.util.List;

import solution.model.Event;
import solution.model.Solution;
import solution.model.Telescope;

public class SolutionScoreCalculator {
	private List<Event> events;
	private List<Telescope> telescopes;
	public SolutionScoreCalculator(List<Event> events, List<Telescope> telescopes) {
		this.events = events;
		this.telescopes = telescopes;
	}

	public double getSolutionScore(Solution solution) {
		List<Event> solutionEvents = calculateSolutionEvents(solution);
		// add f(R) to score
		return eventFit(solutionEvents, this.events);
	}
	
	private double eventFit(List<Event> solutionEvents, List<Event> realEvents) {
		// sort by telescope
		//for telescope
		//false positive
		//false negatives
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
