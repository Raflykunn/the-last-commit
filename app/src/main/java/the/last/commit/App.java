package the.last.commit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import the.last.commit.utils.DatabaseConnection;
import the.last.commit.views.LoginRegister;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        DatabaseConnection.initializeDatabase();

        LoginRegister loginRegisterView = new LoginRegister(primaryStage);
        Scene scene = loginRegisterView.createScene();

        primaryStage.setTitle("The Last Commit");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Otomatis posisikan ke tengah layar saat Scene berganti ukuran
        primaryStage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                javafx.application.Platform.runLater(primaryStage::centerOnScreen);
            }
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}