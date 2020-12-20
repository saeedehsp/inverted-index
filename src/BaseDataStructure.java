import javax.swing.JTextArea;
import java.util.ArrayList;

/**
 * General Data Structure Interface
 */
interface BaseDataStructure {
    /**
     * Finds given word in data structure
     * @param id Given word
     * @return Node that represents that word
     */
    BaseNode find(String id);

    /**
     * Deletes the node containing the word
     * @param id Given word
     * @return If node is deleted or not
     */
    boolean delete(String id);

    /**
     * Inserts a word with it's file name to the data structure
     * @param id The word
     * @param fileName The file name
     */
    void insert(String id, String fileName);

    /**
     * Deletes given word from data structure
     * @param filename Given file name
     */
    void deleteFile(String filename);

    /**
     * @return Will traverse the data structure and return it
     */
    String traverse();

    /**
     * @return Count of words in data structure
     */
    int wordsCount();

    /**
     * Making an array list from the files of data structure
     * @return Array of file names
     */
    ArrayList<String> getFiles();

    /**
     * Make an array of list of the files for the node of the word given in search
     * @param word Given word
     * @return Array of file names
     */
    ArrayList<String> fileListOfFoundWord(String word);

    /**
     * @return Maximum height of data structure
     */
    int height();
}
