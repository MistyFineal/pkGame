package PKgame;

import com.sun.deploy.util.FXLoader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //protected static FXMLLoader loader = new FXMLLoader();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample_pkgame.fxml"));
        primaryStage.setTitle("PK GAME");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
