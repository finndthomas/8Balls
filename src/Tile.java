public class Tile {

    String tileID = "0z";
    boolean[] occupied = {false,false,false}; //[0]=Crop,[1]=Animal,[2]=Property
    int[] tilePosition = {0,0}; //[0]=X,[1]=Y
    String tileResourceName = "Default";
    String tilePicture = "resources/tile_images/"+tileResourceName+".png";

    int dayCountdown = 0;
    int foodPayout = 0;
    int cashPayout = 0;
    int upgradeLevel = 0;
    int season = 0; //1 Summer, 2 Autumn, 3 Winter, 4 Spring


}
