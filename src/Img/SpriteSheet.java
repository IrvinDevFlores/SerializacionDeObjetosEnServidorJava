package Img;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    //Instance Variables
    private String path;
    private int frameWidth;
    private int frameHeight;
    private BufferedImage sheet = null;
    private BufferedImage[] frameImages;

    //Constructors
    public SpriteSheet(String aPath, int width, int height) {

        path = aPath;
        frameWidth = width;
        frameHeight = height;

        try {
            sheet = ImageIO.read( getClass().getResourceAsStream( path ) );
            frameImages = getAllSprites();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedImage getSprite(int frame) {
        return frameImages[frame];
    }

    //Methods
    public int getHeight() {
        return frameHeight;
    }

    public int getWidth() {
        return frameWidth;
    }

    public int getColumnCount() {
        return sheet.getWidth() / getWidth();
    }

    public int getRowCount() {
        return sheet.getHeight() / getHeight();
    }

    public int getFrameCount() {
        int cols = getColumnCount();
        int rows = getRowCount();
        return cols * rows;
    }

    private BufferedImage getSprite(int x, int y, int h, int w) {
        BufferedImage sprite = sheet.getSubimage( x, y, h, w );
        return sprite;
    }

    public BufferedImage[] getAllSprites() {
        int cols = getColumnCount();
        int rows = getRowCount();
        int frameCount = getFrameCount();
        BufferedImage[] sprites = new BufferedImage[frameCount];
        int index = 0;
        System.out.println( "cols = " + cols );
        System.out.println( "rows = " + rows );
        System.out.println( "frameCount = " + frameCount );
        for (int row = 0; row < getRowCount(); row++) {
            for (int col = 0; col < getColumnCount(); col++) {
                int x = col * getWidth();
                int y = row * getHeight();
                System.out.println( index + " " + x + "x" + y );
                BufferedImage currentSprite = getSprite( x, y, getWidth(), getHeight() );
                sprites[index] = currentSprite;
                index++;
            }
        }
        return sprites;

    }
}