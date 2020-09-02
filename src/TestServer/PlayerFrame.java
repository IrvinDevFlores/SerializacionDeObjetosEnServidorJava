package TestServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;

public class PlayerFrame extends JFrame
{

    private int Width, Height;
    private Container contentPane;

    private PlayerSprite me;
    private PlayerSprite enemy;
    private DrawableComponent doc;

    private ReadFromServer readFromServerRunna;
    private WriteToServer writeFromServerRunna;

    private boolean down, left, right;
    public PlayerFrame(int wi, int he)
    {
        Width = wi;
        Height = he;
        left = false;
        right = false;
    }

    private Timer animationTimer;
    private void setUpAnimationTimer()
    {
        int speed = 5;
        int invertal = 200;
        ActionListener action = e -> {
             if(playerId == 1)
             {
                 me.moveX( speed );
             }
             else if(playerId == 2)
             {
                 me.moveX( -speed );
             }
            doc.repaint();
        };
        animationTimer = new Timer(invertal,action);
        animationTimer.start();
    }
    KeyListener keysEvents;
    private void setUpKeyListener()
    {
        keysEvents = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if(keyCode == KeyEvent.VK_RIGHT)
                {
                    right = true;
                    System.out.println("Rigt");
                }

                if(keyCode == KeyEvent.VK_LEFT)
                {
                    left = true;
                    System.out.println("Rigt");
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();

            }
        };
        contentPane.setFocusable( true );
        contentPane.addKeyListener( keysEvents );


    }

    public void setUpGUI()
    {
        contentPane = this.getContentPane();
        this.setTitle( "Player " + playerId );
        contentPane.setPreferredSize( new Dimension( Width, Height ) );

        createSprite();
        doc = new DrawableComponent();

        contentPane.add( doc );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        this.pack();
        this.setVisible(true);

        setUpAnimationTimer();
        setUpKeyListener();
    }

    public void createSprite()
    {
        if(playerId == 1)
        {
            me = new PlayerSprite( 100,400,60,Color.RED);
            enemy = new PlayerSprite( 490,400,60,Color.BLUE);
        }
        else if(playerId == 2)
        {
            enemy = new PlayerSprite( 100,400,60,Color.RED);
            me = new PlayerSprite( 490,400,60,Color.BLUE);
        }

    }

    Socket serverConnection;
    public int playerId;
    public void ConnectToServer()
    {
        try
        {
            serverConnection = new Socket( "localhost",45371 );
            DataInputStream input = new DataInputStream( serverConnection.getInputStream() );
            DataOutputStream output = new DataOutputStream( serverConnection.getOutputStream() );

            playerId = input.readInt();
            System.out.println("You are the player: " + playerId);
            if(playerId == 1)
            {
                System.out.println("Waiting for player 2...");
            }

            readFromServerRunna = new ReadFromServer( input );
            writeFromServerRunna = new WriteToServer( output );

            //Ready
            readFromServerRunna.WaitForStartMessage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public class DrawableComponent extends JComponent
    {
        protected void paintComponent(Graphics g)
        {
            enemy.drawSprite( (Graphics2D) g );
           me.drawSprite( (Graphics2D) g );
        }
    }


    private class ReadFromServer implements Runnable
    {
        private DataInputStream input;
        public ReadFromServer(DataInputStream inp)
        {
            input = inp;
            System.out.println("Layendo datos del cliente....");
        }
        public void run()
        {
            try
            {
                while(true)
                {
                    if(enemy!=null)
                    {
                        enemy.setX(input.readInt());
                        enemy.setY(input.readInt());
                    }
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void WaitForStartMessage()
        {
            try
            {
                String stMess = input.readUTF();
                System.out.println("Message from server: "+stMess);

                Thread readThread = new Thread( readFromServerRunna );
                Thread writeThread = new Thread( writeFromServerRunna );
                readThread.start();
                writeThread.start();

            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class WriteToServer implements Runnable
    {
        private DataOutputStream output;

        public WriteToServer(DataOutputStream out)
        {
            output = out;

            System.out.println("write from server runnable creater");
        }
        public void run()
        {
            try
            {
                while (true)
                {
                    if(me!=null)
                    {
                        output.writeInt(me.getX() );
                        output.writeInt(me.getY() );
                        output.flush();
                    }
                    try
                    {
                        Thread.sleep( 25 );
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}
