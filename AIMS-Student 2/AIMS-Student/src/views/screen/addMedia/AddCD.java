package views.screen.addMedia;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import entity.media.Book;
import entity.media.CD;
import entity.media.Media;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import utils.Configs;
import utils.Utils;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

import java.util.Date;


public class AddCD extends AddBaseMediaHandler implements Initializable {
	@FXML
	private Label screenTitle;

	@FXML
	private TextField fieldArtist;

	@FXML
	private TextField fieldRecord;
	@FXML
	private ComboBox<String> fieldGenre;
	@FXML
	private DatePicker fieldDate;

	public AddCD(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.fieldGenre.getItems().addAll(Configs.CD_TYPE);
		isEdit = false;
	}
	
	
	public void fillEditField(Media m) throws SQLException {
		super.fillEditField(m);
		CD cd = new CD();
		cd = (CD) cd.getMediaById(m.getId());
		fieldArtist.setText(cd.getArtist());
		fieldRecord.setText(cd.getRecordLabel());
		fieldGenre.setValue(cd.getMusicType());
	}
	void submitData() throws Exception{
		String artist = fieldArtist.getText();
		String record = fieldRecord.getText();
		String genre = fieldGenre.getValue();
		Date pdate = Date.from(fieldDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		if (Utils.checkEmpty(artist) || Utils.checkEmpty(record) || Utils.checkEmpty(genre)) throw new Exception();

		CD cd;
		try {
			cd = new CD(tempMedia, artist, record, genre, pdate);
			System.out.println(isEdit);
			if (!isEdit) cd.addMediaToDB();
			else cd.updateMediaById();
		} catch (SQLException e) {
            PopupScreen.error(Configs.wrong_info);
			e.printStackTrace();
		}
		
	}

}
