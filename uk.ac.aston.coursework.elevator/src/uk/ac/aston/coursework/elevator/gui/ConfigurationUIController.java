package uk.ac.aston.coursework.elevator.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uk.ac.aston.coursework.elevator.simulation.Configuration;

/**
 * This is the controller for {@code ConfigurationUI} fxml file. It displays
 * text fields and requires user to input and press the run button at bottom.
 * That button will then open another window which will run the simulation based
 * on the fields provided in this class.
 * 
 * @author Nisath
 *
 */
public class ConfigurationUIController {
	@FXML
	public TextField employeesTextField, gogglesTextField, MugtomeTextField, seedTextField;
	@FXML
	public Slider pSlider, qSlider;
	private int numOfEmployees, numOfGoggles, numOfMugtome, seed;
	private double p, q;

	/**
	 * This method is called when the button labelled "run" is clicked. It takes in
	 * all the inputs from the fields, passes them onto a class called
	 * TextUIController and launches TextUI fxml. If an error is raised, an fxml
	 * window called {@code ErrorDialog} will be launched.
	 */
	public void runButtonOnAction() {
		try {
			this.numOfEmployees = convertToInt(employeesTextField.getText().trim());
			this.numOfGoggles = convertToInt(gogglesTextField.getText().trim());
			this.numOfMugtome = convertToInt(MugtomeTextField.getText().trim());
			this.seed = convertToInt(seedTextField.getText().trim());
			this.p = pSlider.getValue();
			this.q = qSlider.getValue();

			Configuration config = new Configuration(numOfEmployees, numOfGoggles, numOfMugtome, seed, p, q);

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("TextUI.fxml"));
			loader.setController(new TextUIController(config));
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.setTitle("Settings");
			stage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(root, 820, 550);
			stage.setScene(scene);
			stage.showAndWait();

		} catch (Exception ex) {
			launchErrorDialog();

		}
	}

	private void launchErrorDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ErrorDialog.fxml"));
			ErrorDialogController dialog = new ErrorDialogController();
			loader.setController(dialog);
			Parent root = loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			Scene scene = new Scene(root, 400, 300);
			stage.setScene(scene);
			stage.showAndWait();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private int convertToInt(String str) {
		return Integer.parseInt(str);
	}

}
