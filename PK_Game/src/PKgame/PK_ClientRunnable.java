package PKgame;

import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class PK_ClientRunnable extends Task<Void> implements Runnable{
    private Socket soc;
    private Controller ctrl;
    private String message;


    public PK_ClientRunnable(Socket socket, Controller c) {
        this.soc = socket;
        this.ctrl = c;
        this.message = null;
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
            //reader = new BufferedReader(new InputStreamReader(System.in));
            //System.out.print("Server name? >");
            //String serverName = reader.readLine();
            //String serverName = ipAddress;
            //socket = new Socket(serverName, 5572);
            socket = this.soc;
            System.out.println("サーバへの接続成功");
            Platform.runLater(() -> ctrl.sendMessageToGUI("サーバへの接続成功"));
            //ctrl.sendMessageToGUI("サーバへの接続成功");
            System.out.println("他のプレイヤーを待機しています");
            //ctrl.sendMessageToGUI("他のプレイヤーを待機しています");
            Platform.runLater(() ->ctrl.sendMessageToGUI("他のプレイヤーを待機しています"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        Integer send;
        String recieve;
        int input;
        try {
            ois = new ObjectInputStream((socket.getInputStream()));
            oos = new ObjectOutputStream(socket.getOutputStream());


            String ready = (String) ois.readObject();        //プレイヤーが揃ったときのメッセージ受信
            System.out.println(ready);
            Platform.runLater(() ->ctrl.sendMessageToGUI(ready));
            String ready2 = (String) ois.readObject();        //プレイヤーが揃ったときのメッセージ受信
            System.out.println(ready2);
            Platform.runLater(() ->ctrl.sendMessageToGUI(ready2));

            boolean loop = true;
            while (loop) {
                input = 0;
                recieve = (String) ois.readObject();
                System.out.println(recieve);
                while (!(1 <= input && input <= 6)) {
                    System.out.print("1 - 6から選んでください >");
                    input = Integer.parseInt(reader.readLine());
                }
                send = new Integer(input);
                oos.writeObject(send);
                oos.flush();
            }
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

}