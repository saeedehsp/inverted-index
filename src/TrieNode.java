import java.util.ArrayList;

/**
 * Trie Tree Node
 */
class TrieNode implements BaseNode {
    /**
     * Data of node
     */
    char content;

    /**
     * Is this a complete word
     */
    boolean isEnd;

    /**
     * Count of children
     */
    int count;

    /**
     * Children of node
     */
    ArrayList<TrieNode> childList;

    /**
     * List of files containing this word
     */
    private MyLinkedList fileList;

    /**
     * Constructor
     * @param c Character
     */
    TrieNode(char c) {
        childList = new ArrayList<TrieNode>();
        isEnd = false;
        content = c;
        count = 0;
    }

    /**
     * Search for the node representing the given character in child nodes
     * @param c Given character
     * @return The node representing the given character
     */
    TrieNode subNode(char c) {
        if (childList != null)
            for (TrieNode eachChild : childList)
                if (eachChild.content == c)
                    return eachChild;
        return null;
    }

    /**
     * Will add file names to the node linked list
     * @param fileName Given file name
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
     * @return If linked list is empty or not
     */
    public boolean isEmpty() {
        return (fileList == null || fileList.isEmpty());
    }

    /**
     * @return Array list of a node files
     */
    ArrayList<String> getFileList() {
        if (fileList == null) return new ArrayList<String>();
        return fileList.print();
    }
}


