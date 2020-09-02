package OtherServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        MarcoCliente mimarco=new MarcoCliente();
        mimarco.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
    }
}

class MarcoCliente extends JFrame
{
    public MarcoCliente()
    {
        setBounds(600,300,280,350);
        LaminaMarcoCliente milamina=new LaminaMarcoCliente();
        add(milamina);
        setVisible(true);
    }
}

//Para que este siempre a la escucha runnable
class LaminaMarcoCliente extends JPanel implements Runnable{

    private final JTextField nick;
    private final JTextField ip;

    public LaminaMarcoCliente(){
        nick = new JTextField( 5 );
        ip = new JTextField( 8 );
        JLabel texto=new JLabel("Chat");
        campoChat = new JTextArea(  12,20);
        campo1=new JTextField(20);

        add(ip);
        add(nick);
        add(texto);

        add(campoChat);
        add(campo1);

        miboton=new JButton("Enviar");

        EnviarTexto event = new EnviarTexto();
        miboton.addActionListener( event );
        add(miboton);

        Thread procesoCliente = new Thread( this );
        procesoCliente.start();
    }

    //Estara aceptando conexiones
    @Override
    public void run() {
        try
        {
            ServerSocket serverCliente = new ServerSocket( 9090 );
            Socket cliente ;

            PaqueteDatos paqueteRecibido = null;

            while (true)
            {
                cliente = serverCliente.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream( cliente.getInputStream() );
                paqueteRecibido =(PaqueteDatos) flujoEntrada.readObject();

                campoChat.append( "\n"+ paqueteRecibido.getNick() + ": " +paqueteRecibido.getMensaje());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    class EnviarTexto implements ActionListener
    {

        Socket socket = null;
        String host = "";

        public void SetConnection(String host,
                                  int port)
        {
            try
            {
                socket = new Socket( host
                        ,port);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            campoChat.append( campo1.getText() );
            try
            {
                SetConnection( "192.168.233.1", 9090);

                PaqueteDatos datos = new PaqueteDatos();
                datos.setIp( ip.getText() );
                datos.setNick( nick.getText() );

                ObjectOutputStream paqueteWritter = new ObjectOutputStream( socket.getOutputStream() );

                paqueteWritter.writeObject( datos );
                socket.close();
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    JTextField campo1;

    private JTextArea campoChat;

    private JButton miboton;


}

class PaqueteDatos implements Serializable
{
    private String Ip;
    private String Nick;
    private String Mensaje;

    public String getIp() {
        return Ip;
    }

    public void setIp(String ip) {
        Ip = ip;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

}
