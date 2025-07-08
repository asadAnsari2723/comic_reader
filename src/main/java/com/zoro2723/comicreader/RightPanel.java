package com.zoro2723.comicreader;

import static com.zoro2723.comicreader.FileItemComponent.selectedComponents;
import FileHandler.FileList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RightPanel extends JPanel {

    private static MyTabbedPane pane;

    public static MyTabbedPane getPane() {
        return pane;
    }

    public static void setPane(MyTabbedPane pane) {
        RightPanel.pane = pane;
    }
    private final List<createPreviewPanel> listener = new ArrayList<>();
    private FileList<? extends File> fileList;
    private final ArrayList<FileItemComponent> addedComponents = new ArrayList<>();

    // Constructor
    public RightPanel() {

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));  // hgap = 30 . vgap = 50

        //setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding around content
        addListeners();

    }

    public void setFileList(FileList<? extends File> fileList) {
        this.fileList = fileList;

    }

    public void addPreviousFiles() {
        Iterator<? extends File> it = this.fileList.getMainList().iterator();
        while (it.hasNext()) {
            addFile(it.next());
        }

        updateRightPanelUI();
    }

    public void addPanelListener(createPreviewPanel ln) {
        listener.add(ln);
    }

    public void removePanelListener(createPreviewPanel ln) {
        listener.remove(ln);
    }

    public void invokeEvent(MyEventObject e) {
        System.out.println("Invoked Eventt");
        for (createPreviewPanel p : listener) {
            p.createPanel(e);
        }
    }

    // Method to add a file to the rightPanel
    public void addFile(File file) {
        FileItemComponent item = new FileItemComponent(file);
        this.add(item);
        addedComponents.add(item);
    }

    public ArrayList<FileItemComponent> getAddedComponents() {
        return addedComponents;
    }

    public void updateRightPanelUI() {
        revalidate();
        repaint();
    }

    public void removeFile(File file) {
        fileList.remove(file);

        //to repaint components
        updateRightPanelUI();
    }

    // Method to remove all files from the rightPanel
    public void removeAllFiles() {
        this.removeAll();  // Remove all components (including files)

        updateRightPanelUI();
    }

    private void addListeners() {
        // Mouse listener for deselection
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    deselectAll();
                }
            }

            private void deselectAll() {
                for (FileItemComponent item : new HashSet<>(selectedComponents)) {
                    item.deselect();
                }
                selectedComponents.clear();
            }
        });
    }

    // Override paintComponent to draw custom content (the "Add Files" message) in the rightPanel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // If the panel has no components (i.e., no files), draw "Add Files" message
        if (getComponentCount() == 0) {
            String message = "Add Files";
            Font font = new Font("Arial", Font.BOLD, 30);
            g.setFont(font);
            g.setColor(Color.GRAY);

            // Calculate the position to center the message in the rightPanel
            int messageWidth = g.getFontMetrics().stringWidth(message);
            int messageHeight = g.getFontMetrics().getHeight();
            int x = (getWidth() - messageWidth) / 2;
            int y = (getHeight() + messageHeight) / 2;

            // Draw the message on the rightPanel
            g.drawString(message, x, y);
        }
    }

}
