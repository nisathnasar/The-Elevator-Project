package uk.ac.aston.coursework.elevator.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aston.coursework.elevator.people.Employee;
import uk.ac.aston.coursework.elevator.people.Goggles;
import uk.ac.aston.coursework.elevator.people.MaintenanceCrew;
import uk.ac.aston.coursework.elevator.people.Mugtome;
import uk.ac.aston.coursework.elevator.people.Person;
import uk.ac.aston.coursework.elevator.simulation.Configuration;
import uk.ac.aston.coursework.elevator.simulation.Simulation;

public class ElevatorTest {
	private Building b;
	private Elevator e;
	private int p;
	private Random rnd;
	
	@Before
	public void setUp() {
		p = 0;
		int seed = 5;
		b = new Building(new Configuration(0, 0, 0, seed, p, 0.002));
		rnd = new Random(seed);
		
		e = b.getElevator();
	}
	
	@Test
	public void testTargetFloorsGood() {
		Simulation sim = new Simulation(new Configuration(10, 5, 5, 100, 0.005, 0.01));
		for(int i = 0; i < 2000; i++) {
			sim.tickBuilding();
		}
	}
	
	@Test
	public void testContains() {
		Person person = new Goggles(b, p, rnd);
		
		b.getElevator().enterElevator(person);
		assertTrue(b.getElevator().contains(Goggles.class));
		assertFalse(b.getElevator().contains(Employee.class));
		b.leaveElevator(person);
		
		
		Person person2 = new Mugtome(b, p, rnd);
		b.getElevator().enterElevator(person2);
		assertTrue(b.getElevator().contains(Mugtome.class));
		
		
	}
	
	@Test
	public void testEnterElevator() {
		
		Person p1 = new Employee(b, p, rnd);
		Person p2 = new Mugtome(b, p, rnd);
		Person p3 = new Employee(b, p, rnd);
		Person p4 = new Mugtome(b, p, rnd);
		Person p5 = new Employee(b, p, rnd);
		Person p6 = new Employee(b, p, rnd);
		
		e.enterElevator(p1);
		e.enterElevator(p2);
		e.enterElevator(p3);
		e.enterElevator(p4);
		e.enterElevator(p5);
		
		assertTrue(e.contains(p1));
		assertTrue(e.contains(p2));
		assertTrue(e.contains(p3));
		assertTrue(e.contains(p4));
		assertFalse(e.contains(p6));
	}
	
	@Test
	public void testLeaveElevatorGood() {
		Person p1 = new Employee(b, p, rnd);
		Person p2 = new Mugtome(b, p, rnd);
		Person p3 = new Employee(b, p, rnd);	
		e.enterElevator(p1);
		e.enterElevator(p2);
		assertTrue(e.contains(p1));
		assertTrue(e.contains(p2));
		e.leaveElevator(p2);
		assertTrue(e.contains(p1));
		assertFalse(e.contains(p2));
		assertFalse(e.contains(p3));
	}
	
	@Test (expected=NullPointerException.class)
	public void testLeaveElevatorForNullPointer() {
		Person p1 = new Employee(b, p, rnd);
		e.leaveElevator(p1);
	}
	
	@Test
	public void testEnterElevatorForMCGood() {
		Person mc = new MaintenanceCrew(b, rnd);
		e.enterElevator(mc);
	}
	
	@Test
	public void testEnterElevatorForMCBad() {
		Person mc = new MaintenanceCrew(b, rnd);
		Person p1 = new Employee(b, p, rnd);
		e.enterElevator(p1);
		e.enterElevator(mc);
		assertFalse(e.contains(mc));
	}
	
	@Test
	public void testLeaveElevatorMCGood() {
		Person mc = new MaintenanceCrew(b, rnd);
		e.enterElevator(mc);
		assertTrue(e.contains(mc));
		e.leaveElevator(mc);
		assertFalse(e.contains(mc));
	}
	
	@Test 
	public void testGoToFloorUp() {
		for(int i = 0; i < 6; i++) {
			e.goToFloor(6, false);
		}
		assertEquals(6, e.getCurrentFloor());
	}
	
	@Test 
	public void testGoToFloorDown() {
		for(int i = 0; i < 6; i++) {
			e.goToFloor(6, false);
		}
		for(int i = 0; i < 6; i++) {
			e.goToFloor(0, false);
		}
		assertEquals(0, e.getCurrentFloor());
	}
	
}
