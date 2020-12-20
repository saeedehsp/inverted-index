import java.util.ArrayList;

/**
 * TST Tree Node
 */
class TSTNode implements BaseNode {
    /**
     * Node Content
     */
    char m_char;

    /**
     * Left Node
     */
    TSTNode left;

    /**
     * Center Node
     */
    TSTNode center;

    /**
     * Right Node
     */
    TSTNode right;

    /**
     * Is this a complete word
     */
    boolean wordEnd;

    /**
     * List of files containing this word
     */
    private MyLinkedList fileList;

    /**
     * Constructor
     * @param ch Character
     * @param wordEnd Is this last character of the word
     */
    TSTNode(char ch, boolean wordEnd) {
        this.m_char = ch;
        this.wordEnd = wordEnd;
    }

    /**
     * @param fileName File name to add to list of files that containing this word
     */
    void addFile(String fileName) {
        if (fileList == null)
            fileList = new MyLinkedList();
        fileList.insert(fileName);
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
     * @return Array of files of the node
     */
    ArrayList<String> getFileList() {
        if (fileList == null) return new ArrayList<String>();
        return fileList.print();
    }

    /**
     * @return If file list of node is empty
     */
    public boolean isEmpty() {
        return (fileList == null || fileList.isEmpty());
    }
}