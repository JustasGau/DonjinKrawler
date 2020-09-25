package main.java.DonjinKrawler;

import java.util.Random;

public class GameMapGenerator {

    private int size = 10;
    private int walkerSteps = size*4;
    private int walkers = 2;
    private int cellIDNum = 10;

    public GameMapGenerator(int size) {
        this.size = size;
    }

    // 0 - empty cell
    // 1 - starting cell
    // x - cells by id
    // 9 - ending cell
    //Starts at the top left
    public String generate() {
        int x = 0;
        int y = 0;
        Random rand = new Random();
        int minDif = -1;
        //has a bias to move right and down
        int maxDif = 2;
        int[][] mapGrid = new int[size][size];

        for (int i = 0; i < walkers; i++) {
            x = 0;
            y = 0;
            for (int j = 0; j < walkerSteps; j++) {

                int dif = rand.nextInt((maxDif - minDif) + 1) + minDif;
                if (dif > 1 ) { dif = 1; }
                int cellID = rand.nextInt((8 - 2) + 1) + 2;

                //changes direction
                if (j%2 == 0)
                    y += dif;
                else
                    x += dif;

                //fail safes
                if (x < 0)
                    x = 0;
                if (x > (size-1))
                    x = size - 1;
                if (y < 0)
                    y = 0;
                if (y > (size-1))
                    y = size - 1;

                mapGrid[x][y] = cellID;
            }

        }
        mapGrid[x][y] = 9;
        mapGrid[0][0] = 1;
        printMap(mapGrid);
        return convertToString(mapGrid);
    }

    private String convertToString(int[][] arr) {
        String mapString = "";
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                mapString = mapString + arr[i][j];
            }
        }
        return mapString;
    }
    private void printMap(int[][] arr) {
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
}
