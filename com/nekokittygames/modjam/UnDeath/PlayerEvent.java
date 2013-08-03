package com.nekokittygames.modjam.UnDeath;

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
			double x=event.entity.posX;
			double y=event.entity.posY;
			double z=event.entity.posZ;
			ItemSpawner.spawnCreature(event.entity.worldObj, x, y, z, (EntityPlayer)event.entity);
			event.setCanceled(true);
		}
	}
}
