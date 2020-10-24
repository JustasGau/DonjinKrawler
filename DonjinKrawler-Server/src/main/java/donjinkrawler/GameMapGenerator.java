package donjinkrawler;

import donjinkrawler.builder.BossRoomBuilder;
import donjinkrawler.builder.ItemRoomBuilder;
import donjinkrawler.builder.NormalRoomBuilder;
import donjinkrawler.builder.RoomDirector;
import donjinkrawler.logging.LoggerSingleton;
import krawlercommon.map.RoomData;

import java.util.*;

public class GameMapGenerator {
    private LoggerSingleton logger = LoggerSingleton.getInstance();

    private int size = 10;
    private final int walkerSteps = size * 4;
    private final int walkers = 2;

    public GameMapGenerator(int size) {
        this.size = size;
    }

    // 0 - empty cell
    // 1 - starting cell
    // x - cells by id
    // 9 - ending cell
    //Starts at the top left
    public List<String> generate() {
        int x = 0;
        int y = 0;
        Random rand = new Random();
        int minDif = -1;
        //has a bias to move right and down
        int maxDif = 2;
        int[][] mapGrid = new int[size][size];
        int cellId = 1;
        for (int i = 0; i < walkers; i++) {
            x = 0;
            y = 0;
            for (int j = 0; j < walkerSteps; j++) {

                int dif = rand.nextInt((maxDif - minDif) + 1) + minDif;
                if (dif > 1) {
                    dif = 1;
                }

                //changes direction
                if (j % 2 == 0) {
                    y += dif;
                } else {
                    x += dif;
                }
                //fail safes
                if (x < 0) {
                    x = 0;
                }
                if (x > (size - 1)) {
                    x = size - 1;
                }
                if (y < 0) {
                    y = 0;
                }
                if (y > (size - 1)) {
                    y = size - 1;
                }
                if (mapGrid[x][y] == 0) {
                    mapGrid[x][y] = cellId;
                    cellId++;
                }
            }
        }
        logger.info("Generated map \n");
        logger.info(printMap(mapGrid));
        return convertToAdjacencyMatrix(mapGrid, cellId + 1);
    }

    private List<String> convertToAdjacencyMatrix(int[][] mapGrid, int roomCount) {
        List<String> adjacencyMatrix = new ArrayList<>();
        Map<Integer, String> connections = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mapGrid[i][j] != 0) {
                    connections.put(mapGrid[i][j], getAdjacencyString(mapGrid, roomCount, i, j));
                }
            }
        }
        for (int i = 1; i < roomCount - 1; i++) {
            adjacencyMatrix.add(connections.get(i));
        }
        return adjacencyMatrix;
    }

    private String getAdjacencyString(int[][] mapGrid, int roomCount, int i, int j) {
        StringBuilder adjacencyString = new StringBuilder("0".repeat(roomCount));
        if (i != 0 && mapGrid[i - 1][j] != 0) {
            adjacencyString.setCharAt(mapGrid[i - 1][j] - 1, '1');
        }
        if (i < size - 1 && mapGrid[i + 1][j] != 0) {
            adjacencyString.setCharAt(mapGrid[i + 1][j] - 1, '1');
        }
        if (j != 0 && mapGrid[i][j - 1] != 0) {
            adjacencyString.setCharAt(mapGrid[i][j - 1] - 1, '1');
        }
        if (j < size - 1 && mapGrid[i][j + 1] != 0) {
            adjacencyString.setCharAt(mapGrid[i][j + 1] - 1, '1');
        }
        return adjacencyString.toString();
    }

    private String printMap(int[][] arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (arr[i][j] == 0) {
                    builder.append(" ");
                } else {
                    builder.append(arr[i][j]).append(" ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public HashMap<Integer, RoomData> generateRoomsFromString(List<String> adjacencyMatrix) {
        HashMap<Integer, RoomData> rooms = makeRooms(adjacencyMatrix);
        makeConnections(adjacencyMatrix, rooms);
        return rooms;
    }

    private HashMap<Integer, RoomData> makeRooms(List<String> adjacencyMatrix) {
        HashMap<Integer, RoomData> rooms = new HashMap<>();
        RoomDirector normalRoomDirector = new RoomDirector(new NormalRoomBuilder());
        RoomDirector itemRoomDirector = new RoomDirector(new ItemRoomBuilder());
        RoomDirector bossRoomDirector = new RoomDirector(new BossRoomBuilder());
        SplittableRandom random = new SplittableRandom();
        rooms.put(0, bossRoomDirector.constructRoom(0));
        for (int i = 1; i < adjacencyMatrix.size(); i++) {
            if (random.nextInt(10) == 0) {
                rooms.put(i, itemRoomDirector.constructRoom(i));
            } else if (i == size) {
                rooms.put(i, bossRoomDirector.constructRoom(i));
            } else {
                rooms.put(i, normalRoomDirector.constructRoom(i));
            }
        }
        return rooms;
    }

    private void makeConnections(List<String> adjacencyMatrix, HashMap<Integer, RoomData> rooms) {
        for (int i = 0; i < adjacencyMatrix.size(); i++) {
            RoomData roomData = rooms.get(i);
            char[] chars = adjacencyMatrix.get(i).toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == '1') {
                    roomData.setAdjacentRooms(rooms.get(j));
                }
            }
        }
    }
}
