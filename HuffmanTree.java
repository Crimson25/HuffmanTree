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
public class HuffmanTree {
  
  public class Node {
    public Node left;
    public char data;
    public Node right;
    public Node parent;
   
    public Node(Node L, char d, Node R, Node P) {
      left = L;
      data = d;
      right = R;
      parent = P;
    }
  }
  
  private Node root; 
  private Node current; 
  
  public HuffmanTree() {
    root = null;
    current = null; 
  }
  
  public HuffmanTree(char d) {
  //makes a single node tree

    root = new Node(null, d, null, null);
    current = root; 
  }

  public HuffmanTree(String t, char nonLeaf) {
  //Assumes t represents a post order representation of the tree as discussed
  // in class
  //nonLeaf is the char value of the data in the non-leaf nodes
  //in the following I will use (char) 128 for the non-leaf value
    
    Stack<HuffmanTree> hft = new Stack<>();
    HuffmanTree huff;
    
    for(int i = 0; i < t.length(); i++) {
      if(t.charAt(i) >= nonLeaf) {
        HuffmanTree right = hft.pop();
        HuffmanTree left = hft.pop();
                
        huff = new HuffmanTree(left, nonLeaf, right);
        hft.add(huff);
      }
      else {
        huff = new HuffmanTree(t.charAt(i));
        hft.add(huff);
      }
    }
    root = hft.pop().root;
    current = root;   
  }
  
  public HuffmanTree(HuffmanTree b1, char d, HuffmanTree b2) {
  //makes a new tree where b1 is the left subtree and b2 is the right subtree
  //d is the data in the root
    root = new Node(b1.root, d, b2.root, null);
    current = root;
  }
  
  //use the move methods to traverse the tree
  //the move methods change the value of current
  //use these in the decoding process
  
  public void moveToRoot() {
  //change current to reference the root of the tree
    current = root;
  }
  
  public void moveToLeft() {
  //PRE: the current node is not a leaf
  //change current to reference the left child of the current node
    if (current.left != null) {
    current = current.left;
    }
  }
  
  
  public void moveToRight() {
  //PRE: the current node is not a leaf
  //change current to reference the right child of the current node
    if (current.right != null) {
    current = current.right;
    }
  }
  
  public void moveToParent() {
  //PRE: the current node is not the root
  //change current to reference the parent of the current node
    if(current != root) {
      current = current.parent;
    }
  }
  
  public boolean atRoot() {
  //returns true if the current node is the root otherwise returns false
    if(current == root){
      return true;
    }
    return false;
  }
  
  public boolean atLeaf() {
  //returns true if current references a leaf other wise returns false
    if(current.left == null && current.right == null) {
      return true;
    }
    return false;
  }
  
  public char current() {
  //returns the data value in the node referenced by current
    return current.data;
  }
  
  private String[] arrayOfPaths = new String[128];
  
  public String[] pathsToLeaves() {
    /*
    returns an array of strings with all paths from the root to the leaves
    each value in the array contains a leaf value followed by a sequence of
    0s and 1s. The 0s and 1s represent the path from the root to the node
    containing the leaf value.
    */
    int length = 128;
 
    for (int i = 0; i < length; i++)
    {
      arrayOfPaths[i] = findPaths(root, i, "");
    }
    return arrayOfPaths;
  }

  private String findPaths(Node r, int code, String path){
    if (r == null){
      return "";
    }
    
    if(r.left == null && r.right == null) {
      if((int)r.data == code) {
        return path;
      } 
      else {
        return "";
      }
    }
    return findPaths(r.left, code, path + "0") + findPaths(r.right, code, path + "1");
  }
  
  private String completeTree;
  
  public String toString() { 
  //returns a string representation of the tree using the postorder format
  // discussed in the following slides
    completeTree = "";
    printPostOrderH(root);
    return completeTree;
  }
  
  public String printPostOrderH(Node r) {
    if(r == null) {
      return "";
    }
    printPostOrderH(r.left);
    printPostOrderH(r.right);
    return completeTree = completeTree + r.data;
  }
}
