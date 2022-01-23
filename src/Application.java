import nl.saxion.app.CsvReader;
import nl.saxion.app.SaxionApp;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Application implements Runnable {

    //48 to 57 are accepted integer ASCII's
    Player player = new Player();
    int dayCount = 1;//Season change every 30 days
    int season = 1;//1 Summer, 2 Autumn, 3 Winter, 4 Spring
    String seasons = "";
    int level = 3;// level of player game 1-3
    int levelUpFee = 0;
    Statistics statistics = new Statistics();
    boolean emptyList1 = false;
    boolean emptyList2 = false;
    int y = 100;
    int x = 150;
    int fontSize = 13;
    int bankPot = 0;
    boolean gameOver = false;
    Crop[] saleCrops = new Crop[2];
    Tile[] tiles;
    Crop[] crops;
    Animal[] animals;
    Property[] properties;

    public static void main(String[] args) {
        SaxionApp.start(new Application(), 1200, 600);
    }

    public void run() {
        initialization();
    }

    public void initialization() {
        logoScreen();
        SaxionApp.pause();
        SaxionApp.setFill(Color.black);
        crops = cropSetup();
        tiles = tileSetup();
        animals = animalSetup();
        properties = propertySetup();
        SaxionApp.clear();
        tutorial();
        SaxionApp.pause();
        SaxionApp.clear();
        drawBoard();
        shopMenu();
    }

    public void logoSmall(int x, int y) {
        SaxionApp.drawImage("resources/logoSmall.png", x, y, 149, 118);
    }

    public void logoScreen() {
        SaxionApp.drawImage("resources/logoScreen.png", 0, 0, 1200, 675);
    }

    public void menuLines() {
        SaxionApp.setBorderColor(Color.white);
        SaxionApp.drawLine(485, 0, 485, 800);
        SaxionApp.drawLine(485, 400, 1500, 400);
        SaxionApp.drawLine(750, 400, 750, 800);
    }

    public void menuRectangleGraph() {
        SaxionApp.turnBorderOff();
        Color background = SaxionApp.createColor(58, 46, 39);
        SaxionApp.setBackgroundColor(background);
        Color ground = SaxionApp.createColor(88, 70, 58);
        SaxionApp.setFill(ground);
        SaxionApp.drawRectangle(0, 0, 485, 600);
        Color ground1 = SaxionApp.createColor(67, 54, 46);
        SaxionApp.setFill(ground1);
        SaxionApp.drawRectangle(485, 400, 1000, 500);

    }

    /*Step1.1 mainmenu(Harun)
    public void mainMenu() {
        drawBoard();
        SaxionApp.printLine(" ");
        SaxionApp.printLine("Welcome to the FarmX!");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("1. New Game");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("2. Tutorial");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("3. Quit");
        SaxionApp.printLine(" ");
        SaxionApp.print("Please enter your choice: ");
        char selection = SaxionApp.readChar();

        switch (selection) {
            //new game
            case '1':
                SaxionApp.clear();
                tutorial();
                SaxionApp.pause();
                SaxionApp.clear();
                drawBoard();
                shopMenu();
                drawBoard();
                break;
            //tutorial
            case '2':
                SaxionApp.clear();
                tutorial();
                SaxionApp.pause();
                SaxionApp.clear();
                mainMenu();
                break;
            //exit
            case '3':
                SaxionApp.clear();
                SaxionApp.drawText("GOOD BYE!", 400, 250, 60);
                break;
            default:
                SaxionApp.printLine("Invalid selection", Color.red);
                SaxionApp.sleep(1);
                mainMenu();
                break;
        }

    }*/

    //Step1.2 shopmenu(Harun)
    public void shopMenu() {
        if (gameOver){//Jacques
            System.exit(0);
        }
        drawBoard();
        SaxionApp.printLine("STORE");
        SaxionApp.printLine(" ");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("1. Crops");
        SaxionApp.printLine(" ");
        if (level < 2) {
            SaxionApp.printLine("2. Animals - Locked (Lvl 2)", Color.gray);
        } else {
            SaxionApp.printLine("2. Animals");
        }
        SaxionApp.printLine(" ");
        if (level < 3) {
            SaxionApp.printLine("3. Properties - Locked (Lvl 3)", Color.gray);
        } else {
            SaxionApp.printLine("3. Properties");
        }
        SaxionApp.printLine(" ");
        SaxionApp.printLine("4. Upgrade");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("5. Empty tile");
        SaxionApp.printLine(" ");
        if (level != 3) {
            levelUp(false);
        } else {
            SaxionApp.printLine("6. Max level", Color.gray);
        }
        SaxionApp.printLine(" ");
        SaxionApp.printLine("7. GO TO THE NEXT DAY >>>");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("8. Option Menu");
        SaxionApp.printLine();
        char ss = SaxionApp.readChar();

        switch (ss) {
            //1.plants
            case '1':
                SaxionApp.clear();
                drawBoard();
                SaxionApp.printLine("0. Back");
                SaxionApp.printLine("1. Buy");
                SaxionApp.printLine("2. Sell");
                char s = SaxionApp.readChar();
                switch (s) {
                    case '1':
                        //buy
                        SaxionApp.clear();
                        drawBoard();
                        cropsMenu();
                        break;
                    case '2':
                        //sell
                        SaxionApp.clear();
                        drawBoard();
                        sellMenu();
                        break;
                    case '0':
                        shopMenu();
                        break;
                    default:
                        SaxionApp.printLine("Incorrect selection", Color.red);
                        SaxionApp.sleep(1);
                        shopMenu();
                }
                break;
            //2.animals
            case '2':
                if (level >= 2) {
                    animalsMenu();
                } else {
                    shopMenu();
                }
                break;
            case '3':
                SaxionApp.clear();
                drawBoard();
                if (level >= 3) {
                    SaxionApp.printLine("0. Back");
                    SaxionApp.printLine("1. Buy Property");
                    SaxionApp.printLine("2. Enter Property");
                    char choice1 = SaxionApp.readChar();
                    switch (choice1) {
                        case '1':
                            //buy
                            SaxionApp.clear();
                            drawBoard();
                            buyProperties();
                            break;
                        case '2':
                            SaxionApp.clear();
                            drawBoard();
                            boolean[] boughtProperties = {false, false, false};//bank,market,casino
                            for (Tile tile : tiles) {
                                if (tile.tileResourceName.equals("Bank")) {
                                    boughtProperties[0] = true;
                                } else if (tile.tileResourceName.equals("Market")) {
                                    boughtProperties[1] = true;
                                } else if (tile.tileResourceName.equals("Casino")) {
                                    boughtProperties[2] = true;
                                }
                            }
                            SaxionApp.printLine("0. Back");
                            if (!boughtProperties[0]) {
                                SaxionApp.printLine("1. Bank (Locked)", Color.gray);
                            } else {
                                SaxionApp.printLine("1. Bank");
                            }
                            if (!boughtProperties[1]) {
                                SaxionApp.printLine("2. Market (Locked)", Color.gray);
                            } else {
                                SaxionApp.printLine("2. Market");
                            }
                            if (!boughtProperties[2]) {
                                SaxionApp.printLine("3. Casino (Locked)", Color.gray);
                            } else {
                                SaxionApp.printLine("3. Casino");
                            }
                            char choice2 = SaxionApp.readChar();
                            switch (choice2) {
                                case '1':
                                    if (!boughtProperties[0]) {
                                        SaxionApp.printLine("Property not yet constructed", Color.red);
                                        SaxionApp.sleep(1);
                                    } else { bankMenu(); }
                                    break;
                                case '2':
                                    if (!boughtProperties[1]) {
                                        SaxionApp.printLine("Property not yet constructed", Color.red);
                                        SaxionApp.sleep(1);
                                    } else { marketMenu(); }
                                    break;
                                case '3':
                                    if (!boughtProperties[2]) {
                                        SaxionApp.printLine("Property not yet constructed", Color.red);
                                        SaxionApp.sleep(1);
                                    } else {
                                        SaxionApp.print("Enter your pot: ");
                                        int pot = SaxionApp.readInt();
                                        if (pot > player.cashCount) {
                                            SaxionApp.printLine("You do not have this much cash", Color.red);
                                            SaxionApp.sleep(1);
                                            shopMenu();
                                        }
                                        player.cashCount -= pot;
                                        casinoMenu(pot);
                                    }
                                    break;
                                case '0':
                                    shopMenu();
                                    break;
                                default:
                                    SaxionApp.printLine("Incorrect selection", Color.red);
                                    SaxionApp.sleep(1);
                                    shopMenu();
                            }
                        case '0':
                            shopMenu();
                        default:
                            SaxionApp.printLine("Incorrect selection", Color.red);
                            SaxionApp.sleep(1);
                            shopMenu();
                    }
                } else {
                    shopMenu();
                }
                break;
            case '4':
                upgradeCrop();
                break;
            case '5':
                clearTile();
                break;
            case '6':
                if (level != 3) {
                    levelUp(true);
                } else {
                    shopMenu();
                }
                break;
            case '7':
                nextDay();
                break;
            case '8':
                option();
                break;
            default:
                SaxionApp.printLine("Invalid selection", Color.red);
                SaxionApp.sleep(1);
                shopMenu();
                break;
        }
    }

    //Step2 tutorial(Harun)
    public void tutorial() {
        SaxionApp.turnBorderOff();
        Color backTut = SaxionApp.createColor(37, 30, 24);
        SaxionApp.setFill(backTut);
        int y = 100;
        int x = 150;

        SaxionApp.drawRectangle(x, y, 850, 400);
        SaxionApp.drawText("GAME TUTORIAL", x + 300, y + 50, 25);
        SaxionApp.drawText("Welcome to FarmX!", x + 30, y + 100, 13);
        SaxionApp.drawText("The aim of the game is to progress through the 3 levels and hit $1,000,000 in your account.", x + 30, y + 120, 13);
        SaxionApp.drawText("You start with $50 and 5 available tiles. You can increase the number of tiles by leveling up.", x + 30, y + 140, 13);
        SaxionApp.drawText("", x + 30, y + 160, 13);
        SaxionApp.drawText("At level 1, you can only buy seeds. Grow these seeds into crops and then sell their food value for profit.", x + 30, y + 180, 13);
        SaxionApp.drawText("At level 2, you can buy animals. Animals eat crop every day to survive, but pay out a high number of food.", x + 30, y + 200, 13);
        SaxionApp.drawText("At level 3, you can build properties. Properties require a lot of food per day, but pay out lots of cash.", x + 30, y + 220, 13);
        SaxionApp.drawText("You can upgrade your crops to increase their payout, and each property also has a unique function to explore. Check them out!", x + 30, y + 240, 13);
        SaxionApp.drawText("Press any key to skip the tutorial >>", x + 560, 450, 15);
        logoSmall(500, 360);
    }


    public void drawBoard() {
        SaxionApp.clear();
        menuRectangleGraph();
        menuLines();
        logoSmall(540, 440);
        //SaxionApp.drawImage("resources/background.jpeg", 0, 0, 1200, 600);
        //SaxionApp.setBorderColor(Color.white);
        /*SaxionApp.setBorderSize(2);
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
        */
        SaxionApp.setBorderSize(0);
        drawTiles();
        //will be added images
        SaxionApp.drawImage("resources/Icons/seasonsIcon.png",810,410,30,30);
        SaxionApp.drawImage("resources/Icons/foodIcon.png",810,460,30,30);
        SaxionApp.drawImage("resources/Icons/cashIcon.png",812,510,30,30);
        SaxionApp.drawImage("resources/Icons/dayIcon.png",812,560,30,30);
        if(season == 1){
            seasons = "Summer";
        }else if(season == 2){
            seasons = "Autumn";
        }else if(season == 3){
            seasons = "Winter";
        }else if(season == 4){
            seasons = "Spring";
        }
        messageBox("Season: " + (seasons), 850, 420);
        messageBox("Food: " + (player.foodCount), 850, 470);
        messageBox("Cash: " + (player.cashCount), 850, 520);
        messageBox("Day: " + (dayCount), 850, 570);
    }

    public void drawTiles() {
        for (Tile tile : tiles) {
            tile.drawTile(level);
        }
    }

    /*
    public void menu () {
        drawBoard();
        SaxionApp.printLine("0. Shop");
        SaxionApp.printLine("1. Sell Food or Stock");
        SaxionApp.printLine("2. Finish the day");
        char selection = SaxionApp.readChar();

        switch (selection) {
            case '0' -> shop();
            case '1' -> sellMenu();
            case '2' -> nextDay();
            default -> {
                SaxionApp.printLine("Invalid selection",Color.red);
                SaxionApp.sleep(1);
                menu();
            }
        }
    }

     */
    public void sellMenu() {
        drawBoard();
        SaxionApp.printLine("0. Back");
        SaxionApp.printLine("1. Sell Food");
        SaxionApp.printLine("2. Convert Crop into Food");
        char selection = SaxionApp.readChar();

        switch (selection) {
            case '0' -> shopMenu();
            case '1' -> sellFood();
            case '2' -> sellCrop();
            default -> {
                SaxionApp.printLine("Invalid selection", Color.red);
                SaxionApp.sleep(1);
                sellMenu();
            }
        }
    }

    public void sellFood() {
        drawBoard();
        SaxionApp.printLine("How much food do you want to sell? (Enter 0 to go back)");
        int selection = SaxionApp.readInt();

        if (selection == 0) {
            sellMenu();
        } else if (selection > player.foodCount) {
            SaxionApp.printLine("You do not have this much food to sell", Color.red);
            SaxionApp.sleep(1);
            SaxionApp.removeLastPrint();
            sellFood();
        } else {
            SaxionApp.printLine("Confirm sale of " + selection + " food for " + selection + " cash? [Y/N]", Color.yellow);
            char entry = SaxionApp.readChar();
            do {
                entry = Character.toUpperCase(entry);
                switch (entry) {
                    case 'Y' -> {
                        player.foodCount -= selection;
                        player.cashCount += selection;
                        statistics.foodSell += selection;
                        sellMenu();
                    }
                    case 'N' -> sellFood();
                    default -> {
                        SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                        SaxionApp.sleep(1);
                        SaxionApp.removeLastPrint();
                        entry = SaxionApp.readChar();
                    }
                }
            } while (entry != 'Y' && entry != 'N');
        }
    }

    public void sellCrop() {
        drawBoard();
        char selection;
        int[] counters = {0, 0};
        ArrayList<Integer> keyPairs = new ArrayList<>();
        SaxionApp.printLine("0. Back");
        SaxionApp.printLine();
        SaxionApp.printLine("Player's Stock");
        SaxionApp.printLine();
        for (Crop crop : crops) {
            if (player.cropStock[counters[1]] > 0) {
                SaxionApp.print((counters[0] + 1) + ". " + crop.cropName + " - x" + player.cropStock[counters[1]] +
                        " - Gives ");
                SaxionApp.print("▲" + crop.foodPayout, Color.green);
                SaxionApp.print(" food each");
                SaxionApp.printLine();
                keyPairs.add(counters[1]);
                counters[0]++;
            }
            counters[1]++;
        }
        if (counters[0] > 0) {
            SaxionApp.printLine((counters[0] + 1) + ". Convert all");
        }
        selection = SaxionApp.readChar();
        if ((selection - 48) < 0 || (selection - 48) > counters[0] + 1) {
            SaxionApp.printLine("Invalid selection", Color.red);
            SaxionApp.sleep(1);
            sellCrop();
        } else {
            if (selection - 48 == 0) {
                sellMenu();
            } else if (selection - 48 == (counters[0]) + 1) {
                SaxionApp.printLine();
                SaxionApp.printLine("Convert all crops, confirm? [Y/N]", Color.yellow);
                char entry = SaxionApp.readChar();
                do {
                    entry = Character.toUpperCase(entry);
                    switch (entry) {
                        case 'Y' -> {
                            String sellallcrop = "Yesterday, you sold all your crops";
                            statistics.cropSell.add(sellallcrop);
                            emptyList2 = true;
                            sellAll(1);
                            SaxionApp.pause();
                            sellCrop();
                        }
                        case 'N' -> sellCrop();
                        default -> {
                            SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                            SaxionApp.sleep(1);
                            SaxionApp.removeLastPrint();
                            entry = SaxionApp.readChar();
                        }
                    }
                } while (entry != 'Y' && entry != 'N');
            }
            SaxionApp.printLine();
            int quantity;
            do {
                SaxionApp.printLine("Quantity?");
                quantity = SaxionApp.readInt();
                if (player.cropStock[keyPairs.get(selection - 49)] < quantity) {
                    SaxionApp.printLine("You do not have this many to convert", Color.red);
                    SaxionApp.sleep(1);
                    for (int i = 0; i < 3; i++) {
                        SaxionApp.removeLastPrint();
                    }
                } else if (quantity == 0) {
                    SaxionApp.printLine("Enter more than 0", Color.red);
                    SaxionApp.sleep(1);
                    for (int i = 0; i < 3; i++) {
                        SaxionApp.removeLastPrint();
                    }
                }
            } while (player.cropStock[keyPairs.get(selection - 49)] < quantity || quantity == 0);
            SaxionApp.printLine();
            SaxionApp.printLine("Confirm conversion of " + quantity + "x " + crops[keyPairs.get(selection - 49)].cropName + "? [Y/N]", Color.yellow);
            char entry = SaxionApp.readChar();
            do {
                entry = Character.toUpperCase(entry);
                switch (entry) {
                    case 'Y' -> {
                        String cropSold = quantity + "x " + crops[keyPairs.get(selection - 49)].cropName;
                        statistics.cropSell.add(cropSold);
                        emptyList2 = true;
                        player.foodCount += quantity * crops[keyPairs.get(selection - 49)].foodPayout;
                        player.cropStock[keyPairs.get(selection - 49)] -= quantity;
                        SaxionApp.printLine("Successfully converted " + quantity + "x " + crops[keyPairs.get(selection - 49)].cropName, Color.green);
                        SaxionApp.sleep(1);
                        sellCrop();
                    }
                    case 'N' -> sellCrop();
                    default -> {
                        SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                        SaxionApp.sleep(1);
                        SaxionApp.removeLastPrint();
                        entry = SaxionApp.readChar();
                    }
                }
            } while (entry != 'Y' && entry != 'N');
        }
    }

    public void sellAll(int id) {
        if (id == 1) {
            for (int i = 0; i < player.cropStock.length; i++) {
                int j = 0;
                while (player.cropStock[i] > 0) {
                    player.foodCount += crops[i].foodPayout;
                    player.cropStock[i]--;
                    j++;
                }
                if (j > 0) {
                    SaxionApp.print("Converted " + j + "x " + crops[i].cropName + " into ");
                    SaxionApp.print("▲" + crops[i].foodPayout * j, Color.green);
                    SaxionApp.print(" food");
                    SaxionApp.printLine();
                    SaxionApp.sleep(0.5);
                }
            }
        }
    }

    /*
    public void shop(){
        drawBoard();
        SaxionApp.printLine("0. Back");
        SaxionApp.printLine("1. Crops");
        char selection = SaxionApp.readChar();

        switch (selection) {
            case '0' -> menu();
            case '1' -> cropsMenu();
            default -> {
                SaxionApp.printLine("Invalid selection",Color.red);
                SaxionApp.sleep(1);
                shop();
            }
        }
    }

     */
    public void cropsMenu() {
        drawBoard();
        int i = 0;
        SaxionApp.printLine("0. Back");
        for (Crop crop : crops) {
            String month = String.valueOf(crop.season);
            switch (month) {
                case "1" -> month = "Summer";
                case "2" -> month = "Autumn";
                case "3" -> month = "Spring";
                case "4" -> month = "Winter";
            }
            SaxionApp.print((i + 1) + ". " + crop.cropName + " - $" + crop.cost + " - "
                    + crop.dayCountdown + " Day/s - ");
            SaxionApp.print("▲" + crop.foodPayout, Color.green);
            SaxionApp.print(" - " + month + " ");
            SaxionApp.printLine();
            i++;
        }
        char selection = SaxionApp.readChar();
        if (selection < 48 || selection > 56) {
            SaxionApp.printLine("Must be between 0 and 8", Color.red);
            SaxionApp.sleep(1);
            cropsMenu();
        }
        if (selection == '0') {
            shopMenu();
        } else {
            int i2 = 0;
            for (Tile tile : tiles) {
                if (!tile.occupied[0] && !tile.occupied[1]) {
                    i2++;
                }
            }
            if (i2 == 0) {
                SaxionApp.printLine("No free tiles", Color.red);
                SaxionApp.sleep(1);
                cropsMenu();
            }
            if (crops[selection - 49].season != season) {
                SaxionApp.printLine("Warning: crop planted out of season grows slower", Color.red);
            }
            boolean accepted = false;
            while (!accepted) {
                SaxionApp.printLine();
                SaxionApp.printLine("Quantity?");
                int quantity = SaxionApp.readInt();
                if (quantity * crops[selection - 49].cost > player.cashCount) {
                    SaxionApp.printLine("You do not have enough cash for this", Color.red);
                    SaxionApp.sleep(1);
                    SaxionApp.clear();
                    cropsMenu();
                } else if (quantity == 0) {
                    cropsMenu();
                } else {
                    int counter = 0;
                    for (Tile tile : tiles) {
                        if (!tile.occupied[0] && !tile.occupied[1]) {
                            counter++;
                        }
                    }
                    if (counter < quantity) {
                        SaxionApp.printLine("Not enough empty tiles", Color.red);
                        SaxionApp.sleep(1);
                        for (int j = 0; j < 4; j++) {
                            SaxionApp.removeLastPrint();
                        }
                    } else {
                        SaxionApp.printLine();
                        SaxionApp.printLine("Confirm? (Y/N)");
                        SaxionApp.printLine(crops[selection - 49].cropName + " x" + quantity + " for $"
                                + quantity * crops[selection - 49].cost + "?", Color.yellow);
                        char entry = SaxionApp.readChar();
                        do {
                            entry = Character.toUpperCase(entry);
                            switch (entry) {
                                case 'Y' -> {
                                    String plant = crops[selection - 49].cropName + " x" + quantity;
                                    statistics.plantBuy.add(plant);
                                    emptyList1 = true;
                                    accepted = true;
                                    int identifier = 1;
                                    assignToTile(selection, quantity, identifier);
                                }
                                case 'N' -> cropsMenu();
                                default -> {
                                    SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                                    SaxionApp.sleep(1);
                                    SaxionApp.removeLastPrint();
                                    entry = SaxionApp.readChar();

                                }
                            }
                        } while (entry != 'Y' && entry != 'N');
                    }
                }
            }
        }
    }

    public void animalsMenu() {
        drawBoard();
        int i = 1;
        SaxionApp.printLine("0. Back");
        for (Animal animal : animals) {
            SaxionApp.printLine(i + ". " + animal.animalName + " $" + animal.cost + "", Color.yellow);
            SaxionApp.printLine("Consumes " + animal.quantity[0] + "x " + crops[animal.cropRequirement[0]].cropName
                    + " or " + animal.quantity[1] + "x " + crops[animal.cropRequirement[1]].cropName + " per day");
            SaxionApp.printLine(animal.infoMessage);
            i++;
        }
        char selection = SaxionApp.readChar();
        if (selection < 48 || selection > 52) {
            SaxionApp.printLine("Must be between 0 and 4", Color.red);
            SaxionApp.sleep(1);
            animalsMenu();
        }
        if (selection == '0') {
            shopMenu();
        } else {
            int i2 = 0;
            for (Tile tile : tiles) {
                if (!tile.occupied[0] && !tile.occupied[1]) {
                    i2++;
                }
            }
            if (i2 == 0) {
                SaxionApp.printLine("No free tiles", Color.red);
                SaxionApp.sleep(1);
                animalsMenu();
            }
            boolean accepted = false;
            while (!accepted) {
                SaxionApp.printLine();
                SaxionApp.printLine("Quantity?");
                int quantity = SaxionApp.readInt();
                if (quantity * animals[selection - 49].cost > player.cashCount) {
                    SaxionApp.printLine("You do not have enough cash for this", Color.red);
                    SaxionApp.sleep(1);
                    SaxionApp.clear();
                    animalsMenu();
                } else if (quantity == 0) {
                    SaxionApp.printLine("Enter more than 0", Color.red);
                    SaxionApp.sleep(1);
                    SaxionApp.clear();
                    animalsMenu();
                } else {
                    int counter = 0;
                    for (Tile tile : tiles) {
                        if (!tile.occupied[0] && !tile.occupied[1]) {
                            counter++;
                        }
                    }
                    if (counter < quantity) {
                        SaxionApp.printLine("Not enough empty tiles", Color.red);
                        SaxionApp.sleep(1);
                        for (int j = 0; j < 4; j++) {
                            SaxionApp.removeLastPrint();
                        }
                    } else {
                        SaxionApp.printLine();
                        SaxionApp.printLine("Confirm? (Y/N)");
                        SaxionApp.printLine(animals[selection - 49].animalName + " x" + quantity + " for $"
                                + quantity * animals[selection - 49].cost + "?", Color.yellow);
                        char entry = SaxionApp.readChar();
                        do {
                            entry = Character.toUpperCase(entry);
                            switch (entry) {
                                case 'Y' -> {
                                    accepted = true;
                                    int identifier = 2;
                                    assignToTile(selection, quantity, identifier);
                                }
                                case 'N' -> animalsMenu();
                                default -> {
                                    SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                                    SaxionApp.sleep(1);
                                    SaxionApp.removeLastPrint();
                                    entry = SaxionApp.readChar();

                                }
                            }
                        } while (entry != 'Y' && entry != 'N');
                    }
                }
            }
        }
    }

    public void buyProperties() {
        drawBoard();
        int i = 1;
        SaxionApp.printLine("0. Back");
        for (Property property : properties) {
            SaxionApp.printLine(i + ". " + property.propertyName + " $" + property.cashCost + "", Color.yellow);
            SaxionApp.printLine("Consumes " + property.foodCost + " food and generates $" + property.cashPayout + " every day.");
            SaxionApp.printLine(property.infoMessage);
            i++;
        }
        char selection = SaxionApp.readChar();
        if (selection < 48 || selection > 52) {
            SaxionApp.printLine("Must be between 0 and 4", Color.red);
            SaxionApp.sleep(1);
            buyProperties();
        }
        if (selection == '0') {
            shopMenu();
        } else {
            int i2 = 0;
            for (Tile tile : tiles) {
                if (!tile.occupied[0] && !tile.occupied[1]) {
                    i2++;
                }
            }
            if (i2 == 0) {
                SaxionApp.printLine("No free tiles", Color.red);
                SaxionApp.sleep(1);
                buyProperties();
            }
            boolean accepted = false;
            while (!accepted) {
                SaxionApp.printLine();
                SaxionApp.printLine("Quantity?");
                int quantity = SaxionApp.readInt();
                if (quantity * animals[selection - 49].cost > player.cashCount) {
                    SaxionApp.printLine("You do not have enough cash for this", Color.red);
                    SaxionApp.sleep(1);
                    SaxionApp.clear();
                    buyProperties();
                } else if (quantity == 0) {
                    SaxionApp.printLine("Enter more than 0", Color.red);
                    SaxionApp.sleep(1);
                    SaxionApp.clear();
                    buyProperties();
                } else {
                    int counter = 0;
                    for (Tile tile : tiles) {
                        if (!tile.occupied[0] && !tile.occupied[1]) {
                            counter++;
                        }
                    }
                    if (counter < quantity) {
                        SaxionApp.printLine("Not enough empty tiles", Color.red);
                        SaxionApp.sleep(1);
                        for (int j = 0; j < 4; j++) {
                            SaxionApp.removeLastPrint();
                        }
                    } else {
                        SaxionApp.printLine();
                        SaxionApp.printLine("Confirm? (Y/N)");
                        SaxionApp.printLine(properties[selection - 49].propertyName + " x" + quantity + " for $"
                                + quantity * properties[selection - 49].cashCost + "?", Color.yellow);
                        char entry = SaxionApp.readChar();
                        do {
                            entry = Character.toUpperCase(entry);
                            switch (entry) {
                                case 'Y' -> {
                                    accepted = true;
                                    int identifier = 4;
                                    assignToTile(selection, quantity, identifier);
                                }
                                case 'N' -> buyProperties();
                                default -> {
                                    SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                                    SaxionApp.sleep(1);
                                    SaxionApp.removeLastPrint();
                                    entry = SaxionApp.readChar();
                                }
                            }
                        } while (entry != 'Y' && entry != 'N');
                    }
                }
            }
        }
    }

    public void assignToTile(char indexNumber, int quantity, int identifier) {
        drawBoard();
        int count = quantity;
        //Crop crop = crops[indexNumber-49];//Jacques. Get the crop to be placed on tile
        if (!player.automaticPopulation) {
            boolean accepted = false;
            while (!accepted) {
                SaxionApp.printLine("Enter tile coordinates (number then letter)");
                String selection = SaxionApp.readString();
                if (selection.length() != 2) {
                    SaxionApp.printLine("Only enter two characters (e.g 3C)", Color.red);
                    SaxionApp.sleep(1);
                    for (int i = 0; i < 3; i++) {
                        SaxionApp.removeLastPrint();
                    }
                } else {
                    String letterUppercase = selection.substring(1, 2).toUpperCase();
                    String[] acceptedValues = {"12345", "ABCDE"};
                    if (!acceptedValues[0].contains(selection.substring(0, 1)) ||
                            !acceptedValues[1].contains(letterUppercase)) {
                        SaxionApp.printLine("Invalid selection", Color.red);
                        SaxionApp.sleep(1);
                        for (int i = 0; i < 3; i++) {
                            SaxionApp.removeLastPrint();
                        }
                    } else {
                        String ID = selection.charAt(0) + letterUppercase;
                        for (Tile tile : tiles) {
                            if (tile.tileID.equals(ID)) {
                                if (identifier == 3) {
                                    if (!tile.tileResourceName.equals(crops[indexNumber - 1].cropName)) {
                                        SaxionApp.printLine("This is not a " + crops[indexNumber - 1].cropName, Color.red);
                                        SaxionApp.sleep(1);
                                        assignToTile(indexNumber, quantity, identifier);
                                    } else if (tile.upgradeLevel >= 5) {
                                        SaxionApp.printLine("Crop already fully upgraded (x5)",Color.red);
                                        SaxionApp.sleep(1);
                                        assignToTile(indexNumber, quantity, identifier);
                                    } else {
                                        tile.dayCountdown++;
                                        tile.upgradeLevel++;
                                        player.cashCount -= (crops[indexNumber - 1].cost * 2);
                                        drawBoard();
                                        for (int i = 0; i < 3; i++) {
                                            SaxionApp.removeLastPrint();
                                        }
                                        if (count == 1) {
                                            SaxionApp.printLine(crops[indexNumber - 1].cropName + " successfully upgraded", Color.green);
                                            SaxionApp.sleep(1);
                                            accepted = true;
                                            shopMenu();
                                        } else {
                                            count--;
                                        }
                                    }
                                } else if (tile.occupied[0] || tile.occupied[1]) {
                                    SaxionApp.printLine("Tile is already occupied, or locked", Color.red);
                                    SaxionApp.sleep(1);
                                    for (int i = 0; i < 3; i++) {
                                        SaxionApp.removeLastPrint();
                                    }
                                } else {
                                    tile.occupied[1] = true;
                                    if (identifier == 1) {
                                        tile.tileResourceName = crops[indexNumber - 49].cropName;
                                        tile.dayCountdown = crops[indexNumber - 49].dayCountdown;
                                        tile.crop = crops[indexNumber - 49];//Jacques. Set crop on tile
                                        if (crops[indexNumber - 49].season != season) {
                                            if (crops[indexNumber - 49].dayCountdown == 1) {
                                                tile.dayCountdown++;
                                            } else {
                                                tile.dayCountdown = (int) ((crops[indexNumber - 49].dayCountdown) * 1.5);
                                            }
                                        }
                                        tile.season = crops[indexNumber - 49].season;
                                        drawBoard();
                                        SaxionApp.printLine(crops[indexNumber - 49].cropName + " successfully planted", Color.green);
                                        player.cashCount -= crops[indexNumber - 49].cost;
                                        if (count == 1) {
                                            accepted = true;
                                            shopMenu();
                                        } else {
                                            count--;
                                        }
                                    } else if (identifier == 2) {
                                        Animal animal = animals[indexNumber - 49];//Jacques. Get the crop to be placed on tile
                                        tile.animal = animal;
                                        tile.occupied[1] = true;
                                        tile.tileResourceName = animals[indexNumber - 49].animalName;
                                        tile.dayCountdown = animals[indexNumber - 49].dayCountdown;
                                        tile.foodPayout = animals[indexNumber - 49].foodPayout;
                                        tile.cashPayout = animals[indexNumber - 49].cashPayout;
                                        tile.cropRequirement[0] = animals[indexNumber - 49].cropRequirement[0];
                                        tile.cropRequirement[1] = animals[indexNumber - 49].cropRequirement[1];
                                        tile.cropQuantity[0] = animals[indexNumber - 49].quantity[0];
                                        tile.cropQuantity[1] = animals[indexNumber - 49].quantity[1];
                                        player.cashCount -= animals[indexNumber - 49].cost;
                                        SaxionApp.printLine(animals[indexNumber - 49].animalName + " successfully herded", Color.green);
                                        SaxionApp.sleep(1);
                                        if (count == 1) {
                                            accepted = true;
                                            shopMenu();
                                        } else {
                                            count--;
                                        }
                                    } else if (identifier == 4) {
                                        Property property = properties[indexNumber - 49];//Jacques. Get the crop to be placed on tile
                                        tile.property = property;
                                        tile.occupied[1] = true;
                                        tile.tileResourceName = properties[indexNumber - 49].propertyName;
                                        tile.foodCost = properties[indexNumber - 49].foodCost;
                                        tile.cashPayout = properties[indexNumber - 49].cashPayout;
                                        player.cashCount -= properties[indexNumber - 49].cashCost;
                                        SaxionApp.printLine(properties[indexNumber - 49].propertyName + " successfully built!", Color.green);
                                        SaxionApp.sleep(1);
                                        if (count == 1) {
                                            accepted = true;
                                            shopMenu();
                                        } else {
                                            count--;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (Tile tile : tiles) {
                if (identifier == 3) {
                    if (tile.tileResourceName.equals(crops[indexNumber - 1].cropName) && tile.upgradeLevel < 5) {
                        tile.dayCountdown += 2;
                        tile.upgradeLevel++;
                        player.cashCount -= (crops[indexNumber - 1].cost * 2);
                        drawBoard();
                        if (count == 1) {
                            SaxionApp.printLine(crops[indexNumber - 1].cropName + " successfully upgraded", Color.green);
                            SaxionApp.sleep(1);
                            shopMenu();
                        } else {
                            count--;
                        }
                    }
                } else if (!tile.occupied[0] && !tile.occupied[1] && count > 0) {
                    if (identifier == 1) {
                        tile.occupied[1] = true;
                        tile.crop = crops[indexNumber - 49];
                        tile.tileResourceName = crops[indexNumber - 49].cropName;
                        tile.dayCountdown = crops[indexNumber - 49].dayCountdown;
                        if (crops[indexNumber - 49].season != season) {
                            if (crops[indexNumber - 49].dayCountdown == 1) {
                                tile.dayCountdown++;
                            } else {
                                tile.dayCountdown = (int) ((crops[indexNumber - 49].dayCountdown) * 1.5);
                            }
                        }
                        tile.season = crops[indexNumber - 49].season;
                        drawBoard();
                        SaxionApp.printLine(crops[indexNumber - 49].cropName + " successfully planted", Color.green);
                        SaxionApp.sleep(0.5);
                        player.cashCount -= crops[indexNumber - 49].cost;
                        count--;
                    } else if (identifier == 2) {
                        Animal animal = animals[indexNumber - 49];//Jacques. Get the crop to be placed on tile
                        tile.animal = animal;
                        tile.occupied[1] = true;
                        tile.tileResourceName = animals[indexNumber - 49].animalName;
                        tile.dayCountdown = animals[indexNumber - 49].dayCountdown;
                        tile.foodPayout = animals[indexNumber - 49].foodPayout;
                        tile.cashPayout = animals[indexNumber - 49].cashPayout;
                        tile.cropRequirement[0] = animals[indexNumber - 49].cropRequirement[0];
                        tile.cropRequirement[1] = animals[indexNumber - 49].cropRequirement[1];
                        tile.cropQuantity[0] = animals[indexNumber - 49].quantity[0];
                        tile.cropQuantity[1] = animals[indexNumber - 49].quantity[1];
                        player.cashCount -= animals[indexNumber - 49].cost;
                        SaxionApp.printLine(animals[indexNumber - 49].animalName + " successfully herded", Color.green);
                        SaxionApp.sleep(1);
                        if (count == 1) {
                            shopMenu();
                        } else {
                            count--;
                        }
                    } else if (identifier == 4) {
                        Property property = properties[indexNumber - 49];//Jacques. Get the crop to be placed on tile
                        tile.property = property;
                        tile.occupied[1] = true;
                        tile.tileResourceName = properties[indexNumber - 49].propertyName;
                        tile.foodCost = properties[indexNumber - 49].foodCost;
                        tile.cashPayout = properties[indexNumber - 49].cashPayout;
                        player.cashCount -= properties[indexNumber - 49].cashCost;
                        SaxionApp.printLine(properties[indexNumber - 49].propertyName + " successfully built!", Color.green);
                        SaxionApp.sleep(1);
                        if (count == 1) {
                            shopMenu();
                        } else {
                            count--;
                        }
                    }
                }
            }
            shopMenu();
        }
    }

    public void upgradeCrop() {
        drawBoard();
        SaxionApp.printLine("Upgraded crops take a day extra to grow,");
        SaxionApp.printLine("but pay out 3x as much crop.");
        SaxionApp.printLine("(Crops can be upgraded max 5 times.)",Color.yellow);
        SaxionApp.printLine();
        SaxionApp.printLine("0. Back");
        //0=Tomato/1=Corn/2=Lettuce/3=Eggplant/4=Potato/5=Peppers/6=Pumpkin/7=Watermelon
        int[] plantedCrop = {0, 0, 0, 0, 0, 0, 0, 0};
        for (Tile tile : tiles) {
            for (int i = 0; i < crops.length; i++) {
                if (tile.tileResourceName.equals(crops[i].cropName) && tile.upgradeLevel < 5) {
                    plantedCrop[i]++;
                }
            }
        }
        ArrayList<Integer> keyPairs = new ArrayList<>();
        for (int i = 0; i < plantedCrop.length; i++) {
            if (plantedCrop[i] > 0) {
                keyPairs.add(i);
                SaxionApp.printLine(keyPairs.size() + ". " + crops[i].cropName + " x" + plantedCrop[i] + " - Costs "
                        + (crops[i].cost * 2) + " per crop");
            }
        }
        char selection = SaxionApp.readChar();
        if (selection < 48 || selection > keyPairs.size() + 48) {
            SaxionApp.printLine("Invalid selection", Color.red);
            SaxionApp.sleep(1);
            upgradeCrop();
        } else if (selection == 48) {
            shopMenu();
        } else {
            int cost = (crops[keyPairs.get(selection - 49)].cost * 2);
            SaxionApp.printLine("Quantity?");
            int quantity = SaxionApp.readInt();
            if ((quantity * crops[keyPairs.get(selection - 49)].cost * 2) > player.cashCount) {
                SaxionApp.printLine("You do not have enough cash", Color.red);
                SaxionApp.sleep(1);
                upgradeCrop();
            } else if (quantity > plantedCrop[keyPairs.get(selection - 49)]) {
                SaxionApp.printLine("You do not have this many crops to upgrade", Color.red);
                SaxionApp.sleep(1);
                upgradeCrop();
            } else if (quantity == 0) {
                upgradeCrop();
            } else {
                    SaxionApp.printLine("Confirm upgrade of " + quantity + "x " + crops[keyPairs.get(selection - 49)].cropName +
                            " for $" + (quantity * crops[keyPairs.get(selection - 49)].cost * 2) + "? [Y/N]", Color.yellow);
                    char entry = SaxionApp.readChar();
                    do {
                        entry = Character.toUpperCase(entry);
                        switch (entry) {
                            case 'Y' -> {
                                int a = keyPairs.get(selection - 49);
                                assignToTile((char) ((char) a + 1), quantity, 3);
                            }
                            case 'N' -> upgradeCrop();

                            default -> {
                                SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                                SaxionApp.sleep(1);
                                SaxionApp.removeLastPrint();
                                entry = SaxionApp.readChar();
                            }
                        }
                    } while (entry != 'Y' && entry != 'N');
                }
            }
        }

    public void levelUp(boolean skipper) {
        if (!skipper) {
            Color text;
            String warning = "";
            if (level == 1) {
                levelUpFee = 1000;
            } else if (level == 2) {
                levelUpFee = 25000;
            }
            if (player.cashCount < levelUpFee) {
                text = Color.red;
                warning = " - Not enough cash.";
            } else {
                text = Color.green;
            }
            SaxionApp.printLine("6. Level up - $" + levelUpFee + "" + warning, text);
        }
        if (skipper) {
            if (player.cashCount < levelUpFee) {
                shopMenu();
            }
            SaxionApp.printLine();
            SaxionApp.printLine("Are you sure you want to level up? [Y/N]");
            char entry = SaxionApp.readChar();
            do {
                entry = Character.toUpperCase(entry);
                switch (entry) {
                    case 'Y' -> {
                        player.cashCount -= levelUpFee;
                        level++;
                        for (Tile tile : tiles) {
                            if (tile.level <= level) {
                                tile.occupied[0] = false;
                            }
                        }
                        SaxionApp.printLine("Successfully leveled up to level " + level, Color.green);
                        SaxionApp.sleep(1);
                        shopMenu();
                    }
                    case 'N' -> shopMenu();
                    default -> {
                        SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                        SaxionApp.sleep(1);
                        SaxionApp.removeLastPrint();
                        entry = SaxionApp.readChar();
                    }
                }
            } while (entry != 'Y' && entry != 'N');
        }
    }

    public void bankMenu () {
        drawBoard();
        SaxionApp.printLine("Bank of FarmX");
        SaxionApp.printLine();
        SaxionApp.printLine("Deposit your funds here and watch them grow.");
        SaxionApp.print("Current interest rate: ");
        int bankCount = 0;
        for (Tile tile: tiles) {
            if (tile.tileResourceName.equals("Bank")){
                bankCount++;
            }
        }
        int intRate = 10+((5*bankCount)-5);
        SaxionApp.print(intRate+"%",Color.yellow);
        SaxionApp.printLine();
        SaxionApp.print("Current funds at the bank: ");
        SaxionApp.print("$"+bankPot,Color.yellow);
        SaxionApp.printLine();
        SaxionApp.printLine("0. Back");
        SaxionApp.printLine("1. Deposit");
        SaxionApp.printLine("2. Withdraw");
        char selection = SaxionApp.readChar();
        switch (selection) {
            case '0':
                shopMenu();
                break;
            case '1':
                SaxionApp.printLine("Enter amount to deposit: ");
                int deposit = SaxionApp.readInt();
                if (player.cashCount < deposit){
                    SaxionApp.printLine("You do not have this much cash to deposit",Color.red);
                    SaxionApp.sleep(1);
                    bankMenu();
                } else {
                    bankPot+=deposit;
                    player.cashCount-=deposit;
                }
                SaxionApp.printLine("Deposit successful!",Color.green);
                SaxionApp.sleep(1);
                bankMenu();
                break;
            case '2':
                SaxionApp.printLine("Enter amount to withdraw: ");
                int withdrawal = SaxionApp.readInt();
                if (withdrawal > bankPot){
                    SaxionApp.printLine("You do not have this much cash to withdraw",Color.red);
                    SaxionApp.sleep(1);
                    bankMenu();
                } else {
                    bankPot-=withdrawal;
                    player.cashCount+=withdrawal;
                }
                SaxionApp.printLine("Withdrawal successful!",Color.green);
                SaxionApp.sleep(1);
                bankMenu();
                break;
        }
    }

    public void marketMenu () {
        drawBoard();
        SaxionApp.printLine("Welcome to the Market!");
        SaxionApp.printLine();

        SaxionApp.printLine("Today there are buyers for "+saleCrops[0].cropName+" and "+saleCrops[1].cropName+
                ", got any to sell?");
        int[] counters = {0, 0};
        ArrayList<Integer> keyPairs = new ArrayList<>();
        SaxionApp.printLine();
        SaxionApp.printLine("Player's Stock");
        SaxionApp.printLine();
        SaxionApp.printLine("0. Back");
        for (Crop crop : crops) {
            if (player.cropStock[counters[1]] > 0) {
                if (crop.cropName.equals(saleCrops[0].cropName) || crop.cropName.equals(saleCrops[1].cropName)) {
                    SaxionApp.print((counters[0] + 1) + ". " + crop.cropName + " - x" + player.cropStock[counters[1]] +
                            " - Gives ");
                    SaxionApp.print("$" + (crop.foodPayout * 5), Color.green);
                    SaxionApp.print(" each");
                    SaxionApp.printLine();
                    keyPairs.add(counters[1]);
                    counters[0]++;
                }
            }
            counters[1]++;
        }
        char selection = SaxionApp.readChar();
        if ((selection - 48) < 0 || (selection - 48) > counters[0] + 1) {
            SaxionApp.printLine("Invalid selection", Color.red);
            SaxionApp.sleep(1);
            marketMenu();
        } else {
            if (selection - 48 == 0) {
                shopMenu();
            }
            SaxionApp.printLine();
            int quantity;
            do {
                SaxionApp.printLine("Quantity?");
                quantity = SaxionApp.readInt();
                if (player.cropStock[keyPairs.get(selection - 49)] < quantity) {
                    SaxionApp.printLine("You do not have this many to convert", Color.red);
                    SaxionApp.sleep(1);
                    for (int i = 0; i < 3; i++) {
                        SaxionApp.removeLastPrint();
                    }
                } else if (quantity == 0) {
                    SaxionApp.printLine("Enter more than 0", Color.red);
                    SaxionApp.sleep(1);
                    for (int i = 0; i < 3; i++) {
                        SaxionApp.removeLastPrint();
                    }
                }
            } while (player.cropStock[keyPairs.get(selection - 49)] < quantity || quantity == 0);
            SaxionApp.printLine();
            SaxionApp.printLine("Confirm sale of " + quantity + "x " + crops[keyPairs.get(selection - 49)].cropName + "? [Y/N]", Color.yellow);
            char entry = SaxionApp.readChar();
            do {
                entry = Character.toUpperCase(entry);
                switch (entry) {
                    case 'Y' -> {
                        String cropSold = quantity + "x " + crops[keyPairs.get(selection - 49)].cropName;
                        statistics.cropSell.add(cropSold);
                        emptyList2 = true;
                        player.cashCount += quantity * (crops[keyPairs.get(selection - 49)].foodPayout*5);
                        player.cropStock[keyPairs.get(selection - 49)] -= quantity;
                        SaxionApp.printLine("Successfully sold " + quantity + "x " + crops[keyPairs.get(selection - 49)].cropName, Color.green);
                        SaxionApp.sleep(1);
                        marketMenu();
                    }
                    case 'N' -> marketMenu();
                    default -> {
                        SaxionApp.printLine("Invalid selection, try again (Y/N)", Color.red);
                        SaxionApp.sleep(1);
                        SaxionApp.removeLastPrint();
                        entry = SaxionApp.readChar();
                    }
                }
            } while (entry != 'Y' && entry != 'N');
        }
    }

    public Tile[] tileSetup() {
        Tile[] tiles = new Tile[25];
        CsvReader reader = new CsvReader("resources/tileindex.csv");
        reader.skipRow();
        reader.setSeparator(',');
        int topXPos = 784;
        int topYPos = 5;
        int i = 0;
        while (reader.loadRow()) {
            Tile tile = new Tile();
            tile.tileID = reader.getString(0);
            tile.tilePosition[0] = topXPos - (i % 5) * 64;
            tile.tilePosition[1] = topYPos + (i % 5) * 32;
            tiles[reader.getInt(1)] = tile;
            tile.level = reader.getInt(2);
            if (tile.level > level) {
                tile.occupied[0] = true;
            }
            i++;
            if (i % 5 == 0) {
                topYPos = topYPos + 32;
                topXPos = topXPos + 64;
            }
        }
        return tiles;
    }

    public Crop[] cropSetup() {
        Crop[] crops = new Crop[8];
        CsvReader reader = new CsvReader("resources/crops.csv");
        reader.skipRow();
        reader.setSeparator(',');
        while (reader.loadRow()) {
            Crop crop = new Crop();
            crop.cropPos = reader.getInt(1);
            crop.cropName = reader.getString(0);
            crop.cost = reader.getInt(2);
            crop.dayCountdown = reader.getInt(3);
            crop.foodPayout = reader.getInt(4);
            crop.season = reader.getInt(5);
            crop.seededImage = reader.getString(6);
            crop.step1Image = reader.getString(7);//Jacques
            crop.finalImage = reader.getString(8);//Jacques
            crops[reader.getInt(1)] = crop;
        }
        return crops;
    }

    public Animal[] animalSetup() {
        Animal[] newAnimals = new Animal[4];
        CsvReader reader = new CsvReader("resources/animals.csv");
        reader.skipRow();
        reader.setSeparator(',');
        while (reader.loadRow()) {
            Animal animal = new Animal();
            animal.animalName = reader.getString(0);
            animal.cost = reader.getInt(1);
            animal.cropRequirement[0] = reader.getInt(2);
            animal.quantity[0] = reader.getInt(3);
            animal.cropRequirement[1] = reader.getInt(4);
            animal.quantity[1] = reader.getInt(5);
            animal.foodPayout = reader.getInt(6);
            animal.cashPayout = reader.getInt(7);
            animal.infoMessage = reader.getString(9);
            animal.babyImage = reader.getString(10);//Jacques
            animal.teenImage = reader.getString(11);
            animal.fullyImage = reader.getString(12);
            int position = reader.getInt(8);
            newAnimals[position] = animal;
        }
        return newAnimals;
    }

    public Property[] propertySetup() {
        Property[] properties = new Property[4];
        CsvReader reader = new CsvReader("resources/properties.csv");
        reader.skipRow();
        reader.setSeparator(',');
        while (reader.loadRow()) {
            Property property = new Property();
            property.propertyName = reader.getString(0);
            property.cashCost = reader.getInt(1);
            property.foodCost = reader.getInt(2);
            property.cashPayout = reader.getInt(3);
            property.position = reader.getInt(4);
            property.infoMessage = reader.getString(5);
            property.propertyImage = reader.getString(6);
            properties[property.position] = property;
        }
        return properties;
    }

    public void messageBox(String message, int x, int y) {
        SaxionApp.setFill(Color.white);
        SaxionApp.drawBorderedText(message, x, y, 18);
    }

    public void nextDay() {
        SaxionApp.printLine("Are you sure you want to go to the next day?(Y/N)", Color.yellow);
        char selection = SaxionApp.readChar();
        selection = Character.toUpperCase(selection);
        if (selection == 'Y') {
            dayCount++;
            drawsun();
            changeseason();
            for (Tile tile : tiles) {
                if (!tile.tileResourceName.equalsIgnoreCase("Default")) {
                    if (tile.dayCountdown == 0) {
                        for (Crop crop : crops) {
                            if (crop.cropName.contains(tile.tileResourceName)) {
                                int stockpos = crop.cropPos;
                                statistics.cropHarvestedName.add(tile.tileResourceName);
                                statistics.cropStock[stockpos] += 1;
                                if (tile.upgradeLevel > 0) {
                                    player.cropStock[stockpos] += 3 * tile.upgradeLevel;
                                    tile.upgradeLevel = 0;
                                } else {
                                    player.cropStock[stockpos] += 1;
                                }
                                tile.dayCountdown = 0;
                                tile.tileResourceName = "Default";
                                tile.dayCounter = 0;
                                tile.occupied[1] = false;
                                tile.crop = null;
                            }
                        }
                    }
                    if (tile.dayCountdown > 0) {
                        tile.dayCountdown -= 1;
                    }
                }
            }
            for (Tile tile : tiles) {
                tile.dayCounter++;
                if (!tile.tileResourceName.equalsIgnoreCase("Default")) {
                    if (tile.dayCountdown > 0) {
                        tile.dayCountdown -= 1;
                    }
                    for (Animal animal : animals) {
                        if (animal.animalName.contains(tile.tileResourceName)) {
                            if (player.cropStock[animal.cropRequirement[0]] >= animal.quantity[0]) {
                                player.cropStock[animal.cropRequirement[0]] -= animal.quantity[0];
                                player.foodCount += animal.foodPayout;
                            } else if (player.cropStock[animal.cropRequirement[1]] >= animal.quantity[1]) {
                                player.cropStock[animal.cropRequirement[1]] -= animal.quantity[1];
                                player.foodCount += animal.foodPayout;
                            } else {
                                tile.dayCountdown = 0;
                                tile.cropRequirement[0] = 0;
                                tile.cropRequirement[1] = 0;
                                tile.cropQuantity[0] = 0;
                                tile.cropQuantity[1] = 0;
                                tile.tileResourceName = "Default";
                                tile.occupied[1] = false;
                                tile.crop = null;
                                tile.animal = null;
                                tile.dayCounter = 0;
                            }
                        }
                    }
                    if (tile.dayCountdown == 0) {
                        for (Animal animal : animals) {
                            if (animal.animalName.contains(tile.tileResourceName)) {
                                if (animal.animalName.equals("Pig")) {
                                    player.foodCount += animal.foodPayout;
                                    tile.dayCountdown = 0;
                                    tile.dayCounter = 0;
                                    tile.cropRequirement[0] = 0;
                                    tile.cropRequirement[1] = 0;
                                    tile.cropQuantity[0] = 0;
                                    tile.cropQuantity[1] = 0;
                                    tile.tileResourceName = "Default";
                                    tile.occupied[1] = false;
                                    drawTiles();
                                } else if (animal.animalName.equals("Chicken")) {
                                    player.foodCount += animal.foodPayout;
                                    tile.dayCountdown = 1;
                                    drawTiles();
                                }
                            }
                        }
                    } else if (tile.dayCountdown == -1) {
                        player.foodCount += tile.foodPayout;
                        player.cashCount += tile.cashPayout;
                    }
                }
            }
            int bankCounter = 0;
            for (Tile tile : tiles) {
                for (Property property : properties) {
                    if (tile.tileResourceName.equals(property.propertyName)) {
                        if (player.foodCount >= property.foodCost) {
                            player.cashCount += property.cashPayout;
                            player.foodCount -= property.foodCost;
                            if (property.propertyName.equals("Bank")) {
                                bankCounter++;
                            }
                        } else {
                            tile.tileResourceName = "Default";
                            tile.foodCost = 0;
                            tile.cashPayout = 0;
                            tile.occupied[1] = false;
                            tile.crop = null;
                            tile.animal = null;
                            tile.property = null;
                            tile.dayCounter = 0;
                        }
                    }
                }
            }
            if (bankCounter == 0 && bankPot > 0) {
                player.cashCount += bankPot;
                bankPot = 0;
            } else if (bankCounter > 0 && bankPot > 0) {
                double potPercentage = (double) bankPot / 100;
                bankPot += (int) potPercentage * 10;
                for (int i = bankCounter; i > 1; i--) {
                    bankPot += (int) potPercentage * 5;
                }
            }
            for (int i = 0; i < 2; i++) {
                int cropNo = SaxionApp.getRandomValueBetween(0,8);
                saleCrops[i] = crops[cropNo];
            }
            SaxionApp.pause();
            checkForEnd();//Jacques
            shopMenu();
        } else if (selection == 'N') {
            shopMenu();
        } else {
            shopMenu();
        }
    }

    public void drawstatistic() {
        SaxionApp.clear();
        SaxionApp.setFill(Color.gray);
        SaxionApp.drawRectangle(x, y, 850, 400);
        plantcropstat();
        sellFoodstat();
        sellCropstat();
        harvestCrop();
    }

    public void checkForEnd(){//Jacques
        int i = 0;
        int j = 0;
        for (Tile tile: tiles) {
            if (!tile.tileResourceName.equals("Default")){
                i++;
            }
        }
        for (int k = 0; k < player.cropStock.length; k++) {
            j += player.cropStock[k];
        }
        if (player.cashCount >= 1000000){
            SaxionApp.clear();
            SaxionApp.drawText("Player wins",100,100,30);
            SaxionApp.drawText("You beat the game in "+dayCount+" days.",100,150,30);
            gameOver = true;
            SaxionApp.pause();
            shopMenu();
        }else if (player.cashCount < 8 && player.foodCount < 8 && i == 0 && j == 0){
            SaxionApp.clear();
            SaxionApp.drawText("Player loses",100,100,30);
            SaxionApp.drawText("You survived "+dayCount+" days.",100,150,30);
            gameOver = true;
            SaxionApp.pause();
            shopMenu();
        }
    }
    //plant crops stat
    public void plantcropstat() {
        int y1 = y + 50;
        if (!emptyList1) {
            String sumPlant = "Yesterday, you didn't buy anything";
            SaxionApp.drawText(sumPlant, x + 50, y + 50, fontSize);
            removeDraw();
        } else {
            for (int i = 0; i < statistics.plantBuy.size(); i++) {
                String sumPlant = "Yesterday, you bought " + statistics.plantBuy.get(i);
                SaxionApp.drawText(sumPlant, x + 50, y1, fontSize);
                SaxionApp.sleep(1.5 / 8);
                y1 += 30;
            }
            emptyList1 = false;
            for (int i = 0; i < statistics.plantBuy.size(); i++) {
                removeDraw();
            }
        }
        statistics.plantBuy = new ArrayList<>();
    }

    //sellfood stat
    public void sellFoodstat() {
        if (statistics.foodSell == 0) {
            String sumFood = "Yesterday, you didn't sell foods";
            SaxionApp.drawText(sumFood, x + 50, y + 50, fontSize);
            removeDraw();
        } else {
            String sumFood = "Yesterday, you sold " + statistics.foodSell + " foods";
            SaxionApp.drawText(sumFood, x + 50, y + 50, fontSize);
            statistics.foodSell = 0;
            removeDraw();
        }
    }

    //sell crop for food stat
    public void sellCropstat() {
        int y1 = y + 50;
        if (!emptyList2) {
            String cropSold = "Yesterday, you didn't sell any crops";
            SaxionApp.drawText(cropSold, x + 50, y1, fontSize);
            removeDraw();
        } else {
            for (int i = 0; i < statistics.cropSell.size(); i++) {
                if (statistics.cropSell.get(i).equalsIgnoreCase("Yesterday, you sold all your crops")) {
                    String cropSold = "Yesterday, you sold all your crops";
                    SaxionApp.drawText(cropSold, x + 50, y1, fontSize);
                } else {
                    String cropSold = "Yesterday, you sold " + statistics.cropSell.get(i);
                    SaxionApp.drawText(cropSold, x + 50, y1, fontSize);
                    SaxionApp.sleep(1.5);
                    y1 += 30;
                }
            }
            emptyList2 = false;
            for (int i = 0; i < statistics.cropSell.size(); i++) {
                removeDraw();
            }
            statistics.cropSell = new ArrayList<>();
        }
    }

    //harvest crop
    public void harvestCrop() {
        int y1 = y + 50;
        int[] array = {0, 0, 0, 0, 0, 0, 0, 0};
        if (Arrays.equals(statistics.cropStock, array)) {
            String cropHarvested = "Yesterday, you didn't harvest anything";
            SaxionApp.drawText(cropHarvested, x + 50, y + 50, 13);
        } else {
            for (int i = 0; i < statistics.cropStock.length; i++) {
                if (statistics.cropStock[i] != 0) {
                    /*String cropHarvested = "Yesterday, you harvested " + statistics.cropStock[i] + " x" + statistics.cropHarvestedName.get(i);
                    SaxionApp.drawText(cropHarvested, x + 50, y1, 13);*/
                    y1 += 50;
                }
            }
        }
        statistics.cropHarvestedName = new ArrayList<>();
    }

    public void removeDraw() {
        SaxionApp.sleep(1.5);
        SaxionApp.removeLastDraw();
    }

    /*public void drawsun() {
        SaxionApp.clear();
        ArrayList<Color> sunrisecolor = new ArrayList<>();
        sunrisecolor.add(Color.decode("#A1BFFF"));
        sunrisecolor.add(Color.decode("#DFE7FF"));
        sunrisecolor.add(Color.decode("#FFF9FF"));
        sunrisecolor.add(Color.decode("#FFF5F6"));
        sunrisecolor.add(Color.decode("#FFF0E8"));
        sunrisecolor.add(Color.decode("#FFE4CC"));
        sunrisecolor.add(Color.decode("#FFBF7E"));
        sunrisecolor.add(Color.decode("#FFBA75"));
        sunrisecolor.add(Color.decode("#FFB369"));
        sunrisecolor.add(Color.decode("#FFA855"));
        sunrisecolor.add(Color.decode("#FFA24A"));
        sunrisecolor.add(Color.decode("#FF9C3E"));
        sunrisecolor.add(Color.decode("#FF8100"));
        sunrisecolor.add(Color.decode("#FF7900"));
        int y = 400;
        int i = 0;
        while (y >= 100) {
            Color sky = sunrisecolor.get(i);
            SaxionApp.setBackgroundColor(sky);
            SaxionApp.removeLastDraw();
            SaxionApp.removeLastDraw();
            SaxionApp.drawImage("resources/sun.png", 550, y, 100, 100);
            SaxionApp.drawImage("resources/images.png", -5, 400, 1205, 200);
            SaxionApp.sleep(0.1);
            y -= 20;
            i++;
            if (i > 13) {
                i = 13;
            }
        }
    }*/
    public void drawsun() {
        SaxionApp.clear();
        ArrayList<Color> sunrisecolor = new ArrayList<>();
        addSuncolor("#A1BFFF", sunrisecolor);
        addSuncolor("#DFE7FF", sunrisecolor);
        addSuncolor("#FFF9FF", sunrisecolor);
        addSuncolor("#FFF5F6", sunrisecolor);
        addSuncolor("#FFF0E8", sunrisecolor);
        addSuncolor("#FFE4CC", sunrisecolor);
        addSuncolor("#FFBF7E", sunrisecolor);
        addSuncolor("#FFBA75", sunrisecolor);
        addSuncolor("#FFB369", sunrisecolor);
        addSuncolor("#FFA855", sunrisecolor);
        addSuncolor("#FFA24A", sunrisecolor);
        addSuncolor("#FF9C3E", sunrisecolor);
        addSuncolor("#FF8100", sunrisecolor);
        addSuncolor("#FF7900", sunrisecolor);
        ArrayList<Color> nightskyColor = new ArrayList<>();
        addNightcolor("#131862", nightskyColor);
        addNightcolor("#2e4482", nightskyColor);
        addNightcolor("#546bab", nightskyColor);
        addNightcolor("#87889c", nightskyColor);
        addNightcolor("#bea9de", nightskyColor);
       /* for (int i = 0; i<14;i++){
            int x = blockX*i;
            int y = blockY*i;
            Color sky = sunrisecolor.get(i);
            SaxionApp.setBackgroundColor(sky);
            //SaxionApp.removeLastDraw();
            //SaxionApp.removeLastDraw();![](../../../../Downloads/pngtree-moon-png-image_2434780-removebg-preview.png)
            SaxionApp.drawImage("resources/sun.png",x, y, 100, 100);
            SaxionApp.drawImage("resources/images.png", -5, 400, 1205, 200);
            SaxionApp.sleep(0.1);
        }
        double z = Math.pow(x-600,2);
        double doubley = Math.pow(600,2)-z;
        double doubleyy = Math.sqrt(doubley);
        double yy = doubleyy + 600;
        System.out.println(z);
        System.out.println(doubley);
        System.out.println(doubleyy);
        System.out.println(yy);
        SaxionApp.pause();
*/
        float x = 1;
        System.out.println(nightskyColor.size());
        while (x <= 1200) {
            double z = Math.pow(x - 600, 2);
            double doubley = Math.pow(600, 2) - z;
            double doubleyy = Math.sqrt(doubley);
            double yy = 600 - doubleyy;
            SaxionApp.removeLastDraw();
            SaxionApp.removeLastDraw();
            if (x < 600) {
                if (x < 43) {
                    Color sky = sunrisecolor.get(0);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 43) {
                    Color sky = sunrisecolor.get(1);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 86) {
                    Color sky = sunrisecolor.get(2);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 129) {
                    Color sky = sunrisecolor.get(3);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 172) {
                    Color sky = sunrisecolor.get(4);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 215) {
                    Color sky = sunrisecolor.get(5);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 258) {
                    Color sky = sunrisecolor.get(6);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 301) {
                    Color sky = sunrisecolor.get(7);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 344) {
                    Color sky = sunrisecolor.get(8);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 387) {
                    Color sky = sunrisecolor.get(9);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 430) {
                    Color sky = sunrisecolor.get(10);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 473) {
                    Color sky = sunrisecolor.get(11);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 516) {
                    Color sky = sunrisecolor.get(12);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 559) {
                    Color sky = sunrisecolor.get(13);
                    SaxionApp.setBackgroundColor(sky);
                }
                SaxionApp.drawImage("resources/sun.png", (int) x, (int) yy, 100, 100);
                SaxionApp.drawImage("resources/images.png", -5, 400, 1205, 200);
                SaxionApp.sleep(0.05);
            } else {
                if (x > 600) {
                    Color sky = nightskyColor.get(0);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 720) {
                    Color sky = nightskyColor.get(1);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 840) {
                    Color sky = nightskyColor.get(2);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 960) {
                    Color sky = nightskyColor.get(3);
                    SaxionApp.setBackgroundColor(sky);
                }
                if (x > 1080) {
                    Color sky = nightskyColor.get(4);
                    SaxionApp.setBackgroundColor(sky);
                }
                SaxionApp.drawImage("resources/moon.png", (int) x, (int) yy, 100, 100);
                SaxionApp.drawImage("resources/images.png", -5, 400, 1205, 200);
                SaxionApp.sleep(0.05);
            }
            x += 10;
        }
    }

    public static void addSuncolor(String hexcode, ArrayList<Color> colors) {
        for (int i = 0; i < 1; i++) {
            colors.add(Color.decode(hexcode));
        }
    }

    public static void addNightcolor(String hexcode, ArrayList<Color> colors) {
        for (int i = 0; i < 1; i++) {
            colors.add(Color.decode(hexcode));
        }
    }

    public void changeseason() {
        if (dayCount % 30 == 1) {
            season++;
            if (season > 4) {
                season = 1;
            }
        }
    }

    public void clearTile() {
        boolean accepted = false;
        while (!accepted) {
            SaxionApp.printLine("Enter tile coordinates (number then letter)");
            String selection = SaxionApp.readString();
            if (selection.length() != 2) {
                SaxionApp.printLine("Only enter two characters (e.g 3C)", Color.red);
                SaxionApp.sleep(1);
                for (int i = 0; i < 3; i++) {
                    SaxionApp.removeLastPrint();
                }
            } else {
                String letterUppercase = selection.substring(1, 2).toUpperCase();
                String[] acceptedValues = {"12345", "ABCDE"};
                if (!acceptedValues[0].contains(selection.substring(0, 1)) ||
                        !acceptedValues[1].contains(letterUppercase)) {
                    SaxionApp.printLine("Invalid selection", Color.red);
                    SaxionApp.sleep(1);
                    for (int i = 0; i < 3; i++) {
                        SaxionApp.removeLastPrint();
                    }
                } else {
                    String ID = selection.charAt(0) + letterUppercase;
                    for (Tile tile : tiles) {
                        if (tile.tileID.equals(ID)) {
                            if (tile.occupied[1]) {
                                tile.dayCountdown = 0;
                                tile.cropRequirement[0] = 0;
                                tile.cropRequirement[1] = 0;
                                tile.cropQuantity[0] = 0;
                                tile.cropQuantity[1] = 0;
                                tile.tileResourceName = "Default";
                                tile.occupied[1] = false;
                                tile.crop = null;
                                tile.animal = null;
                                tile.dayCounter = 0;
                                SaxionApp.printLine("Tile cleared", Color.green);
                                SaxionApp.sleep(1);
                                shopMenu();
                            } else if (tile.occupied[0] || !tile.occupied[1]) {
                                SaxionApp.printLine("Tile is empty or locked", Color.red);
                                SaxionApp.sleep(1);
                                shopMenu();
                            }
                        }
                    }
                }
            }
        }
    }
    public void option(){
        SaxionApp.printLine("This is the option menu");
        SaxionApp.printLine("1. Print statistics");
        SaxionApp.printLine("2. Plant manually or automatically");
        SaxionApp.printLine("3. Clear tiles");
        char selection = SaxionApp.readChar();
        switch (selection){
            case '1':
                printConsumtion();
                break;
            case '2':
                automaticPlant();
                option();
                break;
            case '3':
                clearTile();
                option();
                break;
        }
    }
    public void printConsumtion() {
        //print the name and number of the animals
        //print the food consumtion
        SaxionApp.printLine("Your farm's statistics");
        scanTile();
    }
    public void scanTile(){
        int multiply = 1;
        ArrayList<String> tileNamenotdouble = new ArrayList<>();
        ArrayList<String> emptyTile = new ArrayList<>();
        ArrayList<String> animalCroprequired = new ArrayList<>();
        ArrayList<String> animalConsuption = new ArrayList<>();
        for (Tile tile:tiles) {
                if (tile.occupied[0]==false){
                    if (tile.occupied[1]==true){
                        if (!tileNamenotdouble.contains(tile.tileResourceName)){
                            tileNamenotdouble.add(tile.tileResourceName);
                        }
                        if (animals!=null){
                            String cropRequired1 = tile.cropQuantity[0]*multiply + "x "+crops[tile.cropRequirement[0]].cropName +" "+ tile.cropQuantity[1]*multiply + "x "+crops[tile.cropRequirement[1]].cropName;
                            if (!animalCroprequired.contains(cropRequired1)){
                                animalCroprequired.add(cropRequired1);
                                multiply++;
                            }
                        }
                    }else if (tile.occupied[1]==false){
                        emptyTile.add("Nothing here");
                    }
                }
        }
        String cropRequired2 = animalCroprequired.get(multiply-2);
        animalConsuption.add(cropRequired2);
        for(int i=0;i<tileNamenotdouble.size();i++){
            SaxionApp.printLine("You have "+tileNamenotdouble.get(i),Color.yellow);
        }
        for(int i=0;i<animalConsuption.size();i++){
            SaxionApp.printLine("Food consumption "+animalConsuption.get(i),Color.yellow);
        }
        if (emptyTile.size()==5){
            SaxionApp.printLine(emptyTile.get(1),Color.red);
        }
        SaxionApp.pause();
        option();
    }
    public void automaticPlant(){
        SaxionApp.printLine("Do you want to plant manually or automatically?");
        String harvest = SaxionApp.readString();
        if (harvest.equalsIgnoreCase("manually")){
            player.automaticPopulation=false;
        }else if (harvest.equalsIgnoreCase("automatically")){
            player.automaticPopulation=true;
        }
    }

    ///////////////CASINO METHODS/////////////////
    public void casinoMenu(int pot) {
        int dealerScore;
        int playerScore;
        int turnCounter;
        boolean dealerTurn;

        ArrayList<Suit> createSuits = createSuits();
        ArrayList<Values> assignValues = assignValues();

        char exit = 'z';
        while (pot > 0 && exit != 'x') { //start of game loop
            dealerScore = 0;
            playerScore = 0;
            turnCounter = 1;
            dealerTurn = false;
            int[] aceCounts = {0, 0};
            int bet = drawAndBet(pot);

            for (int i = 0; i < 2; i++) { //player 2 card start
                int score = drawCards(createSuits, assignValues, dealerTurn, turnCounter, dealerScore, playerScore, aceCounts);
                playerScore += score;
                turnCounter++;
            }

            turnCounter = 1;
            dealerTurn = true; //dealer 1 card start
            int score = drawCards(createSuits, assignValues, dealerTurn, turnCounter, dealerScore, playerScore, aceCounts);
            dealerScore += score;

            SaxionApp.printLine("Player score: " + playerScore);
            SaxionApp.printLine("Dealer score: " + dealerScore);
            SaxionApp.printLine("Press [Y] to stand. Press any other key to hit.");

            exit = SaxionApp.readChar().toString().toLowerCase().charAt(0);

            dealerTurn = false;
            turnCounter = 3;
            while (exit != 'y') { //player turns
                score = drawCards(createSuits, assignValues, dealerTurn, turnCounter, dealerScore, playerScore, aceCounts);
                playerScore += score;
                SaxionApp.printLine("Player score: " + playerScore);
                if (playerScore > 21) {
                    exit = 'y';
                } else {
                    SaxionApp.printLine("Press [Y] to stand. Press any other key to hit.");
                    exit = SaxionApp.readChar().toString().toLowerCase().charAt(0);
                    turnCounter++;
                }
            }
            for (int i = 0; i < 20; i++) {//clear old prints for dealer turn
                SaxionApp.removeLastPrint();
            }
            dealerTurn = true;
            turnCounter = 2;
            while (dealerScore < playerScore && playerScore <= 21) {//dealers turn
                SaxionApp.sleep(1);
                score = drawCards(createSuits, assignValues, dealerTurn, turnCounter, dealerScore, playerScore, aceCounts);
                dealerScore += score;
                turnCounter++;
            }
            if (dealerScore >= playerScore && dealerScore <= 21 || playerScore > 21) {
                SaxionApp.printLine("Dealer wins!");
                SaxionApp.printLine("Dealer score: " + dealerScore + ", Player score: " + playerScore);
                pot = pot - bet;//player must beat house to win in this casino. If tie, dealer wins.
            } else {
                SaxionApp.printLine("Player wins!");
                SaxionApp.printLine("Player score: " + playerScore + ", Dealer score: " + dealerScore);
                pot = pot + bet;
            }
            SaxionApp.pause();
        }
        SaxionApp.clear();//end game
        SaxionApp.drawImage("resources/background.png", 0, 0, 1100, 700);
        SaxionApp.drawBorderedText("Y'er broke! Get outta here!", 200, 350, 60);
        shopMenu();
    }

    public int drawAndBet(int pot) {
        SaxionApp.clear();
        String potDisplay = Integer.toString(pot);
        SaxionApp.drawImage("resources/background.png", 0, 0, 1200, 600);
        SaxionApp.drawBorderedText("€" + potDisplay, 900, 100, 60);
        SaxionApp.drawBorderedText("Player", 880, 40, 60);

        SaxionApp.printLine("Enter your bet, or enter 0 to exit the Casino.");
        int betEntry = SaxionApp.readInt();
        if (betEntry == 0) {
            player.cashCount += pot;
            shopMenu();
        }
        String betDisplay = Integer.toString(betEntry);
        SaxionApp.drawBorderedText("+/-" + betDisplay, 880, 150, 30);
        SaxionApp.removeLastPrint();
        SaxionApp.removeLastPrint();
        return betEntry;
    }

    public ArrayList<Suit> createSuits() {
        ArrayList<Suit> result = new ArrayList<>();

        Suit clubs = new Suit();
        clubs.name = "C";
        clubs.symbol = "♣";
        result.add(clubs);

        Suit spades = new Suit();
        spades.name = "S";
        spades.symbol = "♠";
        result.add(spades);

        Suit hearts = new Suit();
        hearts.name = "H";
        hearts.symbol = "♥";
        result.add(hearts);

        Suit diamonds = new Suit();
        diamonds.name = "D";
        diamonds.symbol = "♦";
        result.add(diamonds);

        return result;
    }

    public ArrayList<Values> assignValues() {
        ArrayList<Values> cardValue = new ArrayList<>();
        for (int i = 2; i < 11; i++) {
            Values values = new Values();
            values.value = (i);
            values.card = Integer.toString(i);
            cardValue.add(values);
        }

        Values ace = new Values();
        ace.value = 11;
        ace.card = "A";
        cardValue.add(ace);

        Values jack = new Values();
        jack.value = 10;
        jack.card = "J";
        cardValue.add(jack);

        Values queen = new Values();
        queen.value = 10;
        queen.card = "Q";
        cardValue.add(queen);

        Values king = new Values();
        king.value = 10;
        king.card = "K";
        cardValue.add(king);

        return cardValue;
    }

    public int drawCards(ArrayList<Suit> result, ArrayList<Values> cardValue, boolean dealerTurn,
                         int turnCounter, int dealerScore, int playerScore, int[] aceCounts) {
        int random1 = SaxionApp.getRandomValueBetween(0, 3);

        Suit chosenSuit = result.get(random1);
        String suitLetter = chosenSuit.name;
        String suitSymbol = chosenSuit.symbol;

        int random2 = SaxionApp.getRandomValueBetween(0, 13);
        Values chosenValue = cardValue.get(random2);
        String chosenCard = chosenValue.card;
        int chosenNumber = chosenValue.value;

        if (!dealerTurn && chosenCard.equals("A")) {
            aceCounts[0] = aceCounts[0] + 1;
        } else if (dealerTurn && chosenCard.equals("A")) {
            aceCounts[1] = aceCounts[1] + 1;
        }

        String player = "Player";
        if (dealerTurn) {
            player = "Dealer";
        }
        SaxionApp.printLine("New Card: " + chosenCard + suitSymbol + " for " + player, Color.green);

        int cardWidth = 100;
        int cardHeight = 200;

        if (!dealerTurn) {
            SaxionApp.drawImage("resources/cardimages/" + chosenCard + suitLetter + ".png"
                    , ((cardWidth * turnCounter) + 20), 400, cardWidth, cardHeight);
            SaxionApp.drawImage("resources/cardimages/purple_back.png"
                    , ((cardWidth * (turnCounter + 1)) + 20), 400, cardWidth, cardHeight);
        } else {
            SaxionApp.drawImage("resources/cardimages/" + chosenCard + suitLetter + ".png"
                    , ((cardWidth * turnCounter) + 20), 200, cardWidth, cardHeight);
            SaxionApp.drawImage("resources/cardimages/purple_back.png"
                    , ((cardWidth * (turnCounter + 1)) + 20), 200, cardWidth, cardHeight);
        }
        if (!dealerTurn && (playerScore + chosenNumber) > 21 && aceCounts[0] > 0) {
            chosenNumber = chosenNumber - 10;
            SaxionApp.printLine("Busted with soft ace, ace value converted to 1");
            aceCounts[0] = aceCounts[0] - 1;
        } else if (dealerTurn && (dealerScore + chosenNumber) > 21 && aceCounts[1] > 0) {
            chosenNumber = chosenNumber - 10;
            SaxionApp.printLine("Busted with soft ace, ace value converted to 1");
            aceCounts[1] = aceCounts[1] - 1;
        }
        return chosenNumber;
    }
    //////////////////////////////////////////////////
}
