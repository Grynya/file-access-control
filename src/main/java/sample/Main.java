package sample;
import javafx.application.Application;
import javafx.stage.Stage;
import sample.controllers.PageLoader;

public class Main extends Application implements PageLoader{

    @Override
    public void start(Stage primaryStage) throws Exception{
        loadResource("/fxml/index.fxml", primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}