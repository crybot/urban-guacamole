import java.util.HashMap;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

public class GraphList<E> implements Graph<E>
{
    private HashMap<E, Node<E>> nodeMap; // not synchronized

    public GraphList() { super(); }

    public void addNode(Node<E> node) throws IllegalArgumentException
    { 
        if (node == null) throw new IllegalArgumentException();
        nodeMap.putIfAbsent(node.getLabel(), node); 
    }

    public void addEdge(Edge<E> edge) throws IllegalArgumentException
    {
        if (edge == null) throw new IllegalArgumentException();
        E out = edge.getOutgoing();
        E in = edge.getIncoming();
        if(!containsNode(out)) addNode(new HashNode<E>(out));
        if(!containsNode(in)) addNode(new HashNode<E>(in));

        nodeMap.get(out).addConnection(in);
        //getNode(out).addConnection(in); // needs getNode returning a referencde
    }

    public void removeNode(Node<E> node) throws NoSuchElementException, IllegalArgumentException
    { 
        if (node == null) throw new IllegalArgumentException();
        removeNode(node.getLabel());
    }
    public void removeNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        if (!nodeMap.containsKey(nodeLabel)) throw new NoSuchElementException();
        nodeMap.remove(nodeLabel);
    }

    public void removeEdge(Edge<E> edge) throws NoSuchElementException, IllegalArgumentException
    {
        if (edge == null) throw new IllegalArgumentException();
        Node<E> node = nodeMap.get(edge.getOutgoing());
        if (node == null) throw new NoSuchElementException();
        node.removeConnection(edge.getIncoming());
    }
    
    public Node<E> getNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        return nodeMap.get(nodeLabel);
    }

    public boolean containsNode(E nodeLabel) throws IllegalArgumentException
    {
        if (nodeLabel == null) throw new IllegalArgumentException();
        return nodeMap.containsKey(nodeLabel);
    }
}
