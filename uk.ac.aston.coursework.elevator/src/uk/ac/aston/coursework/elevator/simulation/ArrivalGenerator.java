package uk.ac.aston.coursework.elevator.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.aston.coursework.elevator.objects.Building;
import uk.ac.aston.coursework.elevator.people.Client;
import uk.ac.aston.coursework.elevator.people.Employee;
import uk.ac.aston.coursework.elevator.people.Goggles;
import uk.ac.aston.coursework.elevator.people.MaintenanceCrew;
import uk.ac.aston.coursework.elevator.people.Mugtome;
import uk.ac.aston.coursework.elevator.people.Person;

/**
 * This class takes in the fields from {@code Configuration}, constructs
 * required employees and developers, constructs clients and maintenance crew
 * based on probability and ticks them.
 * 
 * @author Nisath
 *
 */
public class ArrivalGenerator implements Tickable{

	private Person[] employeeList, gogglesList, mugtomeList;
	private List<Person> clientList, maintenanceCrewList, elevatorPeopleList;
	private final int numOfEmployees, numOfGoggles, numOfMugtomes;
	private final double p, q;
	private final double mcCrewProbability = 0.005;
	private final Random rnd;
	private final Building building;

	/**
	 * @param e        the number of employees to be generated.
	 * @param g        the number of Goggles to be generated.
	 * @param m        the number of Mugtome to be generated.
	 * @param seed     a seed for pseudorandom number generator in integer.
	 * @param p        p: probability of employees and developers changing floors.
	 * @param q        q: probability of generating client per tick.
	 * @param building the {@code Building} object where people should be generated.
	 */
	private ArrivalGenerator(int e, int g, int m, int seed, double p, double q, Building building) {
		this.numOfEmployees = e;
		this.numOfGoggles = g;
		this.numOfMugtomes = m;
		this.rnd = new Random(seed);
		this.p = p;
		this.q = q;
		this.building = building;
		createEmployees();
		createGoggles();
		createMugtomes();
		clientList = new ArrayList<Person>();
		maintenanceCrewList = new ArrayList<Person>();
		resetGenerator();
	}
	
	/**
	 * Builder constructs an ArrivalGenerator. The default value for all the fields except Building object is 0.
	 * Building object needs to provided.
	 * @author Nisath
	 *
	 */
	public static class Builder{
		private int numOfEmployees=0, numOfGoggles=0, numOfMugtomes=0, seed = 0;
		private double p=0, q=0;
		Building building;
		/**
		 * 
		 * @param n the number of employees to be generated.
		 * @return builder object
		 */
		public Builder employees(int n) {
			this.numOfEmployees = n;
			return this;
		}
		/**
		 * 
		 * @param n the number of Goggles to be generated.
		 * @return builder object
		 */
		public Builder goggles(int n) {
			this.numOfGoggles = n;
			return this;
		}
		/**
		 * 
		 * @param n the number of Mugtome to be generated.
		 * @return builder object
		 */
		public Builder mugtomes(int n) {
			this.numOfMugtomes = n;
			return this;
		}
		/**
		 * 
		 * @param n a seed for pseudorandom number generator in integer.
		 * @return builder object
		 */
		public Builder seed(int n) {
			this.seed = n;
			return this;
		}
		/**
		 * 
		 * @param n p: probability of employees and developers changing floors.
		 * @return builder object
		 */
		public Builder p(double n) {
			this.p = n;
			return this;
		}
		/**
		 * 
		 * @param n q: probability of generating client per tick.
		 * @return builder object
		 */
		public Builder q(double n) {
			this.q = n;
			return this;
		}
		/**
		 * 
		 * @param b the {@code Building} object where people should be generated.
		 * @return builder object
		 */
		public Builder building(Building b) {
			this.building = b;
			return this;
		}
		/**
		 * 
		 * @return builder object
		 */
		public ArrivalGenerator build() {
			if(building == null) {
				throw new NullPointerException("Have not passed a building object to ArrivalGenerato");
			}
			return new ArrivalGenerator(numOfEmployees, numOfGoggles, numOfMugtomes, seed, p, q, building);
		}
	}
	
	/**
	 * resets ID's
	 */
	private void resetGenerator() {
		Employee.resetIDCOUNT();
		Goggles.resetIDCOUNT();
		Mugtome.resetIDCOUNT();
		Client.resetIDCOUNT();
		MaintenanceCrew.resetIDCOUNT();
	}

	/* Getter and Setter Methods */

	/**
	 * Get a list of employees in the simulation.
	 * 
	 * @return Returns an {@code ArrayList<Person>} of employees that are in the
	 *         Arrival Generator, in {@code List<Person>} type.
	 */
	public List<Person> getEmployeeList() {
		List<Person> list = new ArrayList<Person>();
		for (int i = 0; i < employeeList.length; i++) {
			list.add(employeeList[i]);
		}
		return list;
	}

