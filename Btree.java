package movida.commons;

public class Btree {

    public BtreeNode root; // Pointer to root node
        public int t; // Minimum degree

        // Constructor (Initializes tree as empty)
        Btree(int t) {
            this.root = null;
            this.t = t;
        }


        // function to search a key in this tree
        public BtreeNode search (String k) {
            if (this.root == null)
                return null;
            else
                return this.root.search(k);
        }

        public void insert(Movie key) {
            boolean leaf;

            if (root == null) {
                root = new BtreeNode(t);
                root.insertAsSorted(key);
                return;
            }
            BtreeNode parent = null;
            BtreeNode node = root;
            int childIndex = 0;

            while (true) {

                //Proactive Split
                if (node.isFull()) {
                    if (parent == null) {
                        parent = new BtreeNode(t);
                        root = parent;
                    }
                    parent.splitChild(childIndex, node);
                    if((key.getTitle()).compareTo((parent.a[childIndex].getTitle()))==0){
                        //Duplicate Data rejected
                        return;
                    }

                    if ((key.getTitle()).compareTo((parent.a[childIndex].getTitle())) < 0) {
                        node = parent.c[childIndex];
                    } else {
                        node = parent.c[childIndex+1];
                    }
                }
                //Binary Search
                int left = 0, right = node.n - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;

                    if ((key.getTitle()).compareTo((node.a[mid].getTitle())) == 0) {
                        //Duplicate Data rejected
                        //For upsert, we can update the value of the key.
                        return;
                    }

                    if ((key.getTitle()).compareTo((node.a[mid].getTitle())) < 0) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }

                // If not found, traverse down the children
                if (node.isLeaf()) {
                    break;
                }
                parent = node;
                childIndex = right+1;
                node = node.c[childIndex];
            }

            //Insert into the leaf node.
            node.insertAsSorted(key);
        }

    }

