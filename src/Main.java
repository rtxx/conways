/**
 * Conway's Game of Life
 * Simple implementation of the 'game'.
 * Made to get familiar with Java syntax and, of course, fun.
 * Rules of the game:
 * 1 - Any live cell with fewer than two live neighbors dies, as if by underpopulation.
 * 2 - Any live cell with two or three live neighbors lives on to the next generation.
 * 3 - Any live cell with more than three live neighbors dies, as if by overpopulation.
 * 4 - Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * How it works:
 * Make a grid, process the game and outputs it.
 * There is no animation, it just loops through all the generations and that's it.
 * Game grid is a simple boolean array matrix, a 2d array.
 * If an index of the game grid is true, then a cell is alive, if not then it's dead.
 * The game grid starts at 0,0 and goes to, for example, 24, 80
 */
class Conways {
    int gridLengthY = 24;
    int gridLengthX = 80;
    boolean[][] gameGrid = new boolean[gridLengthY][gridLengthX];
    //"█■☼Θ"
    String aliveChar = "☼";
    String deadChar = " ";

    int generationsToProcess = 1000;

    public String toString(){
        return "This is a Conways Class";
    }

    public Conways() {
        generateMapGrid();
    }


    public void printDebugGrid() {
        for (int y = 0; y < gameGrid.length; y++) {
            //boolean[] tempArray = new boolean[dictionary.length] ;
            System.out.print("|");
            for (int x = 0; x < gameGrid[y].length; x++) {
                System.out.print(gameGrid[y][x] + " ");
                //System.out.printf("%2dy%dx ", y, x);
                //tempArray[j] = dictionary[j].contains(stringArray[i]);
            }
            System.out.println(" ");
            //checkMatrix[i] = tempArray;
        }
        System.out.println(" --- ");
    }

    /**
     * Prints the current game grid.
     * If current cell is false, then it's a dead cell.
     * If current cell is true, then it's an alive cell.
     * Note: some characters won't work well on Windows.
     */
    public void printGrid(){
        for (int y = 0; y < gameGrid.length; y++){
            //System.out.print("|");
            for (int x = 0; x < gameGrid[y].length; x++){
                if(gameGrid[y][x]) {
                    System.out.printf("%1s", aliveChar);
                } else {
                    System.out.printf("%1s", deadChar);
                }

            }
            System.out.println(" ");
        }
        System.out.println(" --- ");
    }

    /**
     * Generates a map grid.
     * It randomizes each time the game is run.
     * Simple randomness: Gets a number between 0 and {@code gridLengthY} * {@code gridLengthX};
     * If is an even number, the cell is alive, if not then it's dead.
     */
    private void generateMapGrid(){
        // random map grid
        for (int y = 0; y < gameGrid.length; y++){
            for (int x = 0; x < gameGrid[y].length; x++){
                int chance = (int) Math.floor(Math.random() * (gridLengthY * gridLengthX));
                gameGrid[y][x] = chance % 2 == 0;
            }
        }

        // manual testing
        /* simple glider
        gameGrid[1][4] = true;
        gameGrid[2][5] = true;
        gameGrid[3][3] = true;
        gameGrid[3][4] = true;
        gameGrid[3][5] = true;
        */
    }

    /**
     * Process's the game next generation.
     * The meat and potatoes of the game.
     * It checks for the 4 rules on each cell of the game grid.
     * After it generates a new game grid with the new generation.
     */
    public void nextGeneration(){
        boolean[][] newGameGrid = new boolean[gridLengthY][gridLengthX];

        for (int y = 0; y < gameGrid.length; y++){
            for (int x = 0; x < gameGrid[y].length; x++){
                // if cell is alive
                if (gameGrid[y][x]) {
                    if (checkIfCellIsGoingToDie(y, x)) {
                        newGameGrid[y][x] = false;
                    }
                    if (checkIfCellStaysAlive(y,x)) {
                        newGameGrid[y][x] = true;
                    }
                // if cell is dead
                } else {
                    if (checkIfCellIsGoingToBirth(y, x)) {
                        newGameGrid[y][x] = true;
                    }
                }
            }
        }

        // sets the current game grid to the new game grid
        gameGrid = newGameGrid;
    }

    /**
     * Check if a cell is going to birth on the next generation.
     * Rule 4: Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     * Counts the neighbors with {@code getCellNeighborsArray}.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  isGoingToBirth  {@code boolean}
     */
    private boolean checkIfCellIsGoingToBirth(int gridY, int gridX) {
        boolean isGoingToBirth = false;
        boolean[] neighborArray = getCellNeighborsArray(gridY, gridX);

        int countLiveNeighbor = 0;
        for (int i = 0; i < neighborArray.length; i++){
            if (neighborArray[i]) {
                countLiveNeighbor++;
            }
        }

        if (countLiveNeighbor == 3 ) {
            isGoingToBirth = true;
        }

        return isGoingToBirth;
    }

