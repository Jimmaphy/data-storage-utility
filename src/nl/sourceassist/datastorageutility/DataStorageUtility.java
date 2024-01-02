package nl.sourceassist.datastorageutility;

import nl.sourceassist.datastorageutility.datastructure.CompositeNode;
import nl.sourceassist.datastorageutility.datastructure.LeafNode;
import nl.sourceassist.datastorageutility.datastructure.RootNode;

public class DataStorageUtility {
    public static void main(String[] args) {
        RootNode node = new RootNode(2);
        node.addChild(new CompositeNode("first"));
        node.addChild(new LeafNode("second", "hello"));
        node.addChild(new LeafNode("hello", "reason"), "first");

        CompositeNode composite = new CompositeNode("bigger");
        CompositeNode anotherNode = new CompositeNode("evenBigger");
        composite.addChild(anotherNode);
        anotherNode.addChild(new LeafNode("bye", "peasant"));

        node.addChild(composite);
    }
}
