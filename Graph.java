import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

public interface Graph<E>
{
    /* OVERVIEW:
     * The abstract data type Graph<E> represents the generic interface of a Graph
     * data structure with elements of type E.
     * The typical instance of the collection is a tuple <V,E'> where V is the set
     * of vertices and E' is a set containing edges (i.e. connections 
     * between elements ov V). Every element of V shall have a label or info used to
     * identify it along with a collection of every outgoing connection, as described
     * in Node<E>, which implicitly defines E'.
     */

    //REQUIRES: node ≠ null
    //MODIFIES: V
    //EFFECTS:  Adds a node 'v' to V such that V = V ∪ {v}.
    //          if node == nul          throws IllegalArgumentException.
    void addNode(Node<E> node) throws IllegalArgumentException;

    //REQUIRES: edge ِ≠ null
    //MODIFIES: E'
    //EFFECTS:  Adds an edge 'edge' to E', creating a connection between two elements
    //          of V.
    //          if edge == null         throws IllegalArgumentException.
    void addEdge(Edge<E> edge) throws IllegalArgumentException;

    //REQUIRES: node ≠ null
    //          node ∈ V
    //MODIFIES: V, E'
    //EFFECTS:  Removes a node 'node' from V along with all its incoming and outgoing 
    //          connections(namely c ⊆ E') , such that V = V \ {node} and E' = E' \ c.
    //          If node \not ∈ V        throws NoSuchElementException.
    //          If node == null         throws IllegalArgumentException.
    void removeNode(Node<E> node) throws NoSuchElementException, IllegalArgumentException;

    //REQUIRES: nodeLabel ≠ null
    //          getNode(nodeLabel) ∈ V
    //MODIFIES: V, E'
    //EFFECTS:  Removes a node 'v' from V along with all its incoming and outgoing 
    //          connections(namely c ⊆ E'), such that v = getNode(nodeLabel), 
    //          V = V \ {v}      and     E' = E' \ c.
    //          If v \not ∈ V           throws NoSuchElementException.
    //          If nodeLabel == null    throws IllegalArgumentException.
    void removeNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException;

    //REQUIRES: node ≠ null
    //          edge ∈ E'
    //MODIFIES: E'
    //EFFECTS:  Removes an edge from E', such that E' = E' \ {edge}.
    //          If edge == null         throws IllegalArgumentException.
    //          If edge \not ∈ E'       throws NoSuchElementException.
    void removeEdge(Edge<E> edge) throws NoSuchElementException, IllegalArgumentException;


    //REQUIRES: nodeLabel ≠ null
    //EFFECTS:  Returns true if getNode(nodeLabel) ∈ V, false otherwise.
    //          If nodeLabel == null    throws IllegalArgumentException. 
    boolean containsNode(E nodeLabel) throws IllegalArgumentException;


    //REQUIRES: nodeLabel ≠ null
    //          ∃ v ∈ V: v.getLabel() == nodeLabel
    //EFFECTS:  Returns v such that v ∈ V and v.getLabel() == nodeLabel, if
    //          there exist such a node.
    //          Otherwise               throws NoSuchElementException otherwise.
    //          If nodeLabel == null    throws IllegalArgumentException
    Node<E> getNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException; // may expose to vulnerabilities
}
