import javax.swing.*;


public class Main {
    JFrame MainFrame;
    MapPanel MapPanel;
    ControlPanel ControlPanel;


    public Main(){
        MainFrame = new JFrame("Exercise 5B");
        MapPanel = new MapPanel();
        ControlPanel = new ControlPanel(MapPanel);

        MainFrame.setSize(1280, 720);
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setLayout(null);

        MainFrame.setVisible(true);
        MainFrame.setLocationRelativeTo(null);

        MainFrame.add(MapPanel.MapPanel);
        MainFrame.add(ControlPanel.ControlPanel);
    }

    public static void main(String[] args) {
        new Main();
    }
}