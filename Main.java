
public class Main 
{
    public static void main(String[] args)
    {
        SocialNetwork network = new SocialNetwork();

        network.addUser("marco");
        network.addUser("filippo");
        network.addUser("andrea");
        network.addUser("giovanni");

        network.addFriendship("marco", "filippo");
        network.addFriendship("marco", "andrea");
        network.addFriendship("filippo", "andrea");
        network.prettyPrint();
    }
}
