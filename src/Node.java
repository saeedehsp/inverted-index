import java.util.ArrayList;

/**
 * BST Tree Node
 */
class Node implements BaseNode {
    /**
     * Data of node
     */
    String data;

    /**
     * Left child
     */
    Node left;

    /**
     * Right child
     */
    Node right;

    /**
     * Files list of node
     */
    private MyLinkedList fileList;

    /**
     * Constructor
     * @param data Data of node
     */
    Node(String data) {
        this.data = data;
        left = null;
        right = null;
        fileList = new MyLinkedList();
    }

    /**
     * Will compare nodes in the tree if one is greater or less than
     * to decide the left node and the right node data
     * @param nextNode Second node to compare to
     * @return -1 if current node is smaller, 1 if is larger or 0 id both are the same
     */
    int compare(Node nextNode) {
        if (this.data.equals(nextNode.data))
            return 0;
        if (this.data.length() < nextNode.data.length())
            return -1;
        if (this.data.length() == nextNode.data.length()) {
            for (int i = 0; i < data.length(); i++) {
                if (this.data.charAt(i) < nextNode.data.charAt(i))
                    return -1;
                else if (this.data.charAt(i) > nextNode.data.charAt(i))
                    return 1;
            }
        }
        return 1;
    }

    /**
     * Adds file to list of files in node
     * @param fileName File name
     */
    void addFile(String fileName) {
        fileList.insert(fileName);
    }

    /**
     * Creates an array list of files in the node's linked list
     * @return Array of files
     */
    ArrayList<String> getFileList() {
        if (fileList == null) return new ArrayList<String>();
        return fileList.print();
    }

    /**
     * @param filename File name to delete from list of files containing the word
     */
    @Override
    public void deleteFile(String filename) {
        if (fileList != null)
            fileList.delete(filename);
    }

    /**
     * @return If file list of node is empty
     */
    public boolean isEmpty() {
        return fileList.isEmpty();
    }
}
