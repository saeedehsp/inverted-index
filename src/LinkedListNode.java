/**
 * Node of a linked list
 */
class LinkedListNode {
    /**
     * Data of node
     */
    private String data;
    /**
     * Next node pointer
     */
    private LinkedListNode next;

    /**
     * Setting the data of the node
     * @param fileName The data of node
     */
    LinkedListNode(String fileName) {
        data = fileName;
    }

    /**
     * Declaring the linked part of the node
     * @param nextNode Next node
     */
    void setNext(LinkedListNode nextNode) {
        next = nextNode;
    }

    /**
     * @return Next node pointing to
     */
    LinkedListNode getNext() {
        return next;
    }

    /**
     * @return Data saved in node
     */
    String getData() {
        return data;
    }

    /**
     * Check if the file name has already been in the node
     * @param fileName Given file name
     * @return if the file name has already been in the node
     */
    Boolean isEqual(String fileName) {
        return fileName.equals(data);
    }
}
