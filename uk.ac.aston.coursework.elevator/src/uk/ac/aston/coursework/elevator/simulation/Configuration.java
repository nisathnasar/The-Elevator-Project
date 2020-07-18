package uk.ac.aston.coursework.elevator.simulation;

/**
 * This class takes in all the required fields to run the Elevator simulation
 * and stores it. They can then be accessed via getter methods.
 * 
 * @author Nisath
 */
public class Configuration {
	private final int numOfEmployees;
	private final int numOfGoggles;
	private final int numOfMugtome;
	private final double p;
	private final double q;
	private final int seed;

	/**
	 * Stores all the fields in variables so they can be accessed.
	 * 
	 * @param numOfEmployees number of employees to generate.
	 * @param numOfGoggles   number of Goggles to generate.
	 * @param numOfMugtome   number of Mugtomes to generate.
	 * @param seed           the seed to be used for Random.
	 * @param p              the probability p value.
	 * @param q              the probability q value.
	 */
	public Configuration(int numOfEmployees, int numOfGoggles, int numOfMugtome, int seed, double p, double q) {
		this.numOfEmployees = numOfEmployees;
		this.numOfGoggles = numOfGoggles;
		this.numOfMugtome = numOfMugtome;
		this.seed = seed;
		this.p = p;
		this.q = q;
	}

	/**
	 * @return the number of employees.
	 */
	public int getEmployee() {
		return numOfEmployees;
	}

	/**
	 * @return the number of Goggles.
	 */
	public int getGoggles() {
		return numOfGoggles;
	}

	/**
	 * @return the number of Mutgomes
	 */
	public int getMugtome() {
		return numOfMugtome;
	}

	/**
	 * @return the probability of p.
	 */
	public double getP() {
		return p;
	}

	/**
	 * @return the probability of q.
	 */
	public double getQ() {
		return q;
	}

	/**
	 * @return the seed value.
	 */
	public int getSeed() {
		return seed;
	}
}
