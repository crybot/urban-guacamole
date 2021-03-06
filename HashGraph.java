import java.util.HashMap;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.Iterator;

public class HashGraph<E> implements Graph<E>, Iterable<E>
{
    /* OVERVIEW:
     * The class HashGraph<E> represents the generic implementation of a Graph
     * data structure with elements of type E.
     * The typical instance of the collection is a tuple <V,E'> where V is the set
     * of vertices and E' is a set containing edges (i.e. connections 
     * between elements ov V). Every element of V shall have a label or info used to
     * identify it along with a collection of every outgoing connection, as described
     * in Node<E>, which implicitly defines E'.
     * Nodes are collected with an HashMap<E, Node<E>> where the set of node labels
     * (of type E) are mapped into nodes (of type Node<E>).
     *
     * AF:  { <k¹, f(k¹)>, <k², f(k²)>, ... , <kⁿ, f(kⁿ)> }
     *          where f(k) : nodeMap.keySet() -> nodeMap.values()
     *
     * IR:  - nodeMap ≠ null
     *      - k ≠ null ∀ k ∈ nodeMap.keySet()
     *      - nodeMap.get(k) ≠ null ∀ k ∈ nodeMap.keySet()
     *      - <k¹,k²> ∈ nodeMap.keySet() ⇒ k¹ ≠ k² 
     *      - k ∈ nodeMap.keyset() ⇒ ∃ v ∈ nodeMap.values() : nodeMap.get(k) == v
     *      - k ∈ nodeMap.keySet() ⇒ nodeMap.keySet() == (addNode(k) ↓ nodeMap.keySet()) ∧ 
     *                               nodeMap.values() == (addNode(k) ↓ nodeMap.values())
     *  where '↓' means apply left method before evaluating the right one.
     */

    private HashMap<E, Node<E>> nodeMap; // not synchronized hash table

    // used to verify pre/post condition via assertions
    private boolean repOk()
    {
        if (nodeMap == null) return false;

        for (E k : nodeMap.keySet()) 
            if (k == null) return false;

        for (E k : nodeMap.keySet()) 
            if (nodeMap.get(k) == null) return false;

        // statement: <k¹,k²> ∈ nodeMap.keySet() ⇒ k¹ ≠ k² 
        // is automatically verified because nodeMap.keySet() cannot contain
        // dupllicates as it implements Set<E>

        for (E k : nodeMap.keySet())
            if (!nodeMap.values().contains(nodeMap.get(k))) return false;

        for (E k : nodeMap.keySet())
        {
            // cannot test for set equality since we do not infornce deep cloning
            // (although we could), so we test for set sizes
            int keys = nodeMap.keySet().size();
            int values = nodeMap.values().size();
            addNode(k); // ↓
            if (nodeMap.keySet().size() != keys) return false;
            if (nodeMap.values().size() != values) return false;
        }

        return true;
    }

    //MODIFIES: nodeMap
    //EFFECTS: Creates a new instance of nodeMap with no elements in it.
    public HashGraph() 
    { 
        nodeMap = new HashMap<E, Node<E>>();
        assert(repOk());
    }

    //REQUIRES: node ≠ null
    //MODIFIES: nodeMap
    //EFFECTS:  Adds a mapping from node.getLabel() to node inside nodeMap.
    //          If there already exists such a mapping then does nothing.
    //          If node == null         throws IllegalArgumentException (unchecked).
    public void addNode(Node<E> node) throws IllegalArgumentException
    { 
        assert(repOk());
        if (node == null) throw new IllegalArgumentException();
        nodeMap.putIfAbsent(node.getLabel(), node); 
        assert(repOk());
    }

    //REQUIRES: nodeLabel ≠ null
    //MODIFIES: nodeMap
    //EFFECTS:  Adds a mapping from nodeLabel to (new HashNode<E>(nodeLabel)) 
    //          inside nodeMap.
    //          If there already exists such a mapping then does nothing.
    //          If nodeLabel == null    throws IllegalArgumentException (unchecked).
    public void addNode(E nodeLabel) throws IllegalArgumentException
    { 
        assert(repOk());
        if (nodeLabel == null) throw new IllegalArgumentException();
        addNode(new HashNode<>(nodeLabel));
        assert(repOk());
    }

