package donjinkrawler.flyweight;

import java.util.HashMap;
import java.util.Map;

public class EnemySelector {
    static Map<String, EnemyType> enemyTypes = new HashMap<>();

    public static EnemyType getEnemyType(String name) {
        EnemyType result = enemyTypes.get(name);
        if (result == null) {
            result = new EnemyType(name);
            enemyTypes.put(name, result);
        }
        return result;
    }
}
