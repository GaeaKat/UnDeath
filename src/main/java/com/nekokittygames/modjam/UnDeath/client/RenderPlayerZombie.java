package com.nekokittygames.modjam.UnDeath.client;

import com.nekokittygames.modjam.UnDeath.EntityPlayerZombie;
import com.nekokittygames.modjam.UnDeath.EntityPlayerZombiePigmen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
@SideOnly(Side.CLIENT)
public class RenderPlayerZombie extends RenderBiped {
	
	 	
	 	private static final Map<String,ResourceLocation> layerdSkins = new HashMap<String, ResourceLocation>();
	    private ModelBiped modelBipedMain;
	    private ModelBiped modelArmorChestplate;
	    private ModelBiped modelArmor;

	public RenderPlayerZombie() {
		super(new ModelBiped(0.0F), 0.5F);
        this.modelBipedMain = (ModelBiped)this.mainModel;
        this.modelArmorChestplate = new ModelBiped(1.0F);
        this.modelArmor = new ModelBiped(0.5F);
	}
	
	


    
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        //return this.func_110817_a((EntityPlayerZombie)par1Entity);
    	return this.getLayered((EntityPlayerZombie)par1Entity);
    }
    
    public ResourceLocation getLayered(EntityPlayerZombie par1EntityPlayerZombie)
    {
    	String s=par1EntityPlayerZombie.getLayeredName();
    	ResourceLocation rl=layerdSkins.get(s);
    	if(rl==null)
    	{
    		rl=new ResourceLocation(s);
            Minecraft.getMinecraft().getTextureManager().loadTexture(rl,new ResourceLayeredTexture(par1EntityPlayerZombie.func_110309_l(), EntityPlayerZombie.overlay));
    		layerdSkins.put(s, rl);
    	}
    	return rl;
    	
    }




}
