package App;

import Img.StandarImage;
import Serialization.Client;

import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente
{

    public static void main(String... args)
    {
        var x = "GotenPlayer";
        System.out.println(x.substring( 1 ));
    }

    int w = 1000, h = 500;

    Escenario escenario;

    ServerSocket localServer;
    public Cliente()
    {
        System.out.println("Iniciando servicio..."+"\nEsperando por clientes...");
        JFrame frame = new JFrame( "Clieinte" );
        frame.setSize( w,h );

        escenario = new Escenario();
        frame.add(escenario);
        frame.setVisible( true );
    }

    StandarImage persona, enemigo;
    public class Escenario extends JPanel
    {

        public Escenario()
        {
            setSize( w,h );
            setFocusable( true );
            setVisible( true );
            setBackground( Color.red );
            persona = new StandarImage(  "src/Img/1.png",0,0);
            enemigo = new StandarImage( "src/Img/2.png" ,200,0);

        }
        public void paint(Graphics g)
        {
            super.paint( g );
            persona.PaintImage( g );
            if(enemigo!=null)
            {
                //enemigo.PaintImage( g );
            }
        }
    }
}
