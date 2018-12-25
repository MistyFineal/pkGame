package PKgame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Button ok_Button;
    @FXML
    private Label label;

    private PK_Client pkClient;

    public void ok_Click()
    {
        pkClient = new PK_Client();
//        label.setText("");
    }
}
