package uk.ac.aston.coursework.elevator.objects;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Button class is used to store number of floor numbers that called the
 * elevator.
 * 
 * @author Nisath
 *
 */
public class Button {
	private int floorNumber;
	private HashSet<Integer> floorNumbers = new HashSet<Integer>();
	private HashSet<Integer> floorNumbersUp = new HashSet<Integer>();
	private HashSet<Integer> floorNumbersDown = new HashSet<Integer>();

	/**
	 * Stores floor's number.
	 * 
	 * @param floor the floor where this button is located.
	 */
	public Button(Floor floor) {
		floorNumber = floor.getFloorNumber();
	}

	/**
	 * Calls elevator. This should be pressed if the user requires the elevator to
	 * up.
	 */
	public void pressUpButton() {
		floorNumbersUp.add(floorNumber);
		floorNumbers.add(floorNumber);
	}

	/**
	 * Calls elevator. This should be pressed if the user requires the elevator to
	 * down.
	 */
	public void pressDownButton() {
		floorNumbersDown.add(floorNumber);
		floorNumbers.add(floorNumber);
	}

	/**
	 * @return returns true if button is pressed in this floor.
	 */
	public boolean isElevatorRequired() {
		if (floorNumbers.contains(floorNumber)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return returns true if up button is already pressed and is still unclicked.
	 */
	public boolean wasUpPressed() {
		return floorNumbersUp.contains(floorNumber);
	}

	/**
	 * @return returns true if down button is already pressed and is still
	 *         unclicked.
	 */
	public boolean wasDownPressed() {
		return floorNumbersDown.contains(floorNumber);
	}

	/**
	 * @return returns true if up button is pressed above elevator.
	 */
	public boolean isElevatorRequiredAboveMeToGoUp() {
		Iterator<Integer> iter = floorNumbersUp.iterator();
		while (iter.hasNext()) {
			int num = iter.next();
			System.out.println("cur:" + floorNumber + " req:" + num);
			if (num >= floorNumber) {
				return true;
			}

			System.out.println("cur:" + floorNumber + " req:" + num);
		}

		return false;
	}

	/**
	 * @return returns true if down button is pressed above elevator.
	 */
	public boolean isElevatorRequiredAboveMeToGoDown() {
		Iterator<Integer> iter = floorNumbersDown.iterator();
		while (iter.hasNext()) {
			if (iter.next() >= floorNumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return returns true if up button is pressed below elevator.
	 */
	public boolean isElevatorRequiredBelowMeToGoUp() {
		Iterator<Integer> iter = floorNumbersUp.iterator();
		while (iter.hasNext()) {
			if (iter.next() <= floorNumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return returns true if down button is pressed below elevator.
	 */
	public boolean isElevatorRequiredBelowMeToGoDown() {
		Iterator<Integer> iter = floorNumbersDown.iterator();
		while (iter.hasNext()) {
			if (iter.next() <= floorNumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Removes this floor from the need to go to this floor.
	 */
	public void unclickButton() {
		if (floorNumbers.contains(floorNumber)) {
			floorNumbers.remove(floorNumber);
		}
		if (floorNumbersUp.contains(floorNumber)) {
			floorNumbersUp.remove(floorNumber);
		}
		if (floorNumbersDown.contains(floorNumber)) {
			floorNumbersDown.remove(floorNumber);
		}
	}

	@Override
	public String toString() {
		String str = "[";
		Iterator<Integer> iter = floorNumbers.iterator();
		while (iter.hasNext()) {
			str += iter.next() + ", ";
		}
		str += "]";
		return str;
	}

}
