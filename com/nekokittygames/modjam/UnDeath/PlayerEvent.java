package com.nekokittygames.modjam.UnDeath;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

public class PlayerEvent {

	public boolean spawning=false;
	
	@ForgeSubscribe
	public void PlayerDies(LivingDeathEvent event)
	{
		
		if(event.entity.worldObj.isRemote)
		{
			return;
		}
		if(Minecraft.getMinecraft().gameSettings.difficulty==0)
		{
			return;
		}
		if(event.entity instanceof EntityPlayer)
		{
			if (event.source.getEntity() instanceof EntitySkeleton ||event.source.getEntity() instanceof EntityPlayerSkellington)
			{
				Random rand=new Random();
				int num=rand.nextInt(100);
				UnDeath.logging.info(String.format("I got %f,  and Skellification chance is: %f",((double)num/100),Configs.SkellificationChance));
				if(((float)num/100.0f) <= Configs.SkellificationChance)
				{
					double x=event.entity.posX;
					double y=event.entity.posY;
					double z=event.entity.posZ;
					ItemSpawner.spawnCreature(event.entity.worldObj,EntityPlayerSkellington.EntityId, x, y, z, (EntityPlayer)event.entity);
					spawning=true;
				}
				return;
			}
			if (event.source.getEntity() instanceof EntitySlime ||event.source.getEntity() instanceof EntityPlayerSlime)
			{
				Random rand=new Random();
				int num=rand.nextInt(100);
				UnDeath.logging.info(String.format("I got %f,  and slimeEngulf chance is: %f",((double)num/100),Configs.slimeEngulfChance));
				if(((float)num/100.0f) <= Configs.slimeEngulfChance)
				{
					double x=event.entity.posX;
					double y=event.entity.posY;
					double z=event.entity.posZ;
					ItemSpawner.spawnCreature(event.entity.worldObj,EntityPlayerSkellington.EntityId, x, y, z, (EntityPlayer)event.entity);
					event.source.getEntity().isDead=true;
					spawning=true;
				}
				return;
			}
			
		}
	}
	@ForgeSubscribe
	public void PlayerDrops(PlayerDropsEvent event)
	{
		if(spawning=true)
		{
			event.setCanceled(true);
			spawning=false;
	}
	}
}
