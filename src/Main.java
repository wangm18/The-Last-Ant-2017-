import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main{


    public static Data data;
    public static String gameState;
    public static island_music_x bgmusic = new island_music_x();
    public static Thread t = new moveAI();

    public static void main(String[] args) {
        if (data.SHOW_TITLE){
            gameState = "title";
        }else{
            gameState = "play";
        }
        data = new Data();

        bgmusic.loop();
        Renderer.init();

        t.start();
        data.playerAnt.moveAnt(39);
    }

    public static void endGame(String condition){ //End game condition: data.foodNum == 0. Checked at the end of data.playerAnt.moveAnt()
                                                  //Condition can either be "win" or "lose"
        bgmusic.stop();
        data.aiAnt = new AiAnt[0];
        if (condition == "win"){
            gameState = "win";
            new Fanfare().play();
        }else if (condition == "starve"){
            new boo().play();
            gameState = "starve";
        }else if (condition == "killed"){
            gameState = "killed";
            new boo().play();
        }

    }
    public static void quit(){
        System.exit(0);
    }




}
