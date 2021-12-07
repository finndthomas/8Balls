import nl.saxion.app.SaxionApp;

public class Tile {

    String tileID = "0z";
    boolean[] occupied = {false,false}; //[0]=Locked,[1]=Occupied
    int[] tilePosition = {0,0}; //[0]=X,[1]=Y
    String tileResourceName = "Default";

    int dayCountdown = 0;
    int cashPayout = 0;
    int foodPayout = 0;
    int[] cropRequirement = {0,0};
    int[] cropQuantity = {0,0};
    int upgradeLevel = 0;
    int season = 0; //1 Summer, 2 Autumn, 3 Winter, 4 Spring
    int level = 1;
    Crop crop = null; //Jacques. The tile will draw the crop if he has one. "null" means he has no crop yet

    public void drawTile(int drawLevel) {
        if (level <= drawLevel) {
            if (crop == null) {
                drawDefault();
            } else if (dayCountdown <= 0) {
                SaxionApp.drawImage(crop.finalImage, tilePosition[0], tilePosition[1], 135, 85);
            } else if (dayCountdown <= Math.round(50.0*crop.dayCountdown/100)) {
                SaxionApp.drawImage(crop.step1Image, tilePosition[0], tilePosition[1], 135, 85);
            } else
                SaxionApp.drawImage(crop.seededImage, tilePosition[0], tilePosition[1], 135, 85);

            SaxionApp.drawText(tileID, tilePosition[0] + 64, tilePosition[1] + 32, 12);//just to see tile name
        }
    }

    private void drawDefault() {
        SaxionApp.drawImage("resources\\crops images\\default.png", tilePosition[0], tilePosition[1], 135, 85);
    }


}
