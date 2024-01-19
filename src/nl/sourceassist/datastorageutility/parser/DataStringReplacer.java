package nl.sourceassist.datastorageutility.parser;

import nl.sourceassist.datastorageutility.datastructure.CompositeNode;
import nl.sourceassist.datastorageutility.datastructure.LeafNode;

/**
 * The DataStringReplacer is a decorator parser that replaces (a part of) a string in a node's data.
 * Whenever the provided node is a compositeNode, it is skipped.
 */
public class DataStringReplacer extends BaseParser {

    /**
     * The string to look for when replacing data.
     */
    private String toReplace;

    /**
     * The string to replace instances of toReplace with.
     */
    private String replaceWith;

    /**
     * Create an instance of a parser that replaces a part of a node's data.
     *
     * @param parent the parent parser, the list needs to end with an instance of NodeParser
     * @param toReplace the string to look for when replacing
     * @param replaceWith the string to replace instances of toReplace with
     */
    public DataStringReplacer(Parser parent, String toReplace, String replaceWith) {
        super(parent);
        this.toReplace = toReplace;
        this.replaceWith = replaceWith;
    }

    /** {@inheritDoc} **/
    @Override
    protected CompositeNode handleCompositeNode(CompositeNode target) {
        return target;
    }

    /** {@inheritDoc} **/
    @Override
    protected LeafNode handleLeadNode(LeafNode target) {
        target.setData(target.getData().replace(this.toReplace, this.replaceWith));
        return target;
    }
}
