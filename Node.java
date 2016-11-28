import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Collection;
import java.util.NoSuchElementException;

public abstract class Node<E>
{
    /* OVERVIEW:
     * The abstract data type Node<E> represents a generic prototype of a node 
     * data structure that contains a value (or label) of type E.
     * A node holds all of its outgoing connections to other nodes in the form of
     * a set containing values of type E.
     * a typical instance is the tuple: <label, {v¹, v², ... , vⁿ}>
     * Nodes cannot contain connections to themselves.
     */

    protected final E label;
    protected Collection<E> adjacency;

    public Node(Collection<E> adjacency, E label) throws IllegalArgumentException
    {
        if (adjacency == null || label == null) throw new IllegalArgumentException();
        this.label = label; // TODO: force deep copy
        this.adjacency = adjacency;
    }

    public Node(E label)
    {
        this(new ArrayList<E>(), label);
    }

    public E getLabel() 
    { 
        return label; 
    }

    public void addConnection(E nodeLabel) throws IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!nodeLabel.equals(this.label) && !adjacency.contains(nodeLabel) ) 
                adjacency.add(nodeLabel);
    }

    public void removeConnection(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();

        // explicit parameter signature not necessary, but quite informative
        Predicate<E> p = (E label) -> label.equals(nodeLabel); 
        if(!adjacency.removeIf(p)) throw new NoSuchElementException();
    }

    public Collection<E> getAdjacency()
    {
        return adjacency; // TODO: force deep copy
    }
}
