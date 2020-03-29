package com.razzotto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Principal extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
			Scene scene = new Scene (root,506,194);
			primaryStage.setScene(scene);
			primaryStage.show();
			/*BorderPane root = new BorderPane();
			Scene scene = new Scene (root,400,400);
			scene.getStylesheets().add(getClass().getResource("TelaPrincipal.fxml").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}	
	public static void main(String[] args) {
		launch(args);

	}

}
