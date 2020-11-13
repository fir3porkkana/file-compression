import compression.HuffmanNode;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestHuffmanNode {
    
    @Test
    public void testLeafNodeCreation() {
        char c = (char) 97;
        HuffmanNode leafyBoi = new HuffmanNode(c, 5);
        assertTrue(leafyBoi.isLeaf);
    }
    
    @Test
    public void testParentNodeCreation() {
        HuffmanNode leafyA = new HuffmanNode((char) 97, 1);
        HuffmanNode leafyB = new HuffmanNode((char) 98, 2);
        HuffmanNode leafyC = new HuffmanNode((char) 99, 3);
        
        HuffmanNode parentOfTwo = new HuffmanNode(leafyA, leafyB, leafyA.weight + leafyB.weight);
        HuffmanNode grandparent = new HuffmanNode(parentOfTwo, leafyC, parentOfTwo.weight + leafyC.weight);

        
        assertTrue(leafyA.isLeaf);
        assertTrue(parentOfTwo.weight == 3);
        assertFalse(parentOfTwo.isLeaf);
        assertTrue(grandparent.weight == 6);
    }

    @Test
    public void testComparability() {
        HuffmanNode leafyA = new HuffmanNode((char) 97, 1);
        HuffmanNode leafyB = new HuffmanNode((char) 98, 2);

        assertTrue(leafyA.compareTo(leafyB) < 0);
    }
}