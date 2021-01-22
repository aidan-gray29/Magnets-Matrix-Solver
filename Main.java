//Aidan Gray
//2 x N Magnet Matrix Solver
import java.io.*;

public class Main {

    public static int magnetCount = 1;
    public static int col = 0;
    public static int M = 0;

    //do these polarities repel?
    public static boolean repels(char a, char b) {
				return a == b;
    }

    // true if + or n
    public static boolean isPositive(char c) {
        return c == '+';
    }

    // true if - or s
    public static boolean isNegative(char c) {
        return c == '-';
    }

    //true if opp poles or (* & !*), false if same
    public static boolean adjacentSafe(char a, char b) {
        return !repels(a, b) || (a != '*' && b == '*') || (a == '*' && b != '*') || (a == '*' && b == '*');
    }

    //true if same or (* & !*) false if opposite poles
    public static boolean diagonalSafe(char a, char b) {
        // A x  x !A
        //!A A !A
        return repels(a, b) || (a != '*' && b == '*') || (a == '*' && b != '*') || (a == '*' && b == '*');
    }

    // matches?	 	A  x  A
    //       		 !A  x !A
    public static int vertSafe(char[][] mag, boolean[][] taken, int j) {
        if (!taken[0][j] && !taken[1][j]) {
            //middle
            if (j > 0 && j < col - 1) {
                if (adjacentSafe(mag[0][j - 1], mag[1][j - 1]) && adjacentSafe(mag[0][j + 1], mag[1][j + 1]) && diagonalSafe(mag[0][j - 1], mag[0][j + 1]) && diagonalSafe(mag[1][j - 1], mag[1][j + 1]) && adjacentSafe(mag[0][j - 1], mag[1][j + 1]) && adjacentSafe(mag[1][j - 1], mag[0][j + 1]) && diagonalSafe(mag[0][j], mag[1][j - 1]) && diagonalSafe(mag[0][j], mag[1][j + 1]) && diagonalSafe(mag[1][j], mag[0][j - 1]) && diagonalSafe(mag[1][j], mag[0][j + 1])
                ) {
                    return 1;
                }
            }
            //beginning
            if (j == 0) {
                if (adjacentSafe(mag[0][j + 1], mag[1][j + 1]) && diagonalSafe(mag[0][0], mag[1][1]) && diagonalSafe(mag[1][0], mag[0][1])) {
                    return 2;
                }
            }
            //end
            if (j == col - 1) {
                if (adjacentSafe(mag[0][j - 1], mag[1][j - 1]) && diagonalSafe(mag[0][j], mag[1][j - 1]) && diagonalSafe(mag[1][j], mag[0][j - 1])) {
                    return 3;
                }
            }
        }
        return -99;
    }

    // matches?	A x  x !A
    //       		  A !A
    public static int topSafe(char[][] mag, boolean[][] taken, int j) {
        // A x  x !A
        //   A !A
        if ( j <= col - 2 && !taken[0][j] && !taken[0][j + 1]) {
            if ( j >= 1 && j < col - 2) { //middle
                if (diagonalSafe(mag[0][j - 1], mag[1][j]) && diagonalSafe(mag[0][j + 2], mag[1][j + 1])
                        && adjacentSafe(mag[0][j - 1], mag[0][j + 2]) && adjacentSafe(mag[0][j - 1], mag[1][j + 1])
                        && adjacentSafe(mag[1][j], mag[1][j + 1]) && adjacentSafe(mag[0][j + 2], mag[1][j])

                        && diagonalSafe(mag[0][j], mag[0][j + 2]) && diagonalSafe(mag[0][j], mag[1][j + 1])
                        && adjacentSafe(mag[0][j], mag[1][j]) && adjacentSafe(mag[0][j], mag[0][j - 1])

                        && adjacentSafe(mag[0][j + 1], mag[0][j + 2]) && adjacentSafe(mag[1][j + 1], mag[0][j + 1])
                        && diagonalSafe(mag[0][j + 1], mag[1][j]) && diagonalSafe(mag[0][j + 1], mag[0][j - 1])
                ) {
                    return 1;
                }
            }
            if ( j == 0) { //beginning
                if (adjacentSafe(mag[0][2], mag[1][0]) && adjacentSafe(mag[1][0], mag[1][1]) && diagonalSafe(mag[1][1], mag[0][2])
                        && adjacentSafe(mag[0][j + 1], mag[0][j + 2]) && adjacentSafe(mag[1][j + 1], mag[0][j + 1])
                        && diagonalSafe(mag[0][j + 1], mag[1][j]) && diagonalSafe(mag[0][j], mag[0][j + 2])
                        && diagonalSafe(mag[0][j], mag[1][j + 1]) && adjacentSafe(mag[0][j], mag[1][j])
                ) {
                    return 2;
                }
            }
            if ( j == col - 2) { //end
                if (adjacentSafe(mag[1][j], mag[1][j + 1]) && adjacentSafe(mag[1][j + 1], mag[0][j - 1]) && diagonalSafe(mag[0][j - 1], mag[1][j]) && diagonalSafe(mag[0][j], mag[1][j + 1]) && adjacentSafe(mag[0][j], mag[1][j]) && adjacentSafe(mag[0][j], mag[0][j - 1]) && adjacentSafe(mag[1][j + 1], mag[0][j + 1]) && diagonalSafe(mag[0][j + 1], mag[1][j]) && diagonalSafe(mag[0][j + 1], mag[0][j - 1]) )
                {
                    return 3;
                }
            }
        }
        return -99;
    }

