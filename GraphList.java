import java.util.HashMap;

public class GraphList<E> extends Graph<E>
{
    private HashMap<E, Node<E>> nodeMap; // not synchronized

    public GraphList() { super(); }

    @Override
    public void addNode(Node<E> node) { nodeMap.putIfAbsent(node.getLabel(), node); }

    @Override
    public void addEdge(Edge<E> edge)
    {

    }

    @Override
    public void removeNode(Node<E> node) { }

    @Override
    public void removeEdge(Edge<E> edge) { }
}
