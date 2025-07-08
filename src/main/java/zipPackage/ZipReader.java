/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zipPackage;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 *
 * @author zoro2723
 */

//use this
//ImageIO.getReaderFormatNames()
public class ZipReader {
    private String zipFilePath;
    public ZipReader(String zipFilePath){
        this.zipFilePath = zipFilePath;
    }
    
    public static BufferedImage getImage(String imageName, File zipFile){
        BufferedImage bi = null;
       
        return bi;
    }
    
    public ArrayList<String> getFileNames(){
        ArrayList<String> fileList = new ArrayList<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;

            // Iterate through the entries in the ZIP file
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) { // Exclude directories
                    fileList.add(entry.getName());
                }
                zipInputStream.closeEntry(); // Close the current entry
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }

        return fileList;
    }
    
    public ArrayList<String> getFileNames(String[] extensions) {
       
        ArrayList<String> fileName = new ArrayList<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;

            // Iterate through the entries in the ZIP file
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) { // Exclude directories
                    //System.out.println(entry.getName()); // Print the file name
                    for (String extension : extensions) {
                        if (entry.getName().toLowerCase().endsWith("." + extension)) {
                            fileName.add(entry.getName());
                            break;
                        }
                    }
                }
                zipInputStream.closeEntry(); // Close the current entry
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return fileName;
    }
    
    public boolean extract(String fileName, String outputFilePath) {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            // Get the entry from the ZIP file
            ZipEntry entry = zipFile.getEntry(fileName);
            
            //Make sure output directory exists
            createDir(outputFilePath);
            
            if (entry != null) {
                // Create an input stream for the entry
                try (InputStream is = zipFile.getInputStream(entry);
                     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFilePath))) {
                    
                    byte[] buffer = new byte[1024];
                    int length;
                    // Read the entry data and write it to the output file
                    while ((length = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, length);
                    }
                }

                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void extractAllEntries(String outputDir) {
        // Ensure the output directory exists
        File dir = createDir(outputDir);
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            // Get an enumeration of the entries in the ZIP file
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File outputFile = new File(dir, entry.getName());

                // Create directories for nested entries if needed
                if (entry.isDirectory()) {
                    outputFile.mkdirs(); // Create the directory
                    continue; // Move to the next entry
                }

                // Ensure the parent directory exists for the file
                new File(outputFile.getParent()).mkdirs();

                // Extract the file
                try (InputStream is = zipFile.getInputStream(entry);
                     BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, length);
                    }
                }

                System.out.println("Extracted: " + outputFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public File createDir(String outputDir){
         // Ensure the output directory exists
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
