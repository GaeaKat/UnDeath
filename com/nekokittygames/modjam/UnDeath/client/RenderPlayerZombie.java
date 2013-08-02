package com.nekokittygames.modjam.UnDeath.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderPlayerZombie extends RenderBiped {

	 private static final ResourceLocation field_110826_a = new ResourceLocation("textures/entity/steve.png");
	    private ModelBiped modelBipedMain;
	    private ModelBiped modelArmorChestplate;
	    private ModelBiped modelArmor;

	public RenderPlayerZombie(ModelBiped par1ModelBiped, float par2) {
		super(par1ModelBiped, par2);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
