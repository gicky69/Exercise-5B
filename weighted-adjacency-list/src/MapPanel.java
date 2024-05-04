import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

class Node {
    int x, y, diameter;
    String name;

    Node(int x, int y, String n) {
        this.x = x;
        this.y = y;
        this.diameter = 90;
        this.name = n;
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
    Map<Node, List<Node>> connected = new HashMap<>();

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

        print();

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (mode == Mode.ADD_NODE) {
                    int a=0;
                    addNode(e.getX(), e.getY(), "Node " + (nodes.size() + 1));
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
                if (mode == Mode.CONNECT_NODE && draggedNode2 != null)  {
                    List<Node> connections = connected.getOrDefault(draggedNode, new ArrayList<>());
                    // if already connected
                    if (connections.contains(draggedNode2)) {
                        draggedNode = null;
                        draggedNode2 = null;
                        return;
                    } else {
                        connections.add(draggedNode2);
                        connected.put(draggedNode, connections);
                        List<Node> connections2 = connected.getOrDefault(draggedNode2, new ArrayList<>());
                        connections2.add(draggedNode);
                        connected.put(draggedNode2, connections2);
                        connectNodes(draggedNode, draggedNode2);
                    }
                    // Reset dragged nodes to null to unselect them after connecting
                    draggedNode = null;
                    draggedNode2 = null;
                }
                else if (mode == Mode.SELECT_NODE) {
                    draggedNode = null;
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

    public void addNode(int x, int y, String n) {
        Node newNode = new Node(x, y, n);
        nodes.add(newNode);
        adjList.add(new LinkedList<>());
        weightList.add(new LinkedList<>());
        repaint();
    }

    public void connectNodes(Node node1, Node node2) {
        int i = nodes.indexOf(node1);
        int j = nodes.indexOf(node2);
        adjList.get(i).add(node2);
        adjList.get(j).add(node1);

        System.out.println("Connected " + node1.name + " to " + node2.name);

        AddWeight(i, j);
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
            g2D.setColor(Color.BLACK);
            g2D.drawString(node.name, node.x, node.y);

        }
    }

    private void drawRoad(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));

        for (Map.Entry<Node, List<Node>> entry : connected.entrySet()) {
            Node node1 = entry.getKey();
            for (Node node2 : entry.getValue()) {
                g2.drawLine(node1.x + node1.diameter / 2, node1.y + node1.diameter / 2, node2.x + node2.diameter / 2, node2.y + node2.diameter / 2);
            }
        }

        System.out.println("Weight: " + weightList);
        print();
    }

    public void print() {
        for (Map.Entry<Node, List<Node>> entry : connected.entrySet()) {
            System.out.print(entry.getKey().name + " -> ");
            for (Node node : entry.getValue()) {
                System.out.print(node.name + " ");
            }
            System.out.println();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void AddWeight(int i, int j) {
//        int weight = (int) Math.sqrt(Math.pow(nodes.get(i).x - nodes.get(j).x, 2) + Math.pow(nodes.get(i).y - nodes.get(j).y, 2));
        // test case
        int weight = new Random().nextInt(10)+1;
        weightList.get(i).add(weight);
        weightList.get(j).add(weight);
    }

    public void Dijkstra(int src, int dst) {
        int n = nodes.size();
        int[] dist = new int[n];
        Node[] prev = new Node[n];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, null);

        dist[src] = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> dist[nodes.indexOf(node)]));
        queue.add(nodes.get(src));

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            int uIndex = nodes.indexOf(u);

            if (u == nodes.get(dst)) {
                break;
            }

            for (Node v : adjList.get(uIndex)) {
                int vIndex = nodes.indexOf(v);
                int weightIndex = adjList.get(uIndex).indexOf(v);
                if (weightIndex < weightList.get(uIndex).size()) {
                    int altDist = dist[uIndex] + weightList.get(uIndex).get(weightIndex);

                    if (altDist < dist[vIndex]) {
                        dist[vIndex] = altDist;
                        prev[vIndex] = u;

                        // Remove the node from the queue and add it back to update its position
                        queue.remove(v);
                        queue.add(v);
                    }
                }
            }
        }

        if (prev[dst] != null) {
            LinkedList<Node> path = new LinkedList<>();
            for (Node node = nodes.get(dst); node != null; node = prev[nodes.indexOf(node)]) {
                path.addFirst(node);
            }

            System.out.println("Shortest path from " + nodes.get(src).name + " to " + nodes.get(dst).name + ":");
            for (Node node : path) {
                System.out.print(node.name + " ");
            }
            System.out.println("\nDistance: " + dist[dst]);
        } else {
            System.out.println("There is no path from " + nodes.get(src).name + " to " + nodes.get(dst).name);
        }
    }

    public void metourmom(int start, int dist) {

    }
}