/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author tiera
 */
public class TestMyPQueue {
    
    @Test
    public void testPQueueInsertion() {
        char c = (char) 97;
        HuffmanNode leafyBoi = new HuffmanNode(c, 5);
        
        MyPQueue queue = new MyPQueue(256);
        
        assertTrue(queue.size() == 0);
        queue.push(leafyBoi);
        
        assertTrue(queue.size() == 1);
    }
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testCapacityLimit() {
        char c = (char) 97;
        HuffmanNode leafyBoi = new HuffmanNode(c, 5);
        
        MyPQueue queue = new MyPQueue(0);
        
        assertTrue(queue.size() == 0);
        queue.push(leafyBoi);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testEmptyPolling() {
        MyPQueue queue = new MyPQueue(0);
        queue.poll();
    }
    
    @Test
    public void testPQueuePolling() {
        HuffmanNode C = new HuffmanNode('c', 5);
        HuffmanNode A = new HuffmanNode('a', 6);
        HuffmanNode B = new HuffmanNode('b', 1);
        HuffmanNode D = new HuffmanNode('d', 7);
        HuffmanNode T = new HuffmanNode('t', 2);
        HuffmanNode G = new HuffmanNode('g', 4);
        
        MyPQueue queue = new MyPQueue(256);
        
        queue.push(C);
        HuffmanNode cPolled = queue.poll();
        
        assertTrue(cPolled.weight == 5);
        
        queue.push(C);
        queue.push(A);
        queue.push(B);
        queue.push(D);
        queue.push(T);
        queue.push(G);
        
        HuffmanNode bPolled = queue.poll();
        
        assertTrue(bPolled.weight == 1);
        assertTrue(bPolled.value == 'b');
        assertTrue(queue.size() == 5);
    }
}
