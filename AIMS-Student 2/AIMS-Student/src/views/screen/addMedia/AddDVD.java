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
import entity.media.DVD;
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


public class AddDVD extends AddBaseMediaHandler implements Initializable {
	@FXML
	private Label screenTitle;

	@FXML
	private TextField fieldDirector;

	@FXML
	private TextField fieldStudio;
	
	@FXML
	private TextField fieldRuntime;

	@FXML
	private ComboBox<String> fieldGenre;
	
	@FXML
	private ComboBox<String> fieldDiscType;
	
	@FXML
	private ComboBox<String> fieldLanguage;
	
	@FXML
	private ComboBox<String> fieldSubtitles;
	
	@FXML
	private DatePicker fieldDate;

	public AddDVD(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.fieldGenre.getItems().addAll(Configs.DVD_TYPE);
		this.fieldDiscType.getItems().addAll(Configs.DISC_TYPE);

		this.fieldLanguage.getItems().addAll(Configs.LANGUAGE);
		this.fieldSubtitles.getItems().addAll(Configs.LANGUAGE);

		isEdit = false;
	}
	
	
	public void fillEditField(Media m) throws SQLException {
		super.fillEditField(m);
		DVD dvd = new DVD();
		dvd = (DVD) dvd.getMediaById(m.getId());
		fieldDirector.setText(dvd.getDirector());
		fieldStudio.setText(dvd.getStudio());

		fieldRuntime.setText(String.valueOf(dvd.getRuntime()));
		fieldGenre.setValue(dvd.getFilmType());
		fieldDiscType.setValue(dvd.getDiscType());
		
		fieldLanguage.setValue(dvd.getSubtitles());
		fieldSubtitles.setValue(dvd.getSubtitles());


	}
	void submitData() throws Exception{
		String director = fieldDirector.getText();
		int runtime = Integer.parseInt(fieldRuntime.getText());
		String genre = fieldGenre.getValue();
		String discType = fieldDiscType.getValue();
		String subtitles = fieldSubtitles.getValue();
		String studio = fieldStudio.getText();

		Date pdate = Date.from(fieldDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		if (Utils.checkEmpty(director) || Utils.checkEmpty(subtitles) 
				|| Utils.checkEmpty(discType) || Utils.checkEmpty(studio)) throw new Exception();

		DVD dvd;
		try {
			dvd = new DVD(tempMedia, discType, director,runtime, studio, subtitles, pdate, genre);
			System.out.println(isEdit);
			if (!isEdit) dvd.addMediaToDB();
			else dvd.updateMediaById();
		} catch (SQLException e) {
            PopupScreen.error(Configs.wrong_info);
			e.printStackTrace();
		}
		
	}

}
