/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  @version 14/11/23 07:33:39
 *
 *  @author Dylan Gallagher
 *
 *************************************************************************/

package csu22011_a3;

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
    private Node root;             // root of BST

    /**
     * Private node class.
     */
    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int N;             // number of nodes in subtree

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // is the symbol table empty?
    public boolean isEmpty() { return size() == 0; }

    // return number of key-value pairs in BST
    public int size() { return size(root); }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    /**
     *  Search BST for given key.
     *  Does there exist a key-value pair with given key?
     *
     *  @param key the search key
     *  @return true if key is found and false otherwise
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     *  Search BST for given key.
     *  What is the value associated with given key?
     *
     *  @param key the search key
     *  @return value associated with the given key if found, or null if no such key exists.
     */
    public Value get(Key key) { return get(root, key); }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     *  Insert key-value pair into BST.
     *  If key already exists, update with new value.
     *
     *  @param key the key to insert
     *  @param val the value associated with key
     */
    public void put(Key key, Value val) {
        if (val == null) { delete(key); return; }
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.N = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Tree height.
     *
     * Asymptotic worst-case running time using Theta notation:
     *
     * Worst-case runtime: \Theta{N}
     *
     * Since we are considering the worst-case runtime, we are considering a perfect binary search tree.
     * We need to visit all the nodes in this tree to ensure the depth. The worst-case runtime is \Theta{N}, where the
     * BST has N nodes.
     *
     * @return the number of links from the root to the deepest leaf.
     *
     * Example 1: for an empty tree this should return -1.
     * Example 2: for a tree with only one node it should return 0.
     * Example 3: for the following tree it should return 2.
     *   B
     *  / \
     * A   C
     *      \
     *       D
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        if (x.left == null && x.right == null) return 0;
        return Math.max(height(x.left), height(x.right)) + 1;
    }

    /**
     * Median key.
     * If the tree has N keys k1 < k2 < k3 < ... < kN, then their median key
     * is the element at position (N+1)/2 (where "/" here is integer division)
     *
     * @return the median key, or null if the tree is empty.
     */
    public Key median() {
        if (isEmpty()) return null;
        //TODO fill in the correct implementation. The running time should be Theta(h), where h is the height of the tree.
        return median(root, (root.N - 1) / 2).key;
    }

    private Node median(Node x, int index) {
        if (x == null) return null;
        if (x.N == 1) return x;
        int N_left = size(x.left);
        if (index > N_left) return median(x.right, index - N_left - 1);
        if (index == N_left) return x;
        return median(x.left, index);
    }


    /**
     * Print all keys of the tree in a sequence, in-order.
     * That is, for each node, the keys in the left subtree should appear before the key in the node.
     * Also, for each node, the keys in the right subtree should appear before the key in the node.
     * For each subtree, its keys should appear within a parenthesis.
     *
     * Example 1: Empty tree -- output: "()"
     * Example 2: Tree containing only "A" -- output: "(()A())"
     * Example 3: Tree:
     *   B
     *  / \
     * A   C
     *      \
     *       D
     *
     * output: "((()A())B(()C(()D())))"
     *
     * output of example in the assignment: (((()A(()C()))E((()H(()M()))R()))S(()X()))
     *
     * @return a String with all keys in the tree, in order, parenthesized.
     */
    public String printKeysInOrder() {
        if (isEmpty()) return "()";
        // TODO fill in the correct implementation
        return null;
    }

    /**
     * Pretty Printing the tree. Each node is on one line -- see assignment for details.
     *
     * @return a multi-line string with the pretty ascii picture of the tree.
     */
    public String prettyPrintKeys() {
        //TODO fill in the correct implementation.
        return null;
    }

    /**
     * Deletes a key from a tree (if the key is in the tree).
     * Note that this method works symmetrically from the Hibbard deletion:
     * If the node to be deleted has two child nodes, then it needs to be
     * replaced with its predecessor (not its successor) node.
     *
     * @param key the key to delete
     */
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node root, Key key) {
        if (root == null) return null;
        int cmp = key.compareTo(root.key);
        if (cmp < 0) root.left = delete(root.left, key);
        else if (cmp > 0) root.right = delete(root.right, key);
        else { // delete this node
            if (root.right == null) return root.left;
            if (root.left == null) return root.right;
            Node t = root;
            root = max(t.left);
            root.left = deleteMax(t.right);
            root.right = t.right;
        }
        root.N = size(root.left) + size(root.right) + 1;
        return root;
    }

    private Node max(Node root) {
        Node tmp = root;
        while(tmp.right != null) tmp = tmp.right;
        return tmp;
    }


    private Node deleteMax(Node x) {
        // go right until we get a node with a null right link
        // if it has a null right link, return the link to its left node
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.right) + size(x.left) + 1;
        return x;
    }


    /**
     * This method takes an array of Key objects and determines whether the
     * given sequence can represent the pre-order traversal of a BST.
     *
     * @param keys an array of keys
     */
    public boolean isBSTPreOrder(Key[] keys){
        //TODO fill in the correct implementation.
    }


}
