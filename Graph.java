
public abstract class Graph<E>
{
    protected Graph() {}
    abstract void addNode(Node<E> node);

    void addEdge(Edge<E> edge)
    {
        E out = edge.getOutgoing();
        E in = edge.getIncoming();
        if(!containsNode(out)) addNode(new Node<E>(out));
        if(!containsNode(in)) addNode(new Node<E>(in));

        getNode(out).addConnection(in); // needs getNode returning a referencde
    }

    abstract void removeNode(Node<E> node);
    abstract void removeEdge(Edge<E> node); //throws exception
    abstract boolean containsNode(E nodeLabel);
    abstract Node<E> getNode(E nodeLabel); // may expose to vulnerabilities

}
