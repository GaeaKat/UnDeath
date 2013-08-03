package com.nekokittygames.modjam.UnDeath;

import net.minecraft.entity.Entity;

import com.google.common.base.Function;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SpawnPlayerSkellingtons implements Function<EntitySpawnPacket, Entity> {

	@Override
	@SideOnly(Side.CLIENT)
	public 	Entity apply(EntitySpawnPacket input) {
		UnDeath.logging.info(FMLCommonHandler.instance().getEffectiveSide().toString());
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
		{
			return new EntityPlayerSkellington(FMLClientHandler.instance().getClient().theWorld);
		}
		return null;
		//new EntityPlayerZombie(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(input.))
	}

}

