package com.nekokittygames.modjam.UnDeath;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

public class PlayerEvent {

	@ForgeSubscribe
	public void PlayerDies(LivingDeathEvent event)
	{
		
		if(event.entity.worldObj.isRemote)
		{
			return;
		}
		if(event.entity instanceof EntityPlayer)
		{
			Random rand=new Random();
			int num=rand.nextInt(100);
			if(num/100 <= Configs.ZombificationChance)
			{
				double x=event.entity.posX;
				double y=event.entity.posY;
				double z=event.entity.posZ;
				ItemSpawner.spawnCreature(event.entity.worldObj, x, y, z, (EntityPlayer)event.entity);
				event.setCanceled(true);
			}
		}
	}
}
