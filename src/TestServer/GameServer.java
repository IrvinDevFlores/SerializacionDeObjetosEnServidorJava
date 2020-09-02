package TestServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer
{
    private ServerSocket serverRunning;
    private int numPlayers;
    private int maxPlayers;

    private Socket p1Socket;
    private Socket p2Socket;

    private ReadFromClient p1Read;
    private ReadFromClient p2Read;

    private WriteToClients p1Write;
    private WriteToClients p2Write;

    ArrayList<PlayerSprite> sprites;


    public static void main(String... args)
    {
        GameServer gameServer = new GameServer();
        gameServer.AcceptConnections();
    }

    public GameServer()
    {
        System.out.println("Game server");
        numPlayers = 0;
        maxPlayers = 2;
        sprites = new ArrayList<>(  );
        player1X = 100;
        player1Y = 400;
        player2X = 450;
        player2Y = 390;

        MakeServer( 45371 );
    }
    private int player1X, player2X, player1Y, player2Y;
    public void AcceptConnections()
    {
        try{
            System.out.println("Waiting for clients...");
            while(numPlayers < maxPlayers)
            {
                Socket currentClient = serverRunning.accept();
                DataInputStream input =  new DataInputStream(currentClient.getInputStream());
                DataOutputStream output = new DataOutputStream( currentClient.getOutputStream());

                numPlayers++;
                output.writeInt(numPlayers);
                System.out.println("Player "+numPlayers+"Se conecto");

                ReadFromClient readFromClient = new ReadFromClient( numPlayers,input );
                WriteToClients writeToClients = new WriteToClients( numPlayers, output );

                if(numPlayers == 1)
                {
                     p1Socket = currentClient;
                     p1Read = readFromClient;
                     p1Write = writeToClients;
                }
                else
                {
                    p2Socket = currentClient;
                    p2Read = readFromClient;
                    p2Write = writeToClients;

                    p1Write.SendStartMessage();
                    p2Write.SendStartMessage();

                    Thread readThread1 = new Thread(p1Read);
                    Thread readThread2 = new Thread( p2Read );
                    readThread1.start();
                    readThread2.start();

                    Thread writeThread1 = new Thread( p1Write   );
                    Thread writeThread2 = new Thread( p2Write );
                    writeThread1.start();
                    writeThread2.start();

                }
            }
            System.out.println("No se aceptan mas personas");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void MakeServer(int port)
    {
        try
        {
            serverRunning = new ServerSocket( port );
        }
        catch (IOException e )
        {
            e.printStackTrace();
        }
    }

    ArrayList<PlayerSprite> playerSprites = new ArrayList<>(  );
    private class ReadFromClient implements Runnable
    {
        private int PlayerId;
        private DataInputStream input;

        public ReadFromClient(int playerId, DataInputStream in)
        {
            PlayerId = playerId;
            input = in;
            System.out.println("Leyendo datos..." + playerId);

        }
        @Override
        public void run() {
            try
            {
                while(true)
                {
                    if(PlayerId == 1)
                    {
                        player1X = input.readInt();
                        player1Y = input.readInt();

                    }else
                    {
                        player2X = input.readInt();
                        player2Y = input.readInt();
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class WriteToClients implements Runnable
    {
        private int PlayerId;
        private DataOutputStream output;
        public WriteToClients(int playerId, DataOutputStream out)
        {
            PlayerId = playerId;
            output = out;
            System.out.println("Enviando datos..." + playerId);
        }
        @Override
        public void run() {
            try
            {
                while(true)
                {
                    if(PlayerId == 1)
                    {
                        output.writeInt( player2X );
                        output.writeInt( player2Y );
                        output.flush();
                    }

                    else
                    {
                        output.writeInt( player1X );
                        output.writeInt( player1Y );
                        output.flush();
                    }

                    try
                    {
                        Thread.sleep( 25 );
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void SendStartMessage()
        {
            try
            {
                output.writeUTF( "Tenemos dos jugadores" );
            }catch (IOException e)
            {e.printStackTrace();}
        }
    }

}
