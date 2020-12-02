
public class Btree<K extends Comparable<K>, V> implements IDictionary<K,V>{
    private BtreeNode root;             // radice del Btree
    private int t;                       // grado del Btree
    
private class BtreeNode {
        private int size;          // Numero di Nodi
        private K key;           // Chiavi
        private V value;         // Dato della chiave
        private BtreeNode left, right, children;  // Alberi a destra e sinistra
        
        public BtreeNode(int size, K key, V value) {
            this.size = size;
            this.key = key;
            this.value = value;
            }
    }

    public Btree() {
    t=2;
    }

    public boolean isEmpty(BtreeNode x) {
        return size(x) == 0;    //Restituisce vero se non contiente alcun valore
    }


    
    private int size(BtreeNode x) { 
        if (x == null) return 0;  
        else return x.size;   //Restituisce la grandezza del nodo
    }

    private BtreeNode min(BtreeNode x) {  //Restituisce la chiave minore
        if (x.left == null) 
        return x; 
        else                
        return min(x.left);
    }

    @Override
    public V search(K key) {
        if(key == null)
        throw new IllegalArgumentException("calls search() with a null key");
         return search(root, key);

    }

    private V search(BtreeNode x, K key){    
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
        return x.value;
        else if (cmp < 0) 
        return search(x.left,  key);
        else if (cmp > 0) 
        return search(x.right, key);
        else if (size(x) >= ((t*2)-1))
        return search(x.children,  key);
        return x.value;
    }

    public void insert(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = insert(root, key, val);
    }

    private BtreeNode insert(BtreeNode x, K key, V val) {
        if (x == null) 
        return new BtreeNode(1, key, val);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) 
        x.left  = insert(x.left,  key, val);
        else if (cmp > 0) 
        x.right = insert(x.right, key, val);
        else if (size(x) >= ((t*2)-1))
        x.children = insert(x.children,  key, val);
        else            
        x.value   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private BtreeNode deleteMin(BtreeNode x) {  //Rimuove la chiave minore presente nel nodo
        if (x.left == null) 
        return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

   
    @Override
    public V delete(K key) {
        if (key == null) 
        throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        return root.value;
    }


    private BtreeNode delete(BtreeNode x, K key) {
        if (x == null) 
        return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) 
        x.left  = delete(x.left,  key);
        else if (cmp > 0) 
        x.right = delete(x.right, key);
        else if (size(x) >= ((t*2)-1))
        x.children = delete(x.children,  key);
        else {
            if (x.right == null) 
            return x.left;
            if (x.left  == null)
            return x.right;
            BtreeNode t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    

}


