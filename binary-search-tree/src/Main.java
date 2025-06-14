// Kurtis Ivey
// CS 201 assignment 1

import java.util.Scanner;

public class Main {
    Node root; // root node

    public Main() {
        root = null;
    }

    public static void main(String[] args) {
        Main tree = new Main(); //
        Scanner scanner = new Scanner(System.in); // initiate scanner class to take input
        boolean exit = false; // bool used to keep while loop running

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Create a Binary Search Tree");
            System.out.println("2. Add a node");
            System.out.println("3. Delete a node");
            System.out.println("4. Print nodes by InOrder");
            System.out.println("5. Print nodes by PreOrder");
            System.out.println("6. Print nodes by PostOrder");
            System.out.println("7. Exit program");
            System.out.print("Enter your selection~~ ");

            int selection = -1; // set to -1 as placeholder
            if (scanner.hasNextInt()) { // detects if integer is input of scanner
                selection = scanner.nextInt();
                if (selection < 1 || selection > 7) { // don't want ints outside of 1 and 7 since those are menu options
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                    continue; // continues in while loop to reprint menu
                }
            } else { // if input wasn't an integer, throws this UI error
                System.out.println("Invalid input. Please enter a number between 1 and 7 per the menu options");
                scanner.next(); // skips invalid input so user can enter again
                continue; // continues in while loop to reprint menu
            }

            switch (selection) {
                case 1:
                    int[] sortedValues = {1, 2, 3, 4, 5, 6, 7}; // default vals given, but must balance
                    tree.root = null; // reset the tree to null if option 1 selected
                    insertBalanced(tree, sortedValues, 0, sortedValues.length - 1);
                    System.out.println("Balanced Binary Search Tree created using values: 1, 2, 3, 4, 5, 6, 7.");
                    break;

                case 2:
                    System.out.print("Enter value to add a node: ");
                    int insertValue = scanner.nextInt();
                    tree.insert(insertValue);
                    System.out.printf("Node inserted with value: %d \n", insertValue);
                    break;


                case 3:
                    System.out.print("Enter value to delete a node: ");
                    int deleteValue = scanner.nextInt();
                    tree.delete(deleteValue);
                    System.out.printf("Node deleted with value of %d (if it existed). \n", deleteValue);
                    break;

                case 4:
                    System.out.println("InOrder Traversal:"); // left > root > right
                    tree.printInOrder();
                    break;

                case 5:
                    System.out.println("PreOrder Traversal:"); // top to bottom, and left to right
                    tree.printPreOrder();
                    break;

                case 6:
                    System.out.println("PostOrder Traversal:"); // bottom to top, and left to right
                    tree.printPostOrder();
                    break;

                case 7:
                    System.out.println("Exiting. Goodbye");
                    exit = true; // sets while loop boolean to true and breaks out of while loop
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
        scanner.close(); // closes scanner
    }

    // helper method to balance ordered default data per requirements of proj
    private static void insertBalanced(Main tree, int[] arr, int start, int end) {
        if (start > end) return; // stop if start index passes end (nothing left to add)
        int mid = (start + end) / 2; // get middle index to keep tree balanced
        tree.insert(arr[mid]); // insert middle element
        insertBalanced(tree, arr, start, mid - 1); // go left and insert all smaller values
        insertBalanced(tree, arr, mid + 1, end);   // go right and insert all larger values
    }


    public void insert(int key) {
        root = insertNode(root, key);
    }

    private Node insertNode(Node n, int k) { // made private to fix Class 'Node' is exposed outside its defined visibility scope
        if (n == null) {
            System.out.printf("No nodes, creating root node with %d \n", k);
            return new Node(k);
        }

        if (k <= n.key) { // smaller than or equal to node key, traverse left until spot found
            n.leftChild = insertNode(n.leftChild, k);
        } else { // greater than node key, traverse right until spot found
            n.rightChild = insertNode(n.rightChild, k);
        }
        return n;
    }

    public void delete(int k) {
        root = deleteNode(root, k); // uses recursion to find node to delete
    }

    private Node deleteNode(Node n, int k) { // made private to fix Class 'Node' is exposed outside its defined visibility scope
        if (n == null) {
            System.out.println("Current node is null. Nothing to delete.");
            return null;
        }

        if (k < n.key) { // if key is smaller than curr node, search left subtree
            n.leftChild = deleteNode(n.leftChild, k); // reassignment of pointer occurs in else
        } else if (k > n.key) { // if key is greater than curr node, search right subtree
            n.rightChild = deleteNode(n.rightChild, k); // reassignment of pointer occurs in else
        } else { // if it's not greater or less than, it's equal
            if (n.leftChild == null) { // no left child
                return n.rightChild; // return right child to reassign pointer
            } else if (n.rightChild == null) { // no right child
                return n.leftChild; // return left child to reassign pointer
            }
            n.key = minValue(n.rightChild); // reassigns to smallest key in right subtree
            n.rightChild = deleteNode(n.rightChild, n.key); // we then find that node to delete, it has no children as it's at the lowest level
        }
        return n;
    }

    private int minValue(Node n) { // made private to fix Class 'Node' is exposed outside its defined visibility scope
        int minKey = n.key;
        // keep moving down leftwards to find smallest key val as left children are smaller
        while (n.leftChild != null) {
            n = n.leftChild; // move left
            minKey = n.key; // update minkey to new smaller val
            // due to while loop, keeps runnining until left child equals null
        }
        return minKey; // returns smallest key
    }

    // left > root > right
    public void printInOrder() {
        printIO(root); // calls helper method printIO
        System.out.println();
    }

    // left > root > right
    private void printIO(Node n) { // made private to fix Class 'Node' is exposed outside its defined visibility scope
        if (n != null) { // if null, do nothing
            printIO(n.leftChild); // traverse left first to reach smaller vals
            System.out.print(n.key + " "); // now that left side done, print this node's key
            printIO(n.rightChild); // traverse right side and do same thing
        }
    }

    // top to bottom, and left to right
    public void printPreOrder() {
        printPre(root); // calls helper method printPre
        System.out.println();
    }

    // top to bottom, and left to right
    private void printPre(Node n) { // made private to fix Class 'Node' is exposed outside its defined visibility scope
        if (n != null) {
            System.out.print(n.key + " "); // print root first
            printPre(n.leftChild);  // go left next, print everything under that side
            printPre(n.rightChild); // then go right and print everything under that side
        }
    }


    // bottom to top, and left to right
    public void printPostOrder() {
        printPost(root); // calls helper method printPost
        System.out.println();
    }

    // bottom to top, and left to right
    private void printPost(Node n) { // made private to fix Class 'Node' is exposed outside its defined visibility scope
        if (n != null) {
            printPost(n.leftChild);   // go all the way left first and finish that side
            printPost(n.rightChild);  // then go right and finish that side
            System.out.print(n.key + " "); // once both sides are done, print root node
        }
    }

    // Static inner Node class
    static class Node {
        int key;
        Node leftChild;
        Node rightChild;

        Node(int key) {
            this.key = key;
            leftChild = null;
            rightChild = null;
        }
    }
}