package uk.ac.aston.coursework.elevator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.aston.coursework.elevator.gui.ConfigurationUIController;

/**
 * Launches a Configuration window where the user is required to set fields in
 * order to start the simulation.
 * 
 * @author Nisath
 *
 */
public class Launcher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			FXMLLoader l = new FXMLLoader();
			l.setLocation(getClass().getResource("gui/ConfigurationUI.fxml"));
			l.setController(new ConfigurationUIController());
			Parent parent = l.load();
			Scene scene = new Scene(parent, 400, 400);
			stage.setScene(scene);
			stage.setTitle("Elevator");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
