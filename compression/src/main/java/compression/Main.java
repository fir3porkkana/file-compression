/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

import java.util.PriorityQueue;


/**
 *
 * @author tiera
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String test = "ääääääääääääääääääääääääääääääääääääääääääääblblblblblblbltesti";
        String test = "perseaivopaskapyllyäksdeedeedeedee";

 
        
        int[] charFreqs = new int[256];
        
        //lasketaan merkkien esiintymistiheys
        for (char c : test.toCharArray()) {
            charFreqs[c]++;
        }
        
        
        PriorityQueue<HuffmanNode> pqueueContainingLeaves = new PriorityQueue<>();
        
        for (int i = 0; i < charFreqs.length; i++) {
            if (charFreqs[i] > 0) {
                pqueueContainingLeaves.add(new HuffmanNode((char) i, charFreqs[i]));
            }
        }
        
        while (pqueueContainingLeaves.size() > 1) {
            HuffmanNode left = pqueueContainingLeaves.poll();
            HuffmanNode right = pqueueContainingLeaves.poll();
            HuffmanNode tree = new HuffmanNode(left, right, left.weight + right.weight);
            
            pqueueContainingLeaves.add(tree);
        }
        
        HuffmanNode rootNode = (HuffmanNode) pqueueContainingLeaves.poll();
        
        String[] lookupTable = populateLookupTable(rootNode, new String[256]);
        
       String asd = "asd"; 
    }
    /**
     * Calls a recursive function to generate the contents of the lookup-table 
     * based on the provided, complete Huffman tree
     * @param node the root node of the Huffman tree to be used
     * @param lookupTable the table that is to be populated
     * @return returns the same table that is given in the parameters after it
     * has been filled according to the contents of the Huffman tree.
     */
    public static String[] populateLookupTable(HuffmanNode node, String[] lookupTable) {
        recursivelyPopulate(node, "", lookupTable);
        return lookupTable;
    }
    
    /**
     * A recursive function that populates the provided lookup-table. 
     * @param node the root node of the Huffman tree to be used
     * @param lookupTable the table that is to be populated
     */
    public static void recursivelyPopulate(HuffmanNode node, String path, String[] lookupTable) {
        if (node.isLeaf) {
            lookupTable[node.value] = path;
        } else {
            recursivelyPopulate(node.left, path + "0", lookupTable);
            recursivelyPopulate(node.right, path + "1", lookupTable);
        }
    }
    
}
