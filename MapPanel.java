import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MapPanel {
    JPanel MapPanel;

    public MapPanel(){
        MapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
            }
        };
        MapPanel.setBounds(0, 0, 1280, 720/2);
        MapPanel.setBackground(Color.GRAY);
    }

    public void addNode(int x, int y){
        // Add a node to the map
        Graphics g = MapPanel.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        MapPanel.repaint();
    }
}
