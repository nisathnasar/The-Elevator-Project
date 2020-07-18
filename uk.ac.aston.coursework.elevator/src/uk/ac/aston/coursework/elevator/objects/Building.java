package uk.ac.aston.coursework.elevator.objects;

import uk.ac.aston.coursework.elevator.people.Person;
import uk.ac.aston.coursework.elevator.simulation.ArrivalGenerator;
import uk.ac.aston.coursework.elevator.simulation.Configuration;
import uk.ac.aston.coursework.elevator.simulation.Tickable;

/**
 * Represents a Building and contains an array of {@code Floor}, an
 * {@code Elevator} and an {@code ArrivalGenerator} that generates people.
 * 
 * @author Nisath
 *
 */
public class Building implements Tickable{
	private static int NUMOFFLOORS = 7;
	private int[] floorNumbers;
	private Elevator elevator;
	private Floor[] floors;
	private ArrivalGenerator gen;

	/**
	 * Constructor constructs an elevator, array of floors and an ArrivalGenerator.
	 * 
	 * @param config a Configuration object that contains all the required fields in
	 *               order to run the simulation.
	 */
	public Building(Configuration config) {
		this.elevator = new Elevator(this);
		setupFloors();
		gen = new ArrivalGenerator.Builder()
				.employees(config.getEmployee())
				.goggles(config.getGoggles())
				.mugtomes(config.getMugtome())
				.seed(config.getSeed())
				.p(config.getP())
				.q(config.getQ())
				.building(this).build();
	}

	/**
	 * @return Method returns the Arrival Generator object being used.
	 */
	public ArrivalGenerator getArrivalGenerator() {
		return gen;
	}

	/**
	 * 
	 * @return the number of floors that the Building contains.
	 */
	public static int getNumOfFloors() {
		return NUMOFFLOORS;
	}

	/**
	 * @param floor the floor number.
	 * @return the floor object from given floor number.
	 */
	public Floor getFloor(int floorNumber) {
		if (floorNumber < NUMOFFLOORS)
			return floors[floorNumber];
		else
			throw new NullPointerException("attempt to get Floor over 6: floor=" + floorNumber);
	}

	/**
	 * @return the {@code Elevator} object from this building.
	 */
	public Elevator getElevator() {
		return elevator;
	}

	/**
	 * Prints all the floors in console.
	 */
	public void showFloors() {
		for (int i = 0; i < floors.length; i++) {
			System.out.print("floor " + i + " ");
			System.out.println(floors[i].toString());
		}
	}

	/**
	 * Method constructs floors.
	 */
	private void setupFloors() {
		floorNumbers = new int[NUMOFFLOORS];

		for (int i = 0; i < NUMOFFLOORS; i++) {
			floorNumbers[i] = i;
		}

		floors = new Floor[floorNumbers.length];

		for (int i = 0; i < floorNumbers.length; i++) {
			floors[i] = new Floor(floorNumbers[i]);
		}
	}

	/**
	 * The person will officially enter floor. They can then do things such as enter
	 * queue and enter elevator.
	 * 
	 * @param floorNumber the floor number of the floor to enter.
	 * @param p           the person that is to enter.
	 */
	public void enterFloor(int floorNumber, Person p) {
		floors[floorNumber].enterFloor(p);
	}

	/**
	 * The person officially leaves floor. Normally because of entering elevator or
	 * leaving building.
	 * 
	 * @param floorNumber the floor number of the floor to leave.
	 * @param p           the person that is to leave.
	 */
	public void leaveFloor(int floorNumber, Person p) {
		floors[floorNumber].leaveFloor(p);
	}

	/**
	 * Makes the provided person leave the queue they are in.
	 * 
	 * @param floorNumber the floor number of where the queue is located
	 * @param p           the person that is to leave the queue.
	 */
	public void leaveQueue(int floorNumber, Person p) {
		floors[floorNumber].leaveQueue(p);
	}

