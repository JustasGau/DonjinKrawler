package krawlercommon.enemies;

public abstract class EnemyTemplate {

    public void initEnemy() {
        setInterval();
        setStrategies();
        debug();
    }

    abstract void setInterval();
    abstract void setStrategies();
    public void debug(){}

}
