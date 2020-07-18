package uk.ac.aston.coursework.elevator.people;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aston.coursework.elevator.objects.Building;
import uk.ac.aston.coursework.elevator.objects.PersonQueue;
import uk.ac.aston.coursework.elevator.simulation.Configuration;
import uk.ac.aston.coursework.elevator.simulation.Simulation;

public class PersonTest {
	Building b;
	int seed = 10;
	Random rnd = new Random(seed);
	
	@Before
	public void setup() {
		b = new Building(new Configuration(10, 5, 5, 100, 0.005, 0.002));
	}
	
	@Test
	public void testCompareTo() {
		
		double prob = 0.005;
		
		Person dm1 = new Mugtome(b, prob, rnd);
		
		Person c1 = new Client(b, rnd);
		Person e1 = new Employee(b, prob, rnd);
		Person c2 = new Client(b, rnd);
		Person dg1 = new Goggles(b, prob, rnd);
		Person mc1 = new MaintenanceCrew(b, rnd);
		Person e2 = new Employee(b, prob, rnd);
		Person c3 = new Client(b, rnd);
		Person e3 = new Employee(b, prob, rnd);
		Person c4 = new Client(b, rnd);
			
		PersonQueue pq = new PersonQueue();
		pq.enQueue(c1);
		pq.enQueue(e1);
		pq.enQueue(dm1); 
		pq.enQueue(dg1);
		pq.enQueue(c2);
		pq.enQueue(mc1);
		pq.enQueue(e2);
		pq.enQueue(c3);
		pq.enQueue(e3);
		pq.enQueue(c4);
		
		Person unknown1 = pq.deQueue();
		System.out.println(unknown1);
		
		Person unknown2 = pq.deQueue();
		System.out.println(unknown2);
		
		Person unknown3 = pq.deQueue();
		System.out.println(unknown3);
		
		System.out.println(pq.deQueue());
		System.out.println(pq.deQueue());
		System.out.println(pq.deQueue());
		System.out.println(pq.deQueue());
		System.out.println(pq.deQueue());
		
		Person unknown9 = pq.deQueue();
		System.out.println(unknown9);
		
		Person unknown10 = pq.deQueue();
		System.out.println(unknown10);
		
		assertEquals(c1, unknown1);
		assertEquals(c2, unknown2);
		assertEquals(c3, unknown3);
		assertEquals(e2, unknown9);
		assertEquals(e3, unknown10);
		assertNull(pq.deQueue());
	}
	
	@Test
	public void testComplaintCount() {
		Simulation sim = new Simulation(new Configuration(10, 5, 5, 100, 0.1, 0.01));
		for(int i = 0; i < 1000; i++) {
			sim.tickBuilding();
		}
		
		assertTrue(Client.getComplaintCount() > 0);
		System.out.println(Client.getComplaintCount());
		
	}
	
	@Test
	public void testTick() {
		for(int i = 0; i < 2000; i++) {
			b.tick();
		}
	}
	
	
	
}
