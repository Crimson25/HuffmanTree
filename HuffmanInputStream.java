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
public class HuffmanInputStream {
  
  private String tree;
  private int totalChars;
  private DataInputStream d;
  private int count;
  private int bits[];
  
  
  public HuffmanInputStream(String filename) {
    try {
      d = new DataInputStream(new FileInputStream(filename));
      tree = d.readUTF();
      totalChars = d.readInt();
      count = 8;
      bits = new int[8];
    }
    catch (IOException e) {
    }
  }

  public int readBit() {
    
    int b;
    int bit = 0;
    try {
      if(count == 8) {
        b = d.readUnsignedByte();
        for(int i = 7; i >= 0; i--) {
          bits[i] = b % 2;
          b = b / 2;
        }
        count = 0;
      }
      bit = bits[count];
      count++;
    } 
    catch (IOException e) {
    }
    return bit;
  }
  
  public String getTree() {
    return tree;
  }
  
  public int getTotalChars() {
    return totalChars;
  }
  
  public void close() throws IOException {
    d.close();
  }
}
