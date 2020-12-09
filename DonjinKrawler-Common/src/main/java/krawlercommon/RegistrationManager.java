package krawlercommon;

import com.esotericsoftware.kryo.Kryo;
import krawlercommon.composite.Attribute;
import krawlercommon.composite.BaseAttribute;
import krawlercommon.composite.FinalBonus;
import krawlercommon.composite.RawBonus;
import krawlercommon.enemies.*;
import krawlercommon.enemies.big.BigChicken;
import krawlercommon.enemies.big.BigSkeleton;
import krawlercommon.enemies.big.BigZombie;
import krawlercommon.enemies.small.SmallChicken;
import krawlercommon.enemies.small.SmallSkeleton;
import krawlercommon.enemies.small.SmallZombie;
import krawlercommon.items.*;
import krawlercommon.map.*;
import krawlercommon.map.obstacles.Lava;
import krawlercommon.map.obstacles.Slime;
import krawlercommon.map.obstacles.Spikes;
import krawlercommon.packets.*;
import krawlercommon.strategies.*;

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
        kryo.register(ChangeEnemyStrategyPacket.class);
        kryo.register(CharacterAttackPacket.class);
        kryo.register(DamageEnemyPacket.class);
        kryo.register(ServerFullPacket.class);
        kryo.register(ChatMessagePacket.class);
        kryo.register(GetPlayersPacket.class);
        kryo.register(KickPlayerPacket.class);

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
        kryo.register(ItemLocationData.class);
        kryo.register(WeaponData.class);
        kryo.register(ArmorData.class);
        kryo.register(SpeedPotionData.class);
        kryo.register(HealthPotionData.class);
        kryo.register(DamagePotionData.class);
        kryo.register(Obstacle.class);
        kryo.register(Lava.class);
        kryo.register(Slime.class);
        kryo.register(Spikes.class);
        kryo.register(Decoration.class);
        kryo.register(Attack.class);
        kryo.register(MoveAwayFromPlayer.class);
        kryo.register(MoveRandomly.class);
        kryo.register(MoveTowardPlayer.class);
        kryo.register(RangeAttack.class);
        kryo.register(Enemy.Phases.class);
        kryo.register(Boss.class);
        kryo.register(Attribute.class);
        kryo.register(FinalBonus.class);
        kryo.register(RawBonus.class);
        kryo.register(BaseAttribute.class);

        // jdk classes
        kryo.register(ArrayList.class);
        kryo.register(UUID.class);
        kryo.register(Random.class);
        kryo.register(AtomicLong.class);
        // TODO: check if needed
        kryo.register(HashMap.class);
    }
}
