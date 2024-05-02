//import java.util.ArrayList;
//import java.util.LinkedList;
//
//public class Graph {
//
//    ArrayList<LinkedList <Node>> adjlist;
//    ArrayList<LinkedList<Integer>> weightList;
//
//    Graph(){
//        adjlist = new ArrayList<LinkedList<Node>>();
//        weightList = new ArrayList<LinkedList<Integer>>();
//    }
//
//    public void AddNode(Node node){
//        LinkedList<Node> list = new LinkedList<Node>();
//        list.add(node);
//        adjlist.add(list);
//    }
//
//    public void AddWeight(int srcNode, int dstTo, int weight){
//        LinkedList<Node> list = adjlist.get(srcNode);
//        Node dstNode = adjlist.get(dstTo).get(0);
//        list.add(dstNode);
//
//        LinkedList<Integer> weightList = new LinkedList<Integer>();
//        weightList.add(weight);
//        this.weightList.add(weightList);
//    }
//
//    public void print(){
//        for (LinkedList<Node> list : adjlist){
//            for (Node node : list){
//                System.out.print(node.data + " -> ");
//            }
//            System.out.println();
//        }
//
//        for (LinkedList<Integer> list : weightList){
//            for (Integer weight : list){
//                System.out.print(weight);
//            }
//            System.out.println();
//        }
//    }
//}
