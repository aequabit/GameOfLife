/**
 * Implementation of John Conway's Game of Life.
 *
 * @author aequabit
 */
class GameOfLife {
    private int columns;
    private int rows;
    private int scale;

    /**
     * Two-dimensional array to store cell states.
     */
    private int[][] map;

    /**
     * Constructor.
     *
     * @param height Height of the map.
     * @param width  Width of the map.
     * @param scale  Scale of the map.
     */
    GameOfLife(int width, int height, int scale) {
        /**
         * Calculate columns and rows depending on the scale.
         */
        this.columns = width / scale;
        this.rows = height / scale;

        this.scale = scale;

        /**
         * Initialize the map.
         */
        this.map = new int[columns][rows];
    }

    /**
     * Overrides the life state of a cell.
     *
     * @param x     X coordinate of the cell.
     * @param y     Y coordinate of the cell.
     * @param state Life state to set.
     */
    public void OverrideLifeState(int x, int y, int state) {
        if (x > rows - 1 || y > columns - 1 || x < 0 || y < 0) return;
        map[x][y] = state;
    }

    /**
     * Gets the count of a cell's alive neighbours.
     *
     * @param x X coordinate of the cell.
     * @param y Y coordinate of the cell.
     * @return Alive neighbours
     */
    private int AliveNeighbours(int x, int y) {
        int aliveNeighbours = 0;

        /**
         * Iterate over all neighbour cells.
         */
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // If the cell is itself, continue.
                aliveNeighbours += map[x + i][y + j];
            }
        }

        return aliveNeighbours;
    }

    /**
     * Applies all 23/3 rules to a cell.
     *
     * @param x X coordinate of the cell.
     * @param y Y coordinate of the cell.
     * @return The state of the cell after applying the rules.
     */
    private int ApplyRules(int x, int y) {
        int aliveNeighbours = AliveNeighbours(x, y);

        if ((map[x][y] == 0) && (aliveNeighbours == 3)) // Dead cell with three alive neighbours.
            return 1; // Gets reproduced.
        else if ((map[x][y] == 1) && (aliveNeighbours < 2)) // Alive cell with less than two alive neighbours.
            return 0; // Dies due to underpopulation.
        else if ((map[x][y] == 1) && (aliveNeighbours == 2 || (aliveNeighbours == 3))) // Alive cell with two or three alive neighbours.
            return map[x][y]; // Stays alive.
        else if ((map[x][y] == 1) && (aliveNeighbours > 3)) // Alive cell with more than three alive neighbours.
            return 0; // Dies due to overpopulation.

        return 0;
    }

    /**
     * Calculates the next lifecycle by applying the rules to all cells.
     */
    public void Evolve() {
        /**
         * Two-dimensional array to store the following generation's cells.
         */
        int[][] followingGeneration = new int[columns][rows];

        /**
         * Iterate over all cells.
         */
        for (int x = 1; x < columns - 1; x++) {
            for (int y = 1; y < rows - 1; y++) {

                /**
                 * Apply the rules to the current cell.
                 */
                followingGeneration[x][y] = ApplyRules(x, y);
            }
        }

        /**
         * Override the old map with the evolved one.
         */
        map = followingGeneration;
    }

    /**
     * Creates a randomly populated map.
     * @return Map
     */
    public int[][] RandomMap() {
        /**
         * Create a temporary map.
         */
        int[][] finalMap = new int[rows][columns];

        /**
         * Populate the map with random cells.
         */
        for (int x = 1; x < columns - 1; x++) {
            for (int y = 1; y < rows - 1; y++) {
                finalMap[x][y] = (int) (Math.random() * 2);
            }
        }

        return finalMap;
    }

    /**
     * Creates an empty map containing a Gosper Glider Gun.
     * @return Map
     */
    public int[][] GliderGun() {
        /**
         * Create a temporary map.
         */
        int[][] finalMap = new int[rows][columns];

        /**
         * CTRL+F -> 1 to see the structure.
         */
        int[][] gliderCannon = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        /**
         * Embed the structure into the temporary map.
         */
        for (int x = 0; x < 38; x++) {
            for (int y = 0; y < 11; y++) {
                finalMap[x + 1][y + 1] = gliderCannon[y][x];
            }
        }

        return finalMap;
    }

    /**
     * Gets the amount of columns.
     *
     * @return Amount of columns.
     */
    public int GetColumns() {
        return this.columns;
    }

    /**
     * Gets the amount of rows.
     *
     * @return Amount of rows
     */
    public int GetRows() {
        return this.rows;
    }

    /**
     * Gets the map scale.
     *
     * @return Map scale.
     */
    public int GetScale() {
        return this.scale;
    }

    /**
     * Gets the current map.
     *
     * @return Current map.
     */
    public int[][] GetMap() {
        return this.map;
    }

    /**
     * Overrides the current map.
     *
     * @param map Map to set.
     */
    public void SetMap(int[][] map) {
        this.map = map;
    }
}
