package main;

import java.util.ArrayList;
import Seeds.*;

public class Store {

    private ArrayList<Seeds> rootCropSale = new ArrayList<>();
    private ArrayList<Seeds> flowerSale = new ArrayList<>();
    private ArrayList<Seeds> fruitTreeSale = new ArrayList<>();

    public Store() {

        rootCropSale.add(new Turnip());
        rootCropSale.add(new Carrot());
        rootCropSale.add(new Potato());
        flowerSale.add(new Rose());
        flowerSale.add(new Turnips());
        flowerSale.add(new Sunflower());
        fruitTreeSale.add(new Mango());
        fruitTreeSale.add(new Apple());

    }

    public ArrayList<Seeds> getRootCropSale() {return rootCropSale;}

    public ArrayList<Seeds> getFlowerSale() {return flowerSale;}

    public ArrayList<Seeds> getFruitTreeSale() {return fruitTreeSale;}

    //public Seeds buySeed (int index) { return seedSale.get(index);}
}
