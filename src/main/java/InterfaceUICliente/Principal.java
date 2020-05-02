package InterfaceUICliente;

import com.razzotto.Controller.Controller;

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
			Scene scene = new Scene (root,553,274);
			//Controller controller = new Control
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}	
	public static void main(String[] args) {
		// new Controller(dirOriginario, dirDestinado);
		launch(args);

	}

}
