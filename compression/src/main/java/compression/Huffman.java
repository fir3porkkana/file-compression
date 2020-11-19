package compression;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;

/**
 *
 * @author tiera
 */
public class Huffman {

    public Huffman() {
    }
    
    
    /**
     * Compresses any input string-input it is given via the Huffman compression algorithm.
     * @param input the root node of the Huffman tree to be used
     */
    public void compress(String input) throws IOException {
        int[] charFreqs = new int[256];
        
        //lasketaan merkkien esiintymistiheys
        for (char c : input.toCharArray()) {
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
        
        String encodedFileString = generateBinaryOutput(lookupTable, input);
        
        byte[] bittibois = new byte[encodedFileString.length()];

        for (int i = 0; i < encodedFileString.length(); i++) {
            char c = encodedFileString.charAt(i);
            byte bitti = (byte) c;
            bittibois[i] = bitti;
        }
        
        Files.write(Paths.get("./out"), bittibois);
    }
    
    /**
     * Decompress any binary input produced by the compress() -method
     */
    public void decompress() {
        
    }
    
    /**
     * Calls a recursive function to generate the contents of the lookup-table 
     * based on the provided, complete Huffman tree
     * @param node the root node of the Huffman tree to be used
     * @param lookupTable the table that is to be populated
     * @return returns the same table that is given in the parameters after it
     * has been filled according to the contents of the Huffman tree.
     */
    public String[] populateLookupTable(HuffmanNode node, String[] lookupTable) {
        recursivelyPopulate(node, "", lookupTable);
        return lookupTable;
    }
    
    /**
     * A recursive function that populates the provided lookup-table. 
     * @param node the root node of the Huffman tree to be used
     * @param lookupTable the table that is to be populated
     */
    public void recursivelyPopulate(HuffmanNode node, String path, String[] lookupTable) {
        if (node.isLeaf) {
            lookupTable[node.value] = path;
        } else {
            recursivelyPopulate(node.left, path + "0", lookupTable);
            recursivelyPopulate(node.right, path + "1", lookupTable);
        }
    }
    
    /**
     * A recursive function that populates the provided lookup-table. 
     * @param lookupTable used to look up which binary string matches with each character in the input
     * @param input used to gather the encoded input string with the provided lookup-table
     */
    public String generateBinaryOutput(String[] lookupTable, String input) {
        
        //initialise a stringbuilder that is efficient in a loop with an 
        //approximate of the capacity required
        StringBuilder sb = new StringBuilder(input.length()*3);
        for (char c : input.toCharArray()) {
            sb.append(lookupTable[c]);
        }
        
        return sb.toString();
    }
}