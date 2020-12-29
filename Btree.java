import java.util.ArrayList;

public class Btree<K extends Comparable<K>, V> implements IDictionary<K,V>{
    private BtreeNode root;             // radice del Btree
    private int t;                       // grado del Btree
    private ArrayList<V> values = new ArrayList<>();
    
private class BtreeNode {
        private ArrayList<K> keys = new ArrayList<>();       // Chiavi
        private ArrayList<V> value = new ArrayList<>();      // Dato della chiave
        private BtreeNode left, right, middle;  // Alberi a destra e sinistra
        
        public BtreeNode(K key, V value) {
            this.keys.add(key);
            this.value.add(value); 
            }
    }

    public Btree() {
    root=null;
    t=2;
    }

    @Override
    public V search(K key) {
        if(key == null)
        throw new IllegalArgumentException("calls search() with a null key");
         return search(root, key);

    }

    private V search(BtreeNode x, K key){ 
        V val=null;
        boolean found=false;
        if (x == null){
            return null;
        }
        for (int i=0; i<x.keys.size(); i++ ){
            int cmp = key.compareTo(x.keys.get(i));
            if (cmp == 0){
            val= x.value.get(i);
            i=x.keys.size();
            found=true;} }
                if (!found)
                    if  (key.compareTo(x.keys.get(0))<0 )
                        val= search(x.left,  key);
                            else if (key.compareTo(x.keys.get(x.keys.size()-1))>0 )
                            val= search(x.right, key);
                                else
                                val= search(x.middle, key);
                    
        return val;
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
        if(x == null)
        return new BtreeNode(key, val);
            else if (x.keys.size()>=(((t*2)-1)))
                if  (key.compareTo(x.keys.get(0))<0 )
                    x.left  = insert(x.left,  key, val);
                    else if (key.compareTo(x.keys.get(x.keys.size()-1))>0 )
                        x.right = insert(x.right, key, val);
                        else
                        x.middle = insert(x.middle, key, val);
        else                
        for (int i=0; i<x.keys.size(); i++ ){
            int cmp = key.compareTo(x.keys.get(i));
            if      (cmp < 0 ) {
               x.keys.add(i, key);
               x.value.add(i, val); 
               i=x.keys.size();
            }
            else if (i==x.keys.size()-1){
            x.keys.add(i+1, key);
               x.value.add(i+1, val); 
               i=x.keys.size();
            }
            }
    return x;
}

   
    @Override
    public void delete(K key) {
        if (key == null) 
        throw new IllegalArgumentException("calls delete() with a null key");
        delete(root, key);
        
    }


    private void delete(BtreeNode x, K key) {
        boolean found =false;
        for (int i=0; i<x.keys.size(); i++ ){
            int cmp = key.compareTo(x.keys.get(i));
            if (cmp == 0){
            x.value.remove(i);
            x.keys.remove(i);
            i=x.keys.size();
            found=true;} }
                if (!found)
                    if  (key.compareTo(x.keys.get(0))<0 )
                        delete(x.left,  key);
                            else if (key.compareTo(x.keys.get(x.keys.size()-1))>0 )
                            delete(x.right, key);
                                else
                                delete(x.middle, key);
                    }
    
    
    public void clear(){
        root = null;
    }
    private void traversal() {
        traversalRec(root);
    }

    private void traversalRec(BtreeNode root) {
        if (root != null) 
            for(int i=0; i<root.value.size(); i++ )
            values.add(root.value.get(i));
        if (root.left !=null)    
            traversalRec(root.left);
        if (root.right !=null)
            traversalRec(root.right);
        if (root.middle !=null)
            traversalRec(root.middle);
    }

    public ArrayList<V> keyValues(){
        values.removeAll(values);
        traversal();
        return values;
    }

    

}


