/**
 * Hashmap Entry Implementation
 */
class LinkedHashEntry {
    /**
     * Key of entry
     */
    private double key;

    /**
     * Value of entry (HashNode)
     */
    private HashNode value;

    /**
     * Next entry with same hash
     */
    private LinkedHashEntry next;

    /**
     * Constructor
     * @param key Key of entry
     * @param value Value of entry
     */
    LinkedHashEntry(double key, HashNode value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    /**
     * @return HashNode Value of entry
     */
    HashNode getValue() {
        return value;
    }

    /**
     * Set Value of entry
     * @param value Value of entry
     */
    void setValue(HashNode value) {
        this.value = value;
    }

    /**
     * @return double Key of entry
     */
    double getKey() {
        return key;
    }

    /**
     * @return LinkedHashEntry Next entry with same hash
     */
    LinkedHashEntry getNext() {
        return next;
    }

    /**
     * Set Next entry with same hash
     * @param next LinkedHashEntry Next entry with same hash
     */
    void setNext(LinkedHashEntry next) {
        this.next = next;
    }
}
