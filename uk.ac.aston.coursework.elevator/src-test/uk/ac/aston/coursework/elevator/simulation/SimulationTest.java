package uk.ac.aston.coursework.elevator.simulation;


import org.junit.Before;
import org.junit.Test;

public class SimulationTest {

	Simulation sim;
	
	@Before
	public void setup() {
		Configuration settings = new Configuration(10, 5, 5, 100, 0.001, 0.002);
		sim = new Simulation(settings);
	}
	
	@Test
	public void testSimulation() {
		sim.tickBuilding();
	}

}
