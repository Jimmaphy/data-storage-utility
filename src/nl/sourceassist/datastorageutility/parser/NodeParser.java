package nl.sourceassist.datastorageutility.parser;

import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;

/**
 * The NodeParser is the base class of the decorator structure and should be used as the root.
 * Its purpose is to return the final node to the end user.
 */
public class NodeParser implements Parser {

    /**
     * Process the node by return it as is.
     * This function acts as an identity function.
     *
     * @param target the target node to parse
     * @return the original node
     */
    @Override
    public IdentifiableNode process(IdentifiableNode target) {
        return target;
    }
}
