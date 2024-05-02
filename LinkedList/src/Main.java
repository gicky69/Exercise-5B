public class Main {

    static Node head; // first node
    static Node tail; // last node

    public static void InsertAtBeginning(String data) {
        Node temp = new Node(data);
        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            temp.next = head; // set the pointer of new node (next) to the current head node
            head.prev = temp; // set the pointer of new node (prev) to the current node
            head = temp; // set the temp as the new head
        }
    }

    public static void InsertAtEnd(String data) {
        Node temp = new Node(data);
        if (head == null) {
            head = temp;
            tail = temp;
        } else {
            tail.next = temp;
            temp.prev = tail;
            tail = temp;
        }
    }
    // check
    // check 2

    public static void Connect() {

    }

    public static void PrintList() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + "-->");
            current = current.next;
        }
    }



    public static void main(String[] args) {
        InsertAtBeginning("Hello");
        InsertAtBeginning("Second");
        InsertAtEnd("LAST");
        InsertAtBeginning("Beginning");
        PrintList();

    }


}