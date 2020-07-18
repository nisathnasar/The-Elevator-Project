package uk.ac.aston.coursework.elevator.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import uk.ac.aston.coursework.elevator.simulation.Simulation;
import uk.ac.aston.coursework.elevator.simulation.Statistics;

/**
 * This class displays everyone's waiting times along with thier respective ID
 * in GUI.
 * 
 * @author Nisath
 *
 */
public class StatisticsUIController {
	private Statistics stats;
	@FXML
	public Label blankLabel, blankLabel2;

	/**
	 * 
	 * @param sim the simulation object that is to be used.
	 */
	public StatisticsUIController(Simulation sim) {
		stats = sim.getBuilding().getArrivalGenerator().getStatistics();
	}

	/**
	 * populates {@code blankLabel2} with all the people in the simulation and thier
	 * waiting Ticks.
	 */
	public void generateButton() {
		blankLabel.setText(
				"Average Waiting Time: " + String.format("%.2f", stats.getAverageWaitingTime() * 10 / 60) + " minutes");
		blankLabel2.setText(stats.getWaitingListMapString());
	}

}
