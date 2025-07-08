/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewerPanelPackage;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author zoro2723
 */
public class PreviewPanel extends JPanel implements MouseWheelListener, MouseListener, ImageSetter {

    public final static int NO_OF_IMAGES = 8;
    private final ImagePreviewPanel[] panels;
    private final ImagePreviewData ipd;
    private final RightPanelContainer parent;

    public PreviewPanel(ImagePreviewData ipd, RightPanelContainer parent) {
        //set panel properties
        super();
        this.setLayout(new GridLayout(2, 4, 20, 20));
        this.setBorder(new TitledBorder("Preview"));

        this.ipd = ipd;
        this.parent = parent;

        panels = new ImagePreviewPanel[NO_OF_IMAGES];
        addImagePreviewPanel();

    }

    //Initailization Block for adding listener and such
    {
        //to scroll through imagePreviewData of zip file
        this.addMouseWheelListener(this);

    }

    private void addImagePreviewPanel() {

        for (int index = 0; index < panels.length; index++) {
            panels[index] = new ImagePreviewPanel(index, ipd);

            // Add the MouseListener to each ImagePreviewPanel so when clicked show different panel
            panels[index].addMouseListener(this);

            // Add the panel to the PreviewPanel's layout
            this.add(panels[index]);
        }

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mwe) {
        if (mwe.getWheelRotation() < 0) { // Scroll up
//            System.out.println("wheel up");
            if (ipd.getImageIndex() > 0) {
                ipd.decrementImageIndex(NO_OF_IMAGES);
            }
        } else { // Scroll down
//          System.out.println("wheel down");

            if (ipd.getImageIndex() < ipd.getImageList().size() - NO_OF_IMAGES) {
                ipd.incrementImageIndex(NO_OF_IMAGES);
            }
        }
        repaintPanels(); // Repaint all panels after scrolling
    }

    public void repaintPanels() {
        for (ImagePreviewPanel panel : panels) {
            panel.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        //ensure to act only when imagePreviewPanel is clicked
        if (me.getSource() instanceof ImagePreviewPanel panel) {
            //give data to the parent which image panel has been clicked and  which panel to show

            ipd.setCurrentImageIndex(panel.getPanelIndex());

            //Parent Container : RightPanelContainer
            //gets new image on the basis of above set current index
            parent.getNewImage();

            //separately handle on which panel to show the image
            parent.changePanel("show");
        }
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
    }

    @Override
    public void setImage(BufferedImage image) {
        
    }

    @Override
    public boolean getFocus(){
        return this.requestFocusInWindow();
    }
}
