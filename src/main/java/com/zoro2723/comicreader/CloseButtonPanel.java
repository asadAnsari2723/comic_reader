/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zoro2723.comicreader;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author zoro2723
 */
public class CloseButtonPanel extends JPanel implements MouseListener {

    public static int xCenter = 5, yCenter = 5, offset = 5;
    public static int circleWidth = 20, circleHeight = 20;
    public static Dimension PREFFERED_SIZE = new Dimension(30, 30);
    private FileItemComponent parent;
    private boolean hover = false;

    public CloseButtonPanel(FileItemComponent parent) {
        setPreferredSize(PREFFERED_SIZE);
       
        setOpaque(false);
        this.parent = parent;
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent me) {
        parent.removeFile();
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent me) {
        hover = true;
        repaint();
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent me) {
        hover = false;
        repaint();
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent me) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent me) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke(2));
        if (hover) {
            g2d.setColor(Color.RED);
            g2d.fillOval(xCenter, yCenter, circleWidth, circleHeight);
            g2d.setColor(Color.YELLOW);
            g2d.drawOval(xCenter, yCenter, circleWidth, circleHeight);
        } else {
            g2d.setColor(Color.LIGHT_GRAY);

            g2d.drawOval(xCenter, yCenter, circleWidth, circleHeight);

        }

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(2));
        // g2d.drawLine(xCenter, yCenter, circleWidth, circleHeight);
        g2d.drawLine(xCenter + offset, yCenter + offset, xCenter + 20 - offset, yCenter + 20 - offset);
        g2d.drawLine(xCenter + 20 - offset, yCenter + offset, xCenter + offset, yCenter + 20 - offset);

    }
}
