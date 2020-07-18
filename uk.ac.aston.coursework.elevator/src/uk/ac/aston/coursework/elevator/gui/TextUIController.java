package uk.ac.aston.coursework.elevator.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.ac.aston.coursework.elevator.objects.Building;
import uk.ac.aston.coursework.elevator.objects.Elevator;
import uk.ac.aston.coursework.elevator.people.Client;
import uk.ac.aston.coursework.elevator.simulation.ArrivalGenerator;
import uk.ac.aston.coursework.elevator.simulation.Configuration;
import uk.ac.aston.coursework.elevator.simulation.Simulation;
import uk.ac.aston.coursework.elevator.simulation.Statistics;

/**
 * This class is controller for TextUI FXML file. It constructs the simulation.
 * It updates and shows the state of building, including floors, queues,
 * elevator and the people in them.
 * 
 * @author Nisath
 *
 */
public class TextUIController {
	@FXML
	public Label currentFloorLabel, floorSixthLabel, floorFifthLabel, floorFourthLabel, floorThirdLabel,
			floorSecondLabel, floorFirstLabel, floorZeroLabel, queueSixthLabel, queueFifthLabel, queueFourthLabel,
			queueThirdLabel, queueSecondLabel, queueFirstLabel, queueZeroLabel, inElevatorLabel, tickLabel,
			complaintLabel, directionLabel, doorStatusLabel, clientsLabel, floorRequestsLabel, maintenanceCrewLabel,
			averageWaitingTimeLabel;
	private Simulation sim;
	private Building building;
	private ArrivalGenerator gen;
	private Elevator elevator;
	private Statistics stats;

	/**
	 * We construct a new simulation passing in Settings which contains the required
	 * fields to start the simulation.
	 * 
	 * @param config this is from the Configuration class where the fields are
	 *               saved.
	 */
	public TextUIController(Configuration config) {
		sim = new Simulation(config);
		building = sim.getBuilding();
		gen = building.getArrivalGenerator();
		elevator = building.getElevator();
		stats = building.getArrivalGenerator().getStatistics();
	}

	/**
	 * This method is called when a button is clicked. It ticks once.
	 */
	public void oneTickAction() {
		tickFor(1);
	}

	/**
	 * This method is called when a button is clicked. It ticks ten times.
	 */
	public void tenTickAction() {
		if (Simulation.getMaxTick() - Simulation.getTick() >= 10) {
			tickFor(10);
		}
	}

	/**
	 * This method is called when a button is clicked. It ticks one hundred times.
	 */
	public void hundredTickAction() {
		if (Simulation.getMaxTick() - Simulation.getTick() >= 100) {
			tickFor(100);
		}
	}

	/**
	 * This method is called when a button is clicked. It ticks five hundred times.
	 */
	public void fiveHundredTickAction() {
		if (Simulation.getMaxTick() - Simulation.getTick() >= 500) {
			tickFor(500);
		}
	}

	/**
	 * This method is called when a button is clicked. It ticks thousand times.
	 */
	public void thousandTickAction() {
		if (Simulation.getMaxTick() - Simulation.getTick() >= 1000) {
			tickFor(1000);
		}
	}

	/**
	 * This method launched {@code StatisticsUI} fxml window.
	 */
	public void statisticsButton() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("StatisticsUI.fxml"));
			loader.setController(new StatisticsUIController(sim));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Statitics");
			stage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(root, 800, 500);
			stage.setScene(scene);
			stage.showAndWait();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * This method updates all the labels in the top part of the window.
	 */
	private void updateAllMenuLabels() {
		floorRequestsLabel.setText(elevator.getFloorRequests());
		clientsLabel.setText(String.valueOf(gen.getClientList().size()));
		maintenanceCrewLabel.setText(String.valueOf(gen.getMaintenanceCrewList().size()));
		doorStatusLabel.setText(elevator.isDoorOpen() ? "OPEN" : "CLOSE");
		directionLabel.setText(elevator.getDirection().toString());
		currentFloorLabel.setText(String.valueOf(elevator.getCurrentFloor()));
		inElevatorLabel.setText(elevator.getPeopleInElevator());
		averageWaitingTimeLabel.setText(String.format("%.2f", stats.getAverageWaitingTime()) + " minutes");
		tickLabel.setText(String.valueOf(Simulation.getTick()));
		complaintLabel.setText(String.valueOf(Client.getComplaintCount()));
	}

	/**
	 * This method updates all the floors.
	 */
	private void updateAllFloors() {
		floorSixthLabel.setText(building.getFloor(6).toString());
		floorFifthLabel.setText(building.getFloor(5).toString());
		floorFourthLabel.setText(building.getFloor(4).toString());
		floorThirdLabel.setText(building.getFloor(3).toString());
		floorSecondLabel.setText(building.getFloor(2).toString());
		floorFirstLabel.setText(building.getFloor(1).toString());
		floorZeroLabel.setText(building.getFloor(0).toString());
	}

	/**
	 * This method updates all the queues.
	 */
	private void updateAllQueues() {
		queueSixthLabel.setText(building.getFloor(6).getQueueList().toString());
		queueFifthLabel.setText(building.getFloor(5).getQueueList().toString());
		queueFourthLabel.setText(building.getFloor(4).getQueueList().toString());
		queueThirdLabel.setText(building.getFloor(3).getQueueList().toString());
		queueSecondLabel.setText(building.getFloor(2).getQueueList().toString());
		queueFirstLabel.setText(building.getFloor(1).getQueueList().toString());
		queueZeroLabel.setText(building.getFloor(0).getQueueList().toString());
	}

	/**
	 * This method ticks Simulation at a given number of tick.
	 * 
	 * @param ticks the number of ticks the Simulation should tick for.
	 */
	private void tickFor(int ticks) {
		if (Simulation.getTick() < Simulation.getMaxTick()) {
			for (int i = 0; i < ticks; i++) {
				sim.tickBuilding();
			}
			updateAllMenuLabels();
			updateAllFloors();
			updateAllQueues();
		}
	}
}
