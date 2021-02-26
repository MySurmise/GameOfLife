import java.util.Random;
import java.lang.Runtime;

public class table {

    public static void main(String... args) {
        
        /* test with commands
        try {
            Process p = Runtime.getRuntime().exec("C:\\Windows\\System32\\chcp.com 65001");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Didn't work!");
        }
        */
        
        
        table gameOfLife;
        int waitTime = 1000;
        if (args.length > 0) {
            try {
                gameOfLife = new table(Integer.valueOf(args[0]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
                gameOfLife.randomAssign(Integer.valueOf(args[4]));
                waitTime = Integer.valueOf(args[1]);
                
            } catch (Exception e) {
                throw new Error(e
                        + "Syntax: 'java table <size> <waitTime between prints in ms> <minNeighbors> <maxNeighbors> <number of random chosen living fields>'\nOnly use numbers!");
            }
        } else {
            gameOfLife = new table(35, 2, 3);
            gameOfLife.randomAssign(15);
        }
        // gameOfLife.printtable();
        
        gameOfLife.printtable();
        // gameOfLife.valTable[20][20][0] = true;
        // gameOfLife.valTable[20][21][0] = true;
        // gameOfLife.valTable[21][20][0] = true;
        // gameOfLife.valTable[21][21][0] = true;
        gameOfLife.printtable();
        gameOfLife.propagate(1000, true, waitTime);
    }

    private boolean[][][] valTable;
    private int mode, size, minThresh, maxThresh;

    // private int size = 5;

    public table(int size, int minThresh, int maxThresh) {
        this.size = size;
        boolean[][][] valTable = new boolean[size][size][2];
        int mode = 0;
        this.valTable = valTable;
        this.mode = mode;
        this.minThresh = minThresh;
        this.maxThresh = maxThresh;
    }

    public void printtable() {
        for (int i = 0; i < (2 + 2 * this.size); i++) {
            System.out.print("-");
        }
        System.out.print("\n");

        for (int row = 0; row < this.size; row++) {
            System.out.print("|");

            for (int column = 0; column < this.size; column++) {
                if (this.valTable[row][column][this.mode]) {
                    // System.out.print("X "); // Print White Square ⬛
                    System.out.print("⬛");
                    // System.out.print('\u25FB');
                    // System.out.print("* ");
                } else {
                    // System.out.print("O "); // Print Black Square ⬜
                    System.out.print("⬜");
                    // ystem.out.print('\u25FC');
                    // System.out.print('◻');
                    // System.out.print(" ");
                }
            }
            System.out.print("|\n");
        }
        /*
         * for (int i = 0; i < (2 + 2.27 * this.size); i++) { System.out.print("-"); }
         */

        System.out.println("");

    }

    public void propagate(int number) {
        for (int i = 0; i < number; i++) {
            int newMode;
            if (this.mode == 0) {
                newMode = 1;
            } else if (this.mode == 1) {
                newMode = 0;
            } else {
                throw new Error("This mode isn't possible!");
            }
            int neighborNumber;
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {
                    neighborNumber = neighbors(row, column);
                    if (neighborNumber >= minThresh && neighborNumber <= maxThresh) {
                        this.valTable[row][column][newMode] = true;
                    } else {
                        this.valTable[row][column][newMode] = false;
                    }
                }
            }
            this.mode = newMode;
        }
    }

    public void propagate() {
        propagate(1);
    }

    public void propagate(int repeats, boolean print, int waitInMillis) {
        for (int i = 0; i < repeats; i++) {
            propagate();
            sleep(waitInMillis);
            printtable();
        }
    }

    public int neighbors(int row, int column) {
        int neighborNumber = 0;
        for (int x = row - 1; x <= row + 1; x++) {
            for (int y = column - 1; y <= column + 1; y++) {
                if ((x >= 0 && x < this.size && y >= 0 && y < this.size) && (x != row || y != column)) {
                    if (this.valTable[x][y][this.mode]) {
                        neighborNumber += 1;
                    }
                }
            }
        }
        return neighborNumber;
    }

    public void sleep(int milliseconds) {
        long begin = System.currentTimeMillis();
        while (System.currentTimeMillis() - begin < milliseconds) {
            ;
        }
    }

    public void randomAssign(int number) {
        Random rand = new Random();
        if (this.size * this.size < number) {
            throw new Error("Die Nummer ist höher als die der verfügbaren Felder!");
        }
        for (int i = 0; i < number; i++) {
            int randomNumber = rand.nextInt(this.size * this.size);
            // System.out.println("Size: " + this.size);
            // System.out.println("Number: " + randomNumber);
            int row = randomNumber % this.size;
            int column = randomNumber / this.size;
            // System.out.println(row);
            // System.out.println(column);
            this.valTable[row][column][0] = true;
        }
    }
}
