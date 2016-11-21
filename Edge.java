
public class Edge<E>
{
    private E inLabel, outLabel;

    public Edge(E outLabel, E inLabel)
    {
        this.inLabel = inLabel;
        this.outLabel = outLabel;
    }

    public E getIncoming() { return inLabel; }
    public E getOutgoing() { return outLabel; }
}
