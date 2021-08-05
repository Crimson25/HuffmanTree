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
public class HuffmanOutputStream {
 
  private DataOutputStream d;
  private int b;
  private int count;
  
  public HuffmanOutputStream(String filename, String tree, int totalChars) {
    try {
      d = new DataOutputStream(new FileOutputStream(filename));
      d.writeUTF(tree);
      d.writeInt(totalChars);
      b = 0;
      count = 0;
    } catch (IOException e) {
      // Not implemented.
    }
  }
  
  public void writeBit(char bit) {
    try {
      b = 2 * b + (bit-'0');
      count++;
      if(count == 8) {
        System.out.print(bit);
        d.writeByte(b);
        b = 0;
        count = 0;
      }
    } catch(IOException e) {
      // Not implemented.
    }
  }
  
  public void close() {
    try {
      if (count != 0) {
        b = b << (8-count);
        d.writeByte(b);
      }
      d.close();
    } catch (IOException e) {
      // Not implemented.
    }
  }
}
