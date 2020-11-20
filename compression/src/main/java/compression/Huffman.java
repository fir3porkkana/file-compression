package compression;

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
    public HuffmanEncodedResult compress(String input) {
        int[] charFreqs = new int[256];
        
        //count the frequency for each character
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
        
//        byte[] bittibois = new byte[encodedFileString.length()];
//
//        for (int i = 0; i < encodedFileString.length(); i++) {
//            char c = encodedFileString.charAt(i);
//            byte bitti = (byte) c;
//            bittibois[i] = bitti;
//        }
//        
//        Files.write(Paths.get("./out"), bittibois);

        return new HuffmanEncodedResult(rootNode, encodedFileString);
    }
    
    /**
     * Decompress any binary input produced by the compress() -method
     * @param encodedObject object that contains the message-to-be-decoded and 
     * the associated decoding key (Huffman tree)
     */
    public String decompress(HuffmanEncodedResult encodedObject) {
        StringBuilder resultBuilder = new StringBuilder(encodedObject.encodedContent.length()/3);
        HuffmanNode currentNode = encodedObject.huffmanTree;
        int numberOfBits = 0;

        //for each bit in the encoded string, traverse downward the tree
        while (numberOfBits < encodedObject.encodedContent.length()) {
            
            //whenever a leaf is encountered, its value is concatenated to the 
            //result string and it returns to the starting (root) node
            while (!currentNode.isLeaf) {
                char bit = encodedObject.encodedContent.charAt(numberOfBits);
                if (bit == '0') {
                    currentNode = currentNode.left;
                } else if (bit == '1') {
                    currentNode = currentNode.right;
                }
                numberOfBits++;
            }
//            System.out.println("asdknnalfknsknfasknfdlakndflaskdnflanfdas");
            
            resultBuilder.append(currentNode.value);
            currentNode = encodedObject.huffmanTree;
        }
        
        return resultBuilder.toString();
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
     * Generates and returns the encoded message. 
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