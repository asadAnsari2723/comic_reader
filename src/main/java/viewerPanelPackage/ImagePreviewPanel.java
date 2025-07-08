/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewerPanelPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author zoro2723
 */
public class ImagePreviewPanel extends JPanel {

    private final static Logger logger = Logger.getLogger(ImagePreviewPanel.class.getName());

    //unique to each panel
    public final int panelIndex;

    //common shared image preview data
    public final ImagePreviewData zipData;

    //Panel Properties
    private static final Dimension preferredSize = new Dimension(400, 200);

    //Some reference to increase code readability
    private ArrayList<ImageData> previewImagesList;

    public ImagePreviewPanel(int panelIndex, ImagePreviewData zipData) {
        this.panelIndex = panelIndex;
        this.zipData = zipData;
        setPreferredSize(preferredSize);
        init();
    }

    private void init() {
        previewImagesList = zipData.getImageList(); //this list stores imageData.
    }

    public int getPanelIndex() {
        return panelIndex;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        int index = zipData.getImageIndex() + panelIndex;
        if (index < previewImagesList.size()) {
            g.drawImage(previewImagesList.get(index).getBufferedImage(), 0, 0, getWidth(), getHeight(), null);

        } else {
            g.setColor(Color.BLACK);
            g.drawString("No more files", getWidth() / 4, getHeight() / 2);
        }
        g.dispose();
    }

}
