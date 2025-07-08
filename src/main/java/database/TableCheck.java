/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCheck{
    
       // Check if a table exists
    public static boolean doesTableExist(Connection conn, String tableName) {
        String sql = """
                SELECT name
                FROM sqlite_master
                WHERE type='table' AND name=?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tableName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a result is found
        } catch (SQLException e) {
            System.out.println("Failed to check table existence: " + e.getMessage());
            return false;
        }
    }
    
    public static void createTables(Connection conn) {
        String createZipFilesTable = """
                CREATE TABLE IF NOT EXISTS zip_files (
                    zip_name TEXT PRIMARY KEY,
                    processed BOOLEAN DEFAULT FALSE,
                    processed_at TIMESTAMP
                );
                """;

        String createImagePreviewsTable = """
                CREATE TABLE IF NOT EXISTS image_previews (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    zip_name TEXT,
                    image_name TEXT,
                    preview BLOB,
                    created_at TIMESTAMP,
                    FOREIGN KEY (zip_name) REFERENCES zip_files(zip_name)
                );
                """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createZipFilesTable);
            stmt.execute(createImagePreviewsTable);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Table creation failed: " + e.getMessage());
        }
    }

    

    // Insert image preview
    public static void insertImagePreview(Connection conn, String zipName, String imageName, byte[] preview) {
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

    

    

}