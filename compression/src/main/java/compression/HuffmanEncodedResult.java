package compression;

/**
 *
 * @author tiera
 * An object used as output for the compress()-method
 * @param huffmanTree the tree that can be used to decode the encoded message
 * @param encodedContent the encoded message itself
 */
public class HuffmanEncodedResult {
    public HuffmanNode huffmanTree;
    public String encodedContent;

    public HuffmanEncodedResult(HuffmanNode huffmanTree, String encodedContent) {
        this.huffmanTree = huffmanTree;
        this.encodedContent = encodedContent;
    }
}