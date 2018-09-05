import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

public class Ant {
    //Ant's x and y coordinates are integers from 0-31 and 0-23, respectively.
    protected int x;
    protected int y;
    protected int energy = 1000;
    protected int maxEnergy;
    protected int orientation; //0 = left | 1 = up | 2 = right | 3 = down


    public Ant(){
        x = 0;
        y = 0;

        maxEnergy = energy;
        orientation = 2;
    }


    public Ant(int xIn, int yIn){
        x = xIn;
        y = yIn;

        maxEnergy = energy;
        orientation = 2;
    }

    public void draw(Graphics g){ //draws the ant as a humble little gray square.
        g.setColor(Color.GRAY);
        g.fillRect(x * Main.data.TILE_SIZE, y * Main.data.TILE_SIZE, Main.data.TILE_SIZE, Main.data.TILE_SIZE);
    }

    public void draw(Graphics g, BufferedImage image){
        g.drawImage(image, x*Main.data.TILE_SIZE, y*Main.data.TILE_SIZE, null);
    }
    public void draw(Graphics g, BufferedImage[] images){
        draw(g, images[orientation]);
    }


    //moves the ant based on the keycode 'k' entered. Checks for walls.
    //Updates info based on tilemap
    public void moveAnt(int k){
        boolean moved = false;

        if (k == 37){
            if (x != 0){
                x += -1;
                moved = true;
                orientation = 0;
            }
        }else if (k == 38){
            if (y != 0){
                y += -1;
                moved = true;
                orientation = 1;
            }
        }else if (k == 39){
            if (x != Main.data.TILE_WIDTH - 1){
                x += 1;
                moved = true;
                orientation = 2;
            }
        }else{
            if (y != Main.data.TILE_HEIGHT - 1){
                y += 1;
                moved = true;
                orientation = 3;
            }
        }


        if (moved){
            Tile steppedOn = Main.data.tileMap.getTile(x, y);
            for (int i = 0 ; i < Main.data.aiAnt.length ; i++) {
                if (!Main.data.aiAnt[i].isEgg() &&
                        Main.data.aiAnt[i].getx() == Main.data.playerAnt.getx() && Main.data.aiAnt[i].gety() == Main.data.playerAnt.gety()) { //ai and player occupy same space
                    Main.endGame("killed");
                }
            }
            if (steppedOn.gethasFood()){
                energy += Main.data.FOOD_ENERGY;
                new chomp().play();
            }else {
                new boing_x().play();
            }
            if (steppedOn.getisDug()){
                energy -= Main.data.NONDIG_ENERGY;
            }else{
                energy -= Main.data.DIG_ENERGY;
            }
            for (int i = 0 ; i < Main.data.aiAnt.length ; i++){
                if (Main.data.aiAnt[i].getx() == x && Main.data.aiAnt[i].gety() == y){ //player occupies same tile as an enemy
                    if (Main.data.aiAnt[i].isEgg()){
                        Main.data.aiAnt[i].found();
                    }else{
                        //YOU GETTIN FUCCCED UP BY AN ENEMY
                    }
                }
            }
            Main.data.tileMap.playerSteppedOn(x, y); //alerts the tilemap class that the player has stepped on a certain tile

            if (Main.data.foodNum == 0){
                Main.endGame("win");
            }
            if (energy <= 0){
                Main.endGame("stave");
            }
        }


        if (energy > maxEnergy){
            maxEnergy = energy;
        }
    }


    public int getx(){
        return x;
    }
    public int gety(){
        return y;
    }
    public int getEnergy(){
        return energy;
    }
    public int getMaxEnergy(){
        return maxEnergy;
    }
}