	/**
	 * This method is used to check if button is pressed anywhere or if someone in
	 * elevator is going to the mentioned floor.
	 * 
	 * @param floorNumber the floor number that is to be checked.
	 * @return true if elevator is required to go to that floor.
	 */
	public boolean isElevatorRequired(int floorNumber) {
		return floors[floorNumber].isElevatorRequired();
	}

	/**
	 * Presses up button.
	 * 
	 * @param floorNumber the floor number of where the button is being pressed
	 *                    from.
	 */
	public void pressUpButton(int floorNumber) {
		floors[floorNumber].pressUpButton();
	}

	/**
	 * Presses down button. Method delegates pressDownButton method from Floor.
	 * 
	 * @param floorNumber the floor number of where the button is being pressed
	 *                    from.
	 */
	public void pressDownButton(int floorNumber) {
		floors[floorNumber].pressDownButton();
	}

	/**
	 * Unclicks button. Method delegates unclickButton method from Floor.
	 * 
	 * @param floorNumber the floor number of where the button is to be unclicked.
	 */
	public void unclickButton(int floorNumber) {
		floors[floorNumber].unclickButton();
	}

	/**
	 * Checks if provided person is in the queue from the floor of the floor number
	 * provided.
	 * 
	 * @param floorNumber the floor number of where the queue is location.
	 * @param p           the person that is to be checked.
	 * @return true if the provided person is in the queue from floor number
	 *         provided.
	 */
	public boolean isInQueue(int floorNumber, Person p) {
		return floors[floorNumber].isInQueue(p);
	}

	/**
	 * Checks if up button is pressed above elevator.
	 * 
	 * @param floorNumber the floor number of the elevator.
	 * @return true if up button is pressed above elevator.
	 */
	public boolean isElevatorRequiredAboveMeToGoUp(int floorNumber) {
		return floors[floorNumber].isElevatorRequiredAboveMeToGoUp();
	}

	/**
	 * Checks if down button is pressed above elevator.
	 * 
	 * @param floorNumber the floor number of the elevator.
	 * @return true if down button is pressed above elevator.
	 */
	public boolean isElevatorRequiredAboveMeToGoDown(int floorNumber) {
		return floors[floorNumber].isElevatorRequiredAboveMeToGoDown();
	}

	/**
	 * Checks if up button is pressed below elevator.
	 * 
	 * @param floorNumber the floor number of the elevator.
	 * @return true if up button is pressed below elevator.
	 */
	public boolean isElevatorRequiredBelowMeToGoUp(int floorNumber) {
		return floors[floorNumber].isElevatorRequiredBelowMeToGoUp();
	}

	/**
	 * Checks if down button is pressed below elevator.
	 * 
	 * @param floorNumber the floor number of the elevator.
	 * @return true if down button is pressed below elevator.
	 */
	public boolean isElevatorRequiredBelowMeToGoDown(int floorNumber) {
		return floors[floorNumber].isElevatorRequiredBelowMeToGoDown();
	}

	/**
	 * Make the provided person enter elevator.
	 * 
	 * @param p the person that is to enter elevator.
	 */
	public void enterElevator(Person p) {
		elevator.enterElevator(p);
	}

	/**
	 * Make the provided person leave elevator.
	 * 
	 * @param p the person that is to leave elevator.
	 */
	public void leaveElevator(Person p) {
		elevator.leaveElevator(p);
	}

	/**
	 * Check if the provided person is in the elevator.
	 * 
	 * @param p the person that is to be checked.
	 * @return true if the provided person is in the elevator.
	 */
	public boolean elevatorContains(Person p) {
		return elevator.contains(p);
	}

	/**
	 * Return the number of clients in building.
	 * 
	 * @return number of clients entered building so far.
	 */

	public int getNumOfClients() {
		return gen.getClientList().size();
	}

	/**
	 * Return the number of Maintenance Crew in the building.
	 * 
	 * @return number of maintenance crew entered building so far.
	 */
	public int getNumOfMC() {
		return gen.getMaintenanceCrewList().size();
	}

	/**
	 * Method ticks Elevator and Arrival Generator.
	 */
	public void tick() {
		gen.tick();
		elevator.tick();
	}
}
