/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

/**
 *
 * @author tiera
 * A fixed-capacity minimum priority-queue for the proprietary class HuffmanNode
 * (i.e. non-general)
 */
public class MyPQueue {
    private HuffmanNode[] array;
    private int size;

    public MyPQueue(int maxSize) {
        array = new HuffmanNode[maxSize];
        size = 0;
    }

    /**
    *
    * Adds a HuffmanNode to the priority queue, and rearranges the queue to 
    * ensure the smallest weight node is at the root of the heap
    * @param node node to be inserted into the queue
    */
    public void push(HuffmanNode node) {
        //throw a fit in case the specified max capacity would be exceeded
        if (size == array.length) {
            throw new ArrayIndexOutOfBoundsException("Too many entries!");
        }
        
        //size points to the next available and empty index
        int position = size;
        
        //place the node in the empty index
        array[position] = node;
        
        //then, let it "bubble up" to the right spot in the heap and lift it upwards
        //until it encounters a node that weighs less and "settles" on its place
        while (position > 0) {
            int parentIndex = ((position + 1) / 2) - 1;
            if (array[parentIndex].weight <= array[position].weight) {
                break;
            }
            swap(parentIndex, position);
            position = parentIndex;
        }
        
        size++;
    }
    
    /**
    *
    * Removes and returns the root of the heap (the smallest node) and then 
    * rebalances the heap so that the smallest node after the fact is found 
    * at the root
    * @return the smallest weight node in the queue
    */
    public HuffmanNode poll() {
        //can't poll from an empty queue
        if (size == 0) {
            throw new IllegalStateException("Cannot return a value from an empty queue!");
        }
        
        //store the smallest node for returning it at the end
        HuffmanNode smallestNode = array[0];
        
        //replace the smallest node with one from the bottom
        array[0] = array[size - 1];
        size--;

        int position = 0;
        //let the heavy node just placed at the top to "sink" down to whatever 
        //level it'll settle at
        while (position < size / 2) {
            int rightChild = (2 * position) + 1;
            int leftChild = rightChild + 1;
            
            //check that the child indexes exist. should that be the case, 
            //prioritise the right child
            if (rightChild < size && array[leftChild].weight <= array[rightChild].weight) {
                
                //if the child is heavier than the sinking node, stop going down
                if (array[position].weight <= array[rightChild].weight) {
                    break;
                }
                swap(position, rightChild);
                position = rightChild;
            } else {
                if (array[position].weight <= array[leftChild].weight) {
                    break;
                }
                swap(position, leftChild);
                position = leftChild;
            }
        }
        
        return smallestNode;
    }
    
    public void swap(int firstIndex, int secondIndex) {
        HuffmanNode temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
    
    /**
    *
    * Return the current size of the queue
    * @return the size of the queue
    */
    public int size() {
        return size;
    }
     
}
