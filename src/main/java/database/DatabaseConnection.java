package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.InputStream;

public class DatabaseConnection {

    // Method to get the database URL from XML configuration
    private static String getDatabaseURL() {
        try {
            // Load the XML file from the resources folder
            InputStream xmlStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("xmlFiles/config.xml");
            if (xmlStream == null) {
                System.out.println("Error: config.xml not found in resources folder.");
                return null;
            }

            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlStream);

            // Extract the URL value from the XML file
            NodeList urlList = document.getElementsByTagName("url");
            if (urlList.getLength() > 0) {
                Element urlElement = (Element) urlList.item(0);
                return urlElement.getTextContent(); // Return the URL text
            }
        } catch (Exception e) {
            System.out.println("Error reading XML: " + e.getMessage());
        }
        return null;
    }

    // Method to establish a database connection
    public static Connection connect() {
        String url = getDatabaseURL(); // Get URL from the XML file
        if (url != null) {
            try {
                // Connect to SQLite database
                Connection conn = DriverManager.getConnection(url);
                return conn;
            } catch (SQLException e) {
                System.out.println("Connection error: " + e.getMessage());
            }
        } else {
            System.out.println("Database URL not found.");
        }
        return null;
    }

}