    //REQUIRES: edge ِ≠ null
    //MODIFIES: nodeMap
    //EFFECTS:  Creates a connection between two nodes, first retrieving
    //          the node from which the edge is coming and then appending 
    //          edge.getOutgoing() to its connections set.
    //          If either of the two nodes are not already mapped inside nodeMap,
    //          then they are first included inside the collection.
    //          If connection already exists, then does nothing.
    //          If edge == null         throws IllegalArgumentException (unchecked).
    public void addEdge(Edge<E> edge) throws IllegalArgumentException
    {
        assert(repOk());
        if (edge == null) throw new IllegalArgumentException();
        E out = edge.getOutgoing();
        E in = edge.getIncoming();
        if(!containsNode(out)) addNode(new HashNode<E>(out));
        if(!containsNode(in)) addNode(new HashNode<E>(in));

        nodeMap.get(out).addConnection(in);
        assert(repOk());
    }

    //REQUIRES: node ≠ null
    //          nodeMap.containsKey(nodeLabel) == true
    //MODIFIES: nodeMap
    //EFFECTS:  Removes the (only) mapping <nodeLabel, _> from nodeMap.
    //          If nodeMap.containsKey(nodeLabel) == false
    //                                  throws NoSuchElementException (unchecked).
    //          If nodeLabel == null    throws IllegalArgumentException (unchecked).
    public void removeNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        assert(repOk());
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!nodeMap.containsKey(nodeLabel)) throw new NoSuchElementException();
        nodeMap.remove(nodeLabel);
        assert(repOk());
    }

    //REQUIRES: node ≠ null
    //          nodeMap.containsKey(node.getLabel()) == true
    //MODIFIES: nodeMap
    //EFFECTS:  Removes the mapping <node.getLabel(), node> from nodeMap.
    //          If nodeMap.containsKey(node.getLabel()) == false
    //                                  throws NoSuchElementException.
    //          If node == null         throws IllegalArgumentException.
    public void removeNode(Node<E> node) throws NoSuchElementException, IllegalArgumentException
    { 
        assert(repOk());
        if (node == null) throw new IllegalArgumentException();
        removeNode(node.getLabel());
        assert(repOk());
    }


    //REQUIRES: node ≠ null
    //          nodeMap.containsKey(edge.getOutgoing()) == true
    //          nodeMap.containsKey(edge.getIncoming()) == true
    //MODIFIES: nodeMap
    //EFFECTS:  Removes edge.GetOutgoing() from the collections set of
    //          nodeMap.get(edge.getIncoming()).
    //          If edge == null         throws IllegalArgumentException (unchecked).
    //          If nodeMap.containsKey(edge.getOutgoing()) == false
    //                                  throws NoSuchElementException (unchecked).
    //          If nodeMap.containsKey(edge.getIncoming()) == false
    //                                  throws NoSuchElementException (unchecked).
    public void removeEdge(Edge<E> edge) throws NoSuchElementException, IllegalArgumentException
    {
        assert(repOk());
        if (edge == null) throw new IllegalArgumentException();
        Node<E> node = nodeMap.get(edge.getOutgoing());
        if (node == null) throw new NoSuchElementException();
        node.removeConnection(edge.getIncoming());
        assert(repOk());
    }


    //REQUIRES: nodeLabel ≠ null
    //EFFECTS:  Returns true if nodeMap.get(nodeLabel) ≠ null (there exists a mapping
    //          from nodeLabel to any object), false otherwise.
    //          If nodeLabel == null    throws IllegalArgumentException (unchecked). 
    public boolean containsNode(E nodeLabel) throws IllegalArgumentException
    {
        assert(repOk());
        if (nodeLabel == null) throw new IllegalArgumentException();
        assert(repOk());
        return nodeMap.containsKey(nodeLabel);
    }

    //EFFECTS: Returns nodeMap.size() = nodeMap.keySet().size()
    public int size() 
    {
        return nodeMap.size(); // assert == nodeMap.keySet().size()
    }

    //REQUIRES: nodeLabel ≠ null
    //          nodeMap.containsKey(nodeLabel) == true          
    //EFFECTS:  Returns a collection containing adjacency nodes of
    //          nodeMap.get(nodeLabel)
    //          If nodeLabel == null    throws IllegalArgumentException
    //          If nodeMap.containsKey(nodeLabel) == false  
    //                                  throws NoSuchElementException
    public Collection<E> getAdjacency(E nodeLabel)
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        Node<E> node = nodeMap.get(nodeLabel);
        if (node == null) throw new NoSuchElementException();
        return node.getAdjacency();
    }

    //EFFECTS: Returns an iterator over the keys
    public Iterator<E> iterator()
    {
        return nodeMap.keySet().iterator();
    }

    //EFFECTS: Returns a string representation of the current class instance
    @Override
    public String toString() 
    {
        assert(repOk());
        final StringBuilder sb = new StringBuilder();
        for (E node : nodeMap.keySet()) 
        {
            sb.append(node + ": " + nodeMap.get(node) + "\n");
        }
        assert(repOk());
        return sb.toString();
    }
}



































