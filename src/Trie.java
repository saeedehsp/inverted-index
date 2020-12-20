import java.util.ArrayList;

/**
 * Trie Tree Implementation
 */
class Trie implements BaseDataStructure {
    /**
     * Root node
     */
    TrieNode root;

    /**
     * Temporary string to concatenate string parts
     */
    private String tempString = "";

    /**
     * Constructor
     */
    Trie() {
        root = new TrieNode(' ');
    }

    /**
     * The method will add a word from a specific file to the tree
     * @param word       The word
     * @param filename The file name
     */
    public void insert(String word, String filename) {
        TrieNode current = find(word);
        if (current != null) {
            current.addFile(filename);
            return;
        }

        current = root;
        for (char ch : word.toCharArray()) {
            TrieNode child = current.subNode(ch);
            if (child != null)
                current = child;
            else {
                current.childList.add(new TrieNode(ch));
                current = current.subNode(ch);
            }
            current.count++;
        }
        current.isEnd = true;
        current.addFile(filename);
    }

    /**
     * To check if the node is the end of the word to delete the file
     * that the word is in if else checking the children of the node
     * @param filename Given file name
     * @param node Root node
     * @return If delete was successful or not
     */
    private boolean deleteFile(String filename, TrieNode node) {
        if (node == null)
            return false;

        boolean hasChild = false;

        ArrayList<TrieNode> childrenToDelete = new ArrayList<TrieNode>();

        for (TrieNode child : node.childList) {
            if (deleteFile(filename, child))
                hasChild = true;
            else {
                childrenToDelete.add(child);
                node.count--;
            }
        }

        for (TrieNode child : childrenToDelete) {
            node.childList.remove(child);
        }
        if (node.count < 0)
            node.count = 0;

        node.deleteFile(filename);

        if (node.isEmpty())
            node.isEnd = false;

        return hasChild || node.isEnd;
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
     * This method will search for the given word in the tree
     * @param word Given word
     * @return The node that represents that word in tree
     */
    public TrieNode find(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (current.subNode(ch) == null)
                return null;
            else
                current = current.subNode(ch);
        }
        if (current.isEnd)
            return current;
        return null;
    }

    /**
     * Make an array of list of the files for the node of the word given in search
     * @param word Given word
     * @return Array of file names
     */
    @Override
    public ArrayList<String> fileListOfFoundWord(String word) {
        TrieNode wordNode = find(word);
        if (wordNode != null)
            return wordNode.getFileList();
        return new ArrayList<String>();
    }

    /**
     * Deletes the node containing the word
     * @param id Given word
     * @return If node is deleted or not
     */
    @Override
    public boolean delete(String id) {
        if (find(id) == null)
            return false;

        TrieNode current = root;
        for (char ch : id.toCharArray()) {
            TrieNode child = current.subNode(ch);
            if (child.count == 1) {
                current.childList.remove(child);
                return false;
            } else {
                child.count--;
                current = child;
            }
        }
        current.isEnd = false;
        return true;
    }

    /**
     * Creates an array from files in the tree from isEnd nodes linked list
     * @param node Root node
     * @return Array of files
     */
    private ArrayList<String> getFiles(TrieNode node) {
        ArrayList<String> output = new ArrayList<String>();
        if (node.isEnd) {

            ArrayList<String> fileList = node.getFileList();
            for (String file : fileList) {
                if (output.indexOf(file) == -1)
                    output.add(file);
            }

        }

        for (TrieNode child : node.childList) {
            ArrayList<String> childFiles = getFiles(child);
            for (String file : childFiles) {
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
     * Will traverse and print the tree
     * @param prefix Completing word
     * @param node Node ot traverse
     */
    private void traverse(String prefix, TrieNode node) {
        if (node.isEnd) {
            tempString += "\n" + prefix + node.content + "->";
            ArrayList<String> fileList = node.getFileList();
            for (String file : fileList) {
                tempString += file + " ";
            }
        }

        for (TrieNode child : node.childList) {
            traverse(prefix + node.content, child);
        }
    }

    /**
     * @return Will traverse the data structure and return it
     */
    @Override
    public String traverse() {
        tempString = "";
        traverse("", root);
        String result = tempString + "\n";
        tempString = "";
        return result;
    }

    /**
     * @return Count of words in tree
     */
    @Override
    public int wordsCount() {
        return wordsCount(root);
    }

    /**
     * @param node Root node
     * @return The number of words in the tree by checking the isEnd nodes
     */
    private int wordsCount(TrieNode node) {
        int amount = 0;
        if (node != null) {
            if (node.isEnd)
                amount++;
            for (TrieNode child : node.childList) {
                amount += wordsCount(child);
            }
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
    private int height(TrieNode node, int currentHeight) {
        int height = currentHeight;
        if (node.childList != null) {
            for (TrieNode child : node.childList) {
                int childHeight = height(child, height + 1);
                if (childHeight > currentHeight)
                    currentHeight = childHeight;
            }
        }
        return currentHeight;
    }
}
