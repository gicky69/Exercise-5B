import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlPanel {
    JPanel ControlPanel;
    MapPanel MapPanel;
    JButton AddNode;
    JButton AddEdge;

    public ControlPanel(MapPanel mapPanel){
        this.MapPanel = mapPanel;
        ControlPanel = new JPanel();
        ControlPanel.setBounds(0, 720/2, 1280, 720/2);
        ControlPanel.setBackground(Color.DARK_GRAY);

        AddNode = new JButton("Add Node");
        //test
        AddNode.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MapPanel.MapPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        MapPanel.addNode(e.getX(), e.getY());
                    }
                });
            }
        });

        ControlPanel.add(AddNode);
        ControlPanel.setVisible(true);
    }
}
