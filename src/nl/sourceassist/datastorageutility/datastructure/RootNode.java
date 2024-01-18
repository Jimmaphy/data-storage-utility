package nl.sourceassist.datastorageutility.datastructure;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.concurrent.StructureViolationException;

/**
 * The root of a data structure comprised of composable IdentifiableNode objects.
 * This class acts not only as the root,
 * but also as the builder interface for the structure.
 */
public class RootNode {

    /**
     * Whether the depth should be enforced or not.
     * This value is determined by the value of startingDepth during initialization.
     * Whenever that value is not 0, enforced becomes true.
     */
    private final int maxDepth;

    /**
     * The actual data structure.
     * Holds a composition of IdentifiableNode objects.
     */
    private final ArrayList<IdentifiableNode> children;

    /**
     * Create an instance of the rootNode builder class.
     * The maximum size of the structure is set to the maximum value of the int datatype.
     *
     */
    public RootNode() {
        this.maxDepth = Integer.MAX_VALUE;
        this.children = new ArrayList<>();
    }

    /**
     * Create an instance of the rootNode builder class.
     * The maximum size is provided by through the parameter.
     *
     * @param maximumDepth the maximum depth of the structure, minimal value is 1.
     * @throws NegativeArraySizeException whenever maximumDepth is lower than 1.
     */
    public RootNode(int maximumDepth) {
        if (maximumDepth < 1) {
            throw new NegativeArraySizeException();
        }

        this.maxDepth = maximumDepth;
        this.children = new ArrayList<>();
    }

    /**
     * Internal constructor used to check additional length when adding composite nodes.
     * In order to check for the extra length, a simple instance with a single node is needed.
     * However, none of the checks should be executed since that will create an infinite loop.
     *
     * @param node the node you want to add to the structure without checks.
     */
    private RootNode(IdentifiableNode node) {
        this.maxDepth = Integer.MAX_VALUE;
        this.children = new ArrayList<>();
        this.children.add(node);
    }

    /**
     * Return the current depth of the data structure.
     * The number returned is the deepest point the structure.
     *
     * @return the depth of the structure
     */
    public int getDepth() {
        int depth = 0;

        if (!this.children.isEmpty()) {
            ArrayList<IdentifiableNode> queue = new ArrayList<>(this.children);

            while(!queue.isEmpty()) {
                ArrayList<IdentifiableNode> currentQueue = new ArrayList<>(queue);
                queue.clear();

                for (IdentifiableNode node : currentQueue) {
                    if (node instanceof CompositeNode) {
                        queue.addAll(((CompositeNode) node).getChildren());
                    }
                }

                depth = depth + 1;
            }
        }

        return depth;
    }

    /**
     * Helper function to determine the depth of a node.
     * When a composite node is provided, the depth of that node will be calculated.
     *
     * @param node the node to analyze
     * @return the extra depth required for the node.
     */
    private int determineExtraDepth(IdentifiableNode node) {
        int size = 1;

        if (node instanceof CompositeNode) {
            RootNode structure = new RootNode(node);
            size = structure.getDepth();
        }

        return size;
    }

    public ArrayList<IdentifiableNode> getChildren() {
        return this.children;
    }

    /**
     * Get a child from the data structure.
     *
     * @param key the key of the child to look for
     * @return the child object, null if not found
     */
    private IdentifiableNode getChild(String key) {
        if (!this.children.isEmpty()) {
            ArrayList<IdentifiableNode> queue = new ArrayList<>(this.children);

            while(!queue.isEmpty()) {
                IdentifiableNode node = queue.removeFirst();

                if (node.getKey().equals(key)) {
                    return node;
                }

                if (node instanceof CompositeNode) {
                    queue.addAll(((CompositeNode) node).getChildren());
                }
            }
        }

        return null;
    }

