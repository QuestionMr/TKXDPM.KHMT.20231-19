package views.screen.payment;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import controller.PaymentController;
import entity.cart.Cart;
import common.exception.PlaceOrderException;
import entity.invoice.Invoice;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import utils.Configs;
import utils.MyMap;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class PaymentScreenHandler extends BaseScreenHandler {

	@FXML
	private Button btnConfirmPayment;

	@FXML
	private ImageView loadingImage;
	
	@FXML
	private VBox webDisplayArea;

	private Invoice invoice;

	public PaymentScreenHandler(Stage stage, String screenPath, int amount, String contents) throws IOException {
		super(stage, screenPath);
		displayWebpage();
	}
	public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		displayWebpage();
//		btnConfirmPayment.setOnMouseClicked(e -> {
//			try {
//				confirmToPayOrder();
//				((PaymentController) getBController()).emptyCart();
//			} catch (Exception exp) {
//				System.out.println(exp.getStackTrace());
//			}
//		});
	}
	private void displayWebpage() {
		String contents = "pay order";
		//PaymentController ctrl = (PaymentController) getBController();
		PaymentController ctrl = new PaymentController();

		String connectResponse = ctrl.generateOrderPayment(invoice.getAmount() * 1000, contents);
		WebEngine engine;
		WebView wv=new WebView();
		engine=wv.getEngine();
		engine.load(Configs.vnp_PayUrl + "?" + connectResponse);
		engine.locationProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("Back url " + newValue);
			redirectToResultScreen(newValue);
		});
		webDisplayArea.getChildren().clear();
		webDisplayArea.getChildren().add(wv);
	}
	private void redirectToResultScreen(String url) {
		if (url.contains(Configs.vnp_ReturnUrl)) {
			try {
				String query = new URI(url).getQuery();
				System.out.println(query);
				setResult(query);
			} 
			catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	private void setResult(String query)throws IOException {
		Map<String,String> res = Configs.stringToMap(query);
		PaymentController ctrl = (PaymentController) getBController();
		Map<String, String> response = ctrl.generateResult(res);
		BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE") );
		resultScreen.setPreviousScreen(this);
		resultScreen.setHomeScreenHandler(homeScreenHandler);
		resultScreen.setScreenTitle("Result Screen");
		resultScreen.show();
	}
	@FXML
	private Label pageTitle;

	@FXML
	private TextField cardNumber;
	

	@FXML
	private TextField holderName;

	@FXML
	private TextField expirationDate;

	@FXML
	private TextField securityCode;

}