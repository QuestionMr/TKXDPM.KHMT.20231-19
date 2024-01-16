package views.screen.addMedia;

import java.io.IOException;
import java.sql.SQLException;

import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class AddBaseMediaHandler extends BaseScreenHandler{

	protected Media tempMedia;
	protected boolean isEdit;

	public AddBaseMediaHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}
	public void setTempMedia(Media m) {
		tempMedia = m;
	}
	public void fillEditField(Media m) throws SQLException {
		isEdit = true;
	}
	void submitData() throws Exception{}
	
	@FXML
	void submit(MouseEvent event) throws IOException  {
			try {
				submitData();
				returnToHomeScreen();
			} catch (Exception e) {
	            PopupScreen.error(Configs.wrong_info);
				e.printStackTrace();
			}
		
	}
}
