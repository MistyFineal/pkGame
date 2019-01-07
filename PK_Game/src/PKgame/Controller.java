package PKgame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
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
    @FXML private ImageView keeperView;
    @FXML private ImageView ballView1;
    @FXML private ImageView ballView2;
    @FXML private ImageView ballView3;
    @FXML private ImageView ballView4;
    @FXML private ImageView ballView5;
    @FXML private ImageView ballView6;
    @FXML private Circle c1;
    @FXML private Circle c2;
    @FXML private Circle c3;
    @FXML private Circle c4;
    @FXML private Circle c5;
    @FXML private Circle c6;
    @FXML private Label you;
    @FXML private Label yourPoint;
    @FXML private Label enemy;
    @FXML private Label enemyPoint;
    @FXML private ImageView keeperF1;
    @FXML private ImageView keeperF2;
    @FXML private ImageView keeperF3;
    @FXML private ImageView keeperF4;
    @FXML private ImageView keeperF5;
    @FXML private ImageView keeperF6;
    @FXML private ImageView keeperS1;
    @FXML private ImageView keeperS2;
    @FXML private ImageView keeperS3;
    @FXML private ImageView keeperS4;
    @FXML private ImageView keeperS5;
    @FXML private ImageView keeperS6;
    @FXML private Label turn;
    @FXML private Label turnNum;
    @FXML private Label sudden;


    private ObservableList<String> items;
    private int selectedNum;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pkClient = null;
        titleText.setVisible(true);
        goalView.setVisible(false);
        keeperView.setVisible(false);
        keepersViewFalse();
        ballViewFalse();
        sudden.setVisible(false);
        labelView(false);

        items = FXCollections.observableArrayList();
        listView.setItems(items);
        listView.getItems().add("接続するサーバのIPアドレスを入力し, ゲーム開始ボタンを押してください.");

        selectedNum = 0;
        setVisibleForCircles(false);
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
        if (1 <= selectedNum && selectedNum <= 6) {
            pkClient.setInput(selectedNum);
        } else {
            sendMessageToGUI("エリアを選択してください");
        }
    }

    public void sendMessageToGUI(String msg) {
        listView.getItems().add(msg);
        listView.scrollTo(listView.getItems().size()-1);
    }

    public void setKeeper(int n) {
        keeperView.setVisible(true);
        labelView(true);
    }


    public void selectC1() {
        selectedNum = 1;
        c1.setOpacity(0.7);
        c2.setOpacity(0.3);
        c3.setOpacity(0.3);
        c4.setOpacity(0.3);
        c5.setOpacity(0.3);
        c6.setOpacity(0.3);
    }
    public void selectC2() {
        selectedNum = 2;
        c1.setOpacity(0.3);
        c2.setOpacity(0.7);
        c3.setOpacity(0.3);
        c4.setOpacity(0.3);
        c5.setOpacity(0.3);
        c6.setOpacity(0.3);
    }
    public void selectC3() {
        selectedNum = 3;
        c1.setOpacity(0.3);
        c2.setOpacity(0.3);
        c3.setOpacity(0.7);
        c4.setOpacity(0.3);
        c5.setOpacity(0.3);
        c6.setOpacity(0.3);
    }
    public void selectC4() {
        selectedNum = 4;
        c1.setOpacity(0.3);
        c2.setOpacity(0.3);
        c3.setOpacity(0.3);
        c4.setOpacity(0.7);
        c5.setOpacity(0.3);
        c6.setOpacity(0.3);
    }
    public void selectC5() {
        selectedNum = 5;
        c1.setOpacity(0.3);
        c2.setOpacity(0.3);
        c3.setOpacity(0.3);
        c4.setOpacity(0.3);
        c5.setOpacity(0.7);
        c6.setOpacity(0.3);
    }
    public void selectC6() {
        selectedNum = 6;
        c1.setOpacity(0.3);
        c2.setOpacity(0.3);
        c3.setOpacity(0.3);
        c4.setOpacity(0.3);
        c5.setOpacity(0.3);
        c6.setOpacity(0.7);
    }

    public void setVisibleForCircles(boolean isVisible) {
        c1.setVisible(isVisible);
        c2.setVisible(isVisible);
        c3.setVisible(isVisible);
        c4.setVisible(isVisible);
        c5.setVisible(isVisible);
        c6.setVisible(isVisible);
    }

    public void selectClear() {
        selectedNum = 0;
        c1.setOpacity(0.3);
        c2.setOpacity(0.3);
        c3.setOpacity(0.3);
        c4.setOpacity(0.3);
        c5.setOpacity(0.3);
        c6.setOpacity(0.3);
        keepersViewFalse();
        ballViewFalse();
        keeperView.setVisible(true);
    }

    public void ballViewFalse() {
        ballView1.setVisible(false);
        ballView2.setVisible(false);
        ballView3.setVisible(false);
        ballView4.setVisible(false);
        ballView5.setVisible(false);
        ballView6.setVisible(false);
    }

    public void labelView(boolean isVisible) {
        you.setVisible(isVisible);
        yourPoint.setVisible(isVisible);
        enemy.setVisible(isVisible);
        enemyPoint.setVisible(isVisible);
        turn.setVisible(isVisible);
        turnNum.setVisible(isVisible);
    }

    public void setPointToLabel(boolean isAttack, int myNum, int eneNum) {
        if (myNum != eneNum) {
            if (isAttack) {
                int myP = Integer.parseInt(yourPoint.getText());
                myP++;
                yourPoint.setText(Integer.toString(myP));
            } else {
                int eneP = Integer.parseInt(enemyPoint.getText());
                eneP++;
                enemyPoint.setText(Integer.toString(eneP));
            }
        }
    }

    public void keepersViewFalse() {
        keeperF1.setVisible(false);
        keeperF2.setVisible(false);
        keeperF3.setVisible(false);
        keeperF4.setVisible(false);
        keeperF5.setVisible(false);
        keeperF6.setVisible(false);
        keeperS1.setVisible(false);
        keeperS2.setVisible(false);
        keeperS3.setVisible(false);
        keeperS4.setVisible(false);
        keeperS5.setVisible(false);
        keeperS6.setVisible(false);
    }

    public void shootedView(int attackNum, int defenseNum) {
        ballViewFalse();
        keeperView.setVisible(false);
        if (attackNum != defenseNum) {
            switch (attackNum) {
                case 1:
                    ballView1.setVisible(true);
                    break;
                case 2:
                    ballView2.setVisible(true);
                    break;
                case 3:
                    ballView3.setVisible(true);
                    break;
                case 4:
                    ballView4.setVisible(true);
                    break;
                case 5:
                    ballView5.setVisible(true);
                    break;
                case 6:
                    ballView6.setVisible(true);
                    break;

                default:
                    break;
            }
            switch (defenseNum) {
                case 1:
                    keeperF1.setVisible(true);
                    break;
                case 2:
                    keeperF2.setVisible(true);
                    break;
                case 3:
                    keeperF3.setVisible(true);
                    break;
                case 4:
                    keeperF4.setVisible(true);
                    break;
                case 5:
                    keeperF5.setVisible(true);
                    break;
                case 6:
                    keeperF6.setVisible(true);
                    break;

                default:
                    break;
            }

        } else {
            switch (defenseNum) {
                case 1:
                    keeperS1.setVisible(true);
                    break;
                case 2:
                    keeperS2.setVisible(true);
                    break;
                case 3:
                    keeperS3.setVisible(true);
                    break;
                case 4:
                    keeperS4.setVisible(true);
                    break;
                case 5:
                    keeperS5.setVisible(true);
                    break;
                case 6:
                    keeperS6.setVisible(true);
                    break;

                default:
                    break;
            }
        }

    }

    public void turnCountUp() {
        int tn = Integer.parseInt(turnNum.getText());
        tn++;
        turnNum.setText(Integer.toString(tn));
    }

    public void isSuddenDeathTrue() {
        sudden.setVisible(true);
    }


}
