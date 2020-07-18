package uk.ac.aston.coursework.elevator.people;

import java.util.Random;

import uk.ac.aston.coursework.elevator.objects.Building;

/**
 * This class represents Mugtome which extends Developer.
 * 
 * @author Nisath
 *
 */
public class Mugtome extends Developer {
	private static int IDCOUNT;

	/**
	 * Contructs a Mugtome with a unique ID to differentiate from other Mugtome.
	 * 
	 * @param building    the {@code Building} object that this person should spawn
	 *                    in.
	 * @param probability the probability of changing floor in {@code double}.
	 * @param rnd         a {@code Random} object with a set seed.
	 */
	public Mugtome(Building building, double probability, Random rnd) {
		super(building, rnd);
		ID = IDCOUNT + 1;
		IDCOUNT++;
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
	protected void changeFloorWhenRequired() {
		rndValue = rnd.nextDouble();
		if (rndValue < probabilityP) {
			targetFloor = rnd.nextInt(4) + 3;
			myFloor = targetFloor;
		}
	}

	@Override
	public int compareTo(Person o) {
		if (o instanceof Client) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return ID + "D-M(T:" + getTargetFloor() + ")";
	}
}
