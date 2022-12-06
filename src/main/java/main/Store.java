package main;

import java.util.ArrayList;
import Seeds.*;

/**
 * This class stores the seed that the user will buy from. Seeds are initialized here and added to the store.
 * Seed properties when initialized are affected by the Farmer's type.
 */
public class Store {

    /**Array list of seeds that will be sold in the store. */
    private ArrayList<Seeds> allForSale = new ArrayList<>();

    /**
     * Constructor for Store class
     * Seeds are initialized and added to the store
     * @param farmerType Farmer's type, affects all seeds' properties
     */
    public Store(String farmerType) {
        allForSale.add(new Turnip());
        allForSale.add(new Carrot());
        allForSale.add(new Potato());
        allForSale.add(new Rose());
        allForSale.add(new Turnips());
        allForSale.add(new Sunflower());
        allForSale.add(new Mango());
        allForSale.add(new Apple());

        // Farmer's type affects all seeds' properties
        for (Seeds seeds : allForSale) {
            seeds.setExtraBonusFC(farmerType);
            seeds.setExtraBonusWC(farmerType);
            seeds.setDiscountSeedCost(farmerType);
        }

    }

    /**
     * Get all seeds that are for sale
     * @return all seeds that are for sale
     */
    public ArrayList<Seeds> getAllForSale() {return allForSale;}

}
