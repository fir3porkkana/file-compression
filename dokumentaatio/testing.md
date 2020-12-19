## Testing

the project has been tested via unit tests and by hand

### unit tests

these tests cover some of the data structures and the functionality of the core class made for the project, and can be executed via ```gradle test```. 

### hand testing

this has been done simply by running the program via ```gradle run``` and manually typing a test input, as well as running some test files with the program. in the repository's ```dokumentaatio``` -directory features a couple of the files i used: ```bigsampleA.txt```, ```sample.txt``` and ```sample-2mb.txt```. there were no time issues with any of these.

as per the functioning method of the huffman algorithm, the (compression) performance varies.

here are the results i had with the included files:

* ```sample.txt``` size uncompressed/compressed: 21.4 KiB (21,952 bytes) / 17.5 KiB (17,917 bytes)

* ```sample-2mb.txt```: 2.1 MiB (2,167,737 bytes) / 2.3 MiB (2,383,306 bytes)

* ```bigsampleA.txt```: 1.6 MiB (1,704,950 bytes) / 520.2 KiB (532,670 bytes)

as seen here, the 2mb-size file of lorem ipsum seems to unfortunately have negative performance. on the other hand, repetitive inputs (such as ```bigsampleA.txt```) enjoy significant size shrinkage as expected.  