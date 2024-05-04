import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Node {
    int x, y, diameter;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.diameter = 90;
    }

    public boolean isOver(Node node, int mx, int my) {
        int radius = node.diameter/2;
        int centerX = node.x + radius;
        int centerY = node.y + radius;
        double distance = Math.pow(mx - centerX, 2) + Math.pow(my - centerY, 2);
        return distance <= Math.pow(radius,2);
    }
}

public class MapPanel extends JPanel {
    ArrayList<Node> nodes = new ArrayList<>();
    ArrayList<LinkedList<Node>> adjList = new ArrayList<LinkedList<Node>>();
    ArrayList<LinkedList<Integer>> weightList = new ArrayList<LinkedList<Integer>>();
    Node draggedNode = null;
    Node draggedNode2 = null;
    Mode mode = Mode.ADD_NODE;
    enum Mode {
        ADD_NODE,
        SELECT_NODE,
        CONNECT_NODE,
        DELETE_NODE
    }

    public MapPanel() {
        this.setPreferredSize(new Dimension(1280, 720));
        this.setLayout(null);
        this.setVisible(true);

//        nodes.add(new Node(100, 100));
//        nodes.add(new Node(400, 400));
//        nodes.add(new Node(1000, 300));
//        nodes.add(new Node(800, 50));
//
//        for (int i = 0; i < nodes.size(); i++) {
//            LinkedList<Node> list = new LinkedList<Node>();
//            list.add(nodes.get(i));
//            adjList.add(list);
//            weightList.add(new LinkedList<Integer>());
//        }
//
//        AddWeight(0, 1);
//        AddWeight(1, 2);
//        AddWeight(2, 3);
//        AddWeight(1, 3);


        print();
        //DijkstraAlgo(0, 3);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (mode == Mode.ADD_NODE) {
                    addNode(new Node(e.getX(),e.getY()));
                    for (int i = 0; i < nodes.size(); i++) {
                        LinkedList<Node> list = new LinkedList<Node>();
                        list.add(nodes.get(i));
                        adjList.add(list);
                        weightList.add(new LinkedList<Integer>());
                    }

                } else if (mode == Mode.SELECT_NODE) {
                    draggedNode2 = null;
                    if (draggedNode == null) {
                        for (Node node : nodes) {
                            if (node.isOver(node, e.getX(), e.getY())) {
                                draggedNode = node;
                                break;
                            }
                        }
                    }

                } else if (mode == Mode.CONNECT_NODE) {

                    if (draggedNode == null) {
                        for (Node node : nodes) {
                            if (node.isOver(node, e.getX(), e.getY())) {
                                draggedNode = node;
                                break;
                            }
                        }
                    } else if (draggedNode2 == null) {
                        for (Node node : nodes) {
                            if (node != draggedNode && node.isOver(node, e.getX(), e.getY())) {
                                draggedNode2 = node;
                                break;
                            }
                        }
                    }

                } else if (mode == Mode.DELETE_NODE) {
                    for (Node node : nodes) {
                        if (node.isOver(node, e.getX(), e.getY())) {
                            deleteNode(node);
                            break;
                        }
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // select and drag node
                if (mode == Mode.CONNECT_NODE) {
                    if (draggedNode2 != null) {
                        draggedNode2.x = e.getX();
                        draggedNode2.y = e.getY();
                        repaint();
                    } else if (draggedNode != null) {
                        draggedNode.x = e.getX();
                        draggedNode.y = e.getY();
                        repaint();
                    }
                } else if (mode == Mode.SELECT_NODE) {
                    if (draggedNode != null) {
                        draggedNode.x = e.getX();
                        draggedNode.y = e.getY();
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (mode == Mode.SELECT_NODE) {
                    draggedNode = null;
                } else if (mode == Mode.CONNECT_NODE && draggedNode2 != null)  {
                    // Reset dragged nodes to null to unselect them after connecting
                    draggedNode = null;
                    draggedNode2 = null;
                }
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
    }

    // allow draw methods to run
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawNodes(g);
        drawRoad(g);
    }

    private void addNode(Node node) {
        nodes.add(node);
        repaint();
    }

    private void deleteNode(Node node) {
        nodes.remove(node);
        repaint();
    }

    private void drawNodes(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.RED);
        for (Node node : nodes) {
            g2D.setColor(Color.RED); // Set color to red by default
            if (node == draggedNode || node == draggedNode2) {
                g2D.setColor(Color.BLUE); // Set color to blue if node is selected
            }
            g2D.fillOval(node.x, node.y, node.diameter, node.diameter);
        }
    }

    private void drawRoad(Graphics g){

        if (draggedNode != null && draggedNode2 != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));

            Node node1 = draggedNode;
            Node node2 = draggedNode2;
            g2.drawLine(node1.x + node1.diameter / 2, node1.y + node1.diameter / 2, node2.x + node2.diameter / 2, node2.y + node2.diameter / 2);
        }

//        Node node1 = nodes.get(0);
//        Node node2 = nodes.get(1);
//        Node node3 = nodes.get(2);
//        Node node4 = nodes.get(3);

//        // Connect Node(0) to Node(1)
//        g2.drawLine(node1.x + node1.diameter / 2, node1.y + node1.diameter / 2, node2.x + node2.diameter / 2, node2.y + node2.diameter / 2);
//        // Connect Node(1) to Node(2)
//        g2.drawLine(node2.x + node2.diameter / 2, node2.y + node2.diameter / 2, node3.x + node3.diameter / 2, node3.y + node3.diameter / 2);
//        // Connect Node(2) to Node(3)
//        g2.drawLine(node3.x + node3.diameter / 2, node3.y + node3.diameter / 2, node4.x + node4.diameter / 2, node4.y + node4.diameter / 2);
//        // Connect Node(1) to Node(3)
//        g2.drawLine(node2.x + node2.diameter / 2, node2.y + node2.diameter / 2, node4.x + node4.diameter / 2, node4.y + node4.diameter / 2);
//
//        System.out.println("Road 1: " + (int) Math.sqrt(Math.pow(node2.x - node1.x, 2) + Math.pow(node2.y - node1.y, 2)));
//        System.out.println("Road 2: " + (int) Math.sqrt(Math.pow(node3.x - node2.x, 2) + Math.pow(node3.y - node2.y, 2)));
//        System.out.println("Road 3: " + (int) Math.sqrt(Math.pow(node4.x - node3.x, 2) + Math.pow(node4.y - node3.y, 2)));
//        System.out.println("Road 4: " + (int) Math.sqrt(Math.pow(node4.x - node2.x, 2) + Math.pow(node4.y - node2.y, 2)));
    }

    // A, B, C, D, E
