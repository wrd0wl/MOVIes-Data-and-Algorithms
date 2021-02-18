package src.implementation;

import java.util.ArrayList;

public class BST<K extends Comparable<K>, V> implements IDictionary<K, V> {
    private Node root;
    private ArrayList<V> values = new ArrayList<>();

    private class Node{
        public K key;
        public V value;
        public Node left, right;

        public Node (K key, V value){
            this.key = key;
            this.value = value;
            left = right = null;
        }
    }
    
    public BST(){
        root = null;
    }

    public void insert (K key, V value){
        root = insertRec(key, value, root);
    }

    private Node insertRec(K key, V value, Node root){
        //The tree is empty
        if (root == null){
            root =  new Node (key, value);
            return root;
        }
        
        if(key.compareTo(root.key) < 0){
            root.left = insertRec(key, value, root.left);
        }
        else if(key.compareTo(root.key) > 0){
            root.right = insertRec(key, value, root.right);
        }
        else{
            root.value = value;
        }
        return root;
    }

    public V search (K key){
        return searchRec(key, root);        
    }

    private V searchRec (K key, Node root){
        if (root == null){
            return null;
        }
        if(key.compareTo(root.key) < 0){
            return searchRec(key, root.left);
        }
        else if(key.compareTo(root.key) > 0){
            return searchRec(key, root.right);
        }
        else{
            return root.value;
        }
    }

    public void delete(K key){
        root = deleteRec(key, root);
    }

    private Node deleteRec(K key, Node root){
        if (root == null){
            return root;
        }

        if(key.compareTo(root.key) < 0){
            root.left = deleteRec(key, root.left);
        }
        else if(key.compareTo(root.key) > 0){
            root.right = deleteRec(key, root.right);
        }
        else{
            //delete node with one child ot without children
            if(root.left == null){
                return root.right;
            }
            else if(root.right == null){
                return root.left;
            }

            //delete node with two children
            Node successor = minSubtree(root.right);
            root.key = successor.key;
            root.value = successor.value;
            root.right = deleteRec(root.key, root.right);
        }
        return root;

    }

    private Node minSubtree(Node root){
        while(root.left != null){
            root = root.left;
        }
        return root;
    }

    public void clear(){
        root = null;
    }

    private void traversal() {
        traversalRec(root);
    }

    private void traversalRec(Node root) {
        if (root != null) {
            values.add(root.value);
            traversalRec(root.left);
            traversalRec(root.right);
        }
    }

    public ArrayList<V> keyValues(){
        values.removeAll(values);
        traversal();
        return values;
    }
}
