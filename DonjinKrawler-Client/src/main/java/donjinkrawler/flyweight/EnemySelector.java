package donjinkrawler.flyweight;

import java.util.HashMap;
import java.util.Map;

public class EnemySelector {
    static Map<String, EnemyFlyweight> enemyTypes = new HashMap<>();

    public static EnemyFlyweight getEnemyType(String name) {
        EnemyFlyweight result = enemyTypes.get(name);
        if (result == null) {
            result = new EnemyFlyweight(name);
            enemyTypes.put(name, result);
        }
        return result;
    }
}
