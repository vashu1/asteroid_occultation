package solution;
import solution.model.Telescope;
import solution.model.TelescopeManager;
import java.util.*;

public class Test {
	public static void main(String[] args) throws Exception {
		TelescopeManager tm = new TelescopeManager();
		
		tm.Load();
		
		List<Telescope> telescopes = tm.getTelescopes();
		
		System.out.println(telescopes);
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
