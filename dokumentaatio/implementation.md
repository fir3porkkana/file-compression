## Implementation

the program invokes a CLI and the Huffman class in its main class, through which the user commands are issued. the Huffman class uses its own huffmanNode -data structure to form the tree it uses to then execute the encoding itself.

an input can be read from a file or an input from the command line. then, that is passed to the huffman class, compresses/decompresses the input and either passes the result to the io side of the program to output to a file or prints it to the console.


## Time intensity

the compression works about as you'd expect and follows the time intensity as quoted here: "The time complexity of the Huffman algorithm is O(nlogn). Using a heap to store the weight of each tree, each iteration requires O(logn) time to determine the cheapest weight and insert the new weight. There are O(n) iterations, one for each item."

the implementation here is done via a heap, so the above time analysis from the first source applies.


## shortcomings

there are 0-7 extra characters that correspond to the path '0' in every decompressed file, due to bit-rounding in the io-part of the program, but as that is addition and no informational value is lost, it's not the end of the world (albeit annoying, and a bug to be fixed for sure).

for some reason, the jar cannot determine the main class name even though it's properly declared in ```build.gradle```. as such, the program must be run via ```java -cp [jar name here] compression.Main``` to point out the main class name to java, instead of just ```java [jar name here]```.

### sources
[the Huffman algorithm encoding/decoding](https://www.cs.auckland.ac.nz/software/AlgAnim/huffman.html#:~:text=The%20time%20complexity%20of%20the,iterations%2C%20one%20for%20each%20item.)

[succinct encoding for a tree](https://en.wikipedia.org/wiki/Binary_tree#Succinct_encodings) for encoding the huffman tree into binary