/**
 * 
 */
package com.nekokittygames.modjam.UnDeath;

import com.google.common.base.Function;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.internal.FMLMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;

/**
 * @author Katrina
 *
 */
public class SpawnPlayerZombies implements Function<FMLMessage.EntitySpawnMessage, Entity> {

	@Override
	@SideOnly(Side.CLIENT)
	public 	Entity apply(FMLMessage.EntitySpawnMessage input) {
		if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT)
		{
			return new EntityPlayerZombie(FMLClientHandler.instance().getClient().theWorld);
		}
		return null;
		//new EntityPlayerZombie(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(input.))
	}

}
