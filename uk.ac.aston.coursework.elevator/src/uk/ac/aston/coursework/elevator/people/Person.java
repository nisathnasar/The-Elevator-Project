package uk.ac.aston.coursework.elevator.people;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.aston.coursework.elevator.objects.Building;
import uk.ac.aston.coursework.elevator.objects.Elevator;
import uk.ac.aston.coursework.elevator.simulation.Simulation;
import uk.ac.aston.coursework.elevator.simulation.Tickable;

/**
 * This abstract {@code Person} class has fields and methods required to
 * construct a person. Its tick method does all the basic things a person does
 * including but not limited to using queue and elevator to go to a
 * {@code targetFloor}. It accomodates special cases that a subtype may need
 * such as the need to go to specific range of floors, need to complain and
 * leave building at specific {@code TICK}, the ability to change floors and the
 * ability to finish job and leave. It is also hardcoded so that subclass
 * {@code Goggles} and {@code Mugtome} do not enter elevator when the other is
 * already in elevator.
 * 
 * @author Nisath
 *
 */
public abstract class Person implements Comparable<Person>, Tickable {
	protected int currentFloor;
	protected int myFloor;
	protected int requiredSpace;
	protected int targetFloor;
	protected int arrivalTick;
	protected int ID;
	protected int queueNumber = 0;
	protected double rndValue;
	protected double probabilityP;
	protected boolean isInsideBuilding = false;
	protected boolean jobDone;
	protected List<Integer> waitinDataList;
	protected Integer queueStartTick;
	protected Building building;
	protected Elevator elevator;
	protected Random rnd;

	/**
	 * Constructor sets person inside building and sets appropriate fields for a
	 * person
	 * 
	 * @param building the {@code Building} object that this person should spawn in.
	 * @param rnd      a {@code Random} object with a set seed.
	 */
	public Person(Building building, Random rnd) {
		this.building = building;
		elevator = building.getElevator();
		isInsideBuilding = true;
		jobDone = false;
		this.rnd = rnd;
		requiredSpace = 1;
		waitinDataList = new ArrayList<Integer>();
		arrivalTick = Simulation.getTick();
	}

	/* getter and setter methods */

	/**
	 * Set the queue number. (1 being the first and 0 is not in the queue)
	 * 
	 * @param n the unique queue number describing when this person came into queue
	 *          compared to others.
	 */
	public void setQueueNumber(int ticket) {
		queueNumber = ticket;
	}

	/**
	 * Get the queue number. (1 being the first and 0 is not in the queue)
	 * 
	 * @return the queue number of this person, describing when this person came
	 *         into queue compared to others.
	 */
	protected int getQueueNumber() {
		return queueNumber;
	}

	/**
	 * Get the target floor of this person.
	 * 
	 * @return the current target floor of this person.
	 */
	public int getTargetFloor() {
		return targetFloor;
	}
	
	public void setToLeaveBuilding() {
		isInsideBuilding = false;
	}

	/**
	 * Set the current floor of this person.
	 * 
	 * @param currFloor is the current floor to be set.
	 */
	protected void setCurrentFloor(int currFloor) {
		currentFloor = currFloor;
	}

	/**
	 * Get a list of waiting ticks of all the waiting episodes.
	 * 
	 * @return a {@code List<Integer>} of the number of {@code TICK}s waited by this
	 *         person.
	 */
	public List<Integer> getWaitingDataList() {
		return waitinDataList;
	}

	/**
	 * 
	 * Any sub class can override this method and they will leave a complaint and
	 * leave building if when need be.
	 */
	protected void complainAndLeaveIfNeeded() {
	};

	/**
	 * 
	 * If the person needs to finish job during the simulation will do so within
	 * this method.
	 */
	protected abstract void finishJobWhenRequired();

	/**
	 * 
	 * If the person needs to change floor should do so in this method.
	 */
	protected abstract void changeFloorWhenRequired();

	/**
	 * 
	 * @return the unit of space this person takes up in the elevator.
	 */
	public int getRequiredSpace() {
		return requiredSpace;
	}

	/* helper methods */

	protected boolean isInQueue() {
		return building.isInQueue(currentFloor, this);
	}

	private boolean isInElevator() {
		return building.elevatorContains(this);
	}

	private boolean elevatorIsInCurrentFloor() {
		return elevator.getCurrentFloor() == currentFloor;
	}

	private boolean isInFrontOfQueue() {
		return building.getFloor(currentFloor).peek() == this;
	}

	private boolean isGoggles() {
		return this instanceof Goggles;
	}

	private boolean isMugtome() {
		return this instanceof Mugtome;
	}

	private boolean elevatorHasEnoughSpace() {
		return elevator.getCapacity() - elevator.getNumOfPeopleInElevator() >= requiredSpace;
	}

	private void leaveFloor() {
		building.getFloor(currentFloor).leaveFloor(this);
	}

