package com.nekokittygames.modjam.UnDeath.client;

import java.util.HashMap;
import java.util.Map;

import com.nekokittygames.modjam.UnDeath.EntityPlayerSkellington;
import com.nekokittygames.modjam.UnDeath.EntityPlayerZombiePigmen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderPlayerPigZombie extends RenderBiped {
	private static final Map<String,ResourceLocation> layerdSkins = new HashMap<String, ResourceLocation>();
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;

public RenderPlayerPigZombie() {
	super(new ModelBiped(0.0F), 0.5F);
    this.modelBipedMain = (ModelBiped)this.mainModel;
    this.modelArmorChestplate = new ModelBiped(1.0F);
    this.modelArmor = new ModelBiped(0.5F);
}





@Override
protected ResourceLocation func_110775_a(Entity par1Entity)
{
    //return this.func_110817_a((EntityPlayerZombie)par1Entity);
	return this.getLayered((EntityPlayerZombiePigmen)par1Entity);
}

public ResourceLocation getLayered(EntityPlayerZombiePigmen par1EntityPlayerSkellington)
{
	String s=par1EntityPlayerSkellington.getLayeredName();
	ResourceLocation rl=layerdSkins.get(s);
	if(rl==null)
	{
		rl=new ResourceLocation(s);
		Minecraft.getMinecraft().func_110434_K().func_110579_a(rl,new ResourceLayeredTexture(par1EntityPlayerSkellington.getSkins()));
		layerdSkins.put(s, rl);
	}
	return rl;
	
}

// this is where it gets the thign to render as skin
protected ResourceLocation func_110817_a(EntityPlayerZombiePigmen par1EntityPlayerSkellington)
{
    return par1EntityPlayerSkellington.func_110306_p();
}
}
