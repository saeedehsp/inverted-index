import java.util.ArrayList;
import java.util.Vector;

/**
 * TST Tree Implementation
 */
class TST implements BaseDataStructure {
    /**
     * Root node
     */
    TSTNode root = null;

    /**
     * Temporary string to concatenate string parts
     */
    private String tempString = "";

    /**
     * Constructor
     */
    TST() {
        this.root = null;
    }

    /**
     * A private method to insert character in the tree from a file
     * @param key Word
     * @param pos Position of character of word
     * @param node Parent node
     * @param filename File name that contains the word
     */
    private void insert(String key, int pos, TSTNode node, String filename) {
        char s[] = key.toCharArray();
        if (node == null)
            node = new TSTNode(s[pos], false);

        if (root == null)
            root = node;

        if (s[pos] < node.m_char) {
            if (node.left == null)
                node.left = new TSTNode(s[pos], false);
            insert(key, pos, node.left, filename);
        } else if (s[pos] > node.m_char) {
            if (node.right == null)
                node.right = new TSTNode(s[pos], false);
            insert(key, pos, node.right, filename);
        } else {
            if (pos + 1 == key.length()) {
                node.wordEnd = true;
                node.addFile(filename);
            } else {
                if (node.center == null)
                    node.center = new TSTNode(s[pos + 1], false);
                insert(key, pos + 1, node.center, filename);
            }
        }
    }

    /**
     * The method will add a word from a specific file to the tree
     * @param s       The word
     * @param filename The file name
     */
    public void insert(String s, String filename) {
        if (s == null || s.isEmpty())
            return;

        insert(s, 0, this.root, filename);
    }

