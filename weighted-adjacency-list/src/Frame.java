import javax.swing.*;
import java.awt.*;

public class Frame {
    MapPanel MapPanel;

    public Frame() {
        JFrame frame = new JFrame("MAP");

        MapPanel = new MapPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        frame.add(MapPanel);
        frame.setVisible(true);
    }
}
