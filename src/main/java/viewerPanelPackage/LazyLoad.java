///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package viewerPanelPackage;
//
//You're right!  My apologies for the JavaScript-focused response.  Here's a Java Swing implementation for lazy loading image previews from your database, keeping a buffer of 20 images:
//
//```java
//import java.awt.*;
//import java.awt.event.AdjustmentEvent;
//import java.awt.event.AdjustmentListener;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import javax.imageio.ImageIO;
//import javax.swing.*;
//
//public class LazyImagePreview {
//
//    private Connection conn; // Your database connection
//    private JScrollPane scrollPane;
//    private JPanel imagePanel;
//    private int offset = 0;
//    private int limit = 20;
//    private List<ImagePreview> imagePreviews = new ArrayList<>();
//
//    public LazyImagePreview(Connection conn) {
//        this.conn = conn;
//        initializeGUI();
//    }
//
//    private void initializeGUI() {
//        JFrame frame = new JFrame("Lazy Image Previews");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 400);
//
//        imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        scrollPane = new JScrollPane(imagePanel);
//        frame.add(scrollPane);
//
//        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            @Override
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//                if (e.getValueIsAdjusting() && e.getAdjustable().getValue() + e.getAdjustable().getVisibleAmount() >= e.getAdjustable().getMaximum() - 100) {
//                    loadMoreImages();
//                }
//            }
//        });
//
//        frame.setVisible(true);
//    }
//
//    private void loadMoreImages() {
//        imagePreviews.addAll(getImagePreviews(conn, offset, limit));
//        offset += limit;
//
//        for (ImagePreview preview : imagePreviews) {
//            try {
//                BufferedImage image = loadImageFromPreview(preview.getPreview());
//                JLabel imageLabel = new JLabel(new ImageIcon(image));
//                imagePanel.add(imageLabel);
//            } catch (IOException e) {
//                System.err.println("Error loading image: " + e.getMessage());
//            }
//        }
//
//        imagePanel.revalidate();
//        imagePanel.repaint();
//    }
//
//    private BufferedImage loadImageFromPreview(byte[] preview) throws IOException {
//        InputStream inputStream = new ByteArrayInputStream(preview);
//        return ImageIO.read(inputStream);
//    }
//
//    private List<ImagePreview> getImagePreviews(Connection conn, int offset, int limit) {
//        List<ImagePreview> previews = new ArrayList<>();
//        String sql = "SELECT zip_name, image_name, preview FROM image_previews LIMIT ?, ?";
//
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, offset);
//            pstmt.setInt(2, limit);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                ImagePreview preview = new ImagePreview(
//                    rs.getString("zip_name"),
//                    rs.getString("image_name"),
//                    rs.getBytes("preview")
//                );
//                previews.add(preview);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving image previews: " + e.getMessage());
//        }
//
//        return previews;
//    }
//
//    public static void main(String[] args) {
//        // Replace with your actual database connection
//        Connection conn = ...; 
//        SwingUtilities.invokeLater(() -> new LazyImagePreview(conn));
//    }
//}
//
//class ImagePreview {
//    private String zipName;
//    private String imageName;
//    private byte[] preview;
//
//    public ImagePreview(String zipName, String imageName, byte[] preview) {
//        this.zipName = zipName;
//        this.imageName = imageName;
//        this.preview = preview;
//    }
//
//    public String getZipName() {
//        return zipName;
//    }
//
//    public String getImageName() {
//        return imageName;
//    }
//
//    public byte[] getPreview() {
//        return preview;
//    }
//}
