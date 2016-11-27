import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.lang.Math;
import java.util.NoSuchElementException;

public class SocialNetwork
{
    private HashGraph<String> friendsGraph;

    public SocialNetwork()
    {
        friendsGraph = new HashGraph<>();
    }

    public void addUser(String user)
    {
        friendsGraph.addNode(user);
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

    public int shortestPath(String user1, String user2)
    {
        Queue<Node<String>> toVisit = new LinkedList<>();
        Collection<Node<String>> users = friendsGraph.getNodes();

        for(Node<String> user : users) 
        {
            user.setVisited(false);
            user.setDistance(-1);
        }

        Node<String> source = friendsGraph.getNode(user1);
        source.setDistance(0);
        toVisit.add(source);

        while(toVisit.size() > 0) 
        {
            Node<String> user = toVisit.poll();
            user.setVisited(true);

            for(String username : user.getAdiacency())
            {
                Node<String> adjacent = friendsGraph.getNode(username);
                if (!adjacent.visited())
                {
                    adjacent.setVisited(true);
                    adjacent.setDistance(user.getDistance() + 1);
                    toVisit.add(adjacent);
                }
            }
        }
        return friendsGraph.getNode(user2).getDistance();
    }

    public void prettyPrint()
    {
        System.out.println("Network:\n" + friendsGraph.toString());
    }
}
