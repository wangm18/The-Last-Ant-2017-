import static java.lang.Thread.sleep;

public class moveAI extends Thread
{

    @Override
    public void run()
    {
        while(true) {
            for (int i = 0 ; i < Main.data.aiAnt.length ; i++) {
                Main.data.aiAnt[i].moveAnt();
                if (!Main.data.aiAnt[i].isEgg() &&
                        Main.data.aiAnt[i].getx() == Main.data.playerAnt.getx() && Main.data.aiAnt[i].gety() == Main.data.playerAnt.gety()) { //ai and player occupy same space
                    Main.endGame("killed");
                }
            }
            try{
                Thread.sleep(200);
            }catch (InterruptedException e){
                System.out.println("Interrupted exception caught: " + e.getMessage());
            }
        }
    }
}