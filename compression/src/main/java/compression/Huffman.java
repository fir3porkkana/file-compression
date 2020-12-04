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
    public String compress(String input) {
        int[] charFreqs = new int[256];
        
        //count the frequency for each character
        for (char c : input.toCharArray()) {
            charFreqs[c]++;
        }
        
        HuffmanNode rootNode = makeTreeFromFrequencyArray(charFreqs);
        
        String[] lookupTable = populateLookupTable(rootNode, new String[256]);
        
        String encodedFileString = generateBinaryOutput(lookupTable, input);
        
        PriorityQueue<HuffmanNode> pqueueContainingLeaves = new PriorityQueue<>();
        
        for (int i = 0; i < charFreqs.length; i++) {
            if (charFreqs[i] > 0) {
                pqueueContainingLeaves.add(new HuffmanNode((char) i, charFreqs[i]));
            }
        }
        
        String encodedTreeString = encodeTreeToBinaryForm(pqueueContainingLeaves);
        System.out.println("merkkejä: " + (encodedTreeString.length()/40));
        
        String divider = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        
        //return the tree and the encoded input with a 48-bit
        //divider in between the two
        return encodedTreeString + divider + encodedFileString;
    }
    
    /**
     * Encodes the given Huffman tree into a string of 0s and 1s.
     * @param tree Huffman tree to be encoded
     * @param treeBuilder the stringbuilder that ends up as the bit representation of the tree
     * @return a string that contains the given huffman tree in bit form
     */
    public String encodeTreeToBinaryForm(PriorityQueue<HuffmanNode> leaves) {
        //not a leaf, represented as a 0
        StringBuilder treeBuilder = new StringBuilder();
        while (leaves.size() > 0) {
            HuffmanNode leaf = leaves.poll();
            StringBuilder bitBuilder = new StringBuilder(7);
            
            //get the character's binary representation and ensure it's 8 bits long
            String nodeValueBitForm = Integer.toBinaryString(leaf.value);
            for (int i = 0; i < 8 - nodeValueBitForm.length(); i++) {
                bitBuilder.append('0');
            }
            nodeValueBitForm = bitBuilder.toString() + nodeValueBitForm;
            
            bitBuilder.setLength(0);
            
            //get the weight's binary representation and ensure it's 32 bits long
            String nodeWeightBitForm = Integer.toBinaryString(leaf.weight);
            for (int i = 0; i < 32 - nodeWeightBitForm.length(); i++) {
                bitBuilder.append('0');
            }
            nodeWeightBitForm = bitBuilder.toString() + nodeWeightBitForm;
            
            treeBuilder.append(nodeValueBitForm);
            treeBuilder.append(nodeWeightBitForm);
        }
        return treeBuilder.toString();
    }
    
    
    public HuffmanNode makeTreeFromFrequencyArray(int[] charFreqs) {
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
        
        return pqueueContainingLeaves.poll();
    }
    
    /**
     * Decodes the given string of 0s and 1s into a character frequency array.
     * @param bitTreeStructure the bit string -form tree from which the frequency array will be extracted
     * @return the original frequency array that can be used to build a huffman tree
     */
    public int[] decodeFreqArrayFromBinaryForm(String bitTreeStructure) {
        int[] charFreqs = new int[256];
        
        for (int i = 0; i < bitTreeStructure.length(); i += 40) {
            String characterAndWeight = bitTreeStructure.substring(i, i + 40);
            int charIntValue = Integer.parseInt(characterAndWeight.substring(0, 8), 2);
            int charWeightValue = Integer.parseInt(characterAndWeight.substring(9, 40), 2);
                
                //make an entry into the provided array/table with the extracted values
             charFreqs[charIntValue] = charWeightValue;
        }
        //freq array will be complete after the loop
        return charFreqs;
    }
    
    /**
     * Decompress any binary input produced by the compress() -method
     * @param inputAsString string that contains the message-to-be-decoded and 
     * the associated decoding key (Huffman tree)
     */
    public String decompress(String inputAsString) {
        
        String[] encodedTreeAndFile = inputAsString.split("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        int[] charFreqs = decodeFreqArrayFromBinaryForm(encodedTreeAndFile[0]);
        
        HuffmanNode huffmanTree = makeTreeFromFrequencyArray(charFreqs);
        
        HuffmanEncodedResult encodedObject = new HuffmanEncodedResult(huffmanTree, encodedTreeAndFile[1]);
        
        StringBuilder resultBuilder = new StringBuilder(inputAsString.length()/3);
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