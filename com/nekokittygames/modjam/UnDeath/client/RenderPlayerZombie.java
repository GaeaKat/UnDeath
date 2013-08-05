package com.nekokittygames.modjam.UnDeath.client;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.LayeredHighlighter;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;
import com.nekokittygames.modjam.UnDeath.EntityPlayerZombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
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
    protected ResourceLocation func_110775_a(Entity par1Entity)
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
    		Minecraft.getMinecraft().func_110434_K().func_110579_a(rl,new ResourceLayeredTexture(par1EntityPlayerZombie.getSkins()));
    		layerdSkins.put(s, rl);
    	}
    	return rl;
    	
    }




}
