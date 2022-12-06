package FarmerModel;

/**
 * This class is the farmer class which acts as the extension of the player.
 * Every action done by the player will affect various stats of the farmer.
 * The farmer class also affects different aspects of the game
 */
public class Farmer {

    /**The name of the farmer. */
    private final String NAME;

    /**Amount of money the farmer has. */
    private double objectCoin;

    /**Farmer's current level. */
    private int lvl;

    /**Farmer's current experience. */
    private double xp;

    /**Days passed since the game started. */
    private int daysPassed = 0;

    /**Array that contains different farmer types */
    private final String [] FARMERTYPE = {"FarmerModel", "Registered Farmer", "Distinguished Farmer", "Legendary Farmer"};

    /**Farmer's current type. */
    private String farmerType;


    /**
     * Constructor for Farmer class.
     * Default values for object coin, level, experience and farmer type.
     * @param NAME Farmer's name
     */
    public Farmer(String NAME){
        this.NAME = NAME;
        farmerType = FARMERTYPE[0];
        objectCoin = 100;
        lvl = 0;
        xp = 0;
    }

    /**
     * Constructor used when passing farmer data to different scenes.
     * @param farmer Farmer object
     */
    public Farmer(Farmer farmer) {
        this.NAME = farmer.NAME;
        this.farmerType = farmer.farmerType;
        this.objectCoin = farmer.objectCoin;
        this.lvl = farmer.lvl;
        this.xp = farmer.xp;
        this.daysPassed = farmer.daysPassed;
    }

    /**
     * get the farmer's name.
     * @return farmer's name
     */
    public String getName() {return NAME;}

    /**
     * get the farmer's object coin.
     * @return farmer's object coin
     */
    public double getObjectCoin() {return objectCoin;}

    /**
     * get the farmer's level.
     * @return farmer's level
     */
    public int getLvl() {return lvl;}

    /**
     * get the farmer's farmer type.
     * @return farmer's farmer type
     */
    public int getDaysPassed() {return daysPassed;}

    /**
     * get the farmer's farmer type.
     * @return farmer's farmer type
     */
    public String getFarmerType() {return farmerType;}

    /**
     * Detailed description of the benefits of each farmer type.
     * @param farmerType Farmer type
     * @return String containing the benefits of the farmer type
     */
    public String getBenefits(String farmerType) {
        int[] lvl = {0, 5, 10, 15};
        int[] produceBonus = {0, 1, 2, 4};
        int[] seedCostReduction = {0, 1, 2, 3};
        int[] waterBonus = {0, 0, 1, 2};
        int[] fertilizerBonus = {0, 0, 0, 1};
        int[] regFee = {0, 200, 300, 400};
        String [] farmerReqs = {"None", FARMERTYPE[0], FARMERTYPE[1], FARMERTYPE[2]};
        int index = 0;
        for (String status: FARMERTYPE) {
            if (status.equals(farmerType))
                return String.format("""
                        Farmer Type: %s
                        Level Requirement: %d
                        Registration fee: %d
                        Farmer Type Requirement: %s
                        
                        Bonus earning per produce: %d
                        Seed cost reduction: %d
                        Water bonus limit increase: %d
                        Fertilizer bonus limit increase: %d
                        

                        """, status, lvl[index], regFee[index],
                        farmerReqs[index], produceBonus[index], seedCostReduction[index],
                        waterBonus[index], fertilizerBonus[index]);
            index++;
        }
        return "";
    }

    /**
     * Detailed description of the Farmer or the Player's stats.
     * @return String containing the Farmer's stats
     */
    public String getFarmerStatus() {
        return String.format("""
                Farmer Name: %s
                Farmer Type: %s
                Farmer Level: %d
                Farmer XP: %.2f
                Farmer Object Coin: %.2f
                """, NAME, farmerType, lvl, xp, objectCoin);
    }

    /**
     * Add object coin to the farmer's object coin.
     * @param value Amount of object coin to be added
     */
    public void addObjectCoin(double value) { objectCoin = objectCoin + value;}

    /**
     * Subtract object coin from the farmer's object coin.
     * @param value Amount of object coin to be subtracted
     */
    public void decreaseObjectCoin (double value) {objectCoin = objectCoin - value;}

    /**
     * Checks if player is eligible to change their farmer type, if yes, change farmer type, else, do nothing.
     * @param farmerType Farmer type to be changed to
     * @return true if eligible, false if not
     */
    public boolean registerNewFarmerType(String farmerType) {
        switch (farmerType) {
            case "Registered Farmer" -> {
                if (getLvl() >= 5 && getFarmerType().equals("FarmerModel")
                        && getObjectCoin() >= 200) {
                    //Updates the player's status
                    this.farmerType = FARMERTYPE[1];
                    decreaseObjectCoin(200);
                    return true;
                }
            }
            case "Distinguished Farmer" -> {
                if (getLvl() >= 10 && getFarmerType().equals("Registered Farmer")
                        && getObjectCoin() >= 300) {
                    //Updates the player's status
                    this.farmerType = FARMERTYPE[2];
                    decreaseObjectCoin(300);
                    return true;
                }
            }
            case "Legendary Farmer" -> {
                if (getLvl() >= 15 && getFarmerType().equals("Distinguished Farmer")
                        && getObjectCoin() >= 400) {
                    //Updates the player's status
                    this.farmerType = FARMERTYPE[3];
                    decreaseObjectCoin(400);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Increases the farmer's level when the farmer's experience reaches the required amount for each level
     * @return true if the farmer's level has increased, else false
     */
    public boolean canLvlUp () {
        if ( xp - (lvl * 100) >= 100) {
            lvlUp();
            return true;
        }

        return false;
    }

    /**
     * Helper method for canLvlUp().
     */
    private void lvlUp() {lvl = lvl + 1;}

    /**
     * Increases the farmer's experience.
     * @param xpYield Amount of experience to be added
     */
    public void addXP(double xpYield) { xp = xp + xpYield;}

    /**
     * Increments the days passed.
     */
    public void passDay () {daysPassed++;}
}
