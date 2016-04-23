package solution;
import java.util.List;

import solution.model.Asteroid;
import solution.model.AsteroidManager;
import solution.model.Telescope;
import solution.model.Event;
import solution.model.TelescopeManager;
import solution.model.EventManager;

public class Test {
	public static void main(String[] args) throws Exception {
		TelescopeManager tm = new TelescopeManager();
		
		tm.Load();
		
		AsteroidManager am = new AsteroidManager();
		
		am.Load();
		
		List<Telescope> telescopes = tm.getTelescopes();
		
		List<Asteroid> asteroids = am.getAsteroids();
		
		System.out.println(telescopes);
		
		
		EventManager em = new EventManager();
		
		em.Load(tm);
		
		List<Event> events = em.getEvents();
		
		System.out.println(events);
		System.out.println(asteroids);

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
