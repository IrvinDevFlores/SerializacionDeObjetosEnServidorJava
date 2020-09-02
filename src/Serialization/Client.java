package Serialization;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends JFrame
{
    JTextField CampoIntroducir;
    JTextArea AreaPantalla;
    String Mensaje = "";
    String ServidorChat;

    Socket serverConnection = null;
    ObjectInputStream input = null;
    ObjectOutputStream output = null;

    public Client(String maquina)
    {

        super("Cliente");
        ServidorChat = maquina;

        CampoIntroducir = new JTextField(  );
        CampoIntroducir.setEditable( false );
        CampoIntroducir.addActionListener( e -> {
            EnviarDatos(e.getActionCommand());
            CampoIntroducir.setText( "" );
        } );

        add(CampoIntroducir, BorderLayout.NORTH );

        AreaPantalla = new JTextArea(  );
        add(new JScrollPane( AreaPantalla ));
        setSize( 300,150 );
        setVisible( true );
    }


    public void EjecutarCliente()
    {
        try
        {
            ConectarAlServidor();
            ObtenerFlujoDatos();
            ProcesarConexion();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            CerrarConexion();
        }
    }


    private void ConectarAlServidor() throws IOException
    {
        MostrarMensaje( "Realizando conexion..." );
        serverConnection = new Socket( InetAddress.getByName( ServidorChat ),12000 );
        MostrarMensaje( "Cliente conectado "  + serverConnection.getInetAddress());
    }


    private void ObtenerFlujoDatos() throws IOException
    {
        output = new ObjectOutputStream( serverConnection.getOutputStream() );
        output.flush();

        input = new ObjectInputStream( serverConnection.getInputStream() );
        MostrarMensaje( "Flujos del servidor listos..." );
    }

    private void ProcesarConexion() throws IOException
    {
        SetTextBoxEditable( true );

        do {
            try
            {
                Mensaje = (String) input.readObject();
                MostrarMensaje(Mensaje);
            }
            catch (ClassNotFoundException e)
            {
                System.out.println(e);
            }
        }while(!Mensaje.equals(""));
    }

    private void CerrarConexion() {
        MostrarMensaje( "Ccerrando conexion" );
        SetTextBoxEditable( false );

        try
        {
            output.close();
            input.close();
            serverConnection.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void EnviarDatos(String men)
    {
        try
        {
            output.writeObject(null);
            output.flush();

        }catch (IOException e)
        {
            AreaPantalla.append( "Error al escribir" );
        }
    }

    public synchronized void SetTextBoxEditable(final boolean isEditable)
    {
        SwingUtilities.invokeLater(()-> CampoIntroducir.setEditable( isEditable ) );
    }


    public void MostrarMensaje(String men)
    {
        SwingUtilities.invokeLater( ()-> AreaPantalla.append("\n"+ men ) );
    }

    public static void main(String... args)
    {
        new Client("127.0.0.1").EjecutarCliente();
    }
}