    // matches?	 	A !A
    //       		A x  x !A
    public static int botSafe(char[][] mag, boolean[][]taken, int j) {
        //   A !A
        // A x  x !A
        if(j <= col - 2 && !taken[1][j] && !taken[1][j+1] ){
            if (j >= 1 && j < col - 2) {    //middle
                if (diagonalSafe(mag[1][j - 1], mag[0][j]) && diagonalSafe(mag[1][j + 2], mag[1][j + 1]) && adjacentSafe(mag[0][j + 1], mag[1][j - 1]) && adjacentSafe(mag[0][j], mag[1][j + 2]) && adjacentSafe(mag[0][j], mag[0][j + 1]) && adjacentSafe(mag[1][j + 2], mag[1][j - 1]) && diagonalSafe(mag[1][j], mag[1][j + 2]) && diagonalSafe(mag[1][j], mag[0][j + 1]) && adjacentSafe(mag[1][j], mag[0][j]) && adjacentSafe(mag[1][j], mag[1][j - 1]) && adjacentSafe(mag[1][j + 1], mag[1][j + 2]) && adjacentSafe(mag[1][j + 1], mag[0][j + 1]) && diagonalSafe(mag[1][j + 1], mag[0][j]) && diagonalSafe(mag[1][j + 1], mag[1][j - 1]))
                {
                    return 1;
                }
            }
            if (j == 0) {  //beginning
                if (diagonalSafe(mag[0][1], mag[1][2]) && adjacentSafe(mag[0][0], mag[0][1]) && adjacentSafe(mag[0][0], mag[1][2]) && diagonalSafe(mag[1][j], mag[1][j + 2]) && diagonalSafe(mag[1][j], mag[0][j + 1]) && adjacentSafe(mag[1][j], mag[0][j]) && adjacentSafe(mag[1][j + 1], mag[1][j + 2]) && adjacentSafe(mag[1][j + 1], mag[0][j + 1]) && diagonalSafe(mag[1][j + 1], mag[0][j]))
                {
                    return 2;
                }
            }
            if (j == col - 2) {  //end
                if (diagonalSafe(mag[0][j], mag[1][j - 1]) && adjacentSafe(mag[0][j + 1], mag[0][j]) && adjacentSafe(mag[0][j + 1], mag[1][j - 1]) && diagonalSafe(mag[1][j], mag[0][j + 1]) && adjacentSafe(mag[1][j], mag[0][j]) && adjacentSafe(mag[1][j], mag[1][j - 1]) && adjacentSafe(mag[1][j + 1], mag[0][j + 1]) && diagonalSafe(mag[1][j + 1], mag[0][j]) && diagonalSafe(mag[1][j + 1], mag[1][j - 1]))
                {
                    return 3;
                }
            }
        }
        return -99;
    }
    //   A !A			->     A  !A
    // A x  x !A			A !A   A !A
    public static void matchXb(char[][] mag, boolean[][] taken, int j, int safety, BufferedWriter out) throws IOException {
        //  A !A
        //A x  x !A
        //middle
        if (safety == 1) {
            if (isNegative(mag[1][j]) || isPositive(mag[1][j+1]) || isPositive(mag[0][j]) || isPositive(mag[1][j - 1]) || isNegative(mag[0][j + 1]) || isNegative(mag[1][j + 2])) {
                mag[1][j] = '-';
                mag[1][j + 1] = '+';
                magnetCount++;
                taken[1][j] = true;
                taken[1][j + 1] = true;
                out.write("1 " + (j + 1) + " 1 " + j + "\n");
            } else {
                mag[1][j] = '+';
                mag[1][j + 1] = '-';
                magnetCount++;
                taken[1][j] = true;
                taken[1][j + 1] = true;
                out.write("1 " + j + " 1 " + (j + 1) + "\n");
            }
            //beginning
        } else if (safety == 2) {
            if (isNegative(mag[1][j]) || isPositive(mag[1][j+1]) || isPositive(mag[0][j]) || isNegative(mag[0][j + 1]) || isNegative(mag[1][j + 2])) {
                mag[1][j] = '-';
                mag[1][j + 1] = '+';
                magnetCount++;
                taken[1][j] = true;
                taken[1][j + 1] = true;
                out.write("1 " + (j + 1) + " 1 " + j + "\n");
            } else {
                mag[1][j] = '+';
                mag[1][j + 1] = '-';
                magnetCount++;
                taken[1][j] = true;
                taken[1][j + 1] = true;
                out.write("1 " + j + " 1 " + (j + 1) + "\n");
            }
            //end
        } else if (safety == 3) {
            if (isNegative(mag[1][j]) || isPositive(mag[1][j+1]) || isPositive(mag[0][j]) || isPositive(mag[1][j - 1]) || isNegative(mag[0][j + 1])) {
                mag[1][j] = '-';
                mag[1][j + 1] = '+';
                magnetCount++;
                taken[1][j] = true;
                taken[1][j + 1] = true;
                out.write("1 " + (j + 1) + " 1 " + j + "\n");
            } else {
                mag[1][j] = '+';
                mag[1][j + 1] = '-';
                magnetCount++;
                taken[1][j] = true;
                taken[1][j + 1] = true;
                out.write("1 " + j + " 1 " + (j + 1) + "\n");
            }
        }
        return;
    }