    /**
     * Get the depth of a specific node.
     *
     * @param key the key of the node to look for
     * @return the depth of the node, starting at 1 for root level nodes, -1 if not found
     */
    public int getNodeDepth(String key) {
        if (!this.children.isEmpty()) {
            ArrayList<IdentifiableNode> queue = new ArrayList<>(this.children);
            int depth = 1;

            while(!queue.isEmpty()) {
                if (queue.stream().anyMatch(node -> node.getKey().equals(key))) {
                    return depth;
                }

                for (IdentifiableNode node : new ArrayList<>(queue)) {
                    if (node instanceof CompositeNode) {
                        queue.addAll(((CompositeNode) node).getChildren());
                    }
                }

                depth = depth + 1;
            }
        }

        return -1;
    }

    /**
     * Check whether a node with the provided key exists directly below the root.
     *
     * @param key the key of the node to search for
     * @return true if the node exists, false if not
     */
    private boolean doesParentHaveChildWithKey(String key) {
        ArrayList<IdentifiableNode> children = new ArrayList<>(this.children);
        return hasChild(key, children);
    }

    /**
     * Check whether a node with the provided key exists within the parent node.
     *
     * @param key the key of the node to search for
     * @param parent the parent of the node.
     * @return true if the node exists, false if not
     */
    private boolean doesParentHaveChildWithKey(String key, CompositeNode parent) {
        ArrayList<IdentifiableNode> children = parent.getChildren();
        return hasChild(key, children);
    }

    /**
     * Check whether a child exists in a list of children.
     * This function is meant to be used in combination with doesParentHaveChildWithKey.
     *
     * @param key the key of the child to look for
     * @param children the list of children to search in
     * @return true if the child exists, false otherwise
     * @see RootNode#doesParentHaveChildWithKey
     */
    private static boolean hasChild(String key, ArrayList<IdentifiableNode> children) {
        for (IdentifiableNode child : children) {
            if (child.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add a child node to the data structure.
     * When parent is set to null, the child is added to the root of the structure.
     * Before adding the child, the key is checked for duplicity.
     * When a parent is provided,
     * the type and the depth of the parent are checked before adding the new node.
     *
     * @param newChild the child node to add to the structure
     * @throws NullPointerException whenever the provided node is null
     * @throws KeyAlreadyExistsException if a child with the same key already exists
     * @throws IndexOutOfBoundsException if the maximum depth of the structure is violated
     */
    public void addChild(IdentifiableNode newChild) {
        if (newChild == null) {
            throw new NullPointerException();
        }

        if (doesParentHaveChildWithKey(newChild.getKey())) {
            throw new KeyAlreadyExistsException();
        }

        if (this.maxDepth < this.determineExtraDepth(newChild)) {
            throw new IndexOutOfBoundsException();
        }

        else {
            this.children.add(newChild);
        }
    }

    /**
     * A new node is added to an existing node.
     * The function checks whether the new Node follows the depth rules,
     * whether the parent exists and whether the parent is a composite node.
     *
     * @param newChild the child node to add to the structure
     * @param parentKey the key of the parent, provide null add the child directly to the root
     * @throws NullPointerException whenever the provided node is null
     * @throws KeyAlreadyExistsException if a child with the same key already exists
     * @throws StructureViolationException when the chosen parent is LeafNode or does not exist
     * @throws IndexOutOfBoundsException if the maximum depth of the structure is violated
     */
    public void addChild(IdentifiableNode newChild, String parentKey) {
        IdentifiableNode parent = getChild(parentKey);

        if (newChild == null) {
            throw new NullPointerException();
        }

        if (!(parent instanceof CompositeNode)) {
            throw new StructureViolationException();
        }

        if (doesParentHaveChildWithKey(newChild.getKey(), (CompositeNode) parent)) {
            throw new KeyAlreadyExistsException();
        }

        if (this.maxDepth < this.getNodeDepth(parentKey) + this.determineExtraDepth(newChild)) {
            throw new IndexOutOfBoundsException();
        }

        ((CompositeNode) parent).addChild(newChild);
    }
}