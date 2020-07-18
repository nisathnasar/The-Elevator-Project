package uk.ac.aston.coursework.elevator.objects;

/**
 * This class allows to set direction to be going up, down or stationary. The
 * direction can be checked via getter methods.
 * 
 * @author Nisath
 *
 */
public class Direction {
	private boolean goingUp;
	private boolean stationary;

	/**
	 * Set direction to up.
	 */
	public void goUp() {
		goingUp = true;
		stationary = false;
	}

	/**
	 * Set direction to down.
	 */
	public void goDown() {
		goingUp = false;
		stationary = false;
	}

	/**
	 * Set direction to stationary.
	 */
	public void goStationary() {
		goingUp = false;
		stationary = true;
	}

	/**
	 * @return true if direction is up.
	 */
	public boolean isGoingUp() {
		return goingUp;
	}

	/**
	 * @return true if direction is down.
	 */
	public boolean isGoingDown() {
		return !goingUp && !stationary;
	}

	/**
	 * @return true if direction is stationary.
	 */
	public boolean isStationary() {
		return stationary;
	}

	@Override
	public String toString() {
		if (isGoingUp()) {
			return "[ UP ]";
		} else if (isGoingDown()) {
			return "[DOWN]";
		} else {
			return "[STAT]";
		}
	}
}
