package donjinkrawler.enemies;

public interface EnemyFactory {
    public Enemy make(EnemyType enemyType, int id);
}
