import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static java.awt.Font.PLAIN;

public class Renderer {

    public static boolean running = true;

    private static Frame frame;
    private static Canvas canvas;


    private static BufferedImage antImage;
    private static BufferedImage undugTileImage;
    private static BufferedImage dugTileImage;
    private static BufferedImage foodImage;
    private static BufferedImage upAntImage;
    private static BufferedImage downAntImage;
    private static BufferedImage rightAntImage;
    private static BufferedImage leftAntImage;
    private static BufferedImage upEnemyAntImage;
    private static BufferedImage downEnemyAntImage;
    private static BufferedImage rightEnemyAntImage;
    private static BufferedImage leftEnemyAntImage;
    private static BufferedImage egg1Image;
    private static BufferedImage egg2Image;
    private static BufferedImage egg3Image;
    private static BufferedImage egg4Image;
    private static BufferedImage egg5Image;

    private static BufferedImage[] antImages = new BufferedImage[4];
    private static BufferedImage[] enemyAntImages = new BufferedImage[4];
    private static BufferedImage[] eggImages = new BufferedImage[5];


    private static URL antURL;
    private static URL undugTileURL;
    private static URL dugTileURL;
    private static URL foodURL;
    private static URL upAntURL;
    private static URL downAntURL;
    private static URL rightAntURL;
    private static URL leftAntURL;
    private static URL upEnemyAntURL;
    private static URL downEnemyAntURL;
    private static URL rightEnemyAntURL;
    private static URL leftEnemyAntURL;
    private static URL egg1URL;
    private static URL egg2URL;
    private static URL egg3URL;
    private static URL egg4URL;
    private static URL egg5URL;



