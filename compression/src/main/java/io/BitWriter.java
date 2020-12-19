/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author tiera
 */
public class BitWriter {
    private BufferedOutputStream stream;
    private int aByte = 0;
    private int bitCount = 0;
    private int writtenBits = 0;
    

    public BitWriter(File designatedOutputFile) {
        try {
            this.stream = new BufferedOutputStream(new FileOutputStream(designatedOutputFile));
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file:\n" + e);
        }
    }
    
    /**
     * Writes the bit-form string into a file byte-by-byte
     * @param string the actual encoded string produced by the huffman class
     */
    public void writeBitsInString(String string) {
        
        for (int i = 0; i < string.length(); i++) {
            char bit = string.charAt(i);
                
            if (bit == '0') {
                writeOneBit(0);
            } else if (bit == '1') {
                writeOneBit(1);
            }
        }
        
    }
    
    /**
     * Adds the provided bit into a byte, and then writes out the accumulated 
     * byte if it is of the length 8
     * @param bit the bit to be written
     */
    public void writeOneBit(int bit) {
        try {
            aByte = (aByte << 1) | bit;
            bitCount++;
            writtenBits++;

            if (bitCount == 8) {
                stream.write(aByte);
                aByte = 0;
                bitCount = 0;
            }
        } catch (IOException e) {
            System.out.println("Error writing bytes:\n" + e);
        }
    }
    
    /**
     * Ensures that the last written byte is 8-bits long
     */
    public void finishUpWriting() {
        while (writtenBits % 8 != 0 && writtenBits > 0) {
            writeOneBit(0);
        }
    }
    
    /**
     * Closes the buffered output stream
     */
    public void closeStream() {
        try {
            stream.close();
        } catch (Exception e) {
            System.out.println("Error closing stream:\n" + e);
        }
    }    
}
