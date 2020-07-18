package uk.ac.aston.coursework.elevator.people;

import java.util.Random;

import uk.ac.aston.coursework.elevator.objects.Building;
import uk.ac.aston.coursework.elevator.simulation.Simulation;

/**
 * This class represents a Maintenance Crew. They take up 4 unit of space unlike
 * normal people. They enter building at a probability of q and finish job after
 * 20 to 40 minutes. They will only go to floor 6.
 * 
 * @author Nisath
 *
 */
public class MaintenanceCrew extends Person {
	private static int IDCOUNT;
	private int arrivalTick;

	/**
	 * Constructs a Maintenance Crew. They are set inside building to floor 0, given
	 * a target floor of 6 and a unique ID to differentiate from other Maintenance
	 * Crew.
	 * 
	 * @param building the {@code Building} object that this person should spawn in.
	 * @param rnd      a {@code Random} object with a set seed.
	 */
	public MaintenanceCrew(Building building, Random rnd) {
		super(building, rnd);
		ID = IDCOUNT + 1;
		IDCOUNT++;
		isInsideBuilding = true;
		building.enterFloor(0, this);
		setArrivalTick(Simulation.getTick());
		myFloor = 6;
		targetFloor = myFloor;
		requiredSpace = 4;
		System.out.println(Simulation.getTick() + " " + this + " entered Building");
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
	 * 
	 * @param tick the {@code TICK} to set to {@code arrivalTick} field.
	 */
	private void setArrivalTick(int tick) {
		arrivalTick = tick;
	}

	@Override
	protected void finishJobWhenRequired() {
		if (((Simulation.getTick() - arrivalTick) > (rnd.nextInt(120) + 120)) && !jobDone) {
			jobDone = true;
			System.out.println(Simulation.getTick() + " " + this + " jobdone");
		}
	}

	@Override
	protected void changeFloorWhenRequired() {
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
		return ID + "MC(T:" + getTargetFloor() + ")";
	}
}
