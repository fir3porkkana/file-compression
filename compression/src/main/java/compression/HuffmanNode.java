package compression;

/**
 *
 * @author tiera
 * A node in the Huffman tree, has one or two children that can either be a subtree or a HuffmanLeaf
 * @param left the left child
 * @param rigth the right child
 */
abstract class HuffmanNode extends HuffmanTree {
    public HuffmanTree left;
    public HuffmanTree right;

    public HuffmanNode(HuffmanTree left, HuffmanTree right, int weight) {
        super(left.weight + right.weight);
        this.left = left;
        this.right = right;
    }

}