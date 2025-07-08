///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package viewerPanelPackage;
//
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Point;
//import java.awt.RenderingHints;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import java.awt.geom.AffineTransform;
//import java.awt.image.BufferedImage;
//import javax.swing.JPanel;
//
///**
// *
// * @author zoro2723
// */
//public class FitToWindowPreview extends JPanel implements KeyListener, ImageSetter, MouseWheelListener, MouseMotionListener, MouseListener {
//
//    private boolean zoomable = true;
//
//    private double scale = 1.0;
//    private Point zoomCenter = new Point(0, 0);
//    private Point mousePosition = new Point(0, 0); // Track mouse posit
//    private BufferedImage image;
//    private final RightPanelContainer parent;
//    private final ImagePreviewData ipd;
//    private Point dragStart = null;
//    private Point dragOffset = new Point(0, 0);
//
//    public FitToWindowPreview(ImagePreviewData ipd, RightPanelContainer parent) {
//        super();
//        this.ipd = ipd;
//        this.parent = parent;
//    }
//
//    {
//        this.addKeyListener(this);
//        this.addMouseWheelListener(this);
//        this.addMouseMotionListener(this);
//    }
//
//    @Override
//    public void setImage(BufferedImage image) {
//        this.image = image;
//        this.setSize(getPreferredSize());
//        revalidate();
//        repaint();
//
//    }
//
//    @Override
//    public boolean getFocus() {
//        return this.requestFocusInWindow();
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
//
//        if (zoomable) {
//
//            // Calculate the center of the panel
//            int panelWidth = getWidth();
//            int panelHeight = getHeight();
//            Point panelCenter = new Point(panelWidth / 2, panelHeight / 2);
//
//            // Calculate the offset to keep the zoomed image centered
//            int xOffset = (int) ((panelCenter.x - mousePosition.x) * (1 - scale)) + dragOffset.x;
//            int yOffset = (int) ((panelCenter.y - mousePosition.y) * (1 - scale)) + dragOffset.y;
//
//            AffineTransform transform = AffineTransform.getTranslateInstance(xOffset, yOffset);
//            transform.scale(scale, scale);
//            g2d.drawImage(image, transform, null);
//
////            // Calculate the center of the panel
////            int panelWidth = getWidth();
////            int panelHeight = getHeight();
////            Point panelCenter = new Point(panelWidth / 2, panelHeight / 2);
////
////            // Calculate the offset to keep the zoomed image centered
////            int xOffset = (int) ((panelCenter.x - mousePosition.x) * (1 - scale));
////            int yOffset = (int) ((panelCenter.y - mousePosition.y) * (1 - scale));
////
////            AffineTransform transform = AffineTransform.getTranslateInstance(xOffset, yOffset);
////            transform.scale(scale, scale);
////            g2d.drawImage(image, transform, null);
//////            
//////            AffineTransform transform = AffineTransform.getTranslateInstance(-zoomCenter.x, -zoomCenter.y);
//////            transform.scale(scale, scale);
//////            transform.translate(zoomCenter.x, zoomCenter.y);
//////            g2d.drawImage(image, transform, null);
//        } else {
//            int imageWidth = image.getWidth();
//            int imageHeight = image.getHeight();
//            int panelWidth = getWidth();
//            int panelHeight = getHeight();
//
//            // Calculate scaling factor to fit image height within panel height
//            double scaleFactor = (double) panelHeight / imageHeight;
//
//            // Calculate scaled width and height
//            int scaledWidth = (int) (imageWidth * scaleFactor);
//            int scaledHeight = panelHeight;
//
//            // Calculate x-offset for horizontal centering
//            int xOffset = (panelWidth - scaledWidth) / 2;
//
//            // Draw the scaled image centered horizontally
//            g2d.drawImage(image, xOffset, 0, scaledWidth, scaledHeight, null);
//
//        }
//        g2d.dispose();
//    }
//
//    @Override
//    public void mouseWheelMoved(MouseWheelEvent e) {
//        int notches = e.getWheelRotation();
//        double zoomFactor = notches > 0 ? 0.9 : 1.1; // Zoom out on scroll up, zoom in on scroll down
//
//        // Calculate zoom center based on mouse position
//        zoomCenter = e.getPoint();
//
//        // Adjust scale
//        scale *= zoomFactor;
//
//        // Limit zoom range (optional)
//        scale = Math.max(0.1, Math.min(scale, 5.0));
//
//        repaint();
//    }
//
//    private BufferedImage getNextImage() {
//        ipd.setNextImageIndex();
//        return parent.getNewImage();
//    }
//
//    private BufferedImage getPreviousImage() {
//        ipd.setPreviousImageIndex();
//        return parent.getNewImage();
//    }
//
//    @Override
//    public void keyTyped(KeyEvent ke) {
//        char c = ke.getKeyChar();
//        if (c == ShowPanel.NEXT_CHAR) {
//            setImage(getNextImage());
//        } else if (c == ShowPanel.PREVIOUS_CHAR) {
//            setImage(getPreviousImage());
//        }
//    }
//
//    @Override
//    public void keyPressed(KeyEvent ke) {
//
//    }
//
//    @Override
//    public void keyReleased(KeyEvent ke) {
//
//    }
//
//    @Override
//    public Dimension getPreferredSize() {
//        if (image == null) {
//            return new Dimension(1000, 1000);
//        } else {
//            return new Dimension(image.getWidth(), image.getHeight());
//        }
//    }
//
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        if (dragStart != null) {
//            int dx = e.getX() - dragStart.x;
//            int dy = e.getY() - dragStart.y;
//            dragOffset.x += dx;
//            dragOffset.y += dy;
//            dragStart = e.getPoint();
//            repaint();
//        }
//    }
//
//    @Override
//    public void mouseMoved(MouseEvent e) {
//        mousePosition = e.getPoint();
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        dragStart = null; // Reset dragStart when mouse button is released
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void mousePressed(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    @Override
//    public void mouseExited(MouseEvent me) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//}
package viewerPanelPackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class FitToWindowPreview extends JPanel implements KeyListener, ImageSetter, MouseWheelListener, MouseMotionListener, MouseListener {

    private boolean zoomable = true;
    private double scale = 1.0;
    private Point zoomCenter = new Point(0, 0);
    private Point mousePosition = new Point(0, 0);
    private BufferedImage image;
    private final RightPanelContainer parent;
    private final ImagePreviewData ipd;
    private Point dragStart = null;
    private Point dragOffset = new Point(0, 0);

    public FitToWindowPreview(ImagePreviewData ipd, RightPanelContainer parent) {
        super();
        this.ipd = ipd;
        this.parent = parent;
        this.addKeyListener(this);
        this.addMouseWheelListener(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
        //this.setSize(getPreferredSize());
        revalidate();
        repaint();
    }

    @Override
    public boolean getFocus() {
        return this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        if (image != null) {
            // Calculate the center of the panel
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            Point panelCenter = new Point(panelWidth / 2, panelHeight / 2);

            // Calculate the offset to keep the zoomed image centered
            int xOffset = (int) ((panelCenter.x - mousePosition.x) * (1 - scale)) + dragOffset.x;
            int yOffset = (int) ((panelCenter.y - mousePosition.y) * (1 - scale)) + dragOffset.y;

            AffineTransform transform = AffineTransform.getTranslateInstance(xOffset, yOffset);
            transform.scale(scale, scale);
            g2d.drawImage(image, transform, null);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (zoomable) {
            double zoomFactor = 0.1; // Adjust zoom speed
            if (e.getWheelRotation() < 0) {
                scale += zoomFactor; // Zoom in
            } else {
                scale = Math.max(0.1, scale - zoomFactor); // Zoom out, prevent negative scale
            }
            mousePosition = e.getPoint(); // Update mouse position for zooming
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint(); // Start dragging
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStart != null) {
            Point currentPoint = e.getPoint();
            dragOffset.x += currentPoint.x - dragStart.x;
            dragOffset.y += currentPoint.y - dragStart.y;
            dragStart = currentPoint; // Update drag start point
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragStart = null; // Stop dragging
    }

    private BufferedImage getNextImage() {
        ipd.setNextImageIndex();
        return parent.getNewImage();
    }

    private BufferedImage getPreviousImage() {
        ipd.setPreviousImageIndex();
        return parent.getNewImage();
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        char c = ke.getKeyChar();
        if (c == ShowPanel.NEXT_CHAR) {
            setImage(getNextImage());
        } else if (c == ShowPanel.PREVIOUS_CHAR) {
            setImage(getPreviousImage());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = e.getPoint(); // Update mouse position
    }
}
