package krawlercommon;

import com.esotericsoftware.kryo.Kryo;
import krawlercommon.enemies.Chicken;
import krawlercommon.enemies.Enemy;
import krawlercommon.enemies.Skeleton;
import krawlercommon.enemies.Zombie;
import krawlercommon.enemies.big.BigChicken;
import krawlercommon.enemies.big.BigSkeleton;
import krawlercommon.enemies.big.BigZombie;
import krawlercommon.enemies.small.SmallChicken;
import krawlercommon.enemies.small.SmallSkeleton;
import krawlercommon.enemies.small.SmallZombie;
import krawlercommon.packets.ConnectPacket;
import krawlercommon.packets.EnemyPacket;
import krawlercommon.packets.MessagePacket;
import krawlercommon.strategies.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RegistrationManager {

    public static void registerKryo(Kryo kryo) {
        kryo.register(ConnectPacket.class);
        kryo.register(MessagePacket.class);
        kryo.register(EnemyPacket.class);
        kryo.register(ArrayList.class);
        kryo.register(Enemy.class);
        kryo.register(Chicken.class);
        kryo.register(Skeleton.class);
        kryo.register(Zombie.class);
        kryo.register(SmallChicken.class);
        kryo.register(SmallSkeleton.class);
        kryo.register(SmallZombie.class);
        kryo.register(BigChicken.class);
        kryo.register(BigSkeleton.class);
        kryo.register(BigZombie.class);
        kryo.register(Attack.class);
        kryo.register(MoveAwayFromPlayer.class);
        kryo.register(MoveRandomly.class);
        kryo.register(MoveTowardPlayer.class);
        kryo.register(RangeAttack.class);
        kryo.register(UUID.class);
        kryo.register(Random.class);
        kryo.register(AtomicLong.class);
    }
}
