/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewerPanelPackage;

import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.*;
import javax.swing.Timer;

/**
 *
 * @author zoro2723
 */
public class RightPanelContainer extends JPanel implements MouseListener, ActionListener {

    private final HashMap<String, ImageSetter> panelMap = new HashMap<>();

    private final HashMap<String, BufferedImage> imageCache = new HashMap<>();

    private final static Logger logger = Logger.getLogger(RightPanelContainer.class.getName());

    private BufferedImage image;
    private ZipFile zip;
    private String imageName;
    private final PreviewPanel previewPanel;
    final ShowPanel showPanel;
    private final ImagePreviewData ipd;
    private final JScrollPane scrollPane, fitWindowScrollPane;
    private final FitToWindowPreview fitWindowPanel;

    private String currentShowingPanel = "preview";
    CardLayout card;

    public RightPanelContainer(ImagePreviewData ipd) {
        super();
        this.ipd = ipd;
        setLayout(new CardLayout());
        previewPanel = new PreviewPanel(ipd, this);
        showPanel = new ShowPanel(ipd, this);
        fitWindowPanel = new FitToWindowPreview(ipd, this);
        scrollPane = new JScrollPane(showPanel);
        fitWindowScrollPane = new JScrollPane(fitWindowPanel);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        addMyComponents();
    }

    public final void addMyComponents() {
        this.add(previewPanel, "preview");
        this.add(scrollPane, "show");
//        this.add(fitWindowPanel, "fitWindow");
        this.add(fitWindowScrollPane, "fitWindow");
        panelMap.put("preview", previewPanel);
        panelMap.put("show", showPanel);
        panelMap.put("fitWindow", fitWindowPanel);

        card = (CardLayout) this.getLayout();
        card.show(this, "preview");

    }

    public void changePanel(String panelIdentifier) {
        this.currentShowingPanel = panelIdentifier;
        ImageSetter setImage = panelMap.get(panelIdentifier);
        setImage.setImage(image);
        this.card.show(this, panelIdentifier);
        if (setImage.getFocus()) {
            logger.log(Level.INFO, "{0} has focus", panelIdentifier);
        } else {
            logger.log(Level.WARNING, "{0} failed to get focus", panelIdentifier);
        }
    }

    public BufferedImage getImage(String imageName) {

        // Check if the image is already cached
        if (imageCache.containsKey(imageName)) {
            logger.log(Level.INFO, "Image loaded from cache: {0}", imageName);
            return imageCache.get(imageName);
        }

        if (zip == null) {
            createZipReference();
            if (zip == null) {
                logger.severe("ZIP file reference could not be created.");
                return null;
            }
        }

        ZipEntry entry = zip.getEntry(imageName);
        if (entry == null) {
            logger.log(Level.WARNING, "File not found in the ZIP archive: {0}", imageName);
            return null;
        }

        try (InputStream inputStream = zip.getInputStream(entry)) {
            // Load the image and cache it
            BufferedImage img = ImageIO.read(inputStream);
            if (image != null) {
                imageCache.put(imageName, img);
                logger.log(Level.INFO, "Image cached: {0}", imageName);
            }
            return img;
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error reading image from ZIP file: " + imageName, ex);
            return null;
        }
    }

    public void clearCache() {
        imageCache.clear();
        logger.info("Image cache cleared.");
    }
//
//    private boolean isIndexPossible(int index) {
//        if (index >= 0 && index < ipd.getImageList().size()) {
//            return true;
//        } else {
//            return false;
//        }
//    }

//    private ImageData setNextImage() {
//        if (isIndexPossible(currentImageIndex + 1)) {
//            return ipd.getImageList().get(++currentImageIndex);
//        }
//
//        return null;
//    }
//
//    private ImageData setPreviousImage() {
//        if (isIndexPossible(currentImageIndex - 1)) {
//            return ipd.getImageList().get(--currentImageIndex);
//        }
//
//        return null;
//    }
//
//    private void setImage(ImageData imageData) {
//        if (imageData != null) {
//            setImageName(imageData.getImageName(), currentImageIndex);
//        } else {
//            logger.severe("Next or previous image not found , image data is null");
//        }
//    }
    private void createZipReference() {
        try {
            zip = new ZipFile(ipd.getFic().file);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot create zip reference: Unable to identify zip file.", ex);
        }
    }

    private void getFocus(String panelIdentifier) {
        panelMap.get(panelIdentifier).getFocus();
    }

    @Override
    public void mouseClicked(MouseEvent me) {

    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
        System.out.print("Mouse Entered");
    }

    //called by previewPanel, showPanel(for next and previous image)
    public BufferedImage getNewImage() {
        //get required image from the zip by reading the current index modified by preview panel

        //name of image to be extracted from zip to view
        this.imageName = ipd.getImageData().getImageName();

        //return and store the buffered image into cache from the zip 
        image = getImage(imageName);

        return image;

    }

    File extractImage() {
        File extractedFile = null;
        try {

            String homeDir = System.getenv("HOME"); // Gets the home directory on Linux/macOS
            extractedFile = new File(homeDir + "/Pictures/" + imageName);
            extractedFile.mkdirs();
            logger.log(Level.INFO, "file extraction path: {0}", imageName);
            // Write the image to the file
            boolean isWritten = ImageIO.write(image, "PNG", extractedFile);

            if (isWritten) {
                System.out.println("Image written successfully!");
            } else {
                System.out.println("Image writing failed.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot write image file", e);

        }
        return extractedFile;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (currentShowingPanel.equals("preview")) {
            changePanel("show");
        } else {
            ImageSetter panel = panelMap.get(this.currentShowingPanel);
            panel.setImage(getNewImage());
        }
        if (ipd.setNextImageIndex() == false) {
            if (ae.getSource() instanceof Timer timer) {
                timer.stop();
                logger.log(Level.INFO, "Timer Stopped");

            } else {
                logger.log(Level.SEVERE, "Unable to stop timer");
            }
        }
    }

}
