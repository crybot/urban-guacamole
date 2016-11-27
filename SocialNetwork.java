import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.lang.Boolean;

public class SocialNetwork
{
    private HashGraph<String> friendsGraph;
    private HashMap<String, Boolean> visited;
    private HashMap<Map.Entry<String, String>, Integer> distance;


    public SocialNetwork()
    {
        friendsGraph = new HashGraph<>();
        visited = new HashMap<>();
        distance = new HashMap<>();
    }

    public void addUser(String user)
    {
        friendsGraph.addNode(user);
    }

    public Collection<String> getFriends(String user)
    {
        return friendsGraph.getNode(user).getAdiacency();
    }

    public void addFriendship(String user1, String user2)
    {
        friendsGraph.addEdge(new Edge<String>(user1, user2));
        friendsGraph.addEdge(new Edge<String>(user2, user1));
    }

    public String randomUser()
    {
        Collection<String> users = friendsGraph.getLabels();
        int rand = (int) (Math.random() *  users.size());
        for(String user : users) { if(--rand <= 0) return user; }

        throw new NoSuchElementException();
    }

    private void setVisited(String user, Boolean visited)
    {
        this.visited.put(user, visited);
    }

    private void setDistance(String source, String destination, int distance)
    {
        this.distance.put(new SimpleEntry<>(source, destination), distance);
    }

    private boolean isVisited(String user)
    {
        return visited.get(user);
    }

    private int getDistance(String source, String destination)
    {
        return distance.getOrDefault(new SimpleEntry<>(source, destination), -1);
    }

    public int diameter()
    {
        int max = -1;
        for (Node<String> source : friendsGraph.getNodes())
        {
            for (Node<String> destination : friendsGraph.getNodes())
            {
                int distance = 0;
                if (source != destination)
                    if ((distance = getDistance(source.getLabel(), destination.getLabel())) == -1)
                        distance = shortestPath(source.getLabel(), destination.getLabel());

                if (distance > max) max = distance;
            }
        }
        return max;
    }

    public int shortestPath(String source, String destination)
    {
        Queue<Node<String>> toVisit = new LinkedList<>();
        Collection<Node<String>> users = friendsGraph.getNodes();

        for(Node<String> user : users) 
        {
            setVisited(user.getLabel(), false);
            setDistance(source, user.getLabel(), -1);
        }

        setDistance(source, source, 0);
        toVisit.add(friendsGraph.getNode(source));

        while(toVisit.size() > 0) 
        {
            Node<String> user = toVisit.poll();
            setVisited(user.getLabel(), true);
            int current = getDistance(source, user.getLabel());

            for(String username : user.getAdiacency())
            {
                Node<String> adjacent = friendsGraph.getNode(username);
                if (!isVisited(adjacent.getLabel()))
                {
                    setVisited(adjacent.getLabel(), true);
                    setDistance(source, adjacent.getLabel(), current + 1);
                    toVisit.add(adjacent);
                }
            }
        }
        return getDistance(source, destination);
    }

    public void prettyPrint()
    {
        System.out.println("Network:\n" + friendsGraph.toString());
    }
}

















































