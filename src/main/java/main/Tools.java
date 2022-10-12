package main;

public abstract class Tools {

    private String toolName;
    private int costUsage;
    private double xpYield;

    public Tools(String toolName, int costUsage, double xpYield) {
        this.toolName = toolName;
        this.costUsage = costUsage;
        this.xpYield = xpYield;
    }

    public abstract boolean useTool(String tileStatus);

    public String getToolName() {return toolName;}

    public int getCostUsage() {return costUsage;}

    public double getXpYield() {return xpYield;}

    public abstract String toolDescription();
}
