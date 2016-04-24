package solution;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.nio.file.Paths;

public class SimulationRunner {
	private static final String GENERATOR_FILE = "generate_data.py";
	private String generatorFile;
	
	public SimulationRunner() {
		this.generatorFile = GENERATOR_FILE;
	}
	
	public SimulationRunner(String generatorFile) {
		this.generatorFile = generatorFile;
	}

	public void launchSimulation() throws Exception {
		String absPath = Paths.get(Paths.get("").toAbsolutePath() + java.io.File.separator + ".." + 
				java.io.File.separator + "simulation" + java.io.File.separator + this.generatorFile).toString();
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
