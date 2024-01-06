package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.ViewCartException;
import controller.BaseController;
import controller.HomeController;
import controller.ViewCartController;
import entity.cart.Cart;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.addMedia.AddMediaHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.media.ViewMedia;

public class AdminHomeScreenHandler extends HomeScreenHandler{

    public static Logger LOGGER = Utils.getLogger(HomeScreenHandler.class.getName());
    private List<Media> deleteQueue;

    @FXML
    private SplitMenuButton splitMenuBtnSearch;
    
    @FXML
    private Button addMediaBtn;

    public AdminHomeScreenHandler(Stage stage, String screenPath) throws IOException{
        super(stage, screenPath);
    }
    @Override
    public void show() {
    	super.show();
    	if (!this.homeItems.isEmpty())reloadScreen();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	if (deleteQueue == null) deleteQueue = new ArrayList<Media>();
        setBController(new HomeController());
        try{
            List medium = getBController().getAllMedia();
            this.homeItems = new ArrayList<>();
            for (Object object : medium) {
                Media media = (Media)object;
                MediaHandler m1 = new MediaHandler(Configs.ADMIN_HOME_MEDIA_PATH, media, this);
           
                this.homeItems.add(m1);
            }
        }catch (SQLException | IOException e){
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }
        
        addMediaBtn.setOnMouseClicked(e -> {
            AddMediaHandler addMedia;
            // control coupling
            try {
                LOGGER.info("User clicked to add media");
                addMedia = new AddMediaHandler(this.stage, Configs.ADD_MEDIA_PATH);
                addMedia.setHomeScreenHandler(this);
                addMedia.setBController(new ViewCartController());
                addMedia.setPreviousScreen(this);
                addMedia.show();
            } catch (IOException e1) {
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });
        
        aimsImage.setOnMouseClicked(e -> {
            addMediaHome(this.homeItems);
        });
        
        setUpVboxes(this.homeItems);
        addMediaHome(this.homeItems);
        addMenuItem(0, "Book", splitMenuBtnSearch);
        addMenuItem(1, "DVD", splitMenuBtnSearch);
        addMenuItem(2, "CD", splitMenuBtnSearch);
    } 
    
    public void reloadScreen() {
    	deleteQueue.clear();
   	 try{
            List medium = getBController().getAllMedia();
            this.homeItems.clear();
            for (Object object : medium) {
                Media media = (Media)object;
                MediaHandler m1 = new MediaHandler(Configs.ADMIN_HOME_MEDIA_PATH, media, this);
                this.homeItems.add(m1);
            }
        }catch (SQLException | IOException e){
            LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
        }
       addMediaHome(homeItems);
   }
    
    public void viewDetailScreen(Media m) throws IOException {
    	ViewMedia viewMedia = new ViewMedia(this.stage, Configs.VIEW_PATH);
    	viewMedia.setHomeScreenHandler(this);
    	viewMedia.setBController(new ViewCartController());
    	viewMedia.setPreviousScreen(this);
    	viewMedia.setMediaInfo(m);
    	viewMedia.show();
    }
    public void bufferDeleteQueue(Media m, boolean selected) {
    	if (selected) deleteQueue.add(m);
//    	else deleteQueue.forEach(media ->{
//    		if (media.getId() == m.getId()) {
//    			deleteQueue.remove(media);
//    		}
//    	});
    	else {
    		for (int i = 0; i < deleteQueue.size(); i++) {
    			if (deleteQueue.get(i).getId() == m.getId()) deleteQueue.remove(i);
    		}
    	}
    }
    @FXML
    void deleteMediaInQueue() {
    	deleteQueue.forEach(media ->{
    		media.deleteMedia();
    		});
    	deleteQueue.clear();
    	reloadScreen();
    }
    
    public int getDeleteQueueSize() {
    	return deleteQueue.size();
    }
}

