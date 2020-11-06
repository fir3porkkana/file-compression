package compression;

/**
 *
 * @author tiera
 * The superclass that forms the basis for the nodes and leaves for the Huffman tree structure
 * @param value     the character that the leaf represents
 * @param weight    represents the "weight" or the number of occurrences of the character in the input the tree is built off of
 */
abstract class HuffmanLeaf extends HuffmanTree {
    public char value;

    public HuffmanLeaf(char value, int weight) {
        super(weight);
        this.value = value;
    }


}