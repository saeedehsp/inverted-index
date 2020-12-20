import java.util.ArrayList;

/**
 * Linked list implementation
 */
class MyLinkedList {
    /**
     * First node
     */
    private LinkedListNode first;
    /**
     * Last node
     */
    private LinkedListNode last;

    /**
     * Constructor
     */
    MyLinkedList() {
        first = null;
        last = null;
    }

    /**
     * Check if the file has already been in the linked list or not
     * @param fileName Given file name
     * @return if the file has already been in the linked list or not
     */
    private boolean find(String fileName) {
        LinkedListNode current = first;
        while (current != null) {
            if (current.isEqual(fileName))
                return true;
            current = current.getNext();
        }
        return false;
    }

    /**
     * Inserting a file name to the linked list if wasn't inserted
     * @param fileName Given file name
     */
    void insert(String fileName) {
        if (find(fileName))
            return;

        LinkedListNode node = new LinkedListNode(fileName);

        if (last != null)
            last.setNext(node);
        else
            first = node;
        last = node;
    }

    /**
     * Deletes a file name from the linked list
     * @param fileName Given file name
     */
    void delete(String fileName) {
        if (first == null) return;

        if (first.isEqual(fileName))
            first = first.getNext();

        if (first == null) return;

        LinkedListNode previousNode = first;
        LinkedListNode currentNode = first.getNext();
        while (currentNode != null) {
            if (currentNode.isEqual(fileName))
                previousNode.setNext(currentNode.getNext());
            previousNode = currentNode;
            currentNode = currentNode.getNext();
        }
    }

    /**
     * Creates an array of the file names in the linked list
     * @return array of the file names
     */
    ArrayList<String> print() {
        ArrayList<String> files = new ArrayList<String>();
        LinkedListNode current = first;
        while (current != null) {
            if (files.indexOf(current.getData()) == -1)
                files.add(current.getData());
            current = current.getNext();

        }
        return files;
    }

    /**
     * @return If linked list is empty
     */
    boolean isEmpty() {
        return first == null;
    }
}
