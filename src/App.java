import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * UI for the program
 */
class App extends JFrame {
    // declaring UI elements
    private JTextField folderTextField;
    private JTextArea output;
    private ButtonGroup dataStructureGroup;
    private JTextField commandTextField;
    private Stack commandsStack;

    // Instantiating data structures
    private BST bst = new BST();
    private TST tst = new TST();
    private Trie trie = new Trie();
    private HashMap hash = new HashMap();

    /**
     * Current selected data structure
     */
    private BaseDataStructure dataStructure;

    /**
     * Array of stop words
     */
    private String stopWords[] = {
            "about", "above", "according", "across", "after", "afterwards", "again", "against", "albeit", "all",
            "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an",
            "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anywhere", "apart",
            "are", "around", "as", "at", "av", "be", "became", "because", "become", "becomes", "becoming", "been",
            "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "both",
            "but", "by", "can", "cannot", "canst", "certain", "cf", "choose", "contrariwise", "cos", "could", "cu",
            "day", "do", "does", "doesn't", "doing", "dost", "doth", "double", "down", "dual", "during", "each",
            "either", "else", "elsewhere", "enough", "et", "etc", "even", "ever", "every", "everybody", "everyone",
            "everything", "everywhere", "except", "excepted", "excepting", "exception", "exclude", "excluding",
            "exclusive", "far", "farther", "farthest", "few", "ff", "first", "for", "formerly", "forth", "forward",
            "from", "front", "further", "furthermore", "furthest", "get", "go", "had", "halves", "hardly", "has",
            "hast", "hath", "have", "he", "hence", "henceforth", "her", "here", "hereabouts", "hereafter", "hereby",
            "herein", "hereto", "hereupon", "hers", "herself", "him", "himself", "hindmost", "his", "hither",
            "hitherto", "how", "however", "howsoever", "i", "ie", "if", "in", "inasmuch", "inc", "include", "included",
            "including", "indeed", "indoors", "inside", "insomuch", "instead", "into", "inward", "inwards", "is", "it",
            "its", "itself", "just", "kind", "kg", "km", "last", "latter", "latterly", "less", "lest", "let", "like",
            "little", "ltd", "many", "may", "maybe", "me", "meantime", "meanwhile", "might", "moreover", "most",
            "mostly", "more", "mr", "mrs", "ms", "much", "must", "my", "myself", "namely", "need", "neither", "never",
            "nevertheless", "next", "no", "nobody", "none", "nonetheless", "noone", "nope", "nor", "not", "nothing",
            "notwithstanding", "now", "nowadays", "nowhere", "of", "off", "often", "ok", "on", "once", "one", "only",
            "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside",
            "over", "own", "per", "perhaps", "plenty", "provide", "quite", "rather", "really", "round", "said", "sake",
            "same", "sang", "save", "saw", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "seldom",
            "selves", "sent", "several", "shalt", "she", "should", "shown", "sideways", "since", "slept", "slew",
            "slung", "slunk", "smote", "so", "some", "somebody", "somehow", "someone", "something", "sometime",
            "sometimes", "somewhat", "somewhere", "spake", "spat", "spoke", "spoken", "sprang", "sprung", "stave",
            "staves", "still", "such", "supposing", "than", "that", "the", "thee", "their", "them", "themselves",
            "then", "thence", "thenceforth", "there", "thereabout", "thereabouts", "thereafter", "thereby", "therefore",
            "therein", "thereof", "thereon", "thereto", "thereupon", "these", "they", "this", "those", "thou", "though",
            "thrice", "through", "throughout", "thru", "thus", "thy", "thyself", "till", "to", "together", "too",
            "toward", "towards", "ugh", "unable", "under", "underneath", "unless", "unlike", "until", "up", "upon",
            "upward", "upwards", "us", "use", "used", "using", "very", "via", "vs", "want", "was", "we", "week", "well",
            "were", "what", "whatever", "whatsoever", "when", "whence", "whenever", "whensoever", "where",
            "whereabouts", "whereafter", "whereas", "whereat", "whereby", "wherefore", "wherefrom", "wherein",
            "whereinto", "whereof", "whereon", "wheresoever", "whereto", "whereunto", "whereupon", "wherever",
            "wherewith", "whether", "whew", "which", "whichever", "whichsoever", "while", "whilst", "whither", "who",
            "whoa", "whoever", "whole", "whom", "whomever", "whomsoever", "whose", "whosoever", "why", "will", "wilt",
            "with", "within", "without", "worse", "worst", "would", "wow", "ye", "yet", "year", "yippee", "you", "your",
            "yours", "yourself", "yourselves"
    };

