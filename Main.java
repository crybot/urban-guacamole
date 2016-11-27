
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;



public class Main 
{
    public static void main(String[] args)
    {
        SocialNetwork network = new SocialNetwork();

        try
        {
            Files.lines(Paths.get("nomi_italiani.txt")).forEach(line -> 
                    {
                    network.addUser(line);
                    for (int i=0; i<10; i++) network.addFriendship(line, network.randomUser());
                    }
            );
        }
        catch(IOException e) {}

        network.prettyPrint();
        System.out.println(network.shortestPath("marco", "giovanni"));
    }
}
