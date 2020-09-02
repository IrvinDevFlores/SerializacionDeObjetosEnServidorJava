package App;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private ServerSocket Server ;

    public Server(String ipAdress) throws IOException
    {
        if(ipAdress != null && !ipAdress.isEmpty())
        {
            Server = new ServerSocket( 0,1, InetAddress.getByName( ipAdress ) );
        }else
        {
            Server = new ServerSocket( 0,1,InetAddress.getLocalHost() );
        }
    }

    private void Listen() throws IOException
    {
        String Data = null;
        Socket Client = Server.accept();
        String clientAddress = Client.getInetAddress().getHostAddress();
        System.out.println("Nueva conexion con uno de los clientes."+
                clientAddress);

        BufferedReader input = new BufferedReader(
                new InputStreamReader( Client.getInputStream() )
        );

        Data = input.readLine();
       while (Data != null)
       {
           System.out.println( "\n Mensaje de: " + clientAddress + ": " +
                   Data );
       }
    }
}
