import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class Node {
    int x, y, diameter;

    Node(int x, int y, int diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    boolean isOver(int mx, int my) {
        double distance = Math.sqrt(Math.pow(mx - x, 2) + Math.pow(my - y, 2));
        return distance <= diameter / 2.0;
    }
}

public class MapPanel extends JPanel {
    ArrayList<Node> nodes = new ArrayList<>();
    Node draggedNode = null;

    public MapPanel() {
        this.setSize(1280, 720);
        this.setBackground(Color.GRAY);
        this.setVisible(true);

        nodes.add(new Node(100, 100, 90));
        nodes.add(new Node(400, 400, 90));
        nodes.add(new Node(1000, 300, 90));
        nodes.add(new Node(800, 50, 90));

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Node node : nodes) {
                    if (node.isOver(e.getX(), e.getY())) {
                        draggedNode = node;
                        break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedNode != null) {
                    draggedNode.x = e.getX();
                    draggedNode.y = e.getY();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                draggedNode = null;
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawNodes(g);
        drawRoad(g);
    }

    private void drawNodes(Graphics g) {
        g.setColor(Color.RED);
        for (Node node : nodes) {
            if (node == draggedNode) {
                g.setColor(Color.BLUE);
                g.drawOval(node.x, node.y, node.diameter, node.diameter);
            }
            else {
                g.setColor(Color.RED);
                g.fillOval(node.x, node.y, node.diameter, node.diameter);
            }

            g.fillOval(node.x, node.y, node.diameter, node.diameter);
        }
    }

    private void drawRoad(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        Node node1 = nodes.get(0);
        Node node2 = nodes.get(1);
        Node node3 = nodes.get(2);
        Node node4 = nodes.get(3);

        // Connect Node(0) to Node(1)
        g2.drawLine(node1.x + node1.diameter / 2, node1.y + node1.diameter / 2, node2.x + node2.diameter / 2, node2.y + node2.diameter / 2);
        // Connect Node(1) to Node(2)
        g2.drawLine(node2.x + node2.diameter / 2, node2.y + node2.diameter / 2, node3.x + node3.diameter / 2, node3.y + node3.diameter / 2);
        // Connect Node(2) to Node(3)
        g2.drawLine(node3.x + node3.diameter / 2, node3.y + node3.diameter / 2, node4.x + node4.diameter / 2, node4.y + node4.diameter / 2);
        // Connect Node(1) to Node(3)
        g2.drawLine(node2.x + node2.diameter / 2, node2.y + node2.diameter / 2, node4.x + node4.diameter / 2, node4.y + node4.diameter / 2);
    }
}