package nl.sourceassist.datastorageutility.commander;

import nl.sourceassist.datastorageutility.datastructure.CompositeNode;
import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;
import nl.sourceassist.datastorageutility.datastructure.RootNode;
import nl.sourceassist.datastorageutility.files.File;
import nl.sourceassist.datastorageutility.files.FileFactory;
import nl.sourceassist.datastorageutility.parser.DataStringReplacer;
import nl.sourceassist.datastorageutility.parser.KeyStringReplacer;
import nl.sourceassist.datastorageutility.parser.NodeParser;
import nl.sourceassist.datastorageutility.parser.Parser;
import nl.sourceassist.datastorageutility.state.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * The Commander class handles interaction with the user based on a given state.
 * Whenever a state is finished, the commander moves the program to the next state.
 */
public class Commander {
    private State state;
    private Properties properties;
    private Console console;
    private ArrayList<File> files;
    private Parser parser;

    public Commander() throws IOException {
        this.state = State.STARTING;
        this.properties = new Properties();
        this.console = new Console();
        this.files = new ArrayList<>();
        this.parser = new NodeParser();

        this.properties.load(getClass().getResourceAsStream("/strings.properties"));
    }

    /**
     * Execute the next command.
     * The command to be executed is determined by the current state.
     * If a state is finished, the commander moves the program to the next state.
     *
     * @return true if the program is finished, false otherwise.
     */
    public boolean execute() {
        boolean executionResult = switch(this.state) {
            case STARTING -> this.executeStarting();
            case COLLECTING -> this.executeCollecting();
            case DEFINING -> this.executeDefining();
            case MERGING -> this.executeMerging();
            default -> throw new IllegalStateException();
        };

        if (executionResult) {
            this.nextState();
        }

        return this.state.equals(State.FINISHED);
    }

    /**
     * Move the application to the next state.
     * The program states change as follows:
     * Starting ->
     * Collecting ->
     * Defining ->
     * Merging ->
     * Finished.
     */
    private void nextState() {
        this.state = switch(this.state) {
            case STARTING -> State.COLLECTING;
            case COLLECTING -> State.DEFINING;
            case DEFINING -> State.MERGING;
            default -> State.FINISHED;
        };
    }

    /**
     * Perform an action that can handle exception on a given input string.
     * Whenever the action fails, a message and the error message is printed to the console.
     *
     * @param input the input string
     * @param onError the string to display on an error
     * @param onSuccess the lambda that contains the action that should be performed
     */
    private void onInputAction(String input, String onError, Consumer<String> onSuccess) {
        try {
            onSuccess.accept(input);
        }

        catch(Exception exception) {
            console.writeCommand(STR."\{onError} \{exception.getMessage()}");
        }
    }

    /**
     * Print the introduction and move to the next line.
     * @return true to indicate a finished job.
     */
    private boolean executeStarting() {
        console.writeInstruction(properties.getProperty("introduction"));
        return true;
    }

    /**
     * @return
     */
    private boolean executeCollecting() {
        console.writeCommand(properties.getProperty("nextFile"));
        String input = console.readLine();

        if (input.isEmpty() && this.files.size() < 2) {
            console.writeCommand(properties.getProperty("notEnoughFilesSelected"));
            return false;
        }

        else if (!input.isEmpty()) {
            onInputAction(input, properties.getProperty("invalidFile"), (file) -> this.files.add(FileFactory.OpenFileFactory(file)));
            return false;
        }

        return true;
    }

    private boolean executeDefining() {
        console.writeCommand(properties.getProperty("nextParser"));
        console.writeCommand(properties.getProperty("listParsers"));
        String[] input = console.readLine().split(String.valueOf(' '));

        if (input.length == 3) {
            this.parser = switch (input[0].toLowerCase()) {
                case "keystringreplacer" -> new KeyStringReplacer(this.parser, input[1], input[2]);
                case "datastringreplacer" -> new DataStringReplacer(this.parser, input[1], input[2]);
                default -> this.parser;
            };
        }

        else if (!input[0].isEmpty()) {
            console.writeInstruction(properties.getProperty("invalidParserArgument"));
        }

        return input[0].isEmpty();
    }

    private boolean executeMerging() {
        RootNode rootNode = new RootNode();

        for(File file : this.files) {
            CompositeNode fileNode = new CompositeNode(file.getFileName());

            for (IdentifiableNode node : file.readAllData().getChildren()) {
                fileNode.addChild(this.parser.process(node));
            }

            rootNode.addChild(fileNode);
        }

        return true;
    }
}
