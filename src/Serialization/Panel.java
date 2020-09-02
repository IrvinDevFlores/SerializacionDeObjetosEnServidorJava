package Serialization;

import Img.SpriteSheet;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel
{

    public SpriteSheet personaje;
    public Panel(JFrame parentFrame)
    {
        setSize(parentFrame.getWidth(),parentFrame.getHeight() );
        setFocusable( true );
        setVisible( true );
    }

    public void paintPanel(Graphics g)
    {
        super.paint( g );

    }
}
