//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import javax.imageio.ImageIO;
//
//public class ImageWheelPanel extends JPanel implements MouseWheelListener {
//    private BufferedImage[] images;
//    private int currentIndex = 0;
//
//    public ImageWheelPanel(BufferedImage[] images) {
//        this.images = images;
//        this.setPreferredSize(new Dimension(400, 300));
//        this.addMouseWheelListener(this);
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (images.length > 0) {
//            g.drawImage(images[currentIndex], 0, 0, this);
//        }
//    }
//
//    @Override
//    public void mouseWheelMoved(MouseWheelEvent e) {
//        if (e.getWheelRotation() < 0) {
//            // Scroll up
//            currentIndex = (currentIndex + 1) % images.length; // Loop back to the first image
//        } else {
//            // Scroll down
//            currentIndex = (currentIndex - 1 + images.length) % images.length; // Loop to the last image
//        }
//        repaint(); // Refresh the panel to show the new image
//    }
//
//    public static void main(String[] args) {
//        // Load images
//        BufferedImage[] images = new BufferedImage[4];
//        try {
//            images[0] = ImageIO.read(new File("path/to/image1.jpg"));
//            images[1] = ImageIO.read(new File("path/to/image2.jpg"));
//            images[2] = ImageIO.read(new File("path/to/image3.jpg"));
//            images[3] = ImageIO.read(new File("path/to/image4.jpg"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Create the main frame
//        JFrame frame = new JFrame("Image Wheel Panel");
//        ImageWheelPanel imagePanel = new ImageWheelPanel(images);
//        frame.add(imagePanel);
//        frame.pack();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
//}
//
