package views.screen.media;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import controller.ViewCartController;
import entity.media.Book;
import entity.media.CD;
import entity.media.DVD;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.addMedia.AddMediaHandler;

public class ViewMedia extends BaseScreenHandler implements Initializable{

	@FXML
	private Text textId;
	
	@FXML
	private Text textTitle;

	@FXML
	private Text textType;

	@FXML
	private Text textValue;

	@FXML
	private Text textPrice;

	@FXML
	private Text textQuantity;

	@FXML
	private Text textImage;
	
	@FXML
	private ImageView imageS;
	@FXML
	private GridPane gridInfo;
	
	private Media currMedia;


	public ViewMedia(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public void setMediaInfo(Media m){
		File file = new File(m.getImageURL());
        Image image = new Image(file.toURI().toString());
		currMedia = m;
		textId.setText(String.valueOf(m.getId()));
		textTitle.setText(m.getTitle());
		textType.setText(m.getType());
		textValue.setText(String.valueOf(m.getValue()));
		textPrice.setText(String.valueOf(m.getPrice()));
		textQuantity.setText(m.getTitle());
        imageS.setImage(image);

		System.out.println(m.getType());
		Media temp = m;
		if (m.getType().equals("Book"))
			try {
				temp = new Book().getMediaById(m.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if (m.getType().equals("CD"))
			try {
				temp = new CD().getMediaById(m.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if (m.getType().equals("DVD"))
			try {
				temp = new DVD().getMediaById(m.getId());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		temp.setGridInfo(gridInfo);
	}
	@FXML
	void editMedia(MouseEvent event) throws SQLException, IOException {
        AddMediaHandler addMedia;
		 addMedia = new AddMediaHandler(this.stage, Configs.ADD_MEDIA_PATH);
         addMedia.setHomeScreenHandler(this.homeScreenHandler);
         addMedia.setBController(new ViewCartController());
         addMedia.setPreviousScreen(this);
         addMedia.fillEditField(currMedia);
         addMedia.show();
		//returnToPrevScreen();
	}

}
