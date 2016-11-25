import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.HashSet;
import java.util.Collection;
import java.util.NoSuchElementException;

public abstract class Node<E>
{
    protected E label;
    protected Collection<E> adiacency;

    public Node(Collection<E> adiacency, E label) throws IllegalArgumentException
    {
        if (adiacency == null || label == null) throw new IllegalArgumentException();
        this.label = label; // TODO: force deep copy
        this.adiacency = adiacency;
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
        if (!adiacency.contains(nodeLabel)) 
                adiacency.add(nodeLabel);
    }

    public void removeConnection(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();

        // explicit parameter signature not necessary, but quite informative
        Predicate<E> p = (E label) -> label.equals(nodeLabel); 
        if(!adiacency.removeIf(p)) throw new NoSuchElementException();
    }

}
