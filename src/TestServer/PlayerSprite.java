package TestServer;



import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;

public class PlayerSprite implements Serializable
{
    private int x, y , size;
    private Color color;
    private String ruta;
    private ImageIcon Icon;
    private Image Image;

    ArrayList<PlayerSprite> listaSprites;
    public PlayerSprite(int x, int y, int si, Color c)
    {
        this.x = x;
        this.y = y;
        size = si;
        color  = c;

        listaSprites = new ArrayList<>(  );
        listaSprites.add( new PlayerSprite( 0,3,"/" ) );
    }


    private int Width;
    private int Height;
    public PlayerSprite(int a, int b,String ruta)
    {
        x = a;
        y = b;

       Icon = new ImageIcon( ruta );
       Image = Icon.getImage();
       Width = Icon.getIconWidth();
       Height = Icon.getIconHeight();

    }

    public void DrawImage(Graphics e)
    {
        e.drawImage( Image,x,y,Width,Height,null );
    }



    public void drawSprite(Graphics2D g)
    {
        Rectangle2D.Double cuadrado = new Rectangle2D.Double( x,y,size,size );
         g.setColor( color );
         g.fill(cuadrado);

    }

    public void moveX(double n)
    {
        x += n;
    }

    public void moveY(int n)
    {
         y += n;
    }

    public void setX(int n)
    {
        x = n;
    }

    public void setY(int n)
    {
        y = n;
    }

    public int getX()
    {
        return x ;
    }

    public int getY()
    {
        return y ;
    }


}
