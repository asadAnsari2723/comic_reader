package viewerPanelPackage;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * @author zoro2723
 */
public class ShowPanel extends JPanel implements KeyListener, ImageSetter {

    public static char NEXT_CHAR = '.';
    public static char PREVIOUS_CHAR = ',';

    private BufferedImage image;

    private final ImagePreviewData ipd;
    private final static Logger logger = Logger.getLogger(ShowPanel.class.getName());

    //important because the parent is scrollPane not the rightPanelContainer
    private final RightPanelContainer parent;

    public ShowPanel(ImagePreviewData ipd, RightPanelContainer parent) {
        super();
        this.ipd = ipd;
        this.parent = parent;
       // this.setPreferredSize(getParent().getSize()); //sets size of jscrollpane
    }

    {
        this.addKeyListener(this);

    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
        updatePanelSize(); //update panel size according to the image
        this.revalidate();
        this.repaint();
        this.requestFocus();
    }
    
    @Override
    public boolean getFocus(){
        return this.requestFocusInWindow();
    }

    private void updatePanelSize() {
        this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) {
            g.drawString("Cannot Fetch Image", getWidth() / 2, getHeight() / 2);
        } else {
            g.drawImage(image, 0, 0, this);
        }
        g.dispose();
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
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    private BufferedImage getNextImage() {
        ipd.setNextImageIndex();
        return parent.getNewImage();
    }

    private BufferedImage getPreviousImage() {
        ipd.setPreviousImageIndex();
        return parent.getNewImage();
    }

}
