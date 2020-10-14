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
import krawlercommon.items.*;
import krawlercommon.map.*;
import krawlercommon.packets.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RegistrationManager {

    public static void registerKryo(Kryo kryo) {
        // Packets
        kryo.register(LoginPacket.class);
        kryo.register(MessagePacket.class);
        kryo.register(EnemyPacket.class);
        kryo.register(MapPacket.class);
        kryo.register(IdPacket.class);
        kryo.register(CreatePlayerPacket.class);
        kryo.register(MoveCharacter.class);
        kryo.register(DisconnectPacket.class);
        kryo.register(RoomPacket.class);

        // DonjinKrawler classes
        kryo.register(PlayerData.class);
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
        kryo.register(RoomData.class);
        kryo.register(RoomType.class);
        kryo.register(Door.class);
        kryo.register(DoorDirection.class);
        kryo.register(Wall.class);
        kryo.register(ItemData.class);
        kryo.register(WeaponData.class);
        kryo.register(ArmorData.class);
        kryo.register(SpeedPotionData.class);
        kryo.register(HealthPotionData.class);
        kryo.register(DamagePotionData.class);
        kryo.register(Obstacle.class);
        kryo.register(ObstacleType.class);
        kryo.register(Decoration.class);

        // jdk classes
        kryo.register(ArrayList.class);
        kryo.register(UUID.class);
        kryo.register(Random.class);
        kryo.register(AtomicLong.class);
        // TODO: check if needed
        kryo.register(HashMap.class);
    }
}
