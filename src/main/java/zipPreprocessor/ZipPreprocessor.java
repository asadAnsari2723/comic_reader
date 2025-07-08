/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zipPreprocessor;

import com.zoro2723.comicreader.FileItemComponent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;

public class ZipPreprocessor implements Runnable{
    private FileItemComponent fic;
    private int previewWidth = 400, previewHeight = 300;
    private Connection connection;
    //private FileList<? extends File> zipFiles;  // List of zip files to process

    public ZipPreprocessor(FileItemComponent fic, Connection con) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        this.fic = fic;
        connection = con;
    }

//    public ZipPreprocessor(FileList<? extends File> zipFiles, Connection connection) {
//        this.zipFiles = zipFiles;
//        this.connection = connection;
//    }

    // Method to process zip files one by one
//    public void processZipFiles() {
//        Iterator<? extends File> myZipFiles = zipFiles.getMainList().iterator();
//        while (myZipFiles.hasNext()) {
//            try {
//                File zipFile = myZipFiles.next();
//
//                if (isZipProcessed(connection, zipFile.getName())) {
//                    System.out.println("Zip Already Processed: " + zipFile.getName());
//                    
//                } else {
//                    processZipFile(zipFile.getAbsolutePath());
//                    markZipAsProcessed(connection, zipFile.getName());
//                    insertZipFile(connection, zipFile.getName());
//                }
//            } catch (IOException | SQLException e) {
//                System.out.print(e);
//            }
//        }
//    }

    // Mark ZIP file as processed
    private void markZipAsProcessed(Connection conn, String zipName) {
        String sql = """
                UPDATE zip_files
                SET processed = TRUE, processed_at = CURRENT_TIMESTAMP
                WHERE zip_name = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, zipName);
            pstmt.executeUpdate();
            System.out.println("ZIP file marked as processed: " + zipName);
        } catch (SQLException e) {
            System.out.println("Mark ZIP as processed failed: " + e.getMessage());
        }
    }
    
    // Check if a ZIP file is processed
    private boolean isZipProcessed(Connection conn, String zipName) {
        String sql = "SELECT processed FROM zip_files WHERE zip_name = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, zipName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("processed");
            }
        } catch (SQLException e) {
            System.out.println("Check ZIP processed failed: " + e.getMessage());
        }
        return false;
    }

    
    // Insert a new ZIP file entry
    private void insertZipFile(Connection conn, String zipName) {
        String sql = "INSERT INTO zip_files (zip_name) VALUES (?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, zipName);
            pstmt.executeUpdate();
            System.out.println("ZIP file inserted: " + zipName);
        } catch (SQLException e) {
            System.out.println("Insert ZIP file failed: " + e.getMessage());
        }
    }
    
    // Method to process each zip file
    private void processZipFile(String zipFilePath) throws IOException, SQLException {
        
        if(isZipProcessed(connection, zipFilePath)){
            updateProgress(100);
            return;
            
        }
        
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            int size = zipFile.size();
            double percent = 0.0;
            while (entries.hasMoreElements()) {
                
                percent++;
                ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".jpg") || entry.getName().endsWith(".png")) {
                    BufferedImage preview = generatePreview(zipFile, entry);
                    //storePreviewInDatabase(entry.getName(), preview, "jpg");  // Assuming jpg for simplicity
                    insertImagePreview(connection, zipFile.getName(), entry.getName(), generateByteOfPreview(preview, "jpg"));
                    // Update progress after processing each image
                    updateProgress((int) ((percent/size)*100));
                }
            }
            updateProgress(100);
        }
        insertZipFile(connection, zipFilePath);
        markZipAsProcessed(connection, zipFilePath);
    }

    // Generate image preview (thumbnail) for a given image entry in the zip file
    private BufferedImage generatePreview(ZipFile zipFile, ZipEntry entry) throws IOException {
        try (InputStream inputStream = zipFile.getInputStream(entry)) {
            BufferedImage originalImage = ImageIO.read(inputStream);
            return resizeImage(originalImage, previewWidth, previewHeight);  // Resize image to create preview
        }
    }

    // Resize image to create a thumbnail (preview)
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, width, height, null);
        return resizedImage;
    }

//    // Store preview in database
//    private void storePreviewInDatabase(String fileName, BufferedImage previewImage, String format) throws SQLException, IOException {
//        String sql = "INSERT INTO ImagePreviews (file_name, preview, format) VALUES (?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(previewImage, format, baos);  // Convert the image to bytes
//            byte[] previewBytes = baos.toByteArray();
//
//            stmt.setString(1, fileName);
//            stmt.setBytes(2, previewBytes);  // Store as BLOB
//            stmt.setString(3, format);
//
//            stmt.executeUpdate();
//        }
//    }
    public byte[] generateByteOfPreview(BufferedImage preview, String format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(preview, format, baos);  // Convert the image to bytes
        } catch (IOException ex) {
            System.out.println("problem in generateByteOfPreview");
        }
        return baos.toByteArray();
    }

    public void insertImagePreview(Connection conn, String zipName, String imageName, byte[] preview) {
        String sql = """
                INSERT INTO image_previews (zip_name, image_name, preview, created_at)
                VALUES (?, ?, ?, CURRENT_TIMESTAMP);
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, zipName);
            pstmt.setString(2, imageName);
            pstmt.setBytes(3, preview);
            pstmt.executeUpdate();
            System.out.println("Image preview inserted: " + imageName + " in ZIP " + zipName);
        } catch (SQLException e) {
            System.out.println("Insert image preview failed: " + e.getMessage());
        }
    }

    // Update progress
    private void updateProgress(int progress) {
//        System.out.println("");
//        System.out.println(progress);
//        System.out.println("");

        fic.update(progress);
    }

    @Override
    public void run() {
        try {
            //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            processZipFile(this.fic.file.getAbsolutePath());
        } catch (IOException | SQLException ex) {
            Logger.getLogger(ZipPreprocessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
}
