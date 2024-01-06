package entity.media;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import entity.db.AIMSDB;

public class Book extends Media {

    String author;
    String coverType;
    String publisher;
    Date publishDate;
    int numOfPages;
    String language;
    String bookCategory;

    public Book() throws SQLException{

    }
    public Book(Media m, String author,
            String coverType, String publisher, Date publishDate, int numOfPages, String language,
            String bookCategory) throws SQLException {
    	this.id = m.getId();
    	this.title = m.getTitle();
    	this.category = m.getCategory();
    	this.price = m.getPrice();
    	this.value = m.getValue();

    	this.quantity = m.getQuantity();
    	this.type = m.getType();
    	this.imageURL = m.getImageURL();
    	
    	this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }
    public Book(int id, String title, String category, int price, int quantity, String type, String author,
            String coverType, String publisher, Date publishDate, int numOfPages, String language,
            String bookCategory) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.author = author;
        this.coverType = coverType;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.numOfPages = numOfPages;
        this.language = language;
        this.bookCategory = bookCategory;
    }
    
   
    // getter and setter
    public int getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getCoverType() {
        return this.coverType;
    }

    public Book setCoverType(String coverType) {
        this.coverType = coverType;
        return this;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public Book setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public Date getPublishDate() {
        return this.publishDate;
    }

    public Book setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public int getNumOfPages() {
        return this.numOfPages;
    }

    public Book setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
        return this;
    }

    public String getLanguage() {
        return this.language;
    }

    public Book setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getBookCategory() {
        return this.bookCategory;
    }

    public Book setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
        return this;
    }

    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                     "Book " +
                     "INNER JOIN Media " +
                     "ON Media.id = Book.id " +
                     "where Media.id = " + id + ";";
        System.out.println(sql);
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
		if(res.next()) {

            // from Media table
            String title = "";
            String type = res.getString("type");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");

            // from Book table
            String author = res.getString("author");
            String coverType = res.getString("coverType");
            String publisher = res.getString("publisher");
            Date publishDate = res.getDate("publishDate");
            int numOfPages = res.getInt("numOfPages");
            String language = res.getString("language");
            String bookCategory = res.getString("bookCategory");
            
            return new Book(id, title, category, price, quantity, type, 
                            author, coverType, publisher, publishDate, numOfPages, language, bookCategory);
            
		} else {
			throw new SQLException();
		}
    }
   
    public void updateMediaById() throws SQLException {
    	super.updateMediaById();
    	updateMediaFieldById("Book", this.id, "author", this.author);
    	updateMediaFieldById("Book", this.id, "coverType", this.coverType);
    	updateMediaFieldById("Book", this.id, "publisher", this.publisher);
    	updateMediaFieldById("Book", this.id, "publishDate", new java.sql.Date(this.publishDate.getTime()));
    	updateMediaFieldById("Book", this.id, "numOfPages", this.numOfPages);
    	updateMediaFieldById("Book", this.id, "language", this.language);
    	updateMediaFieldById("Book", this.id, "bookCategory", this.bookCategory);
    }
    @Override
    public void addMediaToDB() {
    	super.addMediaToDB();
   	 	String command = "INSERT INTO BOOK(id,author,coverType,publisher,publishDate,numOfPages,language,bookCategory) VALUES(?,?,?,?,?,?,?,?)";
        try(PreparedStatement addstmt = AIMSDB.getConnection().prepareStatement(command)) {
            addstmt.setObject(1,id);
            addstmt.setObject(2,author);
            addstmt.setObject(3,coverType);
            addstmt.setObject(4,publisher);
            addstmt.setObject(5,publishDate);
            addstmt.setObject(6,numOfPages);
            addstmt.setObject(7,language);
            addstmt.setObject(8,bookCategory);

            addstmt.execute();
            System.out.println("Book added");
        } catch(Exception err) {
            System.out.println("An error has occurred.");
            System.out.println("See full details below.");
            err.printStackTrace();
        }
   }
    @Override
    public List getAllMedia() {
        return null;
    }


    @Override
    public String toString() {
        return "{" +
            super.toString() +
            " author='" + author + "'" +
            ", coverType='" + coverType + "'" +
            ", publisher='" + publisher + "'" +
            ", publishDate='" + publishDate + "'" +
            ", numOfPages='" + numOfPages + "'" +
            ", language='" + language + "'" +
            ", bookCategory='" + bookCategory + "'" +
            "}";
    }
}