    /**
     * Check if a cell is going to die on the next generation.
     * Rule 1: Any live cell with fewer than two live neighbors dies, as if by underpopulation.
     * Rule 3: Any live cell with more than three live neighbors dies, as if by overpopulation.
     * Counts the neighbors with {@code getCellNeighborsArray}.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  isGoingToDie    {@code boolean}
     */
    private boolean checkIfCellIsGoingToDie(int gridY, int gridX) {
        boolean isGoingToDie = false;
        boolean[] neighborArray = getCellNeighborsArray(gridY, gridX);

        int countLiveNeighbor = 0;
        for (int i = 0; i < neighborArray.length; i++){
            if (neighborArray[i]) {
                countLiveNeighbor++;
            }
        }

        if (countLiveNeighbor < 2 || countLiveNeighbor > 3) {
            isGoingToDie = true;
        }

        return isGoingToDie;
    }

    /**
     * Check if a cell is going to stay alive on the next generation.
     * Rule 2: Any live cell with two or three live neighbors lives on to the next generation.
     * Counts the neighbors with the result of {@code getCellNeighborsArray}.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  isAlive         {@code boolean}
     */
    private boolean checkIfCellStaysAlive(int gridY, int gridX) {
        boolean isAlive = false;
        boolean[] neighborArray = getCellNeighborsArray(gridY, gridX);

        int countLiveNeighbor = 0;
        for (int i = 0; i < neighborArray.length; i++){
            if (neighborArray[i]) {
                countLiveNeighbor++;
            }
        }

        if (countLiveNeighbor == 2 || countLiveNeighbor == 3) {
             isAlive = true;
        }

        return isAlive;
    }

    /**
     * Check for a cell current neighbors.
     * Each cell has 8 neighbors, which are the adjacent cells.
     * We check for all of them and returns {@code []boolean}.
     * If an index of the array is true, then has a neighbor.
     * @param   gridY               {@code int}
     * @param   gridX               {@code int}
     * @return  neighborsExistence  {@code []boolean}
     */
    private boolean[] getCellNeighborsArray(int gridY, int gridX){
        // clockwise check
        // top          -> y-1, x
        // top-right    -> y-1, x+1
        // right        -> y, x+1
        // down-right   -> y+1, x+1
        // down         -> y+1, x
        // down-left    -> y+1, x-1
        // left         -> y, x-1
        // top-left     -> y-1, x-1

        // 8 possible neighbors
        boolean[] neighborsExistence = new boolean[8];

        // bad but readable
        neighborsExistence[0] = checkNeighborTop(gridY, gridX);
        neighborsExistence[1] = checkNeighborTopRight(gridY, gridX);
        neighborsExistence[2] = checkNeighborRight(gridY, gridX);
        neighborsExistence[3] = checkNeighborDownRight(gridY, gridX);
        neighborsExistence[4] = checkNeighborDown(gridY, gridX);
        neighborsExistence[5] = checkNeighborDownLeft(gridY, gridX);
        neighborsExistence[6] = checkNeighborLeft(gridY, gridX);
        neighborsExistence[7] = checkNeighborTopLeft(gridY, gridX);

        return neighborsExistence;
    }

    /**
     * Check if a cell has a top neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborTop(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // top          -> y-1, x
        // out-of-bounds check
        if ( gridY - 1 >= 0 ) {
            if (gameGrid[gridY - 1][gridX]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }

    /**
     * Check if a cell has a top right neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborTopRight(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // top-right    -> y-1, x+1
        // out-of-bounds check
        if ( gridY - 1 >= 0 && gridX + 1 < gridLengthX ) {
            if (gameGrid[gridY - 1][gridX + 1]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }

    /**
     * Check if a cell has a right neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborRight(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // right        -> y, x+1
        // out-of-bounds check
        if ( gridX + 1 < gridLengthX ) {
            if (gameGrid[gridY][gridX + 1]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }

    /**
     * Check if a cell has a down right neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborDownRight(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // down-right   -> y+1, x+1
        // out-of-bounds check
        if ( gridY + 1 < gridLengthY && gridX + 1 < gridLengthX ) {
            if (gameGrid[gridY + 1][gridX + 1]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }

    /**
     * Check if a cell has a down neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborDown(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // down         -> y+1, x
        // out-of-bounds check
        if ( gridY + 1 < gridLengthY ) {
            if (gameGrid[gridY + 1][gridX]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }

    /**
     * Check if a cell has a down left neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborDownLeft(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // down-left    -> y+1, x-1
        // out-of-bounds check
        if ( gridY + 1 < gridLengthY && gridX - 1 >= 0) {
            if (gameGrid[gridY + 1][gridX - 1]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }

    /**
     * Check if a cell has a left neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborLeft(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // left         -> y, x-1
        // out-of-bounds check
        if ( gridX - 1 >= 0) {
            if (gameGrid[gridY][gridX - 1]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }

    /**
     * Check if a cell has a top left neighbor.
     * @param   gridY           {@code int}
     * @param   gridX           {@code int}
     * @return  hasNeighbor     {@code boolean}
     */
    private boolean checkNeighborTopLeft(int gridY, int gridX) {
        boolean hasNeighbor = false;
        // top-left     -> y-1, x-1
        // out-of-bounds check
        if ( gridY - 1 >= 0 && gridX - 1 >= 0) {
            if (gameGrid[gridY - 1][gridX - 1]) {
                hasNeighbor = true;
            }
        }
        return hasNeighbor;
    }
}

public class Main {
    public static void main(String[] args) {
        Conways game = new Conways();
        // dirty but effective way of getting an "animation" feel to it
        for (int i = 0; i < game.generationsToProcess; i++) {
            game.printGrid();
            game.nextGeneration();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}