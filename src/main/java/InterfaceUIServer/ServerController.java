package InterfaceUIServer;

import com.razzotto.Worker.Server.AplicacaoServer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServerController {
    @FXML
    private Button btn_Start;
    @FXML////////////////////////////////////////////////////////////
    private void StartServer(ActionEvent event) {
    	try {
    		new AplicacaoServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
