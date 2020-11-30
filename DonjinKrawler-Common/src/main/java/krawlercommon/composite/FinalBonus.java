package krawlercommon.composite;

import java.util.Timer;
import java.util.TimerTask;

public class FinalBonus extends BaseAttribute {

    private Attribute parent;
    private long time;

    public FinalBonus() {
        this(0, 0, 0);
    }

    public FinalBonus(long time) {
        this(0, 0, time);
    }

    public FinalBonus(int value, double multiplier, long time) {
        super(value, multiplier);
        this.time = time;
    }

    public void startTimer(Attribute parent) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                parent.removeFinalBonus(FinalBonus.this);
            }
        }, time * 1000);
    }
}
