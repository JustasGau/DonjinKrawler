package krawlercommon.enemies;

public abstract class EnemyTemplate {

    public void initEnemy() {
        setInterval();
        setStrategies();
        debug();
    }

    protected abstract void setInterval();

    protected abstract void setStrategies();

    public void debug(){}

}
