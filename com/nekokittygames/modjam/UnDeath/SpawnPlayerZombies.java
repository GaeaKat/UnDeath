/**
 * 
 */
package com.nekokittygames.modjam.UnDeath;

import net.minecraft.entity.Entity;

import com.google.common.base.Function;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Katrina
 *
 */
public class SpawnPlayerZombies implements Function<EntitySpawnPacket, Entity> {

	@Override
	public
	Entity apply(EntitySpawnPacket input) {
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
		{
			return new EntityPlayerZombie(FMLClientHandler.instance().getClient().theWorld);
		}
		return null;
		//new EntityPlayerZombie(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(input.))
	}

}
