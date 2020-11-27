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
        
        HuffmanNode rootNode = makeTreeFromFrequencyArray(charFreqs);
        
        String[] lookupTable = populateLookupTable(rootNode, new String[256]);
        
        String encodedFileString = generateBinaryOutput(lookupTable, input);
        
        String encodedTreeString = "";

        return new HuffmanEncodedResult(rootNode, encodedFileString);
    }
    
    /**
     * Encodes the given Huffman tree into a string of 0s and 1s.
     * @param tree Huffman tree to be encoded
     * @param bitTreeStructure the string that ends up as the bit representation of the tree
     * @return a string that contains the given huffman tree in bit form
     */
    public String encodeTreeToBinaryForm(HuffmanNode tree, String bitTreeStructure) {
        if (!tree.isLeaf) {
            bitTreeStructure += '0';
            //first, encode the leftmost subtree
            bitTreeStructure = encodeTreeToBinaryForm(tree.left, bitTreeStructure);   
            //then, the subtree on the right
            bitTreeStructure = encodeTreeToBinaryForm(tree.right, bitTreeStructure);
        } else if (tree.isLeaf) {
            //get the node's character in 8-bit-long binary format and add it 
            //directly behind the leaf indicator. do the same for its weight in a 32-bit
            StringBuilder bitBuilder = new StringBuilder(7);
            
            //get the character's binary representation and ensure it's 8 bits long
            String nodeValueBitForm = Integer.toBinaryString(tree.value);
            for (int i = 0; i < 8 - nodeValueBitForm.length(); i++) {
                bitBuilder.append('0');
            }
            nodeValueBitForm = bitBuilder.toString() + nodeValueBitForm;
            
            bitBuilder.setLength(0);
            
            //get the weight's binary representation and ensure it's 32 bits long
            String nodeWeightBitForm = Integer.toBinaryString(tree.weight);
            for (int i = 0; i < 32 - nodeValueBitForm.length(); i++) {
                bitBuilder.append('0');
            }
            nodeWeightBitForm = bitBuilder.toString() + nodeWeightBitForm;
            
            bitTreeStructure += bitTreeStructure + nodeValueBitForm + nodeWeightBitForm;
        }
        
        return bitTreeStructure;
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
     * Decodes the given string of 0s and 1s to its original form.
     * [WORK IN PROGRESS - NOT YET FINISHED]
     * @param i the index where the function is operating
     * @param bitTreeStructure the bit string -form tree that is to be converted to its original form
     * @return a string that contains the given huffman tree in bit form
     */
    public HuffmanNode decodeFreqArrayFromBinaryForm(String bitTreeStructure, int i, HuffmanNode node) {
        char bit = bitTreeStructure.charAt(i);
        i++;
        
        if (bit == '1') {
            
        } else if (bit == '0') {
            
        }
        return new HuffmanNode(bit, i);
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