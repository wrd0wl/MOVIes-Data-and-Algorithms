public class ABR <K extends Comparable <K>, V> implements IDictionary <K, V> {
    private Node root;

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
    
    public ABR(){
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
}
