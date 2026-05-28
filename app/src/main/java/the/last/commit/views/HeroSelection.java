package the.last.commit.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import the.last.commit.models.Hero;
import the.last.commit.models.HeroFactory;
import the.last.commit.models.User;
import the.last.commit.utils.DatabaseConnection;

public class HeroSelection {
    private final Stage stage;
    private final User user;
    private Label messageLabel;

    public HeroSelection(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #0f172a;");

        Label title = new Label("PILIH HERO");
        title.setFont(Font.font("Monospace", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#e94560"));

        Label subtitle = new Label("Pilih salah satu hero untuk memulai petualangan.");
        subtitle.setFont(Font.font("Monospace", 14));
        subtitle.setTextFill(Color.web("#d0d7ff"));

        messageLabel = new Label("");
        messageLabel.setFont(Font.font("Monospace", 12));
        messageLabel.setTextFill(Color.web("#4ecca3"));

        Button katagiriBtn = createHeroButton("Katagiri Rafly", "katagiri");
        Button kyotakaBtn = createHeroButton("Andika Kiyotaka", "kyotaka");

        HBox heroChoices = new HBox(20, katagiriBtn, kyotakaBtn);
        heroChoices.setAlignment(Pos.CENTER);

        VBox content = new VBox(20, title, subtitle, heroChoices, messageLabel);
        content.setAlignment(Pos.CENTER);
        root.setCenter(content);

        return new Scene(root, 600, 550);
    }

    private Button createHeroButton(String heroName, String heroType) {
        Button button = new Button(heroName);
        button.setFont(Font.font("Monospace", FontWeight.BOLD, 14));
        button.setStyle("-fx-background-color: #1b1f3b; -fx-text-fill: white; -fx-padding: 18 30 18 30; -fx-border-color: #e94560; -fx-border-width: 2px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #28304d; -fx-text-fill: white; -fx-padding: 18 30 18 30; -fx-border-color: #e94560; -fx-border-width: 2px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #1b1f3b; -fx-text-fill: white; -fx-padding: 18 30 18 30; -fx-border-color: #e94560; -fx-border-width: 2px;"));
        button.setOnAction(e -> selectHero(heroName, heroType));
        return button;
    }

    private void selectHero(String heroName, String heroType) {
        Hero hero = HeroFactory.createHero(0, heroName, heroType);
        Hero savedHero = DatabaseConnection.createHeroSelection(user, hero);
        if (savedHero != null) {
            stage.setScene(new LobbyScene(stage, savedHero).getScene());
        } else {
            showMessage("⚠ Gagal menyimpan pilihan hero. Coba lagi.", true);
        }
    }

    private void showMessage(String text, boolean error) {
        messageLabel.setText(text);
        messageLabel.setTextFill(error ? Color.web("#ff6b6b") : Color.web("#4ecca3"));
    }
}
