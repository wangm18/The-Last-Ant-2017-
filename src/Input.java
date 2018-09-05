import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    public Input(){

    }


    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        //System.out.println("Released: " + e.getKeyCode());
        if (e.getKeyCode() >= 37 && e.getKeyCode() <= 40 && Main.gameState.equals("play")){
            Main.data.playerAnt.moveAnt(e.getKeyCode());



        }
    }

    //Key codes:

    /*
    37 == left arrow
    38 == up arrow
    39 == right arrow
    40 == down arrow
     */
}
