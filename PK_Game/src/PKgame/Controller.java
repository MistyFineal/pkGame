package PKgame;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private Label titleText;
    @FXML private Button ok_Button;
    @FXML private Button decision_Button;
    @FXML private TextField text_Field;
    @FXML private ListView listView;
    @FXML private PK_ClientRunnable pkClient;
    @FXML private ImageView goalView;

    private ObservableList<String> items;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pkClient = null;
        titleText.setVisible(true);
        goalView.setVisible(false);
        items = FXCollections.observableArrayList();
        listView.setItems(items);
        listView.getItems().add("接続するサーバのIPアドレスを入力し, ゲーム開始ボタンを押してください.");
    }

    public void ok_Click() {
        if (text_Field.getText().equals("")) {
            sendMessageToGUI("IPアドレスが未入力です. ");
            sendMessageToGUI("接続するサーバのIPアドレスを入力し, ゲーム開始ボタンを押してください.");
        } else {
            try {
                String serverName = text_Field.getText();
                Socket socket = new  Socket(serverName, 5572);

                pkClient = new PK_ClientRunnable(socket, this);
                ok_Button.setVisible(false);
                decision_Button.setVisible(true);
                titleText.setVisible(false);
                goalView.setVisible(true);
            } catch (Exception e) {
                sendMessageToGUI("接続に失敗しました.");
                e.printStackTrace();
            }
        }
    }

    public void decision_Click() {

    }

    public void sendMessageToGUI(String msg) {
        listView.getItems().add(msg);
        listView.scrollTo(listView.getItems().size()-1);
    }


}
