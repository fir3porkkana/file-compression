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
        Huffman huffmani = new Huffman();
        System.out.println("Type in your demo input: ");
        Scanner scanner = new Scanner(System.in);
        
        String test = scanner.nextLine();
        
        String rez = huffmani.compress(test);
        System.out.println("encoded string: " + rez);
        System.out.println("decoded string: \n" + huffmani.decompress(rez));
         
    }
    
    
}
