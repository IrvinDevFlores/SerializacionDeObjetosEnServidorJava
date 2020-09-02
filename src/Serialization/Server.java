package Serialization;



import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame implements Runnable
{
    public JTextField CampoText;
    public JTextArea areaPantalla;
    
    Socket clientConnection = null;
    ObjectOutputStream output = null;
    ObjectInputStream input = null;

    ServerSocket server;

    public Server()
    {
        super("Server");
        CampoText = new JTextField(  );
        CampoText.setEditable( false );
        CampoText.addActionListener( e -> {
                EnviarDatos(e.getActionCommand());
            CampoText.setText( "" );
        } );

        add(CampoText, BorderLayout.NORTH );

        areaPantalla = new JTextArea(  );
        add(new JScrollPane( areaPantalla ));
        setSize( 300,150 );
        setVisible( true );
    }

    public synchronized void EsperarConexion() throws IOException
    {
        MostrarMensaje( "Esperando clientes..." );
        clientConnection = server.accept();
        MostrarMensaje( "Conexion establecida desde: " + clientConnection.getInetAddress());
    }

    public void EstablecerFlujo() throws IOException
    {
        output = new ObjectOutputStream( clientConnection.getOutputStream());
        output.flush();
        input = new ObjectInputStream( clientConnection.getInputStream() );
        MostrarMensaje( "Flujo de cliente listo..." );
    }


    public void EnviarDatos(String msj)
    {
        try
        {
            output.writeObject( "Datos del servidor: " );
            output.flush();

            MostrarMensaje( "Datos enviados..." );
        }
        catch (IOException e)
        {
            areaPantalla.append( "Error al escribir object" );
        }
    }

    public void ProcesarConxion() throws IOException
    {
        String mensaje = "Conexion correcta";
        EnviarDatos(mensaje);
        SetTextBoxEditable( true );
        do {
            try{
                Student student = (Student) input.readObject();
            }
            catch (ClassNotFoundException e)
            {
                System.out.println(e.getCause());
            }
        }while (!mensaje.equals( "" ));
    }

    public void CerrarConexion()
    {
        MostrarMensaje( "Conexion terminada" );
        SetTextBoxEditable( false );

        try
        {
            output.close();
            input.close();
            clientConnection.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void EjecutarServer()
    {
        try
        {
            server = new ServerSocket( 12000,20 );
            while (true)
            {
               try
               {
                   EsperarConexion();
                   EstablecerFlujo();
                   ProcesarConxion();
               }
               catch (IOException e)
               {
                   MostrarMensaje( "Conexion terminada" );
               }
               finally {
                   CerrarConexion();
               }
            }
        }
        catch (Exception e)
        {
            System.out.println("Hubo un macaneo en el servidor...");
            e.printStackTrace();
        }
    }

    public void MostrarMensaje(final String msj)
    {
        SwingUtilities.invokeLater( ()-> areaPantalla.append( "\n"+msj ));
    }

    public synchronized void SetTextBoxEditable(final boolean isEditable)
    {
        SwingUtilities.invokeLater(()-> CampoText.setEditable( isEditable ) );
    }
    public static void main(String... args)
    {
        new Server().EjecutarServer();
    }

    Thread thread;
    @Override
    public void run() {

    }
}
