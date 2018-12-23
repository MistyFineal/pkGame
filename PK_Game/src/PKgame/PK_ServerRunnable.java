package PKgame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PK_ServerRunnable implements Runnable{
    private static ServerSocket server = null;

    private Socket socket1;     //p1
    private Socket socket2;     //p2
    private ObjectInputStream ois1;     //p1用のois
    private ObjectInputStream ois2;     //p2用のois
    private ObjectOutputStream oos1;    //p1用のoos
    private ObjectOutputStream oos2;    //p2用のoos
    private String sendMessageToP1;         //p1に送るメッセージ
    private String sendMessageToP2;         //p2に送るメッセージ

    private int threadNum;

    public void setSocket1(Socket socket) {
        this.socket1 = socket;
    }
    public Socket getSocket1() {
        return this.socket1;
    }
    public void setSocket2(Socket socket) { this.socket2 = socket; }
    public Socket getSocket2() { return this.socket2; }
    public void setThreadNum(int n) {
        this.threadNum = n;
    }
    public int getThreadNum() {
        return this.threadNum;
    }
    public void setOis1 (ObjectInputStream ois) { this.ois1 = ois; }
    public void setOis2 (ObjectInputStream ois) { this.ois2 = ois; }
    public ObjectInputStream getOis1 () { return this.ois1;}
    public ObjectInputStream getOis2 () { return this.ois2;}
    public void setOos1 (ObjectOutputStream oos) { this.oos1 = oos; }
    public void setOos2 (ObjectOutputStream oos) { this.oos2 = oos; }
    public ObjectOutputStream getOos1 () { return this.oos1;}
    public ObjectOutputStream getOos2 () { return this.oos2;}
    public void setSendMessageToP1(String s) { this.sendMessageToP1 = s;}
    public void setSendMessageToP2(String s) { this.sendMessageToP2 = s;}
    public String getSendMessageToP1() {return this.sendMessageToP1;}
    public String getSendMessageToP2() {return this.sendMessageToP2;}


    public static void main(String arg[]) {
        try {
            server = new ServerSocket(5572); // ポート番号を指定し、クライアントとの接続の準備を行う
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        int i = 0;
        boolean loop = true;
        PK_ServerRunnable pks = null;
        while(loop) {
            try {
                 pks = new PK_ServerRunnable();

                System.out.println("Thread-" + i + " : Player1の接続を待機しています");
                pks.setThreadNum(i);
                pks.setSocket1(server.accept());               // クライアントからの接続要求を待ち、 要求があればソケットを取得し接続を行う
                System.out.println("Thread-" + i + " : Player1の接続を確認しました");
                System.out.println("Thread-" + i + " : Player2の接続を待機しています");
                pks.setSocket2(server.accept());              // クライアントからの接続要求を待ち、 要求があればソケットを取得し接続を行う
                System.out.println("Thread-" + i + " : Player2の接続を確認しました");
                Thread thread = new Thread(pks, "th-" + String.valueOf(i));
                thread.start();
                i++;
            } catch (Exception e) {
                System.err.println("接続時にエラーが発生したのでプログラムを終了します");
                e.printStackTrace();
                if (pks != null) {
                    try {
                        if (pks.getSocket1() != null)   pks.getSocket1().close();
                        if (pks.getSocket2() != null)   pks.getSocket2().close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //サーバのスレッドの動作
    public void run() {
        try {
            Socket p1Socket = getSocket1();        //Player1のソケット
            Socket p2Socket = getSocket2();        //Player2のソケット
            int tNum = getThreadNum();

            System.out.println("Thread-" + tNum + " : プレイヤーが揃いました.  ゲームを開始します");
            setOos1(new ObjectOutputStream(p1Socket.getOutputStream()));
            setOos2(new ObjectOutputStream(p2Socket.getOutputStream()));
            setSendMessageToP1("プレイヤーが揃いました.  ゲームを開始します\n");
            setSendMessageToP2("プレイヤーが揃いました.  ゲームを開始します\n");
            getOos1().writeObject(getSendMessageToP1());
            getOos1().flush();
            getOos2().writeObject(getSendMessageToP2());
            getOos2().flush();
            setSendMessageToP1("あなたはPlayer1です.\nシュートする場所を選択してください\n");
            setSendMessageToP2("あなたはPlayer2です.\n防衛する場所を選択してください\n");
            getOos1().writeObject(getSendMessageToP1());
            getOos2().writeObject(getSendMessageToP2());
            getOos1().flush();
            getOos2().flush();
            setOis1(new ObjectInputStream(p1Socket.getInputStream()));
            setOis2(new ObjectInputStream(p2Socket.getInputStream()));

            boolean loop = true;
            while(loop) {

            }

            //終了処理
            getOos1().close();
            getOis1().close();
            getOos2().close();
            getOis2().close();
            getSocket1().close();
            getSocket2().close();
        } catch (Exception e) {
            System.err.println("通信時にエラーが発生したのでプログラムを終了します");
            e.printStackTrace();
        }
    }
}
