package Img;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public  class StandarImage extends JPanel
{
    ImageIcon icon;
   Image Image;

   int Width;
   int Heigth;

   int x, y;
    public StandarImage(String ruta, int x, int y)
    {
        this.x = x;
        this.y = y;

        icon = new ImageIcon( ruta );
        Image = icon.getImage();

        Width = icon.getIconWidth();
        Heigth = icon.getIconHeight();

        GenerateImage( Image );
    }

    public void PaintImage(Graphics g) {
        super.paint( g );

        g.drawImage( Image,x,y,Width,Heigth,null );

    }

    public BufferedImage GetImage()
    {
        return ConvertedImage;
    }

    BufferedImage ConvertedImage;
    public void GenerateImage(Image img)
    {
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(Width, Heigth, BufferedImage.TYPE_INT_ARGB);
        // Return the buffered image
        ConvertedImage = bimage;
    }


}
