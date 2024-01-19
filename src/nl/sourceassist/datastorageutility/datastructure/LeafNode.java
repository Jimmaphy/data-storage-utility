package nl.sourceassist.datastorageutility.datastructure;

/**
 * The LeafNode class represents a leaf node in a data structure.
 * It's a point within the structure that contains a single value.
 */
public class LeafNode implements IdentifiableNode {

    /**
     * The key variable represents the key of the leaf node in a data structure.
     * It is used to uniquely identify the leaf node within the data structure.
     * The value of the key variable is set in the constructor of the LeafNode class,
     * and is not intended to be modified after its construction.
     */
    private String key;

    /**
     * The data variable the data associated with the leaf node in a data structure.
     * It is used to store the value associated with this node.
     * The value of the data variable is set in the constructor of the LeafNode class.
     * and is not intended to be modified after its construction.
     */
    private String data;

    /**
     * Constructs a new LeafNode object with the given key and data.
     *
     * @param key the key of the leaf node, used to uniquely identify it within a data structure
     * @param data the data associated with the leaf node
     */
    public LeafNode(String key, String data) {
        this.key = key;
        this.data = data;
    }

    /** {@inheritDoc} */
    public String getKey() {
        return this.key;
    }

    /** {@inheritDoc} */
    public String getData() {
        return this.data;
    }

    /** {@inheritDoc} */
    public void setKey(String newKey) {
        this.key = newKey;
    }

    /**
     * Set the data of the node to a new value.
     *
     * @param newData the new key of the node.
     */
    public void setData(String newData) {
        this.data = newData;
    }
}
