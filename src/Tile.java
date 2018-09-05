import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.swing.*;
public class Tile
{
    private int x;
    private int y;
    private boolean isDug;
    private boolean hasFood;
    public boolean occupied;


    public Tile(int xCord, int yCord)
    {
        x = xCord;
        y = yCord;
        isDug = false;
        occupied = false;

        Random xD = new Random();
        hasFood = xD.nextInt(1000) < Main.data.CHANCE_OF_FOOD;
        if (hasFood) Main.data.foodNum++;


    }
    public void drawTile(Graphics g){
        if (!isDug){
            g.setColor(new Color(139, 69, 19));
        }else{
            g.setColor(new Color(210, 184, 135));
        }
        g.fillRect(x * Main.data.TILE_SIZE, y * Main.data.TILE_SIZE, Main.data.TILE_SIZE, Main.data.TILE_SIZE);
    }
    public void drawTile(Graphics g, BufferedImage dugImage, BufferedImage undugImage, BufferedImage foodImage){
        if (isDug){
            g.drawImage(dugImage, x*Main.data.TILE_SIZE, y*Main.data.TILE_SIZE, null);
        }else{
            g.drawImage(undugImage, x*Main.data.TILE_SIZE, y*Main.data.TILE_SIZE, null);
        }

        if (hasFood){
            g.drawImage(foodImage, x*Main.data.TILE_SIZE, y*Main.data.TILE_SIZE, null);
        }
    }

    public void playerSteppedOn(){ //called when the player moves onto this tile
        isDug = true;
        if (hasFood) Main.data.foodNum--;
        hasFood = false;
    }

    public int getx()
    {
        return x;
    }
    public int gety()
    {
        return y;
    }
    public boolean getisDug()
    {
        return isDug;
    }
    public boolean gethasFood()
    {
        return hasFood;
    }
    public boolean getisAvailable(){
        return isDug && !occupied;
    }

}