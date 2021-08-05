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
public class HuffmanCode {
 
  private int[] frequency = new int[128];
  private PriorityQueue<HuffTreeItem> item; 
  private HuffTreeItem treeItem;
  private HuffmanTree huffmanTree;
  private int counter = 0; 
  
  
  public HuffmanCode(String fileIn, String fileOut) throws IOException {
    // Accepts the input file and then outputs the transformed data and puts in the
    // other argument of the method
    findCharacterFrequency(fileIn);    
    buildSingleLeafNodes();    
    buildHuffmanTree();
    buildEncodings();
       
    String huffTree = huffmanTree.toString(); 
    HuffmanOutputStream output = new HuffmanOutputStream(fileOut, huffTree, counter);
    
    writeOut(fileIn, huffmanTree.pathsToLeaves(), output);
  }

  
  private class HuffTreeItem implements Comparable<HuffTreeItem> {
    // Creates a huffman tree and sets up the basics for it
    private int priority;       
    private HuffmanTree treeH;
     
    private HuffTreeItem(int p, HuffmanTree n) { 
      // Constructor method
      priority = p;
      treeH = n;
    }
   
    public int compareTo(HuffTreeItem x) {
      // Priority queue that is taken from one of the homework files
      if (this.priority == x.priority) {
        return 0;
      }
      else if (this.priority > x.priority) {
        return 1;
      }
      else {
        return -1;
      }
    }
  }
  
  private void findCharacterFrequency(String infile) throws IOException {
    // takes in the inputed file and find the amounts of times
    // that a character appears in the file
 
    BufferedReader input = new BufferedReader(new FileReader(infile));
 
    int chr = input.read();
  
    while(chr != -1) {
      frequency[chr]++;
      counter++;
      chr = input.read();
    }
    
    input.close();
  }
  
  private void buildSingleLeafNodes() {
    // Builds the single leaf nodes needed to make the huffman tree
    item = new PriorityQueue<>();
    
    for(int i = 0; i < frequency.length; i++) {
      if(frequency[i] != 0) {
        
        HuffmanTree tree = new HuffmanTree((char)i);
        
        HuffTreeItem huffTree = new HuffTreeItem(frequency[i], tree);
        
        item.add(huffTree);
      }
    }
  }
 
  private void buildHuffmanTree() {
    // Actually builds a Huffman Tree 
    while(item.size() != 1) {
      HuffTreeItem temp1 = item.poll();
      HuffTreeItem temp2 = item.poll();
      
      int newPrio = temp1.priority + temp2.priority;
      
      HuffmanTree tree = new HuffmanTree(temp1.treeH, (char)128, temp2.treeH);
      HuffTreeItem newTree = new HuffTreeItem(newPrio, tree);
     
      item.add(newTree);
    }
    
    treeItem = item.peek();
    huffmanTree = treeItem.treeH;
  }
  
  
  private void buildEncodings() {
    // This method build the encodings for the output file
    huffmanTree.pathsToLeaves();
  }
  
 
  private void writeOut(String infile, String[] array, HuffmanOutputStream h) throws IOException {
    // This method writes out the input into an array and calls the output stream class
    BufferedReader input = new BufferedReader(new FileReader(infile));
    int charRead = input.read();
    
    while(charRead != -1) { 
      for(int i  = 0; i < array[charRead].length(); i++) {
        h.writeBit(array[charRead].charAt(i));
      }
      charRead = input.read();
    } 
    h.close();
    input.close();
  }

  
  public static void main(String args[]) throws IOException {
    new HuffmanCode(args[0], args[1]);
  }
}
