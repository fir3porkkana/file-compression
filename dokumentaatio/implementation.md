## Implementation

the program invokes the Huffman class in its main class, through which the user commands are issued. the Huffman class uses its own huffmanNode -data structure to form the tree it uses to then execute the encoding itself.

the shorcomings are far and wide: the program is currently only able to encode and decode a string from the command line, and that string isn't even put into a file. (that's next up on the chopping block, and may come to fruition soon.)

### sources
[the Huffman algorithm encoding/decoding](https://www.cs.auckland.ac.nz/software/AlgAnim/huffman.html#:~:text=The%20time%20complexity%20of%20the,iterations%2C%20one%20for%20each%20item.)

[succinct encoding for a tree](https://en.wikipedia.org/wiki/Binary_tree#Succinct_encodings) for encoding the huffman tree into binary