package solution.algorithm;

import java.util.*;

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
		return eventFit(solutionEvents, this.events, solution);
	}
	
	private double eventFit(List<Event> solutionEvents, List<Event> realEvents, Solution solution) {
		Map<Integer, List<Event>> solutionEventMap = getEventMap(solutionEvents);
		Map<Integer, List<Event>> realEventMap = getEventMap(realEvents);
		double totalScore = solution.getRadius() / AlgorithmRunner.INITIAL_RADIUS;
		for (Telescope telescope : this.telescopes) {
			totalScore -= getTelescopeScore(solutionEventMap.get(telescope.getId()), realEventMap.get(telescope.getId()));
		}

		return totalScore; 
	}
	
	private double getTelescopeScore(List<Event> solutionEvents, List<Event> realEvents) {
		double totalSolutionEventDuration = getTotalEventDuration(solutionEvents);
		double totalRealEventDuration = getTotalEventDuration(realEvents);
		return totalSolutionEventDuration + totalRealEventDuration
				- 2 * getTotalIntersectionDuration(solutionEvents, realEvents);
	}
	
	private double getTotalEventDuration(List<Event> events) {
		double duration = 0;
		for (Event e : events) {
			duration += (e.getEndTime() - e.getStartTime());
		}
		return duration;
	}
	
	private double getTotalIntersectionDuration(List<Event> solutionEvents, List<Event> realEvents) {
		double totalIntersection = 0;
		if (solutionEvents.size() == 0 || realEvents.size() == 0)
			return 0;
		int solutionEventIndex = 0;
		int realEventIndex = 0;
		Event solutionEvent = getSafeEvent(solutionEvents, 0);
		Event realEvent = getSafeEvent(realEvents, 0);
		while (solutionEvent != null && realEvent != null) {
			double intersectionDuration = Math.min(solutionEvent.getEndTime(), realEvent.getEndTime()) -
					Math.max(solutionEvent.getStartTime(), realEvent.getStartTime());
			if (intersectionDuration > 0) 
				totalIntersection += intersectionDuration;
			if (solutionEvent.getEndTime() < realEvent.getEndTime()) {
				solutionEventIndex++;
			} else {
				realEventIndex++;
			}

			solutionEvent = getSafeEvent(solutionEvents, solutionEventIndex);
			realEvent = getSafeEvent(realEvents, realEventIndex);
		}
		return totalIntersection;
	}
	
	private Event getSafeEvent(List<Event> list, int index) {
		return (index >= list.size()) ? null : list.get(index);
	}
	
	
	private Map<Integer, List<Event>> getEventMap(List<Event> events) {
		Map<Integer, List<Event>> solutionMap = new HashMap<Integer, List<Event>>();
		for (Event e: events) {
			if (solutionMap.get(e.getTelescope().getId()) == null) {
				solutionMap.put(e.getTelescope().getId(), new ArrayList<Event>());
			}
			solutionMap.get(e.getTelescope().getId()).add(e);
		}
		return solutionMap;
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
		double xa = solution.getX0() - solution.getT0() * solution.getXv();
		double ya = solution.getY0() - solution.getT0() * solution.getYv();
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
