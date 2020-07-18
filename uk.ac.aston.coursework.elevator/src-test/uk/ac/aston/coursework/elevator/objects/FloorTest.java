package uk.ac.aston.coursework.elevator.objects;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aston.coursework.elevator.people.Employee;
import uk.ac.aston.coursework.elevator.people.Person;
import uk.ac.aston.coursework.elevator.simulation.Configuration;

public class FloorTest {
	private Building b;
	
	@Before
	public void setUp() {
		b = new Building(new Configuration(0, 0, 0, 5, 0.005, 0.002));
	}

	@Test
	public void testLeaveQueue() {
		Person p = new Employee(b, 0.008, new Random(10));
		b.enterFloor(0, p);
		b.getFloor(0).enQueue(p);
		assertEquals(p, b.getFloor(0).peek());
		b.leaveQueue(0, p);
		assertEquals(null, b.getFloor(0).peek());
	}

	
}
