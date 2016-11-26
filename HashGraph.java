import java.util.HashMap;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

public class HashGraph<E> implements Graph<E>
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
     */

    private HashMap<E, Node<E>> nodeMap; // not synchronized hash table

    //MODIFIES: nodeMap
    //EFFECTS: Creates a new instance of nodeMap with no elements in it.
    public HashGraph() 
    { 
        nodeMap = new HashMap<E, Node<E>>();
    }

    //REQUIRES: node ≠ null
    //MODIFIES: nodeMap
    //EFFECTS:  Adds a mapping from node.getLabel() to node inside nodeMap.
    //          If there already exists such a mapping then does nothing.
    //          If node == null         throws IllegalArgumentException (unchecked).
    public void addNode(Node<E> node) throws IllegalArgumentException
    { 
        if (node == null) throw new IllegalArgumentException();
        nodeMap.putIfAbsent(node.getLabel(), node); 
    }
    
    //REQUIRES: nodeLabel ≠ null
    //MODIFIES: nodeMap
    //EFFECTS:  Adds a mapping from nodeLabel to (new HashNode<E>(nodeLabel)) 
    //          inside nodeMap.
    //          If there already exists such a mapping then does nothing.
    //          If nodeLabel == null    throws IllegalArgumentException (unchecked).
    public void addNode(E nodeLabel) throws IllegalArgumentException
    { 
        if (nodeLabel == null) throw new IllegalArgumentException();
        nodeMap.putIfAbsent(nodeLabel, new HashNode<>(nodeLabel)); 
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
        if (edge == null) throw new IllegalArgumentException();
        E out = edge.getOutgoing();
        E in = edge.getIncoming();
        if(!containsNode(out)) addNode(new HashNode<E>(out));
        if(!containsNode(in)) addNode(new HashNode<E>(in));

        nodeMap.get(out).addConnection(in);
        //getNode(out).addConnection(in); // needs getNode returning a referencde
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
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!nodeMap.containsKey(nodeLabel)) throw new NoSuchElementException();
        nodeMap.remove(nodeLabel);
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
        if (node == null) throw new IllegalArgumentException();
        removeNode(node.getLabel());
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
        if (edge == null) throw new IllegalArgumentException();
        Node<E> node = nodeMap.get(edge.getOutgoing());
        if (node == null) throw new NoSuchElementException();
        node.removeConnection(edge.getIncoming());
    }


    //REQUIRES: nodeLabel ≠ null
    //          nodeMap.containsKey(nodeLabel) == true
    //EFFECTS:  Returns the mapped value 'v' of nodeLabel such that 
    //          nodeMap.get(nodeLabel) == v if there exists such a node.
    //          Otherwise               throws NoSuchElementException otherwise (unchecked).
    //          If nodeLabel == null    throws IllegalArgumentException (unchecked).
    //TODO:     Deep copy.
    public Node<E> getNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        Node<E> node = nodeMap.get(nodeLabel);
        if (node == null) throw new NoSuchElementException();
        return node; 
    }


    //REQUIRES: nodeLabel ≠ null
    //EFFECTS:  Returns true if getNode(nodeLabel) ≠ null (there exists a mapping
    //          from nodeLabel to any object), false otherwise.
    //          If nodeLabel == null    throws IllegalArgumentException (unchecked). 
    public boolean containsNode(E nodeLabel) throws IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        return nodeMap.containsKey(nodeLabel);
    }

    //EFFECTS: Returns a string representation of the current class instance
    @Override
    public String toString() 
    {
        final StringBuilder sb = new StringBuilder();
        for (E node : nodeMap.keySet()) 
        {
           sb.append(node + ": " + getNode(node) + "\n");
        }
        return sb.toString();
    }
}



