    public static void init(){
        frame = new Frame();
        canvas = new Canvas();

        canvas.setSize(new Dimension(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT));

        frame.add(canvas);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
                                    public void windowClosing(WindowEvent e) {
                                        Main.quit();
                                    }
                                });
        frame.setVisible(true);
        canvas.addKeyListener(new Input());

        createImages();

        startRendering();
    }

    private static void startRendering(){
        Thread thread = new Thread(){
            public void run(){

                GraphicsConfiguration gc = canvas.getGraphicsConfiguration();
                VolatileImage vImage = gc.createCompatibleVolatileImage(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);

                while(running){
                    if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE){
                        vImage = gc.createCompatibleVolatileImage(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);

                    }

                    Graphics g = vImage.getGraphics();
                    // RENDERING OCCURS HERE

                    if (Main.gameState == "play"){
                        paint(g);
                    }else if (Main.gameState == "starve"){
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.RED);
                        g.setFont(myFont());
                        g.drawString("Anthony starved.", Main.data.SCREEN_WIDTH/3, Main.data.SCREEN_HEIGHT/2);
                        g.drawString("You lose :(", Main.data.SCREEN_WIDTH*2/5, Main.data.SCREEN_HEIGHT/2 + 50);
                    }else if (Main.gameState == "win"){
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.GREEN);
                        g.setFont(myFont());
                        g.drawString("Anthony ate all the food.", Main.data.SCREEN_WIDTH/3 - 65, Main.data.SCREEN_HEIGHT/2 - 50);
                        g.drawString("You win! :)", Main.data.SCREEN_WIDTH*2/5, Main.data.SCREEN_HEIGHT/2);
                        g.drawString("Score: " + Main.data.playerAnt.getEnergy(), Main.data.SCREEN_WIDTH/3, Main.data.SCREEN_HEIGHT/2 + 50);
                        g.drawString("Highest energy: " + Main.data.playerAnt.getMaxEnergy(), Main.data.SCREEN_WIDTH/3 - 30, Main.data.SCREEN_HEIGHT/2 + 100);

                    }else if (Main.gameState == "killed"){
                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.RED);
                        g.setFont(myFont());
                        g.drawString("Anthony was killed!.", Main.data.SCREEN_WIDTH/3, Main.data.SCREEN_HEIGHT/2);
                        g.drawString("You lose :(", Main.data.SCREEN_WIDTH*2/5, Main.data.SCREEN_HEIGHT/2 + 50);
                    }else if (Main.gameState == "title"){
                        //start here
                        String firstLine = "The story of Anthony the Ant is a tragic one.";
                        String secondLine = "Originally named Antonia, Anthony's birth as a second queen made ";
                        String thirdLine = "a brutal civil war among her colony inevitable.";
                        String fourthLine = "At first, the retainers of the first queen desired to delay";
                        String fifthLine = "the unavoidable conflict by hiding Antonia";
                        String sixthLine = "and renaming her Anthony. However, when";
                        String seventhLine = "Anthony's queenly wings began to develop,";
                        String eighthLine = "she knew she would be discovered soon.";
                        String ninthLine = "In order to live, she had to kill the incumbent queen.";

                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.white);
                        g.setFont(new Font("Trebuchet MS", PLAIN, 24));
                        g.drawString((firstLine), 180, 30);
                        g.drawString((secondLine), 180, 80);
                        g.drawString((thirdLine), 180, 130);
                        g.drawString((fourthLine), 180, 180);
                        g.drawString((fifthLine), 180, 230);
                        g.drawString((sixthLine), 180, 280);
                        g.drawString((seventhLine), 180, 330);
                        g.drawString((eighthLine), 180, 380);
                        g.drawString((ninthLine), 180, 430);

                        //USE THIS BLOCK BETWEEN SCREENS
                        g.dispose();
                        g = canvas.getGraphics();
                        g.drawImage(vImage, 0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT, null);
                        try {
                            TimeUnit.SECONDS.sleep(Main.data.SWITCH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE){
                            vImage = gc.createCompatibleVolatileImage(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        }
                        g = vImage.getGraphics();
                        //END BLOCK
/*
Upon revealing her identity to the colony and declaring her intention of leading a revolution, the colony immediately broke into two factions.
One side supported the current queen, while the close friends that Anthony had made during her time as a worker, along with those who desired a change
in rulership, supported Anthony.
 */
                        String tenthLine = "Upon revealing her identity to the colony and declaring ";
                        String eleventhLine = "her intention of leading a revolution, ";
                        String twelfthLine = "the colony immediately broke into two factions.";
                        String thirteenthLine = "One side supported the current queen, ";
                        String thirteenthhalfLine = "while the close friends that Anthony had made";
                        String fourteenthLine = "during her time as a worker, along with those who desired a change";
                        String fifteenthLine = "in rulership, supported Anthony.";

                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.white);
                        g.setFont(new Font("Trebuchet MS", PLAIN, 24));
                        g.drawString(tenthLine, 180, 30);
                        g.drawString(eleventhLine, 180, 80);
                        g.drawString(twelfthLine, 180, 130);
                        g.drawString(thirteenthLine, 180, 180);
                        g.drawString(thirteenthhalfLine, 180, 230);
                        g.drawString(fourteenthLine, 180, 280);
                        g.drawString(fifteenthLine, 180, 330);

                        g.dispose();
                        g = canvas.getGraphics();
                        g.drawImage(vImage, 0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT, null);
                        try {
                            TimeUnit.SECONDS.sleep(Main.data.SWITCH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE){
                            vImage = gc.createCompatibleVolatileImage(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        }
                        g = vImage.getGraphics();

 /*
 A bloody civil war ensued, as cousin slaughtered cousin, worker cannabalized worker,
 and the colony quickly disppeared. After traversing through mountains of corpses,
Anthony succeeded in deposing of the former queen. Triumphantly, Anthony retook her name as Antonia.
Yet, upon seizing control of the colony, Anthony realized that she had no colony to rule.
  */

                        String sixteenthLine = "A bloody civil war ensued, as cousin slaughtered cousin,";
                        String seventeenthLine = "worker cannabalized worker, and the colony quickly disappeared.";
                        String eighteenthLine = "After traversing through mountains of corpses,";
                        String nineteenthLine = "Anthony succeeded in deposing of the former queen.";
                        String twentiethLine = "Triumphantly, Anthony retook her name as Antonia.";
                        String twentyfirstLine = "Yet, upon seizing control of the colony, ";
                        String twentysecondLine = "Anthony realized that she had no colony to rule.";

                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.white);
                        g.setFont(new Font("Trebuchet MS", PLAIN, 24));

                        g.drawString(sixteenthLine, 180, 30);
                        g.drawString(seventeenthLine, 180, 80);
                        g.drawString(eighteenthLine, 180, 130);
                        g.drawString(nineteenthLine, 180, 180);
                        g.drawString(twentiethLine, 180, 230);
                        g.drawString(twentyfirstLine, 180,280);
                        g.drawString(twentysecondLine, 180, 330);

                        g.dispose();
                        g = canvas.getGraphics();
                        g.drawImage(vImage, 0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT, null);
                        try {
                            TimeUnit.SECONDS.sleep(Main.data.SWITCH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE){
                            vImage = gc.createCompatibleVolatileImage(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        }
                        g = vImage.getGraphics();
/*
News of the decimation of Antonia's colony spread quickly throughout the front yard,
and enemy colonies quickly moved to take control of the now abandoned tunnels.
Antonia was forced to flee, burrowing her way through unexplored areas of the earth,
never knowing when she may be attacked by enemies while she is searching for food.
 */

                        String twentythirdLine = "News of the decimation of Antonia's colony";
                        String twentyfourthLine = "quickly throughout the front yard, and enemy colonies";
                        String twentyfifthLine = "swiftly moved to take control of the now abandoned tunnels.";
                        String twentysixthLine = "Antonia was forced to flee, burrowing her way";
                        String twentyseventhLine = "through unexplored areas of the earth,never knowing";
                        String twentyeighthLine = "when she may be attacked by enemies while she is searching for food.";

                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.white);
                        g.setFont(new Font("Trebuchet MS", PLAIN, 24));

                        g.drawString(twentythirdLine, 180, 30);
                        g.drawString(twentyfourthLine, 180, 80);
                        g.drawString(twentyfifthLine, 180, 130);
                        g.drawString(twentysixthLine, 180, 180);
                        g.drawString(twentyseventhLine, 180, 230);
                        g.drawString(twentyeighthLine, 180, 280);

                        g.dispose();
                        g = canvas.getGraphics();
                        g.drawImage(vImage, 0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT, null);
                        try {
                            TimeUnit.SECONDS.sleep(Main.data.SWITCH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE){
                            vImage = gc.createCompatibleVolatileImage(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        }
                        g = vImage.getGraphics();
/*
Help Antonia scavenge for food and survive the desolate wilderness of the underground, while avoiding the enemy worker ants.
 */
                        String twentyninthLine = "Help Antonia scavenge for food and survive the desolate wilderness of the ";
                        String thirtiethLine = "underground, while avoiding the enemy worker ants.";

                        g.setColor(Color.BLACK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.white);
                        g.setFont(new Font("Trebuchet MS", PLAIN, 24));

                        g.drawString(twentyninthLine, 100, 160);
                        g.drawString(thirtiethLine, 100, 210);

                        //clear function
                        g.dispose();
                        g = canvas.getGraphics();
                        g.drawImage(vImage, 0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT, null);
                        try {
                            TimeUnit.SECONDS.sleep(Main.data.SWITCH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (vImage.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE){
                            vImage = gc.createCompatibleVolatileImage(Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        }
                        g = vImage.getGraphics();

                        //title page
                        g.setColor(Color.PINK);
                        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT);
                        g.setColor(Color.green);
                        g.setFont(titleFont());
                        g.drawString("THE LAST ANT:", 10, 100);
                        g.drawString("ANTONIA'S ADVENTURE", 10, 200);

                        //don't code past this
                        g.dispose();
                        g = canvas.getGraphics();
                        g.drawImage(vImage, 0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT, null);

                        try {
                            TimeUnit.SECONDS.sleep(Main.data.SWITCH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Main.gameState = "play";

                    }

                    //DONE RENDERING
                    g.dispose();

                    g = canvas.getGraphics();
                    g.drawImage(vImage, 0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT, null);

                    g.dispose();

                }
            }
        };
        thread.setName("Rendering Thread");
        thread.start();
    }

    //This is the class in which everything will be painted. HERE IS WHERE YOU SHOULD EDIT STUFF
    public static void paint(Graphics g){
        //fills background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Main.data.SCREEN_WIDTH, Main.data.SCREEN_HEIGHT - 100);

        //Draw the tile map:
        //Main.data.tileMap.drawTileMap(g);
        Main.data.tileMap.drawTileMap(g, dugTileImage, undugTileImage, foodImage);

        //Draw the ant:
        //Main.data.playerAnt.draw(g, loadImage(Main.data.playerAnt.getImageURL()));
        Main.data.playerAnt.draw(g, antImages);

        //Draw the AI ants
        for (int i = 0 ; i < Main.data.aiAnt.length ; i++){
            Main.data.aiAnt[i].draw(g, enemyAntImages, eggImages);
        }

        //draw grid
        /*
        g.setColor(Color.BLACK);
        for (int i = 0 ; i <= 32 ; i++){
            g.drawLine(i*32, 0,i*32, Main.data.SCREEN_HEIGHT - 100);
        }
        for (int i = 0 ; i <= 24 ; i++){
            g.drawLine(0, i * 32, Main.data.SCREEN_WIDTH, i*32);
        }
        */

        //Draw energy
        g.setColor(Color.black);
        g.fillRect(0, Main.data.SCREEN_HEIGHT - 50, Main.data.SCREEN_WIDTH, 50);
        g.setColor(Color.YELLOW);
        g.setFont(myFont());
        g.drawString("Energy: " + Main.data.playerAnt.getEnergy(), 5, Main.data.SCREEN_HEIGHT - 12);
        g.setColor(Color.GREEN);
        g.drawString("Highest energy: " + Main.data.playerAnt.getMaxEnergy(), Main.data.SCREEN_WIDTH/2, Main.data.SCREEN_HEIGHT - 12);

        /*
        g.setColor(Color.black);
        g.fillRect(0, Main.data.SCREEN_HEIGHT - 50, Main.data.SCREEN_WIDTH, 50);
        g.setColor(Color.YELLOW);
        g.fillRect(5, Main.data.SCREEN_HEIGHT - 45,
                Math.round(Math.round(((Main.data.SCREEN_WIDTH - 10) * (1.0 * Main.data.playerAnt.getEnergy())/1000))), 40);
        */
    }

    public static BufferedImage loadImage (String path) throws IOException {

        BufferedImage rawImage = ImageIO.read(Renderer.class.getResource(path));
        BufferedImage finalImage = canvas.getGraphicsConfiguration()
                .createCompatibleImage(rawImage.getWidth(), rawImage.getHeight(),
                rawImage.getTransparency());

        return finalImage;
    }

    public static BufferedImage loadImage (URL url){
        BufferedImage rawImage = null;
        try{
            rawImage = ImageIO.read(url);
        }catch (IOException e){
            System.err.println("Caught IO Exception: " + e.getMessage());
        }

        /*BufferedImage finalImage = canvas.getGraphicsConfiguration()
                .createCompatibleImage(rawImage.getWidth(), rawImage.getHeight(),
                        rawImage.getTransparency());
        */

        return rawImage;
    }


    private static void createImages(){
        try{
            undugTileURL = new URL("https://i.imgur.com/NpOnq4s.png");
            dugTileURL = new URL("https://i.imgur.com/km9QfVl.png");
            antURL = new URL("https://i.imgur.com/lnvJ8sS.png");
            foodURL = new URL("https://i.imgur.com/ugHbUMM.png");
            upAntURL = new URL("https://i.imgur.com/krjrp7N.png");
            downAntURL = new URL("https://i.imgur.com/A6AXYBW.png");
            rightAntURL = new URL("https://i.imgur.com/bgYADSM.png");
            leftAntURL = new URL("https://i.imgur.com/e977lvP.png");
            upEnemyAntURL = new URL("https://i.imgur.com/FLBNN2f.png");
            downEnemyAntURL = new URL("https://i.imgur.com/TTmrDXm.png");
            rightEnemyAntURL = new URL("https://i.imgur.com/5Y9Y6NT.png");
            leftEnemyAntURL = new URL("https://i.imgur.com/IH59xKo.png");
            egg1URL = new URL("https://i.imgur.com/7YqQC71.png");
            egg2URL = new URL("https://i.imgur.com/jAGA89m.png");
            egg3URL = new URL("https://i.imgur.com/IPOuFhy.png");
            egg4URL = new URL("https://i.imgur.com/zDsfN5Y.png");
            egg5URL = new URL("https://i.imgur.com/IDuUhLX.png");

        }catch (MalformedURLException e){
            System.err.println("Caught Malformed URL Exception: " + e.getMessage());
        }

        /*
https://i.imgur.com/krjrp7N.png upAnt
https://i.imgur.com/A6AXYBW.png downAnt
https://i.imgur.com/bgYADSM.png rightAnt
https://i.imgur.com/e977lvP.png leftAnt
https://i.imgur.com/FLBNN2f.png upEnemyAnt
https://i.imgur.com/TTmrDXm.png downEnemyAnt
https://i.imgur.com/5Y9Y6NT.png rightEnemyAnt
https://i.imgur.com/IH59xKo.png leftEnemyAnt
https://i.imgur.com/7YqQC71.png Egg1
https://i.imgur.com/jAGA89m.png Egg2
https://i.imgur.com/IPOuFhy.png Egg3
https://i.imgur.com/zDsfN5Y.png Egg4
https://i.imgur.com/IDuUhLX.png Egg5
         */

        antImage = loadImage(antURL);
        undugTileImage = loadImage(undugTileURL);
        dugTileImage = loadImage(dugTileURL);
        foodImage = loadImage(foodURL);
        upAntImage = loadImage(upAntURL);
        downAntImage = loadImage(downAntURL);
        rightAntImage = loadImage(rightAntURL);
        leftAntImage = loadImage(leftAntURL);
        upEnemyAntImage = loadImage(upEnemyAntURL);
        downEnemyAntImage = loadImage(downEnemyAntURL);
        rightEnemyAntImage = loadImage(rightEnemyAntURL);
        leftEnemyAntImage = loadImage(leftEnemyAntURL);
        egg1Image = loadImage(egg1URL);
        egg2Image = loadImage(egg2URL);
        egg3Image = loadImage(egg3URL);
        egg4Image = loadImage(egg4URL);
        egg5Image = loadImage(egg5URL);

        antImages[0] = leftAntImage;
        antImages[1] = upAntImage;
        antImages[2] = rightAntImage;
        antImages[3] = downAntImage;
        enemyAntImages[0] = leftEnemyAntImage;
        enemyAntImages[1] = upEnemyAntImage;
        enemyAntImages[2] = rightEnemyAntImage;
        enemyAntImages[3] = downEnemyAntImage;
        eggImages[0] = egg1Image;
        eggImages[1] = egg2Image;
        eggImages[2] = egg3Image;
        eggImages[3] = egg4Image;
        eggImages[4] = egg5Image;
    }

    private static Font myFont(){
    //Font(String name, int style, int size)
        return new Font("Trebuchet MS", PLAIN, 40);
    }

    private static Font titleFont(){
        //Font(String name, int style, int size)
        return new Font("Comic Sans MS", PLAIN, 80);
    }


    private void titleSequence(){

    }
}
