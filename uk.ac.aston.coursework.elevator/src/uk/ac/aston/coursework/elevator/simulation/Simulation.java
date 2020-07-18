package uk.ac.aston.coursework.elevator.simulation;

import uk.ac.aston.coursework.elevator.objects.Building;

/**
 * This simulation sets up a building.
 * 
 * @author Nisath
 *
 */
public class Simulation {
	private static int TICK;
	private static int MAXTICK = 2880;
	private Building building;
	private Configuration config;

	/**
	 * Constructor constructs building.
	 * 
	 * @param config a Configuration object that contains all the required fields in
	 *               order to run the simulation.
	 */
	public Simulation(Configuration config) {
		TICK = 0;
		building = new Building(config);
		building.showFloors();
		this.config = config;
	}

	/**
	 * Get current tick.
	 * 
	 * @return returns current tick.
	 */
	public static int getTick() {
		return Simulation.TICK;
	}

	/**
	 * Get max limit of tick.
	 * 
	 * @return maximum limit of tick.
	 */
	public static int getMaxTick() {
		return Simulation.MAXTICK;
	}

	/**
	 * This method returns the Settings class being used.
	 * 
	 * @return {@code Settings} object that is being used.
	 */
	public Configuration getConfiguration() {
		return config;
	}

	/**
	 * ticks building once per call.
	 */
	public void tickBuilding() {
		building.tick();
		Simulation.TICK++;
	}

	/**
	 * 
	 * @return the building that this simulation runs.
	 */
	public Building getBuilding() {
		return building;
	}

}
