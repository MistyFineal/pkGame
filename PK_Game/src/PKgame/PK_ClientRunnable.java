package PKgame;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class PK_ClientRunnable extends Task<Void> implements Runnable{
    private Socket soc;
    private Controller ctrl;
    private String message;
    private int input;


    public PK_ClientRunnable(Socket socket, Controller c) {
        this.soc = socket;
        this.ctrl = c;
        this.message = null;
        this.input = 0;
        Thread th = new Thread(this, "th-" + soc.getInetAddress().toString());
        th.start();
    }


    @Override
    public void run() {
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void call() throws Exception {
        BufferedReader reader = null;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            socket = this.soc;
            System.out.println("サーバへの接続成功");
            Platform.runLater(() -> ctrl.sendMessageToGUI("サーバへの接続成功"));
            System.out.println("他のプレイヤーを待機しています");
            Platform.runLater(() ->ctrl.sendMessageToGUI("他のプレイヤーを待機しています"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Integer send;
        String msgFromServ = "";

        try {
            ois = new ObjectInputStream((socket.getInputStream()));
            oos = new ObjectOutputStream(socket.getOutputStream());


            String ready = (String) ois.readObject();        //プレイヤーが揃ったときのメッセージ受信
            System.out.println(ready);
            Platform.runLater(() ->ctrl.sendMessageToGUI(ready));
            String ready2 = (String) ois.readObject();        //プレイヤーが揃ったときのメッセージ受信2
            System.out.println(ready2);
            Platform.runLater(() ->ctrl.sendMessageToGUI(ready2));

            Platform.runLater(() -> ctrl.setKeeper(3));         //キーパーを初期位置に表示
            Platform.runLater(() -> ctrl.setVisibleForCircles(true));


            boolean loop = true;
            int loopCounter = 0;
            while (loop) {
                loopCounter++;
                if (loopCounter % 2 != 0)   Platform.runLater(() -> ctrl.turnCountUp());
                if (loopCounter == 15) Platform.runLater(() -> ctrl.isSuddenDeathTrue());

                input = 0;
                msgFromServ = (String) ois.readObject();
                System.out.println(msgFromServ);
                String finalMsgFromServ = msgFromServ;
                Platform.runLater(() -> ctrl.sendMessageToGUI(finalMsgFromServ));


                while (!(1 <= input && input <= 6)) {
                    Thread.sleep(50);//選択が決定するまでなにもせずに待つ
                }


                send = new Integer(input);
                oos.writeObject(send);
                oos.flush();

                int finalInput = input;


                msgFromServ = (String) ois.readObject();
                System.out.println(msgFromServ);
                String finalMsgResultFromServ = msgFromServ;
                Platform.runLater(() -> ctrl.sendMessageToGUI(finalMsgResultFromServ));

                String numTemp = (String)ois.readObject();
                boolean isAttack;
                int enemySelectedNum = Integer.parseInt(numTemp);
                if (finalMsgFromServ.equals("シュートする場所を選択してください")) {
                    isAttack = true;
                    Platform.runLater(() -> ctrl.shootedView(finalInput, enemySelectedNum));
                } else {
                    isAttack = false;
                    Platform.runLater(() -> ctrl.shootedView(enemySelectedNum, finalInput));
                }

                Thread.sleep(1000);

                boolean isFinalAttack = isAttack;

                int mySelectedNum = finalInput;
                Platform.runLater(() -> ctrl.setPointToLabel(isFinalAttack, mySelectedNum, enemySelectedNum));

                if (loopCounter % 2 == 0) {
                    String isLoop = (String)ois.readObject();
                    if (isLoop.equals("false"))     loop = false;
                }

                Platform.runLater(() -> ctrl.selectClear());
            }

            String battleResult = (String)ois.readObject();
            System.out.println(battleResult);
            Platform.runLater(() -> ctrl.sendMessageToGUI(battleResult));


        } catch (java.net.SocketException soe) {
            soe.printStackTrace();
        } catch (IOException e) {
            System.err.println("エラー");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        if (oos != null) {

            try {
                oos.close();
            } catch (IOException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        if (ois != null) {

            try {
                ois.close();
            } catch (IOException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }

        if (socket != null) {

            try {
                socket.close();
            } catch (IOException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        return null;
    }


    public void setInput(int n) {
        this.input = n;
    }
}