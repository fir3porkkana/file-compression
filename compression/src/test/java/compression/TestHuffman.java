package compression;


import static org.junit.Assert.*;

import org.junit.Test;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tiera
 */
public class TestHuffman {
    
    @Test
    public void testSingularCharacterInput() {
        Huffman huffman = new Huffman();
        
        String encodedAAA = huffman.compress("aaa");
        
        String aaa = huffman.decompress(encodedAAA);
        
        assertTrue(aaa.equals("aaa"));
    }
    
    @Test
    public void testShortMultiCharacterInput() {
        Huffman huffman = new Huffman();
        
        String encodedAAA = huffman.compress("i am a test. nothing more, nothing less.");
        
        String testString = huffman.decompress(encodedAAA);
        
        assertTrue(testString.equals("i am a test. nothing more, nothing less."));
    }
    
    @Test
    public void testIntermediateMultiCharacterInput() {
        Huffman huffman = new Huffman();
        
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce eu fermentum massa. Morbi felis tortor, tristique eu magna id, sollicitudin eleifend nisl. Integer semper convallis nisi vel euismod. Fusce at erat ullamcorper turpis tempor ullamcorper quis in leo. Fusce suscipit a lacus sed fringilla. Aenean at faucibus nunc. Aenean tempus leo id eros efficitur, vel dictum dui pharetra. Nulla egestas est quam, non aliquam urna porttitor vitae. Pellentesque feugiat venenatis orci ut vehicula. In at nisi et massa consequat sollicitudin. Quisque sit amet est imperdiet, porta sem in, hendrerit urna.\n" +
                                            "\n" +
                                            "Aliquam sed mollis mauris. Sed eget tellus eros. Aenean dapibus nisi sed est efficitur efficitur. Ut ac vulputate justo, a laoreet eros. Maecenas dictum varius rhoncus. Pellentesque rhoncus metus sit amet urna porta pharetra. Donec euismod malesuada ultricies. Integer aliquam, nisl quis rhoncus blandit, diam turpis cursus lectus, nec egestas turpis dolor id nulla. In a elementum nunc.\n" +
                                            "\n" +
                                            "Nulla diam magna, convallis in scelerisque at, posuere quis quam. Ut aliquam mollis ultricies. Mauris eget lorem vel est euismod interdum. Phasellus pulvinar vestibulum lacus a hendrerit. Fusce pulvinar luctus nisl at posuere. Fusce vel nulla sed elit tincidunt euismod. Mauris quis vehicula tellus, et commodo leo. Phasellus blandit, libero sit amet fringilla viverra, metus lectus faucibus turpis, vel egestas nibh nulla sed dui. Aliquam vitae ex interdum, sollicitudin dolor eget, aliquam erat. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Maecenas dictum nunc neque, vitae eleifend mauris dictum vel. Aliquam fringilla turpis id elementum auctor. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\n" +
                                            "\n" +
                                            "Suspendisse potenti. Mauris ex arcu, dapibus condimentum suscipit id, semper mollis justo. Suspendisse sit amet massa dictum, porttitor mauris et, dignissim mauris. Integer cursus egestas urna. Maecenas sed malesuada elit. Vestibulum ut posuere nisl, quis elementum sapien. Nullam viverra orci at orci aliquet, non pretium orci ornare. Quisque dapibus leo dolor, ut dictum sapien pharetra eget. Vestibulum at semper nibh. Fusce semper maximus dictum.\n" +
                                            "\n" +
                                            "Curabitur imperdiet eu est at mattis. Proin accumsan urna mauris, a tempor nibh vehicula non. Sed a ornare velit. Nullam odio orci, porta et turpis in, aliquam finibus massa. Mauris eget tortor volutpat, egestas libero sed, bibendum ex. Praesent aliquet dictum sapien ut suscipit. Quisque vitae mollis velit. Etiam ex est, venenatis eget euismod eu, feugiat in justo. Ut varius nisi mi, et cursus ex vulputate ac. Aenean eu malesuada nisi. Nunc quis rutrum nibh. Proin at vestibulum dui, et fermentum nulla. Curabitur vitae nisl id ante cursus fringilla vitae eu nisi. In vel ex vel ligula ornare congue. Nulla et risus dictum, aliquet purus vitae, sollicitudin enim.";
        
        String ipsumCompressed = huffman.compress(loremIpsum);
        String testString = huffman.decompress(ipsumCompressed);
        
        assertTrue(testString.equals(loremIpsum));
    }
    
    
    
}
