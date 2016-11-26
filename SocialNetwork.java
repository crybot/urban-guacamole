
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

    public void prettyPrint()
    {
        System.out.println("Network:\n" + friendsGraph.toString());
    }
}
