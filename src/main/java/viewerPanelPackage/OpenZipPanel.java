/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package viewerPanelPackage;

import com.zoro2723.comicreader.MyTabbedPane;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author zoro2723
 */
public class OpenZipPanel extends javax.swing.JPanel {
    
    private static int SLIDE_TIME = 3000; // in milisecond
    private final Timer slideShowTimer;
    private boolean fitWindow = false;
    private final ImagePreviewData ipd;
    private final Timer timer;
    private final RightPanelContainer rightPanel;
    public static final Logger logger = Logger.getLogger(OpenZipPanel.class.getName());
    private final int tabIndex;
    
    public int getTabIndex() {
        return tabIndex;
    }

    /**
     * Creates new form OpenZipPanel
     *
     * @param pd
     */
    public OpenZipPanel(ImagePreviewData pd, int tabIndex) {
        ipd = pd;
        this.tabIndex = tabIndex;
        initComponents();
        timer = new Timer(5000, (ActionEvent ae) -> {
            checkProcessingCompleted();
            
        });
        
        rightPanel = new RightPanelContainer(ipd);
        this.splitPane.setRightComponent(rightPanel);
        
        slideShowTimer = new Timer(SLIDE_TIME, rightPanel);
        
        timer.start();
    }
    
    {
    }
    
    private void checkProcessingCompleted() {
        if (ipd.isFetchingCompleted()) {
            logger.info("Processing Complete");
            enableButtons();
            rightPanel.getNewImage();
            timer.stop();
        }
        
    }
    
    private void enableButtons() {
        openButtoon.setEnabled(true);
        slideShowButton.setEnabled(true);
        closeTabButton.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        toolPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        openButtoon = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        closeTabButton = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(2, 25), new java.awt.Dimension(32767, 50));
        jButton7 = new javax.swing.JButton();
        slideShowButton = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton17 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        fitWindowToggleButton = new javax.swing.JToggleButton();
        jButton14 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        rootPanel = new javax.swing.JPanel();
        splitPane = new javax.swing.JSplitPane();
        leftPanel = new javax.swing.JPanel();
        allItemScrollPane = new javax.swing.JScrollPane();
        allItemPanel = new javax.swing.JPanel();
        bookmarkScrollPane = new javax.swing.JScrollPane();
        bookmarkPanel = new javax.swing.JPanel();
        fullScreen = new javax.swing.JPanel();
        fullScreenImagePanel = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        setLayout(new java.awt.BorderLayout());

        toolPanel.setPreferredSize(new java.awt.Dimension(850, 50));

