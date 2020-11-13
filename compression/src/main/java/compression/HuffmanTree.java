package compression;

/**
 *
 * @author tiera
 * The superclass that forms the basis for the nodes and leaves for the Huffman tree structure
 * @param weight    represents the combined "weight" of the leaves in the tree: i.e. how many instances 
 *                  are there of the characters located in the leaves
 */
abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int weight;

    public HuffmanTree(int weight) {
        this.weight = weight;
    }
    
    @Override
    public int compareTo(HuffmanTree comparee) {
        return weight - comparee.weight;
    }
    
}