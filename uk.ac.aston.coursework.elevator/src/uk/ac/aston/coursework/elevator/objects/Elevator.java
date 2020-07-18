package uk.ac.aston.coursework.elevator.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import uk.ac.aston.coursework.elevator.people.MaintenanceCrew;
import uk.ac.aston.coursework.elevator.people.Person;
import uk.ac.aston.coursework.elevator.simulation.Simulation;
import uk.ac.aston.coursework.elevator.simulation.Tickable;

/**
 * This class represents an Elevator. It can move between floor 0 to n number of
 * floors, depending on the number of floors mentioned in
 * {@code Building.NUMOFFLOORS}, accepting requests from up and down buttons in
 * each floor.
 * 
 * @author Nisath
 *
 */
public class Elevator implements Tickable{
	private int capacity = 4;
	private int numOfPeopleInElevator;
	private int currentFloor;
	private int startTick;
	private boolean isDoorOpen = false;
	private final Direction direction;
	private final Building building;
	private List<Person> peopleInElevatorList;
	private List<Integer> targetFloors;

	/**
	 * This constructor constructs a Direction class which is used to represent the
	 * direction of the elevator. It also constructs an ArrayList of {@code Person}
	 * called peopleInElevatorList which is used to represent the people inside
	 * elevator. It also constructs an ArrayList of {@code Integer} called
	 * targetFloors. This is used to store the floor numbers that require the
	 * elevator.
	 * 
	 * @param building the building where this elevator should operate in.
	 */
	public Elevator(Building building) {
		this.building = building;

		currentFloor = 0;
		numOfPeopleInElevator = 0;

		direction = new Direction();
		direction.goStationary();

		//peopleInElevatorList = new ArrayList<Person>();
		
		peopleInElevatorList = new LinkedList<Person>();
		targetFloors = new ArrayList<Integer>();
	}

