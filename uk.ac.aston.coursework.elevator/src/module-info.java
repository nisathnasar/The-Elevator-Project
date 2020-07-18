module uk.ac.aston.coursework.elevator {
	requires junit;
	//requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive javafx.controls;
	requires javafx.base;
	
	
	exports uk.ac.aston.coursework.elevator;
	exports uk.ac.aston.coursework.elevator.gui;
	exports uk.ac.aston.coursework.elevator.simulation;
	exports uk.ac.aston.coursework.elevator.objects;
	exports uk.ac.aston.coursework.elevator.people;
	exports uk.ac.aston.coursework.elevator.study;
	opens uk.ac.aston.coursework.elevator to javafx.fxml;
	
}