/**
 * General Node Interface
 */
interface BaseNode {
    /**
     * @param filename File name to delete from list of files containing the word
     */
    void deleteFile(String filename);

    /**
     * @return If file list of node is empty
     */
    boolean isEmpty();
}
