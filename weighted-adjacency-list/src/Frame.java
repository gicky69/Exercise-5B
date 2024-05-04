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

    public Frame() {
        JFrame frame = new JFrame("MAP");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        mapPanel = new MapPanel();
        mapPanel.setBounds(0,0,1280,720);

        // MENU BAR
        menuBar = new JMenuBar();
        menu = new JMenu("Options");
        addNode = new JMenuItem("Add Node");
        connectNode = new JMenuItem("Connect Node");
        selectNode = new JMenuItem("Select Node");
        deleteNode = new JMenuItem("Delete Node");
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

        frame.add(mapPanel);
        frame.setVisible(true);

    }

}
