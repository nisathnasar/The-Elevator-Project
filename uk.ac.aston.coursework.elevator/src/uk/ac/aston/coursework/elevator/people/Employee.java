package uk.ac.aston.coursework.elevator.people;

import java.util.Random;

import uk.ac.aston.coursework.elevator.objects.Building;

/**
 * This class represents an employee. Employees will be present throughout the
 * simulation. They will go to any floor 0 to 6 and will change floors as
 * randomly.
 * 
 * @author Nisath
 *
 */
public class Employee extends Person {
	private static int IDCOUNT;

	/**
	 * Constructs an employee, making the person go inside building to floor 0 and
	 * have a random target floor from floor 0 to 6. They are given a unique ID to
	 * differentiate from other employees.
	 * 
	 * @param building    the {@code Building} object that this person should spawn
	 *                    in.
	 * @param probability the probability of this employee changing floor in
	 *                    {@code double}.
	 * @param rnd         a {@code Random} object with a set seed.
	 */
	public Employee(Building building, double probability, Random rnd) {
		super(building, rnd);
		ID = IDCOUNT + 1;
		IDCOUNT++;
		probabilityP = probability;
		isInsideBuilding = true;
		building.enterFloor(0, this);
		myFloor = rnd.nextInt(7);
		targetFloor = myFloor;
	}

	/**
	 * Resets ID counter so that the ID starts from 0 again for a new Simulation. It
	 * also resets the count for the number of times clients have complained in the
	 * background.
	 */
	public static void resetIDCOUNT() {
		IDCOUNT = 0;
	}

	/**
	 * This method changes target floor if random double is less than probability p.
	 */
	@Override
	protected void changeFloorWhenRequired() {
		rndValue = rnd.nextDouble();
		if (rndValue < probabilityP) {
			targetFloor = rnd.nextInt(7);
			myFloor = targetFloor;
		}
	}

	@Override
	public void finishJobWhenRequired() {
		/* do nothing */}

	@Override
	public int compareTo(Person o) {
		if (o instanceof Client) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return ID + "E(T:" + getTargetFloor() + ")";
	}

}
