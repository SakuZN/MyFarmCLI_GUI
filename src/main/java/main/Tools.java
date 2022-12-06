package main;

/**
 * This class contains all the tools that are used in the game. Each tool overrides their use and description methods.
 */
public abstract class Tools {


    /** The name of the tool. */
    private String toolName;

    /** The cost of using the tool. */
    private int costUsage;

    /** XP yield from using the tool. */
    private double xpYield;

    /**
     * Constructor for Tools class
     * Initializes the tool name, cost usage and xp yield
     * @param toolName Name of the tool
     * @param costUsage Cost of using the tool
     * @param xpYield XP yield of using the tool
     */
    public Tools(String toolName, int costUsage, double xpYield) {
        this.toolName = toolName;
        this.costUsage = costUsage;
        this.xpYield = xpYield;
    }

    /**
     * Abstract method for the use of the tool
     * @param tileStatus Status of the tile
     * @return true if the tool can be used, false otherwise
     */
    public abstract boolean useTool(String tileStatus);

    /**
     * Get the name of the tool
     * @return name of the tool
     */
    public String getToolName() {return toolName;}

    /**
     * Get the cost of using the tool
     * @return cost of using the tool
     */
    public int getCostUsage() {return costUsage;}

    /**
     * Get the xp yield of using the tool
     * @return xp yield of using the tool
     */
    public double getXpYield() {return xpYield;}

    /**
     * Abstract method for the description of the tool
     * @return description of the tool
     */
    public abstract String getToolDescription();
}
