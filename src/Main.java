package src;

import javax.swing.*;


public class Main implements Runnable {
    JFrame MainFrame;
    MapPanel MapPanel;
    ControlPanel ControlPanel;
    Thread MainThread;


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
        MainFrame.setFocusableWindowState(true);

        start();
    }

    public void start() {
        MainThread = new Thread(this);
        MainThread.start();
    }

    public void run() {
        while (true){
            MainFrame.revalidate();
            MainFrame.repaint();

            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
            new Main();
    }
}