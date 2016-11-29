import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.lang.Boolean;
import java.util.ArrayList;

public class SocialNetwork
{
    //OVERVIEW: Social network implementation using HashGraph<String>.
    //          Users are identified via their names.
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
        return new ArrayList<>(friendsGraph.getAdjacency(user));
    }

    public void addFriendship(String user1, String user2)
    {
        friendsGraph.addEdge(new Edge<String>(user1, user2));
        friendsGraph.addEdge(new Edge<String>(user2, user1));
    }

    // Returns a random user from the ones already inserted
    public String randomUser()
    {
        int rand = (int) (Math.random() * friendsGraph.size());
        for(String user : friendsGraph) { if(--rand <= 0) return user; }

        throw new NoSuchElementException();
    }

    // Marks a node as visited or not visited
    private void setVisited(String user, Boolean visited)
    {
        this.visited.put(user, visited);
    }

    // Sets minimum distance between source and destination users
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

    // Computes network diameter
    public int diameter()
    {
        int max = -1;
        for (String source : friendsGraph)
        {
            for (String dest : friendsGraph)
            {
                int distance = 0;
                if (!source.equals(dest))
                    if ((distance = getDistance(source, dest)) == -1) // checks if distance has already been computed (memoization)
                        distance = shortestPath(source, dest);

                if (distance > max) max = distance;
            }
        }
        return max;
    }

    //BFS
    public int shortestPath(String source, String destination)
    {
        Queue<String> toVisit = new LinkedList<>();

        for(String user : friendsGraph) 
        {
            setVisited(user, false);
            setDistance(source, user, -1);
        }

        setDistance(source, source, 0);
        toVisit.add(source);

        while(toVisit.size() > 0) 
        {
            String user = toVisit.poll();
            setVisited(user, true);
            int current = getDistance(source, user);

            for(String adjacent : friendsGraph.getAdjacency(user))
            {
                if (!isVisited(adjacent))
                {
                    setVisited(adjacent, true);
                    setDistance(source, adjacent, current + 1);
                    toVisit.add(adjacent);
                }
            }
        }
        return getDistance(source, destination);
    }

    // Prints the network in a nice format
    public void prettyPrint()
    {
        System.out.println("Network:\n" + friendsGraph.toString());
    }
}

















































