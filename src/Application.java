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
    int level = 2;// level of player game 1-3
    Statistics statistics = new Statistics();
    boolean emptyList1 = false;
    boolean emptyList2 = false;
    int y = 100;
    int x = 150;
    int fontSize = 13;
    Tile[] tiles;
    Crop[] crops;
    Animal[] animals;

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
        mainMenu();
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

    //Step1.1 mainmenu(Harun)
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

    }

    //Step1.2 shopmenu(Harun)
    public void shopMenu() {
        drawBoard();
        SaxionApp.printLine("STORE");
        SaxionApp.printLine(" ");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("1. Crops");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("2. Animals");
        SaxionApp.printLine(" ");
        SaxionApp.printLine("3. GO TO THE NEXT DAY >>>");
        SaxionApp.printLine(" ");
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
                }
                break;
            //2.animals
            case '2':
                animalsMenu();
                break;
            case '3':
                nextDay();
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
        SaxionApp.drawText("For the begining, I would like to introduce the game.", x + 30, y + 120, 13);
        SaxionApp.drawText("*This game is a farming simulating game and this game is based on getting daily harvesting", x + 30, y + 140, 13);
        SaxionApp.drawText("*The game gives 5000$ to at the beginning and you can buy vegetables seeds and animals with this money.", x + 30, y + 160, 13);
        SaxionApp.drawText("*Every seeds have different features. For example, if you buy a tomato seed for your field, firstly you have to pay 10$ for a seed.", x + 30, y + 180, 13);
        SaxionApp.drawText("A tomato seed grows up in one day. When you continue for the other day, you'll get 20$ for harvest.", x + 30, y + 200, 13);
        SaxionApp.drawText("*You are going to start the game at LEVEL 1. This means you can buy only first 4 types of vegetables seeds. And the game", x + 30, y + 220, 13);
        SaxionApp.drawText("gives 4 empty fields for you. You will get new 2 fields at further levels and also you can unlock a field by purchasing.", x + 30, y + 240, 13);
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
        messageBox("Day: " + (dayCount), 800, 460);
        messageBox("Food: " + (player.foodCount), 800, 500);
        messageBox("Cash: " + (player.cashCount), 800, 540);
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
                        statistics.foodSell+=selection;
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
        SaxionApp.printLine(player.name + "'s Stock");
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
                        String cropSold= quantity + "x " + crops[keyPairs.get(selection - 49)].cropName;
                        statistics.cropSell.add(cropSold);
                        emptyList2=true;
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
                    SaxionApp.printLine("Enter more than 0", Color.red);
                    SaxionApp.sleep(1);
                    SaxionApp.clear();
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
        for (Animal animal: animals) {
            SaxionApp.printLine(i+". "+animal.animalName+" $"+animal.cost+"",Color.yellow);
            SaxionApp.printLine("Consumes "+animal.quantity[0]+"x "+crops[animal.cropRequirement[0]].cropName
            +" or "+animal.quantity[1]+"x "+crops[animal.cropRequirement[1]].cropName+" per day",Color.red);
            SaxionApp.printLine(animal.infoMessage,Color.green);
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

    public void assignToTile(char indexNumber, int quantity, int identifier) {
        drawBoard();
        int count = quantity;
        Crop crop = crops[indexNumber-49];//Jacques. Get the crop to be placed on tile
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
                                if (tile.occupied[0] || tile.occupied[1]) {
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
                                        tile.crop = crop;//Jacques. Set crop on tile
                                        if (crops[indexNumber - 49].season != season) {
                                            if (crops[indexNumber - 49].dayCountdown == 1) {
                                                tile.dayCountdown++;
                                            } else {
                                                tile.dayCountdown = (int) ((crops[indexNumber - 49].dayCountdown) * 1.5);
                                            }
                                        }
                                        tile.season = crops[indexNumber - 49].season;
                                        drawTiles();
                                        SaxionApp.printLine(crops[indexNumber - 49].cropName + " successfully planted", Color.green);
                                        SaxionApp.sleep(1);
                                        for (int i = 0; i < 3; i++) {
                                            SaxionApp.removeLastPrint();
                                        }
                                        player.cashCount -= crops[indexNumber - 49].cost;
                                        if (count == 1) {
                                            accepted = true;
                                            shopMenu();
                                        } else {
                                            count--;
                                        }
                                    }
                                    else if (identifier == 2){
                                        tile.occupied[1] = true;
                                        tile.tileResourceName = animals[indexNumber - 49].animalName;
                                        tile.dayCountdown = animals[indexNumber-49].dayCountdown;
                                        tile.foodPayout = animals[indexNumber-49].foodPayout;
                                        tile.cashPayout = animals[indexNumber-49].cashPayout;
                                        tile.cropRequirement[0] = animals[indexNumber-49].cropRequirement[0];
                                        tile.cropRequirement[1] = animals[indexNumber-49].cropRequirement[1];
                                        tile.cropQuantity[0] = animals[indexNumber-49].quantity[0];
                                        tile.cropQuantity[1] = animals[indexNumber-49].quantity[1];
                                        player.cashCount -= animals[indexNumber-49].cost;
                                        SaxionApp.printLine(animals[indexNumber - 49].animalName + " successfully herded", Color.green);
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
                if (!tile.occupied[0] && !tile.occupied[1] && count > 0) {
                    if (identifier == 1) {
                        tile.occupied[1] = true;
                        tile.crop = crop;
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
                        drawTiles();
                        SaxionApp.printLine(crops[indexNumber - 49].cropName + " successfully planted", Color.green);
                        SaxionApp.sleep(0.5);
                        player.cashCount -= crops[indexNumber - 49].cost;
                        count--;
                    } else if (identifier == 2) {
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
                    }
                }
            }
            shopMenu();
        }
    }

    public Tile[] tileSetup() {
        Tile[] tiles = new Tile[25];
        CsvReader reader = new CsvReader("resources/tileindex.csv");
        reader.skipRow();
        reader.setSeparator(',');
        int topXPos = 784;
        int topYPos = 58;
        int i = 0;
        while (reader.loadRow()) {
            Tile tile = new Tile();
            tile.tileID = reader.getString(0);
            tile.tilePosition[0] = topXPos - (i % 5) * 64;
            tile.tilePosition[1] = topYPos + (i % 5) * 32;
            tiles[reader.getInt(1)] = tile;
            tile.level = reader.getInt(2);
            if (tile.level > 2) {
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
        Animal[] animals = new Animal[4];
        CsvReader reader = new CsvReader("resources/animals.csv");
        reader.skipRow();
        reader.setSeparator(',');
        while (reader.loadRow()){
            Animal animal = new Animal();
            animal.animalName = reader.getString(0);
            animal.cost = reader.getInt(1);
            animal.cropRequirement[0] = reader.getInt(2);
            animal.quantity[0] = reader.getInt(3);
            animal.cropRequirement[1] = reader.getInt(4);
            animal.quantity[1] = reader.getInt(5);
            animal.foodPayout = reader.getInt(6);
            animal.cashPayout = reader.getInt(7);
            animal.dayCountdown = reader.getInt(8);
            animal.infoMessage = reader.getString(10);
            animals[reader.getInt(9)] = animal;
        }
        return animals;
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
                                statistics.cropStock[stockpos] +=1;
                                player.cropStock[stockpos] += 1;
                                tile.dayCountdown = 0;
                                tile.tileResourceName = "Default";
                                tile.occupied[1] = false;
                                tile.crop = null;
                                drawTiles();
                            }
                        }
                    }
                    if (tile.dayCountdown > 0) {
                        tile.dayCountdown -= 1;
                    }
                }
            }
            for (Tile tile: tiles) {
                if (!tile.tileResourceName.equalsIgnoreCase("Default")) {
                    if (tile.dayCountdown > 0) {
                        tile.dayCountdown -= 1;
                    }
                    for (Animal animal : animals) {
                        if (animal.animalName.contains(tile.tileResourceName)) {
                            if (player.cropStock[animal.cropRequirement[0]] >= animal.quantity[0]) {
                                player.cropStock[animal.cropRequirement[0]] -= animal.quantity[0];
                            } else if (player.cropStock[animal.cropRequirement[1]] >= animal.quantity[1]) {
                                player.cropStock[animal.cropRequirement[1]] -= animal.quantity[1];
                            } else {
                                tile.dayCountdown = 0;
                                tile.cropRequirement[0] = 0;
                                tile.cropRequirement[1] = 0;
                                tile.cropQuantity[0] = 0;
                                tile.cropQuantity[1] = 0;
                                tile.tileResourceName = "Default";
                                tile.occupied[1] = false;
                            }
                        }
                    }
                    if (tile.dayCountdown == 0) {
                        for (Animal animal : animals) {
                            if (animal.animalName.contains(tile.tileResourceName)) {
                                if (animal.animalName.equals("Pig")) {
                                    player.foodCount += animal.foodPayout;
                                    tile.dayCountdown = 0;
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
            drawstatistic();
            SaxionApp.pause();
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
                SaxionApp.sleep(1.5/8);
                y1 += 30;
            }
            emptyList1 = false;
            for (int i =0;i < statistics.plantBuy.size();i++){
                removeDraw();
            }
        }
        statistics.plantBuy=new ArrayList<>();
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
            statistics.foodSell=0;
            removeDraw();
        }
    }
    //sell crop for food stat
    public void sellCropstat () {
        int y1 = y+50;
        if (!emptyList2){
            String cropSold = "Yesterday, you didn't sell any crops";
            SaxionApp.drawText(cropSold, x + 50, y1, fontSize);
            removeDraw();
        }else{
            for (int i = 0; i < statistics.cropSell.size(); i++) {
                if (statistics.cropSell.get(i).equalsIgnoreCase("Yesterday, you sold all your crops")) {
                    String cropSold = "Yesterday, you sold all your crops";
                    SaxionApp.drawText(cropSold, x + 50, y1, fontSize);
                } else {
                    String cropSold = "Yesterday, you sold " + statistics.cropSell.get(i);
                    SaxionApp.drawText(cropSold, x + 50, y1, fontSize);
                    SaxionApp.sleep(1.5);
                    y1+=30;
                }
            }
            emptyList2 = false;
            for(int i = 0;i<statistics.cropSell.size();i++){
                removeDraw();
            }
            statistics.cropSell = new ArrayList<>();
        }
    }
    //harvest crop
    public void harvestCrop () {
        int y1 = y+50;
        int[] array = {0, 0, 0, 0, 0, 0, 0, 0};
        if (Arrays.equals(statistics.cropStock,array)){
            String cropHarvested = "Yesterday, you didn't harvest anything";
            SaxionApp.drawText(cropHarvested, x + 50, y + 50, 13);
        }else {
            for (int i = 0; i < statistics.cropStock.length; i++) {
                if (statistics.cropStock[i] != 0) {
                    /*String cropHarvested = "Yesterday, you harvested " + statistics.cropStock[i] + " x" + statistics.cropHarvestedName.get(i);
                    SaxionApp.drawText(cropHarvested, x + 50, y1, 13);*/
                    y1 += 50;
                }
            }
        } statistics.cropHarvestedName=new ArrayList<>();
    }
    public void removeDraw(){
        SaxionApp.sleep(1.5);
        SaxionApp.removeLastDraw();
    }

    public void drawsun() {
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
    }
    public void changeseason() {
        if (dayCount%30==1){
            season++;
            if (season>4){
                season=1;
            }
        }
    }
}