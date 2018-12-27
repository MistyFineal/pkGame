package PKgame;

import com.sun.deploy.util.FXLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.util.ArrayList;

public class Controller {
    @FXML
    private Button ok_Button;
    @FXML
    private Label message;
    @FXML
    private TextField text_Field;
    @FXML
    private ListView listView;
    @FXML
    private PK_Client pkClient;

    private ObservableList<String> items;

    public void ok_Click() {
        if (text_Field.getText().equals("")) {
            message.setText("IPアドレスが未入力です. 接続するサーバのIPアドレスを入力し、ゲーム開始ボタンを押してください.");
        } else {
            listView = new ListView();
            items = FXCollections.observableArrayList();
            listView.setItems(items);
            pkClient = new PK_Client(text_Field.getText(), this);
            pkClient.start();
        }
    }

    public void sendMessageToGUI(String msg) {
        listView.getItems().add(msg);
    }

}
