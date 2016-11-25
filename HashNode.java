import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.HashSet;
import java.util.Collection;
import java.util.NoSuchElementException;

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
        adiacency.add(nodeLabel);
    }

    @Override
    public void removeConnection(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!adiacency.remove(nodeLabel)) throw new NoSuchElementException();
    }

}
