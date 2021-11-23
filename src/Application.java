import nl.saxion.app.CsvReader;
import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Application implements Runnable {

    public static void main(String[] args) {
        SaxionApp.start(new Application(), 1200, 600);
    }
    //48 to 57 are accepted integer ASCII's
    int dayCount = 1;//Season change every 30 days
    int season = 1;//1 Summer, 2 Autumn, 3 Winter, 4 Spring
    int cashCount = 0;
    int foodCount = 0;
    Tile[] tiles;
    Crop[] crops;

    public void run() {
        initialization();
        /*
        ///////TEST FOR PICTURE LOADING//////
        Tile tileTemp = tiles[0];
        tileTemp.tilePosition[0] = 400;
        tileTemp.tilePosition[1] = 132;
        SaxionApp.drawImage(tileTemp.tilePicture,
                (tileTemp.tilePosition[0])-1,(tileTemp.tilePosition[1])+1,128,64);
        /////////////////////////////////////*/

    }
    public void initialization (){
        SaxionApp.setFill(Color.black);
        drawBoard();
        crops = cropSetup();
        tiles = tileSetup();
        menu();
    }
    public void menu () {
        drawBoard();
        SaxionApp.printLine("1. Store");
        int selection = SaxionApp.readInt();

        switch (selection){
            case 1:
                shop();
                break;
        }
    }
    public void shop(){
        SaxionApp.printLine("1. Crops");
        int selection = SaxionApp.readInt();

        switch (selection) {
            case 1:
                cropsMenu();
                break;
        }
    }
    public void cropsMenu(){
        int i = 0;
        int xPos = 800;
        int yPos = 300;
        SaxionApp.printLine("0. Back");
        for (Crop crop: crops) {
            String month = String.valueOf(crop.season);
            switch (month){
                case "1" -> month = "Summer";
                case "2" -> month = "Autumn";
                case "3" -> month = "Spring";
                case "4" -> month = "Winter";
            }
            SaxionApp.printLine((i+1)+". "+crop.cropName+" - $"+crop.cost+" - "
                    +crop.dayCountdown+" Day/s - ▲"+crop.foodPayout+" - "+month+" ");
            i++;
        }

        boolean accepted = false;
        char selection = 'a';
        do {
            selection = SaxionApp.readChar();
            int asciiSelection = selection;
            if (selection < 48 || selection > 56){
                SaxionApp.printLine("Must be between 0 and 8");
            }
            else { accepted = true; }
        }
        while (!accepted);

        if (selection == '1'){
            shop();
        }
        }
    public void drawBoard () {
        SaxionApp.clear();
        SaxionApp.drawImage("resources/background.jpeg", 0, 0,1200,600);
        //starting pos x+400 and y+100
        SaxionApp.setBorderSize(2);
        SaxionApp.setBorderColor(Color.white);

        int[] position = {850,60}; // 0 = X, 1 = Y
        int[] increments = {64,32}; // 0 = X, 1 = Y

        SaxionApp.drawLine(position[0],position[1], position[0]+(increments[0]*5), position[1]+(increments[1]*5)); //top line going right
        SaxionApp.drawLine(position[0]-(increments[0]),position[1]+(increments[1]),position[0]+(increments[0]*4), position[1]+(increments[1]*6)); //first divider line, forward slash
        SaxionApp.drawLine(position[0]-(increments[0]*2),position[1]+(increments[1]*2), position[0]+(increments[0]*3), position[1]+(increments[1]*7)); //second divider line, forward slash
        SaxionApp.drawLine(position[0]-(increments[0]*3),position[1]+(increments[1]*3), position[0]+(increments[0]*2), position[1]+(increments[1]*8)); //third divider line, forward slash
        SaxionApp.drawLine(position[0]-(increments[0]*4),position[1]+(increments[1]*4), position[0]+(increments[0]), position[1]+(increments[1]*9)); //fourth divider line, forward slash

        SaxionApp.drawLine(position[0],position[1], position[0]-(increments[0]*5), position[1]+(increments[1]*5)); //top line going left
        SaxionApp.drawLine(position[0]+(increments[0]),position[1]+(increments[1]), position[0]-(increments[0]*4), position[1]+(increments[1]*6)); //first divider line, back slash
        SaxionApp.drawLine(position[0]+(increments[0]*2),position[1]+(increments[1]*2), position[0]-(increments[0]*3), position[1]+(increments[1]*7)); //second divider line, back slash
        SaxionApp.drawLine(position[0]+(increments[0]*3),position[1]+(increments[1]*3), position[0]-(increments[0]*2), position[1]+(increments[1]*8)); //third divider line, back slash
        SaxionApp.drawLine(position[0]+(increments[0]*4),position[1]+(increments[1]*4), position[0]-(increments[0]), position[1]+(increments[1]*9)); //fourth divider line, back slash

        SaxionApp.drawLine(position[0]-(increments[0]*5),position[1]+(increments[1]*5), position[0], position[1]+(increments[1]*10)); //bottom left line going down
        SaxionApp.drawLine(position[0],position[1]+(increments[1]*10), position[0]+(increments[0]*5), position[1]+(increments[1]*5));//bottom right line going down

        SaxionApp.setBorderSize(0);
    }
    public Tile[] tileSetup(){
        Tile[] tiles = new Tile[25];
        CsvReader reader = new CsvReader("resources/tileindex.csv");
        reader.skipRow();
        reader.setSeparator(',');
        while (reader.loadRow()){
            Tile tile = new Tile();
            tile.tileID = reader.getString(0);
            tile.tilePosition[0] = reader.getInt(2);
            tile.tilePosition[1] = reader.getInt(3);
            tiles[reader.getInt(1)] = tile;
        }
        return tiles;
    }
    public Crop[] cropSetup(){
        Crop[] crops = new Crop[8];
        CsvReader reader = new CsvReader("resources/crops.csv");
        reader.skipRow();
        reader.setSeparator(',');
        while (reader.loadRow()){
            Crop crop = new Crop();
            crop.cropName = reader.getString(0);
            crop.cost = reader.getInt(2);
            crop.dayCountdown = reader.getInt(3);
            crop.foodPayout = reader.getInt(4);
            crop.season = reader.getInt(5);
            crops[reader.getInt(1)] = crop;
        }
        return crops;
    }
    public void messageBox (String message){
        SaxionApp.drawBorderedText(message, 800, 200, 14);
    }
}