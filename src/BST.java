import java.util.ArrayList;
import java.util.Vector;

/**
 * BST Tree Implementation
 */
class BST implements BaseDataStructure {
    /**
     * Root node
     */
    Node root;

    /**
     * Count of nodes
     */
    private int amount = 0;

    /**
     * Constructor
     */
    BST() {
        this.root = null;
    }

    /**
     * Temporary string to concatenate string parts
     */
    private String tempString = "";

    /**
     * This method will search for the given word in the tree non-recursively
     * @param id Given word
     * @return The node that represents that word in tree
     */
    public Node find(String id) {
        Node tempNode = new Node(id);
        Node current = root;
        while (current != null) {
            if (current.compare(tempNode) == 0)
                return current;
            else if (current.compare(tempNode) > 0)
                current = current.left;
            else
                current = current.right;
        }
        return null;
    }

    /**
     * Make an array of list of the files for the node of the word given in search
     * @param word Given word
     * @return Array of file names
     */
    @Override
    public ArrayList<String> fileListOfFoundWord(String word) {
        Node wordNode = find(word);
        if (wordNode != null)
            return wordNode.getFileList();
        return new ArrayList<String>();
    }

    /**
     * Deletes the node containing the word
     * @param id Given word
     * @return If node is deleted or not
     */
    public boolean delete(String id) {
        Node tempNode = new Node(id);
        Node parent = root;
        Node current = root;
        boolean isLeftChild = false;
        while (!current.data.equals(id)) {
            parent = current;
            if (current.compare(tempNode) > 0) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null) {
                return false;
            }
        }
        // if i am here that means we have found the node
        // Case 1: if node to be deleted has no children
        if (current.left == null && current.right == null) {
            if (current == root)
                root = null;

            if (isLeftChild)
                parent.left = null;
            else
                parent.right = null;
        }
        // Case 2 : if node to be deleted has only one child
        else if (current.right == null) {
            if (current == root)
                root = current.left;
            else if (isLeftChild)
                parent.left = current.left;
            else
                parent.right = current.left;
        } else if (current.left == null) {
            if (current == root)
                root = current.right;
            else if (isLeftChild)
                parent.left = current.right;
            else
                parent.right = current.right;
        } else {

            // Now we have found the minimum element in the right sub tree
            Node successor = getSuccessor(current);
            if (current == root)
                root = successor;
            else if (isLeftChild)
                parent.left = successor;
            else
                parent.right = successor;
            successor.left = current.left;
        }
        amount--;
        return true;
    }

    private Node getSuccessor(Node deleleNode) {
        Node successor = null;
        Node successorParent = null;
        Node current = deleleNode.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }
        // Check if successor has the right child, it cannot have left child for sure
        // If it does have the right child, add it to the left of successorParent.
        if (successor != deleleNode.right) {
            successorParent.left = successor.right;
            successor.right = deleleNode.right;
        }
        return successor;
    }

    /**
     * The method will add a word from a specific file to the tree and
     * will increment the number of existing words in the tree if added
     * @param id       The word
     * @param fileName The file name
     */
    public void insert(String id, String fileName) {
        Node newNode = find(id);
        if (newNode == null) {
            newNode = new Node(id);
            amount++;
        }
        newNode.addFile(fileName);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent;
        while (true) {
            parent = current;
            if (newNode.compare(current) == 0)
                return;
            if (newNode.compare(current) < 0) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            }
        }
    }

    /**
     * The public method to delete a file
     * @param filename Given file name
     */
    @Override
    public void deleteFile(String filename) {
        deleteFile(filename, root);
    }

    /**
     * Making an array list from the files of a node
     * @param node The Node
     * @return Array of file names
     */
    private ArrayList<String> getFiles(Node node) {
        ArrayList<String> output = new ArrayList<String>();
        if (node != null) {
            ArrayList<String> leftFile = getFiles(node.left);
            for (String file : leftFile) {
                if (output.indexOf(file) == -1)
                    output.add(file);
            }
            ArrayList<String> fileList = node.getFileList();
            for (String file : fileList) {
                if (output.indexOf(file) == -1) {
                    output.add(file);
                }
            }
            ArrayList<String> rightFiles = getFiles(node.right);
            for (String file : rightFiles) {
                if (output.indexOf(file) == -1)
                    output.add(file);
            }
        }
        return output;
    }

    /**
     * Making an array list from the files of tree
     * @return Array of file names
     */
    @Override
    public ArrayList<String> getFiles() {
        return getFiles(root);
    }

    /**
     * Will traverse the tree and print it
     * @param root Root node
     */
    private void traverse(Node root) {
        if (root != null) {
            traverse(root.left);
            tempString += "\n" + " " + root.data + "->";
            ArrayList<String> fileList = root.getFileList();
            for (String file : fileList) {
                tempString += file + " ";
            }

            traverse(root.right);
        }
    }

    /**
     * @return Will traverse the data structure and return it
     */
    @Override
    public String traverse() {
        tempString = "";
        traverse(balancedTree());
        String result = tempString + "\n";
        tempString = "";
        return result;
    }

    /**
     * Inorder traverses BST and stores it's nodes in a vector
     * @param root Root node
     * @param nodes Vector of nodes
     */
    private void storeBSTNodes(Node root, Vector<Node> nodes) {
        // Base case
        if (root == null)
            return;

        // Store nodes in Inorder (which is sorted order for BST)
        storeBSTNodes(root.left, nodes);
        nodes.add(root);
        storeBSTNodes(root.right, nodes);
    }


    /**
     * Recursive function to construct binary tree from vector of nodes
     * @param nodes Vector of nodes
     * @param start Start index in vector
     * @param end End index in vector
     * @return New root node
     */
    private Node buildTreeUtil(Vector<Node> nodes, int start, int end) {
        // base case
        if (start > end)
            return null;

        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        Node node = nodes.get(mid);

        /* Using index in Inorder traversal, construct
           left and right subtrees */
        node.left = buildTreeUtil(nodes, start, mid - 1);
        node.right = buildTreeUtil(nodes, mid + 1, end);

        return node;
    }

    /**
     * This functions converts an unbalanced BST to a balanced BST
     * @return New root node
     */
    Node balancedTree() {
        // Store nodes of given BST in sorted order
        Vector<Node> nodes = new Vector<Node>();
        storeBSTNodes(root, nodes);

        // Constructs BST from nodes[]
        int n = nodes.size();
        root = buildTreeUtil(nodes, 0, n - 1);
        return root;
    }

    /**
     * Deletes given file from tree
     * @param filename Given file name
     * @param node Root node
     */
    private void deleteFile(String filename, Node node) {
        if (node == null)
            return;

        node.deleteFile(filename);
        if (node.isEmpty()) {
            delete(node.data);
            deleteFile(filename, root);
        } else {
            deleteFile(filename, node.left);
            deleteFile(filename, node.right);
        }
    }

    /**
     * @return Count of words in tree
     */
    @Override
    public int wordsCount() {
        return amount;
    }

    /**
     * @return Maximum height of tree
     */
    @Override
    public int height() {
        return height(root, 1);
    }

    /**
     * Recursively calculates height of tree from given root
     * @param node Root node
     * @param currentHeight expected height of node
     * @return real height of node
     */
    private int height(Node node, int currentHeight) {
        if (node != null) {

            int rightHeight = height(node.right, currentHeight + 1);
            int leftHeight = height(node.left, currentHeight + 1);
            if (rightHeight > currentHeight) currentHeight = rightHeight;
            if (leftHeight > currentHeight) currentHeight = leftHeight;

            return currentHeight;
        }
        return 0;
    }
}
