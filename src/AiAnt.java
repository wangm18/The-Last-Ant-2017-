import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AiAnt extends Ant {

    private int eggTimer;
    private boolean hidden;

    public AiAnt(int xIn, int yIn){
        super(xIn, yIn);
        eggTimer = Main.data.DEFAULT_EGG_TIMER;
        hidden = true;
    }

    public void moveAnt(){
        if (eggTimer > 0){
            if (!hidden) eggTimer--;
        }
        if (!isEgg()){
            //ai ant movement (can only move through a tunnel, seeks out player)
            boolean l = false;
            boolean u = false;
            boolean r = false;
            boolean d = false;

            if (x != 0)
                l = Main.data.tileMap.getTile(x - 1, y).getisAvailable();
            if (y != 0)
                u = Main.data.tileMap.getTile(x, y - 1).getisAvailable();
            if (x != Main.data.TILE_WIDTH - 1)
                r = Main.data.tileMap.getTile(x + 1, y).getisAvailable();
            if (y != Main.data.TILE_HEIGHT - 1)
                d = Main.data.tileMap.getTile(x, y + 1).getisAvailable();

            String move = "stay";

            Random xD = new Random();


            if (xD.nextInt(1000) < Main.data.RANDOMNESS){
                int i = 0;
                while (i < 100 && move == "stay"){
                    int randNum = xD.nextInt(4);
                    if (randNum == 0 && l == true)
                        move = "l";
                    else if (randNum == 1 && u == true)
                        move = "u";
                    else if (randNum == 2 && r == true)
                        move = "r";
                    else if (randNum == 3 && d == true)
                        move = "d";
                    i++;
                }
            }else{
                int xDistanceToPlayer = Main.data.playerAnt.getx() - x;
                int yDistanceToPlayer = Main.data.playerAnt.gety() - y;


                String[] rank = {"stay", "stay", "stay", "stay"};
                if (Math.abs(xDistanceToPlayer) > Math.abs(yDistanceToPlayer)){
                    if (xDistanceToPlayer > 0){
                        rank[0] = "r";
                        rank[3] = "l";
                    }else{
                        rank[0] = "l";
                        rank[3] = "r";
                    }
                    if (yDistanceToPlayer > 0){
                        rank[1] = "d";
                        rank[2] = "u";
                    }else{
                        rank[1] = "u";
                        rank[2] = "d";
                    }
                }else{
                    if (yDistanceToPlayer > 0){
                        rank[0] = "d";
                        rank[3] = "u";
                    }else{
                        rank[0] = "u";
                        rank[3] = "d";
                    }
                    if (xDistanceToPlayer > 0){
                        rank[1] = "r";
                        rank[2] = "l";
                    }else{
                        rank[1] = "l";
                        rank[2] = "r";
                    }
                }

                move = "stay";

                for (int i = 3 ; i >=0 ; i--){
                    if (rank[i] == "l" && l){
                        move = "l";
                    }else if (rank[i] == "u" && u){
                        move = "u";
                    }else if (rank[i] == "r" && r){
                        move = "r";
                    }else if (rank[i] == "d" && d){
                        move = "d";
                    }
                }
            }


            Main.data.tileMap.getTile(x, y).occupied = false;
            switch(move){
                case "l":
                    x--;
                    orientation = 0;
                    break;
                case "u":
                    y--;
                    orientation = 1;
                    break;
                case "r":
                    x++;
                    orientation = 2;
                    break;
                case "d":
                    y++;
                    orientation = 3;
                    break;
                default:
            }

            Main.data.tileMap.getTile(x, y).occupied = true;
        }
    }

    public void draw(Graphics g){ //draws the ant as a humble little gray square.
        if (!hidden){
            if (isEgg()){
                int hue = 255/Main.data.DEFAULT_EGG_TIMER * eggTimer; //goes from white to yellow
                g.setColor(new Color(255, 255, hue));
            }else{
                g.setColor(Color.RED);
            }
            g.fillRect(x * Main.data.TILE_SIZE, y * Main.data.TILE_SIZE, Main.data.TILE_SIZE, Main.data.TILE_SIZE);
        }
    }
    public void draw(Graphics g, BufferedImage[] antImages, BufferedImage[] eggImages){
        if (!hidden){
            if (!isEgg()){
                draw(g, antImages);
            }else{
                int eggNum = 4 - eggTimer/5;
                g.drawImage(eggImages[eggNum], x*Main.data.TILE_SIZE, y*Main.data.TILE_SIZE, null);
            }
        }
    }

    public void found(){
        hidden = false;
    }
    public boolean isEgg(){
        return eggTimer > 0;
    }
}
