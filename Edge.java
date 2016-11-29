import java.lang.IllegalArgumentException;

public class Edge<E>
{
    //incoming and outgoing nodes info
    private E inLabel, outLabel;

    public Edge(E outLabel, E inLabel) throws IllegalArgumentException
    {
        if (outLabel == null || inLabel == null) throw new IllegalArgumentException();
        this.inLabel = inLabel;
        this.outLabel = outLabel;
    }

    public E getIncoming() { return inLabel; }
    public E getOutgoing() { return outLabel; }
}
