package donjinkrawler.flyweight;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.EnemyShell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlyweightEfficiencyTest {

    static int ENEMIES_TO_DRAW = 1000000;
    static int TYPE_NUM = 7;

    // Guestimated sizes of the object properties
//    int x - 4
//    int y - 4
//    double health - 8
//    String info - 22
//    Name is in flyweight
//    String name - 24
    public static void main(String[] args) throws InterruptedException {
        int cycle = (ENEMIES_TO_DRAW / TYPE_NUM);
        System.out.println("Cycle: " + cycle);
        List<AbstractShellInterface> shells = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < cycle; i++) {
            shells.add(getEnemyObject("Small-Zombie", 0, 0, 0));
            shells.add(getEnemyObject("Big-Zombie", 0, 0, 0));
            shells.add(getEnemyObject("Small-Skeleton", 0, 0, 0));
            shells.add(getEnemyObject("Big-Skeleton", 0, 0, 0));
            shells.add(getEnemyObject("Small-Chicken", 0, 0, 0));
            shells.add(getEnemyObject("Big-Chicken", 0, 0, 0));
            shells.add(getEnemyObject("Boss", 0, 0, 0));
        }
        long end = System.nanoTime();
        long elapsedTime = end - start;
        System.out.println(elapsedTime);
        long allEnemiesSize = 1323 + 1501 + 14379 + 13059 + 1101 + 1181 + 16700 + (24 * 7);
        System.out.println((allEnemiesSize + (38 * cycle)) / 1024 / 1024 + "MB");

        shells = new ArrayList<>();
        start = System.nanoTime();
        for (int i = 0; i < cycle; i++) {
            shells.add(new EnemyShellForTest("Small-Zombie", 0, 0, 0));
            shells.add(new EnemyShellForTest("Big-Zombie", 0, 0, 0));
            shells.add(new EnemyShellForTest("Small-Skeleton", 0, 0, 0));
            shells.add(new EnemyShellForTest("Big-Skeleton", 0, 0, 0));
            shells.add(new EnemyShellForTest("Small-Chicken", 0, 0, 0));
            shells.add(new EnemyShellForTest("Big-Chicken", 0, 0, 0));
            shells.add(new EnemyShellForTest("Boss", 0, 0, 0));
        }
        end = System.nanoTime();
        elapsedTime = end - start;
        System.out.println(elapsedTime);
        allEnemiesSize = 1323 + 1501 + 14379 + 13059 + 1101 + 1181 + 16700 + (52 * 7);
        System.out.println((allEnemiesSize * cycle) / 1024 / 1024 + "MB");
        TimeUnit.SECONDS.sleep(10);
    }

    static private EnemyShell getEnemyObject(String name, int id, int x, int y) {
        EnemyFlyweight type = EnemySelector.getEnemyType(name);
        EnemyShell enemy = new EnemyShell(name, id, x, y, type);
        return enemy;
    }
}