	/**
	 * Get a list of Goggles in the simulation.
	 * 
	 * @return Returns an {@code ArrayList<Person>} of Goggles that are in the
	 *         Arrival Generator, in {@code List<Person>} type.
	 */
	public List<Person> getGogglesList() {
		List<Person> list = new ArrayList<Person>();
		for (int i = 0; i < gogglesList.length; i++) {
			list.add(gogglesList[i]);
		}
		return list;
	}

	/**
	 * Get a list of Mugtome in the simulation.
	 * 
	 * @return Returns an {@code ArrayList<Person>} of Mugtome that are in the
	 *         Arrival Generator, in {@code List<Person>} type.
	 */
	public List<Person> getMugtomeList() {
		List<Person> list = new ArrayList<Person>();
		for (int i = 0; i < mugtomeList.length; i++) {
			list.add(mugtomeList[i]);
		}
		return list;
	}

	/**
	 * Get a list of clients in the simulation.
	 * 
	 * @return Returns an {@code ArrayList<Person>} of Clients that are in the
	 *         Arrival Generator, in {@code List<Person>} type.
	 */
	public List<Person> getClientList() {
		return clientList;
	}

	/**
	 * Get a list of maintenance crew in the simulation.
	 * 
	 * @return Returns an {@code ArrayList<Person>} of Maintenance Crews that are in
	 *         the Arrival Generator, in {@code List<Person>} type.
	 */
	public List<Person> getMaintenanceCrewList() {
		return maintenanceCrewList;
	}

	/**
	 * Creates a new Statistics object with current {@code ArrivalGenerator} object
	 * passed in to the constructor.
	 * 
	 * @return the statistic object with the current Arrival Generator object.
	 */
	public Statistics getStatistics() {
		return new Statistics(this);
	}

	/* End of Getter and Setter Methods */
	/* Private helper methods */

	private void createEmployees() {
		employeeList = new Person[numOfEmployees];
		for (int i = 0; i < numOfEmployees; i++) {
			employeeList[i] = new Employee(building, p, rnd);
		}
	}

	private void createGoggles() {
		gogglesList = new Person[numOfGoggles];
		for (int i = 0; i < numOfGoggles; i++) {
			gogglesList[i] = new Goggles(building, p, rnd);
		}
	}

	private void createMugtomes() {
		mugtomeList = new Person[numOfMugtomes];
		for (int i = 0; i < numOfMugtomes; i++) {
			mugtomeList[i] = new Mugtome(building, p, rnd);
		}
	}

	private void updateElevatorPeopleList() {
		elevatorPeopleList = building.getElevator().getPeopleInElevatorList();
	}

	private void tickPeopleInElevator() {
		List<Person> tickedPeople = new ArrayList<Person>();
		updateElevatorPeopleList();
		int size = elevatorPeopleList.size();
		try {
			for(int i = 0 ; i < size ; i++) {
				Person p = elevatorPeopleList.get(i);
				p.tick(); 
				tickedPeople.add(p);
			}
		}
		catch(IndexOutOfBoundsException e) {
			try {
				tickUnticked(tickedPeople);
			}
			catch(IndexOutOfBoundsException e2) {
				tickUnticked(tickedPeople);
			}
		}
	}
	
	private void tickUnticked(List<Person> tickedPeople) {
		updateElevatorPeopleList();
		int size = elevatorPeopleList.size();
		boolean toTick = true;
		for(int i = 0 ; i < size ; i++) {
			Person p = elevatorPeopleList.get(i);
			for(Person obj : tickedPeople) {
				if(obj.equals(p)) {
					toTick = false;
				}
			}
			if(toTick) p.tick(); 
		}
	}

	private void tickPeopleInQueues() {
		for (int i = 0; i < Building.getNumOfFloors(); i++) {
			List<Person> floorQueueList = building.getFloor(i).getQueueList();
			for (int j = 0; j < floorQueueList.size(); j++) {
				Person p = floorQueueList.get(j);
				p.tick();
			}
		}
	}

	private void tickOtherPeopleInFloors() {
		for (int i = 0; i < Building.getNumOfFloors(); i++) {
			List<Person> peopleInFloorList = building.getFloor(i).getPeopleThatAreNotInQueueButInFloor();
			for (int k = 0; k < peopleInFloorList.size(); k++) {
				Person p = peopleInFloorList.get(k);
				p.tick();
			}
		}
	}

	private void generateClients() {
		if (rnd.nextDouble() < q) {
			clientList.add(new Client(building, rnd));
		}
	}

	private void generateMaintenanceCrews() {
		if (rnd.nextDouble() < mcCrewProbability) {
			maintenanceCrewList.add(new MaintenanceCrew(building, rnd));
		}
	}

	/* End of Private helper methods */

	public void tick() {
		generateClients();
		generateMaintenanceCrews();
		tickPeopleInElevator();
		tickOtherPeopleInFloors();
		tickPeopleInQueues();
	}
}