    // A x  x !A  ->  A !A  A !A
    //	 A !A						 A !A
    public static void matchXt(char[][] mag, boolean[][] taken, int j, int safety, BufferedWriter out) throws IOException {
        if (safety == 1) {//middle
            if ( isNegative(mag[0][j]) || isPositive(mag[0][j+1]) || isPositive(mag[0][j - 1]) || isPositive(mag[1][j]) || isNegative(mag[1][j + 1]) || isNegative(mag[0][j + 2])) {
                mag[0][j] = '-';
                mag[0][j + 1] = '+';
                magnetCount++;
                taken[0][j] = true;
                taken[0][j + 1] = true;
                out.write("0 " + (j + 1) + " 0 " + j + "\n");
            } else {
                mag[0][j] = '+';
                mag[0][j + 1] = '-';
                magnetCount++;
                taken[0][j] = true;
                taken[0][j + 1] = true;
                out.write("0 " + j + " 0 " + (j + 1) + "\n");
            }
        } else if (safety == 2) {// beginning
            if (isNegative(mag[0][j]) || isPositive(mag[0][j+1]) || isPositive(mag[1][j]) || isNegative(mag[1][j + 1]) || isNegative(mag[0][j + 2])) {
                mag[0][j] = '-';
                mag[0][j + 1] = '+';
                magnetCount++;
                taken[0][j] = true;
                taken[0][j + 1] = true;
                out.write("0 " + (j + 1) + " 0 " + j + "\n");
            } else {
                mag[0][j] = '+';
                mag[0][j + 1] = '-';
                magnetCount++;
                taken[0][j] = true;
                taken[0][j + 1] = true;
                out.write("0 " + j + " 0 " + (j + 1) + "\n");
            }
        } else if (safety == 3) {//end
            if (isNegative(mag[0][j]) || isPositive(mag[0][j+1]) || isPositive(mag[0][j - 1]) || isPositive(mag[1][j]) || isNegative(mag[1][j + 1])) {
                mag[0][j] = '-';
                mag[0][j + 1] = '+';
                magnetCount++;
                taken[0][j] = true;
                taken[0][j + 1] = true;
                out.write("0 " + (j + 1) + " 0 " + j + "\n");
            } else {
                mag[0][j] = '+';
                mag[0][j + 1] = '-';
                magnetCount++;
                taken[0][j] = true;
                taken[0][j + 1] = true;
                out.write("0 " + j + " 0 " + (j + 1) + "\n");
            }
        } else
            return;
    }

