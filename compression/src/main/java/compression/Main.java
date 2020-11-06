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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String test = "ääääääääääääääääääääääääääääääääääääääääääääblblblblblblbltesti";
 
        
        int[] charFreqs = new int[256];
        
        for (char c : test.toCharArray()) {
            charFreqs[c]++;
        }
        for (int i = 0; i < charFreqs.length; i++) {
            System.out.println("\n" + charFreqs[i]);
        }
    }
    
}
