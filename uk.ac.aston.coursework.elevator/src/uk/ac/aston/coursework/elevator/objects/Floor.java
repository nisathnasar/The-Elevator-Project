package uk.ac.aston.coursework.elevator.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import uk.ac.aston.coursework.elevator.people.Person;

/**
 * This class represents a floor in a building.
 * 
 * @author Nisath
 *
 */
public class Floor {
	private final int floorNumber;
	private Button button;
	private List<Person> peopleInFloorList;
	private PersonQueue pQueue;

	/**
	 * Sets up a floor with a floor number. peopleInFloorList represents all the
	 * people in the floor. Stores some people from the HashSet to Priority Queue.
	 * 
	 * @param floorNumber the floor number of this floor.
	 */
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
		pQueue = new PersonQueue();
		button = new Button(this);
		peopleInFloorList = new LinkedList<Person>();
	}

	/**
	 * @return floor number of this floor.
	 */
	public int getFloorNumber() {
		return floorNumber;
	}

	/**
	 * Add person to floor.
	 * 
	 * @param person the person to enter floor.
	 */
	public void enterFloor(Person person) {
		boolean inFloor = false;
		Iterator<Person> iter = peopleInFloorList.iterator();
		while(iter.hasNext()) {
			if(iter.next().equals(person)) {
				inFloor = true;
			}
		}
		if(!inFloor) {
			peopleInFloorList.add(person);
		}
	}

	/**
	 * remove given from floor and Queue.
	 * 
	 * @param person the person to leave floor.
	 */
	public void leaveFloor(Person person) {
		Iterator<Person> iter = peopleInFloorList.iterator();
		while(iter.hasNext()) {
			if(iter.next().equals(person)) {
				iter.remove();
			}
		}
		pQueue.remove(person);
	}

	/**
	 * Add given person to queue.
	 * 
	 * @param person the person to be enqueued.
	 */
	public void enQueue(Person person) {
		pQueue.enQueue(person);
		enterFloor(person);
	}

	/**
	 * @return returns the first person in the queue.
	 */
	public Person peek() {
		return pQueue.peek();
	}

	/**
	 * @return returns and remove first person in queue.
	 */
	public Person deQueue() {
		Person p = pQueue.deQueue();
		peopleInFloorList.remove(p);
		return p;
	}

	/**
	 * @param person the person to be checked if they are in the queue.
	 * @return true if queue contains person.
	 */
	public boolean isInQueue(Person person) {
		return pQueue.inQueue(person);
	}

	/**
	 * remove person from queue regardless of position.
	 * 
	 * @param person the person to leave queue.
	 */
	public void leaveQueue(Person person) {
		pQueue.remove(person);
	}

	/**
	 * Method delegates pressUpButton method from Button.
	 */
	public void pressUpButton() {
		button.pressUpButton();
	}

	/**
	 * Method delegates pressDownButton method from Button.
	 */
	public void pressDownButton() {
		button.pressDownButton();
	}

	/**
	 * Method delegates unclickButton method from Button.
	 */
	public void unclickButton() {
		button.unclickButton();
	}

	/**
	 * Method delegates isElevatorRequired method from Button.
	 * 
	 * @return true if button.isElevatorRequired() is true.
	 */
	public boolean isElevatorRequired() {
		return button.isElevatorRequired();
	}

	/**
	 * @return Button object for this floor object.
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * Method delegates isElevatorRequiredAboveMeToGoUp method from Button.
	 * 
	 * @return return true if up button is pressed above this floor.
	 */
	public boolean isElevatorRequiredAboveMeToGoUp() {
		return button.isElevatorRequiredAboveMeToGoUp();
	}

	/**
	 * Method delegates isElevatorRequiredAboveMeToGoDown method from Button.
	 * 
	 * @return true if down button is pressed above this floor.
	 */
	public boolean isElevatorRequiredAboveMeToGoDown() {
		return button.isElevatorRequiredAboveMeToGoDown();
	}

	/**
	 * Method delegates isElevatorRequiredBelowMeToGoUp method from Button.
	 * 
	 * @return return true if up button is pressed below this floor.
	 */
	public boolean isElevatorRequiredBelowMeToGoUp() {
		return button.isElevatorRequiredBelowMeToGoUp();
	}

	/**
	 * Method delegates isElevatorRequiredBelowMeToGoDown method from Button.
	 * 
	 * @return true if down button is pressed below this floor.
	 */
	public boolean isElevatorRequiredBelowMeToGoDown() {
		return button.isElevatorRequiredBelowMeToGoDown();
	}

	/**
	 * Method returns a list of people in the queue. The person at index 0, if there
	 * is one, represents the person at front of the queue whereas the person at the
	 * size of the list represents the person at the end of queue.
	 * 
	 * @return a list of people in the queue in the same order of the queue.
	 */
	public List<Person> getQueueList() {
		return pQueue.getQueue();
	}

	/**
	 * Method returns a list of people that are in the floor but not in the queue.
	 * 
	 * @return a list of people that are in the floor but not in the queue.
	 */
	public List<Person> getPeopleThatAreNotInQueueButInFloor() {
		List<Person> peopleNotInQueueList = new ArrayList<Person>();
		Iterator<Person> iter = peopleInFloorList.iterator();
		while (iter.hasNext()) {
			Person p = iter.next();
			if (!pQueue.inQueue(p)) {
				peopleNotInQueueList.add(p);
			}
		}
		return peopleNotInQueueList;
	}
	
	@Override
	public String toString() {
		return peopleInFloorList.toString();
	}
}