//    public void AddWeight(int srcNode, int dstTo) {
//        Node node1 = nodes.get(srcNode);
//        Node node2 = nodes.get(dstTo);
//        int weight = (int) Math.sqrt(Math.pow(node2.x - node1.x, 2) + Math.pow(node2.y - node1.y, 2));
//
//        LinkedList<Node> list = adjList.get(srcNode);
//        list.add(node2);
//
//        LinkedList<Integer> weightList = this.weightList.get(srcNode);
//        weightList.add(weight);
//    }

    public void print() {
        for (LinkedList<Node> list : adjList) {
            for (Node node : list) {
                System.out.print(node + " -> ");
            }
            System.out.println();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

//    public void DijkstraAlgo(int src, int dst){
//        int[] dist = new int[nodes.size()];
//        int[] prev = new int[nodes.size()];
//        boolean[] visited = new boolean[nodes.size()];
//
//        for (int i=0;i<nodes.size();i++){
//            dist[i] = Integer.MAX_VALUE;
//            prev[i] = -1;
//            visited[i] = false;
//        }
//
//        dist[src] = 0;
//        Queue<Integer> path = new LinkedList<>();
//        path.add(src);
//
//        while (!path.isEmpty()) {
//            int u = path.poll();
//            visited[u] = true;
//
//            LinkedList<Node> list = adjList.get(u);
//            LinkedList<Integer> weightList = this.weightList.get(u);
//
//            System.out.println(list.size());
//            for (int i = 1; i < list.size(); i++) {
//                int weight = weightList.get(i);
//                Node node = list.get(i);
//                int v = nodes.indexOf(node);
//
//
//                if (!visited[v] && dist[v] > dist[u] + weight) {
//                    dist[v] = dist[u] + weight;
//                    prev[v] = u;
//                    path.add(v);
//                }
//            }
//        }
//
//        System.out.println("Shortest Path from " + src + " to " + dst + " is " + dist[dst]);
//    }
}