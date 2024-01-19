package nl.sourceassist.datastorageutility.datastructure;

/**
 * The IdentifiableNode interface represents data within a data structure.
 * A data structure is comparable to how JavaScript handles object.
 * Every component has a key, and it's associated value.
 */
public interface IdentifiableNode {

    /**
     * Returns the key of the component.
     * The key is a string that identifies the component.
     * It is used to uniquely identify the component within a data structure.
     *
     * @return the key of the component as a string
     */
    String getKey();

    /**
     * Returns the data associated with the component.
     * The data is a string representation of the component's value.
     * The data may contain information about the component itself and its children.
     *
     * @return the data associated with the component as a string
     */
    String getData();

    /**
     * Set the key of the node to a new value.
     * CAREFUL, using this functions means the application isn't checking for duplicate keys anymore.
     *
     * @param newKey the new key of the node.
     */
    void setKey(String newKey);
}
