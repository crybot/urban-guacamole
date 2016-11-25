import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.HashSet;
import java.util.Collection;
import java.util.NoSuchElementException;

public class Node<E>
{
    private E label;
    private HashSet<E> adiacency;

    private HashSet<E> copy(Collection<? extends E> adiacency)
    {
        HashSet<E> tmp = new HashSet<>();
        for (E v : adiacency) // may be more generic?
            tmp.add(v);
        return tmp;
    }

    public Node(Collection<? extends E> adiacency, E label) throws IllegalArgumentException
    {
        if (adiacency == null || label == null) throw new IllegalArgumentException();
        this.label = label; // TODO: force deep copy
        this.adiacency = copy(adiacency);
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
        adiacency.add(nodeLabel);
    }

    public void removeConnection(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!adiacency.remove(nodeLabel)) throw new NoSuchElementException();
        //Predicate<E> p = (E label) -> label.equals(nodeLabel); // explicit parameter signature not necessary, but quite informative
        //adiacency.removeIf(p);
    }

    @Override
    public boolean equals(Object other)
    {
        // TODO: implement equals with generics
        return super.equals(other);
    }
}
