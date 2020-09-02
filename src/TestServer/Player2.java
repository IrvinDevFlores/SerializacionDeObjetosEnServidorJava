package TestServer;

public class Player2
{
    public static void main(String... args)
    {
        PlayerFrame player = new PlayerFrame( 600,600 );
        player.ConnectToServer();
        player.setUpGUI();

    }
}
