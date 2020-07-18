package uk.ac.aston.coursework.elevator.people;

import java.util.Random;

import uk.ac.aston.coursework.elevator.objects.Building;
import uk.ac.aston.coursework.elevator.simulation.Simulation;

/**
 * This class represents a client. Clients do not change floors randomly and
 * they finish job between 10 to 20 minutes. The {@code Client} also leaves a
 * complaint if wait in a queue for more than or equal to 10 minutes and attempt
 * to leave building regardless of if their job is completed. They only go to
 * floor 0, 1, 2 or 3.
 * 
 * @author Nisath
 */
public class Client extends Person {
	private static int IDCOUNT, COMPLAINTCOUNT;
	private int maxPatienceMinutes = 10;
	private boolean alreadyExecuted;

	/**
	 * Constructs a Client making the person go inside building to floor 0 and have
	 * random target floor from 0 to 3. They are also given a unique ID to
	 * differentiate from other clients.
	 * 
	 * @param building the {@code Building} object that this person should spawn in.
	 * @param rnd      a {@code Random} object with a set seed.
	 */
	public Client(Building building, Random rnd) {
		super(building, rnd);
		ID = IDCOUNT + 1;
		IDCOUNT++;
		isInsideBuilding = true;
		building.enterFloor(0, this);
		setArrivalTick(Simulation.getTick());
		myFloor = rnd.nextInt(4);
		targetFloor = myFloor;
		alreadyExecuted = false;
	}

	/**
	 * Resets ID counter so that the ID starts from 0 again for a new Simulation. It
	 * also resets the count for the number of times clients have complained in the
	 * background.
	 */
	public static void resetIDCOUNT() {
		IDCOUNT = 0;
		COMPLAINTCOUNT = 0;
	}

	/**
	 * Get the number of times clients have complained.
	 * 
	 * @return the number of times clients have complained.
	 */
	public static int getComplaintCount() {
		return Client.COMPLAINTCOUNT;
	}

	/**
	 * 
	 * @param tick the {@code TICK} to set to {@code arrivalTick} field.
	 */
	protected void setArrivalTick(int tick) {
		arrivalTick = tick;
	}

	/**
	 * Finishes job between 60 to 20 ticks.
	 */
	@Override
	protected void finishJobWhenRequired() {
		/**
		 * 10 minutes is 60 tick and 20 minutes is 120 ticks (10 to 20 min) rnd
		 * generates 0 to 60, add 60 so minimum is 60 max is 120
		 */
		if ((Simulation.getTick() - arrivalTick) >= (rnd.nextInt(60) + 60) && !jobDone) {
			System.out.println(Simulation.getTick() + " " + this + " jobdone");
			jobDone = true;
		}
	}

	/**
	 * Client leaves a complaint if they have waited for more than or equal to 10
	 * minutes. In tick terms, that would be more than or equal to 60 ticks.
	 */
	@Override
	protected void complainAndLeaveIfNeeded() {
		if ((queueStartTick != null)) {
			if (Simulation.getTick() - (queueStartTick) >= (maxPatienceMinutes * 6) && isInQueue()) {
				if (!alreadyExecuted) {
					COMPLAINTCOUNT++;
					jobDone = true;
					System.out.println(this + " complained and set to leave building, " + "waited for 10 minutes.");
				}
				alreadyExecuted = true;
			}
		}
	}

	@Override
	protected void changeFloorWhenRequired() {
		/* do nothing */}

	@Override
	public int compareTo(Person p) {
		if (p instanceof Client && p.getQueueNumber() < this.queueNumber) {
			return 0;
		}
		return -1;
	}

	@Override
	public String toString() {
		return ID + "C(T:" + getTargetFloor() + ")";
	}
}
