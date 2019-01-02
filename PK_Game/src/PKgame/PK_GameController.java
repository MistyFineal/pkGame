package PKgame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class PK_GameController {

    private int p1P;//Player1の選んだ数字(1~6)
    private int p2P;//Player2の選んだ数字(1~6)
    private int P1score = 0;
    private int P2score = 0;
    private PK_ServerRunnable server;
    private Socket p1Socket;
    private Socket p2Socket;

    public PK_GameController(PK_ServerRunnable serv, Socket p1Socket, Socket p2Socket) {
        this.server = serv;
        this.p1Socket = p1Socket;
        this.p2Socket = p2Socket;

        int counter = 7;
        while (counter-- > 0) {
            processTurn();
        }

        /*サドンデスと勝敗*/
        boolean loop = true;
        while (loop) {

            if (P1score > P2score) {
                loop = false;
                System.out.println("Player1の勝利");
                try {
                    server.setSendMessageToP1("あなたの勝利です！");
                    server.setSendMessageToP2("あなたは敗北しました...");
                    server.getOos1().writeObject(server.getSendMessageToP1());
                    server.getOos2().writeObject(server.getSendMessageToP2());
                    server.getOos1().flush();
                    server.getOos2().flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (P2score > P1score) {
                loop = false;
                System.out.println("Player2の勝利");
                try {
                    server.setSendMessageToP1("あなたは敗北しました...");
                    server.setSendMessageToP2("あなたの勝利です！");
                    server.getOos1().writeObject(server.getSendMessageToP1());
                    server.getOos2().writeObject(server.getSendMessageToP2());
                    server.getOos1().flush();
                    server.getOos2().flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                processTurn();
            }
        }
    }


    public void processTurn() {
        try {
            server.setSendMessageToP1("シュートする場所を選択してください");
            server.setSendMessageToP2("防衛する場所を選択してください");
            server.getOos1().writeObject(server.getSendMessageToP1());
            server.getOos2().writeObject(server.getSendMessageToP2());
            server.getOos1().flush();
            server.getOos2().flush();
            server.setOis1(new ObjectInputStream(server.getSocket1().getInputStream()));
            server.setOis2(new ObjectInputStream(server.getSocket2().getInputStream()));
            p1P = (Integer)server.getOis1().readObject();
            p2P = (Integer)server.getOis2().readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (p1P != p2P) {       //p1(シュートする側)がp2(防衛する側)と違う数字を選んだとき
            P1score = P1score + 1;
        } else {
            P2score = P2score + 1;
        }

        //-------------------攻守交代--------------------//

        try {
            server.setSendMessageToP1("防衛する場所を選択してください");
            server.setSendMessageToP2("シュートする場所を選択してください");
            server.getOos1().writeObject(server.getSendMessageToP1());
            server.getOos2().writeObject(server.getSendMessageToP2());
            server.getOos1().flush();
            server.getOos2().flush();
            server.setOis1(new ObjectInputStream(server.getSocket1().getInputStream()));
            server.setOis2(new ObjectInputStream(server.getSocket2().getInputStream()));
            p1P = (Integer)server.getOis1().readObject();
            p2P = (Integer)server.getOis2().readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (p2P != p1P) {       //p1(防衛する側)がp2(シュートする側)と違う数字を選んだとき
            P2score = P2score + 1;
        } else {
            P1score = P1score + 1;
        }
    }

}