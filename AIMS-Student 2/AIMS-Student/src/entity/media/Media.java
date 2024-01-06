package entity.media;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import utils.Utils;

/**
 * The general media class, for another media it can be done by inheriting this class
 * @author nguyenlm
 */
public class Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());

    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;

    public Media() throws SQLException{
        stm = AIMSDB.getConnection().createStatement();
    }

    public Media (int id, String title, String category, int value, int quantity, String type) throws SQLException{
        this.id = id;
        this.title = title;
        this.category = category;
        //this.price = price;
        this.value = value;
        this.quantity = quantity;
        this.type = type;

        //stm = AIMSDB.getConnection().createStatement();
    }

    public int getQuantity(){
        int updated_quantity;
		try {
			updated_quantity = getMediaById(id).quantity;
	        this.quantity = updated_quantity;
	        System.out.println(id + " " + updated_quantity);
		} finally  {
	        return this.quantity;
		}
    }

    public Media getMediaById(int id) throws SQLException{
        String sql = "SELECT * FROM Media WHERE id = " +  id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
		if(res.next()) {

            return new Media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"));
        }
        return null;
    }

    public List getAllMedia() throws SQLException{
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Media");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Media media = new Media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setValue(res.getInt("value"))
                .setType(res.getString("type"));
            medium.add(media);
        }
        return medium;
    }

    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        if (value instanceof String){
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " " 
                          + field + "=" + value + " " 
                          + "where id=" + id + ";");
    }
    public void updateMediaById() throws SQLException {
    	updateMediaFieldById("Media", this.id, "type", this.type);
    	updateMediaFieldById("Media", this.id, "category", this.category);
    	updateMediaFieldById("Media", this.id, "price", this.price);
    	updateMediaFieldById("Media", this.id, "quantity", this.quantity);
    	updateMediaFieldById("Media", this.id, "title", this.title);
    	updateMediaFieldById("Media", this.id, "value", this.value);
    	updateMediaFieldById("Media", this.id, "imageUrl", this.imageURL);
    }
    public void addMediaToDB() {
    	 String command = "INSERT INTO Media(id,type,category,price,quantity,title,value,imageUrl) VALUES(?,?,?,?,?,?,?,?)";
         try(PreparedStatement addstmt = AIMSDB.getConnection().prepareStatement(command)) {
             addstmt.setObject(1,id);
             addstmt.setObject(2,type);
             addstmt.setObject(3,category);
             addstmt.setObject(4,price);
             addstmt.setObject(5,quantity);
             addstmt.setObject(6,title);
             addstmt.setObject(7,value);
             addstmt.setObject(8,imageURL);

             addstmt.execute();
             System.out.println("Media added");
         } catch(Exception err) {
             System.out.println("An error has occurred.");
             System.out.println("See full details below.");
             err.printStackTrace();
         }
    }
    public void deleteMedia() {
    	String command = "DELETE FROM Media WHERE id = ?";
        try(PreparedStatement addstmt = AIMSDB.getConnection().prepareStatement(command)) {
            addstmt.setObject(1,id);
            addstmt.execute();
            System.out.println("Media deleted");
        } catch(Exception err) {
            System.out.println("An error has occurred.");
            System.out.println("See full details below.");
            err.printStackTrace();
        }
    }
    // getter and setter 
    public int getId() {
        return this.id;
    }

    private Media setId(int id){
        this.id = id;
        return this;
    }
    public Media setValue (int value) {
    	System.out.println("value is " + value);
    	this.value = value;
    	return this;
    }
    public String getTitle() {
        return this.title;
    }

    public Media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public Media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }
    public int getValue() {
    	System.out.println(this.value);
        return this.value;
    }

    public Media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public Media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }

    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public Media setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + id + "'" +
            ", title='" + title + "'" +
            ", category='" + category + "'" +
            ", price='" + price + "'" +
            ", quantity='" + quantity + "'" +
            ", type='" + type + "'" +
            ", imageURL='" + imageURL + "'" +
            "}";
    }    

}