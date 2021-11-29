import nl.saxion.app.SaxionApp;

public class Tile {

    String tileID = "0z";
    boolean[] occupied = {false,false}; //[0]=Locked,[1]=Occupied
    int[] tilePosition = {0,0}; //[0]=X,[1]=Y
    String tileResourceName = "Default";
    String tilePicture = "resources/tile_images/"+tileResourceName+".png";

    int dayCountdown = 0;
    int cashPayout = 0;
    int upgradeLevel = 0;
    int season = 0; //1 Summer, 2 Autumn, 3 Winter, 4 Spring
    int level = 1;

    public void drawTile(int drawLevel){
        if (level <= drawLevel) {
            SaxionApp.drawImage(tilePicture, tilePosition[0], tilePosition[1], 135, 85);
            SaxionApp.drawText(tileID, tilePosition[0] + 64, tilePosition[1] + 32, 12);//just to see tile name
        }
    }


}