    //  A  x  A 	->   A !A  A
    // !A  x !A			  !A  A !A
    public static void matchXv(char[][] mag, boolean[][] taken, int j, int safety, BufferedWriter out) throws IOException {
        //middle
        if (safety == 1) {
            if (isNegative(mag[0][j]) || isPositive(mag[1][j]) || isPositive(mag[0][j - 1]) || isPositive(mag[0][j + 1]) || isNegative(mag[1][j - 1]) || isNegative(mag[1][j + 1])) {
                mag[0][j] = '-';
                mag[1][j] = '+';
                magnetCount++;
                taken[0][j] = true;
                taken[1][j] = true;
                out.write("1 " + j + " 0 " + j + "\n");
            } else {
                mag[0][j] = '+';
                mag[1][j] = '-';
                magnetCount++;
                taken[0][j] = true;
                taken[1][j] = true;
                out.write("0 " + j + " 1 " + j + "\n");
            }
        }
        //beginning
        else if (safety == 2) {
            if (isNegative(mag[0][j]) || isPositive(mag[1][j]) || isPositive(mag[0][j + 1]) || isNegative(mag[1][j + 1])) {
                mag[0][j] = '-';
                mag[1][j] = '+';
                magnetCount++;
                taken[0][j] = true;
                taken[1][j] = true;
                out.write("1 " + j + " 0 " + j + "\n");
            } else {
                mag[0][j] = '+';
                mag[1][j] = '-';
                magnetCount++;
                taken[0][j] = true;
                taken[1][j] = true;
                out.write("0 " + j + " 1 " + j + "\n");
            }
        }
        //end
        else if (safety == 3) {
            if (isPositive(mag[1][j]) || isNegative(mag[0][j]) || isPositive(mag[0][j - 1]) || isNegative(mag[1][j - 1])) {
                mag[0][j] = '-';
                mag[1][j] = '+';
                magnetCount++;
                taken[0][j] = true;
                taken[1][j] = true;
                out.write("1 " + j + " 0 " + j + "\n");
            } else {
                mag[0][j] = '+';
                mag[1][j] = '-';
                magnetCount++;
                taken[0][j] = true;
                taken[1][j] = true;
                out.write("0 " + j + " 1 " + j + "\n");
            }
        }
        return;
    }

    // DO EVERYTHING!
    public static void reveal(char[][] mag) throws IOException {
        boolean[][] taken = new boolean[2][col];

        File f = new File("output.txt");
        if (!f.exists()) {
            f.createNewFile();
        }

        FileWriter fOut = new FileWriter(f);
        BufferedWriter out = new BufferedWriter(fOut);
        int safety = 0;
        for (int i = 0; i < 2 && magnetCount <= M; ++i)
        {
            for (int j = 0; j <= col - 1 && magnetCount <= M; j++)
            {
                safety = 0;
                if        ((safety = vertSafe(mag, taken,j)) != -99) {
                    matchXv(mag, taken, j, safety, out);
                } else if ((safety = topSafe(mag, taken,j))  != -99) {
                    matchXt(mag, taken, j, safety, out);
                } else if ((safety = botSafe(mag, taken,j))  != -99) {
                    matchXb(mag, taken, j, safety, out);
                }
            }
        }
        out.close();
    }

    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();
        FileReader in = new FileReader("input4.txt");
        BufferedReader input = new BufferedReader(in);
        //first line always column count
        M = Integer.parseInt(input.readLine());
        String temp1 = input.readLine().trim();
        String temp2 = input.readLine().trim();
        String[] temps = new String[2];
        temps[0] = temp1;
        temps[1] = temp2;
        col = temp1.length();

        char[][] magnets = new char[2][col];
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < col; ++j) {
                magnets[i][j] = temps[i].charAt(j);
            }
        }
        reveal(magnets);
        input.close();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Time in nanoseconds: "+duration);
        System.out.println("Time in milliseconds: "+duration/1000000.0);
    }
}
