package io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author tiera
 */
public class BitReader {
    private byte[] rawBytes;

    public BitReader(byte[] rawBytes) {
        this.rawBytes = rawBytes;
    }
    
    /**
     * Produces a byte string from the provided byte array
     * @return a string representing the rawBytes private variable the class has
     */
    public String read() {
        StringBuilder strBuilder = new StringBuilder();
        
        for (byte b : rawBytes) {
            for (int i = 7; i >= 0; i--) {
                strBuilder.append(getBitAtPosition(b, i));
            }
        }
        return strBuilder.toString();
    }
    
    /**
     * Gets a specific bit at a particular position from a byte
     * @param b the byte where the bit is to be fetched
     * @param position the position, from the right, at which the wanted bit lies in
     * @return the bit at the provided position
     */
    public int getBitAtPosition(byte b, int position) {
        int result = (b >> position) & 1;
        if (result == 0) {
            return 0;
        }
        return 1;
    }
    
}
