package src;

import javax.swing.*;
import java.awt.*;

public class ControlPanel {
    JPanel ControlPanel;
    MapPanel MapPanel;
    Main MainFrame;

    public ControlPanel(int width, int height, MapPanel mapPanel, Main mainFrame){
        this.MapPanel = mapPanel;
        ControlPanel = new JPanel();
        ControlPanel.setBounds(0, height/2, width, height/2);
        ControlPanel.setBackground(Color.DARK_GRAY);

        ControlPanel.setVisible(true);
    }
}
