/**
 * 
 */
package com.nekokittygames.modjam.UnDeath.client;

import net.minecraft.client.model.ModelSlime;

import com.nekokittygames.modjam.UnDeath.EntityPlayerSkellington;
import com.nekokittygames.modjam.UnDeath.EntityPlayerSlime;
import com.nekokittygames.modjam.UnDeath.EntityPlayerZombie;
import com.nekokittygames.modjam.UnDeath.EntityPlayerZombiePigmen;
import com.nekokittygames.modjam.UnDeath.ServerProxy;

import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * @author Katrina
 *
 */
public class ClientProxy extends ServerProxy {


	@Override
	public void SetupRenderers()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityPlayerZombie.class, new RenderPlayerZombie());
		RenderingRegistry.registerEntityRenderingHandler(EntityPlayerSkellington.class, new RenderPlayerSkellington());
		RenderingRegistry.registerEntityRenderingHandler(EntityPlayerSlime.class, new RenderPlayerSlime(new ModelSlime(16), new ModelSlime(0), 0.25f));
		RenderingRegistry.registerEntityRenderingHandler(EntityPlayerZombiePigmen.class, new RenderPlayerPigZombie());
	}
}
