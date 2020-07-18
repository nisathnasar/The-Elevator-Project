package uk.ac.aston.coursework.elevator.simulation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {
	Simulation sim;
	Statistics stats;
	
	@Before
	public void setup() {
		Configuration settings = new Configuration(10, 5, 5, 100, 0.005, 0.01);
		sim = new Simulation(settings);
		stats = sim.getBuilding().getArrivalGenerator().getStatistics();
	}
	
	@Test
	public void testAverageWaitingTime() {
		
		double[] exp = {0.0};
		double[] act = {stats.getAverageWaitingTime()};
		System.out.println("0:" + exp[0]);
		assertArrayEquals(exp, act, 0.0);		
	}

}
