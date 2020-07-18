package uk.ac.aston.coursework.elevator.study;

import java.io.File;
import java.io.IOException;

import uk.ac.aston.coursework.elevator.simulation.Configuration;
import uk.ac.aston.coursework.elevator.simulation.Simulation;

/**
 * This class contains a Main method. It runs the simulation with 10 different
 * seeds to test how the elevator can handle different p and q values.
 * 
 * @author Nisath
 *
 */
public class StudyRunner {

	/**
	 * Main method runs the study, runs the simulation a number of times with
	 * different p, q and seed values. The output is stored in a file called
	 * stats.csv which is located in a folder called resources which is located in
	 * the package uk.ac.aston.coursework.elevator.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		File file = new File("resources", "stats.csv");
		FileHandler handler = new FileHandler(file);

		for (int seed = 1; seed < 11; seed++) {
			for (double p = 0.001; p <= 0.005; p += 0.001) {
				for (double q = 0.002; q <= 0.01; q += 0.002) {
					runSim(p, q, seed, handler);
				}
			}
		}
	}

	/**
	 * Runs the simulation once with given fields.
	 * 
	 * @param p       p value: probability of changing floors.
	 * @param q       q value. probability of generating clients.
	 * @param seed    seed for Random object.
	 * @param handler the Filehandler object being used, to write output on.
	 * @throws IOException
	 */
	private static void runSim(double p, double q, int seed, FileHandler handler) throws IOException {
		Configuration config = new Configuration(10, 5, 5, seed, p, q);
		Simulation sim = new Simulation(config);
		for (int i = 0; i < 2880; i++) {
			sim.tickBuilding();
		}
		handler.write(sim);
	}

}
