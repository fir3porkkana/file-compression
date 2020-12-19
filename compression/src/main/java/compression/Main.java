/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compression;

import io.BitReader;
import io.BitWriter;
import java.util.Scanner;

import io.FileFetcher;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;



/**
 *
 * @author tiera
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        byte b = 15;
        int result = (b >> 4) & 1;
        System.out.println(result);

        System.out.println("\nHello! Observe the key inputs below, what would you like to do?\n\n(1) compress a .txt file, \n(2) decompress a previously compressed file \n(3) run a demo with a typed input\n\nType in your choice here");
        
        Scanner scanner = new Scanner(System.in);
        int choice = Integer.parseInt(scanner.nextLine());
        
        String workingDirPath = System.getProperty("user.dir") +  "/";
        
        if (choice == 1) {
            System.out.println("type in the name or path of the file you would like to compress:");
            compress(scanner.nextLine());
        } else if (choice == 2) {
            System.out.println("type in the name or path of the file you would like to decompress");
            decompress(scanner.nextLine());
        } else if (choice == 3) {
            Huffman huffman = new Huffman();
            String test = scanner.nextLine();
            String rez = huffman.compress(test);
            System.out.println("encoded string: " + rez);
            System.out.println("decoded string: \n" + huffman.decompress(rez));
        } else {
            System.out.println("Bad input command, please comply with the provided instructions.");
        }
    }
    
    public static void compress(String path) {
        Huffman huffman = new Huffman();
        String workingDirPath = System.getProperty("user.dir") +  "/";
            
        FileFetcher fileFetcher = new FileFetcher();
            
        String input = fileFetcher.getFileAsString(workingDirPath + path);
            
        String output = huffman.compress(input);
        
        File outputFile = new File("output.cmp");
        
        try {
            outputFile.createNewFile();
            BitWriter bitwriter = new BitWriter(outputFile);
                
            bitwriter.writeBitsInString(output);
                
            bitwriter.finishUpWriting();
                
            bitwriter.closeStream();
            
            System.out.println("Compression succesful, 'output.cmp' created.");
        } catch (IOException e) {
            System.out.println("File 'output.cmp' likely already exists, rename or remove the file from this directory and try again.\n" + e);
        }
    }

    private static void decompress(String path) {
        Huffman huffman = new Huffman();
        String workingDirPath = System.getProperty("user.dir") +  "/";
        
        FileFetcher fileFetcher = new FileFetcher();
        byte[] inputInBitForm = fileFetcher.getFileAsByteArray(workingDirPath + path);
        
        File outputFile = new File("decompressed.txt");

        
        try {
            outputFile.createNewFile();
            BitReader bitreader = new BitReader(inputInBitForm);
            
            String encodedString = bitreader.read();
            
            String decodedString = huffman.decompress(encodedString);
            
            Path outputFilePath = Paths.get(workingDirPath + "decompressed.txt");
            
            BufferedWriter writer = Files.newBufferedWriter(outputFilePath, StandardOpenOption.WRITE);
            
            writer.write(decodedString);
            writer.flush();
            
            System.out.println("Decompression successful, 'decompressed.txt' created.");
        } catch (IOException e) {
            System.out.println("Error writing to file:\n" + e);
        }
    }
    
}
