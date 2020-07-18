package uk.ac.aston.coursework.elevator.study;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import uk.ac.aston.coursework.elevator.people.Client;
import uk.ac.aston.coursework.elevator.simulation.Simulation;

/**
 * This class has a write method which writes to a file.
 * 
 * @author Nisath
 *
 */
public class FileHandler {
	private static String SEPARATOR = ",";
	private static boolean headersPrinted = false;
	File file;

	/**
	 * Constructor simply stores the file.
	 * 
	 * @param file the file to write to.
	 */
	public FileHandler(File file) {
		this.file = file;
	}

	/**
	 * Uses simulation to extract certain information to run study and writes to
	 * file.
	 * 
	 * @param sim the simulation object to extract information from.
	 * @throws IOException
	 */
	public void write(Simulation sim) throws IOException {
		if (!headersPrinted) {
			try (FileWriter fw = new FileWriter(file); PrintWriter pw = new PrintWriter(fw)) {
				pw.println(String.join(SEPARATOR, "Tick", "p", "q", "seed", "num of Clients", "num of m crew", "complaints", "avg waiting time"));
				System.out.println("done");
			}
			headersPrinted = true;
		}

		try (FileWriter fw = new FileWriter(file, true); PrintWriter pw = new PrintWriter(fw)) {
			pw.println(
					String.join(SEPARATOR, Simulation.getTick() + "", 
							sim.getConfiguration().getP() + "",
							sim.getConfiguration().getQ() + "", 
							sim.getConfiguration().getSeed() + "",
							sim.getBuilding().getArrivalGenerator().getClientList().size() + "",
							sim.getBuilding().getArrivalGenerator().getMaintenanceCrewList().size() + "",
							Client.getComplaintCount() + "",
							String.format("%.2f",
									sim.getBuilding().getArrivalGenerator().getStatistics().getAverageWaitingTime())
									+ ""));
		}
	}
}
