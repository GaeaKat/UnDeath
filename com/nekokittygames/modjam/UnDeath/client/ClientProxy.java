/**
 * 
 */
package com.nekokittygames.modjam.UnDeath.client;

import com.nekokittygames.modjam.UnDeath.EntityPlayerZombie;
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
	}
}
