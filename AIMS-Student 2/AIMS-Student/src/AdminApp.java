import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Configs;
import views.screen.home.AdminHomeScreenHandler;
import views.screen.home.HomeScreenHandler;

public class AdminApp extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			// initialize the scene
			StackPane root = (StackPane) FXMLLoader.load(getClass().getResource(Configs.SPLASH_SCREEN_PATH));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

			
			AdminHomeScreenHandler homeHandler = new AdminHomeScreenHandler(primaryStage, Configs.ADMIN_HOME_PATH);
			homeHandler.setScreenTitle("Admin Home Screen");
			homeHandler.setImage();
			homeHandler.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}
