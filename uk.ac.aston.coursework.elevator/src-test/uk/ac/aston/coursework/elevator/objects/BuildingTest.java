package uk.ac.aston.coursework.elevator.objects;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aston.coursework.elevator.simulation.Configuration;

public class BuildingTest {
	Building b;
	
	@Before
	public void setUp() {
		Configuration ui = new Configuration(10, 5, 5, 100, 0.001, 0.001);
		b = new Building(ui);
		
	}

	@Test (expected=NullPointerException.class)
	public void testGetFloor() {
		b.getFloor(7);
	}

}
