package TestServer;

public class Player1
{
    public static void main(String... args)
    {
        PlayerFrame player2 = new PlayerFrame( 600,600 );
        player2.ConnectToServer();
        player2.setUpGUI();
    }
}
