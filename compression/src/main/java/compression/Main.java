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
//        String test = "ääääääääääääääääääääääääääääääääääääääääääääblblblblblblbltesti";
        String test = "järkevä ja mukava merkkijono testattavaksi";

        Huffman huffmani = new Huffman();
        
        huffmani.compress(test);
        
         
    }
    
    
}
