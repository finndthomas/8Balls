import nl.saxion.app.SaxionApp;

public class Tile {

    String tileID = "0z";
    boolean[] occupied = {false, false}; //[0]=Locked,[1]=Occupied
    int[] tilePosition = {0, 0}; //[0]=X,[1]=Y
    String tileResourceName = "Default";

    int dayCountdown = 0;
    int cashPayout = 0;
    int foodPayout = 0;
    int[] cropRequirement = {0, 0};
    int[] cropQuantity = {0, 0};
    int foodCost = 0;
    int upgradeLevel = 0;
    int season = 0; //1 Summer, 2 Autumn, 3 Winter, 4 Spring
    int level = 1;
    int dayCounter = 0;
    Crop crop = null;//The tile will draw the crop if he has one. "null" means he has no crop yet
    Animal animal = null;//Jacques
    Property property = null;

    public void drawTile(int drawLevel) {
        if (level <= drawLevel) {
            if (crop == null && animal == null && property == null) {
                drawDefault();
            } else if (crop != null) {
                if (dayCountdown <= 0) {
                    SaxionApp.drawImage(crop.finalImage, tilePosition[0], tilePosition[1], 135, 120);
                } else if (dayCountdown <= Math.round(50.0 * crop.dayCountdown / 100)) {
                    SaxionApp.drawImage(crop.step1Image, tilePosition[0], tilePosition[1], 135, 120);
                } else
                    SaxionApp.drawImage(crop.seededImage, tilePosition[0], tilePosition[1], 135, 120);
            } else if (animal != null) {
                if (dayCounter <= 10) {
                    SaxionApp.drawImage(animal.babyImage, tilePosition[0], tilePosition[1], 135, 120);
                } else if (dayCounter <= 20) {
                    SaxionApp.drawImage(animal.teenImage, tilePosition[0], tilePosition[1], 135, 120);
                } else {
                    SaxionApp.drawImage(animal.fullyImage, tilePosition[0], tilePosition[1], 135, 120);
                }
            } else if (property != null) {
                SaxionApp.drawImage(property.propertyImage, tilePosition[0], tilePosition[1], 135, 120);
            }
            SaxionApp.drawText(tileID, tilePosition[0] + 64, tilePosition[1] + 32, 12);//just to see tile name
        }
    }

    private void drawDefault() {
        SaxionApp.drawImage("resources/crops images/default.png", tilePosition[0], tilePosition[1], 135, 120);
    }

}
