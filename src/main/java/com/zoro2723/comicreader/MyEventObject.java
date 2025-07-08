/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zoro2723.comicreader;

import java.util.EventObject;
import viewerPanelPackage.OpenZipPanel;

/**
 *
 * @author zoro2723
 */
public class MyEventObject extends EventObject {
    
    private String zipName;

    public String getZipName() {
        return zipName;
    }
    
    public MyEventObject(FileItemComponent source) {
        super(source);
        MainFrame.iph.addPanel(source);
        zipName = source.getFileName();
    }
    
    public MyEventObject(OpenZipPanel source){
        super(source);
        MainFrame.iph.removePanel(source);
        
    }

    public OpenZipPanel getPanel() {
        return MainFrame.iph.getPanel();
    }

}
