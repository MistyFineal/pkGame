package PKgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class PK_Client {

    public static void main(String arg[]) {
        new PK_Client();
    }


    public PK_Client() {
        BufferedReader reader = null;
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Server name? >");
            String serverName = reader.readLine();
            socket = new Socket(serverName, 5572);
            System.out.println("クライアントからの接続成功");
            System.out.println("他のプレイヤーを待機しています");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer send;
        String recieve;
        int input;
        try {
            ois = new ObjectInputStream((socket.getInputStream()));
            oos = new ObjectOutputStream(socket.getOutputStream());

            String ready = (String)ois.readObject();        //プレイヤーが揃ったときのメッセージ受信
            System.out.println(ready);

            boolean loop = true;
            while (loop) {
                input = 0;
                recieve = (String)ois.readObject();
                System.out.println(recieve);
                while( ! (1 <= input && input <= 6) ) {
                    System.out.print(":1 - 6から選んでください >");
                    input = Integer.parseInt(reader.readLine());
                }
                send = new Integer(input);
                oos.writeObject(send);
                oos.flush();
            }
        }
        catch (java.net.SocketException soe) {
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
    }
}