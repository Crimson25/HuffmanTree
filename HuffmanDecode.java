/*
 * Colin Fitzpatrick
 * 
 * Professor Gendreau
 * 
 * Homework 4c
 * 
 * Huffman Tree
 */
import java.io.*;
import java.util.*;

public class HuffmanDecode {
  private HuffmanTree ht;
  
  public HuffmanDecode(String in, String out) throws IOException {
    // This is the method that calls all the other methods in the class
    // To start the decoding process
    HuffmanInputStream input = new HuffmanInputStream(in);
    ht = new HuffmanTree(input.getTree(), (char)128);
    HuffmanDecoder(input, out);   
  }
  
  private void HuffmanDecoder(HuffmanInputStream input, String out) throws IOException { 
    // This method takes in the encoded text and then outputs the
    // decoded message in a new file
    BufferedWriter br = new BufferedWriter(new FileWriter(out));
    int total = input.getTotalChars();
    
    
    while (total != 0) {   
      if(ht.atLeaf()) {
        total--; 
        br.write(ht.current());
        ht.moveToRoot();
      }
      if(input.readBit() == 0) {
        ht.moveToLeft();
      }
      else {
        ht.moveToRight();
      }
    }
    input.close();
    br.close();
  }
  
  public static void main(String args[]) throws IOException {
    new HuffmanDecode(args[0], args[1]);
  }
}