        jPanel5.setBorder(null);
        jPanel5.setPreferredSize(new java.awt.Dimension(775, 50));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/back.png"))); // NOI18N
        jButton1.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);

        openButtoon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/open.png"))); // NOI18N
        openButtoon.setEnabled(false);
        openButtoon.setPreferredSize(new java.awt.Dimension(32, 32));
        openButtoon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtoonActionPerformed(evt);
            }
        });
        jPanel5.add(openButtoon);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/bookmarked.png"))); // NOI18N
        jButton3.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel5.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/bookmark.png"))); // NOI18N
        jButton4.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel5.add(jButton4);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/zipBookmarked.png"))); // NOI18N
        jButton5.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel5.add(jButton5);

        closeTabButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/close.png"))); // NOI18N
        closeTabButton.setEnabled(false);
        closeTabButton.setPreferredSize(new java.awt.Dimension(32, 32));
        closeTabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeTabButtonActionPerformed(evt);
            }
        });
        jPanel5.add(closeTabButton);
        jPanel5.add(filler1);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/left.png"))); // NOI18N
        jButton7.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton7);

        slideShowButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/slideShow.png"))); // NOI18N
        slideShowButton.setEnabled(false);
        slideShowButton.setPreferredSize(new java.awt.Dimension(32, 32));
        slideShowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slideShowButtonActionPerformed(evt);
            }
        });
        jPanel5.add(slideShowButton);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolBarIcons/right.png"))); // NOI18N
        jButton9.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel5.add(jButton9);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.TRAILING));

        jButton17.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel6.add(jButton17);

        jButton16.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton16);

        fitWindowToggleButton.setPreferredSize(new java.awt.Dimension(32, 32));
        fitWindowToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fitWindowToggleButtonActionPerformed(evt);
            }
        });
        jPanel6.add(fitWindowToggleButton);

        jButton14.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel6.add(jButton14);

        jButton13.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel6.add(jButton13);

        jButton12.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel6.add(jButton12);

        jButton11.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel6.add(jButton11);

        jButton10.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel6.add(jButton10);

        javax.swing.GroupLayout toolPanelLayout = new javax.swing.GroupLayout(toolPanel);
        toolPanel.setLayout(toolPanelLayout);
        toolPanelLayout.setHorizontalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );
        toolPanelLayout.setVerticalGroup(
            toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolPanelLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        add(toolPanel, java.awt.BorderLayout.NORTH);

        rootPanel.setLayout(new java.awt.CardLayout());

        splitPane.setDividerLocation(150);

        leftPanel.setMaximumSize(new java.awt.Dimension(150, 2147483647));
        leftPanel.setMinimumSize(new java.awt.Dimension(150, 28));
        leftPanel.setPreferredSize(new java.awt.Dimension(150, 280));
        leftPanel.setLayout(new java.awt.CardLayout());

        allItemScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        allItemPanel.setMaximumSize(new java.awt.Dimension(150, 32767));
        allItemPanel.setMinimumSize(new java.awt.Dimension(150, 100));
        allItemPanel.setPreferredSize(new java.awt.Dimension(150, 10));
        allItemPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        allItemScrollPane.setViewportView(allItemPanel);

        leftPanel.add(allItemScrollPane, "card2");

        javax.swing.GroupLayout bookmarkPanelLayout = new javax.swing.GroupLayout(bookmarkPanel);
        bookmarkPanel.setLayout(bookmarkPanelLayout);
        bookmarkPanelLayout.setHorizontalGroup(
            bookmarkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        bookmarkPanelLayout.setVerticalGroup(
            bookmarkPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        bookmarkScrollPane.setViewportView(bookmarkPanel);

        leftPanel.add(bookmarkScrollPane, "card3");

        splitPane.setLeftComponent(leftPanel);

        rootPanel.add(splitPane, "card2");

        fullScreen.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 339, Short.MAX_VALUE)
        );

        fullScreenImagePanel.setViewportView(jPanel4);

        fullScreen.add(fullScreenImagePanel, java.awt.BorderLayout.CENTER);

        rootPanel.add(fullScreen, "card3");

        add(rootPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        rightPanel.card.show(rightPanel, "preview");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void fitWindowToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fitWindowToggleButtonActionPerformed
        if (fitWindowToggleButton.isSelected()) {
            //fitWindowToggleButton.setBackground(Color.LIGHT_GRAY);
            rightPanel.changePanel("fitWindow");
        } else {
            //fitWindowToggleButton.setBackground(getParent().getBackground());
            rightPanel.changePanel("show");
        }
    }//GEN-LAST:event_fitWindowToggleButtonActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

    private void openButtoonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtoonActionPerformed
        File file = rightPanel.extractImage();
        
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot open file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        

    }//GEN-LAST:event_openButtoonActionPerformed

    private void slideShowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slideShowButtonActionPerformed
        if (slideShowTimer.isRunning())
            slideShowTimer.stop();
        else {
            slideShowTimer.start();
        }
    }//GEN-LAST:event_slideShowButtonActionPerformed

    private void closeTabButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeTabButtonActionPerformed
        if (this.getParent() instanceof MyTabbedPane pane) {
            //System.out.println("Close button called");
            pane.removeTabAt(this.tabIndex);
        }
        //System.out.println(pane);
    }//GEN-LAST:event_closeTabButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel allItemPanel;
    private javax.swing.JScrollPane allItemScrollPane;
    private javax.swing.JPanel bookmarkPanel;
    private javax.swing.JScrollPane bookmarkScrollPane;
    private javax.swing.JButton closeTabButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JToggleButton fitWindowToggleButton;
    private javax.swing.JPanel fullScreen;
    private javax.swing.JScrollPane fullScreenImagePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton openButtoon;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JButton slideShowButton;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JPanel toolPanel;
    // End of variables declaration//GEN-END:variables
}
