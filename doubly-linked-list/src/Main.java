import java.io.*;
import java.math.*;

public class Main {

    // ******************************
    // ********** MAIN **************
    // ******************************
    public static void main(String[] args) {
        // Create the doubly linked list
        DoubleLists dll = new DoubleLists();

        // Add nodes
        dll.addToHead(50);             // List: 50
        dll.addToTail(100);            // List: 50 -> 100
        dll.addToHead(25);             // List: 25 -> 50 -> 100
        dll.insertNode(dll.head.next, 75); // Insert 75 after 50

        // Print list
        dll.printList(dll.head);
    }

    // **********************
    // ****** Node Class *****
    // **********************
    public static class Node {
        int data;
        Node next;
        Node prev;

        // Constructor creates a new node
        public Node(int data) {
            this.data = data;
        }
    }

    // *****************************
    // ****** DoubleLists Class ****
    // *****************************
    public static class DoubleLists {
        Node head;

        // Add a node to the head of the list
        public void addToHead(int element) {
            Node n = new Node(element);
            n.next = head;
            n.prev = null;

            if (head != null) {
                head.prev = n;
            }

            head = n;
        }

        // Add a node to the tail of the list
        public void addToTail(int element) {
            Node n = new Node(element);
            n.next = null;

            if (head == null) {
                n.prev = null;
                head = n;
                return;
            }

            Node end = head;
            while (end.next != null) {
                end = end.next;
            }

            end.next = n;
            n.prev = end;
        }

        // Insert a node after a given node
        public void insertNode(Node prev, int element) {
            if (prev == null) {
                System.out.println("Cannot have previous node of null");
                return;
            }

            Node n = new Node(element);
            n.next = prev.next;
            prev.next = n;
            n.prev = prev;

            if (n.next != null) {
                n.next.prev = n;
            }
        }

        // Print the list forward and backward
        public void printList(Node node) {
            System.out.println("Going forward --> ");
            Node end = null;

            while (node != null) {
                System.out.print(node.data + " ");
                end = node;
                node = node.next;
            }

            System.out.println();
            System.out.println("<-- Going backward");

            while (end != null) {
                System.out.print(end.data + " ");
                end = end.prev;
            }

            System.out.println();
        }
    }
}
