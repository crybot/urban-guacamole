import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

public interface Graph<E>
{
    void addNode(Node<E> node) throws IllegalArgumentException;

    void addEdge(Edge<E> edge) throws IllegalArgumentException;

    void removeNode(Node<E> node) throws NoSuchElementException, IllegalArgumentException;
    void removeNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException;
    void removeEdge(Edge<E> node) throws NoSuchElementException, IllegalArgumentException;
    boolean containsNode(E nodeLabel) throws IllegalArgumentException;
    Node<E> getNode(E nodeLabel) throws NoSuchElementException, IllegalArgumentException; // may expose to vulnerabilities

}
