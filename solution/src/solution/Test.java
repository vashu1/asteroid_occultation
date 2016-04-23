package solution;
import solution.model.Telescope;
import solution.model.Event;
import solution.model.TelescopeManager;
import solution.model.EventManager;

import java.util.*;

public class Test {
	public static void main(String[] args) throws Exception {
		TelescopeManager tm = new TelescopeManager();
		
		tm.Load();
		
		List<Telescope> telescopes = tm.getTelescopes();
		
		System.out.println(telescopes);
		
		
		EventManager em = new EventManager();
		
		em.Load(tm);
		
		List<Event> events = em.getEvents();
		
		System.out.println(events);
	}
	
//	public static LoadEvents() {
//		
//	}
//	
//	public static LoadAsteroids() {
//		
//	}
//	
//	public static LoadTelescopes() {
//		
//	}

}
