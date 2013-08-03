package com.nekokittygames.modjam.UnDeath;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

public class PlayerEvent {

	@ForgeSubscribe
	public void PlayerDies(PlayerDropsEvent event)
	{
		double x=event.entityPlayer.posX;
		double y=event.entityPlayer.posY;
		double z=event.entityPlayer.posZ;
		
		ItemSpawner.spawnCreature(event.entity.worldObj, x, y, z, event.entityPlayer);
		event.setCanceled(true);
	}
}