	/**
	 * This method checks if the person is in the elevator.
	 * 
	 * @param p the person that needs to be checked if is in elevator.
	 * @return true if the person exists in the elevator.
	 */
	public boolean contains(Person p) {
		if (peopleInElevatorList.contains(p)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param <T>  Any Class that extends Person
	 * @param type The class type.
	 * @return Returns true if elevator contains a person of {@code type}
	 */
	public <T extends Person> boolean contains(Class<T> type) {
		Iterator<Person> iter = peopleInElevatorList.iterator();
		while (iter.hasNext()) {
			if (type.isInstance(iter.next())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method adds Person p to elevator provided they have enough space. Their
	 * target floor is added to targetFloors ArrayList as if they pressed a button
	 * inside the elevator to go to a specific floor.
	 * 
	 * @param p the person that needs to enter the elevator.
	 */
	public void enterElevator(Person p) {
		if (numOfPeopleInElevator < 4 && capacity - numOfPeopleInElevator >= p.getRequiredSpace()) {
			for(Person p2 : peopleInElevatorList) {
				if(p.equals(p2)) {
					throw new NullPointerException("line 99 trying to enter elevator more than once");
				}
			}
			peopleInElevatorList.add(p);
			addToTargetFloors(p.getTargetFloor());
			sortTargetFloors();

			if (building.isElevatorRequired(currentFloor)) {
				building.unclickButton(currentFloor);
			}
			if (p instanceof MaintenanceCrew) {
				numOfPeopleInElevator += 4;
			} else {
				numOfPeopleInElevator++;
			}
		} else {
			isDoorOpen = false;
		}
		// System.out.println(this + " ---> Added " + p);
	}

	/**
	 * This method removes Person p from the elevator.
	 * 
	 * @param p the Person that needs to be removed from elevator.
	 */
	public void leaveElevator(Person p) {
		boolean removed = false;
		Iterator<Person> iter = peopleInElevatorList.iterator();
		while(iter.hasNext()) {
			if(iter.next().equals(p)) {
				iter.remove();
				removed = true;
			}
		}
		if(!removed) {
			throw new NullPointerException(p + " cannot leave elevator, is not in the elevator");
		}
		for (int i = 0; i < targetFloors.size(); i++) {
			if (p.getTargetFloor() == targetFloors.get(i)) {
				targetFloors.remove(i);
			}
		}

		sortTargetFloors();
		if (p instanceof MaintenanceCrew) {
			numOfPeopleInElevator -= 4;
		} else {
			numOfPeopleInElevator--;
		}
		// System.out.println(this + " ---> Removed " + p);
	}

	/* Getter Methods */

	/**
	 * Get the capacity of the elevator.
	 * 
	 * @return the capacity of the elevator.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Get the number of people in the elevator.
	 * 
	 * @return the number of people in the elevator.
	 */
	public int getNumOfPeopleInElevator() {
		return numOfPeopleInElevator;
	}

	/**
	 * Get the current floor of this Elevator.
	 * 
	 * @return the number of floor that this Elevator is on.
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}

	/**
	 * 
	 * @return true if door is open.
	 */
	public boolean isDoorOpen() {
		return isDoorOpen;
	}

	/**
	 * Get a list of all the people in elevator.
	 * 
	 * @return
	 */
	public List<Person> getPeopleInElevatorList() {
		return peopleInElevatorList;
	}

	/**
	 * @return floor requests in String.
	 */
	public String getFloorRequests() {
		return targetFloors.toString();
	}

	/**
	 * 
	 * @return string version of people in elevatorSet.
	 */
	public String getPeopleInElevator() {
		return peopleInElevatorList.toString();
	}

	/**
	 * @return direction object.
	 */
	public Direction getDirection() {
		return direction;
	}

	/* End of getter methods */
	/* Private helper Methods */

	/**
	 * This method moves up or down a floor one by one until floor is reached.
	 * 
	 * @param floor      is the floor that the elevator needs to go to.
	 * @param forStandby true if is going to rest, used for printing on console only
	 *                   once.
	 */
	public void goToFloor(int floor, boolean forStandby) {

		if (currentFloor != floor) {
			isDoorOpen = false; // close the door first and then move floor but in same tick
			if (currentFloor < floor) {
				currentFloor++;
			} else {
				currentFloor--;
			}
		}

		if (currentFloor == floor) {
			startTick = Simulation.getTick();	

			if (building.isElevatorRequired(currentFloor)) {
				building.unclickButton(floor);
			}
			for (int i = 0; i < targetFloors.size(); i++) {
				if (targetFloors.get(i) == floor) {
					targetFloors.remove(i);
				}
			}
			
			if(forStandby) {
				isDoorOpen = false;
			}
			else {
				isDoorOpen = true;
			}
		}
	}

	/**
	 * sort targetfloors
	 */
	private void sortTargetFloors() {
		if (targetFloors.size() > 1) {
			Collections.sort(targetFloors);
		}
	}

	/**
	 * 
	 * @param up true if direction is up
	 */
	private void addFloorsToTargetFloorIfRequired(boolean up) {
		boolean none = true;
		/**
		 * for each floor above elevator, if elevator is required wether to go up or
		 * down, add them to target floor.
		 */
		for (int i = currentFloor; i < Building.getNumOfFloors(); i++) {
			if (up) {
				if (building.isElevatorRequiredAboveMeToGoUp(i) || building.isElevatorRequiredAboveMeToGoDown(i)) {
					addToTargetFloors(i);
					/**
					 * if no one in elevator and elevator required above current floor to go down,
					 * change direction to go down.
					 */
					if (numOfPeopleInElevator == 0) {
						if (building.isElevatorRequiredAboveMeToGoDown(i)) {
							direction.goDown();
						}
					}
					none = false;
				}
			} else {
				if (building.isElevatorRequiredBelowMeToGoUp(i) || building.isElevatorRequiredBelowMeToGoDown(i)) {
					boolean ignore = false;
					for (int j = 0; j < targetFloors.size(); j++) {
						if (targetFloors.get(j) == i) {
							ignore = true;
						}
					}
					if (!ignore) {
						targetFloors.add(i);
						if (numOfPeopleInElevator == 0) {
							if (building.getFloor(i).getButton().wasUpPressed()) {
								direction.goUp();
							}
						}
					}
					none = false;
				}
			}
		}

		/**
		 * if elevator is not required be stationary
		 */
		if (none) {
			direction.goStationary();
		}
	}

	/**
	 * helper method
	 * 
	 * @param floor add floor number to the list of target floors.
	 */
	private void addToTargetFloors(int floor) {
		boolean exists = false;
		for (int j = 0; j < targetFloors.size(); j++) {
			if (targetFloors.get(j) == floor) {
				exists = true;
			}
		}
		if (!exists) {
			targetFloors.add(floor);
		}
	}

	/**
	 * Tick method contains all the logic for how elevator should act or react based
	 * on stimulus.
	 */
	public void tick() {

		if (isDoorOpen) {
			if (Simulation.getTick() - startTick >= 3) {
				isDoorOpen = false;
			}
		}
		if (!isDoorOpen) {
			// ISDOOROPEN = false;

			if (direction.isGoingUp() || direction.isStationary()) {
				addFloorsToTargetFloorIfRequired(true);
				sortTargetFloors();
			}

			if (direction.isGoingDown() || direction.isStationary()) {
				addFloorsToTargetFloorIfRequired(false);
				sortTargetFloors();
			}

			if (targetFloors.size() > 0) {
				if (targetFloors.get(0) > currentFloor) {
					direction.goUp();
					int i = targetFloors.get(0);
					if (building.getFloor(i).getButton().wasDownPressed()) {
						direction.goDown();
					}
				} else if (targetFloors.get(0) < currentFloor ) {
					direction.goDown();
					int i = targetFloors.get(0);
					if (building.getFloor(i).getButton().wasUpPressed()) {
						direction.goUp();
					}
				} else {
					direction.goStationary();
				}

				goToFloor(targetFloors.get(0), false);

			} else if (targetFloors.size() == 0) {
				goToFloor(0, true);
				direction.goStationary();
				isDoorOpen = false;
			}
		}
	}

	@Override
	public String toString() {
		return Simulation.getTick() + (isDoorOpen ? " [O]" : " [C]") + (direction.isGoingUp() ? " [ UP ] " : "")
				+ (direction.isGoingDown() ? " [DOWN] " : "") + (direction.isStationary() ? " [STAT] " : "")
				+ " Elevator(" + currentFloor + "): [" + peopleInElevatorList + "]" + "   " + targetFloors + "   ";
	}

}
