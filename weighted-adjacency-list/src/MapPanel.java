import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

class Node {
    int x, y, diameter;

    String name;
    ImageIcon yellowNode, blueNode;


    Node(int x, int y, String n) {

        //added 2 images for the node and selected node
        yellowNode = new ImageIcon("weighted-adjacency-list/images/whiteduck-removebg-preview.png");
        blueNode = new ImageIcon("weighted-adjacency-list/images/whiteduck-removebg-preview.png");

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

    ImageIcon bgImage;
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

        try {
            bgImage = new ImageIcon("weighted-adjacency-list/images/game.png");
        } catch (Exception e) {
            // Handle the exception: Log the error, use a placeholder image, etc.
            System.err.println("Error loading background image: " + e.getMessage());
            bgImage = new ImageIcon("path/to/placeholder.jpg"); // Replace with your placeholder image path (optional)
        }

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (mode == Mode.ADD_NODE) {
                    addNode(e.getX(), e.getY(), "Duck " + (nodes.size() + 1));
                    LinkedList<Node> list = new LinkedList<Node>();
                    adjList.add(list);
                    weightList.add(new LinkedList<Integer>());
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
        drawBackground(g);
        drawNodes(g);
        drawRoad(g);
    }

    private void drawBackground(Graphics g){
        if (bgImage != null) {
            g.drawImage(bgImage.getImage(), 0, -50, null);
        } else {
            // Handle case if image couldn't be loaded (optional)
            g.setColor(Color.gray);  // Use a light gray placeholder
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void drawNode(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
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
        AddWeight(j,i);
    }

    private void deleteNode(Node node) {
        int index = nodes.indexOf(node);
        // Remove the node from the nodes list
        nodes.remove(node);
        // Remove the node's adjacency list and weight list
        adjList.remove(index);
        weightList.remove(index);

        // check all nodes and delete the connections if there are removed nodes.
        for (int i = 0; i < nodes.size(); i++) {
            int adjIndex = adjList.get(i).indexOf(node);
            if (adjIndex != -1) {
                adjList.get(i).remove(adjIndex);
                weightList.get(i).remove(adjIndex);
            }
        }

        // Remove the node from the connected map
        connected.remove(node);
        // Iterate over all remaining entries in the connected map and remove any connections to the deleted node
        for (Map.Entry<Node, List<Node>> entry : connected.entrySet()) {
            entry.getValue().remove(node);
        }

        repaint();
    }

    private void drawNodes(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.RED);
        for (Node node : nodes) {

            //added node images:
            g2D.drawImage(node.yellowNode.getImage(), node.x, node.y, node.diameter, node.diameter, null);
            g2D.setColor(Color.BLACK);
            g2D.drawString(node.name, node.x, node.y);

            if (node == draggedNode || node == draggedNode2){
                g2D.drawImage(node.blueNode.getImage(), node.x, node.y, node.diameter, node.diameter, null);
                g2D.setColor(Color.black);
                g2D.drawString(node.name, node.x, node.y);
            }

        }
    }

    private void drawRoad(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));

        //setting a larger font text for distance
        Font currentFont = g2.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2.5F);
        g2.setFont(newFont);

        for (Map.Entry<Node, List<Node>> entry : connected.entrySet()) {
            Node node1 = entry.getKey();
            int i = nodes.indexOf(node1);
            for (Node node2 : entry.getValue()) {

                // showing the distance while dragging the connected nodes:
                int centerX1 = node1.x + node1.diameter / 2;
                int centerY1 = node1.y + node1.diameter / 2;
                int centerX2 = node2.x + node2.diameter / 2;
                int centerY2 = node2.y + node2.diameter / 2;

                //Calculate the size of the roadNode image based on the distance between the connected nodes
                int width = Math.abs(centerX2 - centerX1);
                int height = Math.abs(centerY2 - centerY1);
                int x = Math.min(centerX1, centerX2);
                int y = Math.min(centerY1, centerY2);

                //draw linesssss
                g2.drawLine(centerX1, centerY1, centerX2, centerY2);
                int distance = (int) Math.sqrt(Math.pow(centerX2 - centerX1, 2) + Math.pow(centerY2 - centerY1, 2));

                // Calculate the position to draw the distance text
                int textX = (centerX1 + centerX2) / 2;
                int textY = (centerY1 + centerY2) / 2;


                g2.setColor(Color.black);
                //aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
                g2.drawString(Integer.toString(distance), textX, textY);
//                /*int j = nodes.indexOf(node2);
                if (i < i) {
                    int x1 = node1.x + node1.diameter / 2;
                    int y1 = node1.y + node1.diameter / 2;
                    int x2 = node2.x + node2.diameter / 2;
                    int y2 = node2.y + node2.diameter / 2;

                    g2.drawLine(x1, y1, x2, y2);

                    int weightIndex = adjList.get(i).indexOf(node2);
                    if (weightIndex < weightList.get(i).size()) {
                        int weight = weightList.get(i).get(weightIndex);
                        String weightStr = Integer.toString(weight);

                        int midX = (x1 + x2) / 2;
                        int midY = (y1 + y2) / 2;
                        g2.drawString(weightStr, midX, midY);
                    }
                }
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

    // calculate the actual dist
    public void AddWeight(int i, int j) {
        int xDist = nodes.get(i).x - nodes.get(j).x;
        int yDist = nodes.get(i).y - nodes.get(j).y;
        int weight = (int) Math.sqrt(xDist * xDist + yDist * yDist);
        weightList.get(i).add(weight);
        weightList.get(j).add(weight);
    }

    // daming nangyari hahahahahahahahahahah ohmahgah
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
                System.out.println("Checking: " + u.name + " -> " + v.name);
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

            System.out.println("\n\nShortest path from " + nodes.get(src).name + " to " + nodes.get(dst).name + ":");
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