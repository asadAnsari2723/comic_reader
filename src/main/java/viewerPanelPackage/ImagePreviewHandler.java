/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewerPanelPackage;

import com.zoro2723.comicreader.FileItemComponent;
import com.zoro2723.comicreader.MyEventObject;
import com.zoro2723.comicreader.MyTabbedPane;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author zoro2723
 */
public class ImagePreviewHandler {
    private Integer panelIndex = 1; // because the zip container panel is already added
    private final ExecutorService exe = Executors.newFixedThreadPool(4);
    private final HashMap<Integer, OpenZipPanel> panelList = new HashMap<>();
    private OpenZipPanel newlyAddedPanel; //helps to hold newly created panel
    private MyTabbedPane tabbedPane;
    public ImagePreviewHandler() {

    }

    public OpenZipPanel getPanel() {
        return newlyAddedPanel;
    }

    public void addPanel(FileItemComponent fic) {
        //create data holder which run on new thread
        ImagePreviewData data = new ImagePreviewData(fic);

        //start fetchig data from dataBase
        exe.execute(data); //calls getData method of ImagePreviewData  

        //help to return newlyAddedPanel so that it can be added to the tab
        newlyAddedPanel = new OpenZipPanel(data, panelIndex);
        panelList.put(panelIndex++, newlyAddedPanel);

    }
    
    public void setTabbedPanel(MyTabbedPane tabbedPane){
        this.tabbedPane = tabbedPane;
    }
    
  

}
