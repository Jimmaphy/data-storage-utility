package nl.sourceassist.datastorageutility.parser;

import nl.sourceassist.datastorageutility.datastructure.CompositeNode;
import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;
import nl.sourceassist.datastorageutility.datastructure.LeafNode;

/**
 * BaseParser is an abstract class meant to simplify the processing of nodes.
 * It handles the base functionality of the process function,
 * while allowing subclasses to define functionality for composite and leaf nodes separately.
 */
public abstract class BaseParser implements Parser {
    /**
     * The parent parser.
     * After processing the data, the result will be passed on to the next parser.
     */
    private final Parser parent;

    /**
     * Create an instance of BaseParser.
     * Creating it sets the parent of the Parser.
     *
     * @param parent the parent of this class.
     */
    protected BaseParser(Parser parent) {
        this.parent = parent;
    }

    /** {@inheritDoc} **/
    @Override
    public IdentifiableNode process(IdentifiableNode target) {
        IdentifiableNode node = target instanceof CompositeNode
            ? this.handleCompositeNode((CompositeNode) target)
            : this.handleLeadNode((LeafNode) target);

        return this.parent.process(node);
    }

    /**
     * Handle the processing of a CompositeNode.
     *
     * @param target the node to be processed
     * @return the reference to the processed node
     */
    protected abstract CompositeNode handleCompositeNode(CompositeNode target);

    /**
     * Handle the processing of a LeafNode.
     *
     * @param target the node to be processed
     * @return the reference to the processed node
     */
    protected abstract LeafNode handleLeadNode(LeafNode target);
}
