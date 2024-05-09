package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MapPanel {
    JPanel MapPanel;
    Main MainFrame;



    public MapPanel(int width,int height, Main mainFrame){
        this.MainFrame = mainFrame;
        MapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
            }
        };
        MapPanel.setBounds(0, 0, width, height/2);
        MapPanel.setBackground(Color.GRAY);

        MapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addNode(e.getX(), e.getY());
            }
        });
    }

    public void addNode(int x, int y){
        // Add a node to the map
        System.out.println("Node added at: " + x + ", " + y);
        Graphics g = MapPanel.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y, 10, 10);
        MapPanel.repaint();


    }
}
