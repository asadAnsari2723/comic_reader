/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewerPanelPackage;

import java.awt.image.BufferedImage;

public class ImageData {

    private final BufferedImage image;
    private final String imageName;
    private boolean isSelected = false;

    public ImageData(BufferedImage image, String imageName) {
        this.image = image;
        this.imageName = imageName;
    }

    public boolean isIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public String getImageName() {
        return imageName;
    }
}
