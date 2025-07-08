/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zoro2723.comicreader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import viewerPanelPackage.OpenZipPanel;

/**
 *
 * @author zoro2723
 */
public class MyTabbedPane extends JTabbedPane implements createPreviewPanel {

    public MyTabbedPane() {
        super();
    }

    @Override
    public void createPanel(MyEventObject evt) {
        if (evt.getSource() instanceof FileItemComponent) {
            this.addTab(evt.getZipName(), evt.getPanel());
            evt.getPanel().requestFocus();
            this.revalidate();
            this.repaint();
            Logger.getLogger(MyTabbedPane.class.getName()).log(Level.INFO, "OpenZip Panel Added to the TabbedPane");
        }
    }

    @Override
    public void removePanel(MyEventObject evt) {
        if (evt.getSource() instanceof OpenZipPanel panel) {
            this.removeTabAt(panel.getTabIndex());
            this.revalidate();
            this.repaint();
        }
    }

}
