import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
// import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;  // Use for accessing and showing images
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
// import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

// Although this class is known as the "View" class, it contains View code of MVC DP
public class View extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Image icon = new Image(this.class.getResource("./Assets/icon.ico").toExternalForm(), false);  
        primaryStage.setTitle("OS Process Scheduling Program");
        primaryStage.getIcons().add(new Image("file:./Assets/icon.png"));

        BorderPane borderPane = new BorderPane();  // Main borderpane used in the scene
        HBox hbox = new HBox(10);  // HBox witin BorderPane
        VBox vbox = new VBox(8);

        Text titleText = new Text("CPU Process Scheduler"); 
        Text secondaryText = new Text("Manually type in values or import from file"); 

        titleText.setStyle("-fx-font-weight: bold");
        titleText.setFont(new Font("Arial", 25));

        vbox.getChildren().addAll(titleText, secondaryText);

        hbox.getChildren().add(vbox);
        hbox.setAlignment(Pos.CENTER);
        borderPane.setTop(hbox);

        HBox settingsBar = new HBox(30);  // HBox which represents the settings bar

        settingsBar.getChildren().addAll();

        Scene scene = new Scene(borderPane, 1000, 700);  // Scene object
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}