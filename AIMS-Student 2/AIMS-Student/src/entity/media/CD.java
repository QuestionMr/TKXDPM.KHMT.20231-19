package entity.media;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entity.db.AIMSDB;
import utils.Configs;

public class CD extends Media {

    String artist;
    String recordLabel;
    String musicType;
    Date releasedDate;

    public CD() throws SQLException{

    }
    public CD(Media m, String artist,
            String recordLabel, String musicType, Date releasedDate) throws SQLException{
    	
    	copyMedia(m);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }
    public CD(int id, String title, String category, int price, int quantity, String type, String artist,
            String recordLabel, String musicType, Date releasedDate) throws SQLException{
        super(id, title, category, price, quantity, type);
        this.artist = artist;
        this.recordLabel = recordLabel;
        this.musicType = musicType;
        this.releasedDate = releasedDate;
    }

    public String getArtist() {
        return this.artist;
    }

    public CD setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getRecordLabel() {
        return this.recordLabel;
    }

    public CD setRecordLabel(String recordLabel) {
        this.recordLabel = recordLabel;
        return this;
    }

    public String getMusicType() {
        return this.musicType;
    }

    public CD setMusicType(String musicType) {
        this.musicType = musicType;
        return this;
    }

    public Date getReleasedDate() {
        return this.releasedDate;
    }

    public CD setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
        return this;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + " artist='" + artist + "'" + ", recordLabel='" + recordLabel + "'"
                + "'" + ", musicType='" + musicType + "'" + ", releasedDate='"
                + releasedDate + "'" + "}";
    }

    @Override
    public Media getMediaById(int id) throws SQLException {
        String sql = "SELECT * FROM "+
                     "CD " +
                     "INNER JOIN Media " +
                     "ON Media.id = CD.id " +
                     "where Media.id = " + id + ";";
        ResultSet res = stm.executeQuery(sql);
		if(res.next()) {
            
            // from media table
            String title = "";
            String type = res.getString("type");
            int price = res.getInt("price");
            String category = res.getString("category");
            int quantity = res.getInt("quantity");

            // from CD table
            String artist = res.getString("artist");
            String recordLabel = res.getString("recordLabel");
            String musicType = res.getString("musicType");
            Date releasedDate = res.getDate("releasedDate");
           
            return new CD(id, title, category, price, quantity, type, 
                          artist, recordLabel, musicType, releasedDate);
            
		} else {
			throw new SQLException();
		}
    }
    public void updateMediaById() throws SQLException {
    	super.updateMediaById();
    	updateMediaFieldById("CD", this.id, "artist", this.artist);
    	updateMediaFieldById("CD", this.id, "recordLabel", this.recordLabel);
    	updateMediaFieldById("CD", this.id, "musicType", this.musicType);
    	updateMediaFieldById("CD", this.id, "releasedDate", new java.sql.Date(this.releasedDate.getTime()));
    }
    @Override
    public void addMediaToDB() {
    	super.addMediaToDB();
   	 	String command = "INSERT INTO CD(id,artist,recordLabel,musicType,releasedDate) VALUES(?,?,?,?,?)";
        try(PreparedStatement addstmt = AIMSDB.getConnection().prepareStatement(command)) {
            addstmt.setObject(1,id);
            addstmt.setObject(2,artist);
            addstmt.setObject(3,recordLabel);
            addstmt.setObject(4,musicType);
            addstmt.setObject(5,releasedDate);
            addstmt.execute();
            System.out.println("CD added");
        } catch(Exception err) {
            System.out.println("An error has occurred.");
            System.out.println("See full details below.");
            err.printStackTrace();
        }
   }
    public void setLabelInfo() {
    	this.LABELS = Configs.CDLABEL.clone();
    	String[] temp = {this.artist, this.recordLabel, this.musicType, new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(this.releasedDate)}; 
    	this.LABELINFOS = temp.clone();
    }
    @Override
    public List getAllMedia() {
        return null;
    }

}
