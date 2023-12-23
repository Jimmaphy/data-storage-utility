package nl.sourceassist.datastorageutility.datastructure;

import java.util.ArrayList;

/**
 * The CompositeNode class represents a composite node in a data structure.
 * A composite node is a node that can have child nodes.
 */
public class CompositeNode implements IdentifiableNode {

    /**
     * The key variable represents the key of the leaf node in a data structure.
     * It is used to uniquely identify the leaf node within the data structure.
     * The value of the key variable is set in the constructor of the LeafNode class,
     * and is not intended to be modified after its construction.
     */
    private final String key;

    /**
     * children is an ArrayList of IdentifiableNode objects.
     * It represents the children of a composite node in a data structure.
     * The CompositeNode class provides methods to manipulate the children list,
     * such as addChild(), removeChild(), and getChildren().
     */
    private final ArrayList<IdentifiableNode> children;

    /**
     * Creates a CompositeNode object with the given key.
     * The children list is initialized as an empty ArrayList object.
     *
     * @param key the key of the CompositeNode, used to uniquely identify it within a data structure
     */
    public CompositeNode(String key) {
        this.key = key;
        this.children = new ArrayList<>();
    }

    /** {@inheritDoc} */
    public String getKey() {
        return this.key;
    }

    /**
     * Returns the concatenated data of all child nodes.
     * The data for each child node is obtained by calling the child's getData method.
     * The data is concatenated using the key and data of each child node, separated by a colon and space.
     * The result is a string that represents the data of all child nodes,
     * with each child's key and data separated by a semicolon and space.
     *
     * @return the concatenated data of all child nodes as a string
     */
    public String getData() {
        return children.stream().reduce("",
            (String acc, IdentifiableNode child) -> acc + child.getKey() + ": " + child.getData() + "; ",
            String::concat
        );
    }

    /**
     * Adds a child node to the composite node.
     * The child node is only added if there is no existing child node with the same key.
     *
     * @param child the IdentifiableNode object to be added as a child node
     * @return true if the child node was successfully added, false otherwise
     */
    public boolean addChild(IdentifiableNode child) {
        if (this.children.stream().anyMatch(node -> node.getKey().equals(child.getKey()))) {
            return false;
        }

        this.children.add(child);
        return true;
    }

    /**
     * Removes a child node from the composite node if it exists.
     *
     * @param child the child node to be removed
     * @return true if the child node was successfully removed, false otherwise
     */
    public boolean removeChild(IdentifiableNode child) {
        return this.children.remove(child);
    }

    /**
     * Returns the children of the current composite node.
     *
     * @return an ArrayList containing the IdentifiableNode objects that represent the children of the composite node
     */
    public ArrayList<IdentifiableNode> getChildren() {
        return this.children;
    }
}
