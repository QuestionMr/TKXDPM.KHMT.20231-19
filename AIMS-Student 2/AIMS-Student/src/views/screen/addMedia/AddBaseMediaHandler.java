package views.screen.addMedia;

import java.io.IOException;
import java.sql.SQLException;

import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

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
	void submitData() throws SQLException{}
	
	@FXML
	void submit(MouseEvent event) throws SQLException {
		submitData();
		returnToHomeScreen();
	}
}
