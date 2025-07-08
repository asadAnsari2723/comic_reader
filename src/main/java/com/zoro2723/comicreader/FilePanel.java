package com.zoro2723.comicreader;

import FileHandler.FileList;
import database.DatabaseConnection;
import database.TableCheck;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import zipPreprocessor.ZipPreprocessor;

public class FilePanel extends JPanel {

    private FileList<? extends File> fileList;
    private JPanel leftPanel;
    private RightPanel rightPanel;

    public RightPanel getRightPanel() {
        return rightPanel;
    }
    private JScrollPane rightScrollPane;

    FilePanel(FileList<? extends File> fileList) {
        super();
        this.fileList = fileList; //done first before creating rightPanel as reference is passed to rightPanel
        initComponents(); //creates right panel

    }

    private void initComponents() {
        // Set layout of the parent container as BorderLayout to manage left and right panel
        setLayout(new BorderLayout(5, 10));

        // Left panel with fixed width (100px) and expandable vertically
        leftPanel = new JPanel();
        leftPanel.setPreferredSize(new java.awt.Dimension(150, 0));  // Fixed width
        leftPanel.setMaximumSize(new java.awt.Dimension(100, Integer.MAX_VALUE));  // Prevent width expansion
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

        // RightPanel takes up the remaining space and handles files
        rightPanel = new RightPanel();
        rightPanel.setFileList(fileList); //adds files based on this list and list is loaded from previous state
        rightPanel.addPreviousFiles(); //set contents of the list that were previously added to the rightPanel

        //required this for working of FileItemComponent
        //must be done after creating rightPanel as it is a parentContainer.
        initForFileItemComponent();

        // Wrap rightPanel inside a JScrollPane to enable scrolling
        rightScrollPane = new JScrollPane(rightPanel);

        // Adding panels to the parent container
        this.add(leftPanel, BorderLayout.WEST);  // Left panel in WEST
        this.add(rightScrollPane, BorderLayout.CENTER);  // Right panel in CENTER
    }

    public void initForFileItemComponent() {
        //sets the required static component needed by FIC

        //set parent for all fileItemComponent as each have same parentt
        FileItemComponent.parent = rightPanel;

        //to be used by fileitemComponent for painting backgroud after selection or deselection
        FileItemComponent.parentBackgroundColor = rightPanel.getBackground();

    }

    //I think here precompilation should be done
    // Method to add files to the rightPanel
    public void addFiles() {
        //get newly added unique items and add icons of it on the panel
        Iterator<? extends File> it = this.fileList.getNewAddedItems().iterator();
        while (it.hasNext()) {
            rightPanel.addFile(it.next());
        }

        //after added clear list 
        fileList.clearNewlyAddedItems();

        // Revalidate and repaint to update UI
        rightPanel.revalidate();
        rightPanel.repaint();
        processNewZips();

    }

    private void processNewZips() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);  // Create a thread pool
        Connection con = DatabaseConnection.connect();

        if (!TableCheck.doesTableExist(con, "zip_files")) {
            TableCheck.createTables(con);
        }
        if (!TableCheck.doesTableExist(con, "image_previews")) {
            TableCheck.createTables(con);
        }

        for (FileItemComponent item : rightPanel.getAddedComponents()) {

            executorService.submit(new ZipPreprocessor(item, con));

        }

        executorService.shutdown();  // Shut down the executor after all tasks are submitted

        rightPanel.getAddedComponents().clear();
        System.out.print("New Files Processed");
    }

    public void clearRightPanel() {
        rightPanel.removeAllFiles();
    }

    // Method to remove all files from the rightPanel
    public void removeAllFiles() {
        rightPanel.removeAllFiles();
    }

    void setListener(MyTabbedPane myTabbedPane) {
        rightPanel.addPanelListener(myTabbedPane);
        RightPanel.setPane(myTabbedPane);
    }
}
