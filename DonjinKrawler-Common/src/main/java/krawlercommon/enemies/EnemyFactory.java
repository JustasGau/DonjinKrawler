package krawlercommon.enemies;

public interface EnemyFactory {
    public Enemy make(EnemyType enemyType);
}
