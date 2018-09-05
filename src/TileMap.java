import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.Graphics;

public class TileMap
{
    private int x_size = Main.data.TILE_WIDTH;
    private int y_size = Main.data.TILE_HEIGHT;
    private Tile[][] map = new Tile[x_size][y_size];

    public TileMap(){
        for( int x=0; x < x_size; x++ ){
            for(int y=0; y < y_size; y++){
                map[x][y] = new Tile(x,y);
            }
        }
    }
    public void drawTileMap(Graphics g)
    {
        for( int x=0; x < x_size; x++ ){
            for(int y=0; y < y_size; y++){
                map[x][y].drawTile(g);
            }
        }
    }
    public void drawTileMap(Graphics g, BufferedImage dugImage, BufferedImage undugImage, BufferedImage foodImage)
    {
        for( int x=0; x < x_size; x++ ){
            for(int y=0; y < y_size; y++){
                map[x][y].drawTile(g, dugImage, undugImage, foodImage);
            }
        }
    }

    public Tile getTile(int x, int y){
        return map[x][y];
    }

    public void playerSteppedOn(int x, int y){ //called when the player moves onto the tile
        map[x][y].playerSteppedOn();
    }
}