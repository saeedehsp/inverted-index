import java.util.ArrayList;

/**
 * Creating the stack data structure
 */
class Stack {
    private ArrayList<String> p;
    private ArrayList<String> q;

    /**
     * Constructor
     */
    Stack() {
        p = new ArrayList<String>();
        q = new ArrayList<String>();
    }

    /**
     * Pushes a string into stack
     * @param command String to be inserted to stack
     */
    void push(String command) {
        p.add(command);
    }

    /**
     * Popping an element from one array and pushing it to the stack wanted
     * @return The popped element
     */
    String push() {
        if (q.size() == 0)
            return "";
        String string = q.get(q.size() - 1);
        q.remove(q.size() - 1);
        p.add(string);
        return string;
    }

    /**
     * Popping an element from one array and pushing it to the stack wanted
     * @return The popped element
     */
    String pop() {
        if (p.size() == 0)
            return "";
        String string = p.get(p.size() - 1);
        p.remove(p.size() - 1);
        q.add(string);
        return string;
    }
}
