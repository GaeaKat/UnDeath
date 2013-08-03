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
@NetworkMod(clientSideRequired=true,serverSideRequired=false,channels={"undeathZombie"},packetHandler=PacketHandler.class)
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
		
		logging.info(Configs.TestString);
		logging.info(Boolean.toString(Configs.KeepInventory));
		
		spawner=new ItemSpawner(Configs.debugStick);
		LanguageRegistry.addName(spawner, "debug PZ Spawner");
	}
	public static int PlayerZombieId;
	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		EntityRegistry.registerGlobalEntityID(EntityPlayerZombie.class, "playerZombie", EntityRegistry.findGlobalUniqueEntityId(), 0xff0000, 0x00ff00);
		EntityRegistry.registerModEntity(EntityPlayerZombie.class, "playerZombie", EntityRegistry.findGlobalUniqueEntityId(), this, 80, 3, true);
		EntityRegistration er=EntityRegistry.instance().lookupModSpawn(EntityPlayerZombie.class, false);
		er.setCustomSpawning(new SpawnPlayerZombies(), false);
		LanguageRegistry.instance().addStringLocalization("entity.playerZombie.name", "Player Zombie");
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
