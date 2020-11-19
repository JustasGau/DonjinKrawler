package donjinkrawler.flyweight;

import donjinkrawler.AbstractShellInterface;
import donjinkrawler.EnemyShell;

import java.util.ArrayList;
import java.util.List;
import java.lang.instrument.Instrumentation;

public class FlyweightEfficiencyTest {

    static int ENEMIES_TO_DRAW = 10000000;
    static int TYPE_NUM = 7;


    public static void main(String[] args){
        int cycle = (int)(ENEMIES_TO_DRAW/TYPE_NUM);
        List<AbstractShellInterface> shells = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < cycle; i++) {
            shells.add(getEnemyObject("Small-Zombie",  0, 0, 0));
            shells.add(getEnemyObject("Big-Zombie",  0, 0, 0));
            shells.add(getEnemyObject("Small-Skeleton",  0, 0, 0));
            shells.add(getEnemyObject("Big-Skeleton",  0, 0, 0));
            shells.add(getEnemyObject("Small-Chicken",  0, 0, 0));
            shells.add(getEnemyObject("Big-Chicken",  0, 0, 0));
            shells.add(getEnemyObject("Boss",  0, 0, 0));
        }
        long end = System.nanoTime();
        long elapsedTime = end - start;
        System.out.println(elapsedTime);
        System.out.println(((1323 + 1501 + 14379 + 13059 + 1101 + 1181 + 16700) * cycle) / 1024 / 1024 + "MB");

        shells = new ArrayList<>();
        start = System.nanoTime();
        for (int i = 0; i < cycle; i++) {
            shells.add(new EnemyShellForTest("Small-Zombie",  0, 0, 0));
            shells.add(new EnemyShellForTest("Big-Zombie",  0, 0, 0));
            shells.add(new EnemyShellForTest("Small-Skeleton",  0, 0, 0));
            shells.add(new EnemyShellForTest("Big-Skeleton",  0, 0, 0));
            shells.add(new EnemyShellForTest("Small-Chicken",  0, 0, 0));
            shells.add(new EnemyShellForTest("Big-Chicken",  0, 0, 0));
            shells.add(new EnemyShellForTest("Boss",  0, 0, 0));
        }
        end = System.nanoTime();
        elapsedTime = end - start;
        System.out.println(elapsedTime);
        System.out.println(((1323 + 1501 + 14379 + 13059 + 1101 + 1181 + 16700)) / 1024 / 1024 + "MB");
    }

    static private EnemyShell getEnemyObject(String name, int id, int x, int y) {
        EnemyType type = EnemySelector.getEnemyType(name);
        EnemyShell enemy = new EnemyShell(name, id, x, y, type);
        return enemy;
    }
}
