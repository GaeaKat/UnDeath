package com.nekokittygames.modjam.UnDeath;

import java.util.Random;

import net.minecraft.client.Minecraft;
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
			Random rand=new Random();
			int num=rand.nextInt(100);
			UnDeath.logging.info(String.format("I got %f,  and zombification chance is: %f",((double)num/100),Configs.ZombificationChance));
			if(((float)num/100.0f) <= Configs.ZombificationChance)
			{
				double x=event.entity.posX;
				double y=event.entity.posY;
				double z=event.entity.posZ;
				ItemSpawner.spawnCreature(event.entity.worldObj,1, x, y, z, (EntityPlayer)event.entity);
				spawning=true;
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
