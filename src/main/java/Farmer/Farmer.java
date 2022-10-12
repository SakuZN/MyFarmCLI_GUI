package Farmer;

public class Farmer {

    private String name;
    private int seeds;
    private double objectCoin;
    private int lvl;
    private double xp;

    private int daysPassed = 0;

    private final String [] FARMERTYPE = {"Farmer", "Registered Farmer", "Distinguished Farmer", "Legendary Farmer"};
    private String farmerType;


    public Farmer(String name){
        this.name = name;
        farmerType = FARMERTYPE[0];
        seeds = 0;
        objectCoin = 100;
        lvl = 15;
        xp = 0;
    }

    public Farmer(Farmer farmer) {
        this.name = farmer.name;
        this.farmerType = farmer.farmerType;
        this.seeds = farmer.seeds;
        this.objectCoin = farmer.objectCoin;
        this.lvl = farmer.lvl;
        this.xp = farmer.xp;
        this.daysPassed = farmer.daysPassed;
    }

    //Getter Methods
    public String getName() {return name;}

    public int getSeeds() {return seeds;}

    public double getObjectCoin() {return objectCoin;}

    public int getLvl() {return lvl;}

    public double getXp() {return xp;}

    public String getFarmerType() {return farmerType;}

    public int getDaysPassed() {return daysPassed;}

    //Setter Methods
    public void addSeeds() { seeds = seeds + 1;}

    public void addObjectCoin(double value) { objectCoin = objectCoin + value;}
    public void decreaseObjectCoin (double value) {objectCoin = objectCoin - value;}

    public int farmerTypeBenefit(String farmerType) {
        return switch (farmerType) {
            case "Registered Farmer" -> 1;
            case "Distinguished Farmer" -> 2;
            case "Legendary Farmer" -> 3;
            default -> 0;
        };
    }
    public void setFarmerType (int index) {farmerType = FARMERTYPE[index];}
    public void benefitDescriptions() {
        int[] lvl = {0, 5, 10, 15};
        int[] produceBonus = {0, 1, 2, 4};
        int[] seedCostReduction = {0, 1, 2, 3};
        int[] waterBonus = {0, 0, 1, 2};
        int[] fertilizerBonus = {0, 0, 0, 1};
        int[] regFee = {0, 200, 300, 400};
        int index = 0;
        for (String status: FARMERTYPE) {
            System.out.printf("Farmer Type: %s\n", status);
            System.out.printf("Level Requirement: %d\n", lvl[index]);
            System.out.printf("Bonus earning per Produce: %d\n", produceBonus[index]);
            System.out.printf("Seed cost reduction: %d\n", seedCostReduction[index]);
            System.out.printf("Water bonus limit increase: %d\n", waterBonus[index]);
            System.out.printf("Fertilizer bonus limit increase: %d\n", fertilizerBonus[index]);
            System.out.printf("Registration fee: %d\n\n", regFee[index]);
            index++;
        }
    }

    public String getBenefits(String farmerType) {
        int[] lvl = {0, 5, 10, 15};
        int[] produceBonus = {0, 1, 2, 4};
        int[] seedCostReduction = {0, 1, 2, 3};
        int[] waterBonus = {0, 0, 1, 2};
        int[] fertilizerBonus = {0, 0, 0, 1};
        int[] regFee = {0, 200, 300, 400};

        int index = 0;
        for (String status: FARMERTYPE) {
            if (status.equals(farmerType))
                return String.format("""
                        Farmer Type: %s
                        Level Requirement: %d
                        Registration fee: %d
                        
                        Bonus earning per produce: %d
                        Seed cost reduction: %d
                        Water bonus limit increase: %d
                        Fertilizer bonus limit increase: %d
                        

                        """, status, lvl[index], regFee[index],
                        produceBonus[index], seedCostReduction[index],
                        waterBonus[index], fertilizerBonus[index]);
            index++;
        }
        return "";
    }
    public void lvlUp () { lvl = (int)xp / 100;}

    public void addxp (double xpYield) { xp = xp + xpYield;}

    public void passDay () {daysPassed++;}
}