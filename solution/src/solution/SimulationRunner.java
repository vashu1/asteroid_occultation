package solution;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.nio.file.Paths;

public class SimulationRunner {
	public static void launchSimulation() throws Exception {
		String absPath = Paths.get(Paths.get("").toAbsolutePath() + java.io.File.separator + ".." + 
				java.io.File.separator + "simulation" + java.io.File.separator + "generate_data.py" ).toString();
		String command = "python " + absPath;
//		String line;
	    Process child = Runtime.getRuntime().exec(command);
	    int exitCode = child.waitFor();
//	    BufferedReader input = new BufferedReader(new InputStreamReader(child.getInputStream()));
//	    while ((line = input.readLine()) != null) {
//	      System.out.println(line);
//	    }
//	    input.close();
	}
}
