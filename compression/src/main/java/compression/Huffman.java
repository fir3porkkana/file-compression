package compression;
/**
 *
 * @author tiera
 */
public class Huffman {
    private int decodingIndex;

    public Huffman() {
    }
    
    
    /**
     * Compresses any string-input it is given via the Huffman compression algorithm.
     * @param input the root node of the Huffman tree to be used
     */
    public String compress(String input) {
        int[] charFreqs = new int[256];
        
        //count the frequency of each character
        for (char c : input.toCharArray()) {
            charFreqs[c]++;
        }
        
        //build the tree
        HuffmanNode rootNode = makeTreeFromFrequencyArray(charFreqs);
        
        String[] lookupTable = populateLookupTable(rootNode, new String[256]);
        
        //encode the message
        String encodedFileString = generateBinaryOutput(lookupTable, input);
        
        //encode the tree
        StringBuilder treeBuilder = new StringBuilder();
        encodeNode(rootNode, treeBuilder);
        
        String encodedTreeString = treeBuilder.toString();
        
        //get the encoded tree length
        String treeLength = Integer.toBinaryString(encodedTreeString.length());
        //... and store it in a 16-bit byte
        treeBuilder.setLength(0);
        for (int i = 0; i < 16 - treeLength.length(); i++) {
                treeBuilder.append('0');
        }
        treeLength = treeBuilder.toString() + treeLength;
        
        //return the length of the tree, the tree itself and the original message
        //in an encoded string
        return treeLength + encodedTreeString + encodedFileString;
    }
    
    /**
     * A recursive function that encodes a Huffman tree into a bit-string
     * @param node the root node of the tree to be encoded
     * @param treeBuilder a stringbuilder that is then used to build the result
     * one bit at a time. the end result is contained in the stringbuilder afterwards
     */
    public void encodeNode(HuffmanNode node, StringBuilder treeBuilder) {
        if (node.isLeaf) {
            StringBuilder bitBuilder = new StringBuilder();

            treeBuilder.append('1');
            String nodeValueBitForm = Integer.toBinaryString(node.value);
            for (int i = 0; i < 8 - nodeValueBitForm.length(); i++) {
                bitBuilder.append('0');
            }
            nodeValueBitForm = bitBuilder.toString() + nodeValueBitForm;
            treeBuilder.append(nodeValueBitForm);
        } else {
            treeBuilder.append('0');
            encodeNode(node.left, treeBuilder);
            encodeNode(node.right, treeBuilder);
        }
    }
    
    /**
     * A recursive function that reconstructs an encoded Huffman tree from a bit-string
     * @param treeString a string containing a Huffman tree in bit-form
     * @return a huffman subtree, the original call returning the original root node
     */
    public HuffmanNode readNode(String treeString) {
        char bit = treeString.charAt(decodingIndex);
        decodingIndex++;
        
        if (bit == '1') {
            
            //extract the character from the next 8 bits and move the index over 
            //to the next unread bit
            int charIntValue = Integer.parseInt(treeString.substring(decodingIndex, decodingIndex + 8), 2);
            decodingIndex += 8;
            
            return new HuffmanNode((char) charIntValue);
        } else {
            HuffmanNode left = readNode(treeString);
            HuffmanNode right = readNode(treeString);
            return new HuffmanNode(left, right);
        }
    }
    
    /**
     * Creates a Huffman tree from a character frequency array.
     * @param charFreqs an int array containing the frequency of each character 
     * located in the index corresponding to its numerical value
     * @return a Huffman tree representing the array
     */
    public HuffmanNode makeTreeFromFrequencyArray(int[] charFreqs) {
        MyPQueue pqueueContainingLeaves = new MyPQueue(256);
        for (int i = 0; i < charFreqs.length; i++) {
            if (charFreqs[i] > 0) {
                pqueueContainingLeaves.push(new HuffmanNode((char) i, charFreqs[i]));
            }
        }
        
        //combine the lowest weight nodes with a parent node until the 
        //queue only has one node, that being the root node
        while (pqueueContainingLeaves.size() > 1) {
            HuffmanNode left = pqueueContainingLeaves.poll();
            HuffmanNode right = pqueueContainingLeaves.poll();
            HuffmanNode tree = new HuffmanNode(left, right, left.weight + right.weight);
            
            pqueueContainingLeaves.push(tree);
        }
        
        //return the root node
        return pqueueContainingLeaves.poll();
    }
    
    /**
     * Decodes the given string of 0s and 1s into a character frequency array.
     * @param bitTreeStructure the bit string -form tree from which the frequency array will be extracted
     * @return the original frequency array that can be used to build a huffman tree
     */
//    public int[] decodeFreqArrayFromBinaryForm(String bitTreeStructure) {
//        int[] charFreqs = new int[256];
//        
//        for (int i = 0; i < bitTreeStructure.length(); i += 40) {
//            String characterAndWeight = bitTreeStructure.substring(i, i + 40);
//            int charIntValue = Integer.parseInt(characterAndWeight.substring(0, 8), 2);
//            int charWeightValue = Integer.parseInt(characterAndWeight.substring(9, 40), 2);
//                
//                //make an entry into the provided array/table with the extracted values
//             charFreqs[charIntValue] = charWeightValue;
//        }
//        //freq array will be complete after the loop
//        return charFreqs;
//    }
    
    /**
     * Decompress any binary input produced by the compress() -method
     * @param inputAsString string that contains the message-to-be-decoded and 
     * the associated decoding key (Huffman tree)
     */
    public String decompress(String inputAsString) {
        decodingIndex = 0;
        
        //extract length of the tree
        int treeLength = Integer.parseInt(inputAsString.substring(0, 16), 2);
        
        //with that, extract the tree itself
        String encodedTree = inputAsString.substring(16, 16 + treeLength);

        HuffmanNode huffmanTree = readNode(encodedTree);
        
        HuffmanEncodedResult encodedObject = new HuffmanEncodedResult(huffmanTree, inputAsString.substring(encodedTree.length() + 16));
        
        StringBuilder resultBuilder = new StringBuilder(inputAsString.length()/3);
        HuffmanNode currentNode = encodedObject.huffmanTree;
        int numberOfBits = 0;
        
        //if the root node has only one leaf, the original message consists of 
        //one sole character. form the original string and skip over the more 
        //complicated scenario up ahead
        if (currentNode.isLeaf) {
            for (int i = 0; i < encodedObject.encodedContent.length(); i++) {
                resultBuilder.append(currentNode.value);
            }
            numberOfBits = encodedObject.encodedContent.length() + 1;
        }

        //for each bit in the encoded string, traverse downward the tree
        while (numberOfBits < encodedObject.encodedContent.length()) {
            
            //if the bit is 0, go left, and if the bit is 1, go right
            //keep doing so until a leaf is encountered
            while (!currentNode.isLeaf) {
                char bit = encodedObject.encodedContent.charAt(numberOfBits);
                if (bit == '0') {
                    currentNode = currentNode.left;
                }
                if (bit == '1') {
                    currentNode = currentNode.right;
                }
                numberOfBits++;
            }
            
            //concatenate the discovered leaf's value to the result string
            resultBuilder.append(currentNode.value);
            
            //go back to the top of the tree to start over
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
        if (node.isLeaf) {
            lookupTable[node.value] = "0";
            return lookupTable;
        }
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