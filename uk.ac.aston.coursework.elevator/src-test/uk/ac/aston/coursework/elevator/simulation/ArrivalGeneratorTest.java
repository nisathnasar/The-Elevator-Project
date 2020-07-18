package uk.ac.aston.coursework.elevator.simulation;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aston.coursework.elevator.objects.Building;

public class ArrivalGeneratorTest {
	ArrivalGenerator gen;
	
	@Before
	public void setup() {
		Configuration settings = new Configuration(10, 5, 5, 100, 0.001, 0.002);
		Building building = new Building(settings);
		gen = building.getArrivalGenerator();
	}
	
	@Test
	public void testGetStatistics() {
		Statistics stats = gen.getStatistics();
		assertNotNull(stats);
		
	}

}
