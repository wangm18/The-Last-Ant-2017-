import java.util.Random;

public class Data {
    public static final boolean SHOW_TITLE = false;
    public static final int TITLE_TIME = 7; //displays title for TITLE_TIME seconds
    //displays title for TITLE_TIME seconds
    public static final int SWITCH_TIME = 10;

    //screen is 32 x 24 tiles
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 16;
    public static final int TILE_SIZE = 32;

    public static final int SCREEN_WIDTH = TILE_WIDTH * TILE_SIZE;
    public static final int SCREEN_HEIGHT = TILE_HEIGHT * TILE_SIZE + 50;

    public static final int CHANCE_OF_FOOD = 100; //chance out of 1000
    public static final int ENEMY_NUM = 20;
    public static final int DEFAULT_EGG_TIMER = 20; //amount of turns until an aiAnt hatches upon being discovered
    public static final int RANDOMNESS = 500; //chance out of 1000

    public static final int FOOD_ENERGY = 50;
    public static final int DIG_ENERGY = 10;
    public static final int NONDIG_ENERGY = 1;


    public static int foodNum;

    public Ant playerAnt;
    public AiAnt[] aiAnt = new AiAnt[ENEMY_NUM];
    public TileMap tileMap;

    public Data(){
        foodNum = 0;
        playerAnt = new Ant();
        tileMap = new TileMap();

        Random xD = new Random();
        for (int i = 0 ; i < aiAnt.length ; i++){
            int x = xD.nextInt(TILE_WIDTH);
            int y = xD.nextInt(TILE_HEIGHT);
            //System.out.println("(" + x + ", " + y + ")"); //shows where the eggs have spawned
            aiAnt[i] = new AiAnt(x, y);
        }
    }




}
