package compression;

/**
 *
 * @author tiera
 * A node in the Huffman tree, has one or two children that can either be a subtree or a HuffmanLeaf
 * @param left the left child
 * @param rigth the right child
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    public int weight;
    public char value;
    
    public boolean isLeaf;

    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(HuffmanNode left, HuffmanNode right, int weight) {
        this.weight = weight;
        this.left = left;
        this.right = right;
        this.isLeaf = false;
    }
    
    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.left = left;
        this.right = right;
        this.isLeaf = false;
    }
    
    public HuffmanNode(char value, int weight) {
        this.weight = weight;
        this.value = value;
        this.isLeaf = true;
    }
    
    public HuffmanNode(char value) {
        this.value = value;
        this.isLeaf = true;
    }
    
    @Override
    public int compareTo(HuffmanNode comparee) {
        return weight - comparee.weight;
    }

}