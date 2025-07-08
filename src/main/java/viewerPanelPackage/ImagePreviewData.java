/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewerPanelPackage;

import com.zoro2723.comicreader.FileItemComponent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author zoro2723
 */
public class ImagePreviewData implements Runnable {

    private int imageIndex = 0; // for scrolling through arraylist and fetching data.

    //used to show next and previous images also.
    private int currentShowingImageIndex = 0; //hold the image index which is current showing 

    private final ArrayList<ImageData> imageList; //can hold the preview of each image found in the zip
    private final FileItemComponent fic; //Each fic represents a zip file

    //for checking if preview data is completely fetched from the database
    private boolean isFetchingCompleted = false;

    public static final Logger logger = Logger.getLogger(ImagePreviewData.class.getName());

    ImagePreviewData(FileItemComponent fic) {
        this.imageList = new ArrayList<>();
        this.fic = fic;
    }

    @Override
    public void run() {
        getData();
    }

    //populate the above arraylist with preview data(ImageData contains imageName and Buffered Image)
    public void getData() {
        String sql = "SELECT * FROM image_previews WHERE zip_name = ?";

        try (Connection con = database.DatabaseConnection.connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {

            System.out.println("Fetching Data");
            pstmt.setString(1, fic.getAbsoluteFileName());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    byte[] previewBytes = rs.getBytes("preview");
                    InputStream inputStream = new ByteArrayInputStream(previewBytes);

                    // Get BufferedImage and insert into map
                    ImageData imageData = new ImageData(ImageIO.read(inputStream), rs.getString("image_name"));
                    imageList.add(imageData);
                }
            }
        } catch (SQLException | IOException ex) {
            logger.log(Level.SEVERE, "Error fetching image previews:", ex);
        }

        logger.info("Fetching Complete");
        this.isFetchingCompleted = true; // Successfully fetched

    }

    public ArrayList<ImageData> getImageList() {
        return imageList;
    }

    public FileItemComponent getFic() {
        return fic;
    }

    public boolean isFetchingCompleted() {
        return isFetchingCompleted;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    //increament the imageIndex by given value
    public void incrementImageIndex(int increment) {
        this.imageIndex += increment;
    }

    public void decrementImageIndex(int increment) {
        this.imageIndex -= increment;
    }

    public int getCurrentImageIndex() {
        return currentShowingImageIndex;
    }

    public void setCurrentImageIndex(int panelIndex) {
        currentShowingImageIndex = imageIndex + panelIndex;
    }
    
    public boolean setNextImageIndex(){
        if(currentShowingImageIndex + 1 < imageList.size()){
            currentShowingImageIndex++;
            return true;
        }
        return false;
    }
    
    public void setPreviousImageIndex(){
        if(currentShowingImageIndex > 0){
            currentShowingImageIndex--;
        }
    }
    
    public ImageData getImageData(){
        return imageList.get(currentShowingImageIndex);
    }
}
