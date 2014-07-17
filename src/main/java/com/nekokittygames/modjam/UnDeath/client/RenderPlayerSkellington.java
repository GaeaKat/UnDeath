package com.nekokittygames.modjam.UnDeath.client;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.nekokittygames.modjam.UnDeath.EntityPlayerSkellington;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class RenderPlayerSkellington extends RenderBiped {

	private static final Map<String,ResourceLocation> layerdSkins = new HashMap<String, ResourceLocation>();
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;

public RenderPlayerSkellington() {
	super(new ModelBiped(0.0F), 0.5F);
    this.modelBipedMain = (ModelBiped)this.mainModel;
    this.modelArmorChestplate = new ModelBiped(1.0F);
    this.modelArmor = new ModelBiped(0.5F);
}





@Override
protected ResourceLocation getEntityTexture(Entity par1Entity)
{
    //return this.func_110817_a((EntityPlayerZombie)par1Entity);
	return this.getLayered((EntityPlayerSkellington)par1Entity);
}

public ResourceLocation getLayered(EntityPlayerSkellington par1EntityPlayerSkellington)
{
	String s=par1EntityPlayerSkellington.getLayeredName();
	ResourceLocation rl=layerdSkins.get(s);
	if(rl==null)
	{
		rl=new ResourceLocation(s);
        ResourceLocation loc=null;
        if (par1EntityPlayerSkellington.gameProfile!= null)
        {

            Minecraft minecraft = Minecraft.getMinecraft();
            Map map = minecraft.func_152342_ad().func_152788_a(par1EntityPlayerSkellington.gameProfile);

            if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
            {
                loc = minecraft.func_152342_ad().func_152792_a((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            }
            else
                loc=EntityPlayerSkellington.field_110314_b;
        }
        Minecraft.getMinecraft().getTextureManager().loadTexture(rl,new RResourceLayeredTexture(loc,EntityPlayerSkellington.overlay,par1EntityPlayerSkellington.getSkellingtonName()));
		layerdSkins.put(s, rl);
	}
	return rl;
	
}

// this is where it gets the thign to render as skin
protected ResourceLocation func_110817_a(EntityPlayerSkellington par1EntityPlayerSkellington)
{
    return par1EntityPlayerSkellington.func_110306_p();
}
}
