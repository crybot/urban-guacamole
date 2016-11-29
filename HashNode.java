import java.util.HashSet;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class HashNode<E> extends Node<E>
{

    //Copies a generic collection containing values of type E into a new HashSet.
    //Note how it is not necessary to check for duplicates since we are using a Set.
    private static <E> HashSet<E> copy(Collection<E> adjacency)
    {
        HashSet<E> tmp = new HashSet<>();
        for (E v : adjacency) // may be more generic?
            tmp.add(v);
        return tmp;
    }

    public HashNode(Collection<E> adjacency, E label) throws IllegalArgumentException
    {
        super(copy(adjacency), label);
    }

    public HashNode(E label) throws IllegalArgumentException
    {
        // do not invoke this(..) to avoid unnecessary copy
        super(new HashSet<>(), label);
    }

    @Override
    public void addConnection(E nodeLabel) throws IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!nodeLabel.equals(this.label))
            adjacency.add(nodeLabel);
    }

    @Override
    public void removeConnection(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!adjacency.remove(nodeLabel)) throw new NoSuchElementException();
    }

    @Override
    public String toString() 
    {
        final StringBuilder sb = new StringBuilder("{ ");
        for (E v : adjacency)
        {
            sb.append(v + ",");
        }
        sb.deleteCharAt(sb.length()-1); //deletes last comma
        return sb.append(" }").toString();
    }
}
