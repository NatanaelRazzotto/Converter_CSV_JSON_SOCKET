package InterfaceUIServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrincipalServer extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getResource("TelaPrincipalServer.fxml"));
			Scene scene = new Scene (root,553,199);
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