    /**
     * This method will search for the given word in the tree
     * @param key Given word
     * @return The node that represents that word in tree
     */
    public TSTNode find(String key) {
        if (key == null || key.isEmpty())
            return null;

        int pos = 0;
        TSTNode node = this.root;
        char s[] = key.toCharArray();
        while (node != null) {

            if (s[pos] < node.m_char)
                node = node.left;
            else if (s[pos] > node.m_char)
                node = node.right;
            else {
                if (++pos == key.length()) {
                    if (node.wordEnd)
                        return node;
                    else return null;
                }
                node = node.center;
            }
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
        TSTNode wordNode = find(word);
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
        TSTNode tstNode = find(id);
        if (tstNode == null)
            return false;
        tstNode.wordEnd = false;
        // TODO : implement delete
        return true;
    }

    /**
     * Making an array list from the files of a node
     * @param node The Node
     * @return Array of file names
     */
    private ArrayList<String> getFiles(TSTNode node) {
        ArrayList<String> output = new ArrayList<String>();

        if (node != null) {

            ArrayList<String> leftFiles = getFiles(node.left);
            for (String file : leftFiles) {
                if (output.indexOf(file) == -1)
                    output.add(file);
            }
            if (node.wordEnd) {
                ArrayList<String> fileList = node.getFileList();
                for (String file : fileList) {
                    if (output.indexOf(file) == -1)
                        output.add(file);
                }
            }

            ArrayList<String> centerFiles = getFiles(node.center);
            for (String file : centerFiles) {
                if (output.indexOf(file) == -1)
                    output.add(file);
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
     * A recursive function to traverse Ternary Search Tree
     * @param root Root node
     */
    private void display(TSTNode root, char[] buffer, int depth) {

        if (root != null) {
            // First traverse the left subtree
            display(root.left, buffer, depth);

            // Store the character of this node
            buffer[depth] = root.m_char;
            if (root.wordEnd) {
                buffer[depth + 1] = '\0';
                String word = "";
                for (char character : buffer) {
                    if (character != '\0') {
                        word += character;
                    } else break;
                }
                tempString += word + "->";
                ArrayList<String> fileList = root.getFileList();
                for (String file : fileList) {
                    tempString += file + " ";
                }
                tempString += "\n";

            }

            // Traverse the subtree using equal pointer (middle subtree)
            display(root.center, buffer, depth + 1);

            // Finally Traverse the right subtree
            display(root.right, buffer, depth);
        }
    }

    /**
     * The main function to traverse a Ternary Search Tree
     * @param root Root node
     */
    private void traverse(TSTNode root) {
        char[] buffer = new char[10000];
        display(root, buffer, 0);
    }

    /**
     * @return Will traverse the data structure and return it
     */
    @Override
    public String traverse() {
        tempString = "";
        traverse(root);
        String result = tempString + "\n";
        tempString = "";
        return result;
    }

    /**
     * To search a given word in TST
     * @param root Root node
     * @param word The Word
     * @param pos Current character of word
     * @return If it can find the word or not
     */
    private boolean searchTST(TSTNode root, String word, int pos) {
        if (root == null)
            return false;

        if (word.charAt(pos) < root.m_char)
            return searchTST(root.left, word, pos);

        else if (word.charAt(pos) > root.m_char)
            return searchTST(root.right, word, pos);

        else {
            if (pos == word.length() - 1)
                return root.wordEnd;

            return searchTST(root.center, word, pos + 1);
        }
    }

    /**
     * Check if a specific word exists in the tree
     * @param root Root node
     * @param word Given word
     * @return If a specific word exists in the tree
     */
    boolean searchTST(TSTNode root, String word) {
        return !(word == null || word.isEmpty()) && searchTST(root, word, 0);
    }

    /**
     * A private method to check if the node which is word end has the file to be deleted
     * @param filename Given file name
     * @param node Root node
     * @return If it could successfully delete the filename or not
     */
    private boolean deleteFile(String filename, TSTNode node) {
        if (node == null)
            return false;

        boolean hasChild = false;

        if (deleteFile(filename, node.left))
            hasChild = true;
        else
            node.left = null;

        if (deleteFile(filename, node.center))
            hasChild = true;
        else
            node.center = null;

        if (deleteFile(filename, node.right))
            hasChild = true;
        else
            node.right = null;

        node.deleteFile(filename);

        if (node.isEmpty())
            node.wordEnd = false;

        return hasChild || node.wordEnd;
    }

    /**
     * Will delete the file if the private delete file method is true
     * @param filename Given file name
     */
    @Override
    public void deleteFile(String filename) {
        deleteFile(filename, root);
        TSTNode current = root;
        while (current != null) {
            if (current.left == null && current.center == null) {
                current = current.right;
                root = current;
            } else break;
        }
    }

    /**
     * @return Count of words in tree
     */
    @Override
    public int wordsCount() {
        return wordsCount(root);
    }

    /**
     * @param node The node
     * @return Count of child nodes that contains full word plus one if current node has full word
     */
    private int wordsCount(TSTNode node) {
        int amount = 0;
        if (node != null) {
            if (node.wordEnd)
                amount++;
            amount += wordsCount(node.right);
            amount += wordsCount(node.left);
            amount += wordsCount(node.center);
        }
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
    private int height(TSTNode node, int currentHeight) {
        if (node != null) {

            int rightHeight = height(node.right, currentHeight + 1);
            int leftHeight = height(node.left, currentHeight + 1);
            int centerHeight = height(node.center, currentHeight + 1);
            if (rightHeight > currentHeight) currentHeight = rightHeight;
            if (leftHeight > currentHeight) currentHeight = leftHeight;
            if (centerHeight > currentHeight) currentHeight = centerHeight;
            return currentHeight;
        }
        return 0;
    }

    /**
     * Inorder traverses BST and stores it's nodes in a vector
     * @param root Root node
     * @param nodes Vector of nodes
     */
    private void storeBSTNodes(TSTNode root, Vector<TSTNode> nodes) {
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
    private TSTNode buildTreeUtil(Vector<TSTNode> nodes, int start, int end) {
        // base case
        if (start > end)
            return null;

        /* Get the middle element and make it root */
        int mid = (start + end) / 2;
        TSTNode node = nodes.get(mid);

        /* Using index in Inorder traversal, construct
           left and right subtrees */
        node.left = buildTreeUtil(nodes, start, mid - 1);
        node.center = balanceTree(node.center);
        node.right = buildTreeUtil(nodes, mid + 1, end);

        return node;
    }

    /**
     * This functions converts an unbalanced BST nodes to a balanced BST nodes
     * @return New root node
     */
    private TSTNode balanceTree(TSTNode root) {
        // Store nodes of given BST in sorted order
        Vector<TSTNode> nodes = new Vector<TSTNode>();
        storeBSTNodes(root, nodes);

        // Constructs BST from nodes[]
        int n = nodes.size();
        root = buildTreeUtil(nodes, 0, n - 1);
        return root;
    }

    /**
     * This functions converts an unbalanced BST to a balanced BST
     * @return New root node
     */
    TSTNode balancedTree() {
        root = balanceTree(root);
        return root;
    }
}
