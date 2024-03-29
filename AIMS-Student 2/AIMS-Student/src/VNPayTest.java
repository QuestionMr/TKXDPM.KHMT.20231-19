import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Configs;
import views.screen.payment.PaymentScreenHandler;

import javax.swing.text.html.StyleSheet;

import entity.media.Media;

//public class VNPayTest extends Application{
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			// initialize the scene
//			StackPane root = (StackPane) FXMLLoader.load(getClass().getResource(Configs.SPLASH_SCREEN_PATH));
//			Scene scene = new Scene(root);
//			primaryStage.setScene(scene);
//			primaryStage.show();
//
//			
//			PaymentScreenHandler payHandler = new PaymentScreenHandler(primaryStage, Configs.PAYMENT_SCREEN_PATH, null);
//			payHandler.show();
//	}catch (Exception e) {
//		e.printStackTrace();
//	}
//}
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//}
public class VNPayTest{
	public static void main(String[] args) {
		try {
			Media m = new Media(74, "yes", "classic", 30, 10, "cd");
			m.setMediaURL("assets/images/cd/cd9.jpg");
			m.addMediaToDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
