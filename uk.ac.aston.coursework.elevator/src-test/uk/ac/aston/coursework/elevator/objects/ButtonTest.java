package uk.ac.aston.coursework.elevator.objects;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import uk.ac.aston.coursework.elevator.simulation.Configuration;

public class ButtonTest {
	private Building building;
	@Before
	public void setUp() {
		building = new Building(new Configuration(0, 0, 0, 5, 0.001, 0.002));
	}
	
	@Test
	public void testIsElevatorRequired() {
		assertFalse(building.isElevatorRequired(0));
	}
/*
	@Test
	public void testIsElevatorRequiredAboveMeToGoUp() {
		building.getFloor(0).pressUpButton();*/
		/*
		for(int i = 0;i < 2; i++) {
			building.getElevator().goToFloor(2, false);
		}*//*
		assertFalse(building.getFloor(2).isElevatorRequiredAboveMeToGoUp());
		
		building.getFloor(3).pressUpButton();
		building.getFloor(4).pressUpButton();
		building.getFloor(5).pressUpButton();
		building.getFloor(6).pressUpButton();
		
		System.out.println(Elevator.CURRENTFLOOR);
		System.out.println(building.getFloor(2).getButton().isElevatorRequiredAboveMeToGoUp());
		
		assertTrue(building.getFloor(2).getButton().isElevatorRequiredAboveMeToGoUp());
		System.out.println(Elevator.CURRENTFLOOR);
	}
	*/

}
