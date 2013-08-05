package com.nekokittygames.modjam.UnDeath;

import java.util.logging.Logger;

import org.lwjgl.Sys;

import com.google.common.base.Function;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid=UnDeath.ID,name=UnDeath.NAME,version=UnDeath.VERSION,modLanguage="java")
@NetworkMod(clientSideRequired=true,serverSideRequired=false)
public class UnDeath {
	public static final String ID = "UnDeath";
	public static final String VERSION = "1.0";
	public static final String NAME = "Un Death";
	public static ItemSpawner spawner;
	
	@Instance(ID)
	public static UnDeath Instance;
	
	@SidedProxy(clientSide="com.nekokittygames.modjam.UnDeath.client.ClientProxy",serverSide="com.nekokittygames.modjam.UnDeath.ServerProxy")
	public static ServerProxy proxy;
	
	public static Logger logging;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event)
	{
		logging=Logger.getLogger(ID);
		logging.setParent(FMLLog.getLogger());
		
		Configuration config=new Configuration(event.getSuggestedConfigurationFile());
		Configs.load(config);
		
		
		spawner=new ItemSpawner(Configs.debugStick);
		LanguageRegistry.addName(spawner, "debug Spawner");
	}
	public static int PlayerZombieId;
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		int playerZombieId=EntityRegistry.findGlobalUniqueEntityId();
		
		
		EntityRegistry.registerGlobalEntityID(EntityPlayerZombie.class, "playerZombie", playerZombieId, 0xff0000, 0x00ff00);
		int playerSkellingtonId=EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(EntityPlayerSkellington.class, "playerSkellington", playerSkellingtonId, 0xff0000, 0x00ff00);
		int playerSlimeId=EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityPlayerSlime.class, "playerSlime", playerSlimeId, 0xff0000, 0x00ff00);
		int playerPigZombieId=EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerGlobalEntityID(EntityPlayerZombiePigmen.class, "playerPigZombie", playerPigZombieId, 0xff0000, 0x00ff00);
		EntityPlayerZombie.EntityId=playerZombieId;
		EntityPlayerSkellington.EntityId=playerSkellingtonId;
		EntityPlayerSlime.EntityId=playerSlimeId;
		EntityPlayerZombiePigmen.EntityID=playerPigZombieId;
		
		// Register entries
		EntityRegistry.registerModEntity(EntityPlayerZombie.class, "playerZombie", playerZombieId, this, 80, 3, true);
		EntityRegistry.registerModEntity(EntityPlayerSkellington.class, "playerSkellington", playerSkellingtonId, this, 80, 3, true);
		EntityRegistry.registerModEntity(EntityPlayerSlime.class, "playerSlime", playerSlimeId, this, 80, 3, true);
		EntityRegistry.registerModEntity(EntityPlayerZombiePigmen.class, "playerPigZombie", playerPigZombieId, this, 80, 3, true);
		
		EntityRegistration er=EntityRegistry.instance().lookupModSpawn(EntityPlayerZombie.class, false);
		er.setCustomSpawning(new SpawnPlayerZombies(), false);
		
		EntityRegistration er2=EntityRegistry.instance().lookupModSpawn(EntityPlayerSkellington.class, false);
		er2.setCustomSpawning(new SpawnPlayerSkellingtons(), false);
		
		EntityRegistration er3=EntityRegistry.instance().lookupModSpawn(EntityPlayerSlime.class, false);
		er3.setCustomSpawning(new SpawnPlayerSlimes(), false);
		
		
		LanguageRegistry.instance().addStringLocalization("entity.playerZombie.name", "Player Zombie");
		LanguageRegistry.instance().addStringLocalization("entity.playerSkellington.name", "Player Skellington");
		LanguageRegistry.instance().addStringLocalization("entity.playerSlime.name", "Player Slime");
		LanguageRegistry.instance().addStringLocalization("entity.playerPigZombie.name", "Player Pig Zombie");
		proxy.SetupRenderers();
		MinecraftForge.EVENT_BUS.register(new PlayerEvent());
	}
	
	public void SpawnFunction()
	{
		
	}
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
	}
}
