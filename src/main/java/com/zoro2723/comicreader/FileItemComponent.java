package com.zoro2723.comicreader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class FileItemComponent extends JPanel {

    public static Color parentBackgroundColor;
    public static RightPanel parent;

    //sets fixed size for nameLabel in each obj
    public static final Dimension nameLabelSize = new Dimension(100, 20);

    //used for selection among components
    public static Set<FileItemComponent> selectedComponents = new HashSet<>();
    public static FileItemComponent lastSelectedItem = null; // To track the last selected file

    public File file;

    private final JLabel iconLabel;
    private final JLabel nameLabel;
    private final CloseButtonPanel closeButtonPanel;
    private boolean isSelected = false;
    private JProgressBar progressBar;

    
    
    // Constructor
    public FileItemComponent(File file) {

        this.file = file;
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setPreferredSize(new Dimension(150, 150));

        // Load icon from resources (zipIcons/fileIcon.png)
        URL iconURL = getClass().getResource("/zipIcons/fileIcon.png"); // Access file from resources
        if (iconURL != null) {
            iconLabel = new JLabel(new ImageIcon(iconURL), JLabel.CENTER);
        } else {
            iconLabel = new JLabel(UIManager.getIcon("FileView.fileIcon"), JLabel.CENTER); // Fallback icon if not found
        }

        closeButtonPanel = new CloseButtonPanel(this);
        closeButtonPanel.addMouseListener(closeButtonPanel);

        nameLabel = new JLabel(file.getName(), JLabel.CENTER);
        nameLabel.setPreferredSize(nameLabelSize);

        iconLabel.setBounds(25, 25, 100, 100);
        nameLabel.setBounds(25, 125, 100, 25);

        closeButtonPanel.setBounds(120, 0, 30, 30);

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setBounds(5, 5, 100, 20);
        add(progressBar);
        add(iconLabel);
        add(nameLabel);
        add(closeButtonPanel);

        this.setToolTipText(nameLabel.getText());

        // Context Menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem renameItem = new JMenuItem("Rename");
        JMenuItem deleteItem = new JMenuItem("Delete");
        JMenuItem removeItem = new JMenuItem("Remove");

        popupMenu.add(openItem);
        popupMenu.add(renameItem);
        popupMenu.add(deleteItem);
        popupMenu.add(removeItem);

        // Add Actions
        openItem.addActionListener(e -> openFile());
        renameItem.addActionListener(e -> renameFile());
        deleteItem.addActionListener(e -> deleteFile());
        removeItem.addActionListener(e -> removeFile());

        // Mouse Listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    deselectAll();
                    select();
                    popupMenu.show(FileItemComponent.this, e.getX(), e.getY());
                } else if (SwingUtilities.isLeftMouseButton(e)) {

                    if (e.isControlDown()) {
                        toggleSelection(); // Ctrl-click for toggle
                    } else if (e.isShiftDown()) {
                        selectRange(); // Shift-click for range selection
                    } else {
                        deselectAll();
                        select(); // Click without modifier selects the clicked file
                    }
                }

            }

        }
        );
    }

    private void toggleSelection() {
        if (isSelected) {
            deselect();
        } else {
            select();
        }
    }

    private void select() {
        isSelected = true;
        selectedComponents.add(this);
        setBackground(Color.CYAN);
        repaint();
        lastSelectedItem = this; // Update last selected item
    }

    public void deselect() {
        isSelected = false;
        selectedComponents.remove(this);
        setBackground(FileItemComponent.parentBackgroundColor); //why because reused multiple time
        repaint();
    }

    public void deselectAll() {
        for (FileItemComponent item : new HashSet<>(selectedComponents)) {
            item.deselect();
        }
        selectedComponents.clear();
    }

    private void selectRange() {
        if (lastSelectedItem != null) {
            Container parent = getParent();
            List<FileItemComponent> components = getParentComponents();

            // Get indices of last and current clicked file
            int startIndex = components.indexOf(lastSelectedItem);
            int endIndex = components.indexOf(this);

            // Select files between last and current clicked file
            if (startIndex <= endIndex) {
                for (int i = startIndex; i <= endIndex; i++) {
                    components.get(i).select();
                }
            } else {
                for (int i = startIndex; i >= endIndex; i--) {
                    components.get(i).select();
                }
            }
        }
    }

    private List<FileItemComponent> getParentComponents() {
        Container parent = getParent();

        List<FileItemComponent> components = new ArrayList<>();
        for (Component component : parent.getComponents()) {
            if (component instanceof FileItemComponent) {
                components.add((FileItemComponent) component);
            }
        }
        return components;
    }

    private void openFile() {
//        try {
//            Desktop.getDesktop().open(file);
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Cannot open file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }

        MyEventObject evt = new MyEventObject(this);
        parent.invokeEvent(evt);


    }

    public void removeFile() {

        parent.removeFile(this.file); // removes files from mainlist
        parent.remove(this); // removes this component

    }

    private void renameFile() {
        String newName = JOptionPane.showInputDialog(this, "Enter new name:", file.getName());
        if (newName != null && !newName.trim().isEmpty()) {
            File newFile = new File(file.getParentFile(), newName);
            if (file.renameTo(newFile)) {
                file = newFile;
                nameLabel.setText(file.getName());
                JOptionPane.showMessageDialog(this, "File renamed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Rename failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteFile() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this file?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (file.delete()) {
                JOptionPane.showMessageDialog(this, "File deleted successfully!");
                getParent().remove(this);
                getParent().revalidate();
                getParent().repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void update(int progress) {
        progressBar.setValue(progress);

    }
    
    
    //getter and setters
    
    public String getFileName() {
        return file.getName();
    }
    
    public String getAbsoluteFileName(){
        return file.getAbsolutePath();
    }
}
