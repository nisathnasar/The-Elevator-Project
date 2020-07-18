package uk.ac.aston.coursework.elevator.people;

import java.util.Random;

import uk.ac.aston.coursework.elevator.objects.Building;

/**
 * This is a Developer abstract class. Developers of different brands can extend
 * from this class. They will only go to floors; 3, 4, 5 or 6
 * 
 * @author Nisath
 *
 */
public abstract class Developer extends Person implements Comparable<Person> {

	/**
	 * Constructor makes the Developer go inside building to floor 0 and is 
	 * given a target floor between 3 to 6.
	 * 
	 * @param building the {@code Building} object that this person should spawn in.
	 * @param rnd      a {@code Random} object with a set seed.
	 */
	public Developer(Building building, Random rnd) {
		super(building, rnd);
		building.enterFloor(0, this);
		targetFloor = rnd.nextInt(4) + 3;
	}

	@Override
	public void finishJobWhenRequired() {
		/* do nothing */}
}
