package src;

import javax.swing.*;
import java.awt.*;

public class ControlPanel {
    JPanel ControlPanel;
    MapPanel MapPanel;

    public ControlPanel(MapPanel mapPanel){
        this.MapPanel = mapPanel;
        ControlPanel = new JPanel();
        ControlPanel.setBounds(0, 720/2, 1280, 720/2);
        ControlPanel.setBackground(Color.DARK_GRAY);

        ControlPanel.setVisible(true);
    }
}
