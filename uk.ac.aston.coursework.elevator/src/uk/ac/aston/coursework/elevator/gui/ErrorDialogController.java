package uk.ac.aston.coursework.elevator.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 * This class simply has one button that when pressed, will close the window.
 * 
 * @author Nisath
 *
 */
public class ErrorDialogController {
	@FXML
	public Label lblDescription;

	@FXML
	public void closePressed() {
		Scene scene = lblDescription.getScene();
		scene.getWindow().hide();

	}
}