    /**
     * Constructor for app
     */
    App() {
        super("Inverted Index");

        Dimension preferredSize = new Dimension(400, 450);
        setPreferredSize(preferredSize);
        setSize(preferredSize);
        setLocationRelativeTo(null);
        commandsStack = new Stack();

        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        addComponentsToFrame(layout);
        resetForm();

        setResizable(false);
        setVisible(true);
    }

    /**
     * Helper method to draw elements on frame
     * @param layout Layout manager
     */
    private void addComponentsToFrame(SpringLayout layout) {
        final JFrame frame = this;

        // Folder Label
        JLabel folderLabel = new JLabel("Please enter folder address or use browse button");
        add(folderLabel);
        layout.putConstraint(SpringLayout.NORTH, folderLabel, 5, SpringLayout.NORTH, frame);
        layout.putConstraint(SpringLayout.WEST, folderLabel, 5, SpringLayout.WEST, frame);

        // Folder Text Field
        folderTextField = new JTextField(24);
        add(folderTextField);
        layout.putConstraint(SpringLayout.NORTH, folderTextField, 5, SpringLayout.SOUTH, folderLabel);
        layout.putConstraint(SpringLayout.WEST, folderTextField, 0, SpringLayout.WEST, folderLabel);

        // Folder Browse Button
        JButton folderBrowseButton = new JButton("Browse");
        folderBrowseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showDialog(frame, "Select Folder");
                if (fileChooser.getSelectedFile() != null)
                    folderTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        add(folderBrowseButton);
        layout.putConstraint(SpringLayout.NORTH, folderBrowseButton, 0, SpringLayout.NORTH, folderTextField);
        layout.putConstraint(SpringLayout.WEST, folderBrowseButton, 3, SpringLayout.EAST, folderTextField);

        // Output Text Field
        output = new JTextArea(17, 30);
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);
        Dimension scrollPaneDimension = new Dimension(380, 275);
        scrollPane.setPreferredSize(scrollPaneDimension);
        scrollPane.setSize(scrollPaneDimension);
        add(scrollPane);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, folderTextField);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 3, SpringLayout.WEST, folderTextField);

        // Command Label
        final JLabel commandLabel = new JLabel("Enter your command:");
        add(commandLabel);
        layout.putConstraint(SpringLayout.NORTH, commandLabel, 5, SpringLayout.SOUTH, scrollPane);
        layout.putConstraint(SpringLayout.WEST, commandLabel, 5, SpringLayout.WEST, frame);

        // Data Structure Group
        dataStructureGroup = new ButtonGroup();
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String dataStructureType = ((JRadioButton) e.getSource()).getText();
                if (dataStructureType.equals("BST"))
                    dataStructure = bst;
                if (dataStructureType.equals("TST"))
                    dataStructure = tst;
                if (dataStructureType.equals("Trie"))
                    dataStructure = trie;
                if(dataStructureType.equals("Hash"))
                    dataStructure = hash;
            }
        };

        // TST Tree Radio Buttons
        JRadioButton tstRadioButton = new JRadioButton("TST", true);
        tstRadioButton.addChangeListener(changeListener);
        add(tstRadioButton);
        dataStructureGroup.add(tstRadioButton);
        layout.putConstraint(SpringLayout.NORTH, tstRadioButton, -2, SpringLayout.NORTH, commandLabel);
        layout.putConstraint(SpringLayout.WEST, tstRadioButton, 10, SpringLayout.EAST, commandLabel);

        // BST Tree Radio Buttons
        JRadioButton bstRadioButton = new JRadioButton("BST", false);
        bstRadioButton.addChangeListener(changeListener);
        add(bstRadioButton);
        dataStructureGroup.add(bstRadioButton);
        layout.putConstraint(SpringLayout.NORTH, bstRadioButton, 0, SpringLayout.NORTH, tstRadioButton);
        layout.putConstraint(SpringLayout.WEST, bstRadioButton, 2, SpringLayout.EAST, tstRadioButton);

        // Trie Tree Radio Buttons
        JRadioButton trieRadioButton = new JRadioButton("Trie", false);
        trieRadioButton.addChangeListener(changeListener);
        add(trieRadioButton);
        dataStructureGroup.add(trieRadioButton);
        layout.putConstraint(SpringLayout.NORTH, trieRadioButton, 0, SpringLayout.NORTH, tstRadioButton);
        layout.putConstraint(SpringLayout.WEST, trieRadioButton, 2, SpringLayout.EAST, bstRadioButton);

        // Hash Map Radio Buttons
        JRadioButton hashRadioButton = new JRadioButton("Hash", false);
        hashRadioButton.addChangeListener(changeListener);
        add(hashRadioButton);
        dataStructureGroup.add(hashRadioButton);
        layout.putConstraint(SpringLayout.NORTH, hashRadioButton, 0, SpringLayout.NORTH, tstRadioButton);
        layout.putConstraint(SpringLayout.WEST, hashRadioButton, 2, SpringLayout.EAST, trieRadioButton);

        // Command Text Field
        commandTextField = new JTextField(31);
        add(commandTextField);
        layout.putConstraint(SpringLayout.NORTH, commandTextField, 5, SpringLayout.SOUTH, commandLabel);
        layout.putConstraint(SpringLayout.WEST, commandTextField, 0, SpringLayout.WEST, commandLabel);
        commandTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == (char) KeyEvent.VK_ENTER) {
                    String[] parts = commandTextField.getText().trim().split(" ");

                    if (parts.length > 0) {
                        ArrayList<String> params = new ArrayList<String>(parts.length - 1);

                        // To handle quotation:
                        // If a part starts with quotation, append next parts until
                        // the one that ends with quotation, then delete quotations
                        String param = "";
                        boolean isInQuote = false;
                        for (int i = 1; i < parts.length; i++) {
                            if (parts[i].length() > 0 && parts[i].charAt(0) == '"' && !isInQuote) {
                                isInQuote = true;
                            }
                            if (isInQuote) {
                                param += parts[i] + " ";
                                if (parts[i].length() > 0 && parts[i].charAt(parts[i].length() - 1) == '"') {
                                    isInQuote = false;
                                    params.add(param.substring(1, param.length() - 2));
                                    param = "";
                                }
                            } else {
                                params.add(parts[i]);
                            }
                        }
                        String[] parameters = new String[params.size()];
                        params.toArray(parameters);
                        getCommand(parts[0], parameters);
                    }
                    commandsStack.push(commandTextField.getText());
                    commandTextField.setText("");
                }
            }

            /**
             * Providing a command history
             * @param e KeyPressed event
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    commandTextField.setText(commandsStack.pop());
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    commandTextField.setText(commandsStack.push());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        // Build Button
        JButton buildButton = new JButton("Build");

        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                build();
            }
        });
        add(buildButton);
        layout.putConstraint(SpringLayout.NORTH, buildButton, 3, SpringLayout.SOUTH, commandTextField);
        layout.putConstraint(SpringLayout.WEST, buildButton, 0, SpringLayout.WEST, commandTextField);

        // Reset Button
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });
        add(resetButton);
        layout.putConstraint(SpringLayout.NORTH, resetButton, 0, SpringLayout.NORTH, buildButton);
        layout.putConstraint(SpringLayout.WEST, resetButton, 27, SpringLayout.EAST, buildButton);

        // Help Button
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendToOutput(
                        "\n" +
                                "---------------------------------------------\n" +
                                "HELP\n\n" +
                                "add: add <document_name_in_current_folder>\n" +
                                "delete:  del <document_name>\n" +
                                "update:  update <document_name>\n"+
                                "get list of words in program: list –w\n"+
                                "get list of files indexed in program: list –l\n"+
                                "get list of files in folder: list –f\n"+
                                "search statement: search -s\n"+
                                "search statement: search -w\n"+
                                "---------------------------------------------\n"
                ); // TODO: add help text here
            }
        });
        add(helpButton);
        layout.putConstraint(SpringLayout.NORTH, helpButton, 0, SpringLayout.NORTH, buildButton);
        layout.putConstraint(SpringLayout.WEST, helpButton, 27, SpringLayout.EAST, resetButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(exitButton);
        layout.putConstraint(SpringLayout.NORTH, exitButton, 0, SpringLayout.NORTH, buildButton);
        layout.putConstraint(SpringLayout.WEST, exitButton, 27, SpringLayout.EAST, helpButton);
    }

    /**
     * This method will do the job for building the data structures
     */
    private void build() {
        long start = System.currentTimeMillis();
        readFiles(folderTextField.getText());
        long duration = (System.currentTimeMillis()) - start;
        appendToOutput("Duration = " + duration + " milliseconds");
        appendToOutput("Height = " + dataStructure.height());
        countOfFilesOfDataStructures();
        int count = dataStructure.wordsCount();
        appendToOutput("Number of words found = " + count + "\n");

    }

    /**
     * Method to reset the program
     */
    private void resetForm() {
        folderTextField.setText("");
        output.setText("");
        dataStructureGroup.clearSelection();
        dataStructureGroup.setSelected(dataStructureGroup.getElements().nextElement().getModel(), true);
        commandTextField.setText("");
        commandTextField.requestFocus();
    }

    /**
     * This method will read the given file and will return a string
     * @param file Filename
     * @return Content of file
     */
    private String readFile(String file) {
        String content = "";
        try {
            String sCurrentLine;
            BufferedReader br = new BufferedReader(new FileReader(folderTextField.getText() + File.separator + file));
            while ((sCurrentLine = br.readLine()) != null) {
                content += sCurrentLine + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * Will get the files from the folder chosen
     * @param folderAddress Folder to reads file from
     */
    private void readFiles(String folderAddress) {
        File folder = new File(folderAddress);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    addCommand(file.getAbsolutePath(), false);
                }
            }

            if (dataStructure instanceof BST)
                bst.balancedTree();
            if (dataStructure instanceof TST)
                tst.balancedTree();
        }
    }

    /**
     * Getting the words from the string which has been read from a file and then
     * filtering it from stopwords and unwanted characters like numbers and punctuations
     * @param content String which has been read from a file
     * @return Array of keywords
     */
    private ArrayList<String> getWords(String content) {
        String[] words = content.split("\\s+");
        ArrayList<String> keywords = new ArrayList<String>();
        for (String word : words) {
            word = word.toLowerCase().trim();
            word = word.replaceAll("[\\d\\W]+", "");

            // Ignore empty words
            if (word.isEmpty()) continue;

            boolean isStopWord = false;
            for (String stopWord : stopWords) {
                if (word.equals(stopWord)) {
                    isStopWord = true;
                    break;
                }
            }
            if (!isStopWord)
                keywords.add(word);
        }
        return keywords;
    }

    /**
     * The method to call other methods when a specific command has been given
     * @param command command name
     * @param params array of parameters
     */
    private void getCommand(String command, String[] params) {
        if (command.equals("add")) {
            addCommand(params[0], true);
        }
        if (command.equals("del")) {
            deleteCommand(params[0]);
        }
        if (command.equals("update")) {
            updateCommand(params[0]);
        }
        if (command.equals("list")) {
            if (params[0].toLowerCase().equals("-w"))
                getListOfWords();
            else if (params[0].toLowerCase().equals("-f"))
                getListOfFilesOfFolder();
            else if (params[0].toLowerCase().equals("-l"))
                getListOfFilesOfDataStructures();
        }
        if (command.equals("search")) {
            if (params[0].toLowerCase().equals("-w"))
                findWord(params[1]);
            else if (params[0].toLowerCase().equals("-s"))
                findStatement(params[1]);
        }
    }

    /**
     * Read the given file and add it's content to data structures
     * @param filename Given file name
     * @param print Should it write file name in output or not
     */
    private void addCommand(String filename, boolean print) {
        filename = filename.replace(folderTextField.getText() + File.separator, "");
        final ArrayList<String> mustInsertWords = getWords(readFile(filename));

        for (String insertWords : mustInsertWords)
            dataStructure.insert(insertWords, filename);

        if (print)
            appendToOutput("File \"" + filename + "\" added successfully\n");
    }

    /**
     * Delete given file from data structures
     * @param filename Given file name
     */
    private void deleteCommand(String filename) {
        filename = filename.replace(folderTextField.getText() + File.separator, "");
        dataStructure.deleteFile(filename);
    }

    /**
     * Update given file in data structures
     * @param filename Given file name
     */
    private void updateCommand(String filename) {
        filename = filename.replace(folderTextField.getText() + File.separator, "");
        //delete file
        dataStructure.deleteFile(filename);

        //add again
        final ArrayList<String> mustInsertWords = getWords(readFile(filename));
        for (String insertWords : mustInsertWords) {
            dataStructure.insert(insertWords, filename);
        }
    }

    /**
     * Print list of words in data structures and count them
     */
    private void getListOfWords() {
        appendToOutput(dataStructure.traverse());
        int count = dataStructure.wordsCount();
        appendToOutput("Number of words found = " + count + "\n");
    }

    /**
     * Prints list of file in the folder
     */
    private void getListOfFilesOfFolder() {
        String filenames = "";
        File folder = new File(folderTextField.getText());
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    filenames += file.getAbsolutePath().replace(folderTextField.getText() + File.separator, "") + " ";
                }
            }
            appendToOutput(filenames.trim() + "\n");
        }
    }

    /**
     * Print files in data structures
     */
    private void getListOfFilesOfDataStructures() {
        String files = "";
        ArrayList<String> dataStructureFiles = dataStructure.getFiles();
        for (String file : dataStructureFiles) {
            files += file + " ";
        }
        appendToOutput(files.trim() + "\n");
    }

    /**
     * Finds given word in dataStructure and prints filenames containing the word
     * @param word Given word
     */
    private void findWord(String word) {
        String files = "";
        word = word.trim().toLowerCase();
        long start = System.currentTimeMillis();

        ArrayList<String> dataStructureFiles = dataStructure.fileListOfFoundWord(word);
        for (String file : dataStructureFiles) {
            files += file + " ";
        }

        long duration = (System.currentTimeMillis()) - start;
        appendToOutput(files.trim());
        appendToOutput("took " + duration + " milliseconds\n");
    }

    /**
     * To make the print on the text field
     * @param text The text to print
     */
    private void appendToOutput(String text) {
        output.setText(output.getText() + text + "\n");
    }

    /**
     * Finds keywords of given statement in dataStructure and prints filenames containing the keywords
     * @param statement Given statement
     */
    private void findStatement(String statement) {
        String filenames = "";
        ArrayList<String> keyWords = getWords(statement);
        long start = System.currentTimeMillis();

        ArrayList<String> foundStatementFile = new ArrayList<String>();
        for (String keyWord : keyWords) {
            ArrayList<String> files = dataStructure.fileListOfFoundWord(keyWord);
            for (String file : files) {
                if (foundStatementFile.indexOf(file) == -1)
                    foundStatementFile.add(file);
            }
        }

        long duration = (System.currentTimeMillis()) - start;

        for (String file : foundStatementFile) {
            filenames += file + " ";
        }

        appendToOutput(filenames.trim());
        appendToOutput("took " + duration + " milliseconds\n");
    }

    /**
     * Prints count of files stored in data structures
     */
    private void countOfFilesOfDataStructures() {
        ArrayList<String> files = dataStructure.getFiles();
        appendToOutput("The number of files = " + files.size());
    }
}
