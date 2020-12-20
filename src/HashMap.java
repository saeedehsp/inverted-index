import java.util.ArrayList;

/**
 * Hashmap Implementation
 */
class HashMap implements BaseDataStructure {
    private final static int TABLE_SIZE = 128;

    /**
     * Count of nodes
     */
    private int amount = 0;

    /**
     * Hash table
     */
    private LinkedHashEntry[] table;

    /**
     * Constructor
     */
    HashMap() {
        table = new LinkedHashEntry[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++)
            table[i] = null;
    }

    /**
     * Finds given word in tree
     * @param keyName Given word
     * @return Node that represents that word
     */
    @Override
    public HashNode find(String keyName) {
        double key = stringToInt(keyName);
        int hash = (int) (key % TABLE_SIZE);
        if (table[hash] == null)
            return null;
        else {
            LinkedHashEntry entry = table[hash];
            while (entry != null && entry.getKey() != key)
                entry = entry.getNext();
            if (entry == null)
                return null;
            else
                return entry.getValue();
        }
    }

    /**
     * Inserts a word with it's file name to the tree
     * @param keyName  The word
     * @param fileName The file name
     */
    @Override
    public void insert(String keyName, String fileName) {
        double key = stringToInt(keyName);
        int hash = (int) (key % TABLE_SIZE);
        if (table[hash] == null) {
            HashNode hashNode = new HashNode(keyName);
            hashNode.addFile(fileName);
            table[hash] = new LinkedHashEntry(key, hashNode);
            amount++;
        } else {
            LinkedHashEntry entry = table[hash];
            while (entry.getNext() != null && entry.getKey() != key)
                entry = entry.getNext();
            if (entry.getKey() == key) {
                if ((entry.getValue()).data.equals(keyName))
                    entry.getValue().addFile(fileName);
                else {
                    HashNode hashNode = new HashNode(keyName);
                    hashNode.addFile(fileName);
                    entry.setValue(hashNode);
                }
            } else {
                HashNode hashNode = new HashNode(keyName);
                hashNode.addFile(fileName);
                entry.setNext(new LinkedHashEntry(key, hashNode));
                amount++;
            }
        }
    }

    /**
     * Deletes the node containing the word
     * @param keyName Given word
     * @return If node is deleted or not
     */
    @Override
    public boolean delete(String keyName) {
        double key = stringToInt(keyName);
        int hash = (int) (key % TABLE_SIZE);
        if (table[hash] != null) {
            LinkedHashEntry prevEntry = null;
            LinkedHashEntry entry = table[hash];
            while (entry.getNext() != null && entry.getKey() != key) {
                prevEntry = entry;
                entry = entry.getNext();
            }
            if (entry.getKey() == key) {
                if (prevEntry == null)
                    table[hash] = entry.getNext();
                else
                    prevEntry.setNext(entry.getNext());
            }
            amount--;
            return true;
        }
        return false;
    }

    /**
     * Converts given string to it's double value
     * @param input Given string
     * @return Double value of string
     */
    private double stringToInt(String input) {
        return input.hashCode() & 0x00000000ffffffffL;
    }

    /**
     * Deletes given word from tree
     * @param filename Given file name
     */
    @Override
    public void deleteFile(String filename) {
        BaseNode hashNode = find(filename);
        if (hashNode != null) {
            hashNode.deleteFile(filename);
            if (hashNode.isEmpty())
                delete(((HashNode) hashNode).data);
        }
    }

    /**
     * @return Will traverse the data structure and return it
     */
    @Override
    public String traverse() {
        String result = "";
        for (LinkedHashEntry entry : table) {
            if (entry != null) {
                result += "\n" + " " + entry.getValue().data + "->";
                ArrayList<String> fileList = entry.getValue().getFileList();
                for (String file : fileList) {
                    result += file + " ";
                }

                LinkedHashEntry nextEntry = entry.getNext();
                while (nextEntry != null) {
                    result += "\n" + " " + nextEntry.getValue().data + "->";
                    fileList = nextEntry.getValue().getFileList();
                    for (String file : fileList) {
                        result += file + " ";
                    }
                    nextEntry = nextEntry.getNext();
                }
            }
        }
        return result + "\n";
    }

    /**
     * @return Count of words in tree
     */
    @Override
    public int wordsCount() {
        return amount;
    }

    /**
     * Making an array list from the files of tree
     * @return Array of file names
     */
    @Override
    public ArrayList<String> getFiles() {
        ArrayList<String> output = new ArrayList<String>();
        for (LinkedHashEntry entry : table) {
            if (entry == null) continue;

            ArrayList<String> fileList = entry.getValue().getFileList();
            for (String file : fileList) {
                if (output.indexOf(file) == -1) {
                    output.add(file);
                }
            }

            LinkedHashEntry nextEntry = entry.getNext();
            while (nextEntry != null) {
                fileList = nextEntry.getValue().getFileList();
                for (String file : fileList) {
                    if (output.indexOf(file) == -1) {
                        output.add(file);
                    }
                }
                nextEntry = nextEntry.getNext();
            }
        }
        return output;
    }

    /**
     * Make an array of list of the files for the node of the word given in search
     * @param word Given word
     * @return Array of file names
     */
    @Override
    public ArrayList<String> fileListOfFoundWord(String word) {
        HashNode wordNode = find(word);
        if (wordNode != null)
            return wordNode.getFileList();
        return new ArrayList<String>();
    }

    /**
     * @return Maximum height of tree
     */
    @Override
    public int height() {
        return 1;
    }
}