package views.screen.addMedia;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import controller.ViewCartController;
import entity.media.Media;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import utils.Configs;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class AddMediaHandler  extends BaseScreenHandler implements Initializable {
	@FXML
	private Label screenTitle;

	@FXML
	private TextField fieldID;

	@FXML
	private TextField fieldTitle;

	@FXML
	private TextField fieldValue;
	
	@FXML
	private TextField fieldQuantity;

	@FXML
	private TextField fieldCurrentPrice;
	
	@FXML
	private ImageView imageS;
	
	@FXML
	private Button btnFileChoose;

	@FXML
	private ComboBox<String> fieldType;
	private TextField[] textFields;
	private String textDirectory;
	private AddBaseMediaHandler addBaseMedia;
	private boolean isEdit;

	public AddMediaHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		isEdit = false;
		ExtensionFilter ex1 = new ExtensionFilter("PNG files", "*.png");
		ExtensionFilter ex2 = new ExtensionFilter("JPG files", "*.jpg");
		this.fieldType.getItems().addAll(Configs.TYPES);

		btnFileChoose.setOnMouseClicked(event -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(ex1, ex2);
				fileChooser.setTitle("Get Image");
				fileChooser.setInitialDirectory(new File("D:\\Uni Projects\\TKXDPM\\AIMS-git\\TKXDPM.KHMT.20231-19\\AIMS-Student 2\\AIMS-Student\\assets"));
				File selectedFile = fileChooser.showOpenDialog(stage);
				if (selectedFile != null) {
					System.out.println("Open File");
					System.out.println(selectedFile.getPath());
					File file = new File(selectedFile.getPath());
			        Image image = new Image(file.toURI().toString());
			        imageS.setImage(image);
			        textDirectory = selectedFile.getPath();
				}
		});
	}
	@FXML
	void continueToNextScreen(MouseEvent event) throws SQLException {
		try {
			submitData();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//returnToPrevScreen();
	}
	
	void submitData() throws SQLException, IOException {
		int id = 0;
		int v = 0;
		int cp = 0;
		int qt = 0;
		boolean r = false;
		try {
			id = Integer.parseInt(fieldID.getText());
			v = Integer.parseInt(fieldValue.getText());
			cp = Integer.parseInt(fieldCurrentPrice.getText());
			qt = Integer.parseInt(fieldQuantity.getText());
		} 
		catch (Exception e) {
			PopupScreen.error(Configs.wrong_info);
			e.printStackTrace();
			r = true;
		}    
		if (r) return;
		Media m = new Media(id, fieldTitle.getText(), "", v, qt, fieldType.getValue());
		m.setMediaURL(textDirectory);
		m.setPrice(cp);
		m.setValue(v);
		if (fieldType.getValue().equals("Book")) 
			addBaseMedia = new AddBook(this.stage, Configs.BOOK_PATH);
		else if (fieldType.getValue().equals("CD"))	
			addBaseMedia = new AddCD(this.stage, Configs.CD_PATH);
		else if (fieldType.getValue().equals("DVD"))	
			addBaseMedia = new AddDVD(this.stage, Configs.DVD_PATH);
		
		nextScreen(m);
		//m.addMediaToDB();
	}
	public void fillEditField(Media m) {
		isEdit = true;
		fieldID.setText(String.valueOf(m.getId()));
		fieldID.setEditable(false);
		fieldType.getEditor().setText(m.getCategory());
		fieldType.setValue(m.getCategory());
		fieldType.setEditable(false);
		fieldTitle.setText(m.getTitle());
		fieldValue.setText(String.valueOf(m.getValue()));
		fieldCurrentPrice.setText(String.valueOf(m.getPrice()));
		fieldQuantity.setText(String.valueOf(m.getQuantity()));
		
		File file = new File(m.getImageURL());
        Image image = new Image(file.toURI().toString());
        imageS.setImage(image);
        textDirectory = m.getImageURL();
	}
	private void nextScreen(Media m) throws IOException, SQLException {
		addBaseMedia.setHomeScreenHandler(this.homeScreenHandler);
		addBaseMedia.setBController(new ViewCartController());
		addBaseMedia.setPreviousScreen(this);
		addBaseMedia.setTempMedia(m);
		if (isEdit) addBaseMedia.fillEditField(m);
		addBaseMedia.show();
	}

}
