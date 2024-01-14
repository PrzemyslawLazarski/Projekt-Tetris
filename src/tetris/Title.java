package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Title extends JPanel implements KeyListener {

    private static final long serialVersionUID = 1L;
    private BufferedImage instructions;
    private WindowGame window;
    private BufferedImage[] playButton = new BufferedImage[2];
    private Timer timer;


    public Title(WindowGame window){

        timer = new Timer(1000/60, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }

        });
        timer.start();
        this.window = window;



    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, WindowGame.WIDTH, WindowGame.HEIGHT);



        g.setColor(Color.BLACK);
        g.drawString("Press space to start!", 150, WindowGame.HEIGHT/2);


    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_SPACE) {
            window.startTetris();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