	private void enterElevator() {
		building.enterElevator(building.getFloor(currentFloor).deQueue());
	}

	private void enterElevatorAction() {
		enterElevator();
		leaveFloor();
		waitinDataList.add(Simulation.getTick() - queueStartTick);
		queueStartTick = null;
	}

	private void enQueue() {
		building.getFloor(currentFloor).enQueue(this);
	}

	private void leaveQueue() {
		building.leaveQueue(currentFloor, this);
	}

	private boolean isElevatorComing() {
		return building.isElevatorRequired(currentFloor);
	}

	private void enterElevatorDependingOnDirection() {
		if (((building.getElevator().getDirection().isGoingUp()) && targetFloor > currentFloor)
				|| ((building.getElevator().getDirection().isGoingDown()) && targetFloor < currentFloor)
				|| (building.getElevator().getDirection().isStationary())) {
			enterElevatorAction();
		}
	}

	private void leaveQueueIfARivalIsInElevator() {
		if ((isGoggles() && building.getElevator().contains(Mugtome.class))
				|| (isMugtome() && building.getElevator().contains(Goggles.class))) {
			leaveQueue();
			enQueue();
			System.out.println(this + " : 'A rival is in the elevator, re-entering queue");
		}
	}

	/**
	 * 
	 * Makes people leave building.
	 */
	private void leaveBuildingIfNeeded() {
		if (isInsideBuilding && jobDone) {
			if (currentFloor != 0) {
				targetFloor = 0;
			} else if (currentFloor == 0 && !isInElevator()) {
				targetFloor = 0;
				building.leaveFloor(0, this);
				isInsideBuilding = false;
				System.out.println(Simulation.getTick() + " " + this + " left buliding");
			}
		}
	}

	/**
	 * if elevator is in my current floor if I'm in front of the queue if less than
	 * 4 people in elevator leave queue and enter elevator and leave floor
	 */
	protected void goInsideElevatorIfNeeded() {
		if (elevatorIsInCurrentFloor() && isInFrontOfQueue() && elevatorHasEnoughSpace()) {
			if (!(isGoggles() && building.getElevator().contains(Mugtome.class))
					&& !(isMugtome() && building.getElevator().contains(Goggles.class))) {
				enterElevatorDependingOnDirection();
			} else {
				leaveQueueIfARivalIsInElevator();
			}
		}
	}

	/**
	 * if im not in the floor i need to be and im not in the queue and im not in the
	 * elevator then enter queue in my current floor
	 */
	private void enQueueIfNeeded() {
		if (targetFloor != currentFloor && !isInQueue() && !isInElevator()) {
			enQueue();
			queueStartTick = Simulation.getTick();
		}
	}

	/**
	 * if elevator is not in my current floor or if elevator in my current floor but
	 * door is closed and i am in front of the queue then press upbutton if i want
	 * to go any floors above or press down button if i want to go below
	 */
	private void pressButtonsIfNeeded() {
		if ((!elevatorIsInCurrentFloor() || (elevatorIsInCurrentFloor() && !elevator.isDoorOpen()))
				&& isInFrontOfQueue() && !isElevatorComing()) {
			if (isGoggles() && building.getElevator().contains(Mugtome.class)
					|| isMugtome() && building.getElevator().contains(Goggles.class)) {
				System.out.println("waiting until my rival leave the elevator");
			} else {
				if (targetFloor > currentFloor) {
					building.pressUpButton(currentFloor);
					System.out.println(Simulation.getTick() + " UP btn pressed by " + this);
				} else {
					building.pressDownButton(currentFloor);
					System.out.println(Simulation.getTick() + " DOWN btn pressed by " + this);
				}
			}
		}
	}

	/**
	 * if I'm in the elevator and if elevator is in my target floor then leave
	 * elevator.
	 */
	private void leaveElevatorIfNeeded() {
		if (isInElevator() && elevator.getCurrentFloor() == targetFloor) {
			building.leaveElevator(this);
			building.getFloor(elevator.getCurrentFloor()).enterFloor(this);
			setCurrentFloor(elevator.getCurrentFloor());
		}
	}

	private void leaveQueueIfNeeded() {
		if (isInQueue() && targetFloor == currentFloor) {
			leaveQueue();
			queueStartTick = null;
		}
	}

	private void changeFloorWhenNeeded() {
		if (!isInElevator() && !isInQueue()) {
			changeFloorWhenRequired();
		}
	}

	public void tick() {
		leaveBuildingIfNeeded();

		if (isInsideBuilding) {
			enQueueIfNeeded();
			pressButtonsIfNeeded();

			if (elevator.isDoorOpen()) {
				leaveElevatorIfNeeded();
				goInsideElevatorIfNeeded();
			}

			leaveQueueIfNeeded();
			complainAndLeaveIfNeeded();
			changeFloorWhenNeeded();
			finishJobWhenRequired();
		}
	}
}
