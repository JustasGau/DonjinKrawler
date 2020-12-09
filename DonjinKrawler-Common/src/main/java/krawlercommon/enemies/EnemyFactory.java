package krawlercommon.enemies;

public interface EnemyFactory {
    Enemy make(EnemyType enemyType);
}
