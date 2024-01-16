package views.screen.addMedia;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import entity.media.Book;
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


public class AddBook extends AddBaseMediaHandler implements Initializable {
	@FXML
	private Label screenTitle;

	@FXML
	private TextField fieldAuthor;

	@FXML
	private TextField fieldPages;

	@FXML
	private TextField fieldPublisher;

	@FXML
	private ComboBox<String> fieldCover;
	@FXML
	private ComboBox<String> fieldLanguage;
	@FXML
	private ComboBox<String> fieldCategory;
	@FXML
	private DatePicker fieldDate;

	public AddBook(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.fieldCover.getItems().addAll(Configs.COVERS);
		this.fieldLanguage.getItems().addAll(Configs.LANGUAGE);
		this.fieldCategory.getItems().addAll(Configs.CATEGORY);
		isEdit = false;
	}
	
	
	public void fillEditField(Media m) throws SQLException {
		super.fillEditField(m);
		Book b = new Book();
		b = (Book) b.getMediaById(m.getId());
		fieldAuthor.setText(b.getAuthor());
		fieldPages.setText(String.valueOf(b.getNumOfPages()));
		fieldPublisher.setText(String.valueOf(b.getPublisher()));
		fieldCover.setValue(b.getCoverType());
		fieldLanguage.setValue(b.getLanguage());
		fieldCategory.setValue(b.getBookCategory());
	}
	void submitData() throws Exception{
		String author="";
		String publisher="";
		String cover = "";
		Date pdate = new Date();
		
		String language = "";
		String category = "";
		int pages = 0;
		try {
			language = fieldLanguage.getValue();
			category = fieldCategory.getValue();
			pages = Integer.parseInt(fieldPages.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		author = fieldAuthor.getText();
		publisher = fieldPublisher.getText();
		cover = fieldCover.getValue();
		if (Utils.checkEmpty(author) || Utils.checkEmpty(publisher) || Utils.checkEmpty(cover)) throw new Exception();
		System.out.println(fieldAuthor.getText());
		pdate = Date.from(fieldDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		Book b;
		try {
			b = new Book(tempMedia, author, cover, publisher, pdate, pages, language, category);
			System.out.println(isEdit);
			if (!isEdit) b.addMediaToDB();
			else b.updateMediaById();
		} catch (SQLException e) {
            PopupScreen.error(Configs.wrong_info);
			e.printStackTrace();
		}
		
	}

}
