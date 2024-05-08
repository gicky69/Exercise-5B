import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Frame {
    MapPanel mapPanel;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem addNode, connectNode, selectNode, deleteNode;
    JButton Dijkstra;
    ImageIcon bgImage;
    JLabel bgLabel;

    JPanel bgPanel;
    public Frame() {
        JFrame frame = new JFrame("MAP");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        /*bgImage = new ImageIcon("weighted-adjacency-list/images/vector-road-top-view 1920x1344.jpg");
        bgLabel = new JLabel();
        bgLabel.setIcon(bgImage);
        bgLabel.setBounds(0,0, 1920, 1344);*/

        JPanel bgPanel = new JPanel();
        bgPanel.setLayout(null);
        bgPanel.setBounds(0, -50, 1920, 1080);


        bgImage = new ImageIcon("weighted-adjacency-list/images/game.png");
        JLabel bgLabel = new JLabel();
        bgLabel.setIcon(bgImage);
        bgPanel.add(bgLabel);
        bgLabel.setBounds(0, -50, 1920, 1080);

        mapPanel = new MapPanel();
        mapPanel.setLayout(null);
        mapPanel.setBounds(0,-50,1920,1080);
        //mapPanel.add(bgLabel);


        // MENU BAR
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        addNode = new JMenuItem("Add Node");
        connectNode = new JMenuItem("Connect Node");
        selectNode = new JMenuItem("Select Node");
        deleteNode = new JMenuItem("Delete Node");
        Dijkstra = new JButton("Dijkstra");

        Dijkstra.setBounds(0, 0, 100, 50);
        Dijkstra.setVisible(true);


        menu.add(addNode);
        menu.add(connectNode);
        menu.add(selectNode);
        menu.add(deleteNode);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);



        addNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setMode(MapPanel.Mode.ADD_NODE);
            }
        });

        selectNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setMode(MapPanel.Mode.SELECT_NODE);
            }
        });

        connectNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setMode(MapPanel.Mode.CONNECT_NODE);
            }
        });

        deleteNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.setMode(MapPanel.Mode.DELETE_NODE);
            }
        });

        Dijkstra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = JOptionPane.showInputDialog("Enter start node");
                String end = JOptionPane.showInputDialog("Enter end node");
                mapPanel.clearWeights();

                mapPanel.drawRoad(mapPanel.getGraphics());

                mapPanel.Dijkstra(Integer.parseInt(start), Integer.parseInt(end));
            }
        });

        frame.add(Dijkstra);
        //frame.add(bgLabel);
        frame.add(bgPanel);
        frame.add(mapPanel);

        //bgLabel.setVisible(true);
        //mapPanel.add(imageLabel);
        frame.setVisible(true);


    }

}
