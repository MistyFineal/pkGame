package PKgame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private PK_ClientRunnable pkClient;

    private ObservableList<String> items;

    public void ok_Click() {
        if (text_Field.getText().equals("")) {
            message.setText("IPアドレスが未入力です. 接続するサーバのIPアドレスを入力し、ゲーム開始ボタンを押してください.");
        } else {
            items = FXCollections.observableArrayList();
            listView.setItems(items);
            pkClient = new PK_ClientRunnable(text_Field.getText(), this);
            //pkClient.start();
        }
    }

    public void sendMessageToGUI(String msg) {
        listView.getItems().add(msg);
    }
}
