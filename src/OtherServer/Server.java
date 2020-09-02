package OtherServer;


import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args)
    {
        MarcoServidor mimarco = new MarcoServidor();
        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MarcoServidor extends JFrame implements Runnable
{
    public MarcoServidor()
    {

        setBounds(1200,300,280,350);

        JPanel milamina= new JPanel();

        milamina.setLayout(new BorderLayout());

        areatexto=new JTextArea();

        milamina.add(areatexto,BorderLayout.CENTER);

        add(milamina);

        setVisible(true);

        Thread thread = new Thread( this );
        thread.start();
    }


    private	JTextArea areatexto;
    @Override
    public void run() {
        System.out.println("Estoy escuchando");
        try
        {
            ServerSocket server = new ServerSocket( 9090);

            System.out.println("Servidor:" + server.getLocalSocketAddress());

            String nick = "", ip = "", mensaje = "";

            PaqueteDatos paqueteRecibido = null;
            while (true)
            {

                Socket conexionCliente = server.accept();
                ObjectInputStream input = new ObjectInputStream(
                        conexionCliente.getInputStream()
                );

                try {
                    //Se recibe paquete
                    paqueteRecibido = (PaqueteDatos) input.readObject();
                    nick = paqueteRecibido.getNick();
                    ip = paqueteRecibido.getIp();
                    mensaje = paqueteRecibido.getMensaje();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                areatexto.append( "\n"+ nick + ": "+ mensaje +"Para"
                +" para: " + ip);

                //Reenviamos paquete
                Socket enviaDestinatario = new Socket( ip,9090 );
                ObjectOutputStream paqueteReenvio = new ObjectOutputStream(
                  conexionCliente.getOutputStream()
                );
                paqueteReenvio.writeObject( paqueteRecibido );


                enviaDestinatario.close();
                paqueteReenvio.close();
                conexionCliente.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
