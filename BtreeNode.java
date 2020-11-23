package movida.commons;

public class BtreeNode {
    Movie a[]; // An array of movies
        int t; // Minimum degree (defines the range for number of keys)
        BtreeNode c[]; // An array of child pointers
        int n; // Current number of keys

        // Constructor
        BtreeNode(int t) {
            this.t = t;
            this.a = new Movie[2 * t - 1];
            this.c = new movida.commons.BtreeNode[2 * t];
            this.n = 0;
        }

        public boolean isLeaf(){
            return c==null;
        }


        // A function to search a key in the subtree rooted with this node.
        public movida.commons.BtreeNode search(String k) { // returns NULL if t is not present.

            // Find the first key greater than or equal to k
            int i = 0;
            while (i < n && k.compareTo(a[i].getTitle()) > 0)
                i++;

            // If the found key is equal to k, return this node
            if ((a[i].getTitle()).compareTo(k) == 0)
                return this;

            // If the key is not found here and this is a leaf node
            if (isLeaf())
                return null;

            // Go to the appropriate child
            return c[i].search(k);

        }

        public boolean isFull(){
            return (n==2*t-1);
        }

        public void insertAsSorted(Movie key) {

            int j;
            for(j=n-1; j >= 0 && (a[j].getTitle()).compareTo(key.getTitle()) > 0; j--) {
                a[j+1] = a[j];
            }
            a[j+1] = key;
            n++;
        }

        public void splitChild(int childIndex, movida.commons.BtreeNode childNode){

            //Split childNode into two
            movida.commons.BtreeNode rightChild = new movida.commons.BtreeNode(t);
            int middleIndex = childNode.n/2;
            int j=0; int i;
            for(i=middleIndex+1; i<childNode.n; i++, j++){
                rightChild.a[j] = childNode.a[i];
                childNode.a[i]=null;
            }

            if(!childNode.isLeaf()) {
                rightChild.c = new movida.commons.BtreeNode[2 * t];

                j=0;
                for(i=middleIndex+1; i<childNode.n; i++, j++){
                    rightChild.c[j] = childNode.c[i];
                    childNode.c[i]=null;
                }
                rightChild.c[j] = childNode.c[i];
                childNode.c[i]=null;
            }
            childNode.n = middleIndex;
            rightChild.n = j;

            //Move up the middleKey and update its left and right child.
            if(this.c ==null){
                this.c = new movida.commons.BtreeNode[2 * t];
            }
            c[n+1] = c[n];
            for(i=n; i>childIndex; i--){
                a[i] = a[i-1];
                c[i] = c[i-1];
            }

            a[childIndex] = childNode.a[middleIndex];
            c[childIndex] = childNode;
            c[childIndex+1] = rightChild;
            this.n++;

            childNode.a[middleIndex] = null;
        }

    }

