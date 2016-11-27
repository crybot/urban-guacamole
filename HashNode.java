import java.util.HashSet;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class HashNode<E> extends Node<E>
{
    private static <E> HashSet<E> copy(Collection<E> adiacency)
    {
        HashSet<E> tmp = new HashSet<>();
        for (E v : adiacency) // may be more generic?
            tmp.add(v);
        return tmp;
    }

    public HashNode(Collection<E> adiacency, E label) throws IllegalArgumentException
    {
        super(copy(adiacency), label);
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
            adiacency.add(nodeLabel);
    }

    @Override
    public void removeConnection(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!adiacency.remove(nodeLabel)) throw new NoSuchElementException();
    }

    @Override
    public String toString() 
    {
        final StringBuilder sb = new StringBuilder("{ ");
        for (E v : adiacency)
        {
            sb.append(v + ",");
        }
        sb.deleteCharAt(sb.length()-1); //deletes last comma
        return sb.append(" }").toString();
    }
}
