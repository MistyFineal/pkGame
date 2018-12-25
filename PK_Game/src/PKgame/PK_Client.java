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
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result;
        String result2;
        int inputage;
        Integer data;
        Integer data2;
        try {

            oos = new ObjectOutputStream(socket.getOutputStream());


            System.out.print("1 - 6から選んでください >");
            inputage = Integer.parseInt(reader.readLine());

            data = new Integer(inputage);


            oos.writeObject(data);
            oos.flush();


            ois = new ObjectInputStream(socket.getInputStream());
            result = (String) ois.readObject();
            System.out.println(result);


            while (true) {
                System.out.print(":1 - 6から選んでください >");
                inputage = Integer.parseInt(reader.readLine());
                data2 = new Integer(inputage);
                oos.writeObject(data2);
                oos.flush();
                result2 = (String) ois.readObject();
                System.out.println(result2);
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