import java.util.ArrayList;

/**
 * Hash Map Node
 */
class HashNode implements BaseNode {
    /**
     * Data of node
     */
    String data;

    /**
     * Files list of node
     */
    private MyLinkedList fileList;

    /**
     * Constructor
     * @param data Data of node
     */
    HashNode(String data) {
        this.data = data;
        fileList = new MyLinkedList();
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
