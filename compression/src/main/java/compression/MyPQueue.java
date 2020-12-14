/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

/**
 *
 * @author tiera
 */
public class MyPQueue {
    private HuffmanNode[] array;
    private int numberOfEntries;

    public MyPQueue() {
        array = new HuffmanNode[257];
        numberOfEntries = 0;
    }

    public void add(HuffmanNode node) {
        if (numberOfEntries == 0) {
            array[0] = node;
            numberOfEntries++;
            return;
        }
//        
//        if (numberOfEntries == array.length - 1) {
//            increaseSize();
//        }
        
        for (int i = numberOfEntries; i > 0; i--) {
            if (0 < array[i].compareTo(node)) {
                
            }
        }
    }
    
    public void increaseSize() {
        double length = array.length*1.5 + 1;
        HuffmanNode[] newArray = new HuffmanNode[(int) length];
        for (int i = 0; i < numberOfEntries; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
    
    
}
