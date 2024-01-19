package nl.sourceassist.datastorageutility.parser;

import nl.sourceassist.datastorageutility.datastructure.IdentifiableNode;

/**
 * The parser interface is a part of the decorator structure for processing data.
 * The interface can be inherited by steps that might be executed during merging.
 */
public interface Parser {

    /**
     * Perform an operation on a specific node within a structure.
     *
     * @param target the target node to parse
     * @return the updated node.
     */
    IdentifiableNode process(IdentifiableNode target);
}