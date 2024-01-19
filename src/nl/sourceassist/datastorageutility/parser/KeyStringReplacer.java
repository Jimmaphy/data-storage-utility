package nl.sourceassist.datastorageutility.parser;

import nl.sourceassist.datastorageutility.datastructure.CompositeNode;
import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;
import nl.sourceassist.datastorageutility.datastructure.LeafNode;

/**
 * The KeyStringReplacer is a decorator parser that replaces (a part of) a string in a node's key.
 */
public class KeyStringReplacer extends BaseParser {

    /**
     * The string to look for when replacing data.
     */
    private String toReplace;

    /**
     * The string to replace instances of toReplace with.
     */
    private String replaceWith;

    /**
     * Create an instance of a parser that replaces a part of a node's key.
     *
     * @param parent the parent parser, the list needs to end with an instance of NodeParser
     * @param toReplace the string to look for when replacing
     * @param replaceWith the string to replace instances of toReplace with
     */
    public KeyStringReplacer(Parser parent, String toReplace, String replaceWith) {
        super(parent);
        this.toReplace = toReplace;
        this.replaceWith = replaceWith;
    }

    /** {@inheritDoc} **/
    @Override
    protected CompositeNode handleCompositeNode(CompositeNode target) {
        return (CompositeNode) this.changeTargetKey(target);
    }

    /** {@inheritDoc} **/
    @Override
    protected LeafNode handleLeadNode(LeafNode target) {
        return (LeafNode) this.changeTargetKey(target);
    }

    /**
     * Helper function that changes the key of a node.
     * This action is the same for both types of nodes.
     *
     * @param target the node to change the key of
     * @return a reference to the node of which the key was changed
     */
    private IdentifiableNode changeTargetKey(IdentifiableNode target) {
        target.setKey(target.getKey().replace(this.toReplace, this.replaceWith));
        return target;
    }
}
