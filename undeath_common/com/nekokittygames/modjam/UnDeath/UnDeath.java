package com.nekokittygames.modjam.UnDeath;

import java.util.logging.Logger;

import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = UnDeath.ID, name = UnDeath.NAME, version = UnDeath.VERSION, modLanguage = "java")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class UnDeath {
    public static final String ID      = "UnDeath";
    public static final String VERSION = "1.0";
    public static final String NAME    = "Un Death";
    public static ItemSpawner  spawner;
    static Achievement         undeadKilledYourself;
    static Achievement         youKilledYourself;
    @Instance(ID)
    public static UnDeath      Instance;

    @SidedProxy(clientSide = "com.nekokittygames.modjam.UnDeath.client.ClientProxy", serverSide = "com.nekokittygames.modjam.UnDeath.ServerProxy")
    public static ServerProxy  proxy;

    public static Logger       logging;

    public static int          PlayerZombieId;

    private void addAchievementDesc(String ach, String desc) {
        LanguageRegistry.instance().addStringLocalization(
                "achievement." + ach + ".desc", "en_US", desc);
    }

    private void addAchievementName(String ach, String name) {
        LanguageRegistry.instance().addStringLocalization("achievement." + ach,
                "en_US", name);
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        final int playerZombieId = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(EntityPlayerZombie.class,
                "playerZombie", playerZombieId);
        final int playerSkellingtonId = EntityRegistry
                .findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(EntityPlayerSkellington.class,
                "playerSkellington", playerSkellingtonId);
        final int playerSlimeId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityPlayerSlime.class,
                "playerSlime", playerSlimeId);
        final int playerPigZombieId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityPlayerZombiePigmen.class,
                "playerPigZombie", playerPigZombieId);
        EntityPlayerZombie.EntityId = playerZombieId;
        EntityPlayerSkellington.EntityId = playerSkellingtonId;
        EntityPlayerSlime.EntityId = playerSlimeId;
        EntityPlayerZombiePigmen.EntityId = playerPigZombieId;

        // Register entries
        EntityRegistry.registerModEntity(EntityPlayerZombie.class,
                "playerZombie", playerZombieId, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityPlayerSkellington.class,
                "playerSkellington", playerSkellingtonId, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityPlayerSlime.class,
                "playerSlime", playerSlimeId, this, 80, 3, true);
        EntityRegistry.registerModEntity(EntityPlayerZombiePigmen.class,
                "playerPigZombie", playerPigZombieId, this, 80, 3, true);

        final EntityRegistration er = EntityRegistry.instance().lookupModSpawn(
                EntityPlayerZombie.class, false);
        er.setCustomSpawning(new SpawnPlayerZombies(), false);

        final EntityRegistration er2 = EntityRegistry.instance()
                .lookupModSpawn(EntityPlayerSkellington.class, false);
        er2.setCustomSpawning(new SpawnPlayerSkellingtons(), false);

        final EntityRegistration er3 = EntityRegistry.instance()
                .lookupModSpawn(EntityPlayerSlime.class, false);
        er3.setCustomSpawning(new SpawnPlayerSlimes(), false);

        final EntityRegistration er4 = EntityRegistry.instance()
                .lookupModSpawn(EntityPlayerZombiePigmen.class, false);
        er4.setCustomSpawning(new SpawnPlayerPigZombies(), false);

        LanguageRegistry.instance().addStringLocalization(
                "entity.playerZombie.name", "Player Zombie");
        LanguageRegistry.instance().addStringLocalization(
                "entity.playerSkellington.name", "Player Skellington");
        LanguageRegistry.instance().addStringLocalization(
                "entity.playerSlime.name", "Player Slime");
        LanguageRegistry.instance().addStringLocalization(
                "entity.playerPigZombie.name", "Player Pig Zombie");
        proxy.SetupRenderers();
        MinecraftForge.EVENT_BUS.register(new PlayerEvent());
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        logging = Logger.getLogger(ID);
        logging.setParent(FMLLog.getLogger());

        final Configuration config = new Configuration(
                event.getSuggestedConfigurationFile());
        Configs.load(config);
        undeadKilledYourself = new Achievement(
                Configs.undeadkillsYouAchivementID, "undeadKilledYou", 1, -2,
                Item.skull, AchievementList.buildSword).registerAchievement();
        youKilledYourself = new Achievement(Configs.YoukillsYouAchivementID,
                "YouKilledYou", 1, -2, Item.cake, AchievementList.buildSword)
                .setSpecial().registerAchievement();
        spawner = new ItemSpawner(Configs.debugStick);
        LanguageRegistry.addName(spawner, "debug Spawner");
        addAchievementName("undeadKilledYou", "Undead kill you");
        addAchievementDesc("undeadKilledYou",
                "The undead corpse of a player, killed you");

        addAchievementName("YouKilledYou", "You kill you");
        addAchievementDesc("YouKilledYou", "You defeat your own undead corpse");
    }

    public void SpawnFunction() {

    }
}
