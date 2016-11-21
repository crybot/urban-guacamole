import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Node<E>
{
    private E label;
    private List<E> adiacency;

    public Node(List<E> adiacency, E label)
    {
        this.label = label; // TODO: force deep copy
        this.adiacency = adiacency; // TODO: force deep copy
    }

    public Node(E label)
    {
        this(new ArrayList<E>(), label);
    }

    public Node() 
    {
        this(new ArrayList<E>(), null);
    }

    public E getLabel() { return label; }

    public void addConnection(E nodeLabel)
    {
        adiacency.add(nodeLabel);
    }

    public void removeConnection(E nodeLabel)
    {
        Predicate<E> p = (E label) -> label.equals(nodeLabel); // explicit parameter signature not necessary, but quite informative
        adiacency.removeIf(p);
    }

    @Override
    public boolean equals(Object other)
    {
        // TODO: implement equals with generics
        return super.equals(other);
    }
}
