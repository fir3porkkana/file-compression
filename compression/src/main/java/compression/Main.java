/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

import java.util.Scanner;



/**
 *
 * @author tiera
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type in your demo input: ");
        String test = scanner.nextLine();

        Huffman huffmani = new Huffman();
        
        HuffmanEncodedResult rez = huffmani.compress(test);
        System.out.println("encoded string: " + rez.encodedContent);
        System.out.println("de-encoded string: " + huffmani.decompress(rez));
         
    }
    
    
}
